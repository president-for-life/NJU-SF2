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
                if (res.success) {
                    getAdminList();
                    $("#adminModal").modal('hide');
                }
                else {
                    alert(res.message);
                }
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
            alert("用户名不能为空")
        }
        if(!data.password) {
            isValidate = false;
            alert("密码不能为空")
        }
        return isValidate;
    }

    function validateEditAdminForm(data) {
        var isValidate = true;
        if(!data.username) {
            isValidate = false;
            alert("用户名不能为空")
        }
        if(!data.password) {
            isValidate = false;
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
        var i = 0;
        list.forEach(function (user) {
            if (i%4 === 0) {
                adminDomStr +=
                    "<div class='container'>";
                if (i !== 0){
                    adminDomStr += "</div>";
                }
            }
            i++;
            adminDomStr +=
                "<div class=\"col-lg-3 col-sm-6\">" +
                "<div class=\"policy-block text-center\">" +
                "<div id='user-" + user.id + "' class='admin-item policy-icon' data-user='" + JSON.stringify(user) + "'>" +
                "<i class=\"icon-male icon-2x\"></i>" +
                "</div>" +
                "<div class=\"policy-text\">" +
                "<h4 class=\"policy-title\">"+ user.username +"</h4>" +
                "<p class=\"policy-desc\">No." + i + "</p>" +
                "</div>" +
                "</div>" +
                "</div>"
            ;
        });
        adminDomStr += "</div>";
        $('.admin-on-list').append(adminDomStr);
    }

    $(document).on('click','.admin-item',function (e) {
        var user = JSON.parse(e.currentTarget.dataset.user);
        $("#admin-edit-username-input").val(user.username);
        $("#admin-edit-password-input").val(user.password);
        $('#adminEditModal').modal('show');
        $('#adminEditModal')[0].dataset.userId = user.id;
    });

    $('#admin-edit-form-btn').click(function () {
        var form = {
            id: Number($('#adminEditModal')[0].dataset.userId),
            role: "admin",
            username: $("#admin-edit-username-input").val(),
            password : $("#admin-edit-password-input").val()
        };
        if (!validateEditAdminForm(form)){
            return
        }

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
        var r = confirm("确认要删除该Admin对象吗");
        if (r) {
            deleteRequest(
                '/delete/' + Number($('#adminEditModal')[0].dataset.userId),
                null,
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
        }
    })
});