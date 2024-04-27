package webapp;

import webapp.model.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestData {
   public static final String UUID_1 = String.valueOf(UUID.randomUUID());
   public static final String UUID_2 = String.valueOf(UUID.randomUUID());
   public static final String UUID_3 = String.valueOf(UUID.randomUUID());
   public static final String UUID_4 = String.valueOf(UUID.randomUUID());

   public static final Resume UUID_NOT_EXIST = new Resume("UUID_5", "dummy");
   public static final int INITIAL_SIZE = 3;

   public static Resume RESUME_1 = new Resume(UUID_1, "Elena");
   public static Resume RESUME_2 = new Resume(UUID_2, "Sofia");
   public static Resume RESUME_3 = new Resume(UUID_3, "Alexander");
   public static Resume RESUME_4 = new Resume(UUID_4, "Orsik");

   {
      RESUME_1.addContact(ContactType.MOBILE_PHONE_NUMBER, "+7-909-139-64-62");
      RESUME_1.addContact(ContactType.EMAIL, "nas-elena@yandex.ru");
      RESUME_1.addContact(ContactType.SKYPE, "MySkype");
      RESUME_1.addContact(ContactType.HOME_PAGE, "MyHomePage");
      RESUME_1.addContact(ContactType.GITHUB_ACCOUNT, "https://github.com/ElenaNas");
      RESUME_1.addContact(ContactType.LINKEDIN_ACCOUNT, "www.linkedin.com/in/elena-n-a5454a2a7");
      RESUME_1.addContact(ContactType.STACKOVERFLOW_ACCOUNT, "MyStackoverflow");

      Section objectiveText = new TextSection("MyObjective");
      RESUME_1.addSection(SectionType.OBJECTIVE, objectiveText);

      Section personalText = new TextSection("My personal characteristics");
      RESUME_1.addSection(SectionType.PERSONAL, personalText);

      List<String> achievementsList = new ArrayList<>();
      achievementsList.add("Achievement1");
      achievementsList.add("Achievement2");
      achievementsList.add("Achievement3");
      Section achievements = new ListSection(achievementsList);

      RESUME_1.addSection(SectionType.ACHIEVEMENTS, achievements);

      List<String> qualificationsList = new ArrayList<>();
      qualificationsList.add("Qualification1");
      qualificationsList.add("Qualification2");
      qualificationsList.add("Qualification3");
      Section qualifications = new ListSection(qualificationsList);

      RESUME_1.addSection(SectionType.QUALIFICATIONS, qualifications);

      List<Company.Occupation> workList1 = new ArrayList<>();
      LocalDate workStartDate1 = LocalDate.of(2016, Month.DECEMBER, 18);
      LocalDate workEndDate1 = LocalDate.now();
      Company.Occupation occupation1WorkPlace1 = new Company.Occupation(workStartDate1, workEndDate1, "JobTitle from workPlace 1",
              "JobDescription from workPlace1");
      workList1.add(occupation1WorkPlace1);
      Link workPlace1Link = new Link("Company#1", "WebSite");
      Company workPlace1 = new Company(workPlace1Link, workList1);
      RESUME_1.addSection(SectionType.EXPERIENCE, workPlace1);

      List<Company.Occupation> workList2 = new ArrayList<>();
      LocalDate workStartDate2 = LocalDate.of(2009, Month.SEPTEMBER, 1);
      LocalDate workEndDate2 = LocalDate.now();
      Company.Occupation occupation1WorkPlace2 = new Company.Occupation(workStartDate2, workEndDate2, "JobTitle from workPlace 2",
              "JobDescription from workPlace2");
      workList2.add(occupation1WorkPlace2);
      Link workPlace2Link = new Link("Company#1", "WebSite");
      Company workPlace2 = new Company(workPlace2Link, workList2);
      RESUME_1.addSection(SectionType.EXPERIENCE, workPlace2);

      List<Company.Occupation> eduPlaceList1 = new ArrayList<>();
      LocalDate eduStartDate1 = LocalDate.of(2004, Month.SEPTEMBER, 1);
      LocalDate eduEndDate1 = LocalDate.of(2009, Month.JULY, 15);
      Company.Occupation occupation1EduPlace1 = new Company.Occupation(eduStartDate1, eduEndDate1, "Faculty1", "Diploma2");
      eduPlaceList1.add(occupation1EduPlace1);
      Link eduPlace1Link = new Link("EduPlace1", "Website");
      Company eduPlace1 = new Company(eduPlace1Link, eduPlaceList1);
      RESUME_1.addSection(SectionType.EDUCATION, eduPlace1);

      List<Company.Occupation> eduPlaceList2 = new ArrayList<>();
      LocalDate eduStartDate2 = LocalDate.of(2023, Month.SEPTEMBER, 1);
      LocalDate eduEndDate2 = LocalDate.now();
      Company.Occupation occupation1EduPlace2 = new Company.Occupation(eduStartDate2, eduEndDate2, "Faculty2", "Diploma2");
      eduPlaceList2.add(occupation1EduPlace2);
      Link eduPlace2Link = new Link("EduPlace1", "Website");
      Company eduPlace2 = new Company(eduPlace2Link, eduPlaceList2);
      RESUME_1.addSection(SectionType.EDUCATION, eduPlace2);

   }
}
