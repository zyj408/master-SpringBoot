
function updateMeasure(callback) {
    $.ajax({
        type: "POST",
        url: measureUrl,
        dataType: "json",
        headers: {
            'Content-Type': 'application/json'
        },
        data: JSON.stringify({
            page: {
                page: 1,
                rows: 2000
            }
        }),
        success: function (resp) {
            if (resp.code && resp.code == 200) {
                callback(resp.rows);
            }
            else {
                showError("获取测量过程失败，请检查网络");
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
            nationalQualifiedRate: e.record > 0 ? (e.nationalQualified / e.record).toPercent() : "100%",
            factoryQualifiedRate:  e.record > 0 ? (e.factoryQualified / e.record).toPercent() : "100%",
            status: e.status
        });
    });

    return result;
}

function exportMeasure(procedureName) {
    var url = exportUrl + "/" + procedureName;
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
        data: JSON.stringify({ name: procedureName }),
        success: function (resp) {
            if (resp.code && resp.code == 200) {
                callback();
            }
            else {
                showError("获取测量过程失败，请检查网络");
            }
        }
    });

}

$(document).ready(function () {

    updateMeasure(function (data) {
        if (data) {
            $('#measureTemplate').tmpl(transform(data)).appendTo('#measureContent');
            $('.footable').footable();
        }
    });

    $(document).on('click', '.detail-btn', function (e) {
        name = $(e.currentTarget).data('name');
        var url = "flow-measure-detail.html?procedure=" + name;
        window.location = url;
    });
    $(document).on('click', '.export-btn', function (e) {
        var name = $(e.currentTarget).data('name');
        exportMeasure(name);
    });

});