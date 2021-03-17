<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign up</title>
    <%@include file="shared/_libraries.jsp" %>
    <script src="./assets/js/validateUserName.js"></script>
</head>
<body>
    <div class="container-lg">
        <h2 class="title">Sign Up</h2>
        <div id="contentBox" class="container-sm my-5 card py-5" style="max-width: 400px">
        <form action="./signUp" method="post" class="m-auto">
            <div class="form-group was-validated">
                <input class="form-control" type="text" name="userName" id="userName" placeholder="username" required maxlength="16" minlength="1">
                <div class="invalid-feedback">* Please input your username</div>
                <div id="takenName" class="d-none text-danger validation-info">* This username is already taken</div>
                <div id="invalidUserName" class="d-none text-danger validation-info">* The username may only contain digits and letters</div>
            </div>
            <div class="form-group was-validated">
                <input class="form-control" type="password" name="password" id="password" placeholder="password" required maxlength="16" minlength="8">
                <div class="invalid-feedback">* Your password must be 8 - 16 digits</div>
            </div>
            <div class="form-group was-validated">
                <input class="form-control" type="password" name="password" id="repeatPassword" placeholder="repeat password"
                       required>
                <div class="invalid-feedback">* Please repeat your password</div>
                <div id="notMatch" class="d-none text-danger validation-info">* The repeat password does not match</div>
            </div>
            <button type="submit" class="btn btn-primary btn-block" id="signUp">Sign Up</button>
            <hr/>
            <a class="btn btn-block btn-info" href="./signInPage">Go to sign in</a>
            <a class="btn btn-block btn-info" href="./indexPage">Go to landing page</a>
        </form>
    </div>
</div>
<script>
    document.getElementById("userName").addEventListener("input", async () => {
        const userName = document.getElementById("userName").value;
        if (!/^[a-zA-Z0-9]+$/.test(userName) && userName.length > 0) {
            document.getElementById("invalidUserName").classList.remove("d-none");
            document.getElementById("signUp").disabled = true;
        } else {
            document.getElementById("invalidUserName").classList.add("d-none");
            document.getElementById("signUp").disabled = false;
        }
        const result = await validateUserName(userName, null);
        if (!result) {
            document.getElementById("takenName").classList.remove("d-none");
            document.getElementById("signUp").disabled = true;
        } else {
            document.getElementById("takenName").classList.add("d-none");
            document.getElementById("signUp").disabled = false;
        }
    });
    const repeatPasswordValidator = () => {
        const password = document.getElementById("password").value;
        const repeatPassword = document.getElementById("repeatPassword").value;
        if (password !== repeatPassword) {
            document.getElementById("notMatch").classList.remove("d-none");
            document.getElementById("signUp").disabled = true;
        } else {
            document.getElementById("notMatch").classList.add("d-none");
            document.getElementById("signUp").disabled = false;
        }
    };
    document.getElementById("repeatPassword").addEventListener("input", repeatPasswordValidator);
    document.getElementById("password").addEventListener("input", repeatPasswordValidator);
</script>
</body>
</html>
