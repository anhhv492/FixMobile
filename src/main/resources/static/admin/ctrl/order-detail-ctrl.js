app.controller('order-admin-detail-ctrl',function($rootScope,$scope,$http,$compile){
    var urlOrderDetail=`http://localhost:8080/rest/staff/order/detail`;
    var urlOrder=`http://localhost:8080/rest/staff/order`;
    const jwtToken = localStorage.getItem("jwtToken")
    const token = {
        headers: {
            Authorization: `Bearer ` + jwtToken
        }
    }
    $scope.orderDetails=[];
    $scope.imeis=[];
    $scope.imeis2=[];
    $scope.nameProduct=null;
    $scope.form= {};
    $scope.getAllByOrder=function(){
        let id = $rootScope.idOrder;
        $http.get(urlOrderDetail+'/'+id,token).then(resp=>{
            $scope.orderDetails=resp.data;
        }).catch(error=>{
            console.log(error);
        })
    }
    $scope.imeiPro=function(orderDetail){
        $http.get(urlOrderDetail+'/imei/'+orderDetail.product.idProduct,token).then(resp=>{
            $scope.imeis=resp.data;
            $http.get(urlOrderDetail+'/imei2/'+orderDetail.idDetail,token).then(resp=>{
                $scope.imeis2=resp.data;
            })
            // for (let i = 0; i < orderDetail.quantity; i++) {
            //     $scope.selects+=
            //         "<select class=\"form-select mt-3\" ng-model=\"idImei\" ng-change=\"setImei(idD)\">\n" +
            //         " <option ng-repeat=\"imei in imeis\" ng-value=\"imei.idImay\" >{{$index+1}}, {{imei.name}}</option>\n" +
            //         "</select>";
            // }
            // $scope.selects+=
            //     "<hr class='mt-2'>" +
            //     "<div ng-repeat=\"it in imeis2\" ng-if='imeis2'>\n" +
            //     " {{$index+1}}, {{it.name}}" +
            //     "</div>" ;
            // let element = angular.element($('#somediv'));
            // element.html($scope.selects);
            // $compile(element)($scope);
            $scope.nameProduct=resp.data[0].product.name;
            console.log('data',resp.data);
        }).catch(error=>{
            console.log(error);
        })
    }
    $scope.setImei=function (idDetail,idImeiOld) {
        $http.get(urlOrderDetail+'/imei/add/'+idDetail+"/"+idImeiOld+"/"+$scope.form.idImei,token)
            .then(resp=>{
                $scope.imeis2=resp.data;
                const Toast = Swal.mixin({
                    toast: true,
                    position: 'top-end',
                    showConfirmButton: false,
                    timer: 2000,
                    timerProgressBar: true,
                    didOpen: (toast) => {
                        toast.addEventListener('mouseenter', Swal.stopTimer)
                        toast.addEventListener('mouseleave', Swal.resumeTimer)
                    }
                })

                Toast.fire({
                    icon: 'success',
                    title: 'Thay đổi thành công!'
                })
                document.getElementById("closeMd").click();
        }).catch(error=>{
            console.log(error);
        })
    }
    $scope.getAllByOrder();
});