app.controller("forgetPassword-ctrl", function ($scope, $http,$window) {
    $scope.formChangePassMail = {
        email: ''
    };
    $scope.message = function (mes){
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
            icon: 'success',
            title: mes,
        })
    }
    $scope.changePassword = function (email) {
        $http.get(`/rest/guest/accounts/updatePasswordMail/${email}`)
            .then(function (respon) {
                $scope.message('Mật khẩu đã gửi về email của quý khách vui lòng kiêm tra');
                console.log('sessuce ' + respon.data);
                $window.location.href = '#!login';
            }).catch(error => {
            console.log('lỗi ' +error);
        })
    }
})