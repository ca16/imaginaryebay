
/**
 * Created by ben on 7/29/16.
 */

'use strict';

(function(){
    angular.module("ShopApp").controller("homeController",homeController);
})();

function homeController($scope){
    this.numOfItemsOnEachPage=8;
    this.totalUrl="item/totalCount";
    this.itemUrl="/item/page/";
    this.picUrl="http://placehold.it/800x400";

}