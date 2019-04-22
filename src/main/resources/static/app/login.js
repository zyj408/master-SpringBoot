


$(document).ready(function () {

$(document).on('click', '.login-btn', function (e) {
        var account = $("input#account").val();
        var password = $("input#password").val();
        if(!account || !password) {
            showError("账号或者密码为空...");
        }
        else {
            login(account, password, "index.html");
        }
       
    });
});

function login(account, password, redirect) {
    $.ajax({
        type: "POST",
        url: loginUrl,
        dataType: "json",
        headers: {
            'Content-Type': 'application/json'
        },
        data: JSON.stringify({
            account: account,
            password:password
        }),
        success: function (resp) {
            if (resp.code && resp.code == 200) {
                showSuccess("登陆成功!!!");
                window.location.href = redirect + "?account=" + account;
            }
            else {
                showError("登陆密码错误...");
            }
        }
    });

}