function enableArticleLike(id) {
    $(`#likeArticleButton_${id}`).click(function() {
        $(`#likeArticleButton_${id}`).prop('disabled', true);
        $(`#unlikeArticleButton_${id}`).prop('disabled', true);
        $.ajax({
            type: "POST",
            url: "./likeArticle",
            data: $(`#likeArticleForm_${id}`).serialize(),
            success: function() {
                $(`#likeArticleButton_${id}`).prop('disabled', false);
                $(`#unlikeArticleButton_${id}`).prop('disabled', false);
                $(`#likeArticleForm_${id}`).toggleClass("d-none");
                $(`#unlikeArticleForm_${id}`).toggleClass("d-none");
                $(`#articleLikes_${id}`).text(+$(`#articleLikes_${id}`).text() + 1);
            }
        });
        return false;
    });
}

function enableArticleUnlike(id) {
    $(`#unlikeArticleButton_${id}`).click(function() {
        $(`#likeArticleButton_${id}`).prop('disabled', true);
        $(`#unlikeArticleButton_${id}`).prop('disabled', true);
        $.ajax({
            type: "POST",
            url: "./unlikeArticle",
            data: $(`#unlikeArticleForm_${id}`).serialize(),
            success: function() {
                $(`#likeArticleButton_${id}`).prop('disabled', false);
                $(`#unlikeArticleButton_${id}`).prop('disabled', false);
                $(`#likeArticleForm_${id}`).toggleClass("d-none");
                $(`#unlikeArticleForm_${id}`).toggleClass("d-none");
                $(`#articleLikes_${id}`).text(+$(`#articleLikes_${id}`).text() - 1);
            }
        });
        return false;
    });
}

function checkArticleLike(user, article) {
    $.getJSON(`./checkLikeArticleStatus?user=${user}&&article=${article}`, (res) => {
        if (res.status === "success") {
            $(`#unlikeArticleForm_${article}`).toggleClass("d-none");
        } else {
            $(`#likeArticleForm_${article}`).toggleClass("d-none");
        }
    })
}


