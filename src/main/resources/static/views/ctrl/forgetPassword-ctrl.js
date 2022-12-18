app.controller("forgetPassword-ctrl",function ($scope, $http){
    $scope.formChangePassMail = {};
    alert('vvvv')
    $scope.changePassword = function (formChangePassMail) {
            $http.post('/rest/admin/accounts/updatePasswordMail?=', $scope.formChangePassMail)
                .then(function (respon) {
                console.log('sessuce ' + respon.data);
            }).catch(error => {
                console.log(error)
            })

    }
})