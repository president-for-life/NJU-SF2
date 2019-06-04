$(document).ready(function () {
    getCoupon();
    function getMoney() {
        return $('#money-input').val();
    }
    $('#consumption-btn').click(function getMember() {
        var amount=getMoney();
        getRequest(
            '/statistics/consumption?amount='+amount,
            function (res) {
                var data=res.content||[];

                var $content_container_tbody = $("#tbody1");
                $content_container_tbody.empty();
                var consumptionDomStr = "<tr>"+
                    "<td >"+"用户id"+"</td>"+
                    "<td>"+"消费金额"+"</td>" +
                    "<td></td>" +
                    "</tr>"+"<div>";
                data.forEach(function (consumption) {

                    consumptionDomStr+=
                        "<tr>" +
                        "<td>" + consumption.userId+ "</td>" +
                        "<td>"+consumption.amount+"</td>"+
                        "<td >"+
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
    function getCoupon(){
        getRequest(
            '/coupon/get/all',
            function (res) {
                var data=res.content||[];

                var $content_container_tbody = $("#tbody2");
                $content_container_tbody.empty();
                var consumptionDomStr = "<tr>"+
                    "<td >"+"用户id"+"</td>"+
                    "<td>"+"消费金额"+"</td>" +
                    "<td></td>" +
                    "</tr>"+"<div>";
                data.forEach(function (coupon) {

                    consumptionDomStr+=
                        "<tr>" +
                        "<td>" + coupon.name+ "</td>" +
                        "<td>"+coupon.description+"</td>"+
                        "<td>" + coupon.targetAmount+ "</td>" +
                        "<td>"+coupon.discountAmount+"</td>"+
                        "<td>" + timetrans(coupon.startTime)+ "</td>" +
                        "<td>"+timetrans(coupon.endTime)+"</td>"+
                        "<td >"+
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

})