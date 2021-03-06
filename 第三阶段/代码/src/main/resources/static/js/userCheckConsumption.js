$(document).ready(function () {
    clearForm();
    getRequest(
        '/ticket/get/' + sessionStorage.getItem('id'),
        function (res) {
            var data=res.content||[];
            console.log(res);
            var $content_container_tbody = $("#tbody");
            $content_container_tbody.empty();
            if(data.length!==0) {
                var recordDomStr = "";
            }
            else{
                var recordDomStr = "<div>" +
                    "无消费记录" +
                    "</div>" ;
            }
            data.forEach(function (ticket) {
                if(ticket.state == "支付已完成" || ticket.state =="已出票") {
                    recordDomStr +=
                        "<div  class='item' id=\'r\' data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\"> " +
                        "<ul style=\'font-size: small; width: 100%\'>"+"消费时间"+"</ul>"+
                        "<ul style=\'width: 100%;font-size: larger\'>" + timetrans(ticket.time)+ "</ul>" +
                        "<ul  style=\"display: none;width: 100%;font-size: larger\">"+"票价："+ticket.schedule.fare.toFixed(2)+"</ul>"+
                        "<ul style=\"display: none;width: 100%;font-size: larger\" >"+"消费金额："+ticket.actualPayment.toFixed(2)+"</ul>"+
                        "</div>";
                }
            });
            if(data.length!==0) {
                recordDomStr += "</div>";

            }
            $content_container_tbody.append(recordDomStr);
        },
        function (error) {
            alert(error);
        });
});
$(document).on('click', '#r', function () {
    if($(this).get(0).firstChild.nextSibling.nextSibling.nextSibling.style.display==="none") {
        $(this).get(0).firstChild.nextSibling.nextSibling.nextSibling.style.display="";
    }
    else {
        $(this).get(0).firstChild.nextSibling.nextSibling.nextSibling.style.display="none";
    }
    if($(this).get(0).firstChild.nextSibling. nextSibling.nextSibling. nextSibling.style.display==="none") {
        $(this).get(0).firstChild.nextSibling. nextSibling. nextSibling.nextSibling.style.display="";
    }
    else {
        $(this).get(0).firstChild.nextSibling.nextSibling.nextSibling. nextSibling.style.display="none";
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