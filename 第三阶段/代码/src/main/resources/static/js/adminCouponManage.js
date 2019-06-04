$(document).ready(function () {
    function getMoney() {
        return $('#money-input').val();
    }
    $('#consumption-btn').click(function getMember() {
        var amount=getMoney();
        clearForm();
        getRequest(
            '/statistics/consumption?amount='+amount,
            function (res) {
                var data=res.content||[];

                var $content_container_tbody = $("#tbody1");
                $content_container_tbody.empty();
                var consumptionDomStr = "<tr>"+
                    "<td>"+"用户id"+"</td>"+
                    "<td>"+"消费金额"+"</td>" +
                    "<td></td>" +
                    "</tr>"+"<div>";
                data.forEach(function (consumption) {

                    consumptionDomStr+=
                        "<tr>" +
                        "<td>" + consumption.userId+ "</td>" +
                        "<td>"+consumption.amount+"</td>"+
                        "<td style=\"text-align:center;width:35px;\">"+
                        "<input role=\"checkbox\" type=\"checkbox\" class=\"cbox checkbox\">"+
                        "</td>"+
                        "</tr>"

                });
                consumptionDomStr+="</div>";
                console.log(consumptionDomStr)
                $content_container_tbody.append(consumptionDomStr);
            },
            function (error) {
                alert(error);
            });
    });
    function clearForm() {
        $('#tbody1').empty();
        $('#tbody2').empty();
    }
})