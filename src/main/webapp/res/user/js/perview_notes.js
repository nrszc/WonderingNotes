$(document).ready(function() {
    $(".img-banner").css("background","url("+sessionStorage.getItem("title_img")+")");
    $(".title strong").text(sessionStorage.getItem("title"));
    $(".article-show").html(sessionStorage.getItem("article"));
})