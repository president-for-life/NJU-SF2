$(document).ready(function () {
    firstRequest();
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
                        "<td>"+"消费金额&nbsp;:"+"&nbsp;"+consumption.amount.toFixed(2)+"</td>"+
                        "<td >"+
                        "<input role=\"checkbox\" type=\"checkbox\" class=\"coupon-cbox\" name='colModel' id=\"consumption-check\" style=\" width:20px\">"+
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
    function firstRequest(){
        getRequest(
            '/statistics/consumption?amount='+0,
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
                        "<input role=\"checkbox\" type=\"checkbox\" class=\"coupon-cbox\" name='colModel' id=\"consumption-check\" style=\" width:20px\">"+
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
    }
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
                    "<td >"+"</td>"+
                    "</tr>"+"<div>";
                data.forEach(function (coupon) {

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
                    "</div>";

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
        return Y + M + D ;
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
                validCoupons.push(coupons[i].innerHTML);
            }
        }
        validCoupons=validCoupons.toString();
        validConsumptions=validConsumptions.toString();
        postRequest(
            '/coupon/issue?couponIdList='+validCoupons+"&userIdList="+validConsumptions,
            null,
            function (res) {
                $('#coupon-list').modal("hide");
            },
            function (error) {
            alert(error);
        });

    };



    couponsCbox=function(a){

        //遍历所有复选框，设置选中状态。
            if(a.checked)//判断全选按钮的状态是不是选中的
            {
                $("input[name='colModel']").prop("checked",true);//如果是选中的，就让所有的状态为选中。
            }
            else
            {
                $("input[name='colModel']").prop("checked",false) //如果不是选中的，就移除所有的状态是checked的选项。
                console.log("sss");//如果不是选中的，就移除所有的状态是checked的选项。
            }
        }


});