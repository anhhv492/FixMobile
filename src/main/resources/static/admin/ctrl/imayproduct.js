app.controller('imayproduct', function($scope, $http) {
     $scope.formImay = {};
     $scope.product= [];

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


})