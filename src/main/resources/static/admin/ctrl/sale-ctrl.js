app.controller("sale_ctrl", function ($scope, $http,$filter) {
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
    $scope.seLected = [];
    $scope.index=0;
    $scope.totalPages=0;
    $scope.check_first=false;
    $scope.check_last=true;

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
    //validate end

    //addSale Start
    $scope.addSale = function (){
        let item = angular.copy($scope.saleadd);
        let urlsale = `/admin/rest/sale/demo`;
        $http.post(urlsale, item,token).then(resp => {
        }).catch(error => {
            swal.fire({
                icon: 'error',
                showConfirmButton: false,
                title: error.data.message,
                timer: 5000
            });
        })
    }
    $scope.addSaleDetail = function (){
        let listDetail = angular.copy($scope.seLected);
        let urlsale = `/admin/rest/sale/demo3`;
        $http.post(urlsale, listDetail,token).then(resp => {
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
                title: 'Thêm Mới Thất Bại',
                timer: 5000
            });
        })
    }
    $scope.createSale = function (){
        if($scope.saleadd.typeSale==0||$scope.saleadd.typeSale==2){
            $scope.addSale();
            swal.fire({
                icon: 'success',
                showConfirmButton: false,
                title: 'Thêm Mới Thành Công',
                timer: 1000
            });
        }else{
            if(!$scope.addSale()){
                $scope.addSaleDetail();
            }
        }
        $scope.onChangeTypeSale();
    }
    //addSale end

    //get data table start

    $scope.getDataTable =function (urlDataTable,shear){
        $http.get(`${urlDataTable}/`+$scope.index+"?share="+shear,token).then(function(response) {
            console.log(`${urlDataTable}/`+$scope.index+"?share=");
            $scope.dataTable = response.data.content;
            if(response.data.totalElements % 10 == 0){

                $scope.totalPages=response.data.totalElements/10;
            }else{
                $scope.totalPages=Math.floor(response.data.totalElements/10)+1;
            }
        }).catch(error=>{
            console.log(error);
        });
    }
    //get data table end
    $scope.next=function(){
        $scope.check_first=true;
        $scope.index++;
        if($scope.index>=$scope.totalPages){
            $scope.index=0;
            $scope.check_first=false;
            $scope.check_last=true;
        }
        if($scope.index==$scope.totalPages-1){
            $scope.check_first=true;
            $scope.check_last=false;
        }
        $scope.getDataTable();
    }
    $scope.prev=function(){
        $scope.check_last=true;
        $scope.index--;
        if($scope.index<0){
            $scope.index=$scope.totalPages-1;
            $scope.check_first=true;
            $scope.check_last=false;
        }
        if($scope.index==0){
            $scope.check_first=false;
            $scope.check_last=true;
        }
        $scope.getDataTable();
    }
    $scope.checkSelected=function (id){
        var check = true;
        for(var i=0;i<$scope.seLected.length;i++){
            if($scope.seLected[i]==id){
                check = false;
                $scope.seLected.splice(i,1);
            }
        }
        if (check){
            $scope.seLected.push(id);
        }
    }
    $scope.checkSelect=function (id){
        for(var i=0;i<$scope.seLected.length;i++){
            if($scope.seLected[i]==id){
                return true;
            }
        }
    }
    $scope.getDataSale = function (){

    }

    $scope.clear=function (){
        $scope.saleedit = {}
        $scope.dataTable = [];
        $scope.seLected = [];
        $scope.index=0;
        $scope.totalPages=0;
        $scope.check_first=false;
        $scope.check_last=true;
    }
    $scope.onChangeTypeSale=function (){
        if($scope.saleadd.typeSale == '0'){
            $scope.dataTable = [];
            $scope.seLected = [];
            $scope.totalPages=0;
            $scope.check_first=false;
            $scope.check_last=true;
            $scope.nameOnTable = "";
            $scope.hiddenTableAll = false;
            $scope.hiddenValueMin = false;
            $scope.hiddenUserType = false;
            $scope.saleadd.typeSale= 0;
            $scope.saleadd.discountMethod=0;
            $scope.saleadd.discountType=0;
            $scope.saleadd.userType=0
        }else if($scope.saleadd.typeSale == '1'){
            $scope.saleadd.typeSale= 1;
            $scope.hiddenTableAll = true;
            $scope.hiddenValueMin = false;
            $scope.hiddenUserType = false;
            $scope.nameOnTable = "sản phẩm"
            $scope.seLected = []
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
            $scope.seLected = []
            $scope.getDataTable(urlacc);
        }else if($scope.saleadd.typeSale == '4'){
            $scope.saleadd.typeSale= 4;
            $scope.hiddenTableAll = true;
            $scope.hiddenValueMin = false;
            $scope.hiddenUserType = false;
            $scope.nameOnTable = "phụ kiện"
            $scope.seLected = []
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

//showmemu
    $scope.showDataTableSale=function (stt){
        $scope.dataTableSale=[];
        $scope.index=0;
        var urlGetDataTableSale=`/admin/rest/sale/getall/`+$scope.index+`?stt=`+stt+`&share=&type=`;
        $http.get(urlGetDataTableSale).then(function(response) {
            $scope.dataTableSale = response.data.content;
            console.log(response.data.totalElements);
            console.log(response.data.totalElements);
            if(response.data.totalElements % 10 ==0){
                $scope.totalPages=response.data.totalElements/10;
            }else{
                $scope.totalPages=Math.floor(response.data.totalElements/10)+1;
            }
        }).catch(error=>{
            console.log(error);
        });
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

        console.log("jihih")
        return -1;
    }

})

