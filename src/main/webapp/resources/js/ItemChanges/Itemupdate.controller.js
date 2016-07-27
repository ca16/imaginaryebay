(function () {
    angular.module("ShopApp").controller("itemupdateController", itemupdateController);

}());

function itemupdateController($scope, $http, UserService, $location, $routeParams, ItemService) {

    var itemId = $routeParams.itemId;
    var item;

    ItemService.getItem(itemId).success(function (data) {
        item = data;
        $scope.item = data;
        // var date = item.endtime;
        // date = new Date(date);
        // date = date.toISOString().substring(0,10);
        // $scope.auctet = date;
        //document.getElementById("itemcategory").value = item.category;

    });

    $scope.update = function () {

        var userr = UserService.returnUser();

        if (userr == null) {
            window.alert("You must be logged in to edit items.");
            $location.path("app/login");
        }
        else if (userr.id != item.userr.id) {
            window.alert("You are not authorized to edit this item.");
            $location.path("app/item/" + itemId);
        }

        else {
            var cat = $scope.category;
            if (cat == "") {
                cat = null;
            }
            var improvedItem = {
                name: $scope.name,
                description: $scope.description,
                category: cat,
                endtime: $scope.endtime,
                price: $scope.price,
            }

            $http.put("/item/" + itemId, improvedItem)
                .then(
                    function (res) {
                        window.alert("Item updated successfully!");
                        $location.path("app/item/" + itemId);
                    }, function (res) {
                        window.alert("Item update failed: " + res.data.detailedMessage);
                    });

            // if (picture1 != null) {
            //     $http.post("/item/" + itemId + "/picture", $scope.picture1)
            //         .then(
            //             function (res) {
            //                 window.alert("Picture1 added successfully!");
            //             }, function (res) {
            //                 window.alert("Item update failed: " + res.data.detailedMessage);
            //             });
            // }
            
        }

    }

    $scope.uploadFile = function(){
        var file = $scope.myFile;
        var fd = new FormData();
        fd.append('file', file);
        $http.post("/item/" + itemId + "/picture", fd)
            .success(function(){
                console.log("success")
            })
            .error(function(){
                console.log("fail")
            });
    }

}