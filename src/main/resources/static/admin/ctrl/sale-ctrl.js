app.controller("sale_ctrl", function ($scope, $http) {
    let urlprd = "http://localhost:8080/rest/admin/product";
    $scope.saleadd = {};
    $scope.saleedit = {};
    $scope.products = [];
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
    //validate end

    //addSale Start

    $scope.addSale = function (){
        let item = angular.copy($scope.saleadd);
        let urlsale = `/admin/rest/sale/demo`;
        $http.post(urlsale, item).then(resp => {
        })
    }
    $scope.addSaleDetail = function (){
        let listDetail = angular.copy($scope.seLected);
        let urlsale = `/admin/rest/sale/demo3`;
        $http.post(urlsale, listDetail).then(resp => {
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
        $scope.clear();
    }
    $scope.createSale = function (){
        var typeSale = document.getElementById('typeSale');
        var checkValue = typeSale.value;
        if(checkValue==0||checkValue==2){
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
        $scope.saleadd={};
        $scope.onChangeTypeSale();
    }
    //addSale end

    //get data table start
    $scope.getProducts =function (){
        $http.get(`${urlprd}/page/`+$scope.index).then(function(response) {
            $scope.products = response.data.content;
            if(response.data.totalElements % 5 ==0){
                $scope.totalPages=response.data.totalElements/5;
            }else{
                $scope.totalPages=Math.floor(response.data.totalElements/5)+1;
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
        $scope.getProducts();
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
        $scope.getProducts();
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
    $scope.clear=function (){
        $scope.saleedit = {}
        $scope.products = [];
        $scope.seLected = [];
        $scope.index=0;
        $scope.totalPages=0;
        $scope.check_first=false;
        $scope.check_last=true;
    }
    $scope.onChangeTypeSale=function (){
        var typeSale = document.getElementById('typeSale');
        var checkValue = typeSale.value;
        if(checkValue == '0' || checkValue == '? undefined:undefined ?'){
            $scope.products = [];
            $scope.seLected = [];
            $scope.index=0;
            $scope.totalPages=0;
            $scope.check_first=false;
            $scope.check_last=true;
            $scope.nameOnTable = "";
            $scope.hiddenTableAll = false;
            $scope.hiddenValueMin = false;
            $scope.hiddenUserType = false;
        }else if(checkValue == '1'){
            $scope.hiddenTableAll = true;
            $scope.hiddenValueMin = false;
            $scope.hiddenUserType = false;
            $scope.nameOnTable = "sản phẩm"
            $scope.getProducts();
        }else if(checkValue == '2'){
            $scope.hiddenTableAll = false;
            $scope.hiddenValueMin = true;
            $scope.hiddenUserType = false;
            $scope.nameOnTable = ""
            $scope.getProducts();
        }else if(checkValue == '3'){
            $scope.hiddenTableAll = false;
            $scope.hiddenValueMin = false;
            $scope.hiddenUserType = true;
            $scope.nameOnTable = "user"
            $scope.getProducts();
        }else if(checkValue == '4'){
            $scope.hiddenTableAll = true;
            $scope.hiddenValueMin = false;
            $scope.hiddenUserType = false;
            $scope.nameOnTable = "phụ kiện"
            $scope.getProducts();
        }
    }

    $scope.onChangeDiscountMethod=function (){
        var discountMethod = document.getElementById('discountMethod');
        var checkValue = discountMethod.value;
        if(checkValue==0){
            $scope.hiddenDiscountMethod = true;
        }else if(checkValue==1){
            $scope.hiddenDiscountMethod = false;
        }
    }
    $scope.onChangeDiscountType=function (){
        var discountType = document.getElementById('discountType');
        var checkValue = discountType.value;
        if(checkValue==0){
            $scope.hiddenMoneySale = true;
            $scope.hiddenPercentSale = false;
        }else if(checkValue==1){
            $scope.hiddenMoneySale = false;
            $scope.hiddenPercentSale = true;
        }
    }
    $scope.onChangeUserType=function (){
        var userType = document.getElementById('userType');
        var checkValue = userType.value;
        if(checkValue==0){
            $scope.hiddenTableAll = false;
        }else if(checkValue==1){
            $scope.hiddenTableAll = true;
        }
    }
    $scope.loadCateSale = function () {
        $scope.saleadd.typeSale= 0;
    }
    $scope.loadCateSale();
})

