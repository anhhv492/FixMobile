app.controller("account-ctrl", function ($scope, $http,$window) {
    $scope.accounts = [];
    $scope.roles = [];
    $scope.form = {};
    $scope.form.createDate = new Date();
    $scope.form.gender = true;
    $scope.index = 1;
    $scope.check_first = false;
    $scope.check_last = true;
    $scope.check_prev = false;
    $scope.check_next = true;
    $scope.totalPages = 0;
    $scope.idrole_form= 3;
    $scope.b;
    $scope.hideUpdate=true;
    $scope.valueRole = 0;
    $scope.valueStatus = -1;
    $scope.valueSearch = "";
    var role = document.getElementById("role");
    var filter_Status = document.getElementById("filterstatus");
    var filter_Role = document.getElementById("filterRole");
    var search = document.getElementById("search");
    role.value = 3;

    const jwtToken = localStorage.getItem("jwtToken")
    const token = {
        headers: {
            Authorization: `Bearer `+jwtToken
        }
    }


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

    role.onchange = function () {
        if (this.value != "") {
            // console.log(this.value)
            $scope.idrole_form = this.value;
        }
    }

    filter_Status.onchange = function (){
        if (this.value != ""){
            $http.get("/rest/admin/accounts/page?status="+this.value+"&role="+$scope.valueRole+"&search="+$scope.valueSearch,token).then(resp => {
                $scope.accounts = resp.data.content;
                $scope.valueStatus = this.value;
                $scope.totalPages =  resp.data.totalPages;
                if ($scope.index = $scope.totalPages){
                    $scope.check_next = false;
                    $scope.check_last = false;
                }
            }).catch(error => {
                console.log(error);
            });
        }
    }

    filter_Role.onchange= function () {
        if (this.value != null){
            $http.get("/rest/admin/accounts/page?role="+this.value+"&status="+$scope.valueStatus+"&search="+$scope.valueSearch,token).then(resp => {
                $scope.accounts = resp.data.content;
                $scope.valueRole = this.value;
                $scope.totalPages =  resp.data.totalPages;
                if ($scope.index = $scope.totalPages){
                    $scope.check_next = false;
                    $scope.check_last = false;
                }
            }).catch(error => {
                console.log(error);
            });
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
    $scope.a;
    //xóa form
    $scope.reset = function () {
    $scope.hideUsername=false;
    $scope.hideCreate=false;
    $scope.hideUpdate=true;
    $scope.hidePassword=false;
    filter_Role.value = "";
    filter_Status.value="";
    $scope.valueSearch = "";
    $scope.valueStatus = -1;
    $scope.valueRole = 0;

        $scope.form = {
            createDate: new Date(),
            // username:"",
            // fullName:"",
            // email:"",
            // phone:"",
            // password:"",
            image: "https://res.cloudinary.com/dcll6yp9s/image/upload/v1669087979/kbasp5qdf76f3j02mebr.png",
            gender: true,
            status: 1,
            role: 3
        }
        $scope.getPageAccounts();
    }

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
        $http.get("/rest/admin/accounts/page?search="+input.value+"&role="+$scope.valueRole+"&status="+$scope.valueStatus,token).then(resp => {
            $scope.accounts = resp.data.content;
            $scope.valueSearch = input.value;
            $scope.totalPages =  resp.data.totalPages;
            if ($scope.index = $scope.totalPages){
                $scope.check_next = false;
                $scope.check_last = false;
            }
        }).catch(error => {
            console.log(error);
        });
    }

    search.onkeypress = debounce(keyPressCallback, 500);

    $scope.initialize = function () {
        $scope.index = 1
        //load accounts

        $http.get("/rest/admin/accounts/page?page=1",token).then(resp => {
            $scope.accounts = resp.data.content;
            $scope.reset();
            $scope.totalPages =  resp.data.totalPages;
            if ($scope.index = $scope.totalPages){
                $scope.check_next = false;
                $scope.check_last = false;
            }
        }).catch(error => {
            console.log(error);
        });
        $http.get("/rest/admin/accounts/roles",token).then(resp => {
            $scope.roles = resp.data;
            // console.log($scope.roles)
        }).catch(error => {
            console.log(error);
        });

        // alert("Load du lieu tu db thanh cong")

    }

    // $scope.getTotalPages = function () {
    //     $http.get("/rest/admin/accounts/getAll",token).then(function (response) {
    //         $scope.totalPages = Math.ceil(response.data.length / 10);
    //     }).catch(error => {
    //         console.log(error);
    //     });
    // }

    $scope.initialize();

    //hiển thị lên form
    $scope.edit = function (account) {
        $scope.hideUpdate=false;
        $scope.hideUsername=true;
        $scope.hideCreate=true;
        $scope.hidePassword=true;
        $scope.form = angular.copy(account);
        console.log(account.role)
        role.value = account.role;
        $scope.form.createDate = new Date(account.createDate)
        console.log(account.createDate + account.username)
        console.log(account.image)
    }
    //Check trùng
    $scope.check = function () {

        $http.get("/rest/admin/accounts/getAll",token).then(resp => {
            var a = 1;
            console.log("Bắt đầu kiểm tra check trùng")
            $scope.accounts = resp.data;
            console.log("Đọc dữ liệu trong list")
            $scope.accounts.forEach(acc => {

                if ($scope.form.username == acc.username) {
                    console.log($scope.form.username)
                    console.log(acc.username)
                    console.log(" trùng")
                    alert("Tài khoản đã tồn tại")
                    return a = 0;
                }
            })
            return a = $scope.b;
          
        });
console.log("Kết thúc check trùng")
        // $http.get("/rest/admin/accounts").then(resp => {
        //     console.log("Bắt đầu kiểm tra check trùng")
        //     $scope.accounts = resp.data; 
        //     console.log("ĐỌc dữ liệu trong list")
        //     $scope.accounts.some(acc => {
        //         if ( $scope.form.username ==acc.username ) {
        //            console.log(" trùng")
        //             alert("Tài khoản đã tồn tại")
        //             return a =0;     
        //         } 
        //         console.log(" không trùng")
        //         return a=1;
        //     })
        //     console.log($scope.a)
        //     console.log("Kết thúc check trùng")
        // });
    }
    //thêm sản phẩm mới
    $scope.create = function () {
        console.log($scope.b)
        $http.get("/rest/admin/accounts/getAll",token).then(resp => {
            var a = 1;
            console.log("Bắt đầu kiểm tra check trùng")
            $scope.accounts = resp.data;
            console.log("Đọc dữ liệu trong list")
            $scope.accounts.forEach(acc => {

                if ($scope.form.username == acc.username) {
                    Toast.fire({
                        icon: 'error',
                        title: 'Tài khoản đã tồn tại ',
                    })
                    // alert("Tài khoản đã tồn tại")
                    return a = 0;

                }
                if ($scope.form.email == acc.email) {
                    Toast.fire({
                        icon: 'error',
                        title: 'Email đã tồn tại ',
                    })
                    // alert("Tài khoản đã tồn tại")
                    return a = 0;

                }

            })
            if (a == 1) {
                var formData = new FormData();
                angular.forEach()
                angular.forEach($scope.files, function(image) {
                    formData.append('image', image);
                });
                formData.append("username",$scope.form.username);
                formData.append("fullName",$scope.form.fullName);
                formData.append("gender",$scope.form.gender);
                formData.append("email",$scope.form.email);
                formData.append("status",$scope.form.status);
                formData.append("password",$scope.form.password);
                formData.append("phone",$scope.form.phone);
                formData.append("role",$scope.idrole_form);

                let req = {
                    method: 'POST',
                    url: '/rest/admin/accounts/create',
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
                    title: 'Đang thêm  mới vui lòng chờ!',
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
                    $scope.message("Thêm mới thành công");
                    $scope.reset();
                }).catch(error => {
                    $scope.error('Thêm mới thất bại');
                });
            }
            console.log("Kết thúc thêm")
        });
    }
    //Kiểm tra form
    $scope.form = function () {
        alert("Chạy method form")
        var account = angular.copy($scope.form);
        if (account.username.trim() == null) {
            // $scope.error = "Tên tài khoản không được để trống"
            alert("Tên tài khoản trống")
            return null;
        }
        if (account.fullName.trim() == null) {
            alert("Họ và tên không được để trống khoản trống")
            return null;
        }
        if (account.phone.trim() == null) {
            alert("Tên tài khoản trống")
            return null;
        }
        if (account.email.trim() == null) {
            alert("Tên tài khoản trống")
            return null;
        }
        if (account.password.trim() == null) {
            alert("Tên tài khoản trống")
            return null;
        }
        return account;

    }

    //cập nhật account
    $scope.update = function () {
        var account = angular.copy($scope.form);
        console.log(account.username)
        var formData = new FormData();
        angular.forEach($scope.files, function(image) {
            formData.append('image', image);
        });
        formData.append("fullName",$scope.form.fullName);
        formData.append("gender",$scope.form.gender);
        formData.append("email",$scope.form.email);
        formData.append("status",$scope.form.status);
        formData.append("phone",$scope.form.phone);
        formData.append("role",role.value);
        let req = {
            method: 'POST',
            url: '/rest/admin/accounts/update?username='+account.username,
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
            timer: 1000,
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
            $scope.message("Cập nhật thành công");
            $scope.reset();
        }).catch(error => {
            $scope.error('Cập nhật thất bại');
        });
    }


    //xóa account
    $scope.delete = function (account) {

        $http.delete(`/rest/admin/accounts/${account.username}`,token).then(resp => {
            var index = $scope.accounts.findIndex(p => p.username == account.username);
            $scope.accounts.splice(index, 1);
            Toast.fire({
                icon: 'success',
                title: 'Xóa thành công!',
            })
            $scope.reset();
            // alert("Delete success!");
        }).catch(error => {
            // alert("Delete Error!")
            Toast.fire({
                icon: 'error',
                title: 'Xóa thất bại!',
            })
            console.log("Error", error)
        })
    }
    // var urlImage = `http://localhost:8080/rest/files/images/img`;
    // $scope.url = function (fileName) {
    //     return `${urlImage}/` + `${fileName}`;
    // }
    // $scope.fileNames = [];
    // $scope.listFile = function () {
    //     $http.get(urlImage,token).then(res => {
    //         $scope.fileNames = res.data;
    //         console.log('ok', res);
    //     }).catch(err => {
    //         console.log('Load files failse', err);
    //     })
    // }
    //upload hình
    $scope.upLoadFile = function (files) {
        $scope.files = files;
        console.log("SCope.file : " + $scope.files);
        // $http.post(urlImage, form,token, {
        //     transformRequest: angular.identity,
        //     headers: { 'Content-Type': undefined }
        // }).then(res => {
        //     $scope.form.image = res.data.name;
        //     console.log('image', res);
        // }).catch(err => {
        //     console.log('err', err);
        // })
    }
    const pathAPI = "/rest/admin/accounts";
    // pagination
    $scope.next = function () {
        $scope.check_first = true;
        $scope.index++;

        if ($scope.index == $scope.totalPages) {
            $scope.check_first = true;
            $scope.check_last = false;
            $scope.check_next = false;
            $scope.check_prev = true;
        }
        $http.get(pathAPI + `/page?page=` + $scope.index,token).then(res => {
            $scope.accounts = res.data.content;
            console.log('Load accounts success', res.data)
        }).catch(err => {
            console.log('Load accounts false', err.data);
        })
    }
    $scope.prev = function () {
        $scope.check_last = true;
        $scope.index--;

        if ($scope.index == 1) {
            $scope.check_first = false;
            $scope.check_last = true;
            $scope.check_prev = false;
            $scope.check_next = true;
        }
        $http.get(pathAPI + `/page?page=` + $scope.index,token).then(res => {
            $scope.accounts = res.data.content;
            console.log('Load accounts success', res.data)
        }).catch(err => {
            console.log('Load accounts failse', err.data);
        })
    }
    $scope.first = function () {
        $scope.check_first = false;
        $scope.check_next = true;
        $scope.check_prev = false;
        $scope.check_last = true;
        $scope.index = 1;
        $http.get(pathAPI + `/page?page=` + $scope.index,token).then(res => {
            $scope.accounts = res.data.content;
            console.log('Load accounts success', res.data)
        }).catch(err => {
            console.log('Load accounts failse', err.data);
        })
    }
    $scope.last = function () {
        $scope.check_first = true;
        $scope.check_next = false;
        $scope.check_prev = true;
        $scope.check_last = false;
        $scope.index = $scope.totalPages;
        $http.get(pathAPI + `/page?page=` + $scope.index,token).then(res => {
            $scope.accounts = res.data.content;
            console.log('Load accounts success', res.data)
        }).catch(err => {
            console.log('Load accounts failse', err.data);
        })
    }
    $scope.getPageAccounts = function () {
        $http.get(pathAPI + `/page?status=1`,token).then(res => {
            $scope.accounts = res.data.content;
            $scope.totalPages = res.data.totalPages;
            $scope.index = res.data.number + 1;
            $scope.check_last = true;
            $scope.check_next = true;
            console.log('Load accounts success', res.data)
        }).catch(err => {
            console.log('Load accounts failse', err.data);
        })
    }

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
                console.log(respon.data.name);
                if (respon.data.name === "USER" || respon.data.name === "STAFF"){
                    $scope.logOut();
                }
            })
        }
    }

    $scope.checkLogin();

})