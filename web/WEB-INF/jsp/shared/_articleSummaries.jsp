<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="list-unstyled">
    <c:forEach items="${articleSummaries}" var="article">
        <div class="card my-2" id="articleSummary_${article.id}">
            <div class="row no-gutters">
                <img src="./images/cover/${article.cover}" alt="cover"
                         class="card-img col-sm-3 cover-summary" >
                <div class="col-sm-9">
                    <div class="card-body">
                        <h5 class="text-dark">
                            <a href="articlePage?id=${article.id}" class="summary-title">${article.title}</a>
                            <span class="summary-likes">&nbsp;·&nbsp;${article.likes} like${article.likes == 1 ? "" : "s"}</span>
                        </h5>
                        <p class="my-1 summary-info">
                            <c:set var="userSummary" value="${article.user}"/>
                            <%@include file="_userSummary.jsp" %>
                            <span class="time">&nbsp;${article.time}&nbsp;</span>
                        </p>
                        <p class="my-2">
                            <c:forEach var="tag" items="${article.tags}">
                                <a href="./articlesByTagPage?tag=${tag}" class="badge badge-pill badge-light">#${tag}</a>
                            </c:forEach>
                        </p>
                        <c:if test="${isUserLoggedIn && article.user.userName.equals(user.userName)}">
                            <a href="./editArticlePage?id=${article.id}" class="btn btn-sm btn-info">Edit</a>
                            <form action="./deleteArticle" method="post" class="d-inline" id="articleDeleteForm_${article.id}">
                                <input type="hidden" name="id" value="${article.id}">
                                <button type="submit" class="btn btn-sm btn-danger" id="articleDeleteButton_${article.id}">Delete</button>
                            </form>
                            <script>enableArticleDeletion(${article.id});</script>
                        </c:if>
                        <!--
                        <p class="text-justify text-secondary font-weight-light content-summary">${article.contentSummary}</p>
                        -->
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
