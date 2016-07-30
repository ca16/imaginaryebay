
(function(){
    angular
        .module("ShopApp")
        .config(Configuration)

    function Configuration($routeProvider){
        $routeProvider
            .when('/',{
                templateUrl:'./resources/home.html'
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
            .otherwise({
                redirectTo:'/'
            });
    }
}());