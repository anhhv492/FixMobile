var app = angular.module("myApp",["ngRoute"]);
app.config(function($routeProvider){
    const jwtToken = localStorage.getItem("jwtToken");
	$routeProvider
	    .when("/product",{
          templateUrl:"/admin/product/form.html",
          controller: "product",
            headers:{
                Authorization: `Bearer `+jwtToken
            },
        })
        .when("/dashboard",{
            templateUrl:"/admin/dashboard/dashboard.html",
            controller: "dashboard",
            controller: "report-ctrl",
            headers:{
                Authorization: `Bearer `+jwtToken
            },
          })
        .when("/category",{
          templateUrl:"/admin/categories/category.html",
          controller: "ctrl_cate",
            headers:{
                Authorization: `Bearer `+jwtToken
            },
        })
         .when("/account",{
          templateUrl:"/admin/account/form.html",
         controller: "account-ctrl",
             headers:{
                 Authorization: `Bearer `+jwtToken
             },
        })
        .when("/accessory",{
            templateUrl:"/admin/accessory/form.html",
             controller: "rest_accessory",
            headers:{
                Authorization: `Bearer `+jwtToken
            },
        })
        .when("/order",{
            templateUrl:"/admin/order/form.html",
            controller: "order-admin-ctrl",
            headers:{
                Authorization: `Bearer `+jwtToken
            },
        })
        .when("/order-detail",{
            templateUrl:"/admin/order/detail/form.html",
            controller: "order-admin-detail-ctrl",
            headers:{
                Authorization: `Bearer `+jwtToken
            },
        })
        // thuộc tính
        .when("/createcapacity",{
            templateUrl:"/admin/capacity/createcapacity.html",
            controller: "restaccsesries",
            headers:{
                Authorization: `Bearer `+jwtToken
            },
        })
        .when("/createram",{
            templateUrl:"/admin/ram/createram.html",
            controller: "restaccsesries",
            headers:{
                Authorization: `Bearer `+jwtToken
            },
        })
        .when("/color",{
            templateUrl:"/admin/color/createcolor.html",
            controller: "restaccsesries",
            headers:{
                Authorization: `Bearer `+jwtToken
            },
        })
        .when("/images",{
            templateUrl:"/admin/images/createimages.html",
            controller: "restaccsesries",
            headers:{
                Authorization: `Bearer `+jwtToken
            },
        })

        .when("/sale",{
            templateUrl:"/admin/sale/showsale.html",
            controller: "sale_ctrl",
            headers:{
                Authorization: `Bearer `+jwtToken
            },
        })
        .when("/productchange",{
            templateUrl:"/admin/productchange/cofirmproductchange.html",
            controller: "product-change",
            headers:{
                Authorization: `Bearer `+jwtToken
            },
        })
        .otherwise({ 
	     redirectTo:"/dashboard"
});
	
});