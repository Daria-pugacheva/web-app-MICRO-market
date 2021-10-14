angular.module('market-front').controller('orderConfirmationController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8189/market/';

    $scope.loadCart = function () {
        $http ({
            url: contextPath + 'api/v1/cart/' + $localStorage.webMarketGuestCartId,
            method: 'GET'
        }).then(function (response) {
            console.log(response);
            $scope.cart = response.data;
        });
    };

    $scope.createOrder = function () {
        $http ({
            url: contextPath + 'api/v1/orders',
            method: 'POST',
            data: $scope.orderDetails
        }).then(function (response) {
            alert("Ваш заказ успешно сформирован");
            $location.path("/");
        });
    };

    $scope.loadCart();


});