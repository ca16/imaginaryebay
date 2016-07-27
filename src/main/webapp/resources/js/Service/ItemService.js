'use strict';


(function (){
    angular.module("ShopApp").factory("ItemService", ItemService);

    function ItemService($http) {

        var itemCollectionUrl = "/item/";

        var service = {
            getItem: getItem,
            getAllItems: getAllItems
        };
        return service;

        function getItem(itemId){
            return $http.get(itemCollectionUrl + itemId);
        }
        
        function getAllItems(){
            return $http.get(itemCollectionUrl)
        }
    }
}());
