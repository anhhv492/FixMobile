app.controller('view_product_ctrl', function ($scope,$http){
    $scope.productView = [];
    $scope.viewByPrice= [];
    // top 4 sp
    $scope.getTopProduct = function (){
        $http.get(`/rest/admin/product/findByProduct`).then(function(response) {
            $scope.productView = response.data;
            console.log(data);
        }).catch(error=>{
            console.log(error);
        });
    }
    $scope.getTopProductPrice = function (){
        $http.get(`/rest/admin/product/findByPriceExits`).then(function(response) {
            $scope.viewByPrice = response.data;
        }).catch(error=>{
            console.log(error);
        });
    }
    $scope.getTopProduct();
    $scope.getTopProductPrice();
})