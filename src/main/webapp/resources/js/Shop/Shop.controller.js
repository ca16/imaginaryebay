/**
 * Created by ben on 7/29/16.
 */

(function(){
    angular.module("ShopApp").controller("shopController",shopController);
})()

function shopController($scope, $http,$routeParams,$location, UserService){
    this.numOfItemsOnEachPage=6;
    this.sellerID="sellerID="+$routeParams.sellerID;
    $scope.sellerID=$routeParams.sellerID;
    this.totalUrl="item/count";
    this.itemUrl="/item/page/";
    this.picUrl="http://placehold.it/800x400";


    this.category=$location.search().Category;

    if(this.category===undefined) {
        this.additionalInfo=this.sellerID;
    }
    else{
        $scope.currentCategory=this.category;
        this.additionalInfo="cat="+$scope.currentCategory+"&"+this.sellerID;
    }



    $http.get("/user/getName/" + $routeParams.sellerID).then(
        function (res) {
            $scope.shopName = res.data[0];
        }
    )

    $http.get("/item/sellercategories/" + $routeParams.sellerID).then(
        function (res) {
            $scope.categories = res.data;
        }
    )



}