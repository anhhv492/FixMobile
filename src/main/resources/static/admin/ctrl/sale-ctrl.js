app.controller("sale_ctrl", function ($scope, $http) {
    let urlprd = "http://localhost:8080/rest/admin/product";
    $scope.saleadd = {}
    $scope.saleedit = {}
    $scope.products = [];
    $scope.index=0;
    $scope.totalPages=0;
    $scope.createSale = function (){
        let item = angular.copy($scope.saleadd);
        let urlsale = `/admin/rest/sale/demo`;
        $http.post(urlsale, item).then(resp => {

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
        $http.get(`${urlprd}/page/0`).then(function(response) {
            $scope.products = response.data.content;
        }).catch(error=>{
            console.log(error);
        });
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
    } else {
        document.getElementById('tableProduct').hidden = true;
        document.getElementById('tableUser').hidden = false;
        document.getElementById('gttt').hidden = true;
    }
}
