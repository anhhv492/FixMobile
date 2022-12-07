app.controller('product-change',function($rootScope,$scope,$http){
    $scope.listProductChange =[];
    $scope.getAllProductChange=function(){
        $http.get('/rest/productchange/getAll').then(resp=>{
            $scope.listProductChange = resp.data;
            console.log('dsadsadsdsadas '+$scope.listProductChange);
        }).catch(error=>{
            console.log(error);
        })
    }
    $scope.getAllProductChange();
});