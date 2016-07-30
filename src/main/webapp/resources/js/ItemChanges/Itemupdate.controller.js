'use strict';
(function () {
    angular.module("ShopApp").controller("itemupdateController", itemupdateController);

}());

function itemupdateController($scope, $http, UserService, $location, $routeParams) {

    var itemId = $routeParams.itemId;
    var date = new Date();
    date = date.toISOString().substring(0, 10);
    $scope.auctet = date;
    var maxDescLen = 255;
    $scope.remChar = maxDescLen;

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

            // handle diff type of auctions
            var etToPass;
            var aucttype = $scope.auctiontype;
            // long auctions
            if (aucttype == "long") {
                etToPass = $scope.endtime;
            } // short auctions
            else{
                var currDate = new Date();
                currDate.setHours((currDate.getHours()*1) + ($scope.shortendtime*1));
                etToPass = currDate;
            }

            var improvedItem = {
                name: $scope.name,
                description: $scope.description,
                category: cat,
                endtime: etToPass,
                price: $scope.price,
            }

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

    var pictures = [];

    $scope.addPic = function () {
        var file = document.getElementById('file').files[0]
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

    $scope.lenCheck = function(){
        if ($scope.description.length > maxDescLen){
            $scope.description = $scope.description.substring(0, maxDescLen);
        }
        $scope.remChar = maxDescLen - $scope.description.length;
    }

}