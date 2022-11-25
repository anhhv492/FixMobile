app.controller('order-admin-detail-ctrl',function($rootScope,$scope,$http,$window){
    var urlOrder=`http://localhost:8080/rest/staff/order/detail`;
    const jwtToken = localStorage.getItem("jwtToken")
    const token = {
        headers: {
            Authorization: `Bearer ` + jwtToken
        }
    }
    $scope.orderDetails=[];
    $scope.imeis=[];
    $scope.nameProduct=null;
    $scope.getAllByOrder=function(){
        let id = $rootScope.idOrder;
        $http.get(urlOrder+'/'+id,token).then(resp=>{
            $scope.orderDetails=resp.data;
        }).catch(error=>{
            console.log(error);
        })
    }
    $scope.imeiPro=function(id){
        $http.get(urlOrder+'/imei/'+id,token).then(resp=>{
            $scope.imeis=resp.data;
            $scope.nameProduct=resp.data[0].product.name;
            console.log('data',resp.data);
        }).catch(error=>{
            console.log(error);
        })
    }
    $scope.getAllByOrder();
});