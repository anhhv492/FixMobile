app.controller('view_product_ctrl', function ($scope,$http){
    const jwtToken = localStorage.getItem("jwtToken")
    const token = {
        headers: {
            Authorization: `Bearer `+jwtToken
        }
    }
    $scope.productView = [];
    $scope.viewByPrice= [];
    $scope.oneProduct = {};
    // top 4 sp
    $scope.getTopProduct = function (){
        $http.get(`/rest/admin/product/findByProduct`,token).then(function(response) {
            $scope.productView = response.data;
            $scope.priceSale=[];
            for(var i=0; i<response.data.length;i++){
                $scope.getSale(response.data[i].price,response.data.idProduct,'')
                console.log($scope.priceSale);
            }
            console.log(response.data);
        }).catch(error=>{
            console.log(error);
        });
    }
    $scope.getTopProductPrice = function (){
        $http.get(`/rest/admin/product/findByPriceExits`,token).then(function(response) {
            $scope.viewByPrice = response.data;
            $scope.priceSale1=[];
            for(var i=0; i<response.data.length;i++){
                $scope.getSale1(response.data[i].price,response.data.idProduct,'')
                console.log($scope.priceSale1+"hihi");
            }
        }).catch(error=>{
            console.log(error);
        });
    }

    $scope.getTopProduct();
    $scope.getTopProductPrice();
    
    $scope.getOneProduct = function (){
        $http.get('/rest/admin/product/findByProductCode',token).then(function(response) {
            $scope.oneProduct = response.data;
            console.log('product : ' + one
            )
        }).catch(error=>{
            console.log(error);
        });
    }

    $scope.getSale=function (money,  idPrd,  idAcsr){
        var urlSale=`http://localhost:8080/admin/rest/sale/getbigsale?money=`+money+`&idPrd=`+idPrd+`&idAcsr=`+idAcsr;
        $http.get(urlSale, token).then(resp => {
            if(resp.data.moneySale == null) {
                $scope.priceSale.push(money - (money * resp.data.percentSale/100));
            }else if(resp.data.percentSale == null){
                $scope.priceSale.push(money - resp.data.moneySale);
            }else{ $scope.priceSale.push(0)}
        }).catch(error => {
            console.log(error + "hahha");
            $scope.priceSale.push(0)
        })
    }
    $scope.getSale1=function (money,  idPrd,  idAcsr){
        var urlSale=`http://localhost:8080/admin/rest/sale/getbigsale?money=`+money+`&idPrd=`+idPrd+`&idAcsr=`+idAcsr;
        $http.get(urlSale, token).then(resp => {
            if(resp.data.moneySale == null) {
                console.log("hihihihihi")
                $scope.priceSale1.push(money - (money * resp.data.percentSale/100));
            }else if(resp.data.percentSale == null){
                console.log(money - resp.data.moneySale)
                $scope.priceSale1.push(money - resp.data.moneySale);
            }else{ $scope.priceSale1.push(0)}
        }).catch(error => {
            console.log(error + "hahha");
            $scope.priceSale1.push(0)
        })
    }
})