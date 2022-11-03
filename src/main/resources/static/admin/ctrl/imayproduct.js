app.controller('imayproduct', function($scope, $http) {
     $scope.formImay = {};
     $scope.product= [];

     $scope.getProduct = function (){
          $http.get(`/rest/admin/product/getAll`).then(function(response) {
               $scope.product = response.data;
          }).catch(error=>{
               console.log("error findByCate",error);
          });
     }
})