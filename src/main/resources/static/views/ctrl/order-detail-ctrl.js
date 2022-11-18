app.controller('order-detail-ctrl',function($rootScope,$scope,$http,$window){
    var urlOrder=`http://localhost:8080/rest/user/order-detail`;
    $scope.orderDetails=[];
    const jwtToken = localStorage.getItem("jwtToken")
    const token = {
        headers: {
            Authorization: `Bearer `+jwtToken
        }
    }
    $scope.getAllByOrder=function(){
        let id = $rootScope.idOrder;
        $http.get(urlOrder+`/${id}`,token).then(resp=>{
            $scope.orderDetails=resp.data;
        }).catch(error=>{
            console.log(error);
        })
    }
    $scope.getAllByOrder();
});