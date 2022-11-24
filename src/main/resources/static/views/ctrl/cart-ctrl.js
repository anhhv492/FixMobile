app.controller('cart-ctrl', function ($rootScope, $scope, $http, $window) {
    var urlOrder = `http://localhost:8080/rest/guest/order`;
    var urlImei = `http://localhost:8080/rest/guest/imei`;
    var urlAccessory = `http://localhost:8080/rest/guest/accessory`;
    var urlOrderDetail = `http://localhost:8080/rest/guest/order/detail`;
    var urlAccounts = `http://localhost:8080/rest/admin/accounts`;
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
        if(item.idAccessory>-1) {
            var index = $rootScope.carts.findIndex(it => it.idAccessory === item.idAccessory)
        }else if(item.idProduct>-1){
            var index = $rootScope.carts.findIndex(it => it.idProduct === item.idProduct)
        }

        Swal.fire({
            title: 'Xóa: ' + item.name + ' khỏi giỏ hàng?',
            text: "Sau khi xóa không thể khôi phục lại!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                $rootScope.carts.splice(index, 1);
                $rootScope.qtyCart -= item.qty;
                $rootScope.saveLocalStorage();
                $rootScope.loadLocalStorage();
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
        if(item.idAccessory>-1) {
            index = $rootScope.carts.findIndex(it => it.idAccessory === item.idAccessory)
            $http.get(`${urlAccessory}/amount/${item.idAccessory}`).then(res=>{
                if(item.qty>res.data) {
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
                    $scope.getShippingOder();
                }else{
                    $rootScope.carts[index].qty=item.qty;
                    $rootScope.saveLocalStorage();
                    $rootScope.loadLocalStorage();
                    $scope.getShippingOder();
                }
            }).catch(err=>{
                console.log(err)
            })
        }else if(item.idProduct>-1){
            index = $rootScope.carts.findIndex(it => it.idProduct === item.idProduct)
            $http.get(`${urlImei}/amount/${item.idProduct}`).then(res=>{
                if(item.qty>res.data) {
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
                    $scope.getShippingOder();
                }else{
                    $rootScope.carts[index].qty=item.qty;
                    $rootScope.saveLocalStorage();
                    $rootScope.loadLocalStorage();
                    $scope.getShippingOder();
                }
            }).catch(err=>{
                console.log(err)
            })
        }
        $scope.getShippingOder();
        if ($rootScope.carts[index].qty <= 0) {
            $rootScope.saveLocalStorage();
            $rootScope.loadLocalStorage();
            $rootScope.carts.splice(index, 1);
            $window.location.href = '#!cart';
            console.log('I was closed by the timer')
        }
    }
    $scope.raise = function (item) {
        if(item.idAccessory>-1) {
            let index = $rootScope.carts.findIndex(it => it.idAccessory === item.idAccessory)
            $http.get(`${urlAccessory}/amount/${item.idAccessory}`).then(res=>{
                if(item.qty>=res.data) {
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
                    $rootScope.loadLocalStorage();
                    $scope.getShippingOder();
                }else{
                    $rootScope.qtyCart++;
                    $rootScope.carts[index].qty++;
                    $rootScope.saveLocalStorage();
                    $rootScope.loadLocalStorage();
                    $scope.getShippingOder();
                }
            }).catch(err=>{
                console.log(err)
            })
        }else if(item.idProduct>-1){
            let index = $rootScope.carts.findIndex(it => it.idProduct === item.idProduct)
            $http.get(`${urlImei}/amount/${item.idProduct}`).then(res=>{
                if(item.qty>=res.data) {
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
                    $rootScope.carts[index].qty++;
                    $rootScope.qtyCart++;
                    $rootScope.saveLocalStorage();
                    $rootScope.loadLocalStorage();
                    $scope.getShippingOder();
                    console.log('add', item.qty)
                }
            }).catch(err=>{
                console.log(err)
            })
        }
        $scope.getShippingOder();
    }
    $scope.reduce = function (item) {
        if(item.idAccessory>-1) {
            var index = $rootScope.carts.findIndex(it => it.idAccessory === item.idAccessory)
        }else if(item.idProduct>-1){
            var index = $rootScope.carts.findIndex(it => it.idProduct === item.idProduct)
        }
        $rootScope.carts[index].qty--;
        if ($rootScope.carts[index].qty <= 0) {
            $rootScope.saveLocalStorage();
            $rootScope.loadLocalStorage();
            $scope.getShippingOder();
            $rootScope.carts.splice(index, 1);
            $window.location.href = '#!cart';
            console.log('I was closed by the timer')
        }
        $scope.getShippingOder();
        $rootScope.saveLocalStorage();
        $rootScope.loadLocalStorage();
        $rootScope.qtyCart--;
        console.log('add', item.qty)
    }
    $scope.totalPrice = function () {
        let total = 0;
        $rootScope.carts.forEach(item => {
            total += item.qty * item.price;
        })
        return total;
    }
    $scope.totals = function () {
        return $scope.totalPrice() + $scope.ship;
    }
    $scope.totalShip = function () {
        let ship = 50000;
        return ship;
    }
    $scope.refresh = function () {
        $rootScope.carts = [];
        localStorage.clear();
    }
    $scope.buyCart = function () {
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
                            data: price,token,
                            transformResponse: [
                                function (data) {
                                    return data;
                                }
                            ]
                        }).then(res => {
                            console.log("buy cart", res.data)
                            $scope.linkPaypal = res.data;
                            $scope.cart.address = $scope.addressAccount.addressDetail+", "+$scope.addressAccount.addressTake;
                            $scope.cart.createDate = new Date();
                            $scope.cart.total = $scope.totals();
                            $scope.cart.status = 1;
                            $scope.cart.type = false;
                            $http.post(urlOrder + '/add', $scope.cart,token).then(res => {
                                if (res.data) {
                                    $http.post(urlOrderDetail + '/add', $rootScope.carts,token).then(res => {

                                        console.log("orderDetail", res.data)
                                    }).catch(err => {
                                        console.log("err orderDetail", err)
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
                                console.log("err order", err)
                            })
                        }).catch(err => {
                            console.log("error buy cart", err)
                        })

                    } else {
                        $scope.cart.createDate = new Date();
                        $scope.cart.address = $scope.addressAccount.addressDetail+", "+$scope.addressAccount.addressTake;
                        $scope.cart.total = $scope.totals();
                        $scope.cart.status = 0;
                        $scope.cart.type = false;
                        $http.post(urlOrder + '/add', $scope.cart,token).then(res => {
                            if (res.data) {
                                $http.post(urlOrderDetail + '/add', $rootScope.carts,token).then(res => {
                                    $window.location.href = '/views/cart/buy-success.html';
                                }).catch(err => {
                                    console.log("err orderDetail", err)
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
    $scope.checkBuyPaypal = function () {
        $scope.checkBuy = true;
    }
    $scope.checkBuyCOD = function () {
        $scope.checkBuy = false;
    }
    $scope.getAddressAcountACtive = function () {
        if($rootScope.account != null){
            $http.get(urlAccounts + "/getAddress", token).then(function (respon) {
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
            $scope.ship = respon.data.data.total;
            console.log(respon.data.data.total)
        }).catch(err => {
            $http.get(urlShippingOder + "?from_district_id=1542&service_id=53321&to_district_id="
                + $scope.to_district_id + "&to_ward_code=" + $scope.to_ward_code
                + "&weight=200&insurance_value=" + $scope.totalPrice(), token).then(function (respon) {
                $scope.ship = respon.data.data.total;
                console.log(respon.data.data.total)
            })
        })
    }
    $scope.getAddressAcountACtive();
    $scope.getShippingOder();
    $rootScope.loadLocalStorage();
})