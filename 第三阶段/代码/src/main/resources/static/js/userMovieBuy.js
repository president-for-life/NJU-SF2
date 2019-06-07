var selectedSeats = []; // 用户选择的座位
var scheduleId; // 当前的排片
var order = {
	ticketId: [], // 用户选择的电影票
	couponId: -1 // 用户选择使用的优惠券
};
var coupons = []; // 用户拥有且达到使用门槛的优惠券
var isVIP = false;
var useVIP = true;

$(document).ready(function () {
	if(window.location.href.indexOf("orderId") > -1) { // 继续支付订单
	    // TODO bug 超时提示
        let orderId = parseInt(window.location.href.split('?')[1].split('&')[2].split('=')[1]);
        scheduleId = parseInt(window.location.href.split('?')[1].split('&')[1].split('=')[1]);

        // 退出选座页面，进入订单页面
        $('#seat-state').css("display", "none");
        $('#order-state').css("display", "");

        postRequest(
            '/ticket/proceed?orderId=' + orderId,
            null,
            function (res) {
                if (res.success) {
                    var orderInfo = res.content;  // 从后端res中获取返回的数据

                    for(let ticket of res.content.ticketVOList) {
                        selectedSeats.push([ticket.columnIndex, ticket.rowIndex]);
                    }

                    getScheduleAndSeatsInfo();

                    renderOrder(orderInfo);
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );

        getVIPInfo();

	} else { // 从头购票
		scheduleId = parseInt(window.location.href.split('?')[1].split('&')[1].split('=')[1]);

        getScheduleAndSeatsInfo();
	}
});

function getScheduleAndSeatsInfo() {
    // 获得被锁座位信息
    getRequest(
        '/ticket/get/occupiedSeats?scheduleId=' + scheduleId,
        function (res) {
            if (res.success) {
                renderSchedule(res.content.scheduleItem, res.content.seats);
            }
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
}

// 加载某场次的座位
// schedule：场次号
// seats：座位信息
function renderSchedule(schedule, seats) {
	$('#schedule-hall-name').text(schedule.hallName);
	$('#order-schedule-hall-name').text(schedule.hallName);
	$('#schedule-fare').text(schedule.fare.toFixed(2));
	$('#order-schedule-fare').text(schedule.fare.toFixed(2));
	$('#schedule-time').text(schedule.startTime.substring(5, 7) + "月" + schedule.startTime.substring(8, 10) + "日 " + schedule.startTime.substring(11, 16) + "场");
	$('#order-schedule-time').text(schedule.startTime.substring(5, 7) + "月" + schedule.startTime.substring(8, 10) + "日 " + schedule.startTime.substring(11, 16) + "场");

	var hallDomStr = "";
	var seat = ""; // 所有座位的html文本
	for (var i = 0; i < seats.length; i++) { // 影厅的行
		var temp = ""; // 一行座位的html文本
		for (var j = 0; j < seats[i].length; j++) { // 影厅的列
			var id = "seat" + i + j; // 座位id

			if (seats[i][j] == 0) {
				// 座位未被锁定
				temp += "<button class='cinema-hall-seat-choose' id='"
					+ id
					+ "' onclick='seatClick(\"" + id + "\"," + i + "," + j + ")'></button>"; // 选择座位
			} else {
				// 座位已被锁定
				temp += "<button class='cinema-hall-seat-lock'></button>";
			}
		}
		seat += "<div>" + temp + "</div>";
	}
	var hallDom =
		"<div class='cinema-hall'>" +
		"<div>" +
		"<span class='cinema-hall-name'>" + schedule.hallName + "</span>" +
		"<span class='cinema-hall-size'>" + seats.length + '*' + seats[0].length + "</span>" +
		"</div>" +
		"<div class='cinema-seat'>" + seat +
		"</div>" +
		"</div>";
	hallDomStr += hallDom;

	$('#hall-card').html(hallDomStr);
}

// 选择座位
function seatClick(id, i, j) {
	let seat = $('#' + id);
	if (seat.hasClass("cinema-hall-seat-choose")) { // 座位未被选择
		seat.removeClass("cinema-hall-seat-choose");
		seat.addClass("cinema-hall-seat");

		selectedSeats[selectedSeats.length] = [i, j]
	} else { // 座位已被选择
		seat.removeClass("cinema-hall-seat");
		seat.addClass("cinema-hall-seat-choose");

		selectedSeats = selectedSeats.filter(function (value) {
			return value[0] != i || value[1] != j;
		})
	}

	selectedSeats.sort(function (x, y) { // 被选择的座位先按排号排序，再按列号排序
		var res = x[0] - y[0];
		return res === 0 ? x[1] - y[1] : res;
	});

	let seatDetailStr = "";
	if (selectedSeats.length == 0) { // 未选择座位，无法确认下单
		seatDetailStr += "还未选择座位";
		$('#order-confirm-btn').attr("disabled", "disabled")
	} else {
		for (let seatLoc of selectedSeats) {
			seatDetailStr += "<span>" + (seatLoc[0] + 1) + "排" + (seatLoc[1] + 1) + "座</span>";
		}
		$('#order-confirm-btn').removeAttr("disabled");
	}
	$('#seat-detail').html(seatDetailStr);
}

// 确认订单，支付
function orderConfirmClick() {
	// 退出选座页面，进入订单页面
	$('#seat-state').css("display", "none");
	$('#order-state').css("display", "");

	var seats=[];
	selectedSeats.forEach(function (value, index, array) {
		var seat = {};
		seat['columnIndex'] = value[1];
		seat['rowIndex']=value[0];
		seats.push(seat);
	});

	// 随机生成订单号
	function randomOrderId() {
		const now = new Date();
		return now.getFullYear()
			+ now.getMonth()
			+ now.getDate()
			+ now.getHours()
			+ now.getMinutes()
			+ now.getSeconds()
			+ sessionStorage.getItem('id')
			+ Math.floor(Math.random() * 1000);
	}
	
	var orderForm={
		orderId: randomOrderId(),
		userId: sessionStorage.getItem('id'),
		scheduleId: scheduleId,
		seats: seats
	};

	// 向后端提交TicketForm
	postRequest(
		'/ticket/lockSeat',
		orderForm,
		function (res) {
			if (res.success) {
				var orderInfo = res.content;  // 从后端res中获取返回的数据
				renderOrder(orderInfo);
			} else {
				alert(res.message);
			}
		},
		function (error) {
			alert(JSON.stringify(error));
		}
	);

	getVIPInfo();
}

function getVIPInfo() {
    getRequest(
        '/vip/' + sessionStorage.getItem('id') + '/get',
        function (res) {
            isVIP = res.success;
            useVIP = res.success;
            if (isVIP) {
                $('#member-balance').html("<div><b>会员卡余额：</b>" + res.content.balance.toFixed(2) + "元</div>");
            } else { // 不是会员，无会员卡支付界面
                $("#member-pay").css("display", "none");
                $("#nonmember-pay").addClass("active");

                $("#modal-body-member").css("display", "none");
                $("#modal-body-nonmember").css("display", "");
            }
        },
        function (error) {
            alert(error);
        });
}

// 切换支付方式
function switchPay(type) {
	useVIP = (type == 0);
	if (type == 0) {
		$("#member-pay").addClass("active");
		$("#nonmember-pay").removeClass("active");

		$("#modal-body-member").css("display", "");
		$("#modal-body-nonmember").css("display", "none");
	} else {
		$("#member-pay").removeClass("active");
		$("#nonmember-pay").addClass("active");

		$("#modal-body-member").css("display", "none");
		$("#modal-body-nonmember").css("display", "");
	}
}

// 加载订单
function renderOrder(orderInfo) {
	var ticketStr = "<div>" + selectedSeats.length + "张</div>"; // 电影票数量
	for (let ticketInfo of orderInfo.ticketVOList) {
		ticketStr += "<div>" + (ticketInfo.rowIndex + 1) + "排" + (ticketInfo.columnIndex + 1) + "座</div>"; // 座位
		order.ticketId.push(ticketInfo.id); // 增加订单中的电影票
	}
	$('#order-tickets').html(ticketStr);

	var total = orderInfo.total.toFixed(2); // 不计优惠的总金额
	$('#order-total').text(total);
	$('#order-footer-total').text("总金额： ¥" + total);


	var couponTicketStr = "";
	if (orderInfo.coupons.length == 0) { // 不存在用户拥有且达到使用门槛的优惠券
		$('#order-discount').text("优惠金额：无");
		$('#order-actual-total').text(" ¥" + total); // 实付款
		$('#pay-amount').html("<div><b>金额：</b>" + total + "元</div>");
	} else {
		coupons = orderInfo.coupons; // 存在用户拥有且达到使用门槛的优惠券
		for (let coupon of coupons) {
			couponTicketStr += "<option>满" + coupon.targetAmount + "减" + coupon.discountAmount + "</option>"
		}
		$('#order-coupons').html(couponTicketStr);
		changeCoupon(0); // 默认使用第一张优惠券
	}
}

function changeCoupon(couponIndex) {
	order.couponId = coupons[couponIndex].id;
	$('#order-discount').text("优惠金额： ¥" + coupons[couponIndex].discountAmount.toFixed(2));
	var actualTotal = // 实付款
		(parseFloat($('#order-total').text()) - parseFloat(coupons[couponIndex].discountAmount))
			.toFixed(2);
	$('#order-actual-total').text(" ¥" + actualTotal);
	$('#pay-amount').html("<div><b>金额：</b>" + actualTotal + "元</div>");
}

// 确认支付
function payConfirmClick() {
	if (useVIP) {
		postPayByVIPCardRequest();
	} else {
		if (validateForm()) {
			if ($('#userBuy-cardNum').val() === "123123123" && $('#userBuy-cardPwd').val() === "123123") {
				postPayRequest();
			} else {
				alert("银行卡号或密码错误");
			}
		}
	}
}

function postPayByVIPCardRequest() {
	$('#order-state').css("display", "none");

	postRequest(
		'/ticket/vip/buy?couponId='+order.couponId,
		order.ticketId,
		function (res) {
			if (res.success) {
				$('#success-state').css("display", "");
				$('#buyModal').modal('hide');
			} else {
				$('#failure-state').css("display", "");
				$('#buyModal').modal('hide');
				alert("会员卡余额不足，请及时充值");
			}
		},
		function (error) {
			alert(JSON.stringify(error));
		}
	);
}

function postPayRequest() {
	// 退出订单页面，进入付款页面
	$('#order-state').css("display", "none");

	postRequest(
		'/ticket/buy?couponId='+order.couponId,
		order.ticketId,
		function (res) {
			if (res.success) {
				$('#success-state').css("display", "");
				$('#buyModal').modal('hide');
			} else {
				$('#failure-state').css("display", "");
				$('#buyModal').modal('hide');
			}
		},
		function (error) {
			alert(JSON.stringify(error));
		}
	);
}

// 验证银行卡支付的账号和密码
function validateForm() {
	var isValidate = true;
	if (!$('#userBuy-cardNum').val()) { // 未填银行卡账号
		isValidate = false;
		$('#userBuy-cardNum').parent('.form-group').addClass('has-error');
		$('#userBuy-cardNum-error').css("visibility", "visible");
	}
	if (!$('#userBuy-cardPwd').val()) { // 未填银行卡密码
		isValidate = false;
		$('#userBuy-cardPwd').parent('.form-group').addClass('has-error');
		$('#userBuy-cardPwd-error').css("visibility", "visible");
	}
	return isValidate;
}