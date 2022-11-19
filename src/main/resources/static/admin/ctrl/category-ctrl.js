app.controller('ctrl_cate', function($scope, $http) {
    $scope.form = {};
    $scope.category = [];
    $scope.index=0;
    $scope.check_first=false;
    $scope.check_last=true;
    $scope.check_next=true;
    $scope.check_prev=true;
    $scope.totalPages=0;
    $scope.currentPage = 0;
    $scope.title={
        insert:'Thêm mới',
        update:'Cập nhật'
    };
    $scope.checkSubmit=false;

    const jwtToken = localStorage.getItem("jwtToken")
    const token = {
        headers: {
            Authorization: `Bearer `+jwtToken
        }
    }

    const pathAPI = "http://localhost:8080/rest/staff/category";

    $scope.getAll =function (){
        $http.get(pathAPI+'/page/published?page=0',token).then(function(response) {
            $scope.category = response.data.categories
            $scope.totalPages = response.data.totalPages;
            $scope.currentPage = response.data.currentPage;
            $scope.first();
            $scope.check_prev = false;
            $scope.check_first = false;
            $scope.check_next = true;
            $scope.check_last = true;
            $scope.index=0;

        }).catch(error=>{
            console.log(error);
        });
    }

    $scope.next=function(){
        $scope.check_first=true;
        $scope.index++;
        $scope.check_prev =true;
        if($scope.index>=$scope.totalPages){
            $scope.index=0;
            $scope.check_first=false;
            $scope.check_last=true;
        }
        if($scope.index==$scope.totalPages-1){
            $scope.check_first=true;
            $scope.check_last=false;
            $scope.check_next = false;
        }
        $http.get(pathAPI+'/page/published?page='+$scope.index,token).then(res=>{
            $scope.category = res.data.categories;
            console.log('Load accessories success',res.data)
        }).catch(err=>{
            console.log('Load accessories failse',err.data);
        })
    }

    $scope.prev=function(){
        $scope.check_last=true;
        $scope.index--;
        $scope.check_next = true
        if($scope.index<0){
            $scope.index=$scope.totalPages-1;
            $scope.check_first=true;
            $scope.check_last=false;
        }
        if($scope.index==0){
            $scope.check_first=false;
            $scope.check_last=true;
            $scope.check_prev = false;
        }
        $http.get(pathAPI+'/page/published?page='+$scope.index,token).then(res=>{
            $scope.category = res.data.categories;
            console.log('Load accessories success',res.data)
        }).catch(err=>{
            console.log('Load accessories failse',err.data);
        })
    }
    $scope.first=function(){
        $scope.check_first=false;
        $scope.check_last=true;
        $scope.check_prev = false;
        $scope.check_next = true
        $scope.index=0;
        $http.get(pathAPI+'/page/published?page='+$scope.index,token).then(res=>{
            $scope.category = res.data.categories;
            console.log('Load accessories success',res.data)
        }).catch(err=>{
            console.log('Load accessories failse',err.data);
        })
    }
    $scope.last=function(){
        $scope.check_first=true;
        $scope.check_last=false;
        $scope.check_prev= true;
        $scope.check_next=false;
        $scope.index=$scope.totalPages-1;
        $http.get(pathAPI+'/page/published?page='+$scope.index,token).then(res=>{
            $scope.category = res.data.categories;
            console.log('Load accessories success',res.data)
        }).catch(err=>{
            console.log('Load accessories failse',err.data);
        })
    }

    $scope.onSave = function() {

        $http.post(pathAPI+"/create", $scope.form,token).then(response => {
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
                title: 'Thêm mới thất bại!',
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
            confirmButtonText: 'Xác nhận!!',
            cancelButtonText: 'Hủy'
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
                        $http.delete(`${pathAPI}/delete/${category.idCategory}`,token).then(response=> {
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
    };

    $scope.onUpdate = function() {
        $http.put(pathAPI+'/update/'+$scope.form.idCategory, $scope.form,token).then(response=> {
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
            $scope.last();
            $scope.getAll();
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
        $scope.checkSubmit=false;
        $scope.getAll();
    };


    $scope.getAll();
    $scope.refresh();
});