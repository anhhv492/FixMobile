app.controller('profile-ctl', function ($scope,$http, $window) {
    $scope.accountActive = {};
    $scope.form = {};
    const callApi = "http://localhost:8080/rest/user";
    $scope.addressDefault = {};

    const jwtToken = localStorage.getItem("jwtToken")
    const token = {
        headers: {
            Authorization: `Bearer `+jwtToken
        }
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

    $scope.form = function () {
        alert("Chạy method form")
        var account = angular.copy($scope.form);

        if (account.fullName.trim() == null) {
            alert("Họ và tên không được để trống khoản trống")
            return null;
        }
        if (account.phone.trim() == null) {
            alert("Số điện thoại không được để trống")
            return null;
        }
        if (account.email.trim() == null) {
            alert("Email không được để trống")
            return null;
        }
        return account;

    }

    $scope.getAcountActive = function () {
        $http.get(callApi+`/getAccountActive`, token).then(function (respon){
            $scope.accountActive = respon.data;
            console.log($scope.accountActive.username)
        }).catch(err => {
            Swal.fire({
                icon: 'error',
                text: 'Bạn chưa đăng nhập !!!',
            })
            console.log(err)
            $window.location.href='#!login';
        })
    }

    $scope.getAddress = function () {
        $http.get("http://localhost:8080/rest/user/getAddress", token).then(function (respon){
            $scope.addressDefault = respon.data.addressTake;
            console.log($scope.addressDefault)
        }).catch(err => {
            Swal.fire({
                icon: 'error',
                text: 'Vui lòng thêm địa chỉ!!!',
            })
            console.log(err)
            $window.location.href='#!address';
        })
    }

    $scope.updateAccountActive = function () {
        $http.post(callApi+"/updateAccountActive",$scope.accountActive,token).then(response => {
            const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true,
                didOpen: (toast) => {
                    toast.addEventListener('mouseenter', Swal.stopTimer)
                    toast.addEventListener('mouseleave', Swal.resumeTimer)
                }
            })

            Toast.fire({
                icon: 'success',
                title: 'Cập nhật thành công!',
            })
            console.log($scope.accountActive)

        }).catch(error => {
            const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true,
                didOpen: (toast) => {
                    toast.addEventListener('mouseenter', Swal.stopTimer)
                    toast.addEventListener('mouseleave', Swal.resumeTimer)
                }
            })

            Toast.fire({
                icon: 'error',
                title: 'Cập nhât thất bại!',
            })
            console.log($scope.accountActive)
        });
    };
    
    $scope.upLoadFile = function (file) {
        $scope.files = file;
        console.log($scope.files)
        var formData = new FormData();
        angular.forEach($scope.files, function(image) {
            formData.append('image', image);
        });
        let req = {
            method: 'POST',
            url: '/rest/user/updateImage',
            headers: {
                'Content-Type': undefined,
                Authorization: `Bearer `+jwtToken
                // or  'Content-Type':'application/json'
            },
            data: formData
        }
        let timerInterval
        Swal.fire({
            title: 'Đang sửa ảnh vui lòng chờ!',
            html: 'Vui lòng chờ <b></b> milliseconds.',
            timer: 2000,
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
        });
        $http(req).then(response => {
            console.log("ok " + response.data.image);
            $scope.message("Sửa ảnh thành công");
            $scope.accountActive.image = response.data.image;
        }).catch(error => {
            $scope.error('Sửa ảnh thất bại');
        });
    }

    $scope.doSubmit = function() {
        if($scope.accountActive.username) {
            let timerInterval
            Swal.fire({
                title: 'Đang cập nhật!',
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
                /* Read more about handling dismissals below */
                if (result.dismiss === Swal.DismissReason.timer) {
                    $scope.updateAccountActive();
                    console.log('I was closed by the timer')
                }
            })
        }
    };

    $scope.getAddress();
    $scope.getAcountActive();


});
