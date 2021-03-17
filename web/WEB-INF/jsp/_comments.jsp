<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<button data-toggle="collapse" data-target="#comments" class="btn btn-primary my-5" id="collapseCommentsButton">Hide comments</button>
<div class="collapse show" id="comments">
    <c:if test="${article.comments.size() == 0}">
        <h4 class="sub-title">No comments yet...</h4>
    </c:if>
    <c:if test="${article.comments.size() != 0}">
        <c:forEach var="comment" items="${article.comments}">
            <div class="comment" id="comment_${comment.id}"
                 style="margin-left: ${comment.level >= 2 ? 6 : comment.level * 3}rem">
                <div class="summary-info">
                    <c:set var="userSummary" value="${comment.user}"/>
                    <%@include file="shared/_userSummary.jsp" %>
                    <c:if test="${comment.parentUser != null}">
                        <span class="separator">&nbsp;&nbsp;replied&nbsp;&nbsp; </span>
                        <c:set var="userSummary" value="${comment.parentUser}"/>
                        <%@include file="shared/_userSummary.jsp" %>
                    </c:if>
                    <span class="time">&nbsp;${comment.time}&nbsp;</span>
                </div>
                <p class="my-0">${comment.content}</p>
                <span id="commentLikes_${comment.id}" class="summary-likes">${comment.likes}</span>
                <span class="summary-likes">&nbsp;like${comment.likes == 1 ? "" : "s"}</span>
                <c:if test="${isUserLoggedIn}">
                    <form id="likeCommentForm_${comment.id}" action="./likeComment" method="post" class="d-none">
                        <input type="hidden" name="user" value="${user.id}">
                        <input type="hidden" name="comment" value="${comment.id}">
                        <button id="likeCommentButton_${comment.id}" type="submit" class="btn btn-link btn-like">
                            <i class="fa fa-heart" aria-hidden="true" data-toggle="tooltip" title="Like this comment"></i>
                        </button>
                    </form>
                    <form id="unlikeCommentForm_${comment.id}" action="./unlikeComment" method="post" class="d-none">
                        <input type="hidden" name="user" value="${user.id}">
                        <input type="hidden" name="comment" value="${comment.id}">
                        <button id="unlikeCommentButton_${comment.id}" type="submit" class="btn btn-link btn-unlike">
                            <i class="fa fa-heart" aria-hidden="true" data-toggle="tooltip" title="Like this comment"></i>
                        </button>
                    </form>
                    <script>
                        enableCommentLike(${comment.id});
                        enableCommentUnlike(${comment.id});
                        checkCommentLike(${user.id}, ${comment.id});
                    </script>
                </c:if>
                <c:if test="${isUserLoggedIn && (user.userName.equals(article.user.userName) || user.userName.equals(comment.user.userName))}">
                    <form action="./deleteComment" method="post" class="d-inline">
                        <input type="hidden" name="id" value="${comment.id}">
                        <input type="hidden" name="articleId" value="${article.id}">
                        <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                    </form>
                </c:if>
                <c:set var="parent" value="${comment}"/>
                <%@include file="shared/_commentBox.jsp" %>
            </div>
        </c:forEach>
    </c:if>
</div>
