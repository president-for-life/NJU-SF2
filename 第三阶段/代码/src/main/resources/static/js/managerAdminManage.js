$(document).ready(function(){

    getAdminList();

    $("#movie-form-btn").click(function () {
        var formData = getMovieForm();
        if(!validateMovieForm(formData)) {
            return;
        }
        postRequest(
            '/movie/add',
            formData,
            function (res) {
                getMovieList();
                $("#movieModal").modal('hide');
            },
            function (error) {
                alert(error);
            });
    });

    function getMovieForm() {
        return {
            name: $('#movie-name-input').val(),
            startDate: $('#movie-date-input').val(),
            posterUrl: $('#movie-img-input').val(),
            description: $('#movie-description-input').val(),
            type: $('#movie-type-input').val(),
            length: $('#movie-length-input').val(),
            country: $('#movie-country-input').val(),
            starring: $('#movie-star-input').val(),
            director: $('#movie-director-input').val(),
            screenWriter: $('#movie-writer-input').val(),
            language: $('#movie-language-input').val()
        };
    }

    function validateMovieForm(data) {
        var isValidate = true;
        if(!data.name) {
            isValidate = false;
            $('#movie-name-input').parent('.form-group').addClass('has-error');
        }
        if(!data.posterUrl) {
            isValidate = false;
            $('#movie-img-input').parent('.form-group').addClass('has-error');
        }
        if(!data.startDate) {
            isValidate = false;
            $('#movie-date-input').parent('.form-group').addClass('has-error');
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
        list.forEach(function (admin) {
            adminDomStr +=
                "<li class='admin-item card'>" +
                "<div class='admin-info'>" +
                "<div class='admin-title'>" +
                "<span class='primary-text'>" + admin.username + "</span>" +
                "</div>" +
                "</div>" +
                "</li>";
        });
        $('.admin-on-list').append(adminDomStr);
    }
});