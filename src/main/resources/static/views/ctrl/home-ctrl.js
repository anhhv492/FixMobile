 app.controller('home-ctrl',function($rootScope,$scope,$http){
    var urlCategory=`http://localhost:8080/rest/guest/category`;
    var urlAccessory=`http://localhost:8080/rest/guest/accessory`;
    var urlProduct=`http://localhost:8080/rest/guest/product`;
    $scope.cateAccessories=[];
    $scope.cateProducts=[];
    $scope.item= {};
    $rootScope.carts=[];
    $rootScope.qtyCart=0;
    $rootScope.account=null;
    $scope.getAccount=function (){
        $http.get("http://localhost:8080/rest/account").then(resp=>{
            $rootScope.account=resp.data;
            console.log("resp account",resp.data);
        }).catch(error=>{
            $rootScope.account=null;
            console.log("Error",error);
        });
    }
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
        if(item.type){
            $http.get(`${urlAccessory}/cate-access/${item.idCategory}`).then(res=>{
                $rootScope.detailAccessories=res.data;
                console.log("detailAccessories",$rootScope.detailAccessories)
            }).catch(err=>{
                $rootScope.detailAccessories=null;
                console.log("error",err)
            })
        }else{
            $http.get(`${urlProduct}/cate-product/${item.idCategory}`).then(res=>{
                $rootScope.detailAccessories=res.data;
                console.log("detailProducts",$rootScope.detailAccessories)
            }).catch(err=>{
                $rootScope.detailAccessories=null;
                console.log("error",err)
            })
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
                const Toast = Swal.mixin({
                    toast: true,
                    position: 'top-end',
                    showConfirmButton: false,
                    timer: 1500,
                    timerProgressBar: true,
                    didOpen: (toast) => {
                        toast.addEventListener('mouseenter', Swal.stopTimer)
                        toast.addEventListener('mouseleave', Swal.resumeTimer)
                    }
                })

                Toast.fire({
                    icon: 'success',
                    title: 'Thêm thành công!'
                })
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

        for (let i = 0; i < $rootScope.carts.length; i++) {
            $rootScope.carts.find(item=>{
                $http.get("http://localhost:8080/cart-accessory/"+item.idAccessory).then(res=>{
                    $rootScope.carts[i].price=res.data.price;
                    console.log("price",item.price)
                }).catch(err=>{
                    console.log("error",err)
                })
            })
        }
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
    $scope.getAccount();
})