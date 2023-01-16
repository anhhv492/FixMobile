app.controller("sale_ctrl", function ($scope, $http, $window,$rootScope) {
    let urlprd = "http://localhost:8080/rest/staff/product/getdatasale";
    let urlacsr = "http://localhost:8080/rest/staff/accessory/getdatasale";
    let urlacc = "http://localhost:8080/rest/admin/accounts/getdatasale";

    const jwtToken = localStorage.getItem("jwtToken")
    const token = {
        headers: {
            Authorization: `Bearer ` + jwtToken
        }
    }

    $rootScope.check = null;

    $scope.logOut = function (){
        $window.location.href = "http://localhost:8080/views/index.html#!/login"
        Swal.fire({
            icon: 'error',
            title: 'Vui lòng đăng nhập lại!!',
            text: 'Tài khoản của bạn không có quyền truy cập!!',
        })
    }

    $scope.checkLogin = function () {
        if (jwtToken == null){
            $scope.logOut();
        }else {
            $http.get("http://localhost:8080/rest/user/getRole",token).then(respon =>{
                if (respon.data.name === "USER"){
                    $scope.logOut();
                }else if (respon.data.name === "ADMIN"){
                    $rootScope.check = null;
                }else {
                    $rootScope.check = "OK";
                    $scope.logOut();
                }
            })
        }
    }

    $scope.checkLogin();
    $scope.saleadd = {};
    $scope.saleedit = {};
    $scope.dataTable = [];
            $scope.dataTableSale = {};
    $scope.seLected = [];
    $scope.index = 0;
    $scope.totalPages = 0;
    $scope.check_first = false;
    $scope.check_last = true;

    $scope.indextable = 0;
    $scope.totalPagestable = 0;
    $scope.check_firsttable = false;
    $scope.check_lasttable = true;
    $scope.checktypeSalediscountMethod=false;

    //validate start
    $scope.hiddenTableAll = false;
    $scope.hiddenValueMin = false;
    $scope.hiddenDiscountMethod = true;
    $scope.hiddenMoneySale = true;
    $scope.hiddenPercentSale = false;
    $scope.hiddenUserType = false;
    $scope.saleadd.typeSale = 0;
    $scope.saleadd.discountMethod = 0;
    $scope.saleadd.discountType = 0;
    $scope.saleadd.userType = 0;
    var d = new Date();
    d.setSeconds(0, 0);
    $scope.saleadd.idSale = null;
    $scope.saleadd.createStart = new Date(d);
    $scope.saleadd.createEnd = new Date(d);

    //add sale start
    $scope.addSale = function () {
        Swal.fire({
            title: 'Xác nhận thêm mới',
            text: "Lưu ý: Khi thêm mới Sale trực tiếp hệ thống sẽ tự động áp dụng mã có mức giảm giá cao nhất.",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Thêm ngay',
            cancelButtonText: 'Xem lại',
        }).then((result) => {
            if (result.isConfirmed) {
                let item = angular.copy($scope.saleadd);
                let urlsale = `/rest/admin/sale/add`;
                let listDetail = angular.copy($scope.seLected);
                if ($scope.saleadd.typeSale == 0 || $scope.saleadd.typeSale == 2 || ($scope.saleadd.userType == 0 && $scope.saleadd.typeSale == 3) ) {
                    console.log($scope.saleadd.typeSale+'hihihihi')
                    $http.post(urlsale, item, token).then(resp => {
                        swal.fire({
                            icon: 'success',
                            showConfirmButton: false,
                            title: 'Thêm Mới Thành Công',
                            timer: 1000
                        });
                        document.getElementById("clossmodal").click();
                        $scope.clear();
                    }).catch(error => {
                        console.log(error)
                        swal.fire({
                            icon: 'error',
                            showConfirmButton: false,
                            title: error.data.message,
                            timer: 5000
                        });
                    })
                } else {
                    console.log($scope.saleadd.typeSale+'hahhahaah')
                    $http.post(urlsale, item, token).then(resp => {
                        let urlsaledetail = `/rest/admin/sale/adddetail/` + $scope.saleadd.typeSale;
                        console.log(listDetail);
                        $http.post(urlsaledetail, listDetail, token).then(resp => {
                            document.getElementById("clossmodal").click();
                            $scope.clear();
                            swal.fire({
                                icon: 'success',
                                showConfirmButton: false,
                                title: 'Thêm Mới Thành Công',
                                timer: 1000
                            })
                        }).catch(error => {
                            swal.fire({
                                icon: 'error',
                                showConfirmButton: false,
                                title: error.data.message,
                                timer: 5000
                            });
                        })
                    }).catch(error => {
                        swal.fire({
                            icon: 'error',
                            showConfirmButton: false,
                            title: error.data.message,
                            timer: 5000
                        });
                    })
                }
            }
        })


    }
    //addSale end
    $scope.shareTBS = function () {
        var url = "";
        if ($scope.saleadd.typeSale == 1) {
            url = urlprd;
        } else if ($scope.saleadd.userType == 1) {
            url = urlacc;
        } else if ($scope.saleadd.typeSale == 4) {
            url = urlacsr;
        }
        $scope.getDataTable(url, $scope.shareTbS);
    }
    //get data table start
    $scope.getDataTable = function (urlDataTable, shear) {
        $http.get(`${urlDataTable}/` + $scope.index + "?share=" + shear, token).then(function (response) {
            $scope.dataTable = response.data.content;
            $scope.totalPages = response.data.totalPages;
        }).catch(error => {
            console.log(error);
        });
    }
    //get data table end
    $scope.next = function (urlDataTable, shear) {
        if (urlDataTable == 'all') {
            $scope.check_firsttable = true;
            $scope.indextable++;
            if ($scope.indextable >= $scope.totalPagestable) {
                $scope.indextable = 0;
                $scope.check_firsttable = false;
                $scope.check_lasttable = true;
            }
            if ($scope.indextable == $scope.totalPagestable - 1) {
                $scope.check_firsttable = true;
                $scope.check_lasttable = false;
            }
            var urlGetDataTableSale = `/rest/admin/sale/getall/` + $scope.indextable + `?stt=` + idxstt + `&share=&type=`;
            $http.get(urlGetDataTableSale,token).then(function (response) {
                $scope.dataTableSale = response.data.content;
                $scope.totalPagestable = response.data.totalPages;
            }).catch(error => {
                console.log(error);
            });
        } else {
            $scope.check_first = true;
            $scope.index++;
            if ($scope.index >= $scope.totalPages) {
                $scope.index = 0;
                $scope.check_first = false;
                $scope.check_last = true;
            }
            if ($scope.index == $scope.totalPages - 1) {
                $scope.check_first = true;
                $scope.check_last = false;
            }
            if (urlDataTable == 'phụ kiện') {
                urlDataTable = urlacsr;
            } else if (urlDataTable == 'sản phẩm') {
                urlDataTable = urlprd;
            } else if (urlDataTable == 'user') {
                urlDataTable = urlacc;
            }
            $scope.getDataTable(urlDataTable, shear);
        }
    }
    $scope.prev = function (urlDataTable, shear) {
        if (urlDataTable == 'all') {
            $scope.check_lasttable = true;
            $scope.indextable--;
            if ($scope.indextable < 0) {
                $scope.indextable = $scope.totalPagestable - 1;
                $scope.check_firsttable = true;
                $scope.check_lasttable = false;
            }
            if ($scope.indextable == 0) {
                $scope.check_firsttable = false;
                $scope.check_lasttable = true;
            }
            var urlGetDataTableSale = `/rest/admin/sale/getall/` + $scope.indextable + `?stt=` + idxstt + `&share=&type=`;
            $http.get(urlGetDataTableSale,token).then(function (response) {
                $scope.dataTableSale = response.data.content;
                $scope.totalPagestable = response.data.totalPages;
            }).catch(error => {
                console.log(error);
            });
        } else {
            $scope.check_last = true;
            $scope.index--;
            if ($scope.index < 0) {
                $scope.index = $scope.totalPages - 1;
                $scope.check_first = true;
                $scope.check_last = false;
            }
            if ($scope.index == 0) {
                $scope.check_first = false;
                $scope.check_last = true;
            }
            if (urlDataTable == 'phụ kiện') {
                urlDataTable = urlacsr;
            } else if (urlDataTable == 'sản phẩm') {
                urlDataTable = urlprd;
            } else if (urlDataTable == 'user') {
                urlDataTable = urlacc;
            }
            $scope.getDataTable(urlDataTable, shear);
        }
    }
    $scope.checkSelected = function (id) {
        if ($scope.dataTable.length != 0) {
            var checkid = '';
            if ($scope.saleadd.typeSale == 1) {
                checkid = $scope.dataTable[id].idProduct;
            } else if ($scope.saleadd.typeSale == 3) {
                checkid = $scope.dataTable[id].username;
            } else if ($scope.saleadd.typeSale == 4) {
                checkid = $scope.dataTable[id].idAccessory;
            }
            var check = true;
            for (var i = 0; i < $scope.seLected.length; i++) {
                if ($scope.seLected[i] == checkid) {
                    check = false;
                    $scope.seLected.splice(i, 1);
                }
            }
            if (check) {
                $scope.seLected.push(checkid);
            }
        }
    }
    $scope.checkSelect = function (id) {
        var checkid = '';
        if ($scope.dataTable.length != 0) {
            if ($scope.saleadd.typeSale == 1) {
                checkid = $scope.dataTable[id].idProduct;
            } else if ($scope.saleadd.typeSale == 3) {
                checkid = $scope.dataTable[id].username;
            } else if ($scope.saleadd.typeSale == 4) {
                checkid = $scope.dataTable[id].idAccessory;
            }
        }
        for (var i = 0; i < $scope.seLected.length; i++) {
            if ($scope.seLected[i] == checkid) {
                return true;
            }
        }
    }
    $scope.clear = function () {
        $scope.saleedit = {}
        $scope.dataTable = [];
        $scope.seLected = [];
        $scope.index = 0;
        $scope.totalPages = 0;
        $scope.check_first = false;
        $scope.check_last = true;
        $scope.saleadd = {};
        $scope.saleadd.typeSale = 0;
        $scope.saleadd.discountMethod = 0;
        $scope.saleadd.discountType = 0;
        $scope.saleadd.userType = 0;
        $scope.saleadd.idSale = null;
        $scope.iNit();
    }
    $scope.iNit = function () {
        $scope.onChangeTypeSale();
        $scope.onChangeDiscountMethod();
        $scope.onChangeDiscountType();
        $scope.onChangeUserType()
        $scope.showDataTableSale(0);
    }
    $scope.onChangeTypeSale = function () {
        if ($scope.saleadd.typeSale == '0') {
            $scope.dataTable = [];
            $scope.totalPages = 0;
            $scope.check_first = false;
            $scope.check_last = true;
            $scope.nameOnTable = "";
            $scope.hiddenTableAll = false;
            $scope.hiddenValueMin = false;
            $scope.checktypeSalediscountMethod=false;
            $scope.hiddenUserType = false;
            $scope.saleadd.typeSale = 0;
        } else if ($scope.saleadd.typeSale == '1') {
            $scope.saleadd.typeSale = 1;
            $scope.hiddenTableAll = true;
            $scope.hiddenValueMin = false;
            $scope.hiddenUserType = false;
            $scope.checktypeSalediscountMethod=false;
            $scope.nameOnTable = "sản phẩm"
            $scope.getDataTable(urlprd);
        } else if ($scope.saleadd.typeSale == '2') {
            $scope.saleadd.discountMethod=0;
            $scope.checktypeSalediscountMethod=true;
            $scope.saleadd.typeSale = 2;
            $scope.hiddenTableAll = false;
            $scope.hiddenValueMin = true;
            $scope.hiddenUserType = false;
            $scope.nameOnTable = "";
        } else if ($scope.saleadd.typeSale == '3') {
            $scope.checktypeSalediscountMethod=false;
            $scope.saleadd.typeSale = 3;
            $scope.hiddenTableAll = false;
            $scope.hiddenValueMin = false;
            $scope.hiddenUserType = true;
            $scope.nameOnTable = "user";
            $scope.getDataTable(urlacc);
        } else if ($scope.saleadd.typeSale == '4') {
            $scope.saleadd.typeSale = 4;
            $scope.hiddenTableAll = true;
            $scope.hiddenValueMin = false;
            $scope.checktypeSalediscountMethod=false;
            $scope.hiddenUserType = false;
            $scope.nameOnTable = "phụ kiện"
            $scope.getDataTable(urlacsr);
        }
        $scope.onChangeDiscountMethod();
        $scope.onChangeDiscountType();
    }

    $scope.onChangeDiscountMethod = function () {
        if ($scope.saleadd.discountMethod == 0) {
            $scope.hiddenDiscountMethod = true;
        } else if ($scope.saleadd.discountMethod == 1) {
            $scope.hiddenDiscountMethod = false;
        }
    }
    $scope.onChangeDiscountType = function () {
        if ($scope.saleadd.discountType == 0) {
            $scope.hiddenMoneySale = true;
            $scope.hiddenPercentSale = false;
        } else if ($scope.saleadd.discountType == 1) {
            $scope.hiddenMoneySale = false;
            $scope.hiddenPercentSale = true;
        }
    }
    $scope.onChangeUserType = function () {
        if ($scope.saleadd.userType == 0) {
            $scope.hiddenTableAll = false;
        } else if ($scope.saleadd.userType == 1) {
            $scope.hiddenTableAll = true;
        }
    }
    var idxstt;
//showmemu
    $scope.showDataTableSale = function (stt) {
        $scope.dataTableSale = [];
        $scope.indextable = 0;
        var urlGetDataTableSale = `/rest/admin/sale/getall/` + $scope.indextable + `?stt=` + stt + `&share=&type=`;
        $http.get(urlGetDataTableSale,token).then(function (response) {
            $scope.dataTableSale = response.data.content;
            $scope.totalPagestable = response.data.totalPages;
        }).catch(error => {
            console.log(error);
        });
        return idxstt = stt;
    }
    $scope.compareDate = function (dateStart, dateEnd, quantity) {
        var newDate = new Date();
        var startDate = new Date(dateStart);
        var endDate = new Date(dateEnd);
        var idx=0;
        if (endDate < newDate) {
            idx= 2;
        }else
        if (quantity == 0) {
            idx= 3;
        }else
        if (startDate > newDate) {
            idx= 0;
        }else
        if (startDate < newDate && endDate > newDate) {
            idx= 1;
        }
        return idx
    }

    $scope.showdetailSale = function (id) {
        var url = `http://localhost:8080/rest/admin/sale/getsale/` + id;
        $http.get(url,token).then(function (response) {
            $scope.saleadd = response.data;
            $scope.saleadd.createStart = new Date(response.data.createStart);
            $scope.saleadd.createEnd = new Date(response.data.createEnd);
            var url1 = "http://localhost:8080/rest/admin/sale/getsaledetail/" + id;
            $http.get(url1,token).then(function (response1) {
                $scope.seLected = [];
                for (var i = 0; i < response1.data.length; i++) {
                    if ($scope.saleadd.typeSale == 1) {
                        $scope.seLected.push(response1.data[i].idProduct);
                    } else if ($scope.saleadd.userType == 1) {
                        $scope.seLected.push(response1.data[i].userName);

                    } else if ($scope.saleadd.typeSale == 4) {
                        $scope.seLected.push(response1.data[i].idAccessory);
                    }
                }
                $scope.iNit();
            })
        }).catch(error => {
            console.log(error);
        });
    }
    // update Sale start
    $scope.updateSale = function () {
        let item = angular.copy($scope.saleadd);
        let urlsale = `/rest/admin/sale/update`;
        let listDetail = angular.copy($scope.seLected);
        if ($scope.saleadd.typeSale == 0 || $scope.saleadd.typeSale == 2) {
            $http.post(urlsale, item, token).then(resp => {
                document.getElementById("clossmodal").click();
                swal.fire({
                    icon: 'success',
                    showConfirmButton: false,
                    title: 'Cập nhập Thành Công',
                    timer: 1000
                });
                $scope.clear();
            }).catch(error => {
                swal.fire({
                    icon: 'error',
                    showConfirmButton: false,
                    title: error.data.message,
                    timer: 5000
                });
            })
        } else {
            $http.post(urlsale, item, token).then(resp => {
                let urlsaledetail = `/rest/admin/sale/updatedetail/` + $scope.saleadd.typeSale + `/` + $scope.saleadd.idSale;
                $http.post(urlsaledetail, listDetail, token).then(resp => {
                    document.getElementById("clossmodal").click();
                    $scope.clear();
                    swal.fire({
                        icon: 'success',
                        showConfirmButton: false,
                        title: 'Cập nhập Thành Công',
                        timer: 1000
                    })
                }).catch(error => {
                    swal.fire({
                        icon: 'error',
                        showConfirmButton: false,
                        title: error.data.message,
                        timer: 5000
                    });
                })
            }).catch(error => {
                swal.fire({
                    icon: 'error',
                    showConfirmButton: false,
                    title: error.data.message,
                    timer: 5000
                });
            })
        }
    }
    $scope.showDataTableSale(0);
    // update Sale end

    $scope.deleteSale = function () {
        Swal.fire({
            title: 'Bạn chắc chắn muốn dừng Sale này chứ?',
            text: "Hệ thống sẽ tự cập nhật số lượng về 0",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Dừng ngay',
            cancelButtonText: 'Xem lại',
        }).then((result) => {
            if (result.isConfirmed) {
                let item = angular.copy($scope.saleadd);
                let urlsale = `/rest/admin/sale/delete`;
                $http.post(urlsale, item, token).then(resp => {
                    document.getElementById("clossmodal").click();
                    swal.fire({
                        icon: 'success',
                        showConfirmButton: false,
                        title: 'Dừng Sale Thành Công',
                        timer: 1000
                    });
                    $scope.clear();
                }).catch(error => {
                    swal.fire({
                        icon: 'error',
                        showConfirmButton: false,
                        title: error.data.message,
                        timer: 5000
                    });
                })
            }
        })

    }
    $scope.coppySale = function () {
        $scope.saleadd.idSale=null;
        $scope.saleadd.voucher="";
        $scope.saleadd.name="";
    }

})

