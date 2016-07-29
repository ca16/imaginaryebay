'use strict';
(function(){
    angular.
    module("ShopApp").controller("adminController",adminController);

}());


function adminController($scope,$http, UserService, $location) {

    var userr = UserService.returnUser();

    if (userr == null) {
        window.alert("You must be logged in to view this page.");
        $location.path("app/login");
    }
    else if (!userr.admin) {
        window.alert("You are not authorized to view this page.");
        $location.path("#/app/home");
    }

    else {
        $http.get("/user").success(function(res){
            $scope.users = res;
        });
    }
    
    $scope.profile = function (id) {
        console.log("Hi");
        console.log("app/user/" + id);
        $location.path("app/user/" + id);
    }

    $scope.lock = function (id, user) {
        $http.put("/user/" + id + "/lockout")
            .then(
                function (res) {
                    window.alert("User locked out successfully!");
                }, function (res) {
                    window.alert("User locked out failed.");
                });
    }



}