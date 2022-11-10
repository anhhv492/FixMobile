app.controller('profile-ctl', function ($scope,$http, $window) {
    $scope.accountActive = {};
    const callApi = "http://localhost:8080/rest/admin/accounts";

    const jwtToken = localStorage.getItem("jwtToken")
    const token = {
        headers: {
            Authorization: `Bearer `+jwtToken
        }
    }

    $scope.getAcountActive = function () {
        $http.get(callApi+`/getAccountActive`, token).then(function (respon){
            $scope.accountActive = respon.data;
            console.log($scope.accountActive.username)
        }).catch(err => {
            Swal.fire({
                icon: 'error',
                text: 'Bạn chưa đăng nhập !!!',
            })
            console.log(err)
            $window.location.href='#!login';
        })
    }

    $scope.fieldValues = {
        dateOfBirth: ""
    };

    /*Date Of Birth*/

    $scope.days = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31];
    $scope.months = [{id: 1, name:"Tháng 1"},
        {id: 2, name:"Tháng 2"},
        {id: 3, name:"Tháng 3"},
        {id: 4, name:"Tháng 4"},
        {id: 5, name:"Tháng 5"},
        {id: 6, name:"Tháng 6"},
        {id: 7, name:"Tháng 7"},
        {id: 8, name:"Tháng 8"},
        {id: 9, name:"Tháng 9"},
        {id: 10, name:"Tháng 10"},
        {id: 11, name:"Tháng 11"},
        {id: 12, name:"Tháng 12"}
    ];
    $scope.years = [];
    var d = new Date();
    for (var i = (d.getFullYear()+100); i > (d.getFullYear() - 100); i--) {
        $scope.years.push(i);
    }

    $scope.year = "";
    $scope.month = "";
    $scope.day = "";

    $scope.updateDate = function (input){
        if (input == "year"){
            $scope.month = "";
            $scope.day = "";
        }
        else if (input == "month"){
            $scope.day = "";
        }
        if ($scope.year && $scope.month && $scope.day){
            $scope.fieldValues.dateOfBirth = new Date($scope.year, $scope.month.id - 1, $scope.day);
            console.log($scope.fieldValues)
        }
    };

    $scope.getAcountActive();


});

app.filter('validMonths', function () {
    return function (months, year) {
        var filtered = [];
        var now = new Date();
        var over18Month = now.getUTCMonth() + 1;
        var over18Year = now.getUTCFullYear() - 18;
        if(year != ""){
            if(year == over18Year){
                angular.forEach(months, function (month) {
                    if (month.id <= over18Month) {
                        filtered.push(month);
                    }
                });
            }
            else{
                angular.forEach(months, function (month) {
                    filtered.push(month);
                });
            }
        }
        return filtered;
    };
});

app.filter('daysInMonth', function () {
    return function (days, year, month) {
        var filtered = [];
        angular.forEach(days, function (day) {
            if (month != ""){
                if (month.id == 1 || month.id == 3 || month.id == 5 || month.id == 7 || month.id == 8 || month.id == 10 || month.id == 12) {
                    filtered.push(day);
                }
                else if ((month.id == 4 || month.id == 6 || month.id == 9 || month.id == 11) && day <= 30){
                    filtered.push(day);
                }
                else if (month.id == 2){
                    if (year % 4 == 0 && day <= 29){
                        filtered.push(day);
                    }
                    else if (day <= 28){
                        filtered.push(day);
                    }
                }
            }
        });
        return filtered;
    };
});

app.filter('validDays', function () {
    return function (days, year, month) {
        var filtered = [];
        var now = new Date();
        var over18Day = now.getUTCDate();
        var over18Month = now.getUTCMonth() + 1;
        var over18Year = now.getUTCFullYear() - 18;
        if(year == over18Year && month.id == over18Month){
            angular.forEach(days, function (day) {
                if (day <= over18Day) {
                    filtered.push(day);
                }
            });
        }
        else{
            angular.forEach(days, function (day) {
                filtered.push(day);
            });
        }
        return filtered;
    };
});

function changeMe(sel)
{
    sel.style.color = "#000";
}