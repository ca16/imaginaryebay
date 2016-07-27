(function(){
    angular.
    module("ShopApp").controller("itemchangesController",itemchangesController);

}());


function itemchangesController($scope,$http){

    $scope.create=function(){
        var newitem ={
            description:$scope.description,
            category:$scope.category,
            endtime:$scope.endtime,
            price:$scope.price
        }


        $http.post("/item", newitem)
            .then(
                function(res){
                    window.alert("Item created successfully!");
                }, function(res){
                    window.alert("Item creation failed: " + res.data.detailedMessage);
                });
    }

    $scope.update=function(){
        var improveditem ={
            description:$scope.description,
            category:$scope.category,
            endtime:$scope.endtime,
            price:$scope.price        
        };
        var id = $scope.id;


        $http.put("/item/" + id, improveditem)
            .then(
                function(res){
                    window.alert("Item updated successfully!");
                }, function(res){
                    if (id == null){
                        window.alert("Please provide an item ID.");
                    }
                    else {
                        window.alert("Item update failed: " + res.data.detailedMessage);
                    }
                });
    }
}