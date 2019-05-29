$(document).ready(function () {
    getVIP();
    getCoupon();
});

var isBuyState = true;
var vipCardId;
var selectedValue=0;

function getVIP() {
    getRequest(
        '/vip/' + sessionStorage.getItem('id') + '/get',
        function (res) {
            if (res.success) {
                // 是会员
                $("#member-card").css("visibility", "visible");
                $("#member-card").css("display", "");
                $("#nonmember-card").css("display", "none");

                vipCardId = res.content.id;
                $("#member-id").text(res.content.id);
                $("#member-balance").text("¥" + res.content.balance.toFixed(2));
                $("#member-joinDate").text(res.content.joinDate.substring(0, 10));
            } else {
                // 非会员
                $("#member-card").css("display", "none");
                $("#nonmember-card").css("display", "");
            }
        },
        function (error) {
            alert(error);
        });

    getRequest(
        '/vip/getVIPInfo',
        function (res) {
            if (res.success) {
                $("#member-buy-price").text(res.content.price);
                $("#member-buy-description").text("充值优惠：" + res.content.description + "。永久有效");
                $("#member-description").text(res.content.description);
            } else {
                alert(res.content);
            }

        },
        function (error) {
            alert(error);
        });
}

function buyClick() {
    clearForm();
    $('#buyModal').modal('show');
    $("#userMember-amount-group").css("display", "none");
    isBuyState = true;
}

function chargeClick() {
    clearForm();
    $('#buyModal').modal('show');
    $("#userMember-amount-group").css("display", "");
    isBuyState = false;
}
getChargeRecordsClick=function() {
    clearForm();
   getRequest(
        '/vip/charge/records?userId='+16,
        function (res) {
            var temp=0;//给id使用
            var data=res.content||[];
            var $content_container_tbody = $("#tbody");
            $content_container_tbody.empty();
            var recordDomStr = "<tr>"+
                               "<td>"+"充值时间"+"</td>"+
                               "<td></td>" +
                               "</tr>"+"<div>";
            data.forEach(function (vipCardCharge) {

               recordDomStr +=
                    "<tr>" +
                    "<td>" + timetrans(vipCardCharge.time)+ "</td>" +
                    "<td>"+"<span id=\'r\' class=\"caret\" value=temp></span>"+"</td>"+
                    "</tr>"+
                    "<tr  id=\'c\'+temp style=\'display: none\'>"+
                   "<td>"+
                    "充值金额："+vipCardCharge.amount+
                   "</td>"+
                    "</tr>";
               temp=temp+1;
            });
            recordDomStr+="</div>";
            $content_container_tbody.append(recordDomStr);
            $('#checkRecord').modal("show")
        },
        function (error) {
            alert(error);
        });

};
$(document).on('click', '#r', function () {
    var temp=$(this).value;
    var tr1=document.getElementById("c"+temp);
    if(tr1.style.display=='none') {
        temp = '';
    }
    else{
        temp = 'none';
    }
    $(this).parents("tr").next().style.display=temp;

});
//时间转化
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

function clearForm() {
    $('#tbody1').empty();
    $('#userMember-form input').val("");
    $('#userMember-form .form-group').removeClass("has-error");
    $('#userMember-form p').css("visibility", "hidden");
}

function confirmCommit() {
    if (validateForm()) {
        if ($('#userMember-cardNum').val() === "123123123" && $('#userMember-cardPwd').val() === "123123") {
            if (isBuyState) {
                postRequest(
                    '/vip/add?userId=' + sessionStorage.getItem('id'),
                    null,
                    function (res) {
                        $('#buyModal').modal('hide');
                        alert("购买会员卡成功");
                        getVIP();
                    },
                    function (error) {
                        alert(error);
                    });
            } else {
                postRequest(
                    '/vip/charge',
                    {vipId: vipCardId, amount: parseInt($('#userMember-amount').val())},
                    function (res) {
                        $('#buyModal').modal('hide');
                        alert("充值成功");
                        getVIP();
                    },
                    function (error) {
                        alert(error);
                    });
            }
        } else {
            alert("银行卡号或密码错误");
        }
    }
}

function validateForm() {
    var isValidate = true;
    if (!$('#userMember-cardNum').val()) {
        isValidate = false;
        $('#userMember-cardNum').parent('.form-group').addClass('has-error');
        $('#userMember-cardNum-error').css("visibility", "visible");
    }
    if (!$('#userMember-cardPwd').val()) {
        isValidate = false;
        $('#userMember-cardPwd').parent('.form-group').addClass('has-error');
        $('#userMember-cardPwd-error').css("visibility", "visible");
    }
    if (!isBuyState && (!$('#userMember-amount').val() || parseInt($('#userMember-amount').val()) <= 0)) {
        isValidate = false;
        $('#userMember-amount').parent('.form-group').addClass('has-error');
        $('#userMember-amount-error').css("visibility", "visible");
    }
    return isValidate;
}

function getCoupon() {
    getRequest(
        '/coupon/' + sessionStorage.getItem('id') + '/get',
        function (res) {
            if (res.success) {
                var couponList = res.content;
                var couponListContent = '';
                for (let coupon of couponList) {
                    couponListContent += '<div class="col-md-6 coupon-wrapper"><div class="coupon"><div class="content">' +
                        '<div class="col-md-8 left">' +
                        '<div class="name">' +
                        coupon.name +
                        '</div>' +
                        '<div class="description">' +
                        coupon.description +
                        '</div>' +
                        '<div class="price">' +
                        '满' + coupon.targetAmount + '减' + coupon.discountAmount +
                        '</div>' +
                        '</div>' +
                        '<div class="col-md-4 right">' +
                        '<div>有效日期：</div>' +
                        '<div>' + formatDate(coupon.startTime) + ' ~ ' + formatDate(coupon.endTime) + '</div>' +
                        '</div></div></div></div>'
                }
                $('#coupon-list').html(couponListContent);

            }
        },
        function (error) {
            alert(error);
        });
}

function formatDate(date) {
    return date.substring(5, 10).replace("-", ".");
}