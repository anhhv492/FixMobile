app.controller('order-detail-ctrl',function($rootScope,$scope,$http){
    var urlOrder=`http://localhost:8080/rest/user/order/detail`;

    $scope.orderDetails=[];
    $scope.formProductChange={};
    // $scope.saleadd = {};
    $scope.idCheckBox = {};
    $scope.seLected = [];
    $scope.index = 0;
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
    //$scope.files={};

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

    $scope.saveProductChange = function (){

        Swal.fire({
            title: 'Bạn có chắc muốn trả máy : ',
            text: "Đổi trả hàng có thể mất thêm phí !",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if($scope.seLected.length == 0){
                $scope.error('Phải chọn sản phẩm cần trả');
                return null;
            }else if($scope.files == null){
                $scope.error('Chửa chọn hình ảnh tình trạng sản phẩm');
                return null;
            }
            else {
                if (result.isConfirmed) {
                    let timerInterval
                    Swal.fire({
                        title: 'Tạo yêu cầu thành công' +'!',
                        html: 'Vui lòng chờ <b></b> milliseconds.',
                        timer: 1500,
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
                    }).then((result) => {
                        if (result.dismiss === Swal.DismissReason.timer) {

                            var formData = new FormData();
                            angular.forEach($scope.files, function(file) {
                                formData.append('files', file);
                                console.log(file);
                            });
                            formData.append("note",$scope.formProductChange.note);
                            formData.append("email",$scope.formProductChange.email);

                            let req = {
                                method: 'POST',
                                url: '/rest/productchange/save',
                                headers: {
                                    'Content-Type': undefined,
                                },
                                data: formData
                            }
                            Swal.fire({
                                title: 'Đang gửi yêu cầu đến admin' +'!',
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
                                $scope.message("Gửi yêu cầu đổi trả thành công");
                                // $scope.refresh();

                            }).catch(error => {
                                $scope.error('gửi  yêu cầu đổi trả thất bại');
                                console.log('I was closed by the timer'+ formData)
                            });

                        }
                        let details = angular.copy($scope.seLected);
                        $http.post('/rest/productchange/saveRequest',details).then(resp =>{
                            console.log('đã post '+ details);
                        }).catch(error => {
                            console.log('I was closed by the timer'+ formData)
                        });

                    })

                }

            }
        })
    }
    $scope.getAllByOrder();
});