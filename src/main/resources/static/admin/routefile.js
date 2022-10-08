var app = angular.module("myApp",["ngRoute"]);
app.config(function($routeProvider){
	$routeProvider
	    .when("/product",{
          templateUrl:"/admin/product/form.html",
         // controller: "Ctrlproduct"
        })
        .when("/category",{
          templateUrl:"/admin/categories/category.html",
          controller: "ctrl_cate"
        })
         .when("/account",{
          templateUrl:"/admin/account/form.html",
        //  controller: "Ctrluser"
        })
        .when("/accessory",{
            templateUrl:"/admin/accessory/form.html",
             controller: "rest_accessory"
        })
         /* .when("/athotiting",{
          templateUrl:"/asserts/admin/athotiting/athotities.html",
         // controller: "Ctrlathotities"
        })*/
        .otherwise({ 
	     redirectTo:"/product"
});
	
});