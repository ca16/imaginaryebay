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
    this.numOfItemsOnEachPage=8;
    this.userID=$routeParams.userID;
    this.totalUrl="bidding/active/"+this.userID+"/count";
    this.itemUrl="/bidding/active/"+this.userID+"/page/";
    this.picUrl="http://placehold.it/800x400";
    this.additionalInfo="";


    $scope.activeBidding=function(){
        this.totalUrl="bidding/active/"+this.userID+"/count";
        this.itemUrl="/bidding/active/"+this.userID+"/page/";
    }

    $scope.successfullBidding=function(){
        this.totalUrl="bidding/successful/"+this.userID+"/count";
        this.itemUrl="/bidding/successful/"+this.userID+"/page/";
    }


}