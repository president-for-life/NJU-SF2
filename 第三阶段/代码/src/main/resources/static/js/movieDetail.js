$(document).ready(function(){

    var movieId = parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]);
    var userId = sessionStorage.getItem('id');
    var isLike = false;

    getMovie();
    if(sessionStorage.getItem('role') === 'admin')
        getMovieLikeChart();

    function getMovieLikeChart() {
       getRequest(
           '/movie/' + movieId + '/like/date',
           function(res){
               var data = res.content,
                    dateArray = [],
                    numberArray = [];
               data.forEach(function (item) {
                   dateArray.push(item.likeTime);
                   numberArray.push(item.likeNum);
               });

               var myChart = echarts.init($("#like-date-chart")[0]);

               // 指定图表的配置项和数据
               var option = {
                   title: {
                       text: '想看人数变化表'
                   },
                   xAxis: {
                       type: 'category',
                       data: dateArray
                   },
                   yAxis: {
                       type: 'value'
                   },
                   series: [{
                       data: numberArray,
                       type: 'line'
                   }]
               };

               // 使用刚指定的配置项和数据显示图表。
               myChart.setOption(option);
           },
           function (error) {
               alert(error);
           }
       );
    }

    function getMovie() {
        getRequest(
            '/movie/'+movieId + '/' + userId,
            function(res){
                var data = res.content;
                isLike = data.islike;
                repaintMovieDetail(data);
            },
            function (error) {
                alert(error);
            }
        );
    }

    function pureGetMovie() {
        getRequest(
            '/movie/'+movieId + '/' + userId,
            function(res){
                var movie = res.content;
                reshowMovieDetail(movie);
            },
            function (error) {
                alert(error);
            }
        );
    }

    function getMovieForm() {
        return {
            id: Number(movieId),
            name: $('#movie-name-input').val(),
            startDate: $('#movie-date-input').val(),
            posterUrl: $('#movie-img-input').val(),
            director: $('#movie-director-input').val(),
            screenWriter: $('#movie-writer-input').val(),
            starring: $('#movie-star-input').val(),
            type: $('#movie-type-input').val(),
            country: $('#movie-country-input').val(),
            language: $('#movie-language-input').val(),
            length: $('#movie-length-input').val(),
            description: $('#movie-description-input').val()
        };
    }


    function repaintMovieDetail(movie) {
        !isLike ? $('.icon-heart').removeClass('error-text') : $('.icon-heart').addClass('error-text');
        $('#like-btn span').text(isLike ? ' 已想看' : ' 想 看');
        reshowMovieDetail(movie);
    }

    function reshowMovieDetail(movie) {
        $('#movie-img').attr('src',movie.posterUrl);
        $('#movie-name').text(movie.name);
        $('#order-movie-name').text(movie.name);
        $('#movie-description').text(movie.description);
        $('#movie-startDate').text(new Date(movie.startDate).toLocaleDateString());
        $('#movie-type').text(movie.type);
        $('#movie-country').text(movie.country);
        $('#movie-language').text(movie.language);
        $('#movie-director').text(movie.director);
        $('#movie-starring').text(movie.starring);
        $('#movie-writer').text(movie.screenWriter);
        $('#movie-length').text(movie.length);
    }

    function showModifyMovie(movie){
        var Date = movie.startDate
        $('#movie-name-input').val(movie.name);
        $('#movie-date-input').val(Date.slice(0,10));
        $('#movie-img-input').val(movie.posterUrl);
        $('#movie-description-input').val(movie.description);
        $('#movie-type-input').val(movie.type);
        $('#movie-length-input').val(movie.length);
        $('#movie-country-input').val(movie.country);
        $('#movie-language-input').val(movie.language);
        $('#movie-director-input').val(movie.director);
        $('#movie-star-input').val(movie.starring);
        $('#movie-writer-input').val(movie.screenWriter);
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

    function getStatus(){

    }

    // user界面才有
    $('#like-btn').click(function () {
        var url = isLike ?'/movie/'+ movieId +'/unlike?userId='+ userId :'/movie/'+ movieId +'/like?userId='+ userId;
        postRequest(
             url,
            null,
            function (res) {
                 isLike = !isLike;
                getMovie();
            },
            function (error) {
                alert(error);
            });
    });

    // admin界面才有

    // alert('交给你们啦，修改时需要在对应html文件添加表单然后获取用户输入，提交给后端，' +
    //     '别忘记对用户输入进行验证。（可参照添加电影&添加排片&修改排片）');
    $("#modify-btn").click(function () {
        getRequest(
            '/movie/'+movieId + '/' + userId,
            function(res){
                var movie = res.content;//获得电影讯息
                showModifyMovie(movie);
            },
            function (error) {
                alert(error);
            }
        );
    });

    $("#movie-form-btn").click(function () {
        var formData = getMovieForm();
        if(!validateMovieForm(formData)) {
            return;
        }
        postRequest(
            '/movie/update',
            formData,
            function (res) {
                pureGetMovie();
                $("#movieModal").modal('hide');
            },
            function (error) {
                alert(error);
            });
    });

    // alert('交给你们啦，修改时需要在对应html文件添加表单然后获取用户输入，提交给后端，' +
    //     '别忘记对用户输入进行验证。（可参照添加电影&添加排片&修改排片）');
    function pullOffMovie(){
        postRequest(
            '/movie/off/batch',
            {movieIdList:[movieId]},
            function (res) {
                if(res.success){
                    alert("成功下架该电影")
                }
                else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    $("#delete-btn").click(function () {
        var r=confirm("确认要下架该电影吗");
        if (r){
            getRequest(
                '/movie/'+movieId + '/' + userId,
                function(res){
                    var status = res.content.status;
                    if (status == 1){
                        alert("该电影已经下架，无法再次下架");
                        return;
                    }
                    pullOffMovie();
                },
                function (error) {
                    alert(error);
                }
            );
        }
    });

});