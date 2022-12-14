app.controller('order-admin-ctrl',function($rootScope,$scope,$http,$window,$filter,$timeout){
    var urlOrder=`http://localhost:8080/rest/staff/order`;
    $scope.orders=[];
    $scope.accs=[];
    $rootScope.idOrder=null;
    $rootScope.orderModel= {};
    $scope.form= {};
    $scope.userSearch= null;
    $scope.showUpdate=false;
    $scope.checkHoTen=false;
    $scope.checkNgayMua=false;
    $scope.checkTongGia=false;
    $scope.checkTrangThai=false;
    $scope.status = [
        {id : '', name : "Thay đổi"},
        {id : 0, name : "Chờ xác nhận"},
        {id : 1, name : "Xác nhận"},
        {id : 2, name : "Đang giao hàng"},
        {id : 3, name : "Hoàn tất giao dịch"},
        {id : 4, name : "Hủy đơn"},
        {id : 5, name : "Hoàn trả"},
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
    $scope.getOrder=function(item){
        $rootScope.idOrder=item.idOrder;
        $rootScope.orderModel=item;
    }
    $scope.updateStatus=function(id){
        let urlUpdate=`http://localhost:8080/rest/staff/order`;
        $scope.showUpdate=false;
        $scope.form.idOrder=id;
        Swal.fire({
            title: 'Bạn có chắc muốn đổi trạng thái không?',
            text: "Đổi không thể quay lại!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Xác nhận'
        }).then((result) => {
            if (result.isConfirmed) {
                $http.put(urlUpdate,$scope.form,token).then(function(response){
                    if(response.data){
                        $scope.form.status=null;
                        $scope.getAll();
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
                    }else{
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
                            title: 'Không thể thay đổi trạng thái!'
                        })
                    }
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
                        title: 'Không thể thay đổi trạng thái!'
                    })
                });
            }
        })
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

    $scope.sortPriceUp=function(){
        let temp = $scope.orders[0];
        for (let i = 0 ; i < $scope.orders.length - 1; i++) {
            for (let j = i + 1; j < $scope.orders.length; j++) {
                if ($scope.orders[i].total > $scope.orders[j].total) {
                    temp = $scope.orders[j];
                    $scope.orders[j] = $scope.orders[i];
                    $scope.orders[i] = temp;
                }
            }
        }
    }
    $scope.sortPriceDown=function(){
        let temp = $scope.orders[0];
        for (let i = 0 ; i < $scope.orders.length - 1; i++) {
            for (let j = i + 1; j < $scope.orders.length; j++) {
                if ($scope.orders[i] < $scope.orders[j]) {
                    temp = $scope.orders[j];
                    $scope.orders[j] = $scope.orders[i];
                    $scope.orders[i] = temp;
                }
            }
        }
    }
    $scope.hoTen=function(){
        $scope.checkHoTen=!$scope.checkHoTen;
    }
    $scope.ngayMua=function(){
        $scope.checkNgayMua=!$scope.checkNgayMua;
    }
    $scope.tongGiaTang=function(){
        $scope.checkTongGia=!$scope.checkTongGia;
        $scope.sortPriceUp();
    }
    $scope.tongGiaGiam=function(){
        $scope.checkTongGia=!$scope.checkTongGia;
        $scope.sortPriceDown();
    }
    $scope.trangThai=function(){
        $scope.checkTrangThai=!$scope.checkTrangThai;
    }
    $scope.status.id='';
    $scope.getAll();
    $scope.getAllUser();
});