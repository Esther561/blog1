$(document).ready(() => {
    const comments = $('#collapseCommentsButton');
    comments.click(() => {
        comments.text(comments.text() === "Hide comments" ? "Show comments" : "Hide comments");
    });
});