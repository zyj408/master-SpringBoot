
function login(userInfo, callback) {

    console.log("send to server :" + JSON.stringify(userInfo));
    $.ajax({
        type: 'POST',
        url: loginApi,
        data: userInfo,
        success: function (data) {
            console.log(JSON.stringify(data));
            callback(data);
        }
    });
};  