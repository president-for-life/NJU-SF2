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
                "    <div class='activity-coupon primary-bg'>" +
                "        <span class='gray-text'>"+strategy.description+"</span>" +
                "        <span class='title'>价格："+strategy.price+"</span>" +
                "        <span class='title'>满"+strategy.targetAmount+"减<span class='error-text title'>" + strategy.discountAmount+"</span></span>" +
                "    </div>" +
                "</div>";
        });
        $(".content-member").append(strategiesDomStr);
    }
});