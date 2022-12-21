app.controller('ctrl_cate', function($scope, $http, $window,$rootScope) {
    $scope.form = {};
    $scope.category = [];
    $scope.index=1;
    $scope.check_first=false;
    $scope.check_last=true;
    $scope.check_prev = false;
    $scope.check_next = true;
    $scope.totalPages=0;
    $scope.form.type = 0;
    $scope.title={
        insert:'Thêm mới',
        update:'Cập nhật'
    };
    $scope.checkSubmit=false;
    $scope.typeValue = -1;
    $scope.statusValue = -1;
    $scope.valueSelectStatus = 1;
    $scope.listId = [];
    $rootScope.check = null;

    const jwtToken = localStorage.getItem("jwtToken")
    const token = {
        headers: {
            Authorization: `Bearer `+jwtToken
        }
    }

    const pathAPI = "http://localhost:8080/rest/staff/category";

    $scope.getAll =function (){
        $http.get(pathAPI+'/page',token).then(function(response) {
            $scope.category = response.data.content;
            $scope.totalPages = response.data.totalPages;
            $scope.check_last = true;
            $scope.check_next = true;
            if ($scope.index == $scope.totalPages){
                $scope.check_last =false;
                $scope.check_next = false;
            }
        }).catch(error=>{
            console.log(error);
        });
    }
    var select_Status = document.getElementById("selectStatus");
    var filter_Status = document.getElementById("filterStatus");
    var filter_Type = document.getElementById("filterType");
    var search = document.getElementById("search");

    $scope.clickCheckBox = function (id) {
        if ($scope.listId.length == 0){
            $scope.listId.push(id)
            console.log($scope.listId)
        }else {
            $scope.listId = $scope.listId.filter((item)=>item !== id)
            console.log($scope.listId)
        }
    }


    select_Status.value = 1;

    select_Status.onchange = function () {
        if (this.value != ""){
            $scope.valueSelectStatus = this.value;
        }
    }

    filter_Type.onchange = function () {
        if (this.value != ""){
            $scope.typeValue = this.value;
            $http.get(pathAPI+'/page?type='+this.value+'&status='+$scope.statusValue,token).then(function(response) {
                $scope.category = response.data.content;
                $scope.totalPages = response.data.totalPages;
                $scope.check_last =true;
                $scope.check_next = true;
                if ($scope.index == $scope.totalPages){
                    $scope.check_last =false;
                    $scope.check_next = false;
                }

            }).catch(error=>{
                console.log(error);
            });

        }
    }

    filter_Status.onchange = function () {
        if (this.value != ""){
            $scope.statusValue = this.value;
            $http.get(pathAPI+'/page?status='+this.value+'&type='+$scope.typeValue,token).then(function(response) {
                $scope.category = response.data.content;
                $scope.totalPages = response.data.totalPages;
                $scope.check_last =true;
                $scope.check_next = true;
                if ($scope.index == $scope.totalPages){
                    $scope.check_last =false;
                    $scope.check_next = false;
                }

            }).catch(error=>{
                console.log(error);
            });
        }
    }

    $scope.next=function(){
        $scope.check_first=true;
        $scope.check_prev=true;
        $scope.index++;
        if($scope.index==$scope.totalPages){
            $scope.check_first=true;
            $scope.check_last=false;
            $scope.check_next = false;
        }
        $http.get(pathAPI+'/page?page='+$scope.index,token).then(res=>{
            $scope.category = res.data.content;
            console.log('Load accessories success',res.data)
        }).catch(err=>{
            console.log('Load accessories failse',err.data);
        })
    }

    $scope.prev=function(){
        $scope.check_last=true;
        $scope.check_next = true;
        $scope.index--;

        if($scope.index==1){
            $scope.check_first=false;
            $scope.check_last=true;
            $scope.check_prev =false;
        }
        $http.get(pathAPI+'/page?page='+$scope.index,token).then(res=>{
            $scope.category = res.data.content;
            console.log('Load accessories success',res.data)
        }).catch(err=>{
            console.log('Load accessories failse',err.data);
        })
    }
    $scope.first=function(){
        $scope.check_first=false;
        $scope.check_last=true;
        $scope.check_prev = false;
        $scope.check_next = true;
        $scope.index=1;
        $http.get(pathAPI+'/page?page='+$scope.index,token).then(res=>{
            $scope.category = res.data.content;
            console.log('Load accessories success',res.data)
        }).catch(err=>{
            console.log('Load accessories failse',err.data);
        })
    }
    $scope.last=function(){
        $scope.check_first=true;
        $scope.check_last=false;
        $scope.check_next=false;
        $scope.check_prev = true;
        $scope.index=$scope.totalPages;
        $http.get(pathAPI+'/page?page='+$scope.index,token).then(res=>{
            $scope.category = res.data.content;
            console.log('Load accessories success',res.data)
        }).catch(err=>{
            console.log('Load accessories failse',err.data);
        })
    }

    $scope.onSave = function() {
        $scope.form.status = $scope.valueSelectStatus;
        $http.post(pathAPI+"/create", $scope.form,token).then(response => {
            if (response != null){
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
                    title: 'Thêm mới thành công!',
                })
                $scope.refresh();
            }else {
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
                    title: 'Tên thể loại đã tồn tại!',
                })
            }
        }).catch(error => {
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
                title: 'Tên thể loại đã tồn tại!',
            })
        });
    };

    $scope.delete = function(category) {
        Swal.fire({
            title: 'Bạn có chắc muốn xóa: '+category.name+'?',
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
                    title: 'Đang xóa: '+category.name+'!',
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
                        $http.post(`${pathAPI}/delete?id=${category.idCategory}`,category.idCategory,token).then(response=> {
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
                            $scope.category.splice($scope.category.indexOf(category), 1);
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
                                title:'Đã xảy ra lỗi!' ,
                            })
                        });
                        console.log('I was closed by the timer')
                    }
                })

            }
        })

    };

    $scope.edit = function(category) {
        $scope.form = angular.copy(category);
        $scope.checkSubmit=true;
        select_Status.value = $scope.form.status;
        $scope.valueSelectStatus = $scope.form.status;
    };

    $scope.onUpdate = function() {
        $scope.form.status= $scope.valueSelectStatus
        $http.put(pathAPI+'/update?id='+$scope.form.idCategory,
            $scope.form,token).then(response=> {
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
            $scope.index=1;
            $scope.check_first=false;
            $scope.check_last=true;
            $scope.check_next=true;
            $scope.check_prev = false;

            $scope.getAll();
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
        if($scope.form.idCategory) {
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
        $scope.form = {};
        $scope.form.type = 0;
        $scope.checkSubmit=false;
        $scope.getAll();
        if ($scope.totalPages > $scope.index){
            $scope.check_next = true;
            $scope.check_last = true;
        }
        filter_Type.value = "";
        filter_Status.value = "";
        $scope.typeValue = -1;
        $scope.statusValue = -1;
        search.value = "";
        select_Status.value = 1;
        $scope.valueSelectStatus = 1;

    };

    function debounce(cb, interval, immediate) {
        var timeout;

        return function() {
            var context = this, args = arguments;
            var later = function() {
                timeout = null;
                if (!immediate) cb.apply(context, args);
            };

            var callNow = immediate && !timeout;

            clearTimeout(timeout);
            timeout = setTimeout(later, interval);

            if (callNow) cb.apply(context, args);
        };
    };

    function keyPressCallback() {
        var input = document.getElementById('search');
        $http.get(pathAPI+'/page?name='+input.value+'&type='+$scope.typeValue+'&status='+$scope.statusValue,token).then(function(response) {
            $scope.category = response.data.content;
            $scope.totalPages = response.data.totalPages;
            $scope.check_last =true;
            $scope.check_next = true;
            if ($scope.index == $scope.totalPages){
                $scope.check_last =false;
                $scope.check_next = false;
            }

        }).catch(error=>{
            console.log(error);
        });
    }
    search.onkeypress = debounce(keyPressCallback, 500);



    $scope.getAll();
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