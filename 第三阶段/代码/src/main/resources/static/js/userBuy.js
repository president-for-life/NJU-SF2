$(document).ready(function () {
	getMovieList();

	function getMovieList() {
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

	/**
	 * 显示用户已购的电影票列表，每张电影票内容包括：
	 * 电影名、电影开始时间、影厅名称、结束时间、票价、座位号（排号和列号），付款状态 （支付成功和未成功）
	 *
	 * @author 梁正川
	 */
	function renderTicketList(list) {
		var $content_container_tbody = $("#tbody");
		$content_container_tbody.empty();
		var ticketDomStr = '';
		list.forEach(function (ticket) {
			// 这里的座位行号、列号需要+1
			ticketDomStr +=
				"<tr>" +
				"<td>" + ticket.schedule.movieName + "</td>" +
				"<td>" + ticket.schedule.hallName + "</td>" +
				"<td>" + (ticket.rowIndex + 1) + "排" +(ticket.columnIndex + 1) + "座"+"</td>"+
				"<td>" + formatDateAndTime(new Date(ticket.schedule.startTime)) + "</td>" +
				"<td>" + formatDateAndTime(new Date(ticket.schedule.endTime)) + "</td>" +
				"<td>" + ticket.state + "</td>" +
				"</tr>";
		});
		$content_container_tbody.append(ticketDomStr);
	}
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