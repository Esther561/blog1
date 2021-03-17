<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${author.nickname}'s Blog</title>
    <%@include file="shared/_libraries.jsp"%>
</head>
<body>
<%@include file="shared/_navbar.jsp"%>
<div class="container-lg">
    <h4 class="title">About the Author</h4>
    <%@include file="shared/_userProfile.jsp"%>
    <h4 class="title">Articles by ${author.nickname}</h4>
    <c:if test="${articleSummaries.size() == 0}">
        <h4 class="sub-title">No articles yet...</h4>
    </c:if>
    <c:if test="${articlesSummaries.size() != 0}">
        <%@include file="shared/_articleSummaries.jsp"%>
    </c:if>
</div>

</body>
</html>
