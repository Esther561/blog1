<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="my-3 border-0 p-5 profile container-fluid">
    <div class="row">
        <div class="col-sm-5 justify-content-center">
            <a href="./articlesPage?userName=${author.userName}" data-toggle="tooltip" title="${author.signature}" class="profile-userName">
                <p class="my-2">${author.nickname}</p>
                <img src="./images/avatar/${author.avatar}" class="profile-avatar d-block m-auto">
            </a>
        </div>
        <div class="profile-info col-sm-7">
            <table class="profile-table table table-hover">
                <tr>
                    <th>Signature:</th>
                    <td>
                        ${author.signature}
                    </td>
                </tr>
                <tr>
                    <th>Followers:</th>
                    <td>
                        <a href="./followeesPage?followee=${author.id}" id="followers_${author.id}" class="follower-number">${author.followers}</a>
                    </td>
                </tr>
                <tr>
                    <th>Date of birth:</th>
                    <td>
                        ${author.dateOfBirth == null ? "secret" : author.dateOfBirth.toString()}
                    </td>
                </tr>
                <tr>
                    <th>Email:</th>
                    <td>
                        ${author.email == null || author.email.isEmpty() ? "secret" : author.email}
                    </td>
                </tr>
                <tr>
                    <th>Description:</th>
                    <td>
                        ${author.description == null || author.description.isEmpty() ? "secret" : author.description}
                    </td>
                </tr>
            </table>
            <c:if test="${isUserLoggedIn && author.userName.equals(user.userName)}">
                <a href="./editProfilePage?userName=${author.userName}" role="button"
                   class="btn btn-sm btn-info">Edit</a>
            </c:if>
            <c:if test="${isUserLoggedIn && !author.userName.equals(user.userName)}">
                <form id="followForm_${author.id}" action="./follow" method="post" class="d-none">
                    <input type="hidden" name="followee" value="${author.id}">
                    <input type="hidden" name="follower" value="${user.id}">
                    <button id="followButton_${author.id}" type="submit" class="btn btn-sm btn-outline-info">Follow</button>
                </form>
                <form id="unfollowForm_${author.id}" action="./unfollow" method="post" class="d-none">
                    <input type="hidden" name="followee" value="${author.id}">
                    <input type="hidden" name="follower" value="${user.id}">
                    <button id="unfollowButton_${author.id}" type="submit" class="btn btn-sm btn-info">Unfollow</button>
                </form>
                <script>
                    enableFollow(${author.id});
                    enableUnfollow(${author.id});
                    checkFollow(${author.id}, ${user.id});
                </script>
            </c:if>
        </div>
    </div>

</div>
