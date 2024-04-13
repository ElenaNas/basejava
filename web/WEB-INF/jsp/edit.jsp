<%@ page import="webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="webapp.model.Resume" scope="request"/>
    <title>Curriculum Vitae ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Name:</dt>
            <dd><label>
                <input type="text" name="fullName" size=70 value="${resume.fullName}">
            </label></dd>
        </dl>
        <h3>Contact details:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><label>
                    <input type="text" name="${type.name()}" size=50 value="${resume.getContact(type)}">
                </label></dd>
            </dl>
        </c:forEach>
        <h3>Sections:</h3>
        <label>
            <input type="text" name="section" size=50 value="1">
        </label><br/>
        <label>
            <input type="text" name="section" size=50 value="2">
        </label><br/>
        <label>
            <input type="text" name="section" size=50 value="3">
        </label><br/>
        <hr>
        <button type="submit">Save</button>
        <button onclick="window.history.back()">Cancel</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
