<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="webapp.model.ContactType" %>
<%@ page import="java.util.List" %>

<style>
    .footer_C {
        position: fixed;
        bottom: 1cm;
        left: 1cm;
        color: #fff;
    }

    section {
        max-width: 1000px;
        overflow-x: auto;
        margin: 20px auto;
        padding: 20px;
        background-color: #fff;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

    table {
        width: 100%;
        margin-top: 20px;
    }

    .add-cv-button {
        margin-bottom: 20px;
    }

</style>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="WEB-INF/jsp/css/style.css">
    <div class="header">
        <h1>Curriculum Vitae List</h1>
    </div>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/fragments/header.jsp"/>
<section>
    <a href="resume?action=add" class="add-cv-button"><img src="img/add_new_CV.png" class="action-icons">Add new CV</a>
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
                <td><%=ContactType.EMAIL.toHtml(resume.getContact(ContactType.EMAIL))%>
                </td>
                <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png"
                                                                            class="action-icons"></a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png" class="action-icons"></a>
                </td>
            </tr>
        </c:forEach>
    </table>
</section>
<div class="footer_C">
    <p>&copy; 2024 ElenaNasikovskaia</p>
</div>
<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
</body>
</html>
