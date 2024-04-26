<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="webapp.model.TextSection" %>
<%@ page import="webapp.model.ListSection" %>
<%@ page import="webapp.model.CompanySection" %>
<style>
    .header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    section {
        margin-bottom: 30px;
    }

    .upload-photo-frame {
        margin-right: auto;
        margin-left: auto;
        display: flex;
        align-items: center;
    }

    .upload-photo-frame input[type="file"] {
        display: none;
    }

    .upload-photo-frame label {
        background-color: #007bff;
        color: #fff;
        border: 2px solid #007bff;
        border-radius: 8px;
        padding: 6px 16px;
        cursor: pointer;
        margin-left: 10px;
    }

    .upload-photo-frame label:hover {
        background-color: #0056b3;
        border-color: #0056b3;
    }

    .edit-button img {
        width: 20px;
        height: 20px;
    }

    .edit-section-link img {
        width: 20px;
        height: 20px;
    }

    h2, h3 {
        text-align: center;
    }

    table {
        width: 80%;
        margin-top: 20px;
        margin-left: auto;
        margin-right: auto;
        border-collapse: collapse;
    }

    th, td {
        border-bottom: 1px solid #ddd;
        padding: 12px;
    }

    tr:nth-child(even) {
        background-color: #f9f9f9;
    }

    th {
        text-align: center;
        background-color: #f0f0f0;
        border-radius: 6px;
        font-size: 18px;
    }

    .personal-section-content {
        font-size: 20px;
        font-style: italic;
    }

    .occupation-dates {
        width: 15%;
        margin-bottom: 10px;
    }
</style>


<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="WEB-INF/jsp/css/style.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Crimson+Pro&display=swap">
    <jsp:useBean id="resume" type="webapp.model.Resume" scope="request"/>
    <title>Curriculum Vitae ${resume.fullName}</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/fragments/header.jsp"/>
<div class="scrollable-panel">
    <div class="form-wrapper">
        <section>
            <div class="header">
                <div style="display: flex; align-items: center;">
                    <div class="full-name" style="font-size: 24px; font-weight: bold; color: red;">
                        ${resume.fullName}&nbsp;
                    </div>
                    <a href="resume?uuid=${resume.uuid}&action=edit&type=${type}" class="edit-link edit-button"
                       style="margin-left: 10px;">
                        <img src="img/pencil.png" alt="Edit">
                    </a>
                </div>
                <div class="upload-photo-frame">
                    <input type="file" accept="image/*" id="upload-photo" name="upload-photo">
                    <label for="upload-photo">Upload Photo</label>
                </div>
            </div>
            <p>
                <c:forEach var="contactEntry" items="${resume.contacts}">
                    <jsp:useBean id="contactEntry"
                                 type="java.util.Map.Entry<webapp.model.ContactType, java.lang.String>"/>
                    <c:if test="${not empty contactEntry.value}"/>
                        <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
                </c:forEach>
            <p>
            <hr>
            <table cellpadding="2">
                <c:forEach var="sectionEntry" items="${resume.sections}">
                    <jsp:useBean id="sectionEntry"
                                 type="java.util.Map.Entry<webapp.model.SectionType, webapp.model.Section>"/>
                    <c:set var="type" value="${sectionEntry.key}"/>
                    <c:set var="section" value="${sectionEntry.value}"/>
                    <jsp:useBean id="section" type="webapp.model.Section"/>

                    <tr style="margin-bottom: 25px;">
                        <td colspan="2"><h2><a name="type.name">${type.title}</a></h2></td>
                        <td><a href="resume?uuid=${resume.uuid}&action=edit&type=${type}" class="edit-section-link"><img
                                src="img/pencil.png" alt="Edit"></a></td>
                    </tr>

                    <c:choose>
                        <c:when test="${type=='OBJECTIVE'}">
                            <tr>
                                <td colspan="2">
                                    <h3><%=((TextSection) section).getText()%>
                                    </h3>
                                </td>
                            </tr>
                        </c:when>
                        <c:when test="${type=='PERSONAL'}">
                            <tr>
                                <td colspan="2">
                                    <span class="personal-section-content">
                                        <%=((TextSection) section).getText()%>
                                    </span>
                                </td>
                            </tr>
                        </c:when>

                        <c:when test="${type == 'ACHIEVEMENTS' || type== 'QUALIFICATIONS'}">
                            <tr>
                                <td colspan="2">
                                    <ul style="font-size: 20px;">
                                        <c:forEach var="list" items="<%=((ListSection)section).getDataList()%>">
                                            <li>${list}</li>
                                        </c:forEach>
                                    </ul>
                                </td>
                            </tr>
                        </c:when>

                        <c:when test="${type == 'EDUCATION' || type == 'EXPERIENCE'}">
                            <c:forEach var="company" items="<%=((CompanySection) section).getCompanies()%>">
                                <tr>
                                    <td colspan="2">
                                        <c:choose>
                                            <c:when test="${empty company.homePage.url}">
                                                <h3>${company.homePage.name}</h3>
                                            </c:when>
                                            <c:otherwise>
                                                <h3><a href="${company.homePage.url}">${company.homePage.name}</a></h3>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>

                                <c:forEach var="occupation" items="${company.occupationList}">
                                    <jsp:useBean id="occupation" type="webapp.model.Company.Occupation"/>
                                    <tr>
                                        <td class="occupation-dates">
                                            <div style="text-align: center; margin-left: 11px;">${occupation.fromPeriod}
                                                -
                                            </div>
                                            <div style="text-align: center;">${occupation.tillPeriod}</div>
                                        </td>
                                        <td class="occupation-details">
                                            <div style="text-align: center; font-size: 18px; font-weight: bold;">
                                                    ${occupation.jobTitle}</div>
                                            <br>
                                            <div style="text-align: left;">
                                                    ${occupation.jobDescription}</div>
                                        </td>
                                    </tr>
                                </c:forEach>

                            </c:forEach>
                        </c:when>
                    </c:choose>
                </c:forEach>
            </table>
            <a href="javascript:void(0);" onclick="window.history.back()">
                <img src="img/home.png" alt="Back"/>
                Home
            </a>

        </section>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
</body>
</html>

