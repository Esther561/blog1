function enableCommentLike(id) {
    $(`#likeCommentButton_${id}`).click(function() {
        $(`#unlikeCommentButton_${id}`).prop('disabled', true);
        $(`#likeCommentButton_${id}`).prop('disabled', true);
        $.ajax({
            type: "POST",
            url: "./likeComment",
            data: $(`#likeCommentForm_${id}`).serialize(),
            success: function() {
                $(`#unlikeCommentButton_${id}`).prop('disabled', false);
                $(`#likeCommentButton_${id}`).prop('disabled', false);
                $(`#likeCommentForm_${id}`).toggleClass("d-none");
                $(`#unlikeCommentForm_${id}`).toggleClass("d-none");
                $(`#commentLikes_${id}`).text(+$(`#commentLikes_${id}`).text() + 1);
            }
        });
        return false;
    });
}

function enableCommentUnlike(id) {
    $(`#unlikeCommentButton_${id}`).click(function() {
        $(`#unlikeCommentButton_${id}`).prop('disabled', true);
        $(`#likeCommentButton_${id}`).prop('disabled', true);
        $.ajax({
            type: "POST",
            url: "./unlikeComment",
            data: $(`#unlikeCommentForm_${id}`).serialize(),
            success: function() {
                $(`#unlikeCommentButton_${id}`).prop('disabled', false);
                $(`#likeCommentButton_${id}`).prop('disabled', false);
                $(`#likeCommentForm_${id}`).toggleClass("d-none");
                $(`#unlikeCommentForm_${id}`).toggleClass("d-none");
                $(`#commentLikes_${id}`).text(+$(`#commentLikes_${id}`).text() - 1);
            }
        });
        return false;
    });
}

function checkCommentLike(user, comment) {
    $.getJSON(`./checkLikeCommentStatus?user=${user}&&comment=${comment}`, (res) => {
        if (res.status === "success") {
            $(`#unlikeCommentForm_${comment}`).toggleClass("d-none");
        } else {
            $(`#likeCommentForm_${comment}`).toggleClass("d-none");
        }
    })
}