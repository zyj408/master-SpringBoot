var procedureName = null;

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

                $(".procedureName").text(procedure.name);
                $("#recordNum").text(procedure.record);
                $("#standardNum").text(procedure.standard);
                $("#ceateTime").text(new Date(procedure.startTime).Format("yyyy-MM-dd hh:mm:ss"));
                $("#lastTime").text(new Date(procedure.lastTime).Format("yyyy-MM-dd hh:mm:ss"));

                if (procedure.status == "running") {
                    $("#procedureStatus").text("进行中");
                }
                else {
                    $("#procedureStatus").text("已完成");
                    $("#lastTime").text(new Date(procedure.endTime).Format("yyyy-MM-dd hh:mm:ss"));
                }

                setChart(procedure.name);

            }
            else {
                showError("获取测量过程失败，请检查网络");
            }
        }
    });
});

function exportMeasure() {
    var url = exportUrl + "/" + procedureName;
    var fileName = "testAjaxDownload.txt";
    var form = $("<form></form>").attr("action", url).attr("method", "get");
    form.appendTo('body').submit().remove();
}

function setChart(procedure) {

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
                procedure: procedure,
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


    var statistic_1 = calcStepStatistic(result, "q1");
    var statistic_2 = calcStepStatistic(result, "q2");
    var statistic_3 = calcStepStatistic(result, "q3");
    var statistic_total = arrayAdd(statistic_1, arrayAdd(statistic_2, statistic_3));

    var timeSortedResult = result.sort(function (a, b) {
        return a.time - b.time;
    });
    
    console.log(JSON.stringify(timeSortedResult));


    var ctx4_1 = document.getElementById("chartQ1").getContext("2d");
    new Chart(ctx4_1, { type: 'doughnut', data: getDoughnutStruct(statistic_1), options: { responsive: true } });
    var ctx4_2 = document.getElementById("chartQ2").getContext("2d");
    new Chart(ctx4_2, { type: 'doughnut', data: getDoughnutStruct(statistic_2), options: { responsive: true } });
    var ctx4_3 = document.getElementById("chartQ3").getContext("2d");
    new Chart(ctx4_3, { type: 'doughnut', data: getDoughnutStruct(statistic_3), options: { responsive: true } });

    var ctx4_total = document.getElementById("chartTotal").getContext("2d");
    new Chart(ctx4_total, { type: 'doughnut', data: getDoughnutStruct(statistic_total), options: { responsive: true } });


    var lineData = {
        labels: ["January", "February", "March", "April", "May", "June", "July"],
        datasets: [

            {
                label: "Data 1",
                backgroundColor: 'rgba(26,179,148,0.5)',
                borderColor: "rgba(26,179,148,0.7)",
                pointBackgroundColor: "rgba(26,179,148,1)",
                pointBorderColor: "#fff",
                data: [28, 48, 40, 19, 86, 27, 90]
            }, {
                label: "Data 2",
                backgroundColor: 'rgba(220, 220, 220, 0.5)',
                pointBorderColor: "#fff",
                data: [65, 59, 80, 81, 56, 55, 40]
            }
        ]
    };

    var lineOptions = {
        responsive: true
    };


    var ctx = document.getElementById("timeChart").getContext("2d");
    new Chart(ctx, { type: 'line', data: lineData, options: lineOptions });
}


var segment = [0, 0.05, 0.10, 0.15, 1.0];
var segmentColor = ["#1bb394", "#3ac7aa", "#a3e1d4", "#cce7e2", "#dedede"];
//#f8ae5e
function getSegmentTitle() {
    var title = [];
    for (var i = 0; i < segment.length - 1; i++) {
        title[i] = segment[i] + " ~ " + segment[i + 1];
    }
    title[segment.length - 1] = segment[segment.length - 1] + " 以上";
    return title;
}

function calcStepStatistic(result, step) {

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

function getDoughnutStruct(data) {
    var doughnutData = {};
    doughnutData.labels = getSegmentTitle();
    doughnutData.datasets = [{
        data: data,
        backgroundColor: segmentColor
    }];
    return doughnutData;
}
