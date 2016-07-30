'use strict';
(function () {
    angular.module("ShopApp").controller("itemupdateController", itemupdateController);

}());

function itemupdateController($scope, $http, UserService, $location, $routeParams) {

    var itemId;
    if ($routeParams.itemId != null) {
        itemId = $routeParams.itemId;
        $http.get("/item/" + itemId).success(function (data) {
            $scope.item = data;
            var currendtime = new Date(data.endtime);
            $scope.currentendtime = currendtime.toDateString();

        });
    }
    var date = new Date();
    date = date.toISOString().substring(0, 10);
    $scope.auctet = date;
    var maxDescLen = 255;
    $scope.remChar = maxDescLen;

    $scope.create = function () {

        var userr = UserService.returnUser();
        // need to be logged in to create an item

        if (isUserLoggedIn(userr)) {

            var newItem = makeItem();

            $http.post("/item", newItem)
                .then(
                    function (res) {
                        itemId = res.data.id;
                        uploadAll(itemId);
                        window.alert("Item created successfully!");
                        $location.path("app/item/" + itemId);

                    }, function (res) {
                        window.alert("Item creation failed: " + res.data.detailedMessage);
                    });
        }
    }

    $scope.update = function () {

        var userr = UserService.returnUser();
        // need to be logged in to create an item
        if (isUserLoggedIn(userr)) {

            // you can only edit your own items
            if (userr.id != $scope.item.userr.id) {
                window.alert("You are not authorized to edit this item.");
                $location.path("app/item/" + itemId);
            }

            else {

                var improvedItem = makeItem();

                $http.put("/item/" + itemId, improvedItem)
                    .then(
                        function (res) {
                            uploadAll();
                            window.alert("Item updated successfully!");
                            $location.path("app/item/" + itemId);
                        }, function (res) {
                            window.alert("Item update failed: " + res.data.detailedMessage);
                        });
            }
        }

    }

    $scope.lenCheck = function () {
        if ($scope.description.length > maxDescLen) {
            $scope.description = $scope.description.substring(0, maxDescLen);
        }
        $scope.remChar = maxDescLen - $scope.description.length;
    }

    function selectCat() {
        var cat = $scope.category;
        if (cat == "") {
            cat = null;
        }
        return cat;
    }

    function selectEndtime() {
        var toPass;
        var aucttype = $scope.auctiontype;
        // long auctions
        if (aucttype == "long") {
            toPass = $scope.endtime;
        } // short auctions
        else {
            var currDate = new Date();
            currDate.setHours((currDate.getHours() * 1) + ($scope.shortendtime * 1));
            toPass = currDate;
        }
        return toPass;
    }

    function isUserLoggedIn(user) {
        if (user == null) {
            window.alert("You must be logged in to create an item.");
            $location.path("app/login");
            return false;
        }
        else{
            return true;
        }

    }

    function makeItem() {
        // empty category
        var cat = selectCat();

        // handle diff type of auctions
        var etToPass = selectEndtime();

        var item = {
            name: $scope.name,
            description: $scope.description,
            category: cat,
            endtime: etToPass,
            price: $scope.price,
        }// backend handles assigning user to item

        return item;

    }

    var pictures = [];

    $scope.addPic = function () {
        var file = document.getElementById('file').files[0];
        var fd = new FormData();
        fd.append("file", file);
        pictures.push(fd);
        console.log("added pic");
    }

    function uploadAll() {
        for (var i = 0; i < pictures.length; i++) {
            uploadSinglePic(pictures[i]);
        }
    }

    function uploadSinglePic(pic) {
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