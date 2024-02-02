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
        String name=resume.getFullName();
        String uuid= resume.getUuid();

        fillResume(uuid, name);
    }

    public static Resume fillResume(String uuid, String fullName){
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

        List<Company.Occupation> workList1 = new ArrayList<>();
        LocalDate workStartDate1 = LocalDate.of(2016, Month.DECEMBER, 18);
        LocalDate workEndDate1 = LocalDate.now();
        Company.Occupation occupation1WorkPlace1 = new Company.Occupation("JobTitle from workPlace 1",
                "JobDescription from workPlace1", workStartDate1, workEndDate1);
        workList1.add(occupation1WorkPlace1);
        Link workPlace1Link = new Link("Company#1", "WebSite");
        Company workPlace1 = new Company(workPlace1Link, workList1);
        sections.put(SectionType.EXPERIENCE, workPlace1);

        List<Company.Occupation> workList2 = new ArrayList<>();
        LocalDate workStartDate2 = LocalDate.of(2009, Month.SEPTEMBER, 1);
        LocalDate workEndDate2 = LocalDate.now();
        Company.Occupation occupation1WorkPlace2 = new Company.Occupation("JobTitle from workPlace 2",
                "JobDescription from workPlace2", workStartDate2, workEndDate2);
        workList2.add(occupation1WorkPlace2);
        Link workPlace2Link = new Link("Company#1", "WebSite");
        Company workPlace2 = new Company(workPlace2Link, workList2);
        sections.put(SectionType.EXPERIENCE, workPlace2);

        List<Company.Occupation> eduPlaceList1 = new ArrayList<>();
        LocalDate eduStartDate1 = LocalDate.of(2004, Month.SEPTEMBER, 1);
        LocalDate eduEndDate1 = LocalDate.of(2009, Month.JULY, 15);
        Company.Occupation occupation1EduPlace1 = new Company.Occupation("Faculty1", "Diploma2", eduStartDate1, eduEndDate1);
        eduPlaceList1.add(occupation1EduPlace1);
        Link eduPlace1Link = new Link("EduPlace1", "Website");
        Company eduPlace1 = new Company(eduPlace1Link, eduPlaceList1);
        sections.put(SectionType.EDUCATION, eduPlace1);

        List<Company.Occupation> eduPlaceList2 = new ArrayList<>();
        LocalDate eduStartDate2 = LocalDate.of(2023, Month.SEPTEMBER, 1);
        LocalDate eduEndDate2 = LocalDate.now();
        Company.Occupation occupation1EduPlace2 = new Company.Occupation("Faculty2", "Diploma2", eduStartDate2, eduEndDate2);
        eduPlaceList2.add(occupation1EduPlace2);
        Link eduPlace2Link = new Link("EduPlace1", "Website");
        Company eduPlace2 = new Company(eduPlace2Link, eduPlaceList2);
        sections.put(SectionType.EDUCATION, eduPlace2);

        return new Resume(uuid, fullName);
    }
}
