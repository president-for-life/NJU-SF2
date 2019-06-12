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
                "    <div class='strategy-item' " + "id='strategy-" + strategy.id + "' data-strategy='" + JSON.stringify(strategy) + "'>" +
                "        <span class='title'>NJU-<span class='SE title'>SE</span>-VIP</span>" +
                "        <span class='big'>__</span>" +
                "        <span class='big'>价格：<span class='amount'>"+strategy.price+"</span></span>" +
                "        <span class='big'>满<span class='amount'>"+strategy.targetAmount+"</span>减<span class='discount amount'>" + strategy.discountAmount+"</span></span>" +
                "        <span class='gray-text' style='text-align: right'>"+strategy.description+"</span>" +
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
                    $("#addMemberModal").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    // 更改（修改、删除）会员卡充值优惠策略
    $(document).on('click','.strategy-item',function (e) {
        var strategy = JSON.parse(e.currentTarget.dataset.strategy);

        $("#member-edit-description-input").val(strategy.description);
        $("#member-edit-price-input").val(strategy.price);
        $("#member-edit-target-input").val(strategy.targetAmount);
        $("#member-edit-discount-input").val(strategy.discountAmount);

        $('#editMemberModal').modal('show');
        $('#editMemberModal')[0].dataset.strategyId = strategy.id;
    });

    // 修改会员卡充值优惠策略
    $('#strategy-edit-form-btn').click(function () {
        var form = {
            id: Number($('#editMemberModal')[0].dataset.strategyId),
            price: $("#member-edit-price-input").val(),
            description: $("#member-edit-description-input").val(),
            targetAmount: $("#member-edit-target-input").val(),
            discountAmount : $("#member-edit-discount-input").val()
        };

        postRequest(
            '/vip/strategy/update',
            form,
            function (res) {
                if(res.success){
                    getStrategies();
                    $("#editMemberModal").modal('hide');
                } else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    // 删除会员卡充值优惠策略
    $("#admin-edit-remove-btn").click(function () {
        var r = confirm("确认要删除该充值优惠策略吗？");
        if (r) {
            deleteRequest(
                '/vip/strategy/remove?strategyId=' + Number($('#editMemberModal')[0].dataset.strategyId),
                null,
                function (res) {
                    if(res.success){
                        getStrategies();
                        $("#editMemberModal").modal('hide');
                    } else{
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        }
    });
});