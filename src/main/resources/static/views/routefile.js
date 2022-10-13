var app = angular.module("app_module",["ngRoute"]);
app.config(function($routeProvider){
	$routeProvider
	    .when("/cart",{
          templateUrl:"cart/cart.html",
         // controller: "Ctrlproduct"
        })
        .when("/home/index",{
            templateUrl:"home/view.html",
            // controller: "Ctrlproduct"
        })
});