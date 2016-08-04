'use strict';
(function(){
    angular
        .module("ShopApp")
        .config(Configuration);

<<<<<<< HEAD
    function Configuration($routeProvider,$locationProvider){
=======
    function Configuration($routeProvider, $locationProvider){
>>>>>>> 784fef9043762967ec70f51cdfaa1624efab18ab
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
            .when('/app/contact',{
        		templateUrl:'./resources/contact.html'
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
            .when('/app/user/:userId/update',{
                templateUrl:'./resources/userupdate.html'
            })
            .when('/app/profile',{
                templateUrl:'./resources/profile.html'
            })
            .otherwise({
                redirectTo:'/'
            });

<<<<<<< HEAD
            $locationProvider.html5Mode(true);
=======
        $locationProvider.html5Mode(true);
>>>>>>> 784fef9043762967ec70f51cdfaa1624efab18ab
    }
}());