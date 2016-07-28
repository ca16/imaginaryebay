(function(){
    angular.
    module("ShopApp").controller("adminController",adminController);

}());


function adminController($scope,$http, UserService, $location) {

    var userr = UserService.returnUser();

    if (userr == null) {
        window.alert("You must be logged in to view this page.");
        $location.path("app/login");
    }
    else if (!userr.admin) {
        window.alert("You are not authorized to view this page.");
        $location.path("#/app/home");
    }

    else {
        $http.get("/user")
            .then(function (data) {
            $scope.users = data;
        });
        console.log($scope.users);
        for (var usr in $scope.users) {
            console.log(usr);
            console.log(usr.name);
        }
    }
    
    $scope.profile = function (id) {
        console.log("Hi");
        console.log("app/user/" + id);
        $location.path("app/user/" + id);
    }

    $scope.lock = function (id) {
        $http.get("/user/" + id)
            .then(
                function(res){
                    res.setPassword("lockedouthahaha");
                    console.log(res);

                }, function(res){
                    window.alert("User lockout failed.");
                });
       // UserService.returnSpecificUser(id).setPassword("lockedouthahahaha");
        //console.log(UserService.returnSpecificUser(id));
        // console.log("app/user/" + id);
        // $location.path("app/user/" + id);
    }

}