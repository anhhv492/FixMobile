app.controller('imayproduct', function($scope, $http, $window,$rootScope) {
     $scope.formImay = {};
     $scope.product= [];
     $rootScope.check = null;

     const jwtToken = localStorage.getItem("jwtToken")
     const token = {
          headers: {
               Authorization: `Bearer `+jwtToken
          }
     }

     $scope.getProduct = function (){
          $http.get(`/rest/admin/product/getAll`,token).then(function(response) {
               $scope.product = response.data;
          }).catch(error=>{
               console.log("error findByCate",error);
          });
     }

     $scope.logOut = function (){
          $window.location.href = "http://localhost:8080/views/index.html#!/login"
          Swal.fire({
               icon: 'error',
               title: 'Vui lòng đăng nhập lại!!',
               text: 'Tài khoản của bạn không có quyền truy cập!!',
          })
     }

     $scope.checkLogin = function () {
          if (jwtToken == null){
               $scope.logOut();
          }else {
               $http.get("http://localhost:8080/rest/user/getRole",token).then(respon =>{
                    if (respon.data.name === "USER"){
                         $scope.logOut();
                    }else if (respon.data.name === "ADMIN"){
                         $rootScope.check = null;
                    }else {
                         $rootScope.check = "OK";
                    }
               })
          }
     }

     $scope.checkLogin();


})