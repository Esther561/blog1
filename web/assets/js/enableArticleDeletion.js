function enableArticleDeletion(id) {
    $(`#articleDeleteButton_${id}`).click(function() {
        $(`#articleDeleteButton_${id}`).prop('disabled', true);
        $.ajax({
            type: "POST",
            url: "./deleteArticle",
            data: $(`#articleDeleteForm_${id}`).serialize(),
            success: function() {
                $(`#articleSummary_${id}`).fadeOut();
            }
        });
        return false;
    });
}