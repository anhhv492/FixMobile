app.controller('rest_accessory', function($scope, $http) {
    $scope.form = {};
    $scope.accessories = [];
    $scope.categories = [];
    $scope.title={
        insert:'Thêm mới',
        update:'Cập nhật'
    };
    $scope.checkSubmit=false;
    const now = new Date();
    const pathAPI = "http://localhost:8080/rest/admin/accessory";
    $scope.getAll =function (){
        $http.get(pathAPI).then(function(response) {
            $scope.accessories = response.data;
        }).catch(error=>{
            console.log(error);
        });
        $http.get(`${pathAPI}/cate`).then(function(response) {
            $scope.categories = response.data;
        }).catch(error=>{
            console.log("error findByCate",error);
        });
    }
    $scope.onSave = function() {
        $http.post(pathAPI+'/save-file', $scope.imageFile).then(response => {
        })
        $scope.form.status=false;
        $scope.form.createDate=now;
        $scope.form.category ={
            idCategory: $scope.form.category
        };
        $http.post(pathAPI, $scope.form).then(response => {
            Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: 'Thêm mới thành công!',
                showConfirmButton: false,
                timer: 1500
            })
            $scope.getAll();
        }).catch(error => {
            Swal.fire({
                position: 'top-end',
                icon: 'error',
                title: 'Thêm mới thất bại!',
                showConfirmButton: false,
                timer: 1500
            })
        });
    };
    $scope.delete = function(accessory) {
        Swal.fire({
            title: 'Bạn có chắc muốn xóa: '+accessory.name+'?',
            text: "Xóa không thể khôi phục lại!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                $http.delete(`${pathAPI}/delete/${accessory.idAccessory}`).then(response=> {
                    Swal.fire(
                        'Xóa thành công!',
                        'Nhấn ok để tiếp tục.',
                        'success'
                    )
                    $scope.accessories.splice($scope.accessories.indexOf(accessory), 1);
                }).catch(error=>{
                    Swal.fire(
                        'Xóa thất bại!',
                        'Đã xảy ra lỗi.',
                        'error'
                    )
                });
            }
        })

    };
    $scope.edit = function(accessory) {
        $scope.form = angular.copy(accessory);
        $scope.form.category = accessory.category.idCategory;
        $scope.checkSubmit=true;
    };
    $scope.onUpdate = function() {
        $scope.form.category ={
            idCategory: $scope.form.category
        };
        $http.put(pathAPI+'/'+$scope.form.idAccessory, $scope.form).then(response=> {
            Swal.fire({
                position: 'top-end',
                icon: 'success',
                title:'Cập nhật thành công!' ,
                showConfirmButton: false,
                timer: 1500
            })
            $scope.refresh();
        }).catch(error=>{
            Swal.fire({
                position: 'top-end',
                icon: 'error',
                title: 'Cập nhật thất bại!',
                showConfirmButton: false,
                timer: 1500
            })
        })
    };
    $scope.doSubmit = function() {
        if($scope.form.idAccessory) {
            $scope.onUpdate();
        }else{
            $scope.onSave();
        }
    };
    $scope.refresh = function() {
        $scope.form = {};
        $scope.checkSubmit=false;
    };
    var url=`http://localhost:8080/rest/files/images`;
    $scope.url = function(fileName){
        return `${url}/`+`${fileName}`;
    }
    $scope.fileNames=[];
    $scope.listFile = function(){
        $http.get(url).then(res=>{
            $scope.fileNames = res.data;
            console.log('ok',res);
        }).catch(err=>{
            console.log('Load files failse',err);
        })
    }
    $scope.uploadFile = function(files){
        var form = new FormData();
        form.append('file',files[0]);
        $http.post(url,form,{
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).then(res=>{
            $scope.form.image=res.data.name;
            console.log('image',res);
        }).catch(err=>{
            console.log('err',err);
        })
    }
    $scope.loadProducts = function(){
        $http.get(urlProduct+`/page/`+$scope.index).then(res=>{
            $scope.products = res.data;
            console.log('Load products success',res.data)
        }).catch(err=>{
            console.log('Load products failse',err.data);
        });
    }
    $scope.getAll();
});