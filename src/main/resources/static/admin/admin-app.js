alert("Xin chào")
app = angular.module("admin-app",["ngRoute"]);

app.config(function($routeProvider){
    $routeProvider
    .when("/account",{
        templateUrl:"/admin/account/index.html",
        controller:"account-ctrl"
    });
})