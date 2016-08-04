'use strict';
(function () {
    angular.module("ShopApp").controller("userupdateController", userupdateController);

}());

function userupdateController($scope, $http, UserService, $location, $routeParams) {

    var userr = UserService.returnUser();
    var userID = $routeParams.userId;

    if (userr == null) {
        window.alert("You must be logged in to update user information.");
        $location.path("app/login");
    }

    else if (userr.id != userID) {
        window.alert("You can only update your own information.");
        $location.path("app/home");
    }

    else {
        $http.get("/user/" + userID).then(function (res) {
            $scope.user = res.data;
            userr = res.data;
        });

    }

    console.log("changed one shit userra is " + userr);

    $scope.update = function () {

        if (($scope.old == "") || ($scope.old == null)) {
            window.alert("You must enter your password to update any information.");
            return;
        }

        console.log("made it past enter password");

        // if (userr.password != $scope.old) {
        //     window.alert("The old password you entered was incorrect.");
        //     return;
        // }

        if ($scope.first != $scope.confirm) {
            window.alert("Your new password entries don't match.");
            return;
        }

        console.log("made it here do passwords match");

        var updatedName = (($scope.name == null) || ($scope.name == "")) ? userr.name : $scope.name;
        
        console.log("passed name, name is " + updatedName);

        var updatedEmail = (($scope.email == null) || ($scope.email == "")) ? userr.email : $scope.email;

        console.log("past email, email is " + updatedEmail);

        var updatedAddress = ($scope.loc == null) ? userr.address : $scope.loc;

        console.log("past address, adress is: " + updatedAddress);

        var newPass = (($scope.first == null) || ($scope.first == "")) ? $scope.old : $scope.first;

        console.log("past password, updated pass is " + newPass);

        var admin = userr.admin;

        console.log("past admin, admin is " + admin);


        var updatedUser = {
            name: updatedName,
            address: updatedAddress,
            email: updatedEmail,
            password: newPass,
            isAdmin: admin,

        }


        $http.put("/user/" + userID, updatedUser)
            .then(
                function (res) {
                    window.alert("User information updated successfully!");
                    $location.path("app/user/" + userID);
                }, function (res) {
                    window.alert("User information update failed: " + res.data.detailedMessage);
                });

    }

}