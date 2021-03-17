<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${article.user.nickname} - ${article.title}</title>
    <%@include file="shared/_libraries.jsp" %>
</head>
<body>
<%@include file="shared/_navbar.jsp" %>
<div class="container-lg">
    <%@include file="_articleDisplay.jsp" %>
    <div class="mx-lg-5 my-5">
        <div id="comment_box_${parent.id}" class="media">
            <c:if test="${isUserLoggedIn}">
                <img class="mr-2" src="./images/avatar/${user.avatar}" style="width: 20px; border-radius: 50%;">
                <div class="media-body">
                    <form action="./postComment" method="post" class="form">
                        <input type="hidden" name="article" value="${article.id}">
                        <input type="hidden" name="parent" value="">
                        <input type="hidden" name="userName" value="${user.userName}">
                        <textarea class="form-control" id="content" name="content" placeholder="Share your thoughts"></textarea>
                        <button class="btn btn-sm btn-info" type="submit">Post</button>
                    </form>
                </div>
            </c:if>
            <c:if test="${!isUserLoggedIn}">
                <p class="ml-4 text-black-50" style="font-size: small"><a href="./signInPage">sign in to comment on this article</a></p>
            </c:if>
        </div>

        <%@include file="_comments.jsp" %>
    </div>
</div>
</body>
</html>
