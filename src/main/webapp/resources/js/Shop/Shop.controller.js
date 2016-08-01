/**
 * Created by ben on 7/29/16.
 */

(function(){
    angular.module("ShopApp").controller("shopController",shopController);
})()

function shopController($scope, $http,$routeParams){
    this.numOfItemsOnEachPage=6;
    this.sellerID="sellerID="+$routeParams.sellerID;
    this.totalUrl="item/count";
    this.itemUrl="/item/page/";
    this.picUrl="http://placehold.it/800x400";




    $http.get("/item/sellercategories/"+$routeParams.sellerID).then(
        function(res){
            $scope.categories=res.data;
            console.log($scope.categories);
        }
    )




}