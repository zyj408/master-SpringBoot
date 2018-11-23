var IP = '47.106.130.222';
var measureUrl = 'http://' + IP + '/master/procedure/query';
var finishUrl = 'http://' + IP + '/master/procedure/finish';
var restartUrl = 'http://' + IP + '/master/procedure/restart';
var exportUrl = 'http://' + IP + '/master/measure/export';

function updateMeasure(request, callback) {
    $.ajax({
        type: "POST",
        url: measureUrl,
        dataType: "json",
        headers: {
            'Content-Type': 'application/json'
        },
        data: JSON.stringify(request),
        success: function (data) {
            if (data.code && data.code == 200) {
                callback(data.rows);
            }
            else {
                callback();
            }
        }
    });
}

function transform(measureData) {
    var result = [];
    measureData.forEach(e => {
        result.push({
            name: e.name,
            startTime: new Date(e.startTime).Format("yyyy-MM-dd hh:mm:ss"),
            record: e.record,
            standard: e.standard,
            standardRate: e.record > 0 ? e.standard / e.record : 1,
            status: e.status
        });
    });

    return result;
}

function exportMeasure(procedureName) {
    var url = exportUrl + "/" + procedureName;
    var fileName = "testAjaxDownload.txt";
    var form = $("<form></form>").attr("action", url).attr("method", "get");
    form.appendTo('body').submit().remove();
}

function enableMeasure(procedureName, status, callback) {
    var url = status == true ? restartUrl : finishUrl;
    $.ajax({
        type: "POST",
        url: url,
        dataType: "json",
        headers: {
            'Content-Type': 'application/json'
        },
        data: JSON.stringify({name : procedureName}),
        success: function (data) {
            if (data.code && data.code == 200) {
                callback();
            }
            else {
                callback("error");
            }
        }
    });

}

function detailMeasure(procedureName) {
    alert("detail" + procedureName);
}

$(document).ready(function () {
    // 查看详情弹框示例
    var name = '';

    $('#myModal').on('show.bs.modal', function(e) {
        // 这里通过请求获取详情并把详情填入到html中
        $('#myModalBody').html(name);
    });
    $(document).on('click', '.detail-btn', function(e) {
        name = $(e.currentTarget).data('name');
        $('#myModal').modal();
    })

});