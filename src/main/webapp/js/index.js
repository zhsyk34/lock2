$(function () {
    $(".nav").on("click", "li", function (e) {
        e.preventDefault();
        $(".nav li").removeClass("active");

        $(this).addClass("active");
        var link = $(this).children("a").attr("href");
        $("iframe", "#main").attr("src", link);
    });
});