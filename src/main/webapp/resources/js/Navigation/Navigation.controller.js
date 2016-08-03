'use strict';
(function(){
   angular
       .module("ShopApp").controller("navigationController",navigationController);
}())


function navigationController($scope,$http,UserService){
    //console.log("test");
    $scope.loggedInFlag=false;
    $scope.currentUser=UserService.returnUser();
    if ($scope.currentUser!=null){
        $scope.loggedInFlag=true;
       // $scope.adminFlag=$scope.currentUser.adminFlag;
    }
    $scope.adminFlag=true;




}