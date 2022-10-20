app.controller('cart-ctrl',function($rootScope,$scope,$http,$window){
    var urlProduct=`http://localhost:8080/rest/product`;
    var urlOrder=`http://localhost:8080/rest/order`;
    var urlCategory=`http://localhost:8080/rest/staff/category`;
    $scope.info={};
    $scope.categories={};
    $scope.addCart=function(id){
        $rootScope.qtyCart+=1;
        console.log("qty",$scope.qtyCart);
        $scope.item=$rootScope.carts.find(it=>it.id==id);
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
        //         $rootScope.carts.push(res.data);
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
        return $rootScope.carts
            .map(item=>item.qty)
            .reduce((total,qty)=>total+qty,0);
    }
    $scope.amounts=function(){
        return $rootScope.carts
            .map(item=>item.qty*item.price)
            .reduce((total,qty)=>total+=qty,0);
    }
    // $scope.saveLocalStorage = function(){
    //
    //     var json = JSON.stringify(angular.copy($rootScope.items));
    //     localStorage.setItem("cart",json);
    // }
    $scope.remove=function(item){
        const index=$rootScope.carts.findIndex(it=>it.idAccessory===item.idAccessory)
        Swal.fire({
            title: 'Xóa: '+item.name+' khỏi giỏ hàng?',
            text: "Sau khi xóa không thể khôi phục lại!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
          }).then((result) => {
            if (result.isConfirmed) {
             $rootScope.carts.splice(index,1);
             $rootScope.saveLocalStorage();
             $rootScope.loadLocalStorage();
             const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 2000,
                timerProgressBar: true,
                didOpen: (toast) => {
                    toast.addEventListener('mouseenter', Swal.stopTimer)
                    toast.addEventListener('mouseleave', Swal.resumeTimer)
                }
            })

            Toast.fire({
                icon: 'success',
                title: 'Xóa thành công!'
            })
            $window.location.href = '#!cart';
            console.log('I was closed by the timer')
            }
          })
    }
    $scope.update=function (item) {
        const index=$rootScope.carts.findIndex(it=>it.idAccessory===item.idAccessory)
        $rootScope.carts[index].qty=item.qty;
        if($rootScope.carts[index].qty<=0){
            let timerInterval
            Swal.fire({
                title: 'Đang xóa vui lòng chờ!',
                html: 'Cửa sổ sẽ tự đóng sau: <b></b> milliseconds.',
                timer: 1000,
                timerProgressBar: true,
                didOpen: () => {
                    Swal.showLoading()
                    const b = Swal.getHtmlContainer().querySelector('b')
                    timerInterval = setInterval(() => {
                        b.textContent = Swal.getTimerLeft()
                    }, 100)
                },
                willClose: () => {
                    clearInterval(timerInterval)
                }
            }).then((result) => {
                /* Read more about handling dismissals below */
                if (result.dismiss === Swal.DismissReason.timer) {
                    $rootScope.carts.splice(index,1);
                    $rootScope.saveLocalStorage();
                    $rootScope.loadLocalStorage();
                    const Toast = Swal.mixin({
                        toast: true,
                        position: 'top-end',
                        showConfirmButton: false,
                        timer: 2000,
                        timerProgressBar: true,
                        didOpen: (toast) => {
                            toast.addEventListener('mouseenter', Swal.stopTimer)
                            toast.addEventListener('mouseleave', Swal.resumeTimer)
                        }
                    })

                    Toast.fire({
                        icon: 'success',
                        title: 'Xóa thành công!'
                    })
                    $rootScope.carts.splice(index,1);
                    $window.location.href = '#!cart';
                    console.log('I was closed by the timer')
                }
            })
        }
        $rootScope.saveLocalStorage();
        $rootScope.loadLocalStorage();
        $rootScope.qtyCart++;
        console.log('add',item.qty)
    }
    $scope.raise=function (item) {
        const index=$rootScope.carts.findIndex(it=>it.idAccessory===item.idAccessory)
        $rootScope.carts[index].qty++;
        $rootScope.saveLocalStorage();
        $rootScope.loadLocalStorage();
        $rootScope.qtyCart++;
        console.log('add',item.qty)
    }
    $scope.reduce=function (item) {
        const index=$rootScope.carts.findIndex(it=>it.idAccessory===item.idAccessory)
        $rootScope.carts[index].qty--;
        if($rootScope.carts[index].qty<=0){
            let timerInterval
            Swal.fire({
                title: 'Đang xóa vui lòng chờ!',
                html: 'Cửa sổ sẽ tự đóng sau: <b></b> milliseconds.',
                timer: 1000,
                timerProgressBar: true,
                didOpen: () => {
                    Swal.showLoading()
                    const b = Swal.getHtmlContainer().querySelector('b')
                    timerInterval = setInterval(() => {
                        b.textContent = Swal.getTimerLeft()
                    }, 100)
                },
                willClose: () => {
                    clearInterval(timerInterval)
                }
            }).then((result) => {
                /* Read more about handling dismissals below */
                if (result.dismiss === Swal.DismissReason.timer) {
                    $rootScope.carts.splice(index,1);
                    $rootScope.saveLocalStorage();
                    $rootScope.loadLocalStorage();
                    const Toast = Swal.mixin({
                        toast: true,
                        position: 'top-end',
                        showConfirmButton: false,
                        timer: 2000,
                        timerProgressBar: true,
                        didOpen: (toast) => {
                            toast.addEventListener('mouseenter', Swal.stopTimer)
                            toast.addEventListener('mouseleave', Swal.resumeTimer)
                        }
                    })

                    Toast.fire({
                        icon: 'success',
                        title: 'Xóa thành công!'
                    })
                    $rootScope.carts.splice(index,1);
                    $window.location.href = '#!cart';
                    console.log('I was closed by the timer')
                }
            })
        }
        $rootScope.saveLocalStorage();
        $rootScope.loadLocalStorage();
        $rootScope.qtyCart--;
        console.log('add',item.qty)
    }
    $scope.totalPrice=function () {
        let total=0;
        $rootScope.carts.forEach(item=>{
            total+=item.qty*item.price;
        })
        return total;
    }
    $scope.totalShip=function () {
        let ship=50000;
        return ship;
    }
    $scope.totals=function () {
        return $scope.totalPrice()+$scope.totalShip();
    }
    $scope.refresh=function (){
        $rootScope.carts=[];
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
                    $http.post(urlOrder,$rootScope.carts).then(res=>{
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
    $rootScope.loadLocalStorage();
})