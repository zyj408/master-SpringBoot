
$(document).ready(function () {
    var loc = location.href;
    var procedure = getUrlParam("procedure");
    if (!procedure) {
        return;
    }

    $(".procedureName").text(procedure);
});