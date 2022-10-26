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
    $scope.createSale = function (){
        let item = angular.copy($scope.saleadd);
        let urlsale = `/admin/rest/sale/demo`;
        $http.post(urlsale, item).then(resp => {
            $scope.clear();
            swal.fire({
                icon: 'success',
                showConfirmButton: false,
                title: 'Thêm Mới Thành Công',
                timer: 1000
            })
        }).catch(error => {
            console.log("error", error)
            swal.fire({
                icon: 'error',
                showConfirmButton: false,
                title: error.data.message,
                timer: 3000
            })
        })
        $scope.saleadd = {}
    }
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
    $scope.onChange=function (){
        var loaigg = document.getElementById('loaigg1');
        var checkValue = loaigg.value;
        if(checkValue == '0'){
            $scope.products = [];
            $scope.seLected = [];
            $scope.index=0;
            $scope.totalPages=0;
            $scope.check_first=false;
            $scope.check_last=true;
        }else if(checkValue == '1'){
            console.log("hihih")
            $scope.getProducts();
        }
    }

    ptgg();kieugg();loaigg();
    $scope.getProducts();
})
function ptgg() {  //checkphuongthucgiamgia
    var ptgg = document.getElementById('ptgg1');
    var checkValue = ptgg.value;
    if (checkValue == '1') {
        document.getElementById('magg').hidden = true;
    } else {
        document.getElementById('magg').hidden = false;
    }
}

function kieugg() {  //checkmucgiamgia
    var kieugg = document.getElementById('kieugg1');
    var checkValue = kieugg.value;
    if (checkValue == '1') {
        document.getElementById('mggtien').hidden = true;
        document.getElementById('mggpt').hidden = false;
    } else {
        document.getElementById('mggtien').hidden = false;
        document.getElementById('mggpt').hidden = true;
    }
}

function loaigg() {  //checkmucgiamgia
    var loaigg = document.getElementById('loaigg1');
    var checkValue = loaigg.value;
    if (checkValue == '0') {
        document.getElementById('tableProduct').hidden = true;
        document.getElementById('tableUser').hidden = true;
        document.getElementById('gttt').hidden = true;
    } else if (checkValue == '1') {
        document.getElementById('tableProduct').hidden = false;
        document.getElementById('tableUser').hidden = true;
        document.getElementById('gttt').hidden = true;
    } else if (checkValue == '2') {
        document.getElementById('tableProduct').hidden = true;
        document.getElementById('tableUser').hidden = true;
        document.getElementById('gttt').hidden = false;
    } else if (checkValue == '3'){
        document.getElementById('tableProduct').hidden = true;
        document.getElementById('tableUser').hidden = false;
        document.getElementById('gttt').hidden = true;
    }else{

    }
}
