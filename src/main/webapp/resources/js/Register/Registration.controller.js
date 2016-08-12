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
              email:$scope.email,
              password:$scope.password,
            };
            //console.log(credential);
            
            $http.post("/user/new",credential)
                .then(
                   function() {
                       return $http({
                           method: 'POST',
                           url: "/login",
                           headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                           transformRequest: function (obj) {
                               var str = [];
                               for (var p in obj)
                                   str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                               return str.join("&");
                           },
                           data: {username: $scope.email, password: $scope.password}
                       })
                   }
                )
                .then(
                    function(res) {
                        return $http.get("/user/email/"+$scope.email+"/");
                    },
                    function(rej){
                        window.alert("Username or email address has already existed");
                    }
                )
                .then(
                    function(res){
                        var user=res.data;
                        UserService.setUser(user);
                        window.alert("You are successfully registered, a confirmation email has been sent to you")
                        $location.path("/app/home");

                    }
                )
        }
    }
}

