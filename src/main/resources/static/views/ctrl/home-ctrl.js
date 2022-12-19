app.controller('home-ctrl',function($rootScope,$scope,$http, $window){
    var urlCategory=`http://localhost:8080/rest/guest/category`;
    var urlAccessory=`http://localhost:8080/rest/guest/accessory`;
    var urlImei = `http://localhost:8080/rest/guest/imei`;
    var urlProduct=`http://localhost:8080/rest/guest/product`;
    var urlOneProduct = `http://localhost:8080/rest/guest`;
    var urlAccount = `http://localhost:8080/rest/admin/accounts`;
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
    $scope.getPriceSalePrd=function (x){
        $rootScope.detailProducts[x].priceSale = 0;
        var urlSale=`http://localhost:8080/admin/rest/sale/getbigsale?money=`+$rootScope.detailProducts[x].price+`&idPrd=`+$rootScope.detailProducts[x].idProduct+`&idAcsr=0`;
        $http.get(urlSale, token).then(resp => {
            if(resp.data==''){
                $rootScope.detailProducts[x].priceSale = 0;
            }else {
                if (resp.data.moneySale == null) {
                    $rootScope.detailProducts[x].priceSale = $rootScope.detailProducts[x].price * resp.data.percentSale / 100;
                } else if (resp.data.percentSale == null) {
                    if (resp.data.moneySale > $rootScope.detailProducts[x].price) {
                        $rootScope.detailProducts[x].priceSale = $rootScope.detailProducts[x].price;
                    } else {
                        $rootScope.detailProducts[x].priceSale = resp.data.moneySale;
                    }
                }
            }
            console.log($rootScope.detailProducts)
        }).catch(error => {
            console.log(error)
        })
    }
    $scope.getPriceSaleAcsr=function (x){
        $rootScope.detailAccessories[x].priceSale = 0;
        var urlSale=`http://localhost:8080/admin/rest/sale/getbigsale?money=`+$rootScope.detailAccessories[x].price+`&idPrd=0&idAcsr=`+$rootScope.detailAccessories[x].idAccessory;
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
        }else{
            $http.get(`${urlProduct}/cate-product/${item.idCategory}`, token).then(res=>{
                $rootScope.detailProducts=res.data;
                console.log($rootScope.detailProducts)
                for(var i=0; i<res.data.length;i++){
                    $scope.getPriceSalePrd(i);
                }
            }).catch(err=>{
                $rootScope.detailProducts=null;
                console.log("error",err)
            })
        }
    }

    $scope.test=function (){
        console.log($rootScope.carts)
    }
    $scope.addCart=function(item){
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
                    }else{
                        if(!$scope.accessoryItem){
                            data.qty=1;
                            var money = data.price;
                            var urlSale=`http://localhost:8080/admin/rest/sale/getbigsale?money=`+money+`&idPrd=`+'0'+`&idAcsr=`+data.idAccessory;
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
                        title: 'Thêm thất bại!'
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
                            var urlSale=`http://localhost:8080/admin/rest/sale/getbigsale?money=`+money+`&idPrd=`+data.idProduct+`&idAcsr=0`;
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
                                $scope.messageSuccess("Thêm vào giỏ hàng thành công!");
                            }).catch(error => {
                                console.log(error)
                            })
                        }else{
                            $rootScope.carts.find(
                                it=>it.idProduct===item.idProduct
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

    // $scope.getOneProduct = function (productCode){
    //     $http.get(`${urlOneProduct}/findByProductCode/${productCode.idProduct}`).then(res=>{
    //         $rootScope.productCode = res.data;
    //         console.log(productCode);
    //     }).catch(err=>{
    //         console.log("error",err);
    //     })
    // }
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
     if ($rootScope.account != null){
         $scope.getAcountActive();
     }


    $rootScope.loadLocalStorage();
    $scope.getAcount();

    $scope.ramProducts=[];
    $scope.capacityProducts=[];
    $scope.colorProducts=[];
    $scope.findProductAll={};
    $scope.findProductAll.idCategory=[];
    $scope.findProductAll.idRam=[];
    $scope.findProductAll.idColer=[];
    $scope.findProductAll.idCapacity=[];
    $scope.findProductAll.sortMinMax=0;
    $scope.findProductAll.priceSalemin='';
    $scope.findProductAll.priceSalemax='';
    $scope.findProductAll.search='';

    $scope.getAllRam=function (){
        let url=`/rest/guest/getAllRam`;
        $http.get(url).then(function (respon){
            $scope.ramProducts = respon.data;
        }).catch(err => {
            console.log(err)
        })
    }

    $scope.getAllColor=function (){
        let url=`/rest/guest/getAllColor`;
        $http.get(url).then(function (respon){
            $scope.colorProducts = respon.data;
        }).catch(err => {
            console.log(err)
        })
    }

    $scope.getAllCapacity=function (){
        let url=`/rest/guest/getAllCapacity`;
        $http.get(url).then(function (respon){
            $scope.capacityProducts = respon.data;
        }).catch(err => {
            console.log(err)
        })
    }

    $scope.getPriceSale=function (x,y){
        var urlSale=`http://localhost:8080/admin/rest/sale/getbigsale?money=`+$rootScope.detailProducts[x].price+`&idPrd=`+$rootScope.detailProducts[x].idProduct+`&idAcsr=0`;
        $http.get(urlSale, token).then(resp => {
            if(y==0) {
                if (resp.data == '') {
                    $rootScope.detailProducts[x].priceSale = 0;
                } else {
                    if (resp.data.moneySale == null) {
                        $rootScope.detailProducts[x].priceSale = $rootScope.detailProducts[x].price * resp.data.percentSale / 100;
                    } else if (resp.data.percentSale == null) {
                        if (resp.data.moneySale > $rootScope.detailProducts[x].price) {
                            $rootScope.detailProducts[x].priceSale = $rootScope.detailProducts[x].price;
                        } else {
                            $rootScope.detailProducts[x].priceSale = resp.data.moneySale;
                        }
                    }
                }
            } else if(y==1){

            }
        }).catch(error => {
            console.log(error)
        })
    }
    $scope.findProduct=function (){
        $http.post(`/rest/admin/product/findproduct/0`,$scope.findProductAll).then(function (respon) {
            $rootScope.detailProducts = respon.data.content;
            $scope.totalElements = respon.data.totalElements;
            console.log(respon.data)
            for(var i=0;i<$rootScope.detailProducts.length;i++){
                $scope.getPriceSale(i,0);
            }
        }).catch(err => {
                console.log(err + 'kiixu  lỗi')
            }
        )
    }

    $scope.findAccessory=function (){
        $http.post(`/rest/admin/accessory/findaccessory/0`,$scope.findProductAll).then(function (respon) {
            $rootScope.detailAccessories = respon.data.content;
            $scope.totalElements = respon.data.totalElements;
            console.log(respon.data)
            for(var i=0;i<$rootScope.detailProducts.length;i++){
                $scope.getPriceSale(i,1);
            }
        }).catch(err => {
                console.log(err + 'kiixu  lỗi')
            }
        )
    }

    $scope.checksortMinMax=function (text,check){
        if(text=='Tăng dần'){
            $scope.findProductAll.sortMinMax=0;
        }else{
            $scope.findProductAll.sortMinMax=1;
        }
        if(check==0){
            $scope.findProduct();
        } else if(check==1){
            $scope.findAccessory();
        }

    }
    $scope.checkSelected = function (id, check) {
        let checkseleced=true;
        if (check == 0) {
            for(let i=0;i<$scope.findProductAll.idCategory.length;i++){
                if(id==$scope.findProductAll.idCategory[i]){
                    $scope.findProductAll.idCategory.splice(i, 1);
                    checkseleced=false
                }
            }
            if(checkseleced){
                $scope.findProductAll.idCategory.push(id);
            }
        } else if (check == 1) {
            for(let i=0;i<$scope.findProductAll.idCapacity.length;i++){
                if(id==$scope.findProductAll.idCapacity[i]){
                    $scope.findProductAll.idCapacity.splice(i, 1);
                    checkseleced=false
                }
            }
            if(checkseleced){
                $scope.findProductAll.idCapacity.push(id);
            }
        } else if (check == 2) {
            for(let i=0;i<$scope.findProductAll.idRam.length;i++){
                if(id==$scope.findProductAll.idRam[i]){
                    $scope.findProductAll.idRam.splice(i, 1);
                    checkseleced=false
                }
            }
            if(checkseleced){
                $scope.findProductAll.idRam.push(id);
            }
        }else if (check == 3) {
            for(let i=0;i<$scope.findProductAll.idColer.length;i++){
                if(id==$scope.findProductAll.idColer[i]){
                    $scope.findProductAll.idColer.splice(i, 1);
                    checkseleced=false
                }
            }
            if(checkseleced){
                $scope.findProductAll.idColer.push(id);
            }
        }
    }
    $scope.getAllRam();
    $scope.getAllColor();
    $scope.getAllCapacity();
    $scope.findProduct();
    $scope.findAccessory();
})

