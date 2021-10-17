angular.module('market-front', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market/';
    let currentPageIndex = 1;

     $scope.loadProducts = function (pageIndex = 1) {
         currentPageIndex = pageIndex;
        $http ({
            url: contextPath + 'api/v1/products',
            method: 'GET',
            params: {
                p: pageIndex
            }
        }).then(function (response) {
                console.log(response);
                $scope.productsPage = response.data;
                $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.productsPage.totalPages);
            });
     };
// //простой (недокрученный по перебору страниц) вариант со страницами
//     $scope.loadProducts = function () {
//         $http.get(contextPath + 'products')
//             .then(function (response) {
//                 console.log(response);
//                 $scope.productsPage = response.data;
//             });
//     };

    //  //базовый вариант,когда выгружали все продукты
    // $scope.loadProducts = function () {
    //     $http.get(contextPath + 'products')
    //         .then(function (response) {
    //             console.log(response);
    //             $scope.products = response.data;
    //         });
    // };


    $scope.createNewProduct=function (){
        $http.post(contextPath + 'api/v1/products',$scope.new_product)
            .then(function successCallback (response){
                $scope.loadProducts(currentPageIndex);
                $scope.new_product=null;
            },function failureCallback (response){
            alert(response.data.message);
        });
    }
    // $scope.prepareProductForUpdate = function(productId){
    //     $http.get(contextPath + 'api/v1/products/'+ productId)
    //         .then(function successCallback (response){
    //             $scope.updated_product=response.data;
    //         },function failureCallback (response){
    //             alert(response.data.message);
    //         });
    // }
    $scope.prepareProductForUpdate = function (productId){
        $http.get(contextPath + 'api/v1/products/'+productId)
            .then(function successCallback (response){
                $scope.updated_product=response.data;
            },function failureCallback (response){
                alert(response.data.message);
            });
    }

    $scope.updateProduct=function (){
        $http.put(contextPath + 'api/v1/products',$scope.updated_product)
            .then(function successCallback (response){
                $scope.loadProducts(currentPageIndex);
                $scope.updated_product=null;
            },function failureCallback (response){
                alert(response.data.message);
            });
    }

    // $scope.updateProduct=function (){
    //     $http.put(contextPath + 'api/v1/products',$scope.updated_product)
    //         .then(function successCallback (response){
    //             $scope.loadProducts(currentPageIndex);
    //             $scope.updated_product=null;
    //         },function failureCallback (response){
    //             alert(response.data.message);
    //         });
    // }
    //
    // $scope.prepareProductForUpdate = function(productId){
    //     $http.get(contextPath + 'api/v1/products/'+ productId)
    //         .then(function successCallback (response){
    //             $scope.updated_product=response.data;
    //         },function failureCallback (response){
    //             alert(response.data.message);
    //         });
    // }

    $scope.generatePagesIndexes=function (startPage,endPage){
        let arr = [];
        for(let i = startPage; i<endPage+1; i++){
            arr.push(i);
        }
        return arr;
    }

     $scope.loadProducts();

});