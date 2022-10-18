app.controller('home-ctrl',function($rootScope,$scope,$http){
    var urlCategory=`http://localhost:8080/rest/staff/category`;
    var urlAccessory=`http://localhost:8080/rest/admin/accessory`;
    $scope.cateAccessories=[];
    $scope.cateProducts=[];
    $rootScope.qtyCart=0;
    $scope.items=[];
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
    $scope.findById=function(item){
        if(item.category.type){
            $http.get(`${urlAccessory}/${item.idAccessory}`).then(res=>{
                console.log("cartAccessory",res)
                return res.data;
            }).catch(err=>{
                console.log("error",err)
                return null;
            })
        }
    }
    $scope.addCart=function(item){
        $rootScope.qtyCart++;
        console.log("qty",$scope.qtyCart);
        $scope.item = $scope.items.find(
            item=>item.idAccessory=$rootScope.cartAccessory.idAccessory
        );
        if(item.category.type){
            $http.get(`${urlAccessory}/${item.idAccessory}`).then(res=>{
                console.log("cartAccessory",res)
                $rootScope.cartAccessory= res.data;
                if(!$scope.item){
                    $rootScope.cartAccessory.qty=1;
                    $scope.items.push($rootScope.cartAccessory);
                    console.log("addCart",$rootScope.cartAccessory)
                    $scope.saveLocalStorage();
                }else{
                    $scope.item.qty++;
                    $scope.saveLocalStorage();
                }
            }).catch(err=>{
                console.log("error",err)
                $rootScope.cartAccessory=null;
            })
        }
    }
    $scope.saveLocalStorage=function(){
        let json = JSON.stringify(angular.copy($scope.items));
        localStorage.setItem("cart",json);
    }
    $scope.loadLocalStorage = function(){
        let json = localStorage.getItem("cart");
        $scope.items=json? JSON.parse(json):[];
    }
    $scope.overPro=false;
    $scope.overAccess=false;
    $scope.getCategories();
    $scope.loadLocalStorage();
})