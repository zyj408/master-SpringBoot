/**
 * 日期转字符串
 * @param fmt
 * @returns
 */
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}

function getDigitalArray(length) {
    var array = [];
    for (var i = 0; i < length; i++) {
        array[i] = 0;
    }

    return array;
}

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数   
    if (r != null) return unescape(r[2]);
    return null; //返回参数值

}

function showSuccess(info) {
    toastr.options = {
        "positionClass": "toast-top-right",
        "timeOut": 5000
    }
    toastr.success(info);
}

function showError(info) {
    toastr.options = {
        "positionClass": "toast-top-right",
        "timeOut": 5000
    }
    toastr.error(info);
}
