var app = angular.module("myApp",["ngRoute"]);
app.config(function($routeProvider){
	$routeProvider
	    .when("/product",{
          templateUrl:"/admin/product/form.html",
         // controller: "Ctrlproduct"
        })
        .when("/categories",{
          templateUrl:"/admin/categories/category.html",
         // controller: "Ctrlcate"
        })
         .when("/account",{
          templateUrl:"/admin/account/form.html",
        //  controller: "Ctrluser"
        })
         /* .when("/athotiting",{
          templateUrl:"/asserts/admin/athotiting/athotities.html",
         // controller: "Ctrlathotities"
        })*/
        .otherwise({ 
	     redirectTo:"/product"
});
	
});