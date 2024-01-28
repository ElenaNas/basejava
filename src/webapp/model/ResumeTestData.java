package webapp.model;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume=new Resume("0010", "Elena Nasikovskaia");
        System.out.println(resume.getUuid());
        System.out.println(resume.getFullName());

        System.out.println(resume.getContact(ContactSection.MOBILE_PHONE_NUMBER));
        System.out.println(resume.getContact(ContactSection.EMAIL));
        resume.getContact(ContactSection.GITHUB_ACCOUNT);
        resume.getContact(ContactSection.HOME_PAGE);




//        resume.getContact.contacts.put(ContactSection.MOBILE_PHONE_NUMBER, "+7909-139-64-62");
//        resume.contacts.put(ContactSection.EMAIL, "nas-elena@yandex.com");
//        resume.contacts.put(ContactSection.LINKEDIN_ACCOUNT, "linkedIn");
//        resume.contacts.put(ContactSection.SKYPE, "Skype");
//        resume.contacts.put(ContactSection.GITHUB_ACCOUNT, "https://github.com/ElenaNas");
//        resume.contacts.put(ContactSection.STACKOVERFLOW_ACCOUNT, "Stackoverflow");
//        resume.contacts.put(ContactSection.HOME_PAGE, "HomePage");
//
//        resume.sections.put(SectionType.PERSONAL, TextSection);

    }
}
