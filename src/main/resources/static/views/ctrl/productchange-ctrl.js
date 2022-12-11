app.controller('productchange-ctrl',function($rootScope,$scope,$http){
    $scope.orderDetail = {};
    $scope.getProductChange=function(formProductChange){
        $http.get(`/rest/productchange/findProductChange/${formProductChange.idDetail}`  ,
            token).then(resp=>{
            console.log($scope.orderDetail.idDetail)
            $scope.orderDetail = resp.data;
            console.log(resp.data)
        }).catch(error=>{
            console.log(error);
        })
    }
});