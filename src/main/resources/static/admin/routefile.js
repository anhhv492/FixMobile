var app = angular.module("myApp",["ngRoute"]);
app.config(function($routeProvider){
	$routeProvider
	    .when("/product",{
          templateUrl:"/admin/product/form.html",
          controller: "product"
        })
        .when("/dashbroad",{
            templateUrl:"/admin/dashbroad/dashbroad.html",
            controller: "dashbroad"
          })
        .when("/category",{
          templateUrl:"/admin/categories/category.html",
          controller: "ctrl_cate"
        })
         .when("/account",{
          templateUrl:"/admin/account/form.html",
         controller: "account-ctrl"
        })
        .when("/accessory",{
            templateUrl:"/admin/accessory/form.html",
             controller: "rest_accessory"
        })
        // thuộc tính
        .when("/createcapacity",{
            templateUrl:"/admin/capacity/createcapacity.html",
            controller: "restaccsesries"
        })
        .when("/createram",{
            templateUrl:"/admin/ram/createram.html",
            controller: "restaccsesries"
        })
        .when("/color",{
            templateUrl:"/admin/color/createcolor.html",
            controller: "restaccsesries"
        })
        .when("/images",{
            templateUrl:"/admin/images/createimages.html",
            controller: "restaccsesries"
        })
        .otherwise({ 
	     redirectTo:"/product"
});
	
});