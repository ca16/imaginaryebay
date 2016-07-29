'use strict';
(function () {
    angular.module("ShopApp").controller("itemupdateController", itemupdateController);

}());

function itemupdateController($scope, $http, UserService, $location, $routeParams) {

    var itemId = $routeParams.itemId;
    var date = new Date();
    date = date.toISOString().substring(0, 10);
    $scope.auctet = date;

    $http.get("/item/" + itemId).success(function(data){
        $scope.item = data;
        var currendtime = new Date(data.endtime);
        $scope.currentendtime = currendtime.toDateString();

    });

    $scope.update = function () {

        var userr = UserService.returnUser();

        if (userr == null) {
            window.alert("You must be logged in to edit items.");
            $location.path("app/login");
        }
        else if (userr.id != $scope.item.userr.id) {
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
                        $scope.uploadAll();
                        window.alert("Item updated successfully!");
                        $location.path("app/item/" + itemId);
                    }, function (res) {
                        window.alert("Item update failed: " + res.data.detailedMessage);
                    });
        }

    }

    var pictures = [];

    $scope.uploadFile = function (files) {
        var file = $scope.myFile;
        var fd = new FormData();
        fd.append("file", files[0]);
        pictures.push(fd);
        console.log("added pic");
    }

    $scope.uploadAll = function () {
        for (var i = 0; i < pictures.length; i++) {
            $scope.uploadSinglePic(pictures[i]);
        }
    }

    $scope.uploadSinglePic = function (pic) {
        $http.post("/item/" + itemId + "/picture", pic, {
            headers: {'Content-Type': undefined},
            transformRequest: angular.identity
        }).success(function () {
            console.log("success");
        }).error(function (res) {
                console.log("fail");
                console.log(res.data.detailedMessage);
            });
    }

}