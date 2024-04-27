<%@ page import="webapp.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="webapp.model.SectionType, webapp.model.TextSection, webapp.model.ListSection" %>
<%@ page import="webapp.util.DateUtil" %>

<!DOCTYPE html>
<html>
<head>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        section {
            font-family: Arial, sans-serif;
            background-color: #fff;
            padding: 20px;
            max-width: 800px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin: 20px auto;
        }

        h3.section-title, dt.section-title {
            color: #007bff;
            margin-top: 20px;
        }

        dl {
            margin-bottom: 15px;
        }

        dt {
            font-weight: bold;
        }

        input[type="text"], textarea {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        button {
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 4px;
            padding: 10px 20px;
            cursor: pointer;
            margin-top: 10px;
            align-content: space-evenly;
            align-items: self-start;
            align-self: self-start;
        }

        button:hover {
            background-color: #0056b3;
        }

        .date-field1 {
            width: 100%;
            margin-bottom: 10px;
        }

        .date-field2 {
            width: 100%;
            margin-bottom: 10px;
        }

        .error {
            border: 1px solid red;
        }
    </style>

    <script>
        function validateForm() {
            var nameInput = document.querySelector('input[name="fullName"]');
            var isValid = true;

            if (!nameInput.value.trim()) {
                nameInput.classList.add('error');
                isValid = false;
            } else {
                nameInput.classList.remove('error');
            }

            if (!isValid) {
                alert('Please fill in the Name field.');
                return false;
            }
            return true;
        }

    </script>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="webapp.model.Resume" scope="request"/>
    <title>CV ${resume.fullName}</title>
</head>

<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded" onsubmit="return validateForm()">
        <input type="hidden" name="uuid" value="${resume.uuid}">

        <dl>
            <dt class="section-title">Name:</dt>
            <dd>
                <label>
                    <input type="text" name="fullName" size="50" value="${resume.fullName}"
                           placeholder="Your Full Name" class="validate">
                </label>
            </dd>
        </dl>

        <h3 class="section-title">Contact Details:</h3>

        <c:forEach var="type" items="${ContactType.values()}">
            <dl>
                <dt>${type.title}</dt>
                <dd>
                    <label>
                        <input type="text" name="${type.name()}" size="30" value="${resume.getContact(type)}"
                               placeholder="<c:if test='${type == ContactType.MOBILE_PHONE_NUMBER}'>Your Phone Number (###)###-##-##</c:if>
                                            <c:if test='${type != ContactType.MOBILE_PHONE_NUMBER}'>Your ${type.title}</c:if>">
                    </label>
                </dd>
            </dl>
        </c:forEach>

        <hr>

        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" class="webapp.model.Section"/>

            <h3><a>${type.title}</a></h3>

            <c:choose>

                <c:when test="${type=='PERSONAL'}">
                    <label>
                        <textarea
                                name="${type.name()}" cols="75" rows="5"
                                placeholder="Add Your Personal Info here">
                                ${section.getText()}
                        </textarea>
                    </label>
                </c:when>


                <c:when test="${type=='OBJECTIVE'}">
                    <label>
                        <input type='text' name='${type.name()}' size="75"
                               value="<%=((TextSection)section).getText()%>"
                               placeholder="Add your Career Objective here">
                    </label>
                </c:when>

                <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENTS'}">
                    <label>
                        <textarea name="${type}" cols="75" rows="5"
                                  placeholder="Enumerate here">
                           <%=String.join("\n", ((ListSection) section).getDataList())%>
                        </textarea>
                    </label>
                </c:when>

                <c:when test="${type == 'EDUCATION' || type =='EXPERIENCE'}">
                    <c:forEach var="company" items="${section.getCompanies()}"
                               varStatus="counter">
                        <dl>
                            <dt>Company Name:</dt>
                            <dd><label>
                                <input type="text" name="${type}" size="20" value="${company.homePage.name}"
                                       placeholder="Add Company Name here">
                            </label></dd>
                        </dl>

                        <dl>
                            <dt>Company website:</dt>
                            <dd><label>
                                <input type="text" name="${type}url" size="20" value="${company.homePage.url}"
                                       placeholder="Add website here">
                            </label></dd>
                        </dl>

                        <c:forEach var="occupation" items="${company.occupationList}">
                            <jsp:useBean id="occupation" type="webapp.model.Company.Occupation"/>

                            <div class="date-field1">
                                <label>Since:
                                    <input type="text" name="${type}${counter.index}fromPeriod" size="10"
                                           value="<%=DateUtil.format(occupation.getFromPeriod())%>"
                                           placeholder="MM/yyyy">
                                </label>
                            </div>

                            <div class="date-field2">
                                <label>Until:
                                    <input type="text" name="${type}${counter.index}tillPeriod" size="10"
                                           value="<%=DateUtil.format(occupation.getTillPeriod())%>"
                                           placeholder="MM/yyyy">
                                </label>
                            </div>

                            <dl>
                                <dt>Job Title:</dt>
                                <dd><label>
                                    <input type="text"
                                           name="${type}${counter.index}jobTitle"
                                           size="20"
                                           value="<c:if test="${not empty occupation.jobTitle}"><%=occupation.getJobTitle()%></c:if>"
                                           placeholder="Add Title here">
                                </label>
                                </dd>
                            </dl>

                            <dl>
                                <dt>Job Description:</dt>
                                <dd>
                                    <label>
                                        <textarea name="${type.name()}${counter.index}jobDescription"
                                                  id="${type.name()}${counter.index}jobDescription"
                                                  cols="75" rows="5"
                                                  placeholder="Add Description here">
                                            <c:if test="${not empty occupation.jobDescription}">
                                                ${occupation.getJobDescription().trim()}
                                            </c:if>
                                        </textarea>
                                    </label>
                                </dd>
                            </dl>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <button type="submit">Save</button>
        <button onclick="window.location.href='http://localhost:8080/resumes/resume'; return false;">Cancel</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>


