(function(){
    angular.
    module("ShopApp").controller("itemcreateController",itemcreateController);

}());


function itemcreateController($scope,$http, UserService, $location){
    
    $scope.create=function(){

        var userr=UserService.returnUser();

        if(userr == null){
            window.alert("You must be logged in to create an item.");
            $location.path("app/login");
        }
            
        else{
            var newitem ={
                name:$scope.name,
                description:$scope.description,
                category:$scope.category,
                endtime:$scope.endtime,
                price:$scope.price,
                // backend handles assigning the right user to it
            }

            $http.post("/item", newitem)
                .then(
                    function(res){
                        window.alert("Item created successfully!");
                    }, function(res){
                        window.alert("Item creation failed: " + res.data.detailedMessage);
                    });

            console.log(newitem.getId());
        }



    }

    // $scope.update=function(){
    //     var improveditem ={
    //         description:$scope.description,
    //         category:$scope.category,
    //         endtime:$scope.endtime,
    //         price:$scope.price        }
    //     var id = $scope.id;
    //
    //
    //     $http.put("/item/" + id, improveditem)
    //         .then(
    //             function(res){
    //                 window.alert("Item updated successfully!");
    //             }, function(res){
    //                 if (id == null){
    //                     window.alert("Please provide an item ID.");
    //                 }
    //                 else {
    //                     window.alert("Item update failed: " + res.data.detailedMessage);
    //                 }
    //             });
    // }
}