app.controller('order-admin-detail-ctrl',function($rootScope,$scope,$http,$window){
    var urlOrder=`http://localhost:8080/rest/user/order-detail`;
    $scope.orderDetails=[];
    $scope.getAllByOrder=function(){
        let id = $rootScope.idOrder;
        $http.get(urlOrder+`/${id}`).then(resp=>{
            $scope.orderDetails=resp.data;
        }).catch(error=>{
            console.log(error);
        })
    }
    $scope.getAllByOrder();
});