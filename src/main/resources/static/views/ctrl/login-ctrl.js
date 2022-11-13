app.controller('login-ctrl',function($rootScope,$scope,$http,$window){
    $scope.form = {};
    $scope.jwt ;
    const pathAPI = "http://localhost:8080/api/auth/login";



    localStorage.removeItem('jwtToken');
    $scope.onLogin = function () {
        $http.post(pathAPI, $scope.form).then(respon =>{

            Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: 'Đăng nhập thành công',
                showConfirmButton: false,
                timer: 1500
            })
            localStorage.setItem('jwtToken', respon.data.token);
            $scope.jwt = localStorage.getItem('jwtToken')
            $window.location.href = '#!home/index';
        }).catch(error => {
            Swal.fire({
                position: 'top-end',
                icon: 'error',
                title: 'Đăng nhập thất bại',
                showConfirmButton: false,
                timer: 1500
            })
        })
    }

    $scope.logOut= function () {
        localStorage.removeItem('jwtToken');
    }
})