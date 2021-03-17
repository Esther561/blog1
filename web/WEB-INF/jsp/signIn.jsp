<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign in</title>
    <%@include file="shared/_libraries.jsp"%>
</head>
<body>
<div class="container-lg">
    <h2 class="title">Sign In</h2>
    <div id="contentBox" class="container-sm my-5 card py-5" style="max-width: 400px">
        <form action="./signIn" method="post" class="m-auto was-validated">
            <div class="form-group">
                <input class="form-control" type="text" name="userName" id="userName" placeholder="username" required>
                <div class="invalid-feedback">* Please input your username</div>
            </div>
            <div class="form-group">
                <input class="form-control" type="password" name="password" id="password" placeholder="password" required>
                <div class="invalid-feedback">* Please input your password</div>
                <c:if test="${hasLogInFailed}" >
                    <label class="text-danger validation-info">* Login failed. Wrong username or password.</label>
                </c:if>
            </div>
            <button type="submit" class="btn btn-primary btn-block">Sign In</button>
            <hr />
            <a class="btn btn-block btn-info" href="./signUpPage">Go to sign up</a>
            <a class="btn btn-block btn-info" href="./indexPage">Go to landing page</a>
        </form>
    </div>
</div>

</body>
</html>
