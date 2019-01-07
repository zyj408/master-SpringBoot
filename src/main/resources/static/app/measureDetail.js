
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
                console.log(JSON.stringify(procedure));
            }
            else {
                showError("获取测量过程失败，请检查网络");
            }
        }
    });
});

function setChart(procedure) {

    var respCount = 0;
    var serverTotal = -1;
    while(respCount < serverTotal) {
        $.ajax({
            type: "POST",
            url: resultUrl,
            dataType: "json",
            headers: {
                'Content-Type': 'application/json'
            },
            data: JSON.stringify({
                procedure: procedure,
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
    }
    




    var doughnutData = {
        labels: ["App","Software","Laptop" ],
        datasets: [{
            data: [300,50,100],
            backgroundColor: ["#a3e1d4","#dedede","#b5b8cf"]
        }]
    } ;


    var doughnutOptions = {
        responsive: true
    };


    var ctx4 = document.getElementById("doughnutChart").getContext("2d");
    new Chart(ctx4, {type: 'doughnut', data: doughnutData, options:doughnutOptions});
}