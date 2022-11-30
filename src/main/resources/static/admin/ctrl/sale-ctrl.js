app.controller("sale_ctrl", function ($scope, $http) {
    let urlprd = "http://localhost:8080/rest/admin/product/getdatasale";
    let urlacsr="http://localhost:8080/rest/admin/accessory/getdatasale";
    let urlacc="http://localhost:8080/rest/admin/accounts/getdatasale";

    const jwtToken = localStorage.getItem("jwtToken")
    const token = {
        headers: {
            Authorization: `Bearer `+jwtToken
        }
    }
    $scope.saleadd = {};
    $scope.saleedit = {};
    $scope.dataTable = [];
    $scope.seLected =[];
    $scope.index=0;
    $scope.totalPages=0;
    $scope.check_first=false;
    $scope.check_last=true;

    $scope.indextable=0;
    $scope.totalPagestable=0;
    $scope.check_firsttable=false;
    $scope.check_lasttable=true;

    //validate start
    $scope.hiddenTableAll = false;
    $scope.hiddenValueMin = false;
    $scope.hiddenDiscountMethod = true;
    $scope.hiddenMoneySale = true;
    $scope.hiddenPercentSale = false;
    $scope.hiddenUserType = false;
    $scope.saleadd.typeSale= 0;
    $scope.saleadd.discountMethod=0;
    $scope.saleadd.discountType=0;
    $scope.saleadd.userType=0
    var d= new Date();
    d.setSeconds(0,0);
    $scope.saleadd.createStart= new Date(d);
    $scope.saleadd.createEnd= new Date(d);

    //add sale start
    $scope.addSale = function (){
        let item = angular.copy($scope.saleadd);
        let urlsale = `/admin/rest/sale/add`;
        let listDetail = angular.copy($scope.seLected);
        if($scope.saleadd.typeSale==0||$scope.saleadd.typeSale==2){
            $http.post(urlsale, item,token).then(resp => {
                document.getElementById("clossmodal").click();
                $scope.clear();
                swal.fire({
                    icon: 'success',
                    showConfirmButton: false,
                    title: 'Thêm Mới Thành Công',
                    timer: 1000
                });
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
        }else{
            $http.post(urlsale, item,token).then(resp => {
                let urlsaledetail = `/admin/rest/sale/adddetail/`+$scope.saleadd.typeSale;
                $http.post(urlsaledetail,listDetail,token).then(resp => {
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
    //addSale end
    $scope.shareTBS = function(){
        var url ="";
        if ($scope.saleadd.typeSale == 1) {
            url = urlprd;
        }else if($scope.saleadd.userType == 1){
            url = urlacc;
        }else if($scope.saleadd.typeSale == 4){
            url = urlacsr;
        }
        $scope.getDataTable(url, $scope.shareTbS);
    }
    //get data table start
    $scope.getDataTable =function (urlDataTable,shear){
        $http.get(`${urlDataTable}/`+$scope.index+"?share="+shear,token).then(function(response) {
            $scope.dataTable = response.data.content;
            $scope.totalPages = response.data.totalPages;
        }).catch(error=>{
            console.log(error);
        });
    }
    //get data table end
    $scope.next=function(urlDataTable,shear){
        if(urlDataTable == 'all'){
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
            var urlGetDataTableSale=`/admin/rest/sale/getall/`+$scope.indextable+`?stt=`+idxstt+`&share=&type=`;
            $http.get(urlGetDataTableSale).then(function(response) {
                $scope.dataTableSale = response.data.content;
                $scope.totalPagestable=response.data.totalPages;
            }).catch(error=>{
                console.log(error);
            });
        }else {
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
    $scope.prev=function(urlDataTable,shear){
        if(urlDataTable == 'all'){
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
            var urlGetDataTableSale=`/admin/rest/sale/getall/`+$scope.indextable+`?stt=`+idxstt+`&share=&type=`;
            $http.get(urlGetDataTableSale).then(function(response) {
                $scope.dataTableSale = response.data.content;
                $scope.totalPagestable=response.data.totalPages;
            }).catch(error=>{
                console.log(error);
            });
        }else {
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
    $scope.checkSelected=function (id){
        if($scope.dataTable.length!=0){
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
                    console.log('id sale là ' +checkid)
                }
            }
            if (check) {
                $scope.seLected.push(checkid);
            }
        }
    }
    $scope.checkSelect=function (id){
        var checkid = '';
        if($scope.dataTable.length!=0) {
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
    $scope.clear=function (){
        $scope.saleedit = {}
        $scope.dataTable = [];
        $scope.seLected = [];
        $scope.index=0;
        $scope.totalPages=0;
        $scope.check_first=false;
        $scope.check_last=true;
        $scope.saleadd = {};
        $scope.saleadd.typeSale= 0;
        $scope.saleadd.discountMethod=0;
        $scope.saleadd.discountType=0;
        $scope.saleadd.userType=0
        $scope.iNit();
    }
    $scope.iNit=function (){
        $scope.onChangeTypeSale();
        $scope.onChangeDiscountMethod();
        $scope.onChangeDiscountType();
    }
    $scope.onChangeTypeSale=function (){
        if($scope.saleadd.typeSale == '0'){
            $scope.dataTable = [];
            $scope.totalPages=0;
            $scope.check_first=false;
            $scope.check_last=true;
            $scope.nameOnTable = "";
            $scope.hiddenTableAll = false;
            $scope.hiddenValueMin = false;
            $scope.hiddenUserType = false;
            $scope.saleadd.typeSale= 0;
        }else if($scope.saleadd.typeSale == '1'){
            $scope.saleadd.typeSale= 1;
            $scope.hiddenTableAll = true;
            $scope.hiddenValueMin = false;
            $scope.hiddenUserType = false;
            $scope.nameOnTable = "sản phẩm"
            $scope.getDataTable(urlprd);
        }else if($scope.saleadd.typeSale == '2'){
            $scope.saleadd.typeSale= 2;
            $scope.hiddenTableAll = false;
            $scope.hiddenValueMin = true;
            $scope.hiddenUserType = false;
            $scope.nameOnTable = ""
        }else if($scope.saleadd.typeSale == '3'){
            $scope.saleadd.typeSale= 3;
            $scope.hiddenTableAll = false;
            $scope.hiddenValueMin = false;
            $scope.hiddenUserType = true;
            $scope.nameOnTable = "user"
            $scope.getDataTable(urlacc);
        }else if($scope.saleadd.typeSale == '4'){
            $scope.saleadd.typeSale= 4;
            $scope.hiddenTableAll = true;
            $scope.hiddenValueMin = false;
            $scope.hiddenUserType = false;
            $scope.nameOnTable = "phụ kiện"
            $scope.getDataTable(urlacsr);
        }
        $scope.onChangeDiscountMethod();
        $scope.onChangeDiscountType();
    }

    $scope.onChangeDiscountMethod=function (){
        if($scope.saleadd.discountMethod ==0){
            $scope.hiddenDiscountMethod = true;
        }else if($scope.saleadd.discountMethod==1){
            $scope.hiddenDiscountMethod = false;
        }
    }
    $scope.onChangeDiscountType=function (){
        if($scope.saleadd.discountType==0){
            $scope.hiddenMoneySale = true;
            $scope.hiddenPercentSale = false;
        }else if($scope.saleadd.discountType==1){
            $scope.hiddenMoneySale = false;
            $scope.hiddenPercentSale = true;
        }
    }
    $scope.onChangeUserType=function (){
        if($scope.saleadd.userType==0){
            $scope.hiddenTableAll = false;
        }else if($scope.saleadd.userType==1){
            $scope.hiddenTableAll = true;
        }
    }
 var idxstt;
//showmemu
    $scope.showDataTableSale=function (stt){
        $scope.dataTableSale=[];
        $scope.indextable=0;
        var urlGetDataTableSale=`/admin/rest/sale/getall/`+$scope.indextable+`?stt=`+stt+`&share=&type=`;
        $http.get(urlGetDataTableSale).then(function(response) {
            $scope.dataTableSale = response.data.content;
            $scope.totalPagestable=response.data.totalPages;
        }).catch(error=>{
            console.log(error);
        });
        return idxstt = stt;
    }
    $scope.compareDate=function (dateStart, dateEnd, quantity){
        var newDate = new Date();
        var startDate = new Date(dateStart);
        var endDate = new Date(dateEnd);
        if(endDate < newDate){
            return 2;
        }
        if(quantity==0){
            return 3;
        }
        if(startDate > newDate){
            return 0;
        }
        if( startDate < newDate && endDate > newDate){
            return 1;
        }
    }

    $scope.showdetailSale=function (id){
       var url= `http://localhost:8080/admin/rest/sale/getsale/`+id;
        $http.get(url).then(function(response) {
            $scope.saleadd = response.data;
            $scope.saleadd.createStart= new Date(response.data.createStart);
            $scope.saleadd.createEnd = new Date(response.data.createEnd);
            var url1="http://localhost:8080/admin/rest/sale/getsaledetail/"+id;
            $http.get(url1).then(function(response1) {
                $scope.seLected=[];
                for (var i =0 ; i<response1.data.length;i++) {
                    if ($scope.saleadd.typeSale == 1) {
                        $scope.seLected.push(response1.data[i].idProduct);
                    }else if($scope.saleadd.userType == 1){
                        $scope.seLected.push(response1.data[i].username);
                    }else if($scope.saleadd.typeSale == 4){
                        $scope.seLected.push(response1.data[i].idAccessory);
                    }
                }
                $scope.iNit();
            })
        }).catch(error=>{
            console.log(error);
        });
    }
    // update Sale start
    $scope.updateSale=function (){
        let item = angular.copy($scope.saleadd);
        let urlsale = `/admin/rest/sale/update`;
        let listDetail = angular.copy($scope.seLected);
        if($scope.saleadd.typeSale==0||$scope.saleadd.typeSale==2){
            $http.post(urlsale, item,token).then(resp => {
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
        }else{
            $http.post(urlsale, item,token).then(resp => {
                let urlsaledetail = `/admin/rest/sale/updatedetail/`+$scope.saleadd.typeSale+`/`+$scope.saleadd.idSale;
                $http.post(urlsaledetail,listDetail,token).then(resp => {
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

})

