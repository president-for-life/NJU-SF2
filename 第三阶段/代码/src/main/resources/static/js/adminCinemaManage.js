$(document).ready(function() {

    var canSeeDate = 0;

    getCanSeeDayNum();
    getCinemaHalls();

    function getCinemaHalls() {//方法得到影院的影厅
        var halls = [];
        getRequest(
            '/hall/all',//获取来源地址
            function (res) {
                halls = res.content;//从后端res中提取获取数据
                renderHall(halls);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function validateHallForm(data) {
        var isValidate = true;
        if(!data.name) {
            isValidate = false;
            alert("影厅名不能为空")
        }
        if(!data.column) {
            isValidate = false;
            alert("列数不能为空")
        }
        if(!data.row) {
            isValidate = false;
            alert("行数不能为空")
        }
        if(data.row <= 0){
            isValidate = false;
            alert("非法行数")
        }
        if(data.column <= 0){
            isValidate = false;
            alert("非法列数")
        }
        return isValidate;
    }

    function validateEditHallForm(data) {
        var isValidate = true;
        if(!data.name) {
            isValidate = false;
            alert("影厅名不能为空")
        }
        if(!data.column) {
            isValidate = false;
            alert("列数不能为空")
        }
        if(!data.row) {
            isValidate = false;
            alert("行数不能为空")
        }
        if(data.row <= 0){
            isValidate = false;
            alert("非法行数")
        }
        if(data.column <= 0){
            isValidate = false;
            alert("非法列数")
        }
        return isValidate;
    }

    function renderHall(halls){
        $('#hall-card').empty();
        var hallDomStr = "";
        var container = "大";
        halls.forEach(function (hall) {
            var seat = "";
            for(var i =0;i<hall.row;i++){
                var temp = "";
                for(var j =0;j<hall.column;j++){
                    temp+="<div class='cinema-hall-seat'></div>";
                }
                seat+= "<div>"+temp+"</div>";
            }
            if (hall.row * hall.column > 50){
                container = "(大)";
            }
            else {
                container = "(小)";
            }
            var hallDom =
                "<div id='hall-" + hall.id + "' class='hall-item' data-hall='" + JSON.stringify(hall) + "'>" +
                "<div>" +
                "<span class='cinema-hall-name'>"+ hall.name +"</span>" + "<span>"+container+"</span>" +
                "<span class='cinema-hall-size'>"+ hall.column +'*'+ hall.row +"</span>" +
                "</div>" +
                "<div class='cinema-seat'>" + seat +
                "</div>" +
                "</div>";
            hallDomStr+=hallDom;
        });
        $('#hall-card').append(hallDomStr);
    }


    $(document).on('click','.hall-item',function (e) {
        var hall = JSON.parse(e.currentTarget.dataset.hall);
        $("#hall-edit-name-input").val(hall.name);
        $("#hall-edit-column-input").val(hall.column);
        $("#hall-edit-row-input").val(hall.row);
        $('#hallEditModal').modal('show');
        $('#hallEditModal')[0].dataset.hallId = hall.id;
    });



    function getCanSeeDayNum() {
        getRequest(
            '/schedule/view',
            function (res) {
                canSeeDate = res.content;
                $("#can-see-num").text(canSeeDate);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    $('#canview-modify-btn').click(function () {
       $("#canview-modify-btn").hide();
       $("#canview-set-input").val(canSeeDate);
       $("#canview-set-input").show();
       $("#canview-confirm-btn").show();
    });

    $('#canview-confirm-btn').click(function () {
        var dayNum = $("#canview-set-input").val();
        // 验证一下是否为数字
        postRequest(
            '/schedule/view/set',
            {day:dayNum},
            function (res) {
                if(res.success){
                    getCanSeeDayNum();
                    canSeeDate = dayNum;
                    $("#canview-modify-btn").show();
                    $("#canview-set-input").hide();
                    $("#canview-confirm-btn").hide();
                } else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    })

    function getHallForm() {
        return {
            name: $('#hall-name-input').val(),
            column: Number($('#hall-column-input').val()),
            row: Number($('#hall-row-input').val()),
        };
    }

    $("#hall-form-btn").click(function () {
        var formData = getHallForm();
        if(!validateHallForm(formData)) {
            return;
        }
        console.log(formData);
        postRequest(
            '/hall/add',
            formData,
            function (res) {
                if (res.success){
                    getCinemaHalls();
                    $("#hallModal").modal('hide');
                }
                else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(error);
            });
    });

    $('#hall-edit-form-btn').click(function () {
        var form = {
            id: Number($('#hallEditModal')[0].dataset.hallId),
            name: $('#hall-edit-name-input').val(),
            column: Number($('#hall-edit-column-input').val()),
            row: Number($('#hall-edit-row-input').val()),
        };
        if (!validateEditHallForm(form)){
            return
        }
        postRequest(
            '/hall/update',
            form,
            function (res) {
                if(res.success){
                    if (res.success){
                        getCinemaHalls();
                        $("#hallEditModal").modal('hide');
                    }
                    else{
                        alert(res.message);
                    }
                } else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });
});