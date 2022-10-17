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
    const pathAPI = "http://localhost:8080/rest/admin/accessoríes";
    $scope.index=0;
    $scope.check_firsts=false;
    $scope.check_lasts=true;
    $scope.totalPagess=0;
    $scope.currentPages = 0;
    $scope.next=function(){
        $scope.check_firsts=true;
        $scope.index++;
        if($scope.index>=$scope.totalPagess){
            $scope.index=0;
            $scope.check_firsts=false;
            $scope.check_lasts=true;
        }
        if($scope.index==$scope.totalPagess-1){
            $scope.check_firsts=true;
            $scope.check_lasts=false;
        }
        $http.get(pathAPI+'/page/ram?page='+$scope.index).then(res=>{
            $scope.ram = res.data.items;
            console.log('Load accessories success',res.data)
        }).catch(err=>{
            console.log('Load accessories failse',err.data);
        })
    }

    $scope.prev=function(){
        $scope.check_lasts=true;
        $scope.index--;
        if($scope.index<0){
            $scope.index=$scope.totalPagess-1;
            $scope.check_firsts=true;
            $scope.check_lasts=false;
        }
        if($scope.index==0){
            $scope.check_firsts=false;
            $scope.check_lasts=true;
        }
        $http.get(pathAPI+'/page/ram?page='+$scope.index).then(res=>{
            $scope.ram = res.data.items;
            console.log('Load accessories success',res.data)
        }).catch(err=>{
            console.log('Load accessories failse',err.data);
        })
    }
    $scope.first=function(){
        $scope.check_firsts=false;
        $scope.check_lasts=true;
        $scope.index=0;
        $http.get(pathAPI+'/page/ram?page='+$scope.index).then(res=>{
            $scope.ram = res.data.items;
            console.log('Load accessories success',res.data)
        }).catch(err=>{
            console.log('Load accessories failse',err.data);
        })
    }
    $scope.last=function(){
        $scope.check_firsts=true;
        $scope.check_lasts=false;
        $scope.index=$scope.totalPagess-1;
        $http.get(pathAPI+'/page/ram?page='+$scope.index).then(res=>{
            $scope.ram = res.data.items;
            console.log('Load accessories success',res.data)
        }).catch(err=>{
            console.log('Load accessories failse',err.data);
        })
    }

    $scope.create = function (){
        var item = angular.copy($scope.accseries);
        console.log(item);
        $http.post("/rest/admin/accessoríes",item).then(resp => {
            $scope.items.push(item);
            $scope.reset();
            $scope.initialize();
            Swal.fire(
                'Thông báo!',
                'Thêm ram mới thành công!',
                'success'
            )
        }).catch(error => {
            alert('thêm mới lỗi');
            console.log('lỗi', error);
        })
    }
    $scope.delete = function(items) {
        Swal.fire({
            title: 'Bạn có chắc muốn xóa: '+ items.name+'?',
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
                    title: 'Đang xóa: '+items.name+'!',
                    html: 'Vui lòng chờ <b></b> milliseconds.',
                    timer: 800,
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
                        $http.delete(`/rest/admin/accessoríes/delete/${items.idRam}`).then(response=> {
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
                            $scope.items.splice($scope.items.indexOf(items), 1);
                            Toast.fire({
                                icon: 'success',
                                title:'Xóa thành công!'
                            })
                            $scope.reset();
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
    $scope.deleteColor = function(colorItems) {
        Swal.fire({
            title: 'Bạn có chắc muốn xóa: '+ colorItems.name+'?',
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
                    title: 'Đang xóa: '+colorItems.name+'!',
                    html: 'Vui lòng chờ <b></b> milliseconds.',
                    timer: 800,
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
                        $http.delete(`/rest/admin/accessoríes/deleteColor/${colorItems.idColor}`).then(response=> {
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
                            $scope.colorItems.splice($scope.colorItems.indexOf(colorItems), 1);
                            Toast.fire({
                                icon: 'success',
                                title:'Màu này đã xóa thành công!'
                            })
                            $scope.reset();
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

    $scope.allImage = function () {
        $http.get("/rest/admin/accessoríes/allImage").then(resp => {
            $scope.img = resp.data;
            $scope.img.forEach(image => {
                console.log(resp.data +'  list');
            })
        });
    }
    $scope.resetImage = function(){
        $scope.imagePr ={
        }
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
            $scope.resetImage();
            $scope.allImage();
        }).catch(error => {
            alert('thêm mới lỗi');
            console.log('lỗi', error);
        })
    }
    $scope.deleteImage = function(img) {
        Swal.fire({
            title: 'Bạn có chắc muốn xóa: '+ img.name+'?',
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
                    title: 'Đang xóa: '+img.name+'!',
                    html: 'Vui lòng chờ <b></b> milliseconds.',
                    timer: 800,
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
                        $http.delete(`/rest/admin/accessoríes/${img.idImage}`).then(response=> {
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
                            $scope.img.splice($scope.img.indexOf(img), 1);
                            Toast.fire({
                                icon: 'success',
                                title:'Xóa thành công!'
                            })
                            $scope.resetImage();
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
                    }
                })

            }
        })

    };

    $scope.allImage();


})
