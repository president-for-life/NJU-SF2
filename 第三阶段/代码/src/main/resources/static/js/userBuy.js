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
					"<button class='pick-up-btn btn-primary' id='pick-up-" + seat.id + "'>取票</button>" +
					"<button class='refund-btn btn-primary' id='refund-" + seat.id + "'>退票</button>";
			}

			seatsDomStr +=
				"        <div " + "id='ticket-" + seat.id + "' data-ticket='" + JSON.stringify(seat) + "'>" +
				"            <span>" + (seat.rowIndex + 1) + "排" + (seat.columnIndex + 1) + "座" + "</span>" +
				appendButton +
				"        </div>";
		});

		let proceedToPayButton = "";
		if (order.state === "支付未完成") {
			proceedToPayButton =
				"<span>" +
				"<button class='proceed-btn btn-primary' id='proceed-" + order.orderId + "'>" +
				"继续支付" +
				"</button>" +
				"</span>";
		}

		ordersDomStr +=
			"<div class='order-container' id='order-" + order.orderId + "' data-order='" + JSON.stringify(order) + "'>" +
			proceedToPayButton +
			"    <div class='order-card'>" +
			"        <span class='title'>" + "订单号：" + order.orderId + "</span>" +
			"        <span class='title'>" + order.schedule.movieName + "</span>" +
			"        <span class='title'>" + order.schedule.hallName + "</span>" +
			"        <span class='title'>" + formatDateAndTime(new Date(order.schedule.startTime)) + "</span>" +
			"        <span class='title'>" + formatDateAndTime(new Date(order.schedule.endTime)) + "</span>" +
			"    </div>" +
			"    <div class='seat-card'>" +
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

	// console.log(strategy);
	// // console.log(strategy.id);
	// console.log(refundable);
	// console.log(ratio);
	// console.log(time);
	// console.log(finalRefundable);
	// console.log(timeDifference);

	// 如果最终允许退票
	if (finalRefundable) {
		var amount = ticket.actualPayment * ratio;  // 计算退款金额
		// 对"退票"Modal进行初始化，显示当前要退票的详细信息
		$('#refund-ticket-ticketid').text(ticketId);  // 电影票号
		$('#refund-ticket-moviename').text(schedule.movieName);  // 电影名称
		$('#refund-ticket-hallname').text(schedule.hallName);  // 影厅号
		$('#refund-ticket-time').text(timeDifference + " 分钟");  // 距离开影时间
		$('#refund-ticket-seat').text((ticket.rowIndex + 1) + "排 " + (ticket.columnIndex + 1) + "座");  // 座位号
		$('#refund-ticket-payment').text(ticket.actualPayment+" 元");
		$('#refund-ticket-amount').text(amount + " 元");  // 退款金额

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
	var ticketId = Number($('#refund-ticket-ticketid').text());

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