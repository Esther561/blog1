<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="card border-0 mt-2">
    <img src="./images/cover/${article.cover}" class="card-img-top article-cover">
    <div class="card-body mx-lg-4">
        <h2 class="font-weight-bolder text-left article-title">
            ${article.title}
                    <span class="article-likes">
                         &nbsp;·&nbsp;
                    </span>
                    <span id="articleLikes_${article.id}" class="article-likes">
                            ${article.likes}
                    </span>
                    <span class="article-likes">
                         &nbsp;like${article.likes == 1 ? "" : "s"}
                    </span>
                <c:if test="${isUserLoggedIn}">
                    <form id="likeArticleForm_${article.id}" action="./likeArticle" method="post" class="d-none">
                        <input type="hidden" name="user" value="${user.id}">
                        <input type="hidden" name="article" value="${article.id}">
                        <button id="likeArticleButton_${article.id}" type="submit" class="btn btn-link btn-like">
                            <i class="fa fa-heart" aria-hidden="true" data-toggle="tooltip" title="Like this article"></i>
                        </button>
                    </form>
                    <form id="unlikeArticleForm_${article.id}" action="./unlikeArticle" method="post" class="d-none">
                        <input type="hidden" name="user" value="${user.id}">
                        <input type="hidden" name="article" value="${article.id}">
                        <button id="unlikeArticleButton_${article.id}" type="submit" class="btn btn-link btn-unlike">
                            <i class="fa fa-heart" aria-hidden="true" data-toggle="tooltip" title="Like this article"></i>
                        </button>
                    </form>
                    <script>
                        enableArticleLike(${article.id});
                        enableArticleUnlike(${article.id});
                        checkArticleLike(${user.id}, ${article.id});
                    </script>
                </c:if>
        </h2>

        <p class="my-1 summary-info">
            <c:set var="userSummary" value="${article.user}"/>
            <%@include file="shared/_userSummary.jsp" %>
            <span class="time">&nbsp;${article.time}&nbsp;</span>
        </p>
        <p class="my-2">
            <c:forEach var="tag" items="${article.tags}">
                <a href="./articlesByTagPage?tag=${tag}" class="badge badge-secondary px-1">#${tag}</a>
            </c:forEach>
        </p>
        <c:if test="${isUserLoggedIn && article.user.userName.equals(user.userName)}">
            <a href="./editArticlePage?id=${article.id}" class="btn btn-sm btn-info">edit</a>
            <form action="./deleteArticle?id=${article.id}" method="post" class="d-inline">
                <button type="submit" class="btn btn-sm btn-danger">Delete</button>
            </form>
        </c:if>
        <p class="text-justify article-content">${article.content}</p>
    </div>
</div>