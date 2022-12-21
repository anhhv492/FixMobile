app.controller("register-ctrl",function ($scope, $http,$window){
    $scope.accounts = [];
    $scope.roles = [];
    $scope.form = {};
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
    $scope.init = function(){
        
        $http.get("/rest/guest/registers/roles").then(resp => {
            $scope.roles = resp.data;
        }).catch(error => {
            console.log(error);
        });;
        $scope.form = {
            username:null,
            createDate: new Date(),
            image: "https://res.cloudinary.com/dcll6yp9s/image/upload/v1669087979/kbasp5qdf76f3j02mebr.png",
            gender: true,
            status: 1,
            password: null,
            role: {
                idRole: 3,
                name: "USER"
            },
        }
    }
    $scope.reset = function () {
       
            $scope.form = {
                username:"",
                createDate: new Date(),
                image: "https://res.cloudinary.com/dcll6yp9s/image/upload/v1669087979/kbasp5qdf76f3j02mebr.png",
                gender: true,
                status: 1,
                password:"",
                email:"",
                phone:"",
                role: {
                    idRole: 3,
                    name: "USER"
                },
            }
           
    }
    $scope.init();
    const api ="/rest/guest/registers";
    $scope.create =function(){
        $http.get(api).then(resp => {
            var a = 1;
            console.log("Bắt đầu kiểm tra check trùng")
            $scope.accounts = resp.data;
            console.log("Đọc dữ liệu trong list")
            $scope.accounts.forEach(acc => {

                if ($scope.form.username == acc.username) {
                    console.log($scope.form.username)
                    console.log(acc.username)
                    console.log(" trùng")
                    Toast.fire({
                        icon: 'error',
                        title: 'Tài khoản đã tồn tại',
                    })
                    // alert("Tài khoản đã tồn tại")
                    return a = 0;

                }
                if ($scope.form.email == acc.email) {
                    Toast.fire({
                        icon: 'error',
                        title: 'Email đã tồn tại ',
                    })
                    return a = 0;

                }
            })
            if (a == 1) {
                console.log("Bắt đầu thêm mới")
                var account = angular.copy($scope.form);
                $http.post(api, account).then(resp => {
                    resp.data.createDate = new Date(resp.data.createDate)
                    $scope.accounts.push(resp.data);
                    Toast.fire({
                        icon: 'success',
                        title: 'Đăng ký thành công!',
                    })
                    $window.location.href = '#!login';
                    $scope.reset();
                    // alert("Create success!");
                }).catch(error => {
                    Toast.fire({
                        icon: 'error',
                        title: 'Đăng ký thất bại!',
                    })
                    // alert("Error create!")
                    console.log("Error", error)
                })

            }
            console.log("Kết thúc thêm ")
        });
    }
} )