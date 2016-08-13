/**
 * Created by Ben_Big on 8/8/16.
 */

'use strict';
(function(){
    angular.
    module("ShopApp").controller("profileController",profileController);
}());

function profileController(UserService,$scope,$routeParams){
    $scope.user=UserService.returnUser();
    $scope.userName=$scope.user.name;
    $scope.userEmail=$scope.user.email;
    if ($scope.user.address!==null) {
        $scope.userAddress = $scope.user.address;
    }
    else{
        $scope.userAddress = "Please update your address info";
    }

    //stuff for display controller
    this.numOfItemsOnEachPage=5;
    this.userID=$routeParams.userID;
    this.totalUrl="bidding/active/"+this.userID+"/count";
    this.itemUrl="/bidding/active/"+this.userID+"/page/";
    this.totalUrlSuccessful="bidding/successful/"+this.userID+"/count";
    this.itemUrlSuccessful="/bidding/successful/"+this.userID+"/page/";
    this.picUrl="http://placehold.it/800x400";
    this.additionalInfo="";
    $scope.showActiveBidding=true;

    $scope.activeBidding=function(){
        $scope.showActiveBidding=true;
        console.log("active");

    }

    $scope.successfulBidding=function(){
        $scope.showActiveBidding=false;
        console.log("successful");
    }


}