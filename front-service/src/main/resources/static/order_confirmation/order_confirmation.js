angular.module('market-front').controller('orderConfirmationController', function ($scope, $http, $location, $localStorage) {

    $scope.loadCart = function () {
        $http ({
            url: 'http://localhost:5555/cart/api/v1/cart/' + $localStorage.webMarketGuestCartId,
            method: 'GET'
        }).then(function (response) {
            console.log(response);
            $scope.cart = response.data;
        });
    };

    $scope.createOrder = function () {
        $http ({
            url: 'http://localhost:5555/core/api/v1/orders',
            method: 'POST',
            data: $scope.orderDetails
        }).then(function (response) {
            alert("Ваш заказ успешно сформирован");
            var orderId = response.data.value;
            $location.path("/order_pay/" + orderId);
        });
    };

    $scope.loadCart();


});