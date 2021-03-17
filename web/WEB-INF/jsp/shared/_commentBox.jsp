<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<button class="btn btn-info btn-sm ml-3" data-toggle="collapse" data-target="#comment_box_${parent.id}">
    Comment
</button>
<div id="comment_box_${parent.id}" class="media collapse">
    <c:if test="${isUserLoggedIn}">
        <img class="mr-2" src="./images/avatar/${user.avatar}" style="width: 20px; border-radius: 50%;">
        <div class="media-body">
            <form action="./postComment" method="post" class="form">
                <input type="hidden" name="article" value="${article.id}">
                <input type="hidden" name="parent" value="${parent.id}">
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
