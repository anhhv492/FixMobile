app.controller('cart-ctrl', function ($rootScope, $scope, $http, $window,$timeout) {
    var urlOrder = `http://localhost:8080/rest/guest/order`;
    var urlImei = `http://localhost:8080/rest/guest/imei`;
    var urlAccessory = `http://localhost:8080/rest/guest/accessory`;
    var urlProduct= `http://localhost:8080/rest/guest/product`;
    var urlOrderDetail = `http://localhost:8080/rest/guest/order/detail`;
    var urlAccount = `http://localhost:8080/rest/user`;
    var urlShippingOder = `http://localhost:8080/rest/user/address/getShipping-order`;
    const jwtToken = localStorage.getItem("jwtToken")
    const token = {
        headers: {
            Authorization: `Bearer ` + jwtToken
        }
    }
    $scope.addressAccount = {};
    $scope.info = {};
    $scope.checkBuy = null;
    $scope.categories = {};
    $scope.cart = {};
    $scope.checkAmt = null;
    $scope.to_district_id = "";
    $scope.to_ward_code = ""
    $scope.ship = "";
    $scope.counts = function () {
        return $rootScope.carts
            .map(item => item.qty)
            .reduce((total, qty) => total + qty, 0);
    }
    $scope.amounts = function () {
        return $rootScope.carts
            .map(item => item.qty * item.price)
            .reduce((total, qty) => total += qty, 0);
    }
    $scope.remove = function (item) {
        if (item.idAccessory > -1) {
            var index = $rootScope.carts.findIndex(it => it.idAccessory === item.idAccessory)
        } else if (item.idProduct > -1) {
            var index = $rootScope.carts.findIndex(it => it.idProduct === item.idProduct)
        }

        Swal.fire({
            title: 'Xóa: ' + item.name + ' khỏi giỏ hàng?',
            text: "Sau khi xóa không thể khôi phục lại!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Xác nhận'
        }).then((result) => {
            if (result.isConfirmed) {
                $rootScope.carts.splice(index, 1);
                $rootScope.qtyCart -= item.qty;
                $rootScope.saveLocalStorage();
                $rootScope.loadLocalStorage();
                $scope.loadMoneyShip();
                $scope.priceSaleByVocher=0;
                $scope.getVoucherApply=0;
                $scope.totalPriceSale();
                $scope.totalPrice();
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
                    title: 'Xóa thành công!'
                })
                $window.location.href = '#!cart';
                console.log('I was closed by the timer')
            }
        })
    }
    $scope.update = function (item) {
        let index = null;
        if (item.idAccessory > -1) {
            index = $rootScope.carts.findIndex(it => it.idAccessory === item.idAccessory)
            $http.get(`${urlAccessory}/amount/${item.idAccessory}`).then(res => {
                if (item.qty > res.data) {
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
                    item.qty = res.data;
                    $rootScope.saveLocalStorage();
                    $rootScope.loadLocalStorage();
                    $scope.loadMoneyShip();
                } else {
                    $rootScope.carts[index].qty = item.qty;
                    $rootScope.saveLocalStorage();
                    $rootScope.loadLocalStorage();
                    $scope.loadMoneyShip();
                    $scope.priceSaleByVocher=0;
                    $scope.getVoucherApply=0;
                    $scope.totalPriceSale();
                    $scope.totalPrice();
                }
            }).catch(err => {
                console.log(err)
            })
        } else if (item.idProduct > -1) {
            index = $rootScope.carts.findIndex(it => it.idProduct === item.idProduct)
            $http.get(`${urlImei}/amount/${item.idProduct}`).then(res => {
                if (item.qty > res.data) {
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
                    item.qty = res.data;
                    $rootScope.saveLocalStorage();
                    $rootScope.loadLocalStorage();
                    $scope.loadMoneyShip();
                } else {
                    $rootScope.carts[index].qty = item.qty;
                    $rootScope.saveLocalStorage();
                    $rootScope.loadLocalStorage();
                    $scope.loadMoneyShip();
                    $scope.priceSaleByVocher=0;
                    $scope.getVoucherApply=0;
                    $scope.totalPriceSale();
                    $scope.totalPrice();
                }
            }).catch(err => {
                console.log(err)
            })
        }
        if ($rootScope.carts[index].qty <= 0) {
            $rootScope.carts[index].qty = 1;
            $rootScope.saveLocalStorage();
            $rootScope.loadLocalStorage();
            $scope.loadMoneyShip();
            $scope.priceSaleByVocher=0;
            $scope.getVoucherApply=0;
            $scope.totalPriceSale();
            $scope.totalPrice();
            console.log('I was closed by the timer')
        }
    }
    $scope.raise = function (item) {
        if (item.idAccessory > -1) {
            let index = $rootScope.carts.findIndex(it => it.idAccessory === item.idAccessory)
            $http.get(`${urlAccessory}/amount/${item.idAccessory}`).then(res => {
                if (item.qty >= res.data) {
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
                    item.qty = res.data;
                    $rootScope.saveLocalStorage();
                    $rootScope.loadLocalStorage();
                }else{
                    $rootScope.qtyCart++;
                    $rootScope.carts[index].qty++;
                    $rootScope.saveLocalStorage();
                    $rootScope.loadLocalStorage();
                    $scope.loadMoneyShip();
                    $scope.priceSaleByVocher=0;
                    $scope.getVoucherApply=0;
                    $scope.totalPriceSale();
                    $scope.totalPrice();
                }
            }).catch(err => {
                console.log(err)
            })
        } else if (item.idProduct > -1) {
            let index = $rootScope.carts.findIndex(it => it.idProduct === item.idProduct)
            $http.get(`${urlImei}/amount/${item.idProduct}`).then(res => {
                if (item.qty >= res.data) {
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
                    item.qty = res.data;
                    $rootScope.saveLocalStorage();
                    $rootScope.loadLocalStorage();
                    $scope.loadMoneyShip();
                } else {
                    $rootScope.carts[index].qty++;
                    $rootScope.qtyCart++;
                    $rootScope.saveLocalStorage();
                    $rootScope.loadLocalStorage();
                    $scope.priceSaleByVocher=0;
                    $scope.getVoucherApply=0;
                    $scope.totalPriceSale();
                    $scope.totalPrice();
                    $scope.loadMoneyShip();
                    console.log('add', item.qty)
                }
            }).catch(err => {
                console.log(err)
            })
        }
    }
    $scope.reduce = function (item) {
        if (item.idAccessory > -1) {
            var index = $rootScope.carts.findIndex(it => it.idAccessory === item.idAccessory)
        } else if (item.idProduct > -1) {
            var index = $rootScope.carts.findIndex(it => it.idProduct === item.idProduct)
        }
        $rootScope.carts[index].qty--;
        if ($rootScope.carts[index].qty <= 0) {
            $rootScope.saveLocalStorage();
            $rootScope.loadLocalStorage();
            $scope.loadMoneyShip();
            $scope.priceSaleByVocher=0;
            $scope.getVoucherApply=0;
            $scope.totalPriceSale();
            $scope.totalPrice();
            $rootScope.carts.splice(index, 1);
            $window.location.href = '#!cart';
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
                title: 'Xóa thành công!'
            })
            console.log('I was closed by the timer')
        }
        $rootScope.saveLocalStorage();
        $rootScope.loadLocalStorage();
        $scope.loadMoneyShip();
        $rootScope.qtyCart--;
        console.log('add', item.qty)
        $scope.priceSaleByVocher=0;
        $scope.getVoucherApply=0;
        $scope.totalPriceSale();
        $scope.totalPrice();
    }
    $scope.totalPrice = function () {
        let total = 0;
        $rootScope.carts.forEach(item => {
            total += item.qty * item.price;
        })
        return total;
    }
    $scope.totals = function () {
        if($scope.totalPriceSale() !=0){
            return $scope.totalPriceSale()-$scope.priceSaleByVocher;
        }else{
            return $scope.totalPrice()-$scope.priceSaleByVocher;
        }

    }
    $scope.totalShip = function () {
        let ship = 50000;
        return ship;
    }
    $scope.refresh = function () {
        $rootScope.carts = [];
        localStorage.clear();
    }
    $scope.checkAmount=function (){
        $rootScope.carts.forEach(
            item=>{
                if(item.idAccessory){
                    $http.get(`${urlAccessory}/${item.idAccessory}`,token).then(res=>{
                        if(item.qty>res.data.quantity){
                            Swal.fire(
                                'Rất tiếc, quý khách chậm mất rồi!',
                                'Sản phẩm '+item.name+' không đủ số lượng!',
                                'error'
                            )
                            let index = $rootScope.carts.findIndex(it => it.idAccessory === item.idAccessory)
                            $rootScope.carts.splice(index, 1);
                            $rootScope.saveLocalStorage();
                            $rootScope.loadLocalStorage();
                            $scope.loadMoneyShip();
                            $scope.checkAmt=false;
                        }else{
                            $scope.checkAmt=true;
                        }
                    }).catch(err=>{
                        console.log(err)
                    })
                }else if(item.idProduct){
                    $http.get(`${urlProduct}/${item.idProduct}`,token).then(res=>{
                        $http.get(`${urlImei}/amount/${item.idProduct}`,token).then(res=>{
                            if(item.qty>=res.data){
                                Swal.fire(
                                    'Rất tiếc, quý khách chậm mất rồi!',
                                    'Sản phẩm '+item.name+' không đủ số lượng!',
                                    'error'
                                )
                                let index = $rootScope.carts.findIndex(it => it.idProduct === item.idProduct)
                                $rootScope.carts.splice(index, 1);
                                $rootScope.saveLocalStorage();
                                $rootScope.loadLocalStorage();
                                $scope.loadMoneyShip();
                                $scope.checkAmt=false;
                            }else {
                                $scope.checkAmt=true;
                            }
                        }).catch(err=>{
                            console.log(err)
                        })
                    })
                }
            }
        )
    }
    $scope.checkAmt=true;
    $scope.buyCart = function () {
        let check=false;
        if($scope.getVoucherApply!=0){
            var urladdsaleapply=`/rest/user/sale/addsaleapply?idSale=${$scope.getVoucherApply}`;
            $http.post(urladdsaleapply,$scope.getVoucherApply, token).then(res => {
                check=true;
            }).catch(error => {
                check=false;
                swal.fire({
                    icon: 'error',
                    showConfirmButton: false,
                    title: error.data.message,
                    timer: 5000
                });
            })
        }else{
            check=true;
        }
        if(check==true) {
            $rootScope.carts.forEach(
                item => {
                    if (item.idAccessory) {
                        $http.get(`${urlAccessory}/${item.idAccessory}`, token).then(res => {
                            if (item.qty > res.data.quantity) {
                                Swal.fire(
                                    'Rất tiếc, quý khách chậm mất rồi!',
                                    'Sản phẩm ' + item.name + ' không đủ số lượng!',
                                    'error'
                                )
                                let index = $rootScope.carts.findIndex(it => it.idAccessory === item.idAccessory)
                                $rootScope.carts.splice(index, 1);
                                $rootScope.saveLocalStorage();
                                $rootScope.loadLocalStorage();
                                $scope.loadMoneyShip();
                                $scope.checkAmt = false;
                            } else {
                                $scope.checkAmt = true;
                            }
                        }).catch(err => {
                            console.log(err)
                        })
                    } else if (item.idProduct) {
                        $http.get(`${urlProduct}/${item.idProduct}`, token).then(res => {
                            $http.get(`${urlImei}/amount/${item.idProduct}`, token).then(res => {
                                if (item.qty > res.data) {
                                    Swal.fire(
                                        'Rất tiếc, quý khách chậm mất rồi!',
                                        'Sản phẩm ' + item.name + ' không đủ số lượng!',
                                        'error'
                                    )
                                    let index = $rootScope.carts.findIndex(it => it.idProduct === item.idProduct)
                                    $rootScope.carts.splice(index, 1);
                                    $rootScope.saveLocalStorage();
                                    $rootScope.loadLocalStorage();
                                    $scope.loadMoneyShip();
                                    $scope.checkAmt = false;
                                } else {
                                    $scope.checkAmt = true;
                                }
                            }).catch(err => {
                                console.log(err)
                            })
                        })
                    }
                }
            )
            if ($scope.checkAmt) {
                $scope.getShippingOder();
                if (!$rootScope.account) {
                    $window.location.href = '/login';
                } else {
                    Swal.fire({
                        title: 'Xác nhận thanh toán?',
                        text: "Xác nhận thanh toán để mua hàng!",
                        icon: 'info',
                        showCancelButton: true,
                        confirmButtonColor: '#3085d6',
                        cancelButtonColor: '#d33',
                        confirmButtonText: 'Xác nhận!'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            if ($scope.checkBuy) {
                                let price = ($scope.totals() / 24865).toFixed(2)
                                $http({
                                    url: `http://localhost:8080/pay`,
                                    method: 'POST',
                                    data: price, token,
                                    transformResponse: [
                                        function (data) {
                                            return data;
                                        }
                                    ]
                                }).then(res => {
                                    $scope.linkPaypal = res.data;
                                    $scope.cart.personTake = $scope.addressAccount.personTake;
                                    $scope.cart.phoneTake = $scope.addressAccount.phoneTake;
                                    $scope.cart.address = $scope.addressAccount.addressDetail + ", " + $scope.addressAccount.addressTake;
                                    $scope.cart.total = $scope.totals() + $scope.ship;
                                    $scope.cart.status = 0;
                                    $scope.cart.statusBuy = 1;
                                    $scope.cart.moneyShip = $scope.ship;
                                    $scope.cart.type = false;
                                    $http.post(urlOrder + '/add', $scope.cart, token).then(res => {
                                        if (res.data) {
                                            $http.post(urlOrderDetail + '/add', $rootScope.carts, token).then(res => {

                                                console.log("orderDetail", res.data)
                                            }).catch(err => {
                                                swal.fire({
                                                    icon: 'error',
                                                    showConfirmButton: false,
                                                    title: err.data.message,
                                                    timer: 5000
                                                });
                                            })
                                            $window.location.href = $scope.linkPaypal;
                                        } else {
                                            Swal.fire(
                                                'Thanh toán thất bại!',
                                                '',
                                                'error'
                                            )
                                        }
                                    }).catch(err => {
                                        Swal.fire(
                                            'Số tiền quá lớn!',
                                            '',
                                            'error'
                                        )
                                        console.log("err order", err)
                                    })
                                }).catch(err => {
                                    Swal.fire(
                                        'Thanh toán thất bại!',
                                        '',
                                        'error'
                                    )
                                    console.log("error buy cart", err)
                                })

                            } else {
                                $scope.cart.personTake = $scope.addressAccount.personTake;
                                $scope.cart.phoneTake = $scope.addressAccount.phoneTake;
                                $scope.cart.address = $scope.addressAccount.addressDetail + ", " + $scope.addressAccount.addressTake;
                                $scope.cart.total = $scope.totals() + $scope.ship;
                                $scope.cart.status = 0;
                                $scope.cart.statusBuy = 0;
                                $scope.cart.moneyShip = $scope.ship;
                                $scope.cart.type = false;
                                $http.post(urlOrder + '/add', $scope.cart, token).then(res => {
                                    if (res.data) {
                                        $http.post(urlOrderDetail + '/add', $rootScope.carts, token).then(res => {
                                            $window.location.href = '/views/cart/buy-cod-success.html';
                                        }).catch(err => {
                                            swal.fire({
                                                icon: 'error',
                                                showConfirmButton: false,
                                                title: err.data.message,
                                                timer: 5000
                                            });
                                        })
                                    }

                                }).catch(err => {
                                    Swal.fire(
                                        'Thanh toán thất bại!',
                                        '',
                                        'error'
                                    )
                                    console.log("err order", err)
                                })
                            }
                        }

                    })
                }
            }
        }
    }
    $scope.checkBuyPaypal = function () {
        $scope.checkBuy = true;
    }
    $scope.checkBuyCOD = function () {
        $scope.checkBuy = false;
    }

    $scope.getAddressAcountActive = function () {
        if ($rootScope.account != null) {
            $http.get(urlAccount + "/getAddress", token).then(function (respon) {
                $scope.addressAccount = respon.data;
                $scope.to_district_id = $scope.addressAccount.districtId;
                $scope.to_ward_code = $scope.addressAccount.wardId;
                $scope.getShippingOder();
                console.log($scope.to_district_id, $scope.to_ward_code)
                console.log($scope.addressDefault)

            }).catch(err => {
                Swal.fire({
                    icon: 'error',
                    text: 'Vui lòng thêm địa chỉ!!!',
                })
                console.log(err)
                $window.location.href = '#!address';
            })
        }
    }
    $scope.getShippingOder = function () {
        $http.get(urlShippingOder + "?from_district_id=1542&service_id=53320&to_district_id="
            + $scope.to_district_id + "&to_ward_code=" + $scope.to_ward_code
            + "&weight=200&insurance_value=" + $scope.totalPrice(), token).then(function (respon) {

            $scope.ship = respon.data.body.data.total;
            console.log(respon.data.body.data.total)
        })
        $http.get(urlShippingOder + "?from_district_id=1542&service_id=53321&to_district_id="
            + $scope.to_district_id + "&to_ward_code=" + $scope.to_ward_code
            + "&weight=200&insurance_value=" + $scope.totalPrice(), token).then(function (respon) {
            $scope.ship = respon.data.body.data.total;
            console.log(respon.data.body.data.total)
        })
        $http.get(urlShippingOder + "?from_district_id=1542&service_id=53322&to_district_id="
            + $scope.to_district_id + "&to_ward_code=" + $scope.to_ward_code
            + "&weight=200&insurance_value=" + $scope.totalPrice(), token).then(function (respon) {
            $scope.ship = respon.data.body.data.total;
            console.log(respon.data.body.data.total)
        })
    }
    $scope.loadMoneyShip= function () {
        $timeout(function () {
            $scope.getShippingOder();
        }, 2000);
    }
    $scope.getAddressAcountActive();
    $scope.loadMoneyShip();
    $rootScope.loadQtyCart();
    $rootScope.loadLocalStorage();
    $scope.listVoucherSale=[];
    $scope.getVoucherSale=function (money){
        let url = `http://localhost:8080/rest/user/sale/getvoucher?money=`+money
        $http.post(url, $rootScope.carts, token).then(res => {
            $scope.listVoucherSale=res.data;
        }).catch(err => {
            console.log(err)
        })
    }
    $scope.getVoucherApply=0;
    $scope.priceSaleByVocher=0;
    $scope.getSaleApply=function(x){
        $scope.getVoucherApply=x;
        let urlsale = `http://localhost:8080/rest/user/sale/getsale/`+$scope.getVoucherApply;
        $http.get(urlsale, token).then(res => {
            var price=0;
            if(res.data.typeSale==1 || res.data.typeSale==4 || (res.data.typeSale==3 && res.data.userType==1)){
               let urlsaledetail=`http://localhost:8080/rest/user/sale/getsaledetail/`+$scope.getVoucherApply;
                $http.get(urlsaledetail, token).then(res1 => {
                    if(res.data.typeSale==1){
                        price=0;
                        for (var i=0;i<res1.data.length;i++) {
                            $rootScope.carts.forEach(item => {
                                if(item.idProduct == res1.data[i].idProduct){
                                    price+=item.qty * item.priceSale
                                }
                            })
                        }
                        if(null!=res.data.moneySale) {
                            if (price <= res.data.moneySale) {
                                $scope.priceSaleByVocher = price;
                            }else{
                                $scope.priceSaleByVocher=res.data.moneySale;
                            }
                        }
                        if(null!=res.data.percentSale){
                            $scope.priceSaleByVocher=price*res.data.percentSale/100;
                        }
                    }
                    if(res.data.typeSale ==4 ){
                        price=0;
                        for (var i=0;i<res1.data.length;i++) {
                            $rootScope.carts.forEach(item => {
                                if(item.idAccessory == res1.data[i].idAccessory){
                                    price+=item.qty * item.priceSale;
                                }
                            })
                            if(0==price){
                                $scope.priceSaleByVocher=$scope.priceSaleByVocher;
                            }else{
                                if(null!=res.data.moneySale) {
                                    if (price <= res.data.moneySale) {
                                        $scope.priceSaleByVocher = price;
                                    }else{
                                        $scope.priceSaleByVocher=res.data.moneySale;
                                    }
                                }
                                if(null!=res.data.percentSale){
                                    $scope.priceSaleByVocher=price*res.data.percentSale/100;
                                }
                            }
                        }
                    }
                }).catch(err => {
                    console.log(err)
                })
            }else{
                if(null!=res.data.moneySale) {
                    if ($scope.totalPriceSale() <= res.data.moneySale) {
                        $scope.priceSaleByVocher = $scope.totalPriceSale();
                    }else{
                        $scope.priceSaleByVocher=res.data.moneySale;
                    }
                }
                if(null!=res.data.percentSale){
                    $scope.priceSaleByVocher=$scope.totalPriceSale()*res.data.percentSale/100;
                }
            }
            swal.fire({
                icon: 'success',
                showConfirmButton: false,
                title: 'Thêm Mới Thành Công',
                timer: 1000
            })
        }).catch(err => {
            console.log(err)
        })
    }
    $scope.totalPriceSale = function () {
        let total = 0;
        if( $rootScope.carts.length!=0){
            for(var i=0;i<$rootScope.carts.length;i++){
                if($rootScope.carts[i].priceSale==0){
                    total = total + ($rootScope.carts[i].price*$rootScope.carts[i].qty);
                }else{
                    total = total + ($rootScope.carts[i].priceSale*$rootScope.carts[i].qty);
                }
            }
        }else{
            total = 0;
        }
        return total;
    }
})