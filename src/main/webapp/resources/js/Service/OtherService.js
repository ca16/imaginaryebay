/**
 * Created by Brian on 7/26/2016.
 */
(function (){
    angular.module("ShopApp")
        .factory("OtherService",OtherService);

    function OtherService() {

        var currentUser;

        var service = {
            returnUser: returnUser,
            setUser: setUser

        };
        return service;

        function returnUser() {
            return currentUser;
        }

        function setUser(newUser) {
            currentUser=newUser;
        }
    }
}());
