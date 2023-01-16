app.controller('home-ctr-v2', function ($rootScope, $scope, $http,) {
    const jwtToken = localStorage.getItem("jwtToken")
    const token = {
        headers: {
            Authorization: `Bearer `+jwtToken
        }
    }
    $scope.ramProducts = [];
    $scope.capacityProducts = [];
    $scope.colorProducts = [];
    $scope.getAllProduct = [];
    $scope.findProductAll = {};
    $scope.findProductAll.idCategory = [];
    $scope.findProductAll.idRam = [];
    $scope.findProductAll.idColer = [];
    $scope.findProductAll.idCapacity = [];
    $scope.findProductAll.sortMinMax = 0;
    $scope.findProductAll.priceSalemin = '';
    $scope.findProductAll.priceSalemax = '';
    $scope.findProductAll.search = '';
    $scope.totalElements=0;
    $scope.totalPages=0;
    $scope.numberPage=0;

    $scope.totalElementsPRD=0;
    $scope.totalPagesPRD=0;
    $scope.numberPagePRD=0;

    $scope.getPriceSale = function (x, y) {
        console.log("hihi")
        var urlSale='';
        if(y == 0){
            urlSale= `http://localhost:8080/rest/guest/sale/getbigsale?money=` + $rootScope.detailProducts[x].price + `&idPrd=` + $rootScope.detailProducts[x].idProduct + `&idAcsr=0`;
        }else {
            urlSale= `http://localhost:8080/rest/guest/sale/getbigsale?money=` + $rootScope.detailAccessories[x].price + `&idPrd=0&idAcsr=`+ $rootScope.detailAccessories[x].idAccessory;
        }
        $http.get(urlSale, token).then(resp => {
            if (y == 0) {
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
            } else if (y == 1) {
                if (resp.data == '') {
                    $rootScope.detailAccessories[x].priceSale = 0;
                } else {
                    if (resp.data.moneySale == null) {
                        $rootScope.detailAccessories[x].priceSale = $rootScope.detailAccessories[x].price * resp.data.percentSale / 100;
                    } else if (resp.data.percentSale == null) {
                        if (resp.data.moneySale > $rootScope.detailAccessories[x].price) {
                            $rootScope.detailAccessories[x].priceSale = $rootScope.detailAccessories[x].price;
                        } else {
                            $rootScope.detailAccessories[x].priceSale = resp.data.moneySale;
                        }
                    }
                }
            }
        }).catch(error => {
            console.log(error)
        })
    }

    $scope.getAllRam = function () {
        let url = `/rest/guest/getAllRam`;
        $http.get(url).then(function (respon) {
            $scope.ramProducts = respon.data;
        }).catch(err => {
            console.log(err)
        })
    }
    $scope.getAllRam();

    $scope.getAllColor = function () {
        let url = `/rest/guest/getAllColor`;
        $http.get(url).then(function (respon) {
            $scope.colorProducts = respon.data;
        }).catch(err => {
            console.log(err)
        })
    }
    $scope.getAllColor();

    $scope.getAllCapacity = function () {
        let url = `/rest/guest/getAllCapacity`;
        $http.get(url).then(function (respon) {
            $scope.capacityProducts = respon.data;
        }).catch(err => {
            console.log(err)
        })
    }
    $scope.getAllCapacity();
    $scope.findProduct = function (page) {
        if(page<0){
            page=$scope.totalPagesPRD-1;
        }else if(page>$scope.totalPagesPRD-1){
            page=0
        }
        $http.post(`/rest/guest/product/findproduct/`+page, $scope.findProductAll).then(function (respon) {
            $scope.getAllProduct = respon.data.content;
            console.log(respon.data.content)
            $scope.totalElementsPRD=respon.data.totalElements
            $scope.totalPagesPRD=respon.data.totalPages;
            $scope.numberPagePRD=respon.data.number;

        }).catch(err => {
                console.log(err + 'kiixu  lỗi')
            }
        )
    }
    $scope.findProduct(0);
    $scope.findAccessory = function (page) {
        if(page<0){
            page=$scope.totalPages-1;
        }else if(page>$scope.totalPages-1){
            page=0
        }
        $http.post(`/rest/guest/accessory/findaccessory/`+page, $scope.findProductAll).then(function (respon) {
            $rootScope.detailAccessories = respon.data.content;
            $scope.totalElements=respon.data.totalElements
            $scope.totalPages=respon.data.totalPages;
            $scope.numberPage=respon.data.number;
            if($rootScope.detailAccessories !=''){
                for (var i = 0; i < $rootScope.detailAccessories.length; i++) {
                    $scope.getPriceSale(i, 1);
                }
            }
        }).catch(err => {
                console.log(err , "hihihihii")
            }
        )
    }
    $scope.findAccessory(0);

    $scope.checksortMinMax = function (text, check) {
        if (text == 'Tăng dần') {
            $scope.findProductAll.sortMinMax = 0;
        } else {
            $scope.findProductAll.sortMinMax = 1;
        }
        if (check == 0) {
            $scope.findProduct(0);
        } else if (check == 1) {
            $scope.findAccessory(0);
        }

    }
    $scope.checkSelected = function (id, check) {
        let checkseleced = true;
        if (check == 0) {
            for (let i = 0; i < $scope.findProductAll.idCategory.length; i++) {
                if (id == $scope.findProductAll.idCategory[i]) {
                    $scope.findProductAll.idCategory.splice(i, 1);
                    checkseleced = false
                }
            }
            if (checkseleced) {
                $scope.findProductAll.idCategory.push(id);
            }
        } else if (check == 1) {
            for (let i = 0; i < $scope.findProductAll.idCapacity.length; i++) {
                if (id == $scope.findProductAll.idCapacity[i]) {
                    $scope.findProductAll.idCapacity.splice(i, 1);
                    checkseleced = false
                }
            }
            if (checkseleced) {
                $scope.findProductAll.idCapacity.push(id);
            }
        } else if (check == 2) {
            for (let i = 0; i < $scope.findProductAll.idRam.length; i++) {
                if (id == $scope.findProductAll.idRam[i]) {
                    $scope.findProductAll.idRam.splice(i, 1);
                    checkseleced = false
                }
            }
            if (checkseleced) {
                $scope.findProductAll.idRam.push(id);
            }
        } else if (check == 3) {
            for (let i = 0; i < $scope.findProductAll.idColer.length; i++) {
                if (id == $scope.findProductAll.idColer[i]) {
                    $scope.findProductAll.idColer.splice(i, 1);
                    checkseleced = false
                }
            }
            if (checkseleced) {
                $scope.findProductAll.idColer.push(id);
            }
        }
    }
})