$(document).ready(function () {
    getVIP();
    getCoupon();
});

var isBuyState = true;
var vipCardId;

function getVIP() {
    // 加载用户会员信息
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
                $("#member-type").text(res.content.strategy.description);
                $("#member-description").text("满" + res.content.strategy.targetAmount + "送" + res.content.strategy.discountAmount);
            } else {
                // 非会员
                $("#member-card").css("display", "none");
                $("#nonmember-card").css("display", "");
            }
        },
        function (error) {
            alert(error);
        });

    // 加载所有会员卡策略
    getRequest(
        '/vip/strategy/get/all',
        function (res) {
            renderStrategiesForBuy(res.content);
            renderStrategiesForSwitch(res.content);
        },
        function (error) {
            alert(error);
        }
    );

    function renderStrategiesForBuy(strategies) {
        $('#buy-strategy-list').empty();
        strategies = strategies || [];
        let strategiesDomStr = "";
        for (let strategy of strategies) {
            strategiesDomStr +=
                "<div class='strategy-container'>" +
                "    <div class='strategy-item' " + "id='buy-strategy-" + strategy.id + "' data-strategy='" + JSON.stringify(strategy) + "'>" +
                "        <span class='title'>NJU-<span class='SE title'>SE</span>-VIP</span>" +
                "        <span class='big'>__</span>" +
                "        <span class='big'>价格：<span class='amount'>"+strategy.price+"</span></span>" +
                "        <span class='big'>满<span class='amount'>"+strategy.targetAmount+"</span>减<span class='discount amount'>" + strategy.discountAmount+"</span></span>" +
                "        <span class='gray-text' style='text-align: right'>"+strategy.description+"</span>" +
                "    </div>" +
                "</div>";
        }
        $('#buy-strategy-list').html(strategiesDomStr);
    }

    function renderStrategiesForSwitch(strategies) {
        $('#switch-strategy-list').empty();
        strategies = strategies || [];
        let strategiesDomStr = "";
        for (let strategy of strategies) {
            strategiesDomStr +=
                "<div class='strategy-container'>" +
                "    <div class='strategy-item' " + "id='switch-strategy-" + strategy.id + "' data-strategy='" + JSON.stringify(strategy) + "'>" +
                "        <span class='title'>NJU-<span class='SE title'>SE</span>-VIP</span>" +
                "        <span class='big'>__</span>" +
                "        <span class='big'>价格：<span class='amount'>"+strategy.price+"</span></span>" +
                "        <span class='big'>满<span class='amount'>"+strategy.targetAmount+"</span>减<span class='discount amount'>" + strategy.discountAmount+"</span></span>" +
                "        <span class='gray-text' style='text-align: right'>"+strategy.description+"</span>" +
                "    </div>" +
                "</div>";
        }
        $('#switch-strategy-list').html(strategiesDomStr);
    }
}

// 点击选择某种会员卡
$(document).on('click','#buy-strategy-list .strategy-container .strategy-item', function (e) {
    let strategy = JSON.parse(e.currentTarget.dataset.strategy);
    let $item = $('#buy-strategy-' + strategy.id);

    if($('#buyModal')[0].dataset.strategyId == -1
        || $('#buyModal')[0].dataset.strategyId != strategy.id) { // 未选该卡
        $item.parent().siblings().children().css('box-shadow', '');
        $item.css('box-shadow', '10px 10px 5px #888888');
        $('#buyModal')[0].dataset.strategyId = strategy.id;
    } else { // 已选该卡
        $item.css('box-shadow', '');
        $('#buyModal')[0].dataset.strategyId = -1;
    }
});

$(document).on('click','#switch-strategy-list .strategy-container .strategy-item', function (e) {
    let strategy = JSON.parse(e.currentTarget.dataset.strategy);
    let $item = $('#switch-strategy-' + strategy.id);

    if($('#buyModal')[0].dataset.strategyId == -1
        || $('#buyModal')[0].dataset.strategyId != strategy.id) { // 未选该卡
        $item.parent().siblings().children().css('box-shadow', '');
        $item.css('box-shadow', '10px 10px 5px #888888');
        $('#buyModal')[0].dataset.strategyId = strategy.id;
    } else { // 已选该卡
        $item.css('box-shadow', '');
        $('#buyModal')[0].dataset.strategyId = -1;
    }
});

// 点击购买会员卡
function buyClick() {
    clearForm();
    $('#buyModal').modal('show');
    $('#switch-strategy-list').css("display", "none"); // 不显示会员卡列表
    $("#userMember-amount-group").css("display", "none"); // 不显示充值金额输入框
    isBuyState = true;
}

// 点击充值会员卡
function chargeClick() {
    clearForm();
    $('#buyModal').modal('show');
    $('#switch-strategy-list').css("display", "none"); // 不显示会员卡列表
    $("#userMember-amount-group").css("display", "");
    isBuyState = false;
}

getChargeRecordsClick = function() {
    clearForm();
   getRequest(
        '/vip/charge/records?vipCardId='+vipCardId,
        function (res) {
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
                   "<td></td>"+
                    "<td>"+"<span id=\'r\' class=\"caret\" ></span>"+"</td>"+
                    "</tr>"+
                    "<tr style=\"display: none; able-layout:fixed\"   bgcolor=\"#e4e7ea\" width='100%'>"+
                    "<td>"+"充值金额："+vipCardCharge.payment.toFixed(2)+"</td>"+
                   "<td></td>"+
                   "<td></td>"+
                    "</tr>"+
                     "<tr style=\"display: none; able-layout:fixed\"  bgcolor=\"#e4e7ea\" width='100%'>"+
                    "<td>"+"冲入金额："+vipCardCharge.amount.toFixed(2)+"</td>"+
                    "<td></td>"+
                   "<td></td>"+
                    "</tr>";
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

// 点击更换会员卡
function switchCardClick() {
    clearForm();
    $('#buyModal').modal('show');
    $('#switch-strategy-list').css("display", "");
    $("#userMember-amount-group").css("display", "none"); // 不显示充值金额输入框
    isBuyState = true;
}

// 时间转化
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
                let strategyId = $('#buyModal')[0].dataset.strategyId;
                console.log("buy:");
                console.log(strategyId);
                if(strategyId == -1) {
                    alert("请选择会员卡！");
                    return;
                }
                postRequest(
                    '/vip/add?userId=' + sessionStorage.getItem('id')
                    + '&strategyId=' + strategyId,
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
                    {vipCardId: vipCardId, payment: parseInt($('#userMember-amount').val())},
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