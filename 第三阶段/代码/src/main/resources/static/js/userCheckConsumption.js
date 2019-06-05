$(document).ready(function () {
    clearForm();
    getRequest(
        '/ticket/get/' + sessionStorage.getItem('id'),
        function (res) {
            var data=res.content||[];
            console.log(res);
            var $content_container_tbody = $("#tbody");
            $content_container_tbody.empty();
            var recordDomStr = "<tr>"+
                "<td>"+"购买时间"+"</td>"+
                "<td></td>" +
                "</tr>"+"<div>";
            data.forEach(function (ticket) {
                if(ticket.state == "支付已完成" || ticket.state == "已出票") {
                    recordDomStr +=
                        "<tr bgcolor=>" +
                        "<td>" + timetrans(ticket.time)+ "</td>" +
                        "<td></td>"+
                        "<td>"+"<span id=\'r\' class=\"caret\" ></span>"+"</td>"+
                        "</tr>"+
                        "<tr style=\"display: none\" >"+
                        "<td>"+"票价："+ticket.schedule.fare.toFixed(2)+"</td>"+
                        "</tr>"+
                        "<tr style=\"display: none\" >"+
                        "<td>"+"实际消费金额:"+ticket.actualPayment.toFixed(2)+"</td>"+
                        "</tr>";
                }
            });
            recordDomStr+="</div>";
            $content_container_tbody.append(recordDomStr);
        },
        function (error) {
            alert(error);
        });
});
$(document).on('click', '#r', function () {
    if($(this).get(0).parentNode.parentNode. nextSibling.style.display==="none") {
        $(this).get(0).parentNode.parentNode. nextSibling.style.display="";
    }
    else {
        $(this).get(0).parentNode.parentNode. nextSibling.style.display="none";
    }
    if($(this).get(0).parentNode.parentNode. nextSibling. nextSibling.style.display==="none") {
        $(this).get(0).parentNode.parentNode. nextSibling. nextSibling.style.display="";
    }
    else {
        $(this).get(0).parentNode.parentNode. nextSibling. nextSibling.style.display="none";
    }

});
function clearForm() {
    $('#tbody').empty();
}
function timetrans(date) {
    var date = new Date(date );//如果date为13位不需要乘1000
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    var D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate()) + ' ';
    var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
    var m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
    var s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
    return Y + M + D + h + m + s;
}