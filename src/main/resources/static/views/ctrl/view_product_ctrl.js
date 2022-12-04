app.controller('view_product_ctrl', function ($scope,$http){
    const jwtToken = localStorage.getItem("jwtToken")
    const productId = localStorage.getItem("product");
    const token = {
        headers: {
            Authorization: `Bearer `+jwtToken
        }
    }
    $scope.productView = [];
    $scope.viewByPrice= [];
    $scope.allRam= [];
    $scope.allCapacity = [];
    $scope.allColor = [];
    $scope.oneProduct = {};
    $scope.colorId = 1;
    $scope.capacityId = 1;
    $scope.listProduct =[];

    const getAllRam = "http://localhost:8080/rest/guest/getAllRam";
    const getAllCapacity = "http://localhost:8080/rest/guest/getAllCapacity";
    const getAllColor = "http://localhost:8080/rest/guest/getAllColor";



    $scope.getColorId = function (id){
        $scope.colorId = id;
        console.log($scope.colorId);
        $scope.getPrice();
    }
    $scope.getCapacityId = function (id){
        $scope.capacityId = id;
        console.log($scope.capacityId);
        $scope.getPrice();
    }

    $scope.displayProduct = {
        name : "",
        price : "",
        describe : "",
        imageDefault : "https://res.cloudinary.com/dcll6yp9s/image/upload/v1669970939/fugsyd4nw4ks0vb7kzyd.png",
        imageDefault1 : "https://res.cloudinary.com/dcll6yp9s/image/upload/v1669970939/fugsyd4nw4ks0vb7kzyd.png",
        imageDefault2 : "https://res.cloudinary.com/dcll6yp9s/image/upload/v1669970939/fugsyd4nw4ks0vb7kzyd.png",
        imageDefault3 : "https://res.cloudinary.com/dcll6yp9s/image/upload/v1669970939/fugsyd4nw4ks0vb7kzyd.png",
    }

    // top 4 sp
    $scope.getTopProduct = function (){
        $http.get(`/rest/admin/product/findByProduct`,token).then(function(response) {
            $scope.productView = response.data;
            console.log(response.data);
        }).catch(error=>{
            console.log(error);
        });
    }
    $scope.getTopProductPrice = function (){
        $http.get(`/rest/admin/product/findByPriceExits`,token).then(function(response) {
            $scope.viewByPrice = response.data;
        }).catch(error=>{
            console.log(error);
        });
    }

    $scope.getRam = function (){
        $http.get(getAllRam).then(function (response) {
            $scope.allRam = response.data;
        }).catch(err => {
            console.log(err);
        })
    }
    $scope.getCapacity = function (){
        $http.get(getAllCapacity).then(function (response) {
            $scope.allCapacity = response.data;
            console.log($scope.allCapacity)
        }).catch(err => {
            console.log(err);
        })
    }
    $scope.getColor = function (){
        $http.get(getAllColor).then(function (response) {
            $scope.allColor = response.data;
            console.log($scope.allColor);
        }).catch(err => {
            console.log(err);
        })
    }


    $scope.getColor();
    $scope.getCapacity();




    $scope.getTopProduct();
    $scope.getTopProductPrice();

    
    $scope.getOneProduct = function (id){
        $http.get('/rest/admin/product/findByProductCode?id='+id,token).then(function(response) {
            $scope.oneProduct = response.data;
            $scope.displayProduct.name = $scope.oneProduct.name;
            $scope.displayProduct.price = $scope.oneProduct.price;
            $scope.displayProduct.describe = $scope.oneProduct.note;
            $scope.displayProduct.imageDefault = $scope.oneProduct.images[0].name;
            $scope.displayProduct.imageDefault1 = $scope.oneProduct.images[1].name;
            $scope.displayProduct.imageDefault2 = $scope.oneProduct.images[2].name;
            $scope.displayProduct.imageDefault3 = $scope.oneProduct.images[3].name;

            console.log($scope.oneProduct)
        }).catch(error=>{
            console.log("Sản phẩm chưa có nhiều ảnh!!!");
        });
    }

    if (productId == null){
        $scope.displayProduct();
    }else {
        $scope.getOneProduct(productId);
    }


    $scope.changeImage =  function (id) {
        var main_prodcut_image = document.getElementById('main_product_image');
        var image_product_change = document.getElementById('image_product_change');
        var image_product_change1 = document.getElementById('image_product_change1');
        var image_product_change2 = document.getElementById('image_product_change2');
        var image_product_change3 = document.getElementById('image_product_change3');
        if (id == 1){
            main_prodcut_image.src = image_product_change.src;
        }
        if (id == 2){
            main_prodcut_image.src = image_product_change1.src;
        }
        if (id == 3){
            main_prodcut_image.src = image_product_change2.src;
        }
        if (id == 4){
            main_prodcut_image.src = image_product_change3.src;
        }
    }

    $scope.getPrice = function () {
        $http.get("http://localhost:8080/rest/guest/getProductByNameAndCapacityAndColor?name="
            +$scope.displayProduct.name+
            "&capacity="+ $scope.capacityId
            +"&color="+ $scope.colorId).then(function (respon) {
            $scope.listProduct = respon.data;
            $scope.displayProduct.price = $scope.listProduct[0].price;
            console.log($scope.displayProduct.price);
            console.log($scope.listProduct);
        }).catch(err => {
            console.log("Hết Hàng")
        })
    }

})