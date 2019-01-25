var procedureName = null;
var TIME_SEPARATE = 10;


var SEGMENT_UNIVERSAL = [0, 0.015, 0.02];
var SEGMENT_Q1 = [0, 0.015, 0.03];
var SEGMENT_COLOR = ["#a3e1d4", "#dedede", "#b5b8cf"];

$(document).ready(function () {
    procedureName = getUrlParam("procedure");
    if (!procedureName) {
        return;
    }

    $.ajax({
        type: "POST",
        url: measureUrl,
        dataType: "json",
        headers: {
            'Content-Type': 'application/json'
        },
        data: JSON.stringify({
            name: procedureName,
            page: {
                page: 1,
                rows: 2000
            }
        }),
        success: function (resp) {
            if (resp.code && resp.code == 200) {
                var procedure = resp.rows[0];
                setProcedureInfo(procedure);
                
                var procedureDetails = getDetails(procedure.name);
                setChart(procedureDetails);

                $('#detailTemplate').tmpl(transform(procedureDetails)).appendTo('#detailContent');
                $('.footable').footable();2

            }
            else {
                showError("获取测量过程失败，请检查网络");
            }
        }
    });
});


function transform(details) {
    var result = [];
    details.forEach(e => {
        result.push({
            no: e.no,
            q3dev: e.q3.deviation.toPercent(),
            q2dev: e.q2.deviation.toPercent(),
            q1dev: e.q1.deviation.toPercent(),
            national: e.nationalQualified,
            factory: e.factoryQualified,
            time: new Date(e.time).Format("yyyy-MM-dd"),
            q3start: e.q3.start,
            q3end: e.q3.end,
            q2start: e.q2.start,
            q2end: e.q2.end,
            q1start: e.q1.start,
            q1end: e.q1.end,
        });
    });

    return result;
}

function setProcedureInfo(procedure) {
    $(".procedureName").text(procedure.name);
    $("#recordNum").text(procedure.record);
    $("#factoryStandardNum").text(procedure.factoryQualified);
    $("#nationalStandardNum").text(procedure.nationalQualified);
    $("#ceateTime").text(new Date(procedure.startTime).Format("yyyy-MM-dd hh:mm:ss"));
    $("#lastTime").text(new Date(procedure.lastTime).Format("yyyy-MM-dd hh:mm:ss"));

    if (procedure.status == "running") {
        $("#procedureStatus").text("进行中");
    }
    else {
        $("#procedureStatus").text("已完成");
        $("#lastTime").text(new Date(procedure.endTime).Format("yyyy-MM-dd hh:mm:ss"));
    }
}

function exportMeasure() {
    var url = exportUrl + "/" + procedureName;
    var fileName = "testAjaxDownload.txt";
    var form = $("<form></form>").attr("action", url).attr("method", "get");
    form.appendTo('body').submit().remove();
}

function getDetails(procedureName) {
    var result = [];
    var serverTotal = 0;
    do {
        $.ajax({
            type: "POST",
            url: resultUrl,
            dataType: "json",
            async: false,
            headers: {
                'Content-Type': 'application/json'
            },
            data: JSON.stringify({
                procedure: procedureName,
                page: {
                    page: 1,
                    rows: 50
                }
            }),
            success: function (resp) {
                if (resp.code && resp.code == 200) {
                    resp.rows.forEach(e => {
                        result.push(e);
                    });

                    serverTotal = resp.total;
                }
                else {
                    showError("获取记录程失败，请检查网络");
                }
            }
        });
    } while (result.length < serverTotal);

    return result;
}

function setChart(detail) {

    var statistic_1 = calcStepStatistic(detail, "q1", SEGMENT_UNIVERSAL);
    var statistic_2 = calcStepStatistic(detail, "q2", SEGMENT_UNIVERSAL);
    var statistic_3 = calcStepStatistic(detail, "q3", SEGMENT_UNIVERSAL);
    var statistic_total = arrayAdd(statistic_1, arrayAdd(statistic_2, statistic_3));

   
    var ctx4_2 = document.getElementById("chartQ2").getContext("2d");
    new Chart(ctx4_2, { type: 'doughnut', data: getDoughnutStruct(statistic_2, SEGMENT_UNIVERSAL), options: { responsive: true } });
    var ctx4_3 = document.getElementById("chartQ3").getContext("2d");
    new Chart(ctx4_3, { type: 'doughnut', data: getDoughnutStruct(statistic_3, SEGMENT_UNIVERSAL), options: { responsive: true } });

    var ctx4_total = document.getElementById("chartTotal").getContext("2d");
    new Chart(ctx4_total, { type: 'doughnut', data: getDoughnutStruct(statistic_total, SEGMENT_UNIVERSAL), options: { responsive: true } });


    statistic_1 = calcStepStatistic(detail, "q1", SEGMENT_Q1);
    var ctx4_1 = document.getElementById("chartQ1").getContext("2d");
    new Chart(ctx4_1, { type: 'doughnut', data: getDoughnutStruct(statistic_1, SEGMENT_Q1), options: { responsive: true } });
}

function getSegmentTitle(segment) {
    var title = [];
    for (var i = 0; i < segment.length - 1; i++) {
        title[i] = segment[i].toPercent() + " ~ " + segment[i + 1].toPercent();
    }
    title[segment.length - 1] = segment[segment.length - 1].toPercent() + " 以上";
    return title;
}

function calcStepStatistic(result, step, segment) {

    statistics = getDigitalArray(segment.length);
    result.forEach(e => {
        var deviation = Math.abs(e[step].deviation);
        for (var j = 0; j < segment.length - 1; j++) {
            if (segment[j] <= deviation && deviation < segment[j + 1]) {
                statistics[j] += 1;
                break;
            }
            if (deviation >= segment[segment.length - 1]) {
                statistics[segment.length - 1] += 1;
                break;
            }
        }
    });
    return statistics;
}

function getDoughnutStruct(data, segment) {
    var doughnutData = {};
    doughnutData.labels = getSegmentTitle(segment);
    doughnutData.datasets = [{
        data: data,
        backgroundColor: SEGMENT_COLOR
    }];
    return doughnutData;
}
