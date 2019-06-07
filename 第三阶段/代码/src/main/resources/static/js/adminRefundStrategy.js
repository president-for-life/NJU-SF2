$(document).ready(function () {

	getSelectableMovies();

	getStrategyList();

	/**
	 * 点击"确认"按钮
	 */
	$("#strategy-form-btn").click(function () {
		// alert("click");
		var formData = getStrategyForm();
		alert(formData.refundable);
		if (!validateStrategyForm(formData)) {
			alert("not valid");
			return;
		}
		postRequest(
			'/ticket/refundStrategy/publish',
			formData,
			function (res) {
				getStrategyList();
				$('#strategyModal').modal('hide');
			},
			function (error) {
				alert(error);
			}
		);
		// alert("'#strategy-form-btn'.click()")
	});

	/**
	 * 获取当前新增的退票策略的Form
	 */
	function getStrategyForm() {
		alert("getStrategyForm()");
		return {
			refundable: $('#strategy-refundable-input').val() == "是",
			ratio:$('#strategy-ratio-input').val(),
			time:$('#strategy-time-input').val(),
			movieList:$('#strategy-movie-input').val()
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
			$('#strategy-refundable-input').parent('.form-group').addClass('has-error');
		}
		if(!data.ratio){
			isValidate = false;
			$('#strategy-ratio-input').parent('.form-group').addClass('has-error');
		}if(!data.time){
			isValidate = false;
			$('#strategy-time-input').parent('.form-group').addClass('has-error');
		}
		alert(isValidate);
		return isValidate;
	}

	/**
	 * 渲染已有的退票策略
	 */
	function renderStrategyList(strategyList) {
		$('.strategy-list').empty();
		var strategyDomStr = '';
		strategyList.forEach(function (strategy) {
			strategyDomStr+=
				"<li class='strategy-item card'>"+
				"<div class='strategy-info'>" +
				"<span class='strategy-title'>" +
				"<span class='primary-text'>退票策略id："+strategy.id+"</span>"+
				"</div>"+
				"<div style='display: flex'>" +
				"<span class='label "+(!strategy.refundable ? 'primary-bg' : 'error-bg')+"'>" + (strategy.refundable ? '允许' : '禁止') + "</span>"+
				"<span>返还比例："+strategy.ratio+"</span>"+
				"<span>开场前 "+strategy.time+" 分钟不允许退票</span>"+
				"</div>"+
				"<div class='strategy-operation'>修改退票策略</div>"+
				"</div>"+
				"</li>"
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

	$('#strategy-movie-input').change(function () {
		var movieId = $('#strategy-movie-input').val();
		var movieName = $('#strategy-movie-input').children('option:selected').text();
		if (movieId == -1) {
			selectedMovieIds.clear();
			selectedMovieNames.clear();
		} else {
			selectedMovieIds.add(movieId);
			selectedMovieNames.add(movieName);
			// alert(movieName);
		}
		renderSelectedMovies();
		// alert("renderSelectedMovies()")
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
		// alert(moviesDomStr);
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
				$('#strategy-movie-input').append("<option value=" + -1 + ">所有电影</option>");
				movieList.forEach(function (movie) {
					$('#strategy-movie-input').append("<option value=" + movie.id + ">" + movie.name + "</option>");
				});
			},
			function (error) {
				alert(error);
			}
		);

	}
});