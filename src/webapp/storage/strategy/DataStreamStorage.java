package webapp.storage.strategy;

import webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamStorage implements IStreamStrategy {
    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream)
        ) {
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());
            dataOutputStream.writeInt(resume.getContacts().size());

            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                dataOutputStream.writeUTF(entry.getKey().name());
                dataOutputStream.writeUTF(entry.getValue());
            }

            Map<SectionType, Section> sections = resume.getSections();
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                SectionType type = entry.getKey();
                Section section = entry.getValue();
                dataOutputStream.writeUTF(type.name());

                switch (type) {
                    case PERSONAL, OBJECTIVE:
                        dataOutputStream.writeUTF(((TextSection) section).getText());
                        break;
                    case QUALIFICATIONS, ACHIEVEMENTS:
                        writeCollection(dataOutputStream, ((ListSection) section).getDataList());
                        break;
                    case EDUCATION, EXPERIENCE:
                        for (Company company : ((CompanySection) section).getCompanies()) {
                            writeOccupation(company, dataOutputStream);
                        }
                }
                break;
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> void writeCollection(DataOutputStream dataOutputStream, Collection<T> dataList) throws IOException, ClassNotFoundException {
        dataOutputStream.writeInt(dataList.size());
        for (T t : dataList) {
            dataOutputStream.writeUTF(String.valueOf(t));
        }
    }

    public void writeOccupation(Company company,
                                DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(String.valueOf(company.toString()));
        dataOutputStream.writeUTF(String.valueOf(company.getHomePage()));
        for (Company.Occupation occupations : company.getOccupationList()) {
            dataOutputStream.writeUTF(String.valueOf(occupations.getFromPeriod()));
            dataOutputStream.writeUTF(String.valueOf(occupations.getTillPeriod()));
            dataOutputStream.writeUTF(String.valueOf(occupations.getJobTitle()));
            dataOutputStream.writeUTF(String.valueOf(occupations.getJobDescription()));
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (
                DataInputStream dataInputStream = new DataInputStream(inputStream)
        ) {
            String uuid = dataInputStream.readUTF();
            String fullName = dataInputStream.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int contactsSize = dataInputStream.readInt();
            for (int i = 0; i < contactsSize; i++) {
                resume.addContact(ContactType.valueOf(dataInputStream.readUTF()), dataInputStream.readUTF());
            }

            Map<SectionType, Section> sections = resume.getSections();
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                SectionType type = entry.getKey();
                Section section = entry.getValue();
                SectionType sectionType = SectionType.valueOf(dataInputStream.readUTF());
                resume.addSection(sectionType, readSection(dataInputStream, sectionType, section));
            }
            return resume;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private Section readSection(DataInputStream dataInputStream, SectionType type, Section section) throws IOException, ClassNotFoundException {
        switch (type) {
            case PERSONAL, OBJECTIVE:
                return new TextSection(dataInputStream.readUTF());
            case ACHIEVEMENTS, QUALIFICATIONS:
                return new ListSection(String.valueOf(readList(dataInputStream, new ObjectInputStream(dataInputStream))));
            case EDUCATION, EXPERIENCE:
                for (Company company : ((CompanySection) section).getCompanies()) {
                    return new CompanySection((List<Company>) readOccupation(company, dataInputStream));
                }
            default:
                throw new IllegalStateException();
        }
    }


    private List<Company> readList(DataInputStream dataInputStream, ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        int size = dataInputStream.readInt();
        List<Company> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add((Company) objectInputStream.readObject());
        }
        return list;
    }


    public Company.Occupation readOccupation(Company company, DataInputStream dataInputStream) throws IOException {
        String companyName = dataInputStream.readUTF();
        String homePage = dataInputStream.readUTF();
        Company.Occupation occupation = new Company.Occupation();
        int occupationsSize = company.getOccupationList().size();
        for (int i = 0; i < occupationsSize; i++) {
            occupation.setFromPeriod(LocalDate.parse(dataInputStream.readUTF()));
            occupation.setTillPeriod(LocalDate.parse(dataInputStream.readUTF()));
            occupation.setJobTitle(dataInputStream.readUTF());
            occupation.setJobDescription(dataInputStream.readUTF());
        }
        return occupation;
    }
}

