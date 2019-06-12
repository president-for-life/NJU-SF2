var strategy_first;  // 首次请求获取到的strategy

$(document).ready(function () {
	getOrderList();
});

function getOrderList() {
	getRequest(
		'/ticket/get/order/' + sessionStorage.getItem('id'),
		function (res) {
			renderOrderList(res.content);
		},
		function (error) {
			console.log(error);
			alert(error);
		});
}

/**
 * 电影名、电影开始时间、影厅名称、结束时间、票价、座位号（排号和列号），付款状态 （支付成功和未成功）
 */
function renderOrderList(list) {
	let $tryIt = $("#try-it");
	$tryIt.empty();
	let ordersDomStr = "";
	list.forEach(function (order) {
		let seatList = order.seatVOList;

		let seatsDomStr = "";
		seatList.forEach(function (seat) {
            let appendButton = "";
            if (seat.state === "支付已完成") {
                appendButton =
                    "<button type='button' class='btn btn-primary pick-up-btn user-buy-btn ' id='pick-up-" + seat.id + "'>取票</button>" +
                    "<button type='button' class='btn btn-primary refund-btn user-buy-btn' id='refund-" + seat.id + "'>退票</button>";
            }

			let toAddColor = "";

			if (seat.state == "支付已完成") {
				toAddColor = "green";
			} else if (seat.state == "已退票") {
				toAddColor = "palevioletred";
			} else if (seat.state == "已失效") {
				toAddColor = "gray";
			} else if (seat.state == "已出票") {
				toAddColor="lightseagreen";
			}

			seatsDomStr +=
                "        <div class='seat'" + "id='ticket-" + seat.id + "' data-ticket='" + JSON.stringify(seat) + "'>" +
                "            <span class='seat-info'>" + (seat.rowIndex + 1) + "排 " + (seat.columnIndex + 1) + "座" + "</span>" +
				"            <span class='seat-info' style='color: "+toAddColor+"'>" + seat.state+"</span>" +
                appendButton +
                "        </div>";
		});

		let proceedToPayButton = "";
		if(order.state === "支付未完成") {
            proceedToPayButton =
                "<span class='order-detail'>" +
                "<button class='btn proceed-btn btn-primary' id='proceed-" + order.orderId + "'>" +
                "继续支付" +
                "</button>"+
                "</span>";
        }

		ordersDomStr +=
			"<div class='order-container' id='order-" + order.orderId + "' data-order='" + JSON.stringify(order) + "'>" +
            // proceedToPayButton +
			"    <div class='card order-card'>" +
            "        <span class='title'>" + "订单号：" + order.orderId + "</span>" +
			"        <span class='order-detail'>" + "电影：" + order.schedule.movieName + "</span>" +
			"        <span class='order-detail'>" + "影厅：" + order.schedule.hallName + "</span>" +
			"        <span class='order-detail'>" + "开始时间：" + formatDateAndTime(new Date(order.schedule.startTime)) + "</span>" +
			"        <span class='order-detail'>" + "结束时间：" + formatDateAndTime(new Date(order.schedule.endTime)) + "</span>" +
			proceedToPayButton+
			"    </div>" +
			"    <div class='seat-card card'>" +
			seatsDomStr +
			"    </div>" +
			"</div>";
	});
	$tryIt.append(ordersDomStr);
}

// 点击继续购票
$(document).on('click', '.proceed-btn', function (e) {
	// TODO 失效验证
	let orderId = parseInt(this.id.replace("proceed-", ""));
	let order = JSON.parse($('#order-' + orderId)[0].dataset.order);
	console.log(order);
	console.log(order.orderId);
	console.log(order.schedule);
	window.location.href =
		'/user/movieDetail/buy?id=' + order.schedule.movieId +
		'&scheduleId=' + order.schedule.id +
		'&orderId=' + orderId;
});
// =========================================================================
// ==================================取票====================================
// =========================================================================

// 点击取票
$(document).on('click', '.pick-up-btn', function (e) {
	let ticketId = parseInt(this.id.replace("pick-up-", ""));
	let ticket = JSON.parse($('#ticket-' + ticketId)[0].dataset.ticket);
	let orderId = parseInt(this.parentElement.parentElement.parentElement.id.replace("order-", ""));
	let schedule = JSON.parse($('#order-' + orderId)[0].dataset.order).schedule;
	// console.log(ticket);

	// 对"取票"Modal进行初始化，显示当前要取票的详细信息
	$('#pick-up-ticket-ticketid').text(ticketId);
	$('#pick-up-ticket-moviename').text(schedule.movieName);
	$('#pick-up-ticket-hallname').text(schedule.hallName);
	$('#pick-up-ticket-starttime').text(formatDateAndTime(schedule.startTime));
	$('#pick-up-ticket-endtime').text(formatDateAndTime(schedule.endTime));
	$('#pick-up-ticket-seat').text((ticket.rowIndex + 1) + "排 " + (ticket.columnIndex + 1) + "座");

	// 显示"取票"的Modal
	$('#pickUpTicketModal').modal('show');
});

// 点击"取票"Modal的"确认取票"按钮
$(document).on('click', '#pick-up-ticket-form-btn', function (e) {
	let ticketId = parseInt($('#pick-up-ticket-ticketid').text());

	console.log(ticketId);

	postRequest(
		'/ticket/pickUp?ticketId=' + ticketId,
		null,
		function (res) {
			if (res.success) {
				getOrderList(); // 刷新页面
				$('#pickUpTicketModal').modal('hide');
			} else {
				alert(res.message);
			}
		},
		function (error) {
			alert(JSON.stringify(error));
		}
	);
});

// =====================================================================
// =================================退票=================================
// =====================================================================

// 点击退票
$(document).on('click', '.refund-btn', function (e) {
	let ticketId = parseInt(this.id.replace("refund-", ""));
	let ticket = JSON.parse($('#ticket-' + ticketId)[0].dataset.ticket);
	let orderId = parseInt(this.parentElement.parentElement.parentElement.id.replace("order-", ""));
	let schedule = JSON.parse($('#order-' + orderId)[0].dataset.order).schedule;

	// 获取要退票的电影票对应的退票策略
	// console.log(ticketId);

	let strategy = getStrategyForTicket(ticketId);
	strategy_first = strategy;

	// 如果该电影票没有对应的退票策略，默认不允许退票


	// todo:这里还没有测试如果没有对应的退票策略会怎么样
	if (strategy == -1) {
		confirm("当前选中的电影票没有对应的退票策略，不允许退票！");
		return;
	}

	// 如果该电影票有对应的退票策略，则进行相应的计算
	let timeDifference = timeMinuteDifference(new Date(), schedule.startTime);
	let refundable = strategy.refundable;
	let ratio = strategy.ratio;
	let time = strategy.time;
	let finalRefundable = (Number(timeDifference) >= Number(time)) && refundable;

	// 如果最终允许退票
	if (finalRefundable) {
		var amount = ticket.actualPayment * ratio;  // 计算退款金额
		// 对"退票"Modal进行初始化，显示当前要退票的详细信息
		$('#refund-ticket-ticketid').text(ticketId);  // 电影票号
		$('#refund-ticket-moviename').text(schedule.movieName);  // 电影名称
		$('#refund-ticket-hallname').text(schedule.hallName);  // 影厅号
		$('#refund-ticket-time').text(timeDifference + " 分钟");  // 距离开影时间
		$('#refund-ticket-seat').text((ticket.rowIndex + 1) + "排 " + (ticket.columnIndex + 1) + "座");  // 座位号
		$('#refund-ticket-payment').text(ticket.actualPayment.toFixed(2)+" 元");
		$('#refund-ticket-amount').text(amount.toFixed(2) + " 元");  // 退款金额
		$('#refund-ticket-bank-card-input').parent().parent('.form-group').removeClass('has-error');
		$('#refund-ticket-bank-card-input').val('');  // 清空银行卡号

		// 显示"退票"的Modal
		$('#refundTicketModal').modal('show');
	} else {
		// 距离开影的时间不满足
		if (Number(timeDifference) < Number(time)) {
			confirm("当前选中的电影票对应的电影场次距离开影的时间不足 " + time + " 分钟，不允许退票！");
		} else if (!refundable) {
			// 对应的退票策略不允许退票
			confirm("当前选中的电影拍哦对应的退票策略不允许退票！");
		} else {
			confirm("不允许退票！");
		}
	}
	// getOrderList(); // 刷新页面
});

// 点击"退票"Modal的"确认退票"按钮
$(document).on('click', '#refund-ticket-form-btn', function (e) {
	// 银行卡号非空验证
	if ($("#refund-ticket-bank-card-input").val() == "") {
		$("#refund-ticket-bank-card-input").parent().parent('.form-group').addClass('has-error');
		return;
	}
	var ticketId = Number($('#refund-ticket-ticketid').text());

	var strategy_second = getStrategyForTicket(ticketId);  // 第二次获取的strategy
	// 如果两次获取到的strategy不相同，说明退票策略已经被修改，用户需要重新进行退票
	// todo：还没有进行测试

	if (!isSameStrategies(strategy_first,strategy_second)) {
		alert("所选电影票对应的退票策略已经被修改，请重新进行退票！")
		$('#refundTicketModal').modal('hide');
		return;
	}

	postRequest(
		'/ticket/refund/confirm?ticketId=' + ticketId,
		null,
		function (res) {
			if (res.success) {
				getOrderList();  // 重新渲染订单列表
				$('#refundTicketModal').modal('hide');
			} else {
				alert(res.message);
			}
		},
		function (error) {
			alert(JSON.stringify(error));
		}
	);
});

/**
 * 获取指定电影票对应的退票策略
 * @param ticketId
 */
function getStrategyForTicket(ticketId) {
	let strategy;
	// postRequest(
	// 	'/ticket/refund/choose?ticketId=' + ticketId,
	// 	null,
	// 	function (res) {
	// 		if (res.success) {
	// 			strategy= JSON.stringify(res.content);
	// 		} else {
	// 			strategy = -1;
	// 		}
	// 	},
	// 	function (error) {
	// 		alert(JSON.stringify(error));
	// 	}
	// );
	// todo：这里如果不设置async=false，就会出现异步执行，该函数的返回结果会变成"undefined"；不知道有没有别的解决办法
	$.ajax({
		async: false,
		type: "POST",
		url: '/ticket/refund/choose?ticketId=' + ticketId,
		success: function (res) {
			if (res.success) {
				strategy = res.content;
			} else {
				strategy = -1;
			}
		},
		error: function (error) {
			alert(JSON.stringify(error));
		}
	});
	return strategy;
}

function formatDateAndTime(date) {
	var date = new Date(date);
	var year = date.getFullYear();
	var month = date.getMonth() + 1 + '';
	var day = date.getDate() + '';
	var hour = date.getHours() + '';
	var minutes = date.getMinutes() + '';
	var seconds = date.getSeconds() + '';
	month.length === 1 && (month = '0' + month);
	day.length === 1 && (day = '0' + day);
	hour.length === 1 && (hour = '0' + hour);
	minutes.length === 1 && (minutes = '0' + minutes);
	seconds.length === 1 && (seconds = '0' + seconds);
	return year + '-' + month + '-' + day + ' ' + hour + ":" + minutes + ":" + seconds;
}

/**
 * 获取两个Date之间的分钟差（整数），可能为负数
 * YYYY-mm-dd HH:MM:SS
 *
 */
function timeMinuteDifference(startDate, endDate) {
	var start = new Date(startDate);
	var end = new Date(endDate);
	var mm = end - start;  // 毫秒差
	return parseInt(mm / 60000);  // 分钟差:：整数
}

function isSameStrategies(strategy_first, strategy_second) {
	// 如果第二次没有查询到的strategy
	if (strategy_second == -1) {
		console.log("第二次查询strategy没有获取到");
		return false;
	}

	// 比较strategy的id、refundable、ratio、time、movieList
	if (strategy_first.id != strategy_second.id) {
		console.log("id不同");
		return false;
	}
	if (strategy_first.refundable != strategy_second.refundable) {
		console.log("refundable不同");
		return false;
	}
	if (strategy_first.ratio != strategy_second.ratio) {
		console.log("id不同");
		return false;
	}
	if (strategy_first.time != strategy_second.time) {
		console.log("time不同");
		return false;
	}
	if (JSON.stringify(strategy_first.movieList) != JSON.stringify(strategy_second.movieList)) {
		console.log("movieList不同")
		return false;
	}
	console.log("两次获取的退票策略相同");
	return true;
}