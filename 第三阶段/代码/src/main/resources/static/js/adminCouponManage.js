$(document).ready(function () {;
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
                var consumptionDomStr =  "<tr>" +
                    "<td>" + "</td>" +
                    "<td>"+"</td>"+
                    "<td >"+ "</td>"+
                    "</tr>"+"<div>";
                data.forEach(function (consumption) {

                    consumptionDomStr+=
                        "<tr>" +
                        "<td class='user-id'>" + "用户id&nbsp;:"+"&nbsp;"+consumption.userId+ "</td>" +
                        "<td>"+"消费金额&nbsp;:"+"&nbsp;"+consumption.amount+"</td>"+
                        "<td >"+
                        "<input role=\"checkbox\" type=\"checkbox\" class=\"coupon-cbox\"  id=\"consumption-check\" style=\" width:20px\">"+
                        "</td>"+
                        "</tr>"

                });
                consumptionDomStr+="<tr>" +
                    "<td>" + "</td>" +
                    "<td>"+"</td>"+
                    "<td >"+"<input type=\"checkbox\"  role=\"checkbox\"  onclick=\"couponsCbox(this)\"  style=\"width:20px\">\n全选</br>"+"</td>"+
                    "</tr>"+ "<tr>" +
                    "<td>" + "</td>" +
                    "<td>"+"</td>"+
                    "<td >"+" <button type=\"button\" class=\"btn btn-primary\"  onclick=\"getCouponClick()\">赠送优惠券</button>"+ "</td>"+
                    "</tr>"+
                    "</div>";

                $content_container_tbody.append(consumptionDomStr);
            },
            function (error) {
                alert(error);
            });
    });
    getCouponClick =    function(){
        getRequest(
            '/coupon/get/all',
            function (res) {
                var data=res.content||[];

                var $content_container_tbody = $("#tbody2");
                $content_container_tbody.empty();
                var consumptionDomStr = "<tr>"+
                    "<td >"+"名称"+"</td>"+
                    "<td>"+"描述"+"</td>" +
                    "<td >"+"使用门槛"+"</td>"+
                    "<td>"+"优惠金额"+"</td>" +
                    "<td >"+"可用时间"+"</td>"+
                    "<td>"+"失效时间"+"</td>" +
                    "</tr>"+"<div>";
                data.forEach(function (coupon) {
                    console.log(coupon.id);
                    consumptionDomStr+=
                        "<tr>" +
                        "<td style=\"display: none\" class='coupon-id'>" + coupon.id+ "</td>" +
                        "<td style=\"width: 100px; overflow: auto\">" + coupon.name+ "</td>" +
                        "<td  style=\"width: 100px; overflow: auto\">"+coupon.description+"</td>"+
                        "<td  style=\"width: 50px; overflow: auto\">" + coupon.targetAmount+ "</td>" +
                        "<td  style=\"width: 50px; overflow: auto\">"+coupon.discountAmount+"</td>"+
                        "<td  style=\"width: 100px; overflow: auto\">" + timetrans(coupon.startTime)+ "</td>" +
                        "<td  style=\"width: 100px; overflow: auto\">"+timetrans(coupon.endTime)+"</td>"+
                        "<td   style=\"width: 30px; overflow: auto\">"+
                        "<input role=\"checkbox\" type=\"checkbox\" class=\"cbox\"  id=\"coupon-check\" style=\"width:20px\">"+
                        "</td>"+
                        "</tr>"

                });
                consumptionDomStr+="<tr>"+
                    "<td >"+"</td>"+
                    "<td>"+"</td>" +
                    "<td >"+"</td>"+
                    "<td>"+"</td>" +
                    "<td >"+"</td>"+
                    "<td>"+" <button type=\"button\" class=\"btn btn-primary\"  onclick=\"sendCouponClick()\">赠送</button>"+"</td>" +
                    "</tr>"+
                    "</div>"

                $content_container_tbody.append(consumptionDomStr);
                $('#coupon-list').modal("show")
            },
            function (error) {
                alert(error);
            });

    };
    function timetrans(date) {
        var date = new Date(date );//如果date为13位不需要乘1000
        var Y = date.getFullYear() + '-';
        var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
        var D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate()) + ' ';
        var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
        var m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
        var s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
        return Y + M + D + h + m + s;
    };


    sendCouponClick=function(){
        var  consumptionCheak =document.getElementsByClassName("coupon-cbox");
        var consumptions =document.getElementsByClassName("user-id");
        var  couponCheak =document.getElementsByClassName("cbox");
        var coupons =document.getElementsByClassName("coupon-id");
        var validConsumptions=new Array();
        var validCoupons=new Array();
        for(var i=0;i< consumptionCheak .length;i++)
        {
            if( consumptionCheak [i].checked)//判断全选按钮的状态是不是选中的
            {
                console.log(consumptions[i].innerHTML.substring(17,));
                validConsumptions.push(consumptions[i].innerHTML.substring(17,));

            }
        }
        for(var i=0;i< couponCheak .length;i++)
        {
            if(  couponCheak[i].checked)//判断全选按钮的状态是不是选中的
            {
                console.log(coupons.innerHTML);
                validCoupons.push(coupons.innerHTML);

            }
        }

    };



    couponsCbox=function(a){
        //找到下面所有的复选框
        var ck =document.getElementsByClassName("coupon-cbox");

        //遍历所有复选框，设置选中状态。
        for(var i=0;i<ck.length;i++)
        {
            if(a.checked)//判断全选按钮的状态是不是选中的
            {
                ck[i].setAttribute("checked","checked");//如果是选中的，就让所有的状态为选中。
            }
            else
            {
                ck[i].removeAttribute("checked");//如果不是选中的，就移除所有的状态是checked的选项。
            }
        }
    }

});