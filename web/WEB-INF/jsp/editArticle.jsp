<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        <c:if test="${article.id == null}">
            Create New Article
        </c:if>
        <c:if test="${article.id != null}">
            ${article.title} - ${user.nickname}
        </c:if>
    </title>
    <%@include file="./shared/_libraries.jsp" %>
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css"/>/simditor.css"/>
    <style type="text/css" rel="stylesheet">

        #imageWrapper > img {
            height: 200px;
            object-fit: cover;
        }

    </style>
    <script type="text/javascript" src="<c:url value="/assets/js"/>/module.js"></script>
    <script type="text/javascript" src="<c:url value="/assets/js"/>/hotkeys.js"></script>
    <script type="text/javascript" src="<c:url value="/assets/js"/>/simditor.js"></script>
    <script type="text/javascript">
        window.onload = function () {
            //init multimedia editor
            let editor = new Simditor({
                textarea: $('#content'),
                //optional options
                toolbar: [
                    'bold',
                    'italic',
                    'underline',
                    'strikethrough',
                    'fontScale',
                    'color',
                    'ol',
                    'ul',
                    'blockquote',
                    'code',
                    'table',
                    'link',
                    'hr',
                    'indent',
                    'outdent',
                    'alignment',
                ],
                i18n:'en-US',
            });


            //change image when input(file) value changes
            const coverImg = document.querySelector("img#coverImg");
            const coverInput = document.querySelector("input#coverInput");
            const newCoverBtn = document.querySelector("button#newCover");
            const deleteCoverBtn = document.querySelector("button#deleteCover");
            const articleForm = document.querySelector("form#articleForm");

            coverInput.onchange = function () {
                const newCover = coverInput.files[0];
                const reader = new FileReader();

                reader.onload = function (e) {
                    console.log(e.target.result);
                    coverImg.src = e.target.result;
                };
                reader.readAsDataURL(newCover);
            };

            newCoverBtn.onclick = function () {
                coverInput.click();
            };

            deleteCoverBtn.onclick = function () {
                coverImg.src = "<c:url value="/images/cover/default/1.jpg"/>";
                let newCoverInput = coverInput.cloneNode(true);
                articleForm.removeChild(coverInput);
                articleForm.appendChild(newCoverInput);

            }
        }
    </script>
</head>
<body>
<%@include file="shared/_navbar.jsp" %>
<div id="wrapper" class="container-lg" style="max-width: 680px">
    <div id="imageWrapper" class="card bg-dark text-white border-0 rounded-0 mt-2">
        <img id="coverImg" class="card-img border-0" src="<c:url value="/images/cover/${article.cover}"/>">
        <div class="card-img-overlay">
            <div class="btn-group" role="group" aria-label="Basic example">
                <button id="newCover" type="button" class="btn btn-dark">
                    <svg class="bi bi-image-fill" width="1.2em" height="1.2em" viewBox="0 0 16 16" fill="white"
                         xmlns="http://www.w3.org/2000/svg">
                        <path fill-rule="evenodd"
                              d="M.002 3a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v10a2 2 0 0 1-2 2h-12a2 2 0 0 1-2-2V3zm1 9l2.646-2.354a.5.5 0 0 1 .63-.062l2.66 1.773 3.71-3.71a.5.5 0 0 1 .577-.094L15.002 9.5V13a1 1 0 0 1-1 1h-12a1 1 0 0 1-1-1v-1zm5-6.5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0z"></path>
                    </svg>
                </button>
                <button id="deleteCover" type="button" class="btn btn-dark">
                    <svg class="bi bi-trash-fill" width="1.2em" height="1.2em" viewBox="0 0 16 16" fill="white"
                         xmlns="http://www.w3.org/2000/svg">
                        <path fill-rule="evenodd"
                              d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5a.5.5 0 0 0-1 0v7a.5.5 0 0 0 1 0v-7z"/>
                    </svg>
                </button>
            </div>
        </div>
    </div>

    <form action="./postArticle" method="post" enctype="multipart/form-data" class="form" id="articleForm">
        <input type="file" id="coverInput" name="cover" style="display: none">
        <input type="hidden" name="id" value="${article.id}">
        <input type="hidden" name="userName" value="${article.user.userName}">
        <input type="hidden" name="originalCover" value="${article.cover}">
        <div class="form-group my-2">
            <label for="title" class="text-muted" required>Title:</label>
            <input type="text" id="title" name="title" value="${article.title}" placeholder="title"
                   class="form-control" required>
        </div>
        <div class="form-group my-2">
            <label for="tags" class="text-muted">Tags:</label>
            <input type="text" id="tags" name="tags" value="${String.join(", ", article.tags)}"
                   placeholder="tags, separated by ','" class="form-control" maxlength="128">
        </div>
        <div class="form-group my-2">
            <label for="content" class="text-muted">Content</label>
            <textarea id="content" name="content">${article.content}</textarea>
        </div>
    </form>


    <form action="./deleteArticle" method="post" class="form align-content-center">
        <button type="submit" form="articleForm" class="btn btn-primary btn-sm mx-2 px-4">Post</button>
        <c:if test="${article.id != null}">
            <input type="hidden" name="id" value="${article.id}">
            <button type="submit" class="btn btn-danger btn-sm mx-2 px-4">Delete</button>
        </c:if>
    </form>
</div>
</body>
</html>
