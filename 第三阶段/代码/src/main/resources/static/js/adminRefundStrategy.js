$(document).ready(function () {
	var selectedMovieIds = new Set();
	var selectedMovieNames = new Set();

	// getSelectableMovies();

	getStrategyList();

	/**
	 * 点击"新增退票策略"Modal的"确认"按钮
	 */
	$("#add-strategy-form-btn").click(function () {
		var formData = getStrategyForm();
		if (!validateAddStrategyForm(formData)) {
			alert("请确保输入值的有效性！");
			return;
		}
		console.log(formData);
		postRequest(
			'/ticket/refundStrategy/publish',
			formData,
			function (res) {
				getStrategyList();
				// 还原Modal中的设置
				$('#add-strategy-refundable-input').parent().parent('.form-group').removeClass('has-error');
				$('#add-strategy-ratio-input').parent().parent('.form-group').removeClass('has-error');
				$('#add-strategy-time-input').parent().parent('.form-group').removeClass('has-error');
				$('#addStrategyModal').modal('hide');
			},
			function (error) {
				alert(error);
			}
		);
		initializeAddStratrgyModal();
	});

	/**
	 * 当新增退票策略Modal的"是否允许退票"被修改
	 */
	$('#add-strategy-refundable-input').change(function () {
		console.log('"是否允许退票"被修改")');
		if ($('#add-strategy-refundable-input').val() == '是') {
			initializeAddStratrgyModal();
			$('#add-strategy-ratio-input').parent().parent('.form-group').show();
			$('#add-strategy-time-input').parent().parent('.form-group').show();
		} else {
			$('#add-strategy-ratio-input').parent().parent('.form-group').hide();
			$('#add-strategy-time-input').parent().parent('.form-group').hide();
			initializeUpdateStratrgyModal();
		}
	});

	function initializeAddStratrgyModal() {
		$('#add-strategy-ratio-input').val('');
		$('#add-strategy-time-input').val('');
		$('#add-strategy-ratio-input').parent().parent('.form-group').removeClass('has-error');
		$('#add-strategy-time-input').parent().parent('.form-group').removeClass('has-error');
	}

	/**
	 * 获取当前新增的退票策略的Form
	 */
	function getStrategyForm() {
		return {
			refundable: $('#add-strategy-refundable-input').val() == "是",
			ratio: $('#add-strategy-ratio-input').val(),
			time: $('#add-strategy-time-input').val()
			// movieList: selectedMovieIds
		};
	}

	/**
	 * 判断当前新增的退票策略的Form中的数据是否有效
	 * @param data
	 * @returns {boolean}
	 */
	function validateAddStrategyForm(data) {
		var isValidate = true;
		if (data.refundable=='') {
			isValidate = false;
			$('#add-strategy-refundable-input').parent().parent('.form-group').addClass('has-error');
		}
		// 如果不允许退票
		if (!data.refundable) {
			return true;
		}
		if (data.ratio=='') {
			isValidate = false;
			$('#add-strategy-ratio-input').parent().parent('.form-group').addClass('has-error');
		}
		if (data.time=='') {
			isValidate = false;
			$('#add-strategy-time-input').parent().parent('.form-group').addClass('has-error');
		}
		return isValidate;
	}

	/**
	 * 渲染已有的退票策略
	 * 如果是"不允许退票"，则不显示"返还比例"、"时间限制"
	 */
	function renderStrategyList(strategyList) {
		$('.strategy-on-list').empty();
		var strategyDomStr = '';
		strategyList.forEach(function (strategy) {
			strategyDomStr +=
				"<li class='strategy-item card' id='strategy-" + strategy.id + "' data-strategy='" + JSON.stringify(strategy) + "'>" +
				"    <div class='strategy-info'>" +
				"        <div class='strategy-title'>" +
				"            <span class='title'>退票策略号：" + strategy.id + "</span>" +
				"        </div>" +
				"        <div class='strategy-details'>" +
				"            <span class='label strategy-detail " + (strategy.refundable ? 'primary-bg' : 'error-bg') + "'>" + (strategy.refundable ? '允许退票' : '禁止退票') + "</span>";
			if (strategy.refundable) {
				// 如果允许退票
				strategyDomStr +=
					"        <span class='strategy-detail'>返还比例：" + strategy.ratio + "</span>" +
					"        <span class='strategy-detail'>开场前 " + strategy.time + " 分钟不允许退票</span>" +
					"    </div>";
			}
			strategyDomStr+="        " +
				"        <div class='movie-list-for-strategy' >" +
				"            <span>使用本退票策略的电影：</span>" +
				"            <button type='button' class='btn btn-primary add-movie-for-strategy'><i" +
				" class='icon-plus-sign'></i>新增电影</button>" +
				"        </div>" +
				"    </div>" +
				"</li>";
		});
		$('.strategy-on-list').append(strategyDomStr);

		// 渲染已有的退票策略对应的电影列表
		strategyList.forEach(function (strategy) {
			var movieDomStr = '';
			strategy.movieList.forEach(function (movie) {
				movieDomStr += "<span class='label label-primary movie-for-strategy'" + " data-movieid=" + movie.id + " data-strategyid=" + strategy.id + ">" +
					movie.name + " </span>";
				// console.log(movie.name);
			});
			// console.log(movieDomStr);
			$('#strategy-' + strategy.id + ' .movie-list-for-strategy span:first').after(movieDomStr);
			// $('#strategy-'+1+' .movie-list-for-strategy span:first').after("<span>测试</span>")
		});
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

	/**
	 * 获取可以选择的电影列表：这些电影必须还没有指定退票策略、未下架
	 */
	function getSelectableMovies() {
		// 首先进行清空
		$('#add-strategy-movie-input').empty();
		selectedMovieIds.clear();
		selectedMovieNames.clear();

		getRequest(
			'/ticket/refundStrategy/getSelectableMovies',
			function (res) {
				var movieList = res.content;
				if (movieList.length == 0) {
					$('#add-strategy-movie-input').append("<option value=" + -1 + ">没有可供添加的电影</option>");
				} else {
					$('#add-strategy-movie-input').append("<option value=" + -1 + ">所有电影</option>");
				}
				movieList.forEach(function (movie) {
					$('#add-strategy-movie-input').append("<option value=" + movie.id + ">" + movie.name + "</option>");
				});
			},
			function (error) {
				alert(error);
			}
		);

	}

	// =============================================================================
	// ===============================修改退票策略===========================
	// =============================================================================

	/**
	 * 点击"修改退票策略"Modal的"确定"按钮：将新输入的Form提交给后端
	 */
	$('#update-strategy-form-btn').click(function () {
		var formData = {
			id: Number($('#updateStrategyModal')[0].dataset.strategyId),
			refundable: $('#update-strategy-refundable-input').val() == "是",
			ratio: $('#update-strategy-ratio-input').val(),
			time: $('#update-strategy-time-input').val()
		};
		if (!validateUpdateStrategyForm(formData)) {
			alert("请确保输入值的有效性！");
			return;
		}

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
	 * 判断当前修改的退票策略的Form中的数据是否有效
	 * @param data
	 * @returns {boolean}
	 */
	function validateUpdateStrategyForm(data) {
		var isValidate = true;
		if (data.refundable=='') {
			isValidate = false;
			$('#update-strategy-refundable-input').parent().parent('.form-group').addClass('has-error');
		}
		// 如果不允许退票
		if (!data.refundable) {
			return true;
		}
		if (data.ratio=='') {
			isValidate = false;
			$('#update-strategy-ratio-input').parent().parent('.form-group').addClass('has-error');
		}
		if (data.time=='') {
			isValidate = false;
			$('#update-strategy-time-input').parent().parent('.form-group').addClass('has-error');
		}
		return isValidate;
	}

	/**
	 * 如果点击了特定的退票策略，就进入了"修改退票策略"Modal
	 */
	$(document).on('click', '.strategy-item', function (e) {
		var strategy = JSON.parse(e.currentTarget.dataset.strategy);

		$("#update-strategy-refundable-input").val(strategy.refundable ? "是" : "否");
		$("#update-strategy-ratio-input").val(strategy.ratio);
		$("#update-strategy-time-input").val(strategy.time);

		// 如果不允许退票，则不显示ratio、time
		if (!strategy.refundable) {
			$("#update-strategy-ratio-input").parent().parent('.form-group').hide();
			$("#update-strategy-time-input").parent().parent('.form-group').hide();
		}

		initializeUpdateStratrgyModal();

		$("#updateStrategyModal").modal('show');
		$("#updateStrategyModal")[0].dataset.strategyId = strategy.id;
	});

	/**
	 * 当新增退票策略Modal的"是否允许退票"被修改
	 */
	$('#update-strategy-refundable-input').change(function () {
		console.log('"是否允许退票"被修改")');
		if ($('#update-strategy-refundable-input').val() == '是') {
			initializeUpdateStratrgyModal();
			$('#update-strategy-ratio-input').parent().parent('.form-group').show();
			$('#update-strategy-time-input').parent().parent('.form-group').show();
		} else {
			$('#update-strategy-ratio-input').parent().parent('.form-group').hide();
			$('#update-strategy-time-input').parent().parent('.form-group').hide();
			initializeUpdateStratrgyModal();
		}
	});

	function initializeUpdateStratrgyModal() {
		$('#update-strategy-ratio-input').val('');
		$('#update-strategy-time-input').val('');
		$('#update-strategy-ratio-input').parent().parent('.form-group').removeClass('has-error');
		$('#update-strategy-time-input').parent().parent('.form-group').removeClass('has-error');
	}


	// =============================================================================
	// ===============================为退票策略新增电影列表===========================
	// =============================================================================

	/**
	 * 如果点击了"新增电影"按钮，就为指定的退票策略添加电影
	 */
	$(document).on('click', '.add-movie-for-strategy', function (e) {
		e.stopPropagation();

		// 获得可供当前退票策略选择的电影列表
		getSelectableMovies();

		var strategy = JSON.parse(e.currentTarget.parentElement.parentElement.parentElement.dataset.strategy);

		// 在Modal show之前，首先进行初始化清空
		$('#add-selected-movies').empty();

		$('#addMovieForStrategyModal').modal('show');
		$('#addMovieForStrategyModal')[0].dataset.strategyId = strategy.id;
	});

	/**
	 * 如果为指定退票策略新增了电影列表，就在Modal中进行渲染
	 */
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
	 * 在Modal中渲染选择的使用该退票策略的电影列表
	 */
	function renderSelectedMovies() {
		$('#add-selected-movies').empty();

		var moviesDomStr = "";
		selectedMovieNames.forEach(function (movieName) {
			moviesDomStr += "<span class='label label-primary'>" + movieName + "</span>";
		});
		$('#add-selected-movies').append(moviesDomStr);
	}

	/**
	 * 点击"为退票策略新增电影"Modal的"确定"按钮：将选择的要添加的电影列表提交给后端
	 */
	$('#add-movie-for-strategy-form-btn').click(function () {
		// console.log(selectedMovieIds);
		// 如果本次没有选中任何电影，直接返回
		if (selectedMovieIds==undefined||selectedMovieIds.size == 0) {
			$('#addMovieForStrategyModal').modal('hide');
			return;
		}
		var movieIdList = [];
		selectedMovieIds.forEach(function (movieId) {
			movieIdList.push(movieId);
		});
		postRequest(
			'/ticket/refundStrategy/addMovies?refundStrategyId=' + Number(document.getElementById('addMovieForStrategyModal').dataset.strategyId),
			movieIdList,
			function (res) {
				if (res.success) {
					getStrategyList();
					$('#addMovieForStrategyModal').modal('hide');
				} else {
					alert(res.message);
				}
			},
			function (error) {
				alert(JSON.stringify(error));
			}
		);
	});

	// ===================================================================================
	// ===============================为退票策略删除指定的电影列表============================
	// ===================================================================================
	$(document).on('click','.movie-for-strategy',function (e) {
		e.stopPropagation();
		var movieId=Number(e.currentTarget.dataset.movieid);
		var movieName=e.currentTarget.innerText;
		var isToRemove=confirm('确定删除电影："' + movieName+'" ？');
		var strategyId=Number(e.currentTarget.dataset.strategyid);

		// console.log(movieId, movieName, strategyId);
		if (isToRemove) {
			removeSelectedMovie(strategyId,movieId)
		}
	});

	/**
	 * 删除指定退票策略的选中电影
	 */
	function removeSelectedMovie(strategyId,movieId) {
		postRequest(
			'/ticket/refundStrategy/removeMovies?refundStrategyId=' + strategyId,
			[movieId],
			function (res) {
				if (res.success) {
					getStrategyList();
				} else {
					alert(res.message);
				}
			},
			function (error) {
				alert(JSON.stringify(error));
			}
		);
	}

});