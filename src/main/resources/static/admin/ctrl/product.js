app.controller('product', function($scope, $http) {
    const pathAPI = "http://localhost:8080/rest/admin/product";
    $scope.formProduct = {};
    $scope.products = [];
    $scope.categories = [];
    $scope.colors= [];
    $scope.images=[];
    $scope.capacitys=[];
    $scope.rams=[];
    $scope.index=0;
    $scope.check_first=false;
    $scope.check_last=true;
    $scope.totalPages=0;
    $scope.title={
        insert:'Thêm mới',
        update:'Cập nhật'
    };
    $scope.checkSubmit=false;
    const now = new Date();
    $scope.getProducts =function (){
        $http.get(`${pathAPI}/page/0`).then(function(response) {
            $scope.products = response.data;
        }).catch(error=>{
            console.log(error);
        });
        $scope.getCategories();
        $scope.getTotalPages();
        $scope.getRam();
        $scope.getColor();
        $scope.getCapacity();
    }
    $scope.getTotalPages =function (){
        $http.get(pathAPI).then(function(response) {
            $scope.totalPages = Math.ceil(response.data.length/10);
        }).catch(error=>{
            console.log(error);
        });
    }
    $scope.getCategories=function(){
        $http.get(`${pathAPI}/category`).then(function(response) {
            $scope.categories = response.data;
        }).catch(error=>{
            console.log("error findByCate",error);
        });
    };
    $scope.getRam=function(){
        $http.get(`${pathAPI}/getAllRam`).then(function(response) {
            $scope.rams = response.data;
        }).catch(error=>{
            console.log(error + 'looxi');
        });
    };
    $scope.getColor=function(){
        $http.get(`${pathAPI}/getAllColor`).then(function(response) {
            $scope.colors = response.data;
        }).catch(error=>{
            console.log(error + 'looxi');
        });
    };
    $scope.getCapacity=function(){
        $http.get(`${pathAPI}/getAllCapacity`).then(function(response) {
            $scope.capacitys = response.data;
        }).catch(error=>{
            console.log(error + 'looxi');
        });
    };
    $scope.getImageProduct=function(){
        $http.get(`rest/files/images/products`).then(function(response) {
            $scope.images = response.data;
            console.log(reponse.data );
            alert('dddd' + response.data);
        }).catch(error=>{
            console.log("lỗi" +error );
        });
    };

    $scope.onSave = function() {
        /*$http.post(pathAPI+'/save-file', $scope.images).then(response => {
        })*/
        $scope.formProduct.status=false;
        $scope.formProduct.createDate=now;
        $scope.formProduct.category ={
            idCategory: $scope.formProduct.category
        };
        $scope.formProduct.ram ={
            idRam: $scope.formProduct.ram
        };
        $scope.formProduct.color ={
            idColor: $scope.formProduct.color
        };
        $scope.formProduct.capacity ={
            idCapacity: $scope.formProduct.capacity
        };

        $http.post('/rest/admin/product/saveProduct', $scope.formProduct).then(response => {
            console.log("ddd " +$scope.formProduct);
            const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 1500,
                timerProgressBar: true,
                didOpen: (toast) => {
                    toast.addEventListener('mouseenter', Swal.stopTimer)
                    toast.addEventListener('mouseleave', Swal.resumeTimer)
                }
            })

            Toast.fire({
                icon: 'success',
                title: 'Thêm mới thành công!',
            })
            $scope.refresh();
        }).catch(error => {
            const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 1500,
                timerProgressBar: true,
                didOpen: (toast) => {
                    toast.addEventListener('mouseenter', Swal.stopTimer)
                    toast.addEventListener('mouseleave', Swal.resumeTimer)
                }
            })

            Toast.fire({
                icon: 'error',
                title: 'Thêm mới thất bại!',
            })
        });
    };

    $scope.delete = function(formProduct) {
        Swal.fire({
            title: 'Bạn có chắc muốn xóa: '+formProduct.name+'?',
            text: "Xóa không thể khôi phục lại!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                let timerInterval
                Swal.fire({
                    title: 'Đang xóa: '+formProduct.name+'!',
                    html: 'Vui lòng chờ <b></b> milliseconds.',
                    timer: 1500,
                    timerProgressBar: true,
                    didOpen: () => {
                        Swal.showLoading()
                        const b = Swal.getHtmlContainer().querySelector('b')
                        timerInterval = setInterval(() => {
                            b.textContent = Swal.getTimerLeft()
                        }, 100)
                    },
                    willClose: () => {
                        clearInterval(timerInterval)
                    }
                }).then((result) => {
                    /* Read more about handling dismissals below */
                    if (result.dismiss === Swal.DismissReason.timer) {
                        $http.delete(`${pathAPI}/delete/${formProduct.idProduct}`).then(response=> {
                        const Toast = Swal.mixin({
                            toast: true,
                            position: 'top-end',
                            showConfirmButton: false,
                            timer: 3000,
                            timerProgressBar: true,
                            didOpen: (toast) => {
                                toast.addEventListener('mouseenter', Swal.stopTimer)
                                toast.addEventListener('mouseleave', Swal.resumeTimer)
                            }
                        })

                        Toast.fire({
                            icon: 'success',
                            title:'Xóa thành công!'
                        })
                        $scope.products.splice($scope.products.indexOf(formProduct), 1);
                    }).catch(error=>{
                        const Toast = Swal.mixin({
                            toast: true,
                            position: 'top-end',
                            showConfirmButton: false,
                            timer: 3000,
                            timerProgressBar: true,
                            didOpen: (toast) => {
                                toast.addEventListener('mouseenter', Swal.stopTimer)
                                toast.addEventListener('mouseleave', Swal.resumeTimer)
                            }
                        })

                        Toast.fire({
                            icon: 'error',
                            title:'Đã xảy ra lỗi!' ,
                        })
                    });
                        console.log('I was closed by the timer')
                    }
                })

            }
        })

    };
    $scope.edit = function(formProduct) {
        $scope.formProduct = angular.copy(formProduct);
        $scope.formProduct.image = formProduct.image;
        $scope.formProduct.category = formProduct.category.idCategory;
        $scope.formProduct.ram = formProduct.ram.idRam;
        $scope.formProduct.color = formProduct.color.idColor;
        $scope.formProduct.capacity = formProduct.capacity.idCapacity;
        $scope.checkSubmit=true;
    };
    $scope.onUpdate = function() {
        $scope.formProduct.category ={
            idCategory: $scope.formProduct.category
        };
        $scope.formProduct.ram ={
            idRam: $scope.formProduct.ram
        };
        $scope.formProduct.color ={
            idColor: $scope.formProduct.color
        };
        $scope.formProduct.capacity ={
            idCapacity: $scope.formProduct.capacity
        };
        $http.put(pathAPI+'/'+$scope.formProduct.idProduct, $scope.formProduct).then(response=> {
            const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true,
                didOpen: (toast) => {
                    toast.addEventListener('mouseenter', Swal.stopTimer)
                    toast.addEventListener('mouseleave', Swal.resumeTimer)
                }
            })

            Toast.fire({
                icon: 'success',
                title:'Cập nhật thành công!' ,
            })
            $scope.refresh();
        }).catch(error=>{
            const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true,
                didOpen: (toast) => {
                    toast.addEventListener('mouseenter', Swal.stopTimer)
                    toast.addEventListener('mouseleave', Swal.resumeTimer)
                }
            })

            Toast.fire({
                icon: 'error',
                title: 'Cập nhật thất bại!',
            })
        })
    };
    $scope.doSubmit = function() {
        if($scope.formProduct.idProduct) {
            let timerInterval
            Swal.fire({
                title: 'Đang cập nhật!',
                html: 'Vui lòng chờ <b></b> milliseconds.',
                timer: 1500,
                timerProgressBar: true,
                didOpen: () => {
                    Swal.showLoading()
                    const b = Swal.getHtmlContainer().querySelector('b')
                    timerInterval = setInterval(() => {
                        b.textContent = Swal.getTimerLeft()
                    }, 100)
                },
                willClose: () => {
                    clearInterval(timerInterval)
                }
            }).then((result) => {
                /* Read more about handling dismissals below */
                if (result.dismiss === Swal.DismissReason.timer) {
                    $scope.onUpdate();
                    console.log('I was closed by the timer')
                }
            })
        }else{
            let timerInterval
            Swal.fire({
                title: 'Đang lưu mới!',
                html: 'Vui lòng chờ <b></b> milliseconds.',
                timer: 1500,
                timerProgressBar: true,
                didOpen: () => {
                    Swal.showLoading()
                    const b = Swal.getHtmlContainer().querySelector('b')
                    timerInterval = setInterval(() => {
                        b.textContent = Swal.getTimerLeft()
                    }, 100)
                },
                willClose: () => {
                    clearInterval(timerInterval)
                }
            }).then((result) => {
                /* Read more about handling dismissals below */
                if (result.dismiss === Swal.DismissReason.timer) {
                    $scope.onSave();
                    console.log('I was closed by the timer')
                }
            })
        }
    };
    $scope.refresh = function() {
        $scope.formProduct.idProduct=null;
        $scope.formProduct= {};
        $scope.formProduct.status=false;
        $scope.formProduct.image='logo-mobile.png';
        $scope.checkSubmit=false;
        $scope.getProducts();
    };
    var urlImage=`http://localhost:8080/rest/files/images/products`;
    $scope.url = function(fileName){
        return `${urlImage}/`+`${fileName}`;
    }
    $scope.fileNames=[];
    $scope.listFile = function(){
        $http.get(urlImage ).then(res=>{
            $scope.fileNames = res.data;
            console.log('ok',res.data);
            alert('ddddddddd');
        }).catch(err=>{
            console.log('Load files failse',err);
        })
    }
    $scope.uploadFile = function(files){
        var form = new FormData();
        form.append('file',files[0]);
        $http.post(urlImage,form,{
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).then(res=>{
            console.log('image',res.data);
            $scope.product.image= res.data.name;
        }).catch(err=>{
            console.log('err',err);
        })
    }
    $scope.loadProducts = function(){
        $http.get(urlImage+`/page/`+$scope.index).then(res=>{
            $scope.products = res.data;
            console.log('Load products success',res.data)
        }).catch(err=>{
            console.log('Load products failse',err.data);
        });
    }
    // pagination
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
        $http.get(pathAPI+`/page/`+$scope.index).then(res=>{
            $scope.formProduct = res.data;
            console.log('Load accessories success',res.data)
        }).catch(err=>{
            console.log('Load accessories failse',err.data);
        })
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
        $http.get(pathAPI+`/page/`+$scope.index).then(res=>{
            $scope.formProduct = res.data;
            console.log('Load accessories success',res.data)
        }).catch(err=>{
            console.log('Load accessories failse',err.data);
        })
    }
    $scope.first=function(){
        $scope.check_first=false;
        $scope.check_last=true;
        $scope.index=0;
        $http.get(pathAPI+`/page/`+$scope.index).then(res=>{
            $scope.formProduct = res.data;
            console.log('Load accessories success',res.data)
        }).catch(err=>{
            console.log('Load accessories failse',err.data);
        })
    }
    $scope.last=function(){
        $scope.check_first=true;
        $scope.check_last=false;
        $scope.index=$scope.totalPages-1;
        $http.get(pathAPI+`/page/`+$scope.index).then(res=>{
            $scope.formProduct = res.data;
            console.log('Load accessories success',res.data)
        }).catch(err=>{
            console.log('Load accessories failse',err.data);
        })
    }

    $scope.getProducts();

});