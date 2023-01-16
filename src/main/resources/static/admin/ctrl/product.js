app.controller('product', function ($scope, $http, $window, $rootScope) {
    const pathAPI = "/rest/staff/product";
    const callApiPage = "http://localhost:8080/rest/staff/product/pageImei?page="
    const callDeleteImei = "http://localhost:8080/rest/staff/product/deleteImeiById?id="
    $scope.formProduct = {};
    $scope.imeis = []
    $scope.products = [];
    $scope.categories = [];
    $scope.colors = [];
    $scope.images = [];
    $scope.capacitys = [];
    $scope.rams = [];
    $scope.index = 0;
    $scope.imeisPage = 1;
    $scope.check_first = false;
    $scope.check_last = true;
    $scope.check_first_imei = false;
    $scope.check_last_imei = true;
    $scope.check_next_imei = true;
    $scope.check_prev_imei = false;
    $scope.totalPages = 0;
    $scope.currentPage = 0;
    $scope.pageImei = [];
    $scope.totalPagesImei = 0;
    $scope.currentPageImei = 0;
    $scope.title = {
        insert: 'Thêm mới',
        update: 'Cập nhật'
    };
    $rootScope.check = null;
    const jwtToken = localStorage.getItem("jwtToken")
    const token = {
        headers: {
            Authorization: `Bearer ` + jwtToken
        }
    }
    $scope.message = function (mes) {
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
            title: mes,
        })
    }
    $scope.error = function (err) {
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
            icon: 'error',
            title: err,
        })
    }
    $scope.checkButton = true;
    $scope.checkSubmit = false;
    $scope.getProducts = function () {
        $http.get(`${pathAPI}/page/pushedlist?page=0`, token).then(function (response) {
            $scope.products = response.data.list;
            $scope.totalPages = response.data.totalPages;
            $scope.currentPage = response.data.currentPage;
        }).catch(error => {
            console.log(error);
        });
        $scope.getCategories();
        $scope.getRam();
        $scope.getColor();
        $scope.getCapacity();
    }
    $scope.getCategories = function () {
        $http.get(`${pathAPI}/category`, token).then(function (response) {
            $scope.categories = response.data;
        }).catch(error => {
            console.log("error findByCate", error);
        });
    };
    $scope.getRam = function () {
        $http.get(`${pathAPI}/getAllRam`, token).then(function (response) {
            $scope.rams = response.data;
        }).catch(error => {
            console.log(error + 'looxi');
        });
    };
    $scope.getColor = function () {
        $http.get(`${pathAPI}/getAllColor`, token).then(function (response) {
            $scope.colors = response.data;
        }).catch(error => {
            console.log(error + 'looxi');
        });
    };
    $scope.getCapacity = function () {
        $http.get(`${pathAPI}/getAllCapacity`, token).then(function (response) {
            $scope.capacitys = response.data;
        }).catch(error => {
            console.log(error + 'looxi');
        });
    };
    $scope.getImageProduct = function () {
        $http.get(`rest/files/images/products`, token).then(function (response) {
            $scope.images = response.data;
            console.log(reponse.data);
            alert('dddd' + response.data);
        }).catch(error => {
            console.log("lỗi" + error);
        });
    };
    $scope.delete = function (formProduct) {
        Swal.fire({
            title: 'Bạn có chắc muốn xóa: ' + formProduct.name + '?',
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
                    title: 'Đang xóa: ' + formProduct.name + '!',
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
                    if (result.dismiss === Swal.DismissReason.timer) {
                        $http.post(pathAPI + `/delete?id=${formProduct.idProduct}`, formProduct.idProduct, token).then(response => {
                            $scope.message('Đã cập nhật trạng thái  sản phẩm thành hết hàng');
                            $scope.getProducts();
                        }).catch(error => {
                            $scope.error('xóa thất bại');
                        });
                        console.log('I was closed by the timer')
                    }
                })

            }
        })

    };
    $scope.edit = function (formProduct) {
        $scope.formProduct = angular.copy(formProduct);
        $scope.formProduct.category = formProduct.category.idCategory;
        $scope.formProduct.ram = formProduct.ram.idRam;
        $scope.formProduct.color = formProduct.color.idColor;
        $scope.formProduct.capacity = formProduct.capacity.idCapacity;
        $scope.checkSubmit = true;
        $scope.checkButton = false;
    };
    let head = {
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ` + jwtToken

        }
    }

    $scope.onUpdate = function () {
        var formData = new FormData();
        angular.forEach($scope.files, function (file) {
            formData.append('files', file);
        });
        formData.append('idProduct', $scope.formProduct.idProduct);
        formData.append('name', $scope.formProduct.name);
        formData.append('note', $scope.formProduct.note);
        formData.append('size', $scope.formProduct.size);
        formData.append('price', $scope.formProduct.price);
        formData.append('camera', $scope.formProduct.camera);
        formData.append('status', $scope.formProduct.status)
        formData.append('category', $scope.formProduct.category)
        formData.append('ram', $scope.formProduct.ram)
        formData.append('color', $scope.formProduct.color)
        formData.append('capacity', $scope.formProduct.capacity)
        let req = {
            method: 'POST',
            url: '/rest/staff/product/updateProduct?id=' + $scope.formProduct.idProduct,
            headers: {
                'Content-Type': undefined,
                Authorization: `Bearer ` + jwtToken
                // or  'Content-Type':'application/json'
            },
            data: formData
        }
        let timerInterval
        Swal.fire({
            title: 'Đang thêm  mới vui lòng chờ!',
            html: 'Vui lòng chờ <b></b> milliseconds.',
            timer: 5500,
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
        });
        $http(req).then(response => {
            console.log("ddd " + response.data);
            $scope.message("Cập nhật sản phẩm thành công");
            $scope.refresh();
            $scope.getProducts();
        }).catch(error => {
            $scope.error('Cập nhật thất bại');
        });

    };
    $scope.doSubmit = function () {
        if ($scope.formProduct.idProduct) {
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
        } else {

            let timerInterval
            Swal.fire({
                title: 'Đang lưu mới!',
                html: 'Vui lòng chờ <b></b> milliseconds.',
                timer: 2500,
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
    $scope.refresh = function () {
        $scope.formProduct.idProduct = null;
        $scope.formProduct = {};
        $scope.formProduct.status = false;
        $scope.formProduct.image = 'logo-mobile.png';
        $scope.checkSubmit = false;
        $scope.checkButton = true;
        $scope.getProducts();
    };

    //$scope.files={};
    $scope.uploadFile = function (files) {
        $scope.files = files;
        console.log($scope.files);
    }


    // thêm mới
    $scope.onSave = function () {
        var formData = new FormData();
        angular.forEach($scope.files, function (file) {
            formData.append('files', file);
        });
        formData.append('name', $scope.formProduct.name);
        formData.append('note', $scope.formProduct.note);
        formData.append('size', $scope.formProduct.size);
        formData.append('price', $scope.formProduct.price);
        formData.append('camera', $scope.formProduct.camera);
        formData.append('status', $scope.formProduct.status = 1)
        formData.append('category', $scope.formProduct.category)
        formData.append('ram', $scope.formProduct.ram)
        formData.append('color', $scope.formProduct.color)
        formData.append('capacity', $scope.formProduct.capacity)
        console.log($scope.formProduct.category)
        let req = {
            method: 'POST',
            url: '/rest/staff/product/saveProduct',
            headers: {
                'Content-Type': undefined,
                Authorization: `Bearer ` + jwtToken
                // or  'Content-Type':'application/json'
            },
            data: formData
        }
        let timerInterval
        Swal.fire({
            title: 'Đang thêm  mới vui lòng chờ!',
            html: 'Vui lòng chờ <b></b> milliseconds.',
            timer: 5500,
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
        });
        $http(req).then(response => {
            console.log("ddd " + response);
            $scope.message("thêm mới sản phẩm thành công");
            $scope.refresh();
            $scope.getProducts();
        }).catch(error => {
            $scope.error('thêm mới thất bại');
        });
    };


    // pagination phân trang
    $scope.next = function () {
        $scope.check_first = true;
        $scope.index++;
        if ($scope.index >= $scope.totalPages) {
            $scope.index = 0;
            $scope.check_first = false;
            $scope.check_last = true;
        }
        if ($scope.index == $scope.totalPages - 1) {
            $scope.check_first = true;
            $scope.check_last = false;
        }
        $http.get(pathAPI + `/page/pushedlist?page=` + $scope.index, token).then(res => {
            $scope.products = res.data.list;
            console.log('Load product thành công', res.data.list);
        }).catch(err => {
            console.log('Load product failse', err.data.list);
        })
    }
    $scope.prev = function () {
        $scope.check_last = true;
        $scope.index--;
        if ($scope.index < 0) {
            $scope.index = $scope.totalPages - 1;
            $scope.check_first = true;
            $scope.check_last = false;
        }
        if ($scope.index == 0) {
            $scope.check_first = false;
            $scope.check_last = true;
        }
        $http.get(pathAPI + `/page/pushedlist?page=` + $scope.index, token).then(res => {
            $scope.products = res.data.list;
            console.log('Load product success', res.data.list)
        }).catch(err => {
            console.log('Load product failse', err.data);
        })
    }
    $scope.first = function () {
        $scope.check_first = false;
        $scope.check_last = true;
        $scope.index = 0;
        $http.get(pathAPI + `/page/pushedlist?page=` + $scope.index, token).then(res => {
            $scope.products = res.data.list;
        }).catch(err => {
            console.log('Load accessories failse', err.data);
        })
    }
    $scope.last = function () {
        $scope.check_first = true;
        $scope.check_last = false;
        $scope.index = $scope.totalPages - 1;
        $http.get(pathAPI + `/page/pushedlist?page=` + $scope.index, token).then(res => {
            $scope.products = res.data.list;
            console.log('Load product success', res.data.list)
        }).catch(err => {
            console.log('Load product failse', err.data);
        })
    }

    $scope.xcellData = function (files) {
        var form = new FormData();
        form.append('file', files[0]);
        let timerInterval
        Swal.fire({
            title: 'Đang thêm hàng loạt!',
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
            if (result.dismiss === Swal.DismissReason.timer) {
                $http.post(pathAPI + '/readExcel', form, token, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                }).then(res => {
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
                        title: 'Thêm file Excel thành công'
                    })
                    console.log('excel', res);
                }).catch(err => {
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
                        title: 'Có lỗi xảy ra!'
                    })
                    console.log('err', err);
                })
                console.log('I was closed by the timer')
            }
        })
    }
    $scope.doSubmitImay = function () {
        if ($scope.formProduct.idImay) {
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
                if (result.dismiss === Swal.DismissReason.timer) {
                    $scope.onUpdate();
                    console.log('I was closed by the timer')
                }
            })
        } else {
            let timerInterval
            Swal.fire({
                title: 'Đang lưu mới!',
                html: 'Vui lòng chờ <b></b> milliseconds.',
                timer: 2500,
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
                if (result.dismiss === Swal.DismissReason.timer) {
                    $scope.saveImay();
                    console.log('I was closed by the timer')
                }
            })
        }
    }
    $scope.saveImay = function () {
        var form = new FormData();
        form.append('name',  $scope.formProduct.name );
        form.append('product', $scope.formProduct.product);
        let req = {
            method: 'POST',
            url: '/rest/staff/product/saveImay',
            headers: {
                'Content-Type': undefined, // or  'Content-Type':'application/json'
                Authorization: `Bearer ` + jwtToken
            },
            data: form
        }
        $http(req, token).then(response => {
            if(response.data) {
                $scope.message('Thêm thành công');
                $scope.pageImeiFt(1);
                $scope.formProduct = {}
            }else{
                $scope.error('Imei máy đã tồn tại');
                $scope.formProduct = {}
            }
        }).catch(error => {
            $scope.error('Imei máy đã tồn tại');
        })
    }
    $scope.xcellDataImay = function (files) {
        var form = new FormData();
        form.append('file', files[0]);

        let timerInterval
        Swal.fire({
            title: 'Đang thêm hàng loạt!',
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
            if (result.dismiss === Swal.DismissReason.timer) {
                $http.post(pathAPI + '/readExcelImay', form, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                }).then(res => {
                    $scope.message("Thêm hàng loạt dữ liệu thành công")
                    console.log('excel', res);
                }).catch(err => {
                    $scope.error("thêm mới hàng loạt thất bại")
                    console.log('err', err);
                })
                console.log('I was closed by the timer')
            }
        })
    }

    $scope.getProducts();

    $scope.addImei = function () {
        $scope.imeis.push({
            value: ''
        });
        console.log($scope.imeis);
    }
    $scope.removeImei = function () {
        $scope.imeis.splice(
            $scope.imeis.indexOf('')
        )
    }

    $scope.pageImeiFt = function (page) {
        $http.get(callApiPage + page, token).then(respon => {
            $scope.pageImei = respon.data.content;
            $scope.totalPagesImei = respon.data.totalPages;
            $scope.currentPageImei = respon.data.pageable.pageNumber + 1;
            console.log($scope.pageImei);
        }).catch(err => {
            console.log('Load Imei failse', err.data);
        })
    }

    $scope.deleteImei = function (imeiForm) {
        Swal.fire({
            title: 'Bạn có chắc muốn xóa: ' + imeiForm.name + '?',
            text: "Xóa không thể khôi phục lại!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                $http.post(callDeleteImei + imeiForm.idImay, imeiForm.idImay, token).then(response => {
                    $scope.pageImei.splice($scope.pageImei.indexOf(imeiForm), 1);
                    $scope.message('Đã xóa thành công Imei');

                }).catch(error => {
                    $scope.error('xóa thất bại');
                });
            }
        })

    };
    $scope.nextPageImei = function () {
        $scope.check_first_imei = true;
        $scope.check_prev_imei = true;
        $scope.imeisPage++;

        if ($scope.imeisPage === $scope.totalPagesImei) {
            $scope.check_first_imei = true;
            $scope.check_prev_imei = true;
            $scope.check_next_imei = false;
            $scope.check_last_imei = false;
        }
        $scope.pageImeiFt($scope.imeisPage);
    }

    $scope.prevPageImei = function () {
        $scope.check_last_imei = true;
        $scope.check_next_imei = true;
        $scope.imeisPage--;

        if ($scope.imeisPage == 1) {
            $scope.check_first_imei = false;
            $scope.check_prev_imei = false;
            $scope.check_last_imei = true;
            $scope.check_next_imei = true;
        }
        $scope.pageImeiFt($scope.imeisPage);
    }
    $scope.firstPageImei = function () {
        $scope.check_first_imei = false;
        $scope.check_prev_imei = false;
        $scope.check_next_imei = true;
        $scope.check_last_imei = true;
        $scope.imeisPage = 1;
        $scope.pageImeiFt($scope.imeisPage);
    }

    $scope.lastPageImei = function () {
        $scope.check_first_imei = true;
        $scope.check_last_imei = false;
        $scope.check_prev_imei = true;
        $scope.check_next_imei = false;
        $scope.imeisPage = $scope.totalPagesImei;
        $scope.pageImeiFt($scope.imeisPage);
    }
    $scope.pageImeiFt($scope.imeisPage);


    $scope.logOut = function () {
        $window.location.href = "http://localhost:8080/views/index.html#!/login"
        Swal.fire({
            icon: 'error',
            title: 'Vui lòng đăng nhập lại!!',
            text: 'Tài khoản của bạn không có quyền truy cập!!',
        })
    }

    $scope.checkLogin = function () {
        if (jwtToken == null) {
            $scope.logOut();
        } else {
            $http.get("http://localhost:8080/rest/user/getRole", token).then(respon => {
                if (respon.data.name === "USER") {
                    $scope.logOut();
                } else if (respon.data.name === "ADMIN") {
                    $rootScope.check = null;
                } else {
                    $rootScope.check = "OK";
                }
            })
        }
    }

    $scope.checkLogin();

    $scope.generationName = function () {
        if ($scope.formProduct.name != undefined || $scope.formProduct.name != null || $scope.formProduct.name != '') {
            $scope.formProduct.nam = '';
        }
        if ($scope.formProduct.category != undefined || $scope.formProduct.category != null || $scope.formProduct.category != '') {
            for (let i = 0; i < $scope.categories.length; i++) {
                if ($scope.formProduct.category == $scope.categories[i].idCategory) {
                    $scope.formProduct.name = $scope.categories[i].name;
                }
            }
        }
        if ($scope.formProduct.capacity != undefined || $scope.formProduct.capacity != null || $scope.formProduct.capacity != '') {
            for (let i = 0; i < $scope.capacitys.length; i++) {
                if ($scope.formProduct.capacity == $scope.capacitys[i].idCapacity) {
                    $scope.formProduct.name += ' Dung Lượng ' + $scope.capacitys[i].name;
                }
            }
        }
        if ($scope.formProduct.ram != undefined || $scope.formProduct.ram != null || $scope.formProduct.ram != '') {
            for (let i = 0; i < $scope.rams.length; i++) {
                if ($scope.formProduct.ram == $scope.rams[i].idRam) {
                    $scope.formProduct.name += ' RAM ' + $scope.rams[i].name;
                }
            }
        }
        if ($scope.formProduct.color != undefined || $scope.formProduct.color != null || $scope.formProduct.color != '') {
            for (let i = 0; i < $scope.colors.length; i++) {
                if ($scope.formProduct.color == $scope.colors[i].idColor) {
                    $scope.formProduct.name += ' Màu ' + $scope.colors[i].name;
                }
            }
        }

    }

});
