app.controller('viewController', function($scope, $http) {
    $scope.products=[];
    alert('looaodas');
    $scope.getProducts =function (){
        $http.get(`/rest/admin/product/getAll`).then(function(response) {
            $scope.products = response.data;
            // $scope.totalPages = response.data.totalPages;
            // $scope.currentPage = response.data.currentPage;
        }).catch(error=>{
            console.log(error);
        });
    }
    $scope.getProducts();
});