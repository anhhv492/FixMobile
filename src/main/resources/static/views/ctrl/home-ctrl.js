app.controller('home-ctrl',function($rootScope,$scope,$http,$window){
    var urlCategory=`http://localhost:8080/rest/staff/category`;
    var urlAccessory=`http://localhost:8080/rest/admin/accessory`;
    $scope.cateAccessories=[];
    $scope.cateProducts=[];
    $scope.item= {};
    $rootScope.carts=[];
    $rootScope.qtyCart=0;
    $scope.getCategories = function(){
        $http.get(`${urlCategory}/getAll`).then(res=>{
            res.data.forEach(cate=>{
                if(cate.type){
                    $scope.cateAccessories.push(cate);
                } else {
                    $scope.cateProducts.push(cate);
                }
            });
            console.log("ass",$scope.cateAccessories)
            console.log("pro",$scope.cateProducts)
            console.log("res",res)
        }).catch(err=>{
            console.log("error",err)
        })
    }

    $scope.getDetail=function(item){
        $rootScope.cateDetail=item;
        if(item.type){
            $http.get(`${urlAccessory}/cate/${item.idCategory}`).then(res=>{
                $rootScope.detailAccessories=res.data;
                console.log("detailAccessories",$rootScope.detailAccessories)
            }).catch(err=>{
                $rootScope.detailAccessories=null;
                console.log("error",err)
            })
        }else if(!item.type){
            $rootScope.idDetailProduct=item.idCategory;
        }
    }
    $scope.addCart=function(accessory){
        $rootScope.qtyCart++;
        console.log("qty",$scope.qtyCart);
        $scope.item = $rootScope.carts.find(
            it=>it.idAccessory===accessory.idAccessory
        );
        if(accessory.category.type){
            $http.get(`${urlAccessory}/${accessory.idAccessory}`).then(res=>{
                console.log("cartAccessory",res)
                let data= res.data;
                if(!$scope.item){
                    data.qty=1;
                    $rootScope.carts.push(data);
                    console.log("addCart1",data)
                }else{
                    $scope.item.qty++;
                    console.log("addCart2",$scope.item)
                }
                $rootScope.saveLocalStorage();
            }).catch(err=>{
                console.log("error",err)
            })
        }
    }
    $rootScope.saveLocalStorage=function(){
        let json = JSON.stringify(angular.copy($rootScope.carts));
        localStorage.setItem("cart",json);
    }
    $rootScope.loadLocalStorage = function(){
        let json = localStorage.getItem("cart");
        $rootScope.carts=json? JSON.parse(json):[];
    }
    $rootScope.loadQtyCart=function(){
        if($rootScope.carts){
            $rootScope.carts.forEach(item=>{
                $rootScope.qtyCart+=item.qty;
            });
        }
    }
    $scope.overPro=false;
    $scope.overAccess=false;
    $scope.getCategories();
    $rootScope.loadLocalStorage();
    $rootScope.loadQtyCart();
})