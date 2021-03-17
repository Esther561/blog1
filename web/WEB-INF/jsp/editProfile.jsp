<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Profile</title>
    <%@include file="shared/_libraries.jsp" %>
    <style type="text/css">
        button.action {
            border: none;
            background: transparent;
            outline: none;
            color: darkorange;
        }

        button.action:hover {
            color: black;
        }

        img.img-thumbnail {
            background-color: darkorange;
        }

    </style>
    <script src="./assets/js/validateUserName.js"></script>
    <script>
        window.addEventListener("load", function () {
            //following code is to detect whether a username has already been taken.
            const originalUserName = document.getElementById("userName").value;
            document.getElementById("userName").addEventListener("input", async () => {
                const userName = document.getElementById("userName").value;
                if (!/^[a-zA-Z0-9]+$/.test(userName) && userName.length > 0) {
                    document.getElementById("invalidUserName").classList.remove("d-none");
                    document.getElementById("save").disabled = true;
                } else {
                    document.getElementById("invalidUserName").classList.add("d-none");
                    document.getElementById("save").disabled = false;
                }
                const result = await validateUserName(userName, originalUserName);
                if (!result) {
                    document.getElementById("takenName").classList.remove("d-none");
                    document.getElementById("save").disabled = true;
                } else {
                    document.getElementById("takenName").classList.add("d-none");
                    document.getElementById("save").disabled = false;
                }
            });

            //following is to deal with the avatar adjustment
            const btnDecide = document.getElementById("btnDecide");
            const btnUpload = document.getElementById("btnUpload");
            const btnRemove = document.getElementById("btnRemove");
            let inputAvatar = document.getElementById("avatarInput");
            const imgDisplay = document.getElementById("avatarDisplay");
            const imgOptions = document.querySelectorAll("img.avatar-option");
            const inputDefaultAvatar = document.getElementById("defaultAvatar");
            const inputOriginalAvatar = document.getElementById("originalAvatar");
            const rightBlock = document.getElementById("rightBlock");

            const inputChangeEvent = () => {
                const newAvatar = inputAvatar.files[0];
                const reader = new FileReader();
                reader.onload = (e) => {
                    imgDisplay.src = e.target.result;
                };
                reader.readAsDataURL(newAvatar);
            };

            imgOptions.forEach(option => {
                option.onclick = (e) => {
                    e.target.nextElementSibling.checked = true;
                    imgOptions.forEach(o => {
                        o.classList.remove("img-thumbnail")
                    });
                    option.classList.add("img-thumbnail");
                }
            });

            btnDecide.onclick = () => {
                $('#staticBackdrop').modal('hide');
                const chosenValue = document.querySelector('input[name="avatarOptionsRdBtn"]:checked').value;
                imgDisplay.src = "./images/avatar/" + chosenValue;
                inputDefaultAvatar.value = chosenValue;
            };

            btnRemove.onclick = () => {
                imgDisplay.src = "./images/avatar/" + inputOriginalAvatar.value;
                let newInputAvatar = inputAvatar.cloneNode(true);
                rightBlock.removeChild(inputAvatar);
                rightBlock.appendChild(newInputAvatar);
                inputAvatar = newInputAvatar;
                inputAvatar.onchange = inputChangeEvent;
            };

            btnUpload.onclick = () => {
                inputAvatar.click();
            };

            inputAvatar.onchange = inputChangeEvent;

        });
    </script>
</head>
<body>
<%@include file="shared/_navbar.jsp" %>
<div id="wrapper" class="container-md" style="max-width: 720px">
    <!-- title for this page -->
    <h4 class="title">${fn:toUpperCase(empty user.nickname ? user.userName : user.nickname)}'s Profile</h4>
    <div class="container w-100 card">
        <!-- whole form -->
        <form id="profileForm" class="row justify-content-between mx-0 mt-md-5" action="./saveProfile" method="post"
              enctype="multipart/form-data">
            <!-- right part in the form-->
            <div id="rightBlock" class="float-md-right col-md-4 mb-3 mt-5 mt-md-0">
                <input id="defaultAvatar" type="hidden" name="defaultAvatar" value="0">
                <input id="originalAvatar" type="hidden" name="originalAvatar" value="${user.avatar}">
                <input id="avatarInput" type="file" name="avatar" accept="image/jpeg, image/png, image/jpg" style="display: none">
                <p class="mt-md-3 text-center">
                    <img id="avatarDisplay" class="rounded-circle inline profile-avatar" src="./images/avatar/${user.avatar}">
                </p>
                <!-- default avatar button -->
                <div class="row justify-content-center ">
                    <!-- Button trigger modal -->
                    <button type="button" class="btn action mx-2 px-1" data-toggle="modal"
                            data-target="#staticBackdrop">
                        <svg class="bi bi-collection-fill" width="1.4em" height="1.4em" viewBox="0 0 16 16"
                             fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                            <rect width="16" height="10" rx="1.5" transform="matrix(1 0 0 -1 0 14.5)"></rect>
                            <path fill-rule="evenodd"
                                  d="M2 3a.5.5 0 0 0 .5.5h11a.5.5 0 0 0 0-1h-11A.5.5 0 0 0 2 3zm2-2a.5.5 0 0 0 .5.5h7a.5.5 0 0 0 0-1h-7A.5.5 0 0 0 4 1z"></path>
                        </svg>
                    </button>
                    <!-- Modal -->
                    <div class="modal fade" id="staticBackdrop" data-backdrop="static" data-keyboard="false"
                         tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="staticBackdropLabel">Which avatar do you like?</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body border">
                                    <c:forEach var="avatar" items="${defaultAvatars}">
                                        <img src="./images/avatar/${avatar}"
                                             class="avatar-option m-2 rounded-sm"
                                             style="width: 100px; height: 100px; object-fit: cover">
                                        <input type="radio" name="avatarOptionsRdBtn"
                                               value="${avatar}" style="display: none">
                                    </c:forEach>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary px-3" data-dismiss="modal">Cancel
                                    </button>
                                    <button type="button" class="btn btn-primary px-3" id="btnDecide">Okay</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <button type="button" class="action mx-2 px-1" id="btnUpload">
                        <svg class="bi bi-arrow-up-circle" width="1.4em" height="1.4em" viewBox="0 0 16 16"
                             fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                            <path fill-rule="evenodd"
                                  d="M8 15A7 7 0 1 0 8 1a7 7 0 0 0 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"></path>
                            <path fill-rule="evenodd"
                                  d="M4.646 8.354a.5.5 0 0 0 .708 0L8 5.707l2.646 2.647a.5.5 0 0 0 .708-.708l-3-3a.5.5 0 0 0-.708 0l-3 3a.5.5 0 0 0 0 .708z"></path>
                            <path fill-rule="evenodd"
                                  d="M8 11.5a.5.5 0 0 0 .5-.5V6a.5.5 0 0 0-1 0v5a.5.5 0 0 0 .5.5z"></path>
                        </svg>
                    </button>
                    <button type="button" class="action mx-2 px-1" id="btnRemove">
                        <svg class="bi bi-trash" width="1.4em" height="1.4em" viewBox="0 0 16 16" fill="currentColor"
                             xmlns="http://www.w3.org/2000/svg">
                            <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"></path>
                            <path fill-rule="evenodd"
                                  d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4L4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"></path>
                        </svg>
                    </button>
                </div>
            </div>

            <!-- left part in the form -->
            <div id="leftBlock" class=" col-md-8 ">
                <input id="id" type="hidden" name="id" value="${user.id}">
                <input id="originalUserName" type="hidden" name="originalUserName" value="${user.userName}">
                <div class="form-group">
                    <input id="userName" type="text" name="userName" value="${user.userName}" placeholder="user name"
                           class="form-control" required maxlength="16">
                    <div id="takenName" class="d-none text-danger">* This username is already taken</div>
                    <div id="invalidUserName" class="d-none text-danger validation-info">* The username may only contain digits and letters</div>
                </div>
                <div class="form-group">
                    <input id="nickname" type="text" name="nickname" value="${user.nickname}" placeholder="nickname"
                           class="form-control" maxlength="64">
                </div>
                <div class="form-group">
                    <input id="signature" type="text" name="signature" value="${user.signature}" placeholder="signature"
                           class="form-control" maxlength="64">
                </div>
                <div class="row">
                    <div class="col">
                        <div class="form-group">
                            <input id="firstName" type="text" name="firstName" value="${user.firstName}"
                                   placeholder="first name"
                                   class="form-control" maxlength="64">
                        </div>
                    </div>
                    <div class="col">
                        <div class="form-group">
                            <input id="lastName" type="text" name="lastName" value="${user.lastName}"
                                   placeholder="last name"
                                   class="form-control" maxlength="64">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <input id="dateOfBirth" type="date" name="dateOfBirth" value="${user.dateOfBirth.toString()}"
                           placeholder="date of birth" class="form-control">
                </div>
                <div class="form-group">
                    <input id="email" type="email" name="email" value="${user.email}" placeholder="email"
                           class="form-control" maxlength="64">
                </div>
                <div class="form-group">
                <textarea id="description" name="description" placeholder="description"
                          class="form-control" maxlength="1024">${user.description}</textarea>
                </div>

            </div>

        </form>


        <form id="deleteForm" action="./deleteAccount" method="post" class="pl-3 mx-0 d-flex align-content-center">
            <button id="save" type="submit" form="profileForm" class="btn btn-primary mr-2 px-3 btn-sm" id="save">Save</button>
            <input type="hidden" name="userName" value="${user.userName}">
            <button type="button" class="btn btn-danger mr-2 px-3 btn-sm" data-toggle="modal"
                    data-target="#modalDeleteAccount">Delete Account
            </button>
            <!-- Modal -->
            <div class="modal fade" id="modalDeleteAccount" data-backdrop="static" data-keyboard="false" tabindex="-1"
                 role="dialog" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Warning!</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body border">
                            <p>Are you sure to delete this user <i>${user.userName}</i>?</p>
                            <p>This action CANNOT be undone.</p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary px-3" data-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-primary px-3">Delete anyway</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
