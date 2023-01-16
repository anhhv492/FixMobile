app.controller('home-ctrl',function($rootScope,$scope,$http, $window){
    var urlCategory=`http://localhost:8080/rest/guest/category`;
    var urlAccessory=`http://localhost:8080/rest/guest/accessory`;
    var urlImei = `http://localhost:8080/rest/guest/imei`;
    var urlProduct=`http://localhost:8080/rest/guest/product`;
    var urlOneProduct = `http://localhost:8080/rest/guest`;

    var urlAccount = `http://localhost:8080/rest/user`;
    var apiAccount = `http://localhost:8080/rest/guest`;


    var urlCart = `http://localhost:8080/rest/guest/cart`;
    const callApiOneAccessoryHome = "http://localhost:8080/rest/guest/getOneAccessory";

    const jwtToken = localStorage.getItem("jwtToken")
    const token = {
        headers: {
         Authorization: `Bearer `+jwtToken
        }
    }
    const secret='mum&x4mCrhnjVGwL';
    $scope.cateAccessories=[];
    $scope.cateProducts=[];
    $scope.item= {};
    $rootScope.carts=[];
    $rootScope.qtyCart=0;
    $rootScope.account = jwtToken;
    $rootScope.name="";
    $scope.accountActive= {};
    $scope.accountHome= {};
    $rootScope.check = null;
    $scope.checkCapa=0;
    $scope.checkRam=0;
    $scope.checkColer=0;
    $scope.PrD={};

    $scope.checkLogin = function () {
        if (jwtToken == null){

        }else {
            $http.get("http://localhost:8080/rest/user/getRole",token).then(respon =>{
                if (respon.data.name === "USER"){
                    $rootScope.check = "OK";
                }else {
                    $rootScope.check = null;
                }
            })
        }
    }


    $scope.getAcount = function () {
        $http.get(apiAccount+`/getAccount`, token).then(function (respon){
            $scope.accountHome = respon.data;
        }).catch(err => {
            $scope.accountHome = null;
        })

    }
    $scope.getAcountActive = function () {
        $http.get(urlAccount+`/getAccountActive`, token).then(function (respon){
            $scope.accountActive = respon.data;
            $rootScope.name = $scope.accountActive.username;
            console.log($scope.accountActive.username)
        }).catch(err => {
            $scope.accountActive = null;
            $rootScope.account = null;
        })

    }

    $scope.logoff = function () {
        localStorage.removeItem("jwtToken");
        $rootScope.account = null;
    }
    $scope.getCategories = function(){
        $http.get(`${urlCategory}/getAll`, token).then(res=>{
            res.data.forEach(cate=>{
                if(cate.type){
                    $scope.cateAccessories.push(cate);
                } else {
                    $scope.cateProducts.push(cate);
                }
            });
            console.log("ass",$scope.cateAccessories)
            console.log("pro",$scope.cateProducts)
            console.log("res",res)
        }).catch(err=>{
            console.log("error",err)
        })
    }
    $scope.getPriceSalePrd=function (){
        $scope.PrD.priceSale = 0;
        var urlSale=`http://localhost:8080/rest/guest/sale/getbigsale?money=`+$scope.PrD.price+`&idPrd=`+$scope.PrD.idProduct+`&idAcsr=0`;
        $http.get(urlSale, token).then(resp => {
            if(resp.data==''){
                $scope.PrD.priceSale = 0;
            }else {
                if (resp.data.moneySale == null) {
                    $scope.PrD.priceSale = $scope.PrD.price * resp.data.percentSale / 100;
                } else if (resp.data.percentSale == null) {
                    if (resp.data.moneySale > $scope.PrD.price) {
                        $scope.PrD.priceSale = $scope.PrD.price;
                    } else {
                        $scope.PrD.priceSale = resp.data.moneySale;
                    }
                }
            }
            console.log($scope.PrD)
        }).catch(error => {
            console.log(error)
        })
    }
    $scope.getPriceSaleAcsr=function (x){
        $rootScope.detailAccessories[x].priceSale = 0;
        var urlSale=`http://localhost:8080/rest/guest/sale/getbigsale?money=`+$rootScope.detailAccessories[x].price+`&idPrd=0&idAcsr=`+$rootScope.detailAccessories[x].idAccessory;
        $http.get(urlSale, token).then(resp => {
            if(resp.data!='') {
                if (resp.data.moneySale == null) {
                    $rootScope.detailAccessories[x].priceSale = $rootScope.detailAccessories[x].price * resp.data.percentSale / 100;
                } else if (resp.data.percentSale == null) {
                    if (resp.data.moneySale > $rootScope.detailAccessories[x].price) {
                        $rootScope.detailAccessories[x].priceSale = $rootScope.detailAccessories[x].price;
                    } else {
                        $rootScope.detailAccessories[x].priceSale = resp.data.moneySale;
                    }
                }
            }else{
                $rootScope.detailAccessories[x].priceSale = 0;
            }
        }).catch(error => {
            console.log(error)
        })
    }
    $scope.getDetail=function(item){
        if(item.type){
            $http.get(`${urlAccessory}/cate-access/${item.idCategory}`, token).then(res=>{
                $rootScope.detailAccessories=res.data;
                for(var i=0; i<res.data.length;i++){
                    $scope.getPriceSaleAcsr(i);
                }
            }).catch(err=>{
                $rootScope.detailAccessories=null;
                console.log("error",err)
            })
        }
    }

    $scope.test=function (){
        console.log($rootScope.carts)
    }
    $scope.addCart=function(item){
        if(item.idProduct){
            $http.get(`${urlImei}/amount/${item.idProduct}`).then(res=>{
                if(res.data<=0){
                    $scope.messageError("Hết hàng!")
                    stop();
                }
            })
        }
        $http.get(urlAccount+`/getAccountActive`, token).then(function (respon){
            let json = localStorage.getItem(respon.data.username);
            $rootScope.carts=json? JSON.parse(json):[];
        }).catch(err=>{
            let json = localStorage.getItem("cart");
            $rootScope.carts=json? JSON.parse(json):[];
        })
        $scope.accessoryItem = $rootScope.carts.find(
            it=>it.idAccessory===item.idAccessory
        );

        $scope.productItem = $rootScope.carts.find(
            it=>it.idProduct===item.idProduct
        );
        if(item.category.type){
            $http.get(`${urlAccessory}/${item.idAccessory}`).then(res=>{
                let itemCart = $rootScope.carts.find(
                    it=>it.idAccessory===item.idAccessory
                );
                let data= res.data;
                data.priceSale=0
                $http.get(`${urlAccessory}/amount/${item.idAccessory}`).then(res=>{
                    if(itemCart && itemCart.qty>=res.data) {
                        const Toast = Swal.mixin({
                            toast: true,
                            position: 'top-end',
                            showConfirmButton: false,
                            timer: 2000,
                            timerProgressBar: true,
                            didOpen: (toast) => {
                                toast.addEventListener('mouseenter', Swal.stopTimer)
                                toast.addEventListener('mouseleave', Swal.resumeTimer)
                            }
                        })
                        Toast.fire({
                            icon: 'error',
                            title: 'Hết hàng!'
                        })
                        $scope.checkCapa=0;
                        $scope.checkRam=0;
                        $scope.checkColer=0;
                        $scope.PrD={};
                    }else{
                        if(!$scope.accessoryItem){
                            data.qty=1;
                            var money = data.price;
                            var urlSale=`http://localhost:8080/rest/guest/sale/getbigsale?money=`+money+`&idPrd=`+'0'+`&idAcsr=`+data.idAccessory;
                            var total=0;
                            $http.get(urlSale, token).then(resp => {
                                if(resp.data==''){
                                    total=0
                                }else {
                                    if (resp.data.moneySale == null) {
                                        total = money - money * resp.data.percentSale / 100;
                                    } else if (resp.data.percentSale == null) {
                                        if (resp.data.moneySale > money) {
                                            total = money;
                                        } else {
                                            total = money - resp.data.moneySale;
                                        }
                                    }
                                }
                                data.priceSale = total;
                                data.idSale = resp.data.idSale;
                                $rootScope.carts.push(data);
                                $rootScope.saveLocalStorage();
                                $rootScope.loadLocalStorage();
                                $rootScope.qtyCart++;
                                $scope.messageSuccess("Thêm vào giỏ hàng thành công!");
                            }).catch(error => {
                                console.log(error)
                            })
                        }else{
                            $rootScope.carts.find(
                                it=>it.idAccessory===item.idAccessory
                            ).qty++;
                            $rootScope.saveLocalStorage();
                            $scope.messageSuccess("Thêm vào giỏ hàng thành công!");
                        }
                    }
                }).catch(err=>{
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
                        title: 'Hết hàng!'
                    })
                })
            })
        }else{
            $http.get(`${urlProduct}/${item.idProduct}`,token).then(res=>{
                let itemCart = $rootScope.carts.find(
                    it=>it.idProduct===item.idProduct
                );
                let data= res.data;

                $http.get(`${urlImei}/amount/${item.idProduct}`).then(res=>{
                    if(itemCart&&itemCart.qty>=res.data) {
                        $scope.checkCapa=0;
                        $scope.checkRam=0;
                        $scope.checkColer=0;
                        $scope.PrD={}
                        const Toast = Swal.mixin({
                            toast: true,
                            position: 'top-end',
                            showConfirmButton: false,
                            timer: 2000,
                            timerProgressBar: true,
                            didOpen: (toast) => {
                                toast.addEventListener('mouseenter', Swal.stopTimer)
                                toast.addEventListener('mouseleave', Swal.resumeTimer)
                            }
                        })
                        Toast.fire({
                            icon: 'error',
                            title: 'Hết hàng!'
                        })
                    }else{
                        if(!$scope.productItem){
                            data.qty=1;
                            var money = data.price
                            var total=0;
                            var urlSale=`http://localhost:8080/rest/guest/sale/getbigsale?money=`+money+`&idPrd=`+data.idProduct+`&idAcsr=0`;
                            $http.get(urlSale, token).then(resp => {
                                if(resp.data==''){
                                    total=0;
                                }else {
                                    if (resp.data.moneySale == null) {
                                        total = money - money * resp.data.percentSale / 100;
                                    } else if (resp.data.percentSale == null) {
                                        if (resp.data.moneySale > money) {
                                            total = money;
                                        } else {
                                            total = money - resp.data.moneySale;
                                        }
                                    }
                                }
                                data.priceSale=total;
                                data.idSale = resp.data.idSale;
                                $rootScope.carts.push(data);
                                $rootScope.saveLocalStorage();
                                $rootScope.loadLocalStorage();
                                $rootScope.qtyCart++;
                                $scope.checkCapa=0;
                                $scope.checkRam=0;
                                $scope.checkColer=0;
                                $scope.PrD={}
                                $scope.messageSuccess("Thêm vào giỏ hàng thành công!");
                            }).catch(error => {
                                console.log(error)
                            })
                        }else{
                            $rootScope.carts.find(
                                it=>it.idProduct===item.idProduct
                            ).qty++;
                            $rootScope.saveLocalStorage();
                            $scope.checkCapa=0;
                            $scope.checkRam=0;
                            $scope.checkColer=0;
                            $scope.PrD={}
                            $scope.messageSuccess("Thêm vào giỏ hàng thành công!");
                        }
                }
                }).catch(err=>{
                    $scope.checkCapa=0;
                    $scope.checkRam=0;
                    $scope.checkColer=0;
                    $scope.PrD={};
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
                        title: 'Thêm thất bại!'
                    })
                })
            })
        }
    }
    $rootScope.saveLocalStorage=function(){
        let json = JSON.stringify($rootScope.carts);
        $http.get(urlAccount+`/getAccountActive`, token).then(function (respon){
            localStorage.setItem(respon.data.username,json);
        }).catch(err=>{
            localStorage.setItem("cart",json);
        })

    }

    $rootScope.loadLocalStorage = function(){
        $http.get(urlAccount+`/getAccountActive`, token).then(function (respon){
            let json = localStorage.getItem(respon.data.username);
            $rootScope.carts=json? JSON.parse(json):[];
            $rootScope.loadQtyCart();
        }).catch(err=>{
            let json = localStorage.getItem("cart");
            $rootScope.carts=json? JSON.parse(json):[];
            $rootScope.loadQtyCart();
        })
        for (let i = 0; i < $rootScope.carts.length; i++) {
            $rootScope.carts.find(item=>{
                if(item.idAccessory){
                    $http.get(`${urlAccessory}/accessory/`+item.idAccessory, token).then(res=>{
                        $rootScope.carts[i].price=res.data.price;
                    }).catch(err=>{
                        console.log("error",err)
                    })
                }else{
                    $http.get(`${urlProduct}/product/`+item.idProduct, token).then(res=>{
                        $rootScope.carts[i].price=res.data.price;
                    }).catch(err=>{
                        console.log("error",err)
                    })
                }
            })
        }
    }

    $rootScope.loadQtyCart=function(){
        $rootScope.qtyCart=0;
        if($rootScope.carts){
            $rootScope.carts.forEach(item=>{
                $rootScope.qtyCart+=item.qty;
            });
        }
    }
    $scope.messageSuccess=function (text) {
        const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 2000,
            timerProgressBar: true,
            didOpen: (toast) => {
                toast.addEventListener('mouseenter', Swal.stopTimer)
                toast.addEventListener('mouseleave', Swal.resumeTimer)
            }
        })

        Toast.fire({
            icon: 'success',
            title: text
        })
    }
    $scope.messageError=function (text) {
        const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 2000,
            timerProgressBar: true,
            didOpen: (toast) => {
                toast.addEventListener('mouseenter', Swal.stopTimer)
                toast.addEventListener('mouseleave', Swal.resumeTimer)
            }
        })

        Toast.fire({
            icon: 'error',
            title: text
        })
    }
    $rootScope.productCode = {};

    $scope.overPro=false;
    $scope.overAccess=false;
    $scope.getCategories();
    $rootScope.loadQtyCart();
     $rootScope.productCode = {};
     $scope.getOneProductHome = function (productCode){
         $http.get(`${urlOneProduct}/findByProductCode/${productCode.idProduct}`).then(res=>{
             $rootScope.productCode = res.data;
             localStorage.setItem('product', $rootScope.productCode.idProduct);
             console.log(productCode);
             $window.location.reload();
             $window.href = "#!product"
         }).catch(err=>{
             console.log("error",err);
         })
     }
    $rootScope.accessCodeHome = {}
    $scope.getOneAccessoryHome = function (id) {
        $http.get(callApiOneAccessoryHome+"?id="+id).then(function (respon) {
            $rootScope.accessCodeHome = respon.data;
            console.log($rootScope.accessCodeHome.idAccessory)
            localStorage.removeItem("accessCodeHome");
            localStorage.setItem('accessCodeHome', $rootScope.accessCodeHome.idAccessory);
        })
    }
     if ($rootScope.account != null && jwtToken != null){
         $scope.getAcountActive();
         $rootScope.loadLocalStorage();
     }

    $rootScope.loadLocalStorage();
    $scope.getAcount();

    $scope.detailProduct={}
    $scope.idCheck=undefined;
    $scope.getDetailProduct=function (id){
        console.log(id)
        if(id==0){
            id = localStorage.getItem('idDetail');
            $http.post(`/rest/guest/product/detailproduct/` + id).then(function (respon) {
                $scope.detailProduct = respon.data;
            }).catch(err => {
                    console.log(err, 'kiixu  lỗi')
                }
            )
        }else {
            localStorage.removeItem('idDetail');
            localStorage.setItem('idDetail', id);
            $window.location.href='#!product';
        }
    }
    $scope.checkProduct= function (id, check){
        if(check==0){
            $scope.checkCapa=id;
        }else if(check==1){
            $scope.checkRam=id;
        }else if(check==2){
            $scope.checkColer=id;
        }
        if($scope.checkCapa!=0 && $scope.checkRam!=0 && $scope.checkColer!=0) {
            let url = `/rest/guest/product/getdetailproduct/` + $scope.checkCapa + `/` + $scope.checkRam + `/` + $scope.checkColer;
            $http.post(url).then(function (respon) {
                $scope.PrD = respon.data
                if($scope.PrD!='' ){
                    $scope.checkQuantity = false;
                    $scope.getPriceSalePrd();
                }else if($scope.PrD==''){
                    $scope.checkQuantity = true;
                }
            }).catch(err => {
                    console.log(err, 'kiixu  lỗi')
                }
            )
        }
    }
})

