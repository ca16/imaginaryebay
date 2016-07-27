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
            setUser: setUser,
            returnAllUsers: returnAllUsers

        };
        return service;

        function returnUser() {
            return currentUser;
        }

        function setUser(newUser) {
            currentUser=newUser;
        }
        
        function returnAllUsers() {
            return $http.get("/user");
        }
        
    }


}());
