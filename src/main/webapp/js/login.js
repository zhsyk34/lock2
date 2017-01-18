$(function () {

    $(document).on("keydown", function (e) {
        if (e.keyCode === 13) {
            login();
        }
    });
    $("#loginBtn").on("click", function () {
        login();
    });

    var logging = false;

    function login() {
        if (logging) {
            return;
        }
        $.ajax({
            url: "user/login",
            type: "post",
            dataType: "json",
            timeout: 100000,
            data: {
                name: $.trim($("#name").val()),
                password: $.trim($("#password").val())
            },
            success: function (data) {
                console.log(data);
                if (data === true) {
                    window.location = $.basePath + "index";
                    return;
                }
                alert("登录失败");
            },
            error: function () {
                alert("服务器连接超时,请稍后重试...");
            },
            complete: function () {
                logging = false;
            }
        });
    }

});