var app = angular.module("app_module",["ngRoute"]);
app.config(function($routeProvider){
	$routeProvider
        .when("/home/index",{
            templateUrl:"home/view.html",
            controller: "home-ctrl"
        })
	    .when("/cart",{
            templateUrl:"cart/cart.html",
            controller: "cart-ctrl"
        })
        .when("/accessory/detail",{
            templateUrl:"accessory/list_detail.html",
            controller: "home-ctrl"
        })
        .when("/product/detail",{
            templateUrl:"product/detail.html",
            controller: "home-ctrl"
        })
});