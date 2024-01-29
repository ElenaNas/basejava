package webapp.model;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume=new Resume("0010", "Elena Nasikovskaia");
        System.out.println(resume.getUuid());
        System.out.println(resume.getFullName());

        System.out.println(resume.getContact(ContactType.MOBILE_PHONE_NUMBER));
        System.out.println(resume.getContact(ContactType.EMAIL));
        resume.getContact(ContactType.GITHUB_ACCOUNT);
        resume.getContact(ContactType.HOME_PAGE);

    }

    public Resume fillResume(String uuid, String fullName){
        Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

        contacts.put(ContactType.MOBILE_PHONE_NUMBER, "+7-909-139-64-62");
        contacts.put(ContactType.EMAIL, "nas-elena@yandex.ru");
        contacts.put(ContactType.SKYPE, "MySkype");
        contacts.put(ContactType.HOME_PAGE, "MyHomePage");
        contacts.put(ContactType.GITHUB_ACCOUNT, "https://github.com/ElenaNas");
        contacts.put(ContactType.LINKEDIN_ACCOUNT, "www.linkedin.com/in/elena-n-a5454a2a7");
        contacts.put(ContactType.STACKOVERFLOW_ACCOUNT, "MyStackoverflow");

        Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

        AbstractSection objectiveText = new TextSection("MyObjective");
        sections.put(SectionType.OBJECTIVE, objectiveText);

        AbstractSection personalText = new TextSection("My personal characteristics");
        sections.put(SectionType.PERSONAL, personalText);

        List<String> achievementsList = new ArrayList<>();
        achievementsList.add("Achievement1");
        achievementsList.add("Achievement2");
        achievementsList.add("Achievement3");
        AbstractSection achievements = new ListSection(achievementsList);

        sections.put(SectionType.ACHIEVEMENTS, achievements);

        List<String> qualificationsList = new ArrayList<>();
        qualificationsList.add("Qualification1");
        qualificationsList.add("Qualification2");
        qualificationsList.add("Qualification3");
        AbstractSection qualifications = new ListSection(qualificationsList);

        sections.put(SectionType.QUALIFICATIONS, qualifications);

        List<Company> workPlaceList = new ArrayList<>();

        LocalDate workStartDate1 = LocalDate.of(2016, Month.DECEMBER, 18);
        LocalDate workEndDate1 = LocalDate.now();
        Company workPlace1 = new Company("CompanyName1", "JobTitle1",
                "JobDescription1", "WorkPlaceURL1", workStartDate1, workEndDate1);
        workPlaceList.add(workPlace1);

        LocalDate workStartDate2 = LocalDate.of(2009, Month.SEPTEMBER, 1);
        LocalDate workEndDate2 = LocalDate.of(2016, Month.AUGUST, 10);
        Company workplace2 = new Company("CompanyName2", "JobTitle2",
                "JobDescription2", "WorkPlaceURL2", workStartDate2, workEndDate2);
        workPlaceList.add(workplace2);

        AbstractSection workExperienceSection = new CompanySection(workPlaceList);
        sections.put(SectionType.EXPERIENCE, workExperienceSection);

        List<Company> eduPlaceList = new ArrayList<>();

        LocalDate eduStartDate1 = LocalDate.of(2004, Month.SEPTEMBER, 1);
        LocalDate eduEndDate1 = LocalDate.of(2009, Month.JULY, 15);
        Company eduPlace1 = new Company("EduPlaceName1", "Faculty",
                "Diploma", "EduPlaceURL1", eduStartDate1, eduEndDate1);
        eduPlaceList.add(eduPlace1);

        LocalDate eduStartDate2 = LocalDate.of(2023, Month.SEPTEMBER, 1);
        LocalDate eduEndDate2 = LocalDate.now();
        Company eduPlace2 = new Company("EduPlaceName1", "Faculty",
                "Diploma", "EduPlaceURL1", eduStartDate1, eduEndDate1);
        eduPlaceList.add(eduPlace2);

        AbstractSection educationSection = new CompanySection(eduPlaceList);
        sections.put(SectionType.EDUCATION, educationSection);

        return new Resume(uuid, fullName);
    }
}
