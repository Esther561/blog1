<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Authors</title>
    <%@include file="shared/_libraries.jsp"%>
</head>
<body>
<%@include file="shared/_navbar.jsp" %>
<div class="container-lg">
    <h4 class="title">Authors</h4>
    <c:if test="${authors.size() == 0}">
        <h4 class="sub-title">No authors yet...</h4>
    </c:if>
    <c:if test="${authors.size() != 0}">
        <c:forEach var="author" items="${authors}">
            <%@include file="shared/_userProfile.jsp"%>
        </c:forEach>
    </c:if>
</div>
</body>
</html>
