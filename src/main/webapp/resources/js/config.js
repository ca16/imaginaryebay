'use strict';
(function(){
    angular
        .module("ShopApp")
        .config(Configuration)

    function Configuration($routeProvider,$locationProvider){
        $routeProvider
            .when('/',{
                templateUrl:'./resources/home.html'
            })
            .when('/app/shop/:sellerID',{
                templateUrl:'./resources/shop.html'
            })
            .when('/app/login',{
                templateUrl:'./resources/login.html'
            })
            .when('/app/registration',{
                templateUrl:'./resources/registration.html'
            })
            .when('/app/item/create',{
                templateUrl:'./resources/itemcreate.html'
            })
            .when('/app/item/:itemId',{
                templateUrl:'./resources/item.html'
            })
            .when('/app/item/:itemId/update',{
                templateUrl:'./resources/itemupdate.html'
            })
            .when('/app/admin',{
                templateUrl:'./resources/admin.html'
            })
            .when('/app/search',{
                templateUrl:'./resources/searchResult.html'
            })
            .otherwise({
                redirectTo:'/'
            });

            $locationProvider.html5Mode(true);
    }
}());