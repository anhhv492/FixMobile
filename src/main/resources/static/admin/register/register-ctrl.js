const app = angular.module("app-register", []);
app.controller("register-ctrl",function ($scope, $http){
    alert("1")
    $scope.accounts = [];
    $scope.roles = [];
    $scope.form = {};
    $scope.init = function(){
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
    }
    $scope.init();
    const api ="/rest/admin/accounts";
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
                    alert("Tài khoản đã tồn tại")
                    return a = 0;

                }
            })
            if (a == 1) {
                console.log("Bắt đầu thêm mới")
                var account = angular.copy($scope.form);
                $http.post(api, account).then(resp => {
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
} )