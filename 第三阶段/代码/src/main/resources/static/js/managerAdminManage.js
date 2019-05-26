$(document).ready(function(){

    getAdminList();

    $("#admin-form-btn").click(function () {
        var formData = getAdminForm();
        if(!validateAdminForm(formData)) {
            return;
        }
        postRequest(
            '/register',
            formData,
            function (res) {
                getAdminList();
                $("#adminModal").modal('hide');
            },
            function (error) {
                alert(error);
            });
    });





    function getAdminForm() {
        return {
            username: $('#admin-username-input').val(),
            password: $('#admin-password-input').val(),
            role: "admin"
        };
    }

    function validateAdminForm(data) {
        var isValidate = true;
        if(!data.username) {
            isValidate = false;
            $('#admin-username-input').parent('.form-group').addClass('has-error');
            alert("用户名不能为空")
        }
        if(!data.password) {
            isValidate = false;
            $('#admin-password-input').parent('.form-group').addClass('has-error');
            alert("密码不能为空")
        }
        return isValidate;
    }

    function validateEditAdminForm(data) {
        var isValidate = true;
        if(!data.username) {
            isValidate = false;
            $('#admin-edit-username-input').parent('.form-group').addClass('has-error');
            alert("用户名不能为空")
        }
        if(!data.password) {
            isValidate = false;
            $('#admin-edit-password-input').parent('.form-group').addClass('has-error');
            alert("密码不能为空")
        }
        return isValidate;
    }

    function getAdminList() {
        getRequest(
            '/admin/all',
            function (res) {
                renderAdminList(res.content);
            },
            function (error) {
                alert(error);
            }
        );
    }

    function renderAdminList(list) {
        $('.admin-on-list').empty();
        var adminDomStr = '';
        list.forEach(function (user) {
            adminDomStr +=
                "<li id='user-" + user.id + "' class='admin-item' data-user='" + JSON.stringify(user) + "'>" +
                "<div class='admin-info'>" +
                "<div class='admin-title'>" +
                "<span class='primary-text'>" + user.username + "</span>" +
                "</div>" +
                "</div>" +
                "</li>";
        });
        $('.admin-on-list').append(adminDomStr);
    }

    $(document).on('click','.admin-item',function (e) {
        console.log("点击成功");
        var user = JSON.parse(e.currentTarget.dataset.user);
        $("#admin-edit-username-input").val(user.username);
        $("#admin-edit-password-input").val(user.password);
        $('#adminEditModal').modal('show');
        $('#adminEditModal')[0].dataset.userId = user.id;
    });


    $('#admin-edit-form-btn').click(function () {
        var form = {
            id: Number($('#adminEditModal')[0].dataset.userId),
            username: $("#admin-edit-username-input").val(),
            password : $("#admin-edit-password-input").val(),
            role: "admin"
        };
        //todo 需要做一下表单验证？
        if (!validateEditAdminForm(form)){
            return
        }

        console.log("运行到了这一行");

        postRequest(
            '/update',
            form,
            function (res) {
                if(res.success){
                    getAdminList();
                    $("#adminEditModal").modal('hide');
                } else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    $("#admin-edit-remove-btn").click(function () {
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