'use strict';

(function(){
    angular.
    module("ShopApp").controller("itemcreateController",itemcreateController);

}());


function itemcreateController($scope,$http, UserService, $location){

    var date = new Date();
    date = date.toISOString().substring(0,10);
    $scope.auctet = date;
    
    $scope.create=function(){

        var userr=UserService.returnUser();

        if(userr == null){
            window.alert("You must be logged in to create an item.");
            $location.path("app/login");
        }
            
        else{
            var cat=$scope.category;
            if(cat == ""){
                cat=null;
            }
            var newitem ={
                name:$scope.name,
                description:$scope.description,
                category:cat,
                endtime:$scope.endtime,
                price:$scope.price,
                // backend handles assigning the right user to it
            };

            $http.post("/item", newitem)
                .then(
                    function(res){
                        window.alert("Item created successfully!");
                        var id = res.data.id;
                        $location.path("app/item/" + id);
                        uploadAll(id);

                    }, function(res){
                        window.alert("Item creation failed: " + res.data.detailedMessage);
                    });
            
        }

    };

    var pictures = [];

    $scope.uploadFile = function (files) {
        var file = $scope.myFile;
        var fd = new FormData();
        fd.append("file", files[0]);
        pictures.push(fd);
        console.log("added pic");
    };

    uploadAll = function (id){
        for (var i = 0; i < pictures.length; i++) {
            console.log(pictures[i]);
            helper(pictures[i], id);
        }
    };

    helper = function(pic, id){
        $http.post("/item/" + id + "/picture", pic, {
            headers: {'Content-Type': undefined},
            transformRequest: angular.identity
        }).success(function () {
            console.log("success");
        })
            .error(function (res) {
                console.log("fail");
                console.log(res.data.detailedMessage);
            });
    }
    
}