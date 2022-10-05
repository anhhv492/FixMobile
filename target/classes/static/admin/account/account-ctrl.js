app.controller("account-ctrl", function ($scope, $http) {
    $scope.items = [];
    $scope.accounts = [];
    $scope.form = {};

    $scope.initialize = function(){
        //load accounts
        $http.get("/rest/accounts").then(resp =>{
            $scope.items = resp.data;
            $scope.items.forEach(item => {
                item.createDate = new Date(item.createDate)
            })

        });
    }

    $scope.initialize();
     //xóa form
     $scope.reset = function(){
        $scope.form ={
            createDate : new Date(),
            image:"cloud-upload.jpg",
            available:true,
        }
    }
})