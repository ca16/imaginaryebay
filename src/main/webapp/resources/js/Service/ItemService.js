/**
 * Created by Brian on 7/26/2016.
 */
(function (){
    angular.module("ShopApp")
        .factory("ItemService",ItemService);

    function OtherService() {

        var service = {
            getItem: getItem,
            updateItem: updateItem
        };
        return service
        
        
    }
}());
