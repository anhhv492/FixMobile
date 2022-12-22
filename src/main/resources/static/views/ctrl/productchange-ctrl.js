app.controller('productchange-ctrl',function($rootScope,$scope,$http){
    $scope.orderDetail = {};
    $scope.getProductChange=function(orderDetail){
        $http.get(`/rest/user/productchange/findProductChange/${orderDetail}`  ,
            token).then(resp=>{
            console.log($scope.orderDetail.idDetail);
            $scope.orderDetail = resp.data;
            console.log(resp.data)
        }).catch(error=>{
            console.log(error);
        })
    }
    $scope.getProductChange();

});