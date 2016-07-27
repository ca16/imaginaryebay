(function(){
    angular.
    module("ShopApp").controller("adminController",adminController);

}());


function adminController($scope,$http, UserService, $location){

    UserService.returnAllUsers().success(function (data) {
        $scope.users = data;
    });
    console.log($scope.users);
    for (var usr in $scope.users){
        console.log(usr);
        console.log(usr.name);
    }
    
    $scope.profile = function (id) {
        console.log("Hi");
        console.log("app/user/" + id);
        $location.path("app/user/" + id);
    }

    $scope.lock = function (id) {
        console.log("Bye");
        console.log("app/user/" + id);
        $location.path("app/user/" + id);
    }

}