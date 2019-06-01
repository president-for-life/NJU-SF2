$(document).ready(function() {

    getStrategies();

    function getStrategies() {
        getRequest(
            '/vip/strategy/get/all',
            function (res) {
                var strategies = res.content;
                renderStrategies(strategies);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function renderStrategies(strategies) {
        $(".content-member").empty();
        var strategiesDomStr = "";

        strategies.forEach(function (strategy) {
            strategiesDomStr +=
                "<div class='strategy-container'>" +
                "    <div class='strategy-item primary-bg' " + "id='strategy-" + strategy.id + "' data-strategy='" + JSON.stringify(strategy) + "'>" +
                "        <span class='gray-text'>"+strategy.description+"</span>" +
                "        <span class='title'>价格："+strategy.price+"</span>" +
                "        <span class='title'>满"+strategy.targetAmount+"减<span class='error-text title'>" + strategy.discountAmount+"</span></span>" +
                "    </div>" +
                "</div>";
        });
        $(".content-member").append(strategiesDomStr);
    }

    // 发布会员卡充值优惠策略
    $("#strategy-form-btn").click(function() {
        var form = {
            description: $("#member-description-input").val(),
            price: $("#member-price-input").val(),
            targetAmount: $("#member-target-input").val(),
            discountAmount: $("#member-discount-input").val()
        };

        postRequest(
            '/vip/strategy/add',
            form,
            function (res) {
                if(res.success){
                    getStrategies();
                    $("#editMemberModal").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    // 修改会员卡充值优惠策略
    $(document).on('click','.strategy-item',function (e) {
        var strategy = JSON.parse(e.currentTarget.dataset.strategy);

        $("#member-edit-description-input").val(strategy.description);
        $("#member-edit-price-input").val(strategy.price);
        $("#member-edit-target-input").val(strategy.targetAmount);
        $("#member-edit-discount-input").val(strategy.discountAmount);

        $('#editMemberModal').modal('show');

        $('#editMemberModal')[0].dataset.strategyId = strategy.id;
    });

    $('#strategy-edit-form-btn').click(function () {
        var form = {
            id: Number($('#editMemberModal')[0].dataset.strategyId),
            price: $("#member-edit-price-input").val(),
            description: $("#member-edit-description-input").val(),
            targetAmount: $("#member-edit-target-input").val(),
            discountAmount : $("#member-edit-discount-input").val()
        };
        console.log(form);

        postRequest(
            '/vip/strategy/update',
            form,
            function (res) {
                if(res.success){
                    // TODO
                    getStrategies();
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