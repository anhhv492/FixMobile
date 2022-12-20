app.controller('product-change',function($rootScope,$scope,$http){
    $scope.listProductChange =[];
    $scope.getOneProduct = {};
    $scope.objectInt = 5;
    $scope.seLected = [];
    $scope.message = function (mes){
        const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 3500,
            timerProgressBar: true,
            didOpen: (toast) => {
                toast.addEventListener('mouseenter', Swal.stopTimer)
                toast.addEventListener('mouseleave', Swal.resumeTimer)
            }
        })
        Toast.fire({
            icon: 'success',
            title: mes,
        })
    }
    $scope.error =  function (err){
        const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 1500,
            timerProgressBar: true,
            didOpen: (toast) => {
                toast.addEventListener('mouseenter', Swal.stopTimer)
                toast.addEventListener('mouseleave', Swal.resumeTimer)
            }
        })

        Toast.fire({
            icon: 'error',
            title: err,
        })
    }

    $scope.getAllProductChange=function(){
        $http.get('/rest/productchange/getAll').then(resp=>{
            $scope.listProductChange = resp.data;
            console.log('dsadsadsdsadas '+$scope.listProductChange);
        }).catch(error=>{
            console.log(error);
        })
    }
    $scope.imeis = [];
    $scope.getAllProductChangeDetails=function(id){
        $http.get(`/rest/productchange/getPrChangeDetails/${id}`).then(resp=>{
            $scope.getOneProduct = resp.data;
            console.log('dsadsadsdsadas '+resp.data);
            $http.get(`/rest/staff/order/detail/imei2/${ $scope.getOneProduct.orderDetail.idDetail}`).then(res=>{
                $scope.imeis=res.data;
                console.log('dsadsadsdsadas '+ $scope.imeis[0].name);
            }).catch(err=>{
                console.log(err);
            })
        }).catch(error=>{
            console.log(error);
        })

    }

    $scope.getAllProductChange();
    $scope.checkSelected= function (id){
        console.log('sdsadasadsa '+id)
        var check = true;
        for(var i=0;i<$scope.seLected.length;i++){
            if($scope.seLected[i]==id){
                check = false;
                console.log('đã kt id ' + id)
                $scope.seLected.splice(i,1);
            }
        }
        if (check){
            $scope.seLected.push(id);
        }
    }
    $scope.checkSelect=function (id){
        for (var i = 0; i < $scope.seLected.length; i++) {
            if ($scope.seLected[i] == id) {
                console.log('đã kiểm tra id là: '+ id.idChange);
                console.log('đã kiểm tra id là: '+ id.status);
                return true;
            }
        }
    }

    $scope.postRequest = function(){
        if($scope.seLected.length == 0){
            $scope.error("Hãy chọn yêu cầu để xác nhận");
            return null;
        }else{
            let timerInterval
            Swal.fire({
                title: 'Đang gửi thông báo cho khách hàng!',
                html: 'Vui lòng chờ <b></b> milliseconds.',
                timer: 4000,
                timerProgressBar: true,
                didOpen: () => {
                    Swal.showLoading();
                    $http.post(`/rest/admin/productchange/comfirmRequest`,$scope.seLected).then(response => {
                        console.log("ddd " + response.data);
                        $scope.message("Đã xác nhận yêu cầu");
                        $scope.seLected=[];
                        $scope.getAllProductChange();
                    }).catch(error => {
                        $scope.error('lỗi có bug rồi');
                    });
                    const b = Swal.getHtmlContainer().querySelector('b')
                    timerInterval = setInterval(() => {
                        b.textContent = Swal.getTimerLeft()
                    }, 100)
                },
                willClose: () => {
                    clearInterval(timerInterval)
                }
            }).then((result) => {
                if (result.dismiss === Swal.DismissReason.timer) {
                    console.log('I was closed by the timer')
                }
            })

        }

    }

    $scope.putRequest = function(){
        if($scope.seLected.length  == 0){
            $scope.error("Vui lòng chọn yêu cầu hủy");
            return null;
        }
        $http.post(`/rest/admin/productchange/cancelRequest`,$scope.seLected).then(response => {
            console.log("ddd " + response.data);
            $scope.message("Đã hủy yêu cầu");
            $scope.seLected=[];
            $scope.getAllProductChange();
        }).catch(error => {
            $scope.error('lỗi có bug rồi');
        });

    }



});