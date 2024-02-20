package webapp.storage.strategy;

import webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamStorage implements IStreamStrategy {

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {

            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());
            writeContacts(dataOutputStream, resume.getContacts());
            writeSections(dataOutputStream, resume.getSections());
        }
    }

    private void writeContacts(DataOutputStream dataOutputStream, Map<ContactType, String> contacts) throws IOException {
        dataOutputStream.writeInt(contacts.size());
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            dataOutputStream.writeUTF(entry.getKey().name());
            dataOutputStream.writeUTF(entry.getValue());
        }
    }

    private void writeSections(DataOutputStream dataOutputStream, Map<SectionType, Section> sections) throws IOException {
        dataOutputStream.writeInt(sections.size());
        for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
            writeSection(dataOutputStream, entry.getKey(), entry.getValue());
        }
    }

    private void writeSection(DataOutputStream dataOutputStream, SectionType sectionType, Section section) throws IOException {
        dataOutputStream.writeUTF(sectionType.name());
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                dataOutputStream.writeUTF(((TextSection) section).getText());
                break;
            case ACHIEVEMENTS:
            case QUALIFICATIONS:
                List<String> dataList = ((ListSection) section).getDataList();
                dataOutputStream.writeInt(dataList.size());
                for (String data : dataList) {
                    dataOutputStream.writeUTF(data);
                }
                break;
            case EXPERIENCE:
            case EDUCATION:
                List<Company> companies = ((CompanySection) section).getCompanies();
                dataOutputStream.writeInt(companies.size());
                for (Company company : companies) {
                    dataOutputStream.writeUTF(company.getHomePage().toString());
                    dataOutputStream.writeUTF(company.toString());
                    writeOccupations(dataOutputStream, company.getOccupationList());
                }
                break;
        }
    }

    private void writeOccupations(DataOutputStream dataOutputStream, List<Company.Occupation> occupations) throws IOException {
        dataOutputStream.writeInt(occupations.size());
        for (Company.Occupation occupation : occupations) {
            writeLocalDate(dataOutputStream, occupation.getFromPeriod());
            writeLocalDate(dataOutputStream, occupation.getTillPeriod());
            dataOutputStream.writeUTF(occupation.getJobTitle());
            dataOutputStream.writeUTF(occupation.getJobDescription());
        }
    }

    private void writeLocalDate(DataOutputStream dataOutputStream, LocalDate localDate) throws IOException {
        dataOutputStream.writeInt(localDate.getYear());
        dataOutputStream.writeInt(localDate.getMonth().getValue());
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(is)) {
            String uuid = dataInputStream.readUTF();
            String fullName = dataInputStream.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readContacts(dataInputStream, resume);
            readSections(dataInputStream, resume);
            return resume;
        }
    }

    private void readContacts(DataInputStream dataInputStream, Resume resume) throws IOException {
        int size = dataInputStream.readInt();
        for (int i = 0; i < size; i++) {
            ContactType contactType = ContactType.valueOf(dataInputStream.readUTF());
            String contactValue = dataInputStream.readUTF();
            resume.addContact(contactType, contactValue);
        }
    }

    private void readSections(DataInputStream dataInputStream, Resume resume) throws IOException {
        int size = dataInputStream.readInt();
        for (int i = 0; i < size; i++) {
            SectionType sectionType = SectionType.valueOf(dataInputStream.readUTF());
            Section section = readSection(dataInputStream, sectionType);
            resume.addSection(sectionType, section);
        }
    }

    private Section readSection(DataInputStream dataInputStream, SectionType sectionType) throws IOException {
        return switch (sectionType) {
            case PERSONAL, OBJECTIVE -> new TextSection(dataInputStream.readUTF());
            case ACHIEVEMENTS, QUALIFICATIONS -> new ListSection(readOccupations(dataInputStream, dataInputStream::readUTF));
            case EXPERIENCE, EDUCATION -> new CompanySection(
                    readOccupations(dataInputStream, () -> new Company(
                            new Link(dataInputStream.readUTF(), dataInputStream.readUTF()),
                            readOccupations(dataInputStream, () -> new Company.Occupation(
                                    readLocalDate(dataInputStream), readLocalDate(dataInputStream), dataInputStream.readUTF(), dataInputStream.readUTF()
                            ))
                    )));
        };
    }

    private <T> List<T> readOccupations(DataInputStream dis, Reader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private LocalDate readLocalDate(DataInputStream dataInputStream) throws IOException {
        return LocalDate.of(dataInputStream.readInt(), dataInputStream.readInt(), 1);
    }

    private interface Reader<T> {
        T read() throws IOException;
    }
}

