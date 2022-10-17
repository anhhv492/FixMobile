app.controller('cart-ctrl',function($rootScope,$scope,$http){
    var urlProduct=`http://localhost:8080/rest/product`;
    var urlOrder=`http://localhost:8080/rest/order`;
    var urlCategory=`http://localhost:8080/rest/staff/category`;
    $scope.items = [];
    $scope.info={};
    $scope.categories={};
    $scope.addCart=function(id){
        $rootScope.qtyCart+=1;
        console.log("qty",$scope.qtyCart);
        $scope.item=$scope.items.find(it=>it.id==id);
        // if($scope.item){
        //     $scope.item.qty++;
        //     $scope.saveLocalStorage();
        //     Swal.fire({
        //         position: 'top-end',
        //         icon: 'success',
        //         showConfirmButton: false,
        //         timer: 800
        //     })
        // }else{
        //     $http.get(`${urlProduct}/add/${id}`).then(res=>{
        //         res.data.qty=1;
        //         $scope.items.push(res.data);
        //         $scope.saveLocalStorage();
        //         Swal.fire({
        //             position: 'center',
        //             icon: 'success',
        //             title: 'Add card!',
        //             showConfirmButton: false,
        //             timer: 3000
        //         })
        //         console.log("res saving",res)
        //     }).catch(err=>{
        //         Swal.fire({
        //             position: 'center',
        //             icon: 'error',
        //             title: 'Add card failse!'+id,
        //             showConfirmButton: false,
        //             timer: 3000
        //         })
        //         console.log("error saving",err)
        //     })
        // }
    }
    $scope.counts=function(){
        return $scope.items
            .map(item=>item.qty)
            .reduce((total,qty)=>total+qty,0);
    }
    $scope.amounts=function(){
        return $scope.items
            .map(item=>item.qty*item.price)
            .reduce((total,qty)=>total+=qty,0);
    }
    $scope.saveLocalStorage = function(){

        var json = JSON.stringify(angular.copy($scope.items));
        localStorage.setItem("cart",json);
    }
    $scope.loadLocalStorage = function(){
        var json = localStorage.getItem("cart");
        $scope.items=json? JSON.parse(json):[];
    }
    $scope.remove=function(id){
        var index=$scope.items.findIndex(item=>item.id==id)
        Swal.fire({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
          }).then((result) => {
            if (result.isConfirmed) {

                $scope.items.splice(index,1);
                $scope.saveLocalStorage();
                location.reload();
            }
          })
    }
    $scope.refresh=function (){
        $scope.items=[];
        localStorage.clear();
    }
    $scope.buyCart=function () {
        Swal.fire({
            title: 'Xác nhận thanh toán?',
            text: "Xác nhận thanh toán để mua hàng!",
            icon: 'info',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Xác nhận!'
        }).then((result) => {
            if (result.isConfirmed) {
                const address=$scope.address;
                $scope.info={'address':address,'total':$scope.amounts()};
                $http.post(`${urlOrder}/cart`,$scope.info).then(res=>{
                    $http.post(urlOrder,$scope.items).then(res=>{
                        Swal.fire(
                            'Thành công!',
                            'Cảm ơn quý khách!',
                            'success'
                        )
                        $scope.refresh();
                    })
                })
            }
        })
    }
    $scope.loadLocalStorage();
})