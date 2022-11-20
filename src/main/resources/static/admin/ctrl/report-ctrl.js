app.controller('report-ctrl',function($scope,$http){
    var urlReport=`/rest/report`;
    $scope.reportPay=[];
    $scope.form= {};
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
});