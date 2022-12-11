app.controller('home-ctrl',function($rootScope,$scope,$http, $window){
    var urlCategory=`http://localhost:8080/rest/guest/category`;
    var urlAccessory=`http://localhost:8080/rest/guest/accessory`;
    var urlImei = `http://localhost:8080/rest/guest/imei`;
    var urlProduct=`http://localhost:8080/rest/guest/product`;
    var urlOneProduct = `http://localhost:8080/rest/guest`;
    var urlAccount = `http://localhost:8080/rest/admin/accounts`;

     const jwtToken = localStorage.getItem("jwtToken")
     const token = {
             headers: {
                 Authorization: `Bearer `+jwtToken
             }
         }
    $scope.cateAccessories=[];
    $scope.cateProducts=[];
    $scope.item= {};
    $rootScope.carts=[];
    $rootScope.qtyCart=0;
    $rootScope.account = jwtToken;
    $rootScope.name="";
    $scope.accountActive= {};

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
    $scope.getDetail=function(item){
        if(item.type){
            $http.get(`${urlAccessory}/cate-access/${item.idCategory}`, token).then(res=>{
                $rootScope.detailAccessories=res.data;
                $scope.priceSale=[];
                for(var i=0; i<res.data.length;i++){
                    $scope.getSale(res.data[i].price,'',res.data.idAccessory)
                }
            }).catch(err=>{
                $rootScope.detailAccessories=null;
                console.log("error",err)
            })
        }else{
            $http.get(`${urlProduct}/cate-product/${item.idCategory}`, token).then(res=>{
                $rootScope.detailProducts=res.data;
            }).catch(err=>{
                $rootScope.detailProducts=null;
                console.log("error",err)
            })
        }
    }
    $rootScope.carts2=[];
    $scope.addCart2=function(item){
        $rootScope.carts2=$rootScope.carts;
        $rootScope.carts.push(item);
        $rootScope.qtyCart++;
        $rootScope.saveLocalStorage();
        $rootScope.loadLocalStorage();

    }
    $scope.addCart=function(item){
        console.log("qty",$scope.qtyCart);
        let json = localStorage.getItem($rootScope.name);
        $rootScope.carts=json? JSON.parse(json):[];
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
                            title: 'Số lượng sản phẩm không đủ!'
                        })
                    }else{
                        if(!$scope.accessoryItem){
                            data.qty=1;
                            $rootScope.carts.push(data);
                        }else{
                            $scope.accessoryItem.qty++;
                        }
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
                            icon: 'success',
                            title: 'Thêm thành công!'
                        })
                        $rootScope.saveLocalStorage();
                        $rootScope.qtyCart++;
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
                            title: 'Số lượng sản phẩm không đủ!'
                        })
                    }else{
                        if(!$scope.productItem){
                            data.qty=1;
                            $rootScope.carts.push(data);
                        }else{
                            $scope.productItem.qty++;
                        }
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
                        icon: 'success',
                        title: 'Thêm thành công!'
                    })
                    $rootScope.saveLocalStorage();
                    $rootScope.qtyCart++;
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
        localStorage.setItem($rootScope.name,json);
    }
    $rootScope.loadLocalStorage = function(){
        let json = localStorage.getItem($rootScope.name);
        $rootScope.carts=json? JSON.parse(json):[];
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

    $rootScope.productCode = {};
    $scope.getOneProduct = function (productCode){
        $http.get(`${urlOneProduct}/findByProductCode/${productCode.idProduct}`).then(res=>{
            $rootScope.productCode = res.data;
            console.log(productCode);
        }).catch(err=>{
            console.log("error",err);
        })
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
    $scope.overPro=false;
    $scope.overAccess=false;
    $scope.getCategories();
    $rootScope.loadQtyCart();
    $rootScope.loadLocalStorage();
    $scope.getAcountActive();

})

