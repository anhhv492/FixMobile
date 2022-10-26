app.controller("restaccsesries", function ($scope, $http) {
    // ram máy
    $scope.items = [];
    $scope.formCreate  = [];
    $scope.accseries = {};
    // màu sắc
    $scope.colorItems = [];
    $scope.color = {};


    // hàm ram
    $scope.initialize = function () {
        //load accounts
        $http.get("/rest/admin/accessoríes").then(resp => {
            $scope.items = resp.data;
            $scope.items.forEach(item => {
                console.log(resp.data);
            })
        });
    }

    $scope.onSubmitForm = function (event) {
        event.preventDefault();

    }
    //xóa form
    $scope.reset = function(){
        $scope.accseries ={
        }
    }
    $scope.create = function (){
        var item = angular.copy($scope.accseries);
        console.log(item);
        $http.post("/rest/admin/accessoríes",item).then(resp => {
            $scope.items.push(item);
            $scope.reset();
            $scope.initialize();
            alert('thêm mới thành công');
        }).catch(error => {
            alert('thêm mới lỗi');
            console.log('lỗi', error);
        })
    }
    $scope.initialize();
    //hàm màu
    $scope.findAll = function () {
        $http.get("/rest/admin/accessoríes/getall").then(resp => {
            $scope.colorItems = resp.data;
            $scope.colorItems.forEach(color => {
            })
        });
    }
    $scope.resetColor = function(){
        $scope.color ={
        }
    }
    $scope.createColor = function (){
        var item = angular.copy($scope.color);
        console.log(item);
        $http.post("/rest/admin/accessoríes/save",item).then(resp => {
            $scope.colorItems.push(item);
            $scope.resetColor();
            $scope.findAll();
            Swal.fire(
                'Thông báo!',
                'Thêm mới màu thành công!',
                'success'
            )
        }).catch(error => {
            alert('thêm mới lỗi');
            console.log('lỗi', error);
        })
    }
    $scope.findAll();

    //dung lượng
    $scope.capacityItems=[];
    $scope.capacity ={};

    $scope.findAllCapacity = function () {
        $http.get("/rest/admin/accessoríes/getCapacity").then(resp => {
            $scope.capacityItems = resp.data;
            $scope.capacityItems.forEach(color => {
            })
        });
    }
    $scope.resetCapacity = function(){
        $scope.capacity ={
        }
    }
    $scope.createCapacity = function (){
        var capacitys = angular.copy($scope.capacity);
        console.log(capacitys);
        $http.post("/rest/admin/accessoríes/saveCapacity",capacitys).then(resp => {
            $scope.capacityItems.push(capacitys);
            $scope.resetCapacity();
            $scope.findAllCapacity();
            alert('thêm mới dung lượng thành công');
        }).catch(error => {
            alert('thêm mới lỗi');
            console.log('lỗi', error);
        })
    }
    $scope.findAllCapacity();

    // thêm ảnh
    const  url = 'http://localhost:8080/rest/files/images/products';

    $scope.url = function(fileName){
        return `${url}/`+`${fileName}`;
    }
    $scope.fileNames=[];
    $scope.img= [];
    $scope.imagePr ={};
    $scope.listFile = function(){
        $http.get(url).then(res=>{
            $scope.fileNames = res.data;
            console.log($scope.fileNames,' ok',res.data);
            alert("đã update");
        }).catch(err=>{
            console.log('Load files failse',err);
        })
    }
    $scope.uploadFile = function(files){
        var form = new FormData();
        form.append('file',files[0]);
        $http.post(url,form,{
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined},
        }).then(res=>{
            $scope.imagePr.name =res.data.name;
            console.log('image',res);
            alert('đã load')

        }).catch(err=>{
            console.log('err',err);
        })
    }
    $scope.loadProducts = function(){
        $http.get(urlProduct+`/page/`+$scope.index).then(res=>{
            $scope.images = res.data;
            console.log('Load products success',res.data)
        }).catch(err=>{

            console.log('Load products failse',err.data);

        });
    }
    $scope.allImage = function () {
        $http.get("/rest/admin/accessoríes/allImage").then(resp => {
            $scope.img = resp.data;
            $scope.img.forEach(color => {
                console.log(resp.data +'  list');
            })
        });
    }

    $scope.saveImage = function (){
        var item = angular.copy($scope.imagePr);
        $http.post("/rest/admin/accessoríes/saveImage",item).then(resp => {
            $scope.img.push(item);
            Swal.fire(
                'Thông báo!',
                'Thêm mới ảnh thành công!',
                'success'
            )
            $scope.allImage();
        }).catch(error => {
            alert('thêm mới lỗi');
            console.log('lỗi', error);
        })
    }


})
