app.controller('order-admin-ctrl',function($rootScope,$scope,$http,$window){
    var urlOrder=`http://localhost:8080/rest/staff/order`;
    $scope.orders=[];
    $rootScope.idOrder=null;
    $scope.form= {};
    $scope.showUpdate=false;
    $scope.status = [
        {id : '', name : "Thay đổi"},
        {id : 0, name : "Chưa thanh toán"},
        {id : 1, name : "Đã thanh toán"},
        {id : 2, name : "Đang giao hàng"},
        {id : 3, name : "Hoàn tất giao dịch"},
        {id : 4, name : "Hoàn lại đơn"},
    ];
    const jwtToken = localStorage.getItem("jwtToken")
    const token = {
        headers: {
            Authorization: `Bearer ` + jwtToken
        }
    }
    $scope.getAll=function(){
        $http.get(urlOrder,token).then(function(response){
            $scope.orders=response.data;
        }).catch(error=>{
            console.log('error getOrder',error);
        });
    }
    $scope.show=function(){
        $scope.showUpdate=true;

    }
    $scope.getOrder=function(id){
        $rootScope.idOrder=id;
    }
    $scope.updateStatus=function(id){
        let urlUpdate=`http://localhost:8080/rest/staff/order`;
        $scope.showUpdate=false;
        $scope.form.idOrder=id;
        $http.put(urlUpdate,$scope.form,token).then(function(response){
            $scope.getAll();
            $scope.form.status=null;
            console.log('updateStatus');
        }).catch(error=>{
            console.log('error update',error);
        });
    }
    $scope.status.id='';
    $scope.getAll();
});