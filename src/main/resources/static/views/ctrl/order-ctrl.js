app.controller('order-ctrl',function($rootScope,$scope,$http){
    var urlOrder=`http://localhost:8080/rest/user/order`;
    $scope.orders=[];
    $scope.form={};
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

    $scope.huyDon=async function (idOrder) {
        $scope.form.idOrder = idOrder;

        const {value: text} = await Swal.fire({
            input: 'textarea',
            inputLabel: 'Lý do hủy đơn hàng',
            inputPlaceholder: 'Nhập lý do của bạn ở đây...',
            inputAttributes: {
                'aria-label': 'Type your message here'
            },
            showCancelButton: true
        })
        if (text) {
            $scope.form.note=text;
            Swal.fire({
                title: 'Bạn muốn hủy đơn hàng?',
                text: "Xác nhận không thể khôi phục lại!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, delete it!'
            }).then((result) => {
                if (result.isConfirmed) {
                    $http.post(urlOrder+'/change',$scope.form,token).then(function(response){
                        Swal.fire(
                            'Hủy đơn hàng thành công!',
                            'Click để tiếp tục.',
                            'success'
                        )
                        $scope.getAllByUser();
                    }).catch(error=>{
                        Swal.fire(
                            'Hủy đơn hàng thất bại!',
                            'Click để tiếp tục.',
                            'error'
                        )
                        console.log('error update',error);
                    });
                }
            })
        }
    }
    $scope.getOrder=function(id){
        $rootScope.idOrder=id;
    }
    $scope.getAllByUser();
});