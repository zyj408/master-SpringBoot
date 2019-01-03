
$(document).ready(function () {
    var loc = location.href;
    var procedure = getUrlParam("procedure");
    if (!procedure) {
        return;
    }
    $(".procedureName").text(procedure);

    $.ajax({
        type: "POST",
        url: measureUrl,
        dataType: "json",
        headers: {
            'Content-Type': 'application/json'
        },
        data: JSON.stringify({
            name: procedure,
            page: {
                page: 1,
                rows: 2000
            }
        }),
        success: function (resp) {
            if (resp.code && resp.code == 200) {

            }
            else {
                showError("获取测量过程失败，请检查网络");
            }
        }
    });
});