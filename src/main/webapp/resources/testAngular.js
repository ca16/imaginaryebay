/**
 * Created by Ben_Big on 7/12/16.
 */
angular.module('app',['ngRoute']).config(
        function ($routeProvider){
            $routeProvider.when('/',{
                 templateUrl:'./resources/Test.html'
    });
});