/**
 * Created by Ben_Big on 3/27/16.
 */
(function(){
    angular.
        module("ShopApp").controller("loginController",loginController);

}());


function loginController($scope,$http,$route,UserService,$location){

    $scope.onClick=function(){
        var credential={
            name:$scope.name,
            password:$scope.password
        }


        $http.post("/login",credential)
            .then(
                function(res){
                    var user=res.data;
                    if (user.id<0){
                           window.alert("user name or password is incorrect");
                    }
                    else{
                        UserService.setUser(user);
                        console.log(UserService.returnUser());
                        $location.path("#/app/home");
                    }
                })
    }
}