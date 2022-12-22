app.controller('changePassword-ctrl',function($rootScope,$scope,$http,$window){
    $scope.form = {
        oldPassword:'',
        password: '',
        password_verify: ''
    }
    $scope.inputType = 'password';
    const callApi = "http://localhost:8080/rest/user/updatePassword";

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

    $scope.hideShowPassword = function(){
        if ($scope.inputType == 'password')
            $scope.inputType = 'text';
        else
            $scope.inputType = 'password';
    };

    $scope.changePassword = function () {
        if ($scope.form.password_verify === $scope.form.password){
            $http.post(callApi,$scope.form,token).then(function (respon) {
               if (respon.data == true){
                   Swal.fire(
                       'Đổi mật khẩu thành công!',
                       'Vui lòng đăng nhập lại!',
                       'success'
                   )
                    $scope.logOut();
               }else {
                   Swal.fire({
                       icon: 'error',
                       title: 'Lỗi',
                       text: 'Mật khẩu cũ không đúng!!',
                   })
               }
            })
        }else {
            Swal.fire({
                icon: 'error',
                title: 'Lỗi',
                text: 'Vui lòng nhập mật khẩu trùng nhau!!',
            })
        }
    }

    $scope.logOut= function () {
        $window.location.href = '#!login';
        $rootScope.account=null;
        localStorage.removeItem('jwtToken');
    }

})