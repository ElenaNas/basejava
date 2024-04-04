package resumes.storage.strategy;

import resumes.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamStorage implements IStreamStrategy {

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {

            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());

            writeCollection(dataOutputStream, resume.getContacts().entrySet(),
                    (entry) -> {
                        dataOutputStream.writeUTF(entry.getKey().name());
                        dataOutputStream.writeUTF(entry.getValue());
                    });
            writeCollection(dataOutputStream, resume.getSections().entrySet(),
                    (entry) -> writeSection(dataOutputStream, entry.getKey(), entry.getValue()));
        }
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, Writer<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            writer.write(item);
        }
    }

    private String getCompanyString(Company company) {
        return company.getHomePage().toString() + " " + company;
    }

    private void writeSection(DataOutputStream dataOutputStream, SectionType sectionType, Section section) throws IOException {
        dataOutputStream.writeUTF(sectionType.name());
        switch (sectionType) {
            case PERSONAL, OBJECTIVE -> dataOutputStream.writeUTF(((TextSection) section).getText());
            case ACHIEVEMENTS, QUALIFICATIONS -> {
                List<String> dataList = ((ListSection) section).getDataList();
                writeCollection(dataOutputStream, dataList,
                        dataOutputStream::writeUTF);
            }
            case EXPERIENCE, EDUCATION -> {
                List<Company> companies = ((CompanySection) section).getCompanies();
                writeCollection(dataOutputStream, companies,
                        (company) -> {
                            dataOutputStream.writeUTF(company.getHomePage().toString());
                            dataOutputStream.writeUTF(getCompanyString(company));
                            writeCollection(dataOutputStream, company.getOccupationList(),
                                    (occupation) -> {
                                        writeLocalDate(dataOutputStream, occupation.getFromPeriod());
                                        writeLocalDate(dataOutputStream, occupation.getTillPeriod());
                                        dataOutputStream.writeUTF(occupation.getJobTitle());
                                        dataOutputStream.writeUTF(occupation.getJobDescription());
                                    });
                        });
            }
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
            readLines(dataInputStream, () -> {
                ContactType contactType = ContactType.valueOf(dataInputStream.readUTF());
                String contactValue = dataInputStream.readUTF();
                resume.addContact(contactType, contactValue);
            });
            readLines(dataInputStream, () -> {
                SectionType sectionType = SectionType.valueOf(dataInputStream.readUTF());
                Section section = readSection(dataInputStream, sectionType);
                resume.addSection(sectionType, section);
            });
            return resume;
        }
    }

    private void readLines(DataInputStream dataInputStream, DataReader dataReader) throws IOException {
        int size = dataInputStream.readInt();
        for (int i = 0; i < size; i++) {
            dataReader.readData();
        }
    }

    private Section readSection(DataInputStream dataInputStream, SectionType sectionType) throws IOException {
        return switch (sectionType) {
            case PERSONAL, OBJECTIVE -> new TextSection(dataInputStream.readUTF());
            case ACHIEVEMENTS, QUALIFICATIONS ->
                    new ListSection(readOccupations(dataInputStream, dataInputStream::readUTF));
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

    private interface DataReader {
        void readData() throws IOException;
    }

    private interface Reader<T> {
        T read() throws IOException;
    }

    private interface Writer<T> {
        void write(T item) throws IOException;
    }
}

