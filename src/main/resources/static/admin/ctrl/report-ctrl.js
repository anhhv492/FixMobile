app.controller('report-ctrl',function($scope,$http,$window){
    var urlReport=`/rest/report`;
    $scope.reportPay=[];
    $scope.form= {};
    const jwtToken = localStorage.getItem("jwtToken")
    const token = {
        headers: {
            Authorization: `Bearer `+jwtToken
        }
    }
    // $rootScope.formSeach= {};
    $scope.getAllReport=function(){
        $http.get(urlReport + `/Pay`).then(resp=>{
            $scope.reportPay = resp.data;
            console.log('vvvvvvvvvvv '+ resp.data)
        }).catch(error=>{
            console.log(error);
        })
    }
    $scope.getSearchReportData=function(){
        $http.get(urlReport + `/search`).then(resp=>{
            $scope.reportPay = resp.data;
            console.log('vvvvvvvvvvv '+ resp.data)
        }).catch(error=>{
            console.log(error);
        })
    }
    $scope.getAllReport();
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
                if (respon.data.name === "USER"){
                    $scope.logOut();
                }else if (respon.data.name === "STAFF"){
                    $window.location.href = "http://localhost:8080/admin/index.html#!/product";
                }
            })
        }
    }

    $scope.checkLogin();
});