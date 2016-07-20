(function(){
    angular.module("ShopApp").controller("homeController",homeController);
}());


function homeController($scope,$http,UserService){

    $scope.goods=[{name:"Goodie 0",shortDescription:"Description 1"},
                    {name:"Goodie 2",shortDescription:"Description 2"},
                    {name:"Goodie 3",shortDescription:"Description 3"},
                    {name:"Goodie 4",shortDescription:"Description 4"},
                    {name:"Goodie 5",shortDescription:"Description 5"}];



    //console.log(UserService.returnUser());


    /*

    (function(){
        $http.get("/home")
            .then(function(res){
                //UserService.setUser("Alice");
                $scope.goods=res.data;
                //console.log(res.data);
                console.log(UserService.returnUser());
            });

    }());*/


}