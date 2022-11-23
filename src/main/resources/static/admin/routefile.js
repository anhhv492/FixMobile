var app = angular.module("myApp",["ngRoute"]);
app.config(function($routeProvider){
	$routeProvider
	    .when("/product",{
          templateUrl:"/admin/product/form.html",
          controller: "product"
        })
        .when("/dashboard",{
            templateUrl:"/admin/dashboard/dashboard.html",
            controller: "dashboard",
            controller: "report-ctrl",
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
        .when("/order",{
            templateUrl:"/admin/order/form.html",
            controller: "order-admin-ctrl"
        })
        .when("/order-detail",{
            templateUrl:"/admin/order/detail/form.html",
            controller: "order-admin-detail-ctrl"
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
        .when("/sale",{
            templateUrl:"/admin/sale/showsale.html",
            controller: "sale_ctrl"
        })
        .otherwise({ 
	     redirectTo:"/dashboard"
});
	
});