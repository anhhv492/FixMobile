var app = angular.module("app_module",["ngRoute"]);
app.config(function($routeProvider){
	$routeProvider
        .when("/home/index",{
            templateUrl:"home/view.html",
            controller: "home-ctrl",
            controller: "view_product_ctrl"
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
            templateUrl:"product/list_detail.html",
            controller: "home-ctrl"
        })

        .when("/product/product",{
            templateUrl:"product/product.html",
            controller: "home-ctrl"
	})
        .when("/login",{
            templateUrl:"login/form .html",
            controller: "login-ctrl"
            
        })
});
