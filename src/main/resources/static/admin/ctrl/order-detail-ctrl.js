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
    $scope.order= {};
    $scope.idDT= null;

    $scope.getAllByOrder=function(){
        let id = $rootScope.idOrder;
        $http.get(urlOrderDetail+'/'+id,token).then(resp=>{
            $scope.orderDetails=resp.data;
        }).catch(error=>{
            console.log(error);
        })
    }
    $scope.getImeis=function (orderDetail) {
        $http.get(urlOrderDetail+'/imei2/'+orderDetail.idDetail,token).then(resp=>{
            $scope.imeis2=resp.data;
        })
    }
    $scope.removeImei=function (idImei) {
        Swal.fire({
            title: 'Bạn có chắc muốn xóa?',
            text: "Xóa không thể khôi phục lại!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                $http.delete(urlOrderDetail+'/imei/remove/'+idImei,token).then(resp=>{
                    $scope.imeis=resp.data;
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
                        title: 'Xóa thành công!'
                    })

                    document.getElementById("closeMd").click();
                }).catch(error=>{
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
                        icon: 'error',
                        title: 'Xóa thất bại!'
                    })
                    console.log('error update',error);
                });
            }
        })
    }
    $scope.imeiPro=function(orderDetail){
        // $scope.selects="";
        $scope.idDT= orderDetail.idDetail;
        $http.get(urlOrderDetail+'/imei/'+orderDetail.product.idProduct,token).then(resp=>{
            $scope.imeis=resp.data;
            $http.get(urlOrderDetail+'/imei2/'+orderDetail.idDetail,token).then(resp=>{
                $scope.imeis2=resp.data;
                if($scope.imeis2.length>=orderDetail.quantity) {
                    $scope.imeis=null;
                }
            })
            // for (let i = 0; i < orderDetail.quantity; i++) {
            //     $scope.selects+=
            //         "<select class=\"form-select mt-2\" ng-model=\"idImei\" ng-change=\"setImei(idD)\">\n" +
            //         " <option ng-repeat=\"imei in imeis\" ng-value=\"imei.idImay\" >{{$index+1}}, {{imei.name}}</option>\n" +
            //         "</select>";
            // }
            // if($scope.imeis==null){
            //     $scope.selects="Sản phẩm này đã hết hàng!";
            // }
            // if($rootScope.orderModel.status>2){
            //     $scope.selects=
            //         '                <div ng-repeat="it in imeis2" ng-if="imeis2">\n' +
            //         '                 {{$index+1}}, {{it.name}}\n' +
            //         '                </div>';
            // }
            // let element = angular.element($('#somediv'));
            // element.html($scope.selects);
            // $compile(element)($scope);
            $scope.nameProduct=resp.data[0].product.name;
            console.log('data',resp.data);
        }).catch(error=>{
            $scope.nameProduct="Đã hết"
            console.log(error);
        })
    }
    $scope.setImei=function () {
        let idDetail=$scope.idDT;
        $http.get(urlOrderDetail+'/imei/add/'+idDetail+"/"+$scope.form.idImei,token)
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
                    title: 'Thêm thành công!'
                })
                $scope.getImeis();

                document.getElementById("closeMd").click();
        }).catch(error=>{
            console.log(error);
        })
    }
    $scope.getAllByOrder();
});