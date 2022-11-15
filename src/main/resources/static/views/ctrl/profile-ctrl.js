app.controller('profile-ctl', function ($scope,$http, $window) {
    $scope.accountActive = {};
    $scope.form = {};
    const callApi = "http://localhost:8080/rest/admin/accounts";
    $scope.addressDefault = {};

    const jwtToken = localStorage.getItem("jwtToken")
    const token = {
        headers: {
            Authorization: `Bearer `+jwtToken
        }
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
        $http.get("http://localhost:8080/rest/admin/accounts/getAddress", token).then(function (respon){
            $scope.addressDefault = respon.data.addressTake;
            console.log($scope.addressDefault)
        }).catch(err => {
            Swal.fire({
                icon: 'error',
                text: 'Bạn chưa đăng nhập !!!',
            })
            console.log(err)
            $window.location.href='#!login';
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
