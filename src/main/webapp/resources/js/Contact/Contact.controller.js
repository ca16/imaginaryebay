(function(){
    angular.
    module("ShopApp").controller("contactController",contactController);

}());


function contactController($scope,$http){

    $scope.sendEmail=function(){
        var message ={
            name:$scope.name,
            email:$scope.email,
            message:$scope.message,
        }


        $http.post("/contact", message)
            .then(
                function(res){
                    window.alert("Message sent! Thanks for contacting us!");
                });
    }
}