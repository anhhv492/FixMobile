app.controller('view_accessory_ctrl', function ($scope,$http){
    $scope.listTop4 = [];
    $scope.oneAccessory = {};
    $scope.accessoryAddCart = {};
    const callApiTop = "http://localhost:8080/rest/guest/getTop4";
    const callApiOneAccessory = "http://localhost:8080/rest/guest/getOneAccessory";

    $scope.displayAccessory = {
        name : "",
        price : "",
        describe : "",
        imageDefault : "https://res.cloudinary.com/dcll6yp9s/image/upload/v1669970939/fugsyd4nw4ks0vb7kzyd.png",
    }

    $scope.getTop4 = function () {
        $http.get(callApiTop).then(function (respon) {
            $scope.listTop4 = respon.data;
        })
    }
    $scope.getOneAccessory = function (id) {
        $http.get(callApiOneAccessory+"?id="+id).then(function (respon) {
            $scope.oneAccessory = respon.data;
            $scope.displayAccessory.name = $scope.oneAccessory.name;
            $scope.displayAccessory.price = $scope.oneAccessory.price;
            $scope.displayAccessory.describe = $scope.oneAccessory.note;
            $scope.displayAccessory.imageDefault = $scope.oneAccessory.image;
            $scope.accessoryAddCart = respon.data;
            console.log($scope.oneAccessory + id)
        })
    }

    $scope.getTop4();
})