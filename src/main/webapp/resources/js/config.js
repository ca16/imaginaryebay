
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
            .otherwise({
                redirectTo:'/'
            });

    }
}());