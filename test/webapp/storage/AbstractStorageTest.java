package webapp.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webapp.Config;
import webapp.exception.ExistStorageException;
import webapp.exception.NotExistStorageException;
import webapp.exception.StorageException;
import webapp.model.ContactType;
import webapp.model.Resume;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstractStorageTest {
    //protected static final File STORAGE_DIRECTORY = new File("C:\\Users\\nas-e\\basejava\\src\\webapp\\storageSer");
    protected static final File STORAGE_DIRECTORY = Config.get().getStorageDir();

    protected IStorage storage;

    public AbstractStorageTest(IStorage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = String.valueOf(UUID.randomUUID());
    private static final String UUID_2 = String.valueOf(UUID.randomUUID());
    private static final String UUID_3 = String.valueOf(UUID.randomUUID());
    private static final String UUID_4 = String.valueOf(UUID.randomUUID());

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1, "Elena");
        RESUME_2 = new Resume(UUID_2, "Sofia");
        RESUME_3 = new Resume(UUID_3, "Alexander");
        RESUME_4 = new Resume(UUID_4, "Orsik");

        ResumeTestData.fillResume(RESUME_1.getUuid(), RESUME_1.getFullName());

        RESUME_1.setContact(ContactType.MOBILE_PHONE_NUMBER, "+7-909-139-64-62");
        RESUME_1.setContact(ContactType.EMAIL, "nas-elena@yandex.ru");
        RESUME_1.setContact(ContactType.SKYPE, "MySkype");
//        RESUME_1.setContact(ContactType.HOME_PAGE, "MyHomePage");
//        RESUME_1.setContact(ContactType.GITHUB_ACCOUNT, "https://github.com/ElenaNas");
//        RESUME_1.setContact(ContactType.LINKEDIN_ACCOUNT, "www.linkedin.com/in/elena-n-a5454a2a7");
//        RESUME_1.setContact(ContactType.STACKOVERFLOW_ACCOUNT, "MyStackoverflow");
//
//        Section objectiveText = new TextSection("MyObjective");
//        RESUME_1.setSection(SectionType.OBJECTIVE, objectiveText);
//
//        Section personalText = new TextSection("My personal characteristics");
//        RESUME_1.setSection(SectionType.PERSONAL, personalText);

//        List<String> achievementsList = new ArrayList<>();
//        achievementsList.add("Achievement1");
//        achievementsList.add("Achievement2");
//        achievementsList.add("Achievement3");
//        Section achievements = new ListSection(achievementsList);
//
//        RESUME_1.setSection(SectionType.ACHIEVEMENTS, achievements);
//
//        List<String> qualificationsList = new ArrayList<>();
//        qualificationsList.add("Qualification1");
//        qualificationsList.add("Qualification2");
//        qualificationsList.add("Qualification3");
//        Section qualifications = new ListSection(qualificationsList);
//
//        RESUME_1.setSection(SectionType.QUALIFICATIONS, qualifications);
//
//        List<Company.Occupation> workList1 = new ArrayList<>();
//        LocalDate workStartDate1 = LocalDate.of(2016, Month.DECEMBER, 18);
//        LocalDate workEndDate1 = LocalDate.now();
//        Company.Occupation occupation1WorkPlace1 = new Company.Occupation(workStartDate1, workEndDate1, "JobTitle from workPlace 1",
//                "JobDescription from workPlace1");
//        workList1.add(occupation1WorkPlace1);
//        Link workPlace1Link = new Link("Company#1", "WebSite");
//        Company workPlace1 = new Company(workPlace1Link, workList1);
//        RESUME_1.setSection(SectionType.EXPERIENCE, workPlace1);
//
//        List<Company.Occupation> workList2 = new ArrayList<>();
//        LocalDate workStartDate2 = LocalDate.of(2009, Month.SEPTEMBER, 1);
//        LocalDate workEndDate2 = LocalDate.now();
//        Company.Occupation occupation1WorkPlace2 = new Company.Occupation(workStartDate2, workEndDate2, "JobTitle from workPlace 2",
//                "JobDescription from workPlace2");
//        workList2.add(occupation1WorkPlace2);
//        Link workPlace2Link = new Link("Company#1", "WebSite");
//        Company workPlace2 = new Company(workPlace2Link, workList2);
//        RESUME_1.setSection(SectionType.EXPERIENCE, workPlace2);
//
//        List<Company.Occupation> eduPlaceList1 = new ArrayList<>();
//        LocalDate eduStartDate1 = LocalDate.of(2004, Month.SEPTEMBER, 1);
//        LocalDate eduEndDate1 = LocalDate.of(2009, Month.JULY, 15);
//        Company.Occupation occupation1EduPlace1 = new Company.Occupation(eduStartDate1, eduEndDate1, "Faculty1", "Diploma2");
//        eduPlaceList1.add(occupation1EduPlace1);
//        Link eduPlace1Link = new Link("EduPlace1", "Website");
//        Company eduPlace1 = new Company(eduPlace1Link, eduPlaceList1);
//        RESUME_1.setSection(SectionType.EDUCATION, eduPlace1);
//
//        List<Company.Occupation> eduPlaceList2 = new ArrayList<>();
//        LocalDate eduStartDate2 = LocalDate.of(2023, Month.SEPTEMBER, 1);
//        LocalDate eduEndDate2 = LocalDate.now();
//        Company.Occupation occupation1EduPlace2 = new Company.Occupation(eduStartDate2, eduEndDate2, "Faculty2", "Diploma2");
//        eduPlaceList2.add(occupation1EduPlace2);
//        Link eduPlace2Link = new Link("EduPlace1", "Website");
//        Company eduPlace2 = new Company(eduPlace2Link, eduPlaceList2);
//        RESUME_1.setSection(SectionType.EDUCATION, eduPlace2);
    }

    private static final Resume UUID_NOT_EXIST = new Resume("UUID_5", "dummy");
    private static final int INITIAL_SIZE = 3;

    @BeforeEach
    void setUp() throws StorageException {
        clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
        System.out.println("Storage reloaded. It contains 3 resumes");
    }

    @Test
    public void size() throws StorageException {
        assertSize(INITIAL_SIZE);
        System.out.println("Storage size is " + INITIAL_SIZE + ".");
    }

    @Test
    public void get() throws NotExistStorageException {
        assertGet(RESUME_1);
        System.out.println("Here's the resume you are looking for: " + RESUME_1);
        assertGet(RESUME_2);
        System.out.println("Here's the resume you are looking for: " + RESUME_1);
        assertGet(RESUME_3);
        System.out.println("Here's the resume you are looking for: " + RESUME_1);
    }

    @Test
    public void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        assertEquals(list, Arrays.asList(RESUME_3, RESUME_1, RESUME_2));
        System.out.println("All resumes from the storage are listed below:\n" + storage.getAllSorted());
    }

    @Test
    public void update() throws NotExistStorageException {
        Resume resume = new Resume(UUID_1, "Elena Nasikovskaia");
        storage.update(resume);
        assertEquals(resume, storage.get(RESUME_1.getUuid()));
        System.out.println("Resume " + resume.getUuid() + " has been updated.");
    }

    @Test
    public void updateNotExist() throws StorageException {
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_NOT_EXIST.getUuid()));
        System.out.println("Storage does not contain resume uuid0.");
    }

    @Test
    public void clear() throws StorageException {
        storage.clear();
        assertSize(0);
        System.out.println("Storage has been cleared, current size is " + 0);
    }

    @Test
    public void save() throws ExistStorageException {
        assertSize(INITIAL_SIZE);
        storage.save(RESUME_4);
        assertSize(INITIAL_SIZE + 1);
        assertGet(RESUME_4);
        System.out.println("Resume " + RESUME_4 + " has been added.");
    }

    @Test
    public void delete() throws NotExistStorageException {
        storage.delete(UUID_2);
        assertSize(INITIAL_SIZE - 1);
        System.out.println("Resume " + RESUME_2 + " has been deleted.");
        System.out.println("Storage size is " + (INITIAL_SIZE - 1) + ".");
    }

    @Test
    public void getNotExist() throws StorageException {
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_NOT_EXIST.getUuid()));
    }

    @Test
    public void saveExisting() throws StorageException {
        assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
    }

    private void assertSize(int size) throws StorageException {
        assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}

