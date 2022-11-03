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
    $scope.currentPage = 0;
    $scope.title={
        insert:'Thêm mới',
        update:'Cập nhật'
    };
    $scope.checkButton = true;
    $scope.checkSubmit=false;
    $scope.getProducts =function (){
        $http.get(`${pathAPI}/page/pushedlist?page=0`).then(function(response) {
            $scope.products = response.data.list;
            $scope.totalPages = response.data.totalPages;
            $scope.currentPage = response.data.currentPage;
        }).catch(error=>{
            console.log(error);
        });
        $scope.getCategories();
        $scope.getRam();
        $scope.getColor();
        $scope.getCapacity();
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
        $scope.formProduct.category = formProduct.category.idCategory;
        $scope.formProduct.ram = formProduct.ram.idRam;
        $scope.formProduct.color = formProduct.color.idColor;
        $scope.formProduct.capacity = formProduct.capacity.idCapacity;
        $scope.checkSubmit=true;
        $scope.checkButton=false;
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
                timer: 2500,
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
        $scope.checkButton=true;
        $scope.getProducts();
    };

    //$scope.files={};
    $scope.uploadFile = function(files){
        $scope.files = files;
        console.log($scope.files);
    }
    const headers = {
        headers: {
            'Content-Type':'multipart/form-data'
        },
        data: { test: true }
    }
    // thêm mới
    $scope.onSave = function() {
        var formData = new FormData();
        angular.forEach($scope.files, function(file) {
            formData.append('files', file);
        });
        formData.append('name', $scope.formProduct.name);
        formData.append('note', $scope.formProduct.note);
        formData.append('size', $scope.formProduct.size);
        formData.append('price',$scope.formProduct.price);
        formData.append('camera',$scope.formProduct.camera);
        formData.append('status',$scope.formProduct.status=false)
        formData.append( 'category',$scope.formProduct.category)
        formData.append('ram',$scope.formProduct.ram)
        formData.append('color',$scope.formProduct.color)
        formData.append('capacity',$scope.formProduct.capacity)
        formData.append('imei',$scope.formProduct.imei)
        console.log($scope.formProduct.category)
        let req = {
            method: 'POST',
            url: '/rest/admin/product/saveProduct',
            headers: {
                'Content-Type': undefined
                // or  'Content-Type':'application/json'
            },
            data: formData
        }
        let timerInterval
        Swal.fire({
            title: 'Đang thêm  mới vui lòng chờ!',
            html: 'Vui lòng chờ <b></b> milliseconds.',
            timer: 5500,
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
        });
        $http(req).then(response => {
            console.log("ddd " + response);
            const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 3500,
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
            $scope.getProducts();
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
        $http.get(pathAPI+`/page/pushedlist?page=`+$scope.index).then(res=>{
            $scope.products = res.data.list;
            console.log('Load product thành công',res.data.list);
        }).catch(err=>{
            console.log('Load product failse',err.data.list);
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
        $http.get(pathAPI+`/page/pushedlist?page=`+$scope.index).then(res=>{
            $scope.products = res.data.list;
            console.log('Load product success',res.data.list)
        }).catch(err=>{
            console.log('Load product failse',err.data);
        })
    }
    $scope.first=function(){
        $scope.check_first=false;
        $scope.check_last=true;
        $scope.index=0;
        $http.get(pathAPI+`/page/pushedlist?page=`+$scope.index).then(res=>{
            $scope.products = res.data.list;
        }).catch(err=>{
            console.log('Load accessories failse',err.data);
        })
    }
    $scope.last=function(){
        $scope.check_first=true;
        $scope.check_last=false;
        $scope.index=$scope.totalPages-1;
        $http.get(pathAPI+`/page/pushedlist?page=`+$scope.index).then(res=>{
            $scope.products = res.data.list;
            console.log('Load product success',res.data.list)
        }).catch(err=>{
            console.log('Load product failse',err.data);
        })
    }

    $scope.xcellData = function (files){
        var form = new FormData();
        form.append('file',files[0]);
        let timerInterval
        Swal.fire({
            title: 'Đang thêm hàng loạt!',
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
            if (result.dismiss === Swal.DismissReason.timer) {
                $http.post(pathAPI+'/readExcel',form,{
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                }).then(res=>{
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
                        title: 'Thêm file Excel thành công'
                    })
                    console.log('excel',res);
                    $scope.getProducts();
                }).catch(err=>{
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
                        title: 'Có lỗi xảy ra!'
                    })
                    console.log('err',err);
                })
                console.log('I was closed by the timer')
            }
        })
    }

    $scope.getProducts();
});
