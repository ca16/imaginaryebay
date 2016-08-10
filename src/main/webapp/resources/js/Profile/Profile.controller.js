/**
 * Created by Ben_Big on 8/8/16.
 */

'use strict';
(function(){
    angular.
    module("ShopApp").controller("profileController",profileController);
}());

function profileController(UserService,$scope){
    $scope.user=UserService.returnUser();
    $scope.userName=$scope.user.name;
    $scope.userEmail=$scope.user.email;
    if ($scope.user.address!==null) {
        $scope.userAddress = $scope.user.address;
    }
    else{
        $scope.userAddress = "Please update your address info";
    }





}