<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav id="navbar" class="navbar navbar-expand-sm sticky-top navbar-dark justify-content-between">
    <a class="navbar-brand" href="./indexPage">
        · KeyPoint<span class="logo-space-left">&nbsp;</span>-><span class="logo-space-right">&nbsp;</span>
    </a>
    <!-- show when viewpoint is small, to toggle list items -->
    <button class="navbar-toggler" type="button" data-toggle="collapse"
            data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
            aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse justify-content-between" id="navbarSupportedContent">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="./indexPage">Articles</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="./authorsPage">Authors</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="./teamPage">Team KEY</a>
            </li>
            <li class="nav-item">
                <a class="nav-link"
                   href="https://gitlab.com/auckland-ict-grad-school/final-project-groups-2020-s1/group-1/pgcit-project-fulltime-starter"
                   target="_blank">Source code</a>
            </li>
        </ul>
        <ul class="navbar-nav float-right">
            <c:if test="${!isUserLoggedIn}">
                <li class="nav-item">
                    <a class="nav-link" href="./signInPage">Sign In</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="./signUpPage">Sign Up</a>
                </li>
            </c:if>
            <c:if test="${isUserLoggedIn}">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" data-toggle="dropdown"
                       href="#" role="button" id="navbarDropdown" aria-haspopup="true" aria-expanded="false">
                        <span data-toggle="tooltip"
                              title="${user.userName}: ${user.signature}">
                                ${user.nickname}
                        </span>
                        <img src="./images/avatar/${user.avatar}" class="avatar-sm">
                    </a>
                    <div id="navList" class="dropdown-menu dropdown-menu-right">
                        <a class="dropdown-item" href="./editArticlePage?userName=${user.userName}">New Article</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="./articlesPage?userName=${user.userName}">My Blog</a>
                        <a class="dropdown-item" href="./articlesByUserLikePage?userName=${user.userName}">Liked</a>
                        <a class="dropdown-item" href="./followersPage?follower=${user.id}">Followed</a>
                        <a class="dropdown-item" href="./followeesPage?followee=${user.id}">My Followers</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="./editProfilePage?userName=${user.userName}">Edit Profile</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="./signOut">Sign Out</a>
                    </div>
                </li>
            </c:if>
        </ul>
    </div>
</nav>
