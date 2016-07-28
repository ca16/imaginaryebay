/**
 * Created by Ben_Big on 3/24/16.
 */
(function (){
    angular.module("ShopApp")
        .factory("UserService",UserService);

    function UserService($http) {

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
