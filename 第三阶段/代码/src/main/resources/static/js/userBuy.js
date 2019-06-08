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
		if(order.state === "支付未完成") {
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
			"        <span class='order-detail'>" + "电影：" + order.schedule.movieName + "</span>" +
			"        <span class='order-detail'>" + "影厅：" + order.schedule.hallName + "</span>" +
			"        <span class='order-detail'>" + "开始时间：" + formatDateAndTime(new Date(order.schedule.startTime)) + "</span>" +
			"        <span class='order-detail'>" + "结束时间：" + formatDateAndTime(new Date(order.schedule.endTime)) + "</span>" +
			"    </div>" +
			"    <div class='seat-card'>" +
			seatsDomStr +
			"    </div>" +
			"</div>";
	});
	$tryIt.append(ordersDomStr);
}

// 点击继续购票
$(document).on('click','.proceed-btn', function (e) {
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

// 点击取票
$(document).on('click','.pick-up-btn', function (e) {
	let ticketId = parseInt(this.id.replace("pick-up-", ""));
	let ticket = JSON.parse($('#ticket-' + ticketId)[0].dataset.ticket);
	let orderId = parseInt(this.parentElement.parentElement.parentElement.id.replace("order-", ""));

	// console.log(orderId);
	let schedule = JSON.parse($('#order-' + orderId)[0].dataset.order).schedule;
	// console.log(ticket);

	// 对"取票"Modal进行初始化，显示当前要取票的详细信息
	$('#pick-up-ticket-ticketid').text(ticketId);
	$('#pick-up-ticket-moviename').text(schedule.movieName);
	$('#pick-up-ticket-hallname').text(schedule.hallName);
	$('#pick-up-ticket-starttime').text(formatDateAndTime(schedule.startTime));
	$('#pick-up-ticket-endtime').text(formatDateAndTime(schedule.endTime));
	$('#pick-up-ticket-seat').text(ticket.rowIndex+"排 "+ticket.columnIndex+"座");

	// 显示"取票"的Modal
	$('#pickUpTicketModal').modal('show');
});

// 点击"取票"Modal的"确认取票"按钮
$(document).on('click', '#pick-up-ticket-form-btn', function (e) {
	let ticketId = parseInt($('#pick-up-ticket-ticketid').text());

	console.log(ticketId);

	postRequest(
		'/ticket/pickUp?ticketId='+ticketId,
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

// 点击退票
$(document).on('click','.refund-btn', function (e) {
	let ticketId = parseInt(this.id.replace("refund-", ""));
	let ticket = JSON.parse($('#ticket-' + ticketId)[0].dataset.ticket);
	console.log(ticket);
	// TODO
    getOrderList(); // 刷新页面
});

function formatDateAndTime(date) {
	var date = new Date(date);
    var year = date.getFullYear();
    var month = date.getMonth()+1+'';
    var day = date.getDate()+'';
    var hour = date.getHours()+'';
    var minutes = date.getMinutes()+'';
    var seconds = date.getSeconds()+'';
    month.length===1 && (month = '0'+month);
    day.length===1 && (day = '0'+day);
    hour.length===1 && (hour = '0'+hour);
    minutes.length===1 && (minutes = '0'+minutes);
    seconds.length===1 && (seconds = '0'+seconds);
    return year+'-'+month+'-'+day+' '+hour+":"+minutes+":"+seconds;
}