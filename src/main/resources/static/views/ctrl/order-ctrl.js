app.controller('order-ctrl',function($rootScope,$scope,$http,$window){
    var urlOrder=`http://localhost:8080/rest/user/order`;
    $scope.orders=[];
    $rootScope.idOrder=null;
    const jwtToken = localStorage.getItem("jwtToken")
    const token = {
        headers: {
            Authorization: `Bearer `+jwtToken
        }
    }
    $scope.getAllByUser=function(){
        $http.get(urlOrder,token).then(function(response){
            $scope.orders=response.data;
        }).catch(error=>{
            console.log('error getOrder',error);
        });
    }
    $scope.getOrder=function(id){
        $rootScope.idOrder=id;
    }
    $scope.getAllByUser();
});