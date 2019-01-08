
$(document).ready(function () {
    var loc = location.href;
    var procedure = getUrlParam("procedure");
    if (!procedure) {
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
            name: procedure,
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


    console.log(JSON.stringify(result));
    var totalData = calcTotalChartData(result);


    var ctx4 = document.getElementById("doughnutChart").getContext("2d");
    new Chart(ctx4, { type: 'doughnut', data: totalData, options: { responsive: true } });
}


var segment = [0, 0.05, 0.10, 0.15, 1.0];
var step = ["q1", "q2", "q3"];


function calcTotalChartData(result) {

    statistics = getDigitalArray(segment.length);
    result.forEach(e => {
        for (var i in step) {
            var deviation = Math.abs(e[step[i]].deviation);
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
        }
    });

    console.log(JSON.stringify(statistics));

    var doughnutData = {
        labels: ["0-", "Software", "Laptop"],
        datasets: [{
            data: [300, 50, 100],
            backgroundColor: ["#a3e1d4", "#dedede", "#b5b8cf"]
        }]
    };

    return doughnutData;
}


