var IP = '47.106.130.222';
var measureUrl = 'http://' + IP + '/master/procedure/query';
var finishUrl = 'http://' + IP + '/master/procedure/finish';
var restartUrl = 'http://' + IP + '/master/procedure/restart';
var exportUrl = 'http://' + IP + '/master/measure/export';


$(document).ready(function () {
    var loc = location.href;
    var procedure = getUrlParam("procedure");
    if (!procedure) {
        return;
    }
    
    $(".procedureName").text(procedure);
});