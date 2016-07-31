/**
 * Created by ben on 7/29/16.
 */

(function(){
    angular.module("ShopApp").controller("shopController",shopController);
})()

function shopController(){
    this.numOfItemsOnEachPage=6;
    this.totalUrl="item/totalCount";
    this.itemUrl="/item/page/";
    this.picUrl="http://placehold.it/800x400";
}