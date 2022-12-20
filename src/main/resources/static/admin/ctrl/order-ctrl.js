app.controller('order-admin-ctrl',function($rootScope,$scope,$http,$window,$filter,$timeout){
    var urlOrder=`http://localhost:8080/rest/staff/order`;
    $scope.orders=[];
    $scope.accs=[];
    $rootScope.idOrder=null;
    $rootScope.orderModel= {};
    $scope.form= {};
    $scope.userSearch= null;
    $scope.showUpdate=false;
    $scope.checkHoTen=false;
    $scope.checkNgayMua=false;
    $scope.checkTongGia=false;
    $scope.checkTrangThai=false;
    $scope.status = [
        {id : '', name : "Thay đổi"},
        {id : 0, name : "Chờ xác nhận"},
        {id : 1, name : "Xác nhận"},
        {id : 2, name : "Đang giao hàng"},
        {id : 3, name : "Hoàn tất giao dịch"},
        {id : 4, name : "Hủy đơn"}
    ];
    const jwtToken = localStorage.getItem("jwtToken")
    const token = {
        headers: {
            Authorization: `Bearer ` + jwtToken
        }
    }

    $scope.getAll=function(){
        $http.get(urlOrder,token).then(function(response){
            $scope.orders=response.data;
        }).catch(error=>{
            console.log('error getOrder',error);
        });
    }
    $scope.show=function(){
        $scope.showUpdate=true;

    }
    $scope.getOrder=function(item){
        $rootScope.idOrder=item.idOrder;
        $rootScope.orderModel=item;
    }
    $scope.updateStatus=function(id){
        let urlUpdate=`http://localhost:8080/rest/staff/order`;
        $scope.showUpdate=false;
        $scope.form.idOrder=id;
        if($scope.form.status==2||$scope.form.status==3){
            $http.get(urlOrder+'/getDetail/'+id,token).then(function(response){
                if(response.data) {
                    Swal.fire({
                        title: 'Cảnh báo!',
                        text: "Có sản phẩm trong đơn hàng không đủ số lượng!",
                        icon: 'warning',
                    })
                }
            });
        }else{
            Swal.fire({
                title: 'Bạn có chắc muốn đổi trạng thái không?',
                text: "Đổi không thể quay lại!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Xác nhận'
            }).then((result) => {
                if (result.isConfirmed) {
                    $http.put(urlUpdate,$scope.form,token).then(function(response){
                        if(response.data){
                            $scope.form.status=null;
                            $scope.getAll();
                            $scope.messageSuccess("Đổi trạng thái thành công");
                        }else{
                            $scope.messageError("Đổi trạng thái thất bại");
                        }
                    }).catch(error=>{
                        $scope.messageError("Đổi trạng thái thất bại");
                    });
                }
            })
        }

        Swal.fire({
            title: 'Bạn có chắc muốn đổi trạng thái không?',
            text: "Đổi không thể quay lại!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Xác nhận'
        }).then((result) => {
            if (result.isConfirmed) {
                $http.put(urlUpdate,$scope.form,token).then(function(response){
                    if(response.data){
                        $scope.form.status=null;
                        $scope.getAll();
                        $scope.messageSuccess("Đổi trạng thái thành công");
                    }else{
                        $scope.messageError("Đổi trạng thái thất bại");
                    }
                }).catch(error=>{
                    $scope.messageError("Đổi trạng thái thất bại");
                });
            }
        })
    }
    $scope.messageSuccess=function (text) {
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
            title: text
        })
    }
    $scope.messageError=function (text) {
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
            icon: 'error',
            title: text
        })
    }
    $scope.findByDate=function(){
        let date1= $filter('date')($scope.date1, "yyyy/MM/dd");
        let date2= $filter('date')($scope.date2, "yyyy/MM/dd");
        $http.get(urlOrder+`/date?date1=`+date1+'&&date2='+date2,token).then(function(response){
            $scope.orders=response.data;
        }).catch(error=>{
            console.log('error getOrder',error);
            $scope.messageError('Không tìm thấy đơn hàng!');
        });
    }
    $scope.findByPrice=function(){
        $http.get(urlOrder+`/price?price1=`+$scope.price1+'&&price2='+$scope.price2,token).then(function(response){
            if(response.data){
                $scope.orders=response.data;
            }
        }).catch(error=>{
            console.log('error getOrder',error);
            $scope.messageError('Không tìm thấy đơn hàng!');
        });
    }
    $scope.findByName=function(){
        $http.get(urlOrder+'/name/'+$scope.nameSearch.trim(),token).then(function(response){
            if(response.data){
                $scope.orders=response.data;
            }
        }).catch(error=>{
            console.log('error getOrder',error);
            $scope.messageError('Không tìm thấy đơn hàng!');
        });
    }
    $scope.findByStatus=function(){
        $http.get(urlOrder+`/status/`+$scope.statusChange,token).then(function(response){
            $scope.orders=response.data;
        }).catch(error=>{
            console.log('error getOrder',error);
            $scope.messageError('Không tìm thấy đơn hàng!');
        });
    }
    $scope.getAllUser=function(){
        $http.get(urlOrder+'/usernames',token).then(function(response){
            if(response.data){
                $scope.accs=response.data;
            }
        }).catch(error=>{
            console.log('error getOrder',error);
        });
    }
    $scope.selectByUser=function(){
        $http.get(urlOrder+'/accounts/'+$scope.userSearch,token).then(function(response){
            if(response.data){
                $scope.orders=response.data;
            }
        }).catch(error=>{
            console.log('error getOrder',error);
        });
    }

    $scope.sortPriceUp=function(){
        let temp = $scope.orders[0];
        for (let i = 0 ; i < $scope.orders.length - 1; i++) {
            for (let j = i + 1; j < $scope.orders.length; j++) {
                if ($scope.orders[i].total > $scope.orders[j].total) {
                    temp = $scope.orders[j];
                    $scope.orders[j] = $scope.orders[i];
                    $scope.orders[i] = temp;
                }
            }
        }
    }
    $scope.sortPriceDown=function(){
        let temp = $scope.orders[0];
        for (let i = 0 ; i < $scope.orders.length - 1; i++) {
            for (let j = i + 1; j < $scope.orders.length; j++) {
                if ($scope.orders[i] < $scope.orders[j]) {
                    temp = $scope.orders[j];
                    $scope.orders[j] = $scope.orders[i];
                    $scope.orders[i] = temp;
                }
            }
        }
    }
    $scope.hoTen=function(){
        $scope.checkHoTen=!$scope.checkHoTen;
    }
    $scope.ngayMua=function(){
        $scope.checkNgayMua=!$scope.checkNgayMua;
    }
    $scope.tongGiaTang=function(){
        $scope.checkTongGia=!$scope.checkTongGia;
        $scope.sortPriceUp();
    }
    $scope.tongGiaGiam=function(){
        $scope.checkTongGia=!$scope.checkTongGia;
        $scope.sortPriceDown();
    }
    $scope.trangThai=function(){
        $scope.checkTrangThai=!$scope.checkTrangThai;
    }
    $scope.status.id='';
    $scope.getAll();
    $scope.getAllUser();

    $scope.getSaleOrDeTail=function (x){
        if($scope.ordersDetail[x].idSale==null){
            $scope.ordersDetail[x].noteSale = '';
        }else {
            var urlSale = `http://localhost:8080/admin/rest/sale/getsale/` + $scope.ordersDetail[x].idSale;
            $http.get(urlSale).then(resp => {
                if (resp.data != '') {
                    let priceSale='';
                    if(resp.data.moneySale==null){
                        priceSale=resp.data.percentSale + ' %';
                    }else{
                        priceSale=resp.data.moneySale + ' VNĐ';
                    }
                    let minValueSale='';
                    if(resp.data.valueMin==null){
                        minValueSale='0 đồng';
                    }else{
                        minValueSale=resp.data.valueMin+' VNĐ';
                    }
                    let typeSale='';
                    if(resp.data.typeSale==0){
                        typeSale='toàn cửa hàng'
                    }else if(resp.data.typeSale==1){
                        typeSale='cho 1 số sản phẩm'
                    }else if(resp.data.typeSale==2){
                        typeSale='theo đơn hàng'
                    }else if(resp.data.typeSale==3){
                        typeSale='cho riêng bạn'
                    }else if(resp.data.typeSale==4){
                        typeSale='cho 1 số phụ kiện'
                    }
                    $scope.ordersDetail[x].noteSale = 'Giảm giá đã dùng: ID giảm giá: "'+resp.data.idSale+'" giảm giá '+typeSale+' giảm '+priceSale+' đơn từ '+minValueSale;
                } else {
                    $scope.ordersDetail[x].noteSale ='';
                }
            }).catch(error => {
                $scope.ordersDetail[x].noteSale ='';
            })
        }
    }
    $scope.getOrderDetail=function (idOrder){
        let url=`/rest/staff/order/detail/`+idOrder;
        $http.get(url).then(function(response){
            if(response.data){
                $scope.ordersDetail=response.data;
                for(let i=0;i<$scope.ordersDetail.length;i++){
                    $scope.getSaleOrDeTail(i)
                }
            }
        }).catch(error=>{
            console.log(error);
        });
        let urlapplysale=`/admin/rest/sale/saleapply/`+idOrder;
        $http.get(urlapplysale).then(function(response){
                $scope.saleApply=response.data;
        }).catch(error=>{
            $scope.saleApply={};
        });
        console.log( $scope.saleApply)
    }

    $scope.sumPriceSale=function (){
        let total=0;
        if($scope.ordersDetail.length!=0) {
            for (let i = 0; i < $scope.ordersDetail.length; i++) {
                if($scope.ordersDetail[i].priceSale==0){
                    total = total + $scope.ordersDetail[i].price;
                }else {
                    total = total + $scope.ordersDetail[i].priceSale;
                }
            }
        }
        console.log(total)
        return total;
    }
    $scope.sumPrice=function (){
        let total=0;
        if($scope.ordersDetail.length!=0) {
            for (let i = 0; i < $scope.ordersDetail.length; i++) {
                total=total+$scope.ordersDetail[i].price;
            }
        }
        return total;
    }
});