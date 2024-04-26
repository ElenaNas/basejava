import webapp.Config;
import webapp.model.*;
import webapp.storage.IStorage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static webapp.util.DateUtil.DATE_FORMATTER;

public class ResumeServlet extends HttpServlet {
    IStorage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        Resume resume;

        boolean newResume = (uuid == null || uuid.isEmpty());
        if (newResume) {
            resume = new Resume(fullName);
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        }

        updateContacts(request, resume);
        updateSections(request, resume);

        if (newResume) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }

        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                resume = storage.get(uuid);
                break;
            case "add":
                resume = Resume.clearSections();
                request.setAttribute("resume", resume);
                request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
                return;
            case "edit":
                resume = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    Section section = resume.getSection(type);

                    switch (type) {
                        case OBJECTIVE, PERSONAL:
                            if (section == null) {
                                section = new TextSection("");
                            }
                            break;
                        case ACHIEVEMENTS:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = new ListSection("");
                            }
                            break;
                        case EDUCATION:
                        case EXPERIENCE:
                            CompanySection companySection = (CompanySection) section;
                            List<Company> companiesToEdit = new ArrayList<>();
                            List<Company.Occupation> occupationsToEdit = new ArrayList<>();
                            occupationsToEdit.add(new Company.Occupation());
                            if (section == null) {
                                companiesToEdit.add(new Company("", "", new Company.Occupation()));
                            } else {
                                for (Company company : companySection.getCompanies()) {
                                    if (company.getGetOccupationList() == null) {
                                        occupationsToEdit.add(new Company.Occupation());
                                    } else {
                                        occupationsToEdit.addAll(company.getOccupationList());
                                    }
                                    companiesToEdit.add((new Company(company.getHomePage(), occupationsToEdit)));
                                }
                            }
                            section = new CompanySection(companiesToEdit);
                            break;
                    }
                    resume.addSection(type, section);
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    private void updateContacts(HttpServletRequest request, Resume resume) {
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value == null || value.trim().isEmpty()) {
                resume.getContacts().remove(type);
            } else {
                resume.addContact(type, value);
            }
        }
    }

    private void updateSections(HttpServletRequest request, Resume resume) {
        for (SectionType type : SectionType.values()) {
            String[] values = request.getParameterValues(type.name());
            if (values == null || values.length < 2) {
                resume.getSections().remove(type);
            } else {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(type, new TextSection(values[0]));
                        break;
                    case ACHIEVEMENTS:
                    case QUALIFICATIONS:
                        resume.addSection(type, new ListSection(values));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Company> companyList = createCompanies(request, type);
                        resume.addSection(type, new CompanySection(companyList));
                        break;
                }
            }
        }
    }

    private List<Company> createCompanies(HttpServletRequest request, SectionType type) {
        List<Company> companyList = new ArrayList<>();
        String[] companyNames = request.getParameterValues(type.name());
        String[] urls = request.getParameterValues(type.name() + "url");

        for (int i = 0; i < companyNames.length; i++) {
            String companyName = companyNames[i];
            if (companyName != null && !companyName.trim().isEmpty()) {
                List<Company.Occupation> occupations = createOccupations(request, type, i);
                companyList.add(new Company(new Link(companyName, urls[i]), occupations));
            }
        }
        return companyList;
    }

    private List<Company.Occupation> createOccupations(HttpServletRequest request, SectionType type, int index) {
        List<Company.Occupation> occupations = new ArrayList<>();
        String[] startDates = request.getParameterValues(type.name() + index + "fromPeriod");
        String[] endDates = request.getParameterValues(type.name() + index + "tillPeriod");
        String[] titles = request.getParameterValues(type.name() + index + "jobTitle");
        String[] descriptions = request.getParameterValues(type.name() + index + "jobDescription");

        for (int j = 0; j < titles.length; j++) {
            try {
                if (titles[j] != null && !titles[j].trim().isEmpty()) {
                    LocalDate startDate = parseDate(startDates[j]);
                    LocalDate endDate = parseDate(endDates[j]);
                    occupations.add(new Company.Occupation(startDate, endDate, titles[j], descriptions[j]));
                }
            } catch (Exception e) {
                System.out.println("Date is not valid");
            }
        }
        return occupations;
    }

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty() || "Present Day".equals(dateStr)) {
            return LocalDate.now();
        } else {
            YearMonth yearMonth = YearMonth.parse(dateStr, DATE_FORMATTER);
            return LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
        }
    }
}



