app.controller("account-ctrl", function ($scope, $http) {
    $scope.accounts = [];
    $scope.roles = [];
    $scope.form = {};
    $scope.form.createDate = new Date();
    $scope.form.gender = true;
    $scope.index = 0;
    $scope.check_first = false;
    $scope.check_last = true;
    $scope.totalPages = 0;
    $scope.b;
    $scope.hideUpdate=true;

    $scope.a;
    //xóa form
    $scope.reset = function () {
$scope.hideUsername=false;
$scope.hideCreate=false;
$scope.hideUpdate=true;
        $scope.form = {
            username:null,
            createDate: new Date(),
            image: "5.png",
            gender: true,
            status: true,
            password: null,
            role: {
                idRole: 3,
                name: "USER"
            },


        }
        $scope.getPageAccounts();
    }

    $scope.initialize = function () {

        //load accounts

        $http.get("/rest/admin/accounts/page/0").then(resp => {
            $scope.accounts = resp.data;
            $scope.reset();

        }).catch(error => {
            console.log(error);
        });;
        $http.get("/rest/admin/accounts/roles").then(resp => {
            $scope.roles = resp.data;
        }).catch(error => {
            console.log(error);
        });;
        $scope.getTotalPages();
        alert("Load du lieu tu db thanh cong")

    }
    $scope.getTotalPages = function () {
        $http.get("/rest/admin/accounts").then(function (response) {
            $scope.totalPages = Math.ceil(response.data.length / 10);
        }).catch(error => {
            console.log(error);
        });
    }

    $scope.initialize();

    //hiển thị lên form
    $scope.edit = function (account) {
        $scope.hideUpdate=false;
        $scope.hideUsername=true;
        $scope.hideCreate=true;
        $scope.form = angular.copy(account);
        $scope.form.createDate = new Date(account.createDate)
        console.log(account.createDate + account.username)
        console.log(account.image)
    }
    //Check trùng
    $scope.check = function () {

        $http.get("/rest/admin/accounts").then(resp => {
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
            console.log("Hello")
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
        $http.get("/rest/admin/accounts").then(resp => {
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
            if (a == 1) {
                console.log("Bắt đầu thêm mới")
                var account = angular.copy($scope.form);
                $http.post('/rest/admin/accounts', account).then(resp => {
                    resp.data.createDate = new Date(resp.data.createDate)
                    $scope.accounts.push(resp.data);
                    $scope.reset();
                    alert("Create success!");
                }).catch(error => {
                    alert("Error create!")
                    console.log("Error", error)
                })

            }
            console.log("Kết thúc thêm ")


        });
    }
    //Kiểm tra form
    $scope.form = function () {
        alert("CHạy method form")
        var account = angular.copy($scope.form);
        if (account.username == null) {
            $scope.error = "Tên tài khoản không được để trống"
            alert("Tên tài khoản trống")
        }
        if (account.fullName == null) {
            alert("Họ và tên không được để trống khoản trống")
        }
        if (account.phone == null) {
            alert("Tên tài khoản trống")
        }
        if (account.email == null) {
            alert("Tên tài khoản trống")
        }
        if (account.password == null) {
            alert("Tên tài khoản trống")
        }

    }

    //cập nhật sản phẩm
    $scope.update = function () {
        var account = angular.copy($scope.form);
        console.log(account.username)
        $http.put(`/rest/admin/accounts/${account.username}`, account).then(resp => {
            var index = $scope.accounts.findIndex(a => a.username == account.username);
            $scope.accounts[index] = account;
            alert("Update success!");
        }).catch(error => {
            alert("Update Error!")
            console.log("Error", error)
        })

    }

    //xóa sản phẩm
    $scope.delete = function (account) {

        $http.delete(`/rest/admin/accounts/${account.username}`).then(resp => {
            var index = $scope.accounts.findIndex(p => p.username == account.username);
            $scope.accounts.splice(index, 1);
            $scope.reset();
            alert("Delete success!");
        }).catch(error => {
            alert("Delete Error!")
            console.log("Error", error)
        })
    }
    var urlImage = `http://localhost:8080/rest/files/images/img`;
    $scope.url = function (fileName) {
        return `${urlImage}/` + `${fileName}`;
    }
    $scope.fileNames = [];
    $scope.listFile = function () {
        $http.get(urlImage).then(res => {
            $scope.fileNames = res.data;
            console.log('ok', res);
        }).catch(err => {
            console.log('Load files failse', err);
        })
    }
    //upload hình
    $scope.uploadFile = function (files) {
        var form = new FormData();
        form.append('file', files[0]);
        $http.post(urlImage, form, {
            transformRequest: angular.identity,
            headers: { 'Content-Type': undefined }
        }).then(res => {
            $scope.form.image = res.data.name;
            console.log('image', res);
        }).catch(err => {
            console.log('err', err);
        })
    }
    const pathAPI = "/rest/admin/accounts";
    // pagination
    $scope.next = function () {
        $scope.check_first = true;
        $scope.index++;
        if ($scope.index >= $scope.totalPages) {
            $scope.index = 0;
            $scope.check_first = false;
            $scope.check_last = true;
        }
        if ($scope.index == $scope.totalPages - 1) {
            $scope.check_first = true;
            $scope.check_last = false;
        }
        $http.get(pathAPI + `/page/` + $scope.index).then(res => {
            $scope.accounts = res.data;
            console.log('Load accounts success', res.data)
        }).catch(err => {
            console.log('Load accounts failse', err.data);
        })
    }
    $scope.prev = function () {
        $scope.check_last = true;
        $scope.index--;
        if ($scope.index < 0) {
            $scope.index = $scope.totalPages - 1;
            $scope.check_first = true;
            $scope.check_last = false;
        }
        if ($scope.index == 0) {
            $scope.check_first = false;
            $scope.check_last = true;
        }
        $http.get(pathAPI + `/page/` + $scope.index).then(res => {
            $scope.accounts = res.data;
            console.log('Load accounts success', res.data)
        }).catch(err => {
            console.log('Load accounts failse', err.data);
        })
    }
    $scope.first = function () {
        $scope.check_first = false;
        $scope.check_last = true;
        $scope.index = 0;
        $http.get(pathAPI + `/page/` + $scope.index).then(res => {
            $scope.accounts = res.data;
            console.log('Load accounts success', res.data)
        }).catch(err => {
            console.log('Load accounts failse', err.data);
        })
    }
    $scope.last = function () {
        $scope.check_first = true;
        $scope.check_last = false;
        $scope.index = $scope.totalPages - 1;
        $http.get(pathAPI + `/page/` + $scope.index).then(res => {
            $scope.accounts = res.data;
            console.log('Load accounts success', res.data)
        }).catch(err => {
            console.log('Load accounts failse', err.data);
        })
    }
    $scope.getPageAccounts = function () {
        $http.get(pathAPI + `/page/` + $scope.index).then(res => {
            $scope.accounts = res.data;
            console.log('Load accounts success', res.data)
        }).catch(err => {
            console.log('Load accounts failse', err.data);
        })
    }

})