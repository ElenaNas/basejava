<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="webapp.model.ContactType" %>
<%@ page import="java.util.List" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Curriculum Vitae list</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Name</th>
            <th>Email</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="webapp.model.Resume"/>
           <tr>
                <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td><%=ContactType.EMAIL.toHtml(resume.getContact(ContactType.EMAIL))%></td>
                <td><a href="resume?uuid=${resume.uuid}&action=delete">Delete</a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit">Edit</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
<%--
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<div class="scrollable-panel">
    <div class="table-wrapper">
        <div class="add-resume">
            <a class="no-underline-anchor" href="resume?action=add&"></a>
            <a class="text-anchor" href="resume?action=add&">
                <p class="add-resume-title">Добавить резюме</p>
            </a>
        </div>
        <div class="resumes-list">
            <table border="1" cellpadding="8" cellspacing="0">
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th></th>
                        <th></th>
                    </tr>
                    <c:forEach items="${resumes}" var="resume">
                        <jsp:useBean id="resume" type="webapp.model.Resume"/>
                        <tr>
                        <tr>
                            <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                            <td><%=ContactType.EMAIL.toHtml(resume.getContact(ContactType.EMAIL))%></td>
                            <td><a href="resume?uuid=${resume.uuid}&action=delete">Delete</a></td>
                            <td><a href="resume?uuid=${resume.uuid}&action=edit">Edit</a></td>
                        </tr>
                    </c:forEach>
                </table>

                <tr class="t-body">
                    <td class="name-column">
                        <a href="resume?uuid=${resume.uuid}$action=view">${resume.fullName}</a></td>
                    <td class="info-column">
                        <%=ContactType.EMAIL.toHtml(resume.getContact(ContactType.EMAIL))%>
                    </td>
                    <td class="img-column">
                        <a class="no-underline-anchor" href="resume?uuid=&action=edit"></a>
                    </td>
                    <td class="img-column">

                    </td>
                </tr>


                <tr class="t-body">
                    <td class="name-column">
                        <a class="contact-link"
                           href="resume?uuid=22222222-2222-2222-2222-222222222222&action=view&theme=light">Евгений Боев</a>
                    </td>
                    <td class="info-column">
                        <a class="contact-link" href='mailto:yevhenboiev@gmail.com'>yevhenboiev@gmail.com</a>
                    </td>
                    <td class="img-column">
                        <a class="no-underline-anchor" href="resume?uuid=22222222-2222-2222-2222-222222222222&action=edit&theme=light">
                            <img src="img/light/edit.svg" alt="">
                        </a>
                    </td>
                    <td class="img-column">

                    </td>
                </tr>

            </table>
        </div>
    </div>
</div>

<div class="footer">
    <a class="footer-text-anchor" href="http://javaops.ru/reg/basejava">
        <div>Проект: разработка web-приложения «База данных резюме»</div>
    </a>
</div>

</body>
</html>
--%>

