var colors = [
    '#FF6666',
    '#3399FF',
    '#FF9933',
    '#66cccc',
    '#FFCCCC',
    '#9966FF',
    'steelblue'
];

$(document).ready(function() {
    var hallId,
        scheduleDate = formatDate(new Date()),
        schedules = [];

    initSelectAndDate();

    function getSchedules() {//找到对应的排片

        getRequest(
            '/schedule/search?hallId='+hallId+'&startDate='+scheduleDate.replace(/-/g,'/'),
            function (res) {
                schedules = res.content;
                renderScheduleTable(schedules);
             },
            function (error) {
                alert(JSON.stringify(error));
             }
        );
    }

    function renderScheduleTable(schedules){//呈送排片表，在图片上显示排片项目
        $('.schedule-date-container').empty();//清空schedule-date-container类
        $(".schedule-time-line").siblings().remove();//删除schedule-time-line类的所有同胞（不包括自己）
        schedules.forEach(function (scheduleOfDate) {//遍历数组schedules中的内容，当前元素为scheduleOfDate
            $('.schedule-date-container').append("<div class='schedule-date'>"+formatDate(new Date(scheduleOfDate.date))+"</div>");//在类schedule-date-container中增加标准日期
            var scheduleDateDom = $(" <ul class='schedule-item-line'></ul>");//
            $(".schedule-container").append(scheduleDateDom);//增加一行ul
            scheduleOfDate.scheduleItemList.forEach(function (schedule,index) {
                var scheduleStyle = mapScheduleStyle(schedule);
                var scheduleItemDom =$(
                    "<li id='schedule-"+ schedule.id +"' class='schedule-item' data-schedule='"+JSON.stringify(schedule)+"' style='background:"+scheduleStyle.color+";top:"+scheduleStyle.top+";height:"+scheduleStyle.height+"'>"+
                    "<span>"+schedule.movieName+"</span>"+
                    "<span class='error-text'>¥"+schedule.fare+"</span>"+
                    "<span>"+formatTime(new Date(schedule.startTime))+"-"+formatTime(new Date(schedule.endTime))+"</span>"+
                    "</li>");
                scheduleDateDom.append(scheduleItemDom);
            });
        })
    }

    function mapScheduleStyle(schedule) {
        var start = new Date(schedule.startTime).getHours()+new Date(schedule.startTime).getMinutes()/60,
            end = new Date(schedule.endTime).getHours()+new Date(schedule.endTime).getMinutes()/60 ;
        return {
            color: colors[schedule.movieId%colors.length],
            top: 40*start+'px',
            height: 40*(end-start)+'px'
        }
    }

    function initSelectAndDate() {
        $('#schedule-date-input').val(scheduleDate);
        getCinemaHalls();
        getAllMovies();
        
        // 过滤条件变化后重新查询
        $('#hall-select').change (function () {
            hallId=$(this).children('option:selected').val();
            getSchedules();
        });
        $('#schedule-date-input').change(function () {
            scheduleDate = $('#schedule-date-input').val();
            getSchedules();
        });
    }

    function getCinemaHalls() {
        var halls = [];
        getRequest(
            '/hall/all',
            function (res) {
                halls = res.content;
                hallId = halls[0].id;
                halls.forEach(function (hall) {
                    $('#hall-select').append("<option value="+ hall.id +">"+hall.name+"</option>");
                    $('#schedule-hall-input').append("<option value="+ hall.id +">"+hall.name+"</option>");
                    $('#schedule-edit-hall-input').append("<option value="+ hall.id +">"+hall.name+"</option>");
                });
                getSchedules();
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function getAllMovies() {
        getRequest(
            '/movie/all/exclude/off',
            function (res) {
                var movieList = res.content;
                movieList.forEach(function (movie) {
                    $('#schedule-movie-input').append("<option value="+ movie.id +">"+movie.name+"</option>");
                    $("#schedule-edit-movie-input").append("<option value="+ movie.id +">"+movie.name+"</option>");
                });
            },
            function (error) {
                alert(error);
            }
        );
    }

    function validScheForm(form) {

        var currentTime = new Date();
        if (form.startTime==null||form.startTime==""){
            alert("请输入完全开始时间")
            return false
        }
        var startTimeDate = new Date(form.startTime);
        if (form.endTime==null||form.endTime==""){
            alert("请输入完全结束时间")
            return false
        }
        var endTimeDate = new Date(form.endTime);
        var fareNum = form.fare;

        if (startTimeDate <= currentTime){
            alert("开始时间过早（已经早于当前时间）。")
            return false
        }
        if (endTimeDate <= currentTime) {
            alert("结束时间过早（已经早于当前时间）。")
            return false
        }
        if (endTimeDate <= startTimeDate) {
            alert("结束时间过早（已经早于开始时间）。")
            return false
        }
        if(isNaN(fareNum)||fareNum.replace(/(^\s*)|(\s*$)/g,"")==""){
            alert("价钱输入错误");
            return false
        }
        return true
    }

    $(document).on('click','.schedule-item',function (e) {
        var schedule = JSON.parse(e.target.dataset.schedule);
        $("#schedule-edit-hall-input").children('option[value='+schedule.hallId+']').attr('selected',true);
        $("#schedule-edit-movie-input").children('option[value='+schedule.movieId+']').attr('selected',true);
        $("#schedule-edit-start-date-input").val(schedule.startTime.slice(0,16));
        $("#schedule-edit-end-date-input").val(schedule.endTime.slice(0,16));
        $("#schedule-edit-price-input").val(schedule.fare);
        $('#scheduleEditModal').modal('show');
        $('#scheduleEditModal')[0].dataset.scheduleId = schedule.id;
        console.log(schedule);
    });

    
    $('#schedule-form-btn').click(function () {
        var form = {
            hallId: $("#schedule-hall-input").children('option:selected').val(),
            movieId : $("#schedule-movie-input").children('option:selected').val(),
            startTime: $("#schedule-start-date-input").val(),
            endTime: $("#schedule-end-date-input").val(),
            fare: $("#schedule-price-input").val()
        };
        //todo 需要做一下表单验证？
        if (!validScheForm(form)){
            return
        }


        postRequest(
            '/schedule/add',
            form,
            function (res) {
                if(res.success){
                    getSchedules();
                    $("#scheduleModal").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    $('#schedule-edit-form-btn').click(function () {
        var form = {
            id: Number($('#scheduleEditModal')[0].dataset.scheduleId),
            hallId: $("#schedule-edit-hall-input").children('option:selected').val(),
            movieId : $("#schedule-edit-movie-input").children('option:selected').val(),
            startTime: $("#schedule-edit-start-date-input").val(),
            endTime: $("#schedule-edit-end-date-input").val(),
            fare: $("#schedule-edit-price-input").val()
        };
        //todo 需要做一下表单验证？
        if (!validScheForm(form)){
            return
        }


        postRequest(
            '/schedule/update',
            form,
            function (res) {
                if(res.success){
                    getSchedules();
                    $("#scheduleEditModal").modal('hide');
                } else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    $("#schedule-edit-remove-btn").click(function () {
        var r=confirm("确认要删除该排片信息吗")
        if (r) {
            deleteRequest(
                '/schedule/delete/batch',
                {scheduleIdList:[Number($('#scheduleEditModal')[0].dataset.scheduleId)]},
                function (res) {
                    if(res.success){
                        getSchedules();
                        $("#scheduleEditModal").modal('hide');
                    } else{
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        }
    })

});

