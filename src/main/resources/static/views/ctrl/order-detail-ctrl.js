app.controller('order-detail-ctrl',function($window,$rootScope,$scope,$http){
    var urlOrder=`http://localhost:8080/rest/user/order/detail`;
    $scope.orderDetails=[];
    $scope.formProductChange={};
    $scope.idCheckBox = {};
    $scope.seLected = [];
    $scope.index = 0;
    $scope.formDetails= {};

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

    $scope.getProductChange=function(formProductChange){
        $http.get(`/rest/user/productchange/findProductChange/${formProductChange.idDetail}`  ,
            token).then(resp=>{
            console.log($scope.formDetails.idDetail)
            $scope.formDetails = resp.data;
            console.log(resp.data)
        }).catch(error=>{
            console.log(error);
        })
    }

    $scope.message = function (mes){
        const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 1000,
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
            timer: 1000,
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
                console.log('đã kiểm tra id là: '+ id)
                return true;
            }
        }
    }


    $scope.uploadFileChange = function(files){
        $scope.files = files;
        console.log($scope.files);
    }
// get accouunt
    $scope.accountActive = {};
    $scope.getAcountActive = function () {
        $http.get(`/rest/user/getAccountActive`, token).then(function (respon){
            $scope.accountActive = respon.data;
            $rootScope.name = $scope.accountActive.username;
            console.log($scope.accountActive.username)
        }).catch(err => {
        })
    }
    $scope.getAcountActive();

    $scope.findPChangeDetails = function (id){}

    $scope.saveProductChangeDetail = function (){
        var form = new FormData();
        angular.forEach($scope.files, function(file) {
            form.append('files', file);
        });
        form.append("orderDetail", $scope.formDetails.idDetail);
        let req = {
            method: 'POST',
            url: '/rest/user/productchange/saveRequest',
            headers: {
                'Content-Type': undefined,
                Authorization: `Bearer `+jwtToken
            },
            data: form,
        }
        $http(req).then(resp=>{
            console.log(resp.data+ " data");
        }).catch(error=>{
            console.log(error);
        })
    }
    // thực hiện yêu cầu đổi trả
    $scope.saveProductChange = function (){
        Swal.fire({
            title: 'Thực hiện gửi yêu cầu đổi trả ?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Xác nhận!'
        }).then((result) => {
                if (result.isConfirmed) {
                       if($scope.files == null){
                           $scope.error('chưa chọn ảnh tình trạng máy');
                           return null;
                       }else  if($scope.formProductChange.quantity > $scope.formDetails.quantity){
                           $scope.error('Số lượng nhập vào không đúng vui lòng nhập lại');
                           return null;
                       }
                       else if($scope.formProductChange.quantity == null){
                           $scope.error('Vui lòng nhập số lượng máy cần đổi');
                           return null;
                       }

                        let timerInterval
                        Swal.fire({
                            title: 'Tạo yêu cầu thành công' + '!',
                            html: 'Vui lòng chờ <b></b> milliseconds.',
                            timer: 1500,
                            timerProgressBar: true,
                            didOpen: () => {
                                Swal.showLoading()
                                var formData = new FormData();
                                angular.forEach($scope.files, function(file) {
                                    formData.append('files', file);
                                });
                                formData.append("note", $scope.formProductChange.note);
                                formData.append("email", $scope.accountActive.email);
                                formData.append("quantity",$scope.formProductChange.quantity);
                                formData.append("account",$scope.accountActive.username);
                                formData.append("orderDetail",$scope.formDetails.idDetail);
                                let req = {
                                    method: 'POST',
                                    url: '/rest/user/productchange/save',
                                    headers: {
                                        'Content-Type': undefined,
                                        Authorization: `Bearer `+jwtToken
                                    },
                                    data: formData
                                }
                                Swal.fire({
                                    title: 'Đang gửi yêu cầu đến admin' + '!',
                                    html: 'Vui lòng chờ <b></b> milliseconds.',
                                    timer: 3500,
                                    timerProgressBar: true,
                                    didOpen: () => {
                                        Swal.showLoading()
                                        const b = Swal.getHtmlContainer().querySelector('b')
                                        timerInterval = setInterval(() => {
                                            b.textContent = Swal.getTimerLeft()
                                        }, 100)
                                    },
                                    willClose: () => {
                                        clearInterval(timerInterval)
                                    }
                                })
                                $http(req).then(response => {
                                    console.log("ddd " + response.data);
                                    $scope.saveProductChangeDetail();
                                    $scope.message("Gửi yêu cầu đổi trả thành công");
                                    $('#staticBackdrop').modal('hide');
                                    $scope.formProductChange={};
                                    $scope.files=null;
                                    $window.location = '/views/index.html#!/order';
                                }).catch(error => {
                                    $scope.error('gửi  yêu cầu đổi trả thất bại');
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


                            }
                        })
                    }
        })
    }
    $scope.getAllByOrder();
});