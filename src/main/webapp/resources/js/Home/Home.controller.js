/**
 * Created by ben on 7/29/16.
 */

(function(){
    angular.module("ShopApp").controller("homeController",homeController);
})();

function homeController($scope){
    this.numOfItemsOnEachPage=1;
    this.totalUrl="item/totalCount";
    this.itemUrl="/item/page/";
}