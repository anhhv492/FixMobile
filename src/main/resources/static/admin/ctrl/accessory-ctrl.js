app.controller('rest_accessory', function($scope, $http,$rootScope,$window) {
    const pathAPI = "http://localhost:8080/rest/staff/accessory";
    $scope.form = {};
    $scope.accessories = [];
    $scope.categories = [];
    $scope.index=0;
    $scope.check_first=false;
    $scope.check_last=true;
    $scope.totalPages=0;
    $rootScope.check = null;
    $scope.title={
        insert:'Thêm mới',
        update:'Cập nhật'
    };
    const jwtToken = localStorage.getItem("jwtToken")
    const token = {
        headers: {
            Authorization: `Bearer `+jwtToken
        }
    }

    $scope.message = function (mes){
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
            title: mes,
        })
    }
    $scope.error =  function (err){
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
            title: err,
        })
    }
    $scope.checkSubmit=false;
    const now = new Date();
    $scope.loadAccessories =function (){
        $http.get(`${pathAPI}/page/0`,token).then(function(response) {
            $scope.accessories = response.data;
            console.log("access",response.data);
        }).catch(error=>{
            console.log("access error",error);
        });
        $scope.getCategories();
        $scope.getTotalPages();
    }
    $scope.getTotalPages =function (){
        $http.get(pathAPI,token).then(function(response) {
            $scope.totalPages = Math.ceil(response.data.length/5);
        }).catch(error=>{
            console.log(error);
        });
    }
    $scope.getCategories=function(){
        $http.get(`${pathAPI}/cate`,token).then(function(response) {
            $scope.categories = response.data;
        }).catch(error=>{
            $scope.categories=[];
            console.log("error findByCate",error);
        });
    };
    $scope.onSave = function() {
        var formData = new FormData();
        angular.forEach($scope.files, function(image) {
            formData.append('image', image);
        });
        formData.append("name",$scope.form.name);
        formData.append("quantity",$scope.form.quantity);
        formData.append("color",$scope.form.color);
        formData.append("price",$scope.form.price);
        formData.append("note",$scope.form.note);
        formData.append("category", $scope.form.category);
        let req = {
            method: 'POST',
            url: '/rest/staff/accessory/create',
            headers: {
                'Content-Type': undefined,
                Authorization: `Bearer `+jwtToken
                // or  'Content-Type':'application/json'
            },
            data: formData
        }
        console.log("Bắt đầu thêm mới")
        let timerInterval
        Swal.fire({
            title: 'Đang thêm  ảnh vui lòng chờ!',
            html: 'Vui lòng chờ <b></b> milliseconds.',
            timer: 3000,
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
            console.log(response.data);
            $scope.message("Thêm mới thành công");
        })
    };
    $scope.changeStatus = function(accessory) {
        Swal.fire({
            title: 'Thay đổi trạng thái: '+accessory.name+'?',
            text: "Xác nhận đẻ tiếp tục!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Xác nhận!'
        }).then((result) => {
            if (result.isConfirmed) {
                $http.put(`${pathAPI}/change/${accessory.idAccessory}`,accessory,token).then(response=> {
                    const Toast = Swal.mixin({
                        toast: true,
                        position: 'top-end',
                        showConfirmButton: false,
                        timer: 2000,
                        timerProgressBar: true,
                        didOpen: (toast) => {
                            toast.addEventListener('mouseenter', Swal.stopTimer)
                            toast.addEventListener('mouseleave', Swal.resumeTimer)
                        }
                    })

                    Toast.fire({
                        icon: 'success',
                        title:'Thay đổi thành công!'
                    })
                    console.log(response.data);
                    $scope.accessories.find(item=>item.idAccessory==accessory.idAccessory).status=!accessory.status;
                    $scope.accessories.find(item=>item.idAccessory==accessory.idAccessory).quantity=0;
                }).catch(error=>{
                    const Toast = Swal.mixin({
                        toast: true,
                        position: 'top-end',
                        showConfirmButton: false,
                        timer: 2000,
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

    };
    $scope.edit = function(accessory) {
        $scope.form = angular.copy(accessory);
        $scope.form.image = accessory.image;
        $scope.form.category = accessory.category.idCategory;
        $scope.checkSubmit=true;
        document.getElementById('manage-tab').click();
    };
    $scope.onUpdate = function() {
        var accessory = angular.copy($scope.form);
        console.log(accessory.username)
        var formData = new FormData();
        angular.forEach($scope.files, function(image) {
            formData.append('image', image);
        });
        formData.append("name",$scope.form.name);
        formData.append("quantity",$scope.form.quantity);
        formData.append("color",$scope.form.color);
        formData.append("price",$scope.form.price);
        formData.append("note",$scope.form.note);
        formData.append("category", $scope.form.category);
        let req = {
            method: 'POST',
            url: '/rest/staff/accessory/update?id='+accessory.idAccessory,
            headers: {
                'Content-Type': undefined,
                Authorization: `Bearer `+jwtToken
                // or  'Content-Type':'application/json'
            },
            data: formData
        }
        console.log("Bắt đầu cập nhật")
        let timerInterval
        Swal.fire({
            title: 'Đang cập nhật vui lòng chờ!',
            html: 'Vui lòng chờ <b></b> milliseconds.',
            timer: 2000,
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
                timer: 1500,
                timerProgressBar: true,
                didOpen: (toast) => {
                    toast.addEventListener('mouseenter', Swal.stopTimer)
                    toast.addEventListener('mouseleave', Swal.resumeTimer)
                }
            })

            Toast.fire({
                icon: 'success',
                title: 'Cập nhật thành công!'
            })
            $scope.loadAccessories();
        });
    };
    // submit form
    $scope.doSubmit = function() {
        if($scope.form.idAccessory) {
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
                    document.getElementById('list-tab').click();
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
        $scope.form.idAccessory=null;
        $scope.form.status=false;
        $scope.form.image='logo-mobile.png';
        $scope.checkSubmit=false;
        $scope.getPageAccessories();
    };
    var urlImage=`http://localhost:8080/rest/files/images/accessories`;
    $scope.url = function(fileName){
        return `${urlImage}/`+`${fileName}`;
    }
    $scope.fileNames=[];
    $scope.listFile = function(){
        $http.get(urlImage,token).then(res=>{
            $scope.fileNames = res.data;
            console.log('ok',res);
        }).catch(err=>{
            console.log('Load files failse',err);
        })
    }
    $scope.uploadFile = function(files){
        $scope.files = files;
        console.log($scope.files)
    }
    $scope.readExcel = function(files){
        let form = new FormData();
        form.append('file',files[0]);
        let timerInterval
        Swal.fire({
            title: 'Đang thêm hàng loạt!',
            html: 'Vui lòng chờ <b></b> milliseconds.',
            timer: 3000,
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
                $http.post('http://localhost:8080/rest/files/images/read-excel',form,{
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
                    $scope.loadAccessories();
                    console.log('excel',res);
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
                    $scope.loadAccessories();
                    console.log('err',err);
                })
                console.log('I was closed by the timer')
            }
        })
    }
    $scope.loadProducts = function(){
        $http.get(urlProduct+`/page/`+$scope.index,token).then(res=>{
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
        $http.get(pathAPI+`/page/`+$scope.index,token).then(res=>{
            $scope.accessories = res.data;
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
        $http.get(pathAPI+`/page/`+$scope.index,token).then(res=>{
            $scope.accessories = res.data;
            console.log('Load accessories success',res.data)
        }).catch(err=>{
            console.log('Load accessories failse',err.data);
        })
    }
    $scope.first=function(){
        $scope.check_first=false;
        $scope.check_last=true;
        $scope.index=0;
        $http.get(pathAPI+`/page/`+$scope.index,token).then(res=>{
            $scope.accessories = res.data;
            console.log('Load accessories success',res.data)
        }).catch(err=>{
            console.log('Load accessories failse',err.data);
        })
    }
    $scope.last=function(){
        $scope.check_first=true;
        $scope.check_last=false;
        $scope.index=$scope.totalPages-1;
        $http.get(pathAPI+`/page/`+$scope.index,token).then(res=>{
            $scope.accessories = res.data;
            console.log('Load accessories success',res.data)
        }).catch(err=>{
            console.log('Load accessories failse',err.data);
        })
    }
    $scope.getPageAccessories = function(){
        $http.get(pathAPI+`/page/`+$scope.index,token).then(res=>{
            $scope.accessories = res.data;
            console.log('Load accessories success',res.data)
        }).catch(err=>{
            console.log('Load accessories failse',err.data);
        })
    }
    $scope.loadAccessories();

    $scope.logOut = function (){
        $window.location.href = "http://localhost:8080/views/index.html#!/login"
        Swal.fire({
            icon: 'error',
            title: 'Vui lòng đăng nhập lại!!',
            text: 'Tài khoản của bạn không có quyền truy cập!!',
        })
    }

    $scope.checkLogin = function () {
        if (jwtToken == null){
            $scope.logOut();
        }else {
            $http.get("http://localhost:8080/rest/user/getRole",token).then(respon =>{
                if (respon.data.name === "USER"){
                    $scope.logOut();
                }else if (respon.data.name === "ADMIN"){
                    $rootScope.check = null;
                }else {
                    $rootScope.check = "OK";
                }
            })
        }
    }

    $scope.checkLogin();

});