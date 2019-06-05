$(document).ready(function () {
	getTicketList();
	getOrderList();
});

function getTicketList() {
	getRequest(
		'/ticket/get/' + sessionStorage.getItem('id'),
		function (res) {
			renderTicketList(res.content);
		},
		function (error) {
			console.log(error);
			alert(error);
		});
}

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
 * 显示用户已购的电影票列表，每张电影票内容包括：
 * 电影名、电影开始时间、影厅名称、结束时间、票价、座位号（排号和列号），付款状态 （支付成功和未成功）
 */
function renderTicketList(list) {
	let $content_container_tbody = $("#tbody");
	$content_container_tbody.empty();
	let ticketDomStr = '';
	list.forEach(function (ticket) {
		let appendButton = "";
		if(ticket.state === "支付未完成") {
			appendButton =
				"<td><button class='proceed-btn btn-primary' id='proceed-" + ticket.id + "'>继续支付</button></td>";
		} else if (ticket.state === "支付已完成") {
			appendButton =
				"<td><button class='pick-up-btn btn-primary' id='pick-up-" + ticket.id + "'>取票</button></td>" +
				"<td><button class='refund-btn btn-primary' id='refund-" + ticket.id + "'>退票</button></td>";
		}

		// 这里的座位行号、列号需要+1
		ticketDomStr +=
			"<tr " + "id='ticket-" + ticket.id + "' data-ticket='" + JSON.stringify(ticket) + "'>" +
			"<td>" + ticket.schedule.movieName + "</td>" +
			"<td>" + ticket.schedule.hallName + "</td>" +
			"<td>" + (ticket.rowIndex + 1) + "排" +(ticket.columnIndex + 1) + "座"+"</td>"+
			"<td>" + formatDateAndTime(new Date(ticket.schedule.startTime)) + "</td>" +
			"<td>" + formatDateAndTime(new Date(ticket.schedule.endTime)) + "</td>" +
			"<td>" + ticket.state + "</td>" +
			appendButton +
			"</tr>";
	});
	$content_container_tbody.append(ticketDomStr);
}

function renderOrderList(list) {
	let $tryIt = $("#try-it");
	$tryIt.empty();
	let ordersDomStr = "";
	list.forEach(function (order) {

		let ticketList = order.ticketVOList;
		let seatsDomStr = "";
		ticketList.forEach(function (ticket) {
			seatsDomStr += "        <span class='title'>" + (ticket.rowIndex + 1) + "排" +(ticket.columnIndex + 1) + "座" + "</span>";
		});

		ordersDomStr +=
			"<div class='order-container'>" +
			"    <div class='order-card'>" +
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

// 点击取票
$(document).on('click','.pick-up-btn', function (e) {
	let ticketId = parseInt(this.id.replace("pick-up-", ""));
	let ticket = $('#ticket-' + ticketId)[0].dataset.ticket;
	console.log(ticket);
	// TODO
	getTicketList();
});

// 点击退票
$(document).on('click','.refund-btn', function (e) {
	let ticketId = parseInt(this.id.replace("refund-", ""));
	let ticket = $('#ticket-' + ticketId)[0].dataset.ticket;
	console.log(ticket);
	// TODO
	getTicketList();
});

function formatDateAndTime(date) {
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