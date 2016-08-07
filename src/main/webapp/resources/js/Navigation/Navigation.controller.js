'use strict';
(function(){
   angular
       .module("ShopApp").controller("navigationController",navigationController);
}())


function navigationController($scope,$http,UserService){
    $scope.loggedInFlag=false;
    $scope.currentUser=UserService.returnUser();
    if ($scope.currentUser!=null){
        $scope.loggedInFlag=true;
        $scope.id = $scope.currentUser.id;
        $scope.adminFlag=$scope.currentUser.admin;
    }


}