app.controller('order-ctrl',function($rootScope,$scope,$http,$filter){
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
    $scope.findByDate=function(){
        let date1= $filter('date')($scope.date1, "yyyy/MM/dd");
        let date2= $filter('date')($scope.date2, "yyyy/MM/dd");
        $http.get(urlOrder+`/date?date1=`+date1+'&&date2='+date2,token).then(function(response){
            $scope.orders=response.data;
        }).catch(error=>{
            console.log('error getOrder',error);
            $scope.messageError('Không tìm thấy đơn hàng!');
        });
    }
    $scope.findByPrice=function(){
        $http.get(urlOrder+`/price?price1=`+$scope.price1+'&&price2='+$scope.price2,token).then(function(response){
            if(response.data){
                $scope.orders=response.data;
            }
        }).catch(error=>{
            console.log('error getOrder',error);
            $scope.messageError('Không tìm thấy đơn hàng!');
        });
    }
    $scope.findByName=function(){
        $http.get(urlOrder+'/name/'+$scope.nameSearch.trim(),token).then(function(response){
            if(response.data){
                $scope.orders=response.data;
            }
        }).catch(error=>{
            console.log('error getOrder',error);
            $scope.messageError('Không tìm thấy đơn hàng!');
        });
    }
    $scope.findByStatus=function(){
        $http.get(urlOrder+`/status/`+$scope.statusChange,token).then(function(response){
            $scope.orders=response.data;
        }).catch(error=>{
            console.log('error getOrder',error);
            $scope.messageError('Không tìm thấy đơn hàng!');
        });
    }
    $scope.getAllUser=function(){
        $http.get(urlOrder+'/usernames',token).then(function(response){
            if(response.data){
                $scope.accs=response.data;
            }
        }).catch(error=>{
            console.log('error getOrder',error);
        });
    }
    $scope.selectByUser=function(){
        $http.get(urlOrder+'/accounts/'+$scope.userSearch,token).then(function(response){
            if(response.data){
                $scope.orders=response.data;
            }
        }).catch(error=>{
            console.log('error getOrder',error);
        });
    }
    $scope.messageSuccess=function (text) {
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
            title: text
        })
    }
    $scope.messageError=function (text) {
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
            title: text
        })
    }
    $scope.getOrder=function(id){
        $rootScope.idOrder=id;
    }
    $scope.getAllByUser();
});