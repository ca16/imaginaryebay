/**
 * Created by Ben_Big on 4/1/16.
 */
'use strict';
(function(){
    angular.
    module("ShopApp").controller("registrationController",registrationController);

}());

function registrationController($scope,UserService,$location,$http){
    $scope.register=function(){
        //console.log($scope.name);
        if ($scope.password!=$scope.password2){
            window.alert("Two passwords do not match");
        }
        else{
            var credential={
              name:$scope.name,
              password:$scope.password
            };
            //console.log(credential);
            $http.post("/registration",credential)
                .then(
                    function(res){
                        var user=res.data;
                        if (user.id<0){
                            window.alert("this user has already been registered");
                        }
                        else{
                            UserService.setUser(user);
                            $location.path("/home");
                        }
                    }
                )
        }
    }
}

