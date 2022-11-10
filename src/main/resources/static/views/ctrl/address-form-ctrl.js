app.controller('address-form-ctrl', function ($http, $scope, $window) {

    $scope.form = {}
    $scope.title={
        insert:'Thêm mới',
        update:'Cập nhật'
    };
    $scope.checkSubmit=false;
    $scope.listaddress= [];

    const callApiAddress = "http://localhost:8080/rest/user/address";

    const jwtToken = localStorage.getItem("jwtToken")
    const token = {
        headers: {
            Authorization: `Bearer `+jwtToken
        }
    }

    var citis = document.getElementById("city");
    var districts = document.getElementById("district");
    var wards = document.getElementById("ward");

    var Parameter = {
        url: "https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json",
        method: "GET",
        responseType: "application/json",
    };

    $scope.city = "";
    $scope.distri = "";
    $scope.war = "";
    $scope.city = "";
    var promise = axios(Parameter);
    promise.then(function (result) {
        renderCity(result.data);
    });

    function renderCity(data) {
        for (const x of data) {
            citis.options[citis.options.length] = new Option(x.Name, x.Id);
        }
        citis.onchange = function () {
            district.length = 1;
            ward.length = 1;
            if (this.value != "") {
                const result = data.filter(n => n.Id === this.value);

                $scope.city =  result[0].Name;
                console.log($scope.city)
                for (const k of result[0].Districts) {
                    district.options[district.options.length] = new Option(k.Name, k.Id);
                }
            }
        };
        districts.onchange = function () {
            ward.length = 1;
            const dataCity = data.filter((n) => n.Id === citis.value);
            if (this.value != "") {
                const dataWards = dataCity[0].Districts.filter(n => n.Id === this.value)[0].Wards;
                $scope.distri =  dataCity[0].Districts.filter(n => n.Id === this.value)[0].Name;
                console.log($scope.distri)
                for (const w of dataWards) {
                    wards.options[wards.options.length] = new Option(w.Name, w.Id);
                    console.log(w.name)
                }
            }
        };
        wards.onchange = function () {
            const datadistri = data.filter((n) => n.Id === citis.value)[0]
                .Districts.filter(n => n.Id === districts.value)[0].Wards.filter(n => n.Id === wards.value)[0].Name;
            $scope.war = datadistri;
            console.log($scope.war)
        };
    }



    $scope.onSave = function() {

        $http.post(callApiAddress+"/create", $scope.form, token,
            $scope.form.addressTake=$scope.war+", "+$scope.distri+", "+$scope.city).then(response => {
            const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true,
                didOpen: (toast) => {
                    toast.addEventListener('mouseenter', Swal.stopTimer)
                    toast.addEventListener('mouseleave', Swal.resumeTimer)
                }
            })

            Toast.fire({
                icon: 'success',
                title: 'Thêm mới thành công!',
            })
            $window.location.href = '#!address'

        }).catch(error => {
            const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true,
                didOpen: (toast) => {
                    toast.addEventListener('mouseenter', Swal.stopTimer)
                    toast.addEventListener('mouseleave', Swal.resumeTimer)
                }
            })

            Toast.fire({
                icon: 'error',
                title: 'Thêm mới thất bại!',
            })
        });
    };

    $scope.doSubmit = function() {
        if($scope.form.idAddress) {
            let timerInterval
            Swal.fire({
                title: 'Đang cập nhật!',
                html: 'Vui lòng chờ <b></b> milliseconds.',
                timer: 1500,
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
                    $scope.onUpdate();
                    console.log('I was closed by the timer')
                }
            })
        }else{
            let timerInterval
            Swal.fire({
                title: 'Đang lưu mới!',
                html: 'Vui lòng chờ <b></b> milliseconds.',
                timer: 1500,
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
                    $scope.onSave();
                    console.log('I was closed by the timer')
                }
            })
        }
    };

    $scope.getAddressByUsername = function () {
        $http.get(callApiAddress+"/getByUsername", token).then(function (respon){
            $scope.listaddress = respon.data;
            console.log($scope.listaddress)
        }).catch(err => {
            console.log(err)
        })
    }

    $scope.edit = function(addres) {
        $window.location.href = '#!addressCretae'
        $scope.form = angular.copy(addres);
        console.log(addres.addressTake)
        $scope.checkSubmit=true;
    };

    $scope.delete = function(addres) {
        Swal.fire({
            title: 'Bạn có chắc muốn xóa: '+addres.addressTake+'?',
            text: "Xóa không thể khôi phục lại!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                let timerInterval
                Swal.fire({
                    title: 'Đang xóa: '+addres.addressTake+'!',
                    html: 'Vui lòng chờ <b></b> milliseconds.',
                    timer: 1500,
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
                        $http.post(`${callApiAddress}/delete`,[addres.idAddress], token).then(response=> {
                            const Toast = Swal.mixin({
                                toast: true,
                                position: 'top-end',
                                showConfirmButton: false,
                                timer: 3000,
                                timerProgressBar: true,
                                didOpen: (toast) => {
                                    toast.addEventListener('mouseenter', Swal.stopTimer)
                                    toast.addEventListener('mouseleave', Swal.resumeTimer)
                                }
                            })

                            Toast.fire({
                                icon: 'success',
                                title:'Xóa thành công!'
                            })
                            $scope.listaddress.splice($scope.listaddress.indexOf(addres), 1);
                            $scope.getAddressByUsername();
                        }).catch(error=>{
                            const Toast = Swal.mixin({
                                toast: true,
                                position: 'top-end',
                                showConfirmButton: false,
                                timer: 3000,
                                timerProgressBar: true,
                                didOpen: (toast) => {
                                    toast.addEventListener('mouseenter', Swal.stopTimer)
                                    toast.addEventListener('mouseleave', Swal.resumeTimer)
                                }
                            })

                            Toast.fire({
                                icon: 'error',
                                title:'Đã xảy ra lỗi!' ,
                            })
                        });
                        console.log('I was closed by the timer')
                    }
                })

            }
        })

    };

    $scope.getAddressByUsername();

})