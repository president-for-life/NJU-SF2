$(document).ready(function () {

	getSelectableMovies();

	getStrategyList();

	/**
	 * 点击"新增退票策略"Modal的"确认"按钮
	 */
	$("#add-strategy-form-btn").click(function () {
		var formData = getStrategyForm();
		if (!validateStrategyForm(formData)) {
			alert("请确保输入值的有效性！");
			return;
		}
		console.log(formData);
		postRequest(
			'/ticket/refundStrategy/publish',
			formData,
			function (res) {
				getStrategyList();
				$('#addStrategyModal').modal('hide');
			},
			function (error) {
				alert(error);
			}
		);
	});

	/**
	 * 获取当前新增的退票策略的Form
	 */
	function getStrategyForm() {
		return {
			refundable: $('#add-strategy-refundable-input').val() == "是",
			ratio:$('#add-strategy-ratio-input').val(),
			time:$('#add-strategy-time-input').val()
			// movieList: selectedMovieIds
		};
	}

	/**
	 * 判断当前新增的退票策略的Form中的数据是否有效
	 * @param data
	 * @returns {boolean}
	 */
	function validateStrategyForm(data) {
		var isValidate = true;
		if(!data.refundable){
			isValidate = false;
			$('#add-strategy-refundable-input').parent('.form-group').addClass('has-error');
		}
		if(!data.ratio){
			isValidate = false;
			$('#add-strategy-ratio-input').parent('.form-group').addClass('has-error');
		}if(!data.time){
			isValidate = false;
			$('#add-strategy-time-input').parent('.form-group').addClass('has-error');
		}
		return isValidate;
	}

	/**
	 * 渲染已有的退票策略
	 */
	function renderStrategyList(strategyList) {
		$('.strategy-on-list').empty();
		var strategyDomStr = '';
		strategyList.forEach(function (strategy) {
			strategyDomStr+=
				"<li class='strategy-item card' id='strategy-"+strategy.id+"' data-strategy='"+JSON.stringify(strategy)+"'>"+
					"<div class='strategy-info'>" +
						"<div class='strategy-title'>" +
							"<span class='primary-text'>退票策略id："+strategy.id+"</span>"+
							// "<span class='strategy-operation'>修改退票策略</span>"+
						"</div>"+
						"<div style='display: flex'>" +
							"<span class='label "+(strategy.refundable ? 'primary-bg' : 'error-bg')+"'>" + (strategy.refundable ? '允许退票' : '禁止退票') + "</span>"+
							"<span>返还比例："+strategy.ratio+"</span>"+
							"<span>开场前 "+strategy.time+" 分钟不允许退票</span>"+
						"</div>"+
						"<div id='movie-list-for-strategy' >" +
							"<span>使用本退票策略的电影：</span>"+
							"<button type='button' class='btn btn-primary add-movie-for-strategy'><i" +
				" class='icon-plus-sign'></i>新增电影</button>"+
							"<button type='button' class='btn btn-error remove-movie-for-strategy'><i" +
				" class='icon-minus-sign'></i>删除电影</button>"+
						"</div>"+
					"</div>"+
				"</li>";

			var movieDomStr = '';
			strategy.movieList.forEach(function (movieName) {
				movieDomStr+="<span class='label label-primary'>"+movieName+"</span>";
			});
			$('#add-movie-list-for-strategy').append(movieDomStr);
		});
		$('.strategy-on-list').append(strategyDomStr);
	}

	/**
	 * 获取当前已有的退票策略
	 */
	function getStrategyList() {
		getRequest(
			'/ticket/refundStrategy/all',
			function (res) {
				renderStrategyList(res.content);
			},
			function (error) {
				alert(error);
			}
		);
	}

	var selectedMovieIds = new Set();
	var selectedMovieNames = new Set();

	$('#add-strategy-movie-input').change(function () {
		var movieId = $('#add-strategy-movie-input').val();
		var movieName = $('#add-strategy-movie-input').children('option:selected').text();
		if (movieId == -1) {
			selectedMovieIds.clear();
			selectedMovieNames.clear();
		} else {
			selectedMovieIds.add(movieId);
			selectedMovieNames.add(movieName);
		}
		renderSelectedMovies();
	});

	/**
	 * 渲染选择的使用该退票策略的电影列表
	 */
	function renderSelectedMovies() {
		$('#selected-movies').empty();
		var moviesDomStr = "";
		selectedMovieNames.forEach(function (movieName) {
			moviesDomStr+="<span class='label label-primary'>"+movieName+"</span>";
		});
		$('#selected-movies').append(moviesDomStr);
	}

	/**
	 * 获取可以选择的电影列表：这些电影必须还没有指定退票策略、未下架
	 */
	function getSelectableMovies() {
		getRequest(
			'/ticket/refundStrategy/getSelectableMovies',
			function (res) {
				var movieList = res.content;
				$('#add-strategy-movie-input').append("<option value=" + -1 + ">所有电影</option>");
				movieList.forEach(function (movie) {
					$('#add-strategy-movie-input').append("<option value=" + movie.id + ">" + movie.name + "</option>");
				});
			},
			function (error) {
				alert(error);
			}
		);

	}

	/**
	 * 点击"修改退票策略"Modal的"确定"按钮：将新输入的Form提交给后端
	 */
	$('#update-strategy-form-btn').click(function () {
		var formData={
			id: Number($('#updateStrategyModal')[0].dataset.strategyId),
			refundable: $('#update-strategy-refundable-input').val()=="是",
			ratio:$('#update-strategy-ratio-input').val(),
			time:$('#update-strategy-time-input').val()
		};

		postRequest(
			'/ticket/refundStrategy/update',
			formData,
			function (res) {
				if (res.success) {
					getStrategyList();
					$('#updateStrategyModal').modal('hide');
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
	 * 如果点击了特定的退票策略，就进入了"修改退票策略"Modal
	 * todo
	 */
	$(document).on('click', '.strategy-item', function (e) {
		var strategy = JSON.parse(e.currentTarget.dataset.strategy);

		$("#update-strategy-refundable-input").val(strategy.refundable?"是":"否");
		$("#update-strategy-ratio-input").val(strategy.radio);
		$("#update-strategy-time-input").val(strategy.time);

		$("#updateStrategyModal").modal('show');
		$("#updateStrategyModal")[0].dataset.strategyId = strategy.id;
	});

	/**
	 * 如果点击了"新增电影"按钮，就为指定的退票策略添加电影
	 */
	// $('.add-movie-for-strategy').click(function (e) {
	// 	e.stopPropagation();
	// 	return false;
	// });

	$(document).on('click', '.add-movie-for-strategy', function (e) {
		e.stopPropagation();
		alert("点击新增电影按钮");
	});

	/**
	 * 点击"为退票策略新增电影"Modal的"确定"按钮：将选择的要添加的电影列表提交给后端
	 */
	$('#add-movie-for-strategy-form-btn').click(function () {
		// todo
		var formData={

		};


		postRequest(
			'/ticket/refundStrategy/addMovies',
			formData,
			function (res) {
				// todo：渲染指定退票策略的电影列表

			},
			function (error) {
				alert(JSON.stringify(error));
			}
		);
	});


	// /**
	//  * 为指定的退票策略删除电影
	//  */
	// $('.remove-movie-for-strategy').click(function () {
	//
	//
	// });
	//
	// /**
	//  * 点击"为退票策略删除电影"Modal的"确定"按钮：将选择的要删除的电影列表提交给后端
	//  */
	// $('#remove-movie-for-strategy-form-btn').click(function () {
	// 	// todo
	// 	var formData={
	//
	// 	};
	//
	// 	postRequest(
	// 		'/ticket//refundStrategy/removeMovies',
	// 		formData,
	// 		function (res) {
	// 			// todo: 渲染指定退票策略的电影列表
	// 		},
	// 		function (error) {
	// 			alert(JSON.stringify(error));
	// 		}
	// 	);
	// });
});