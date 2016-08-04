(function(){
    angular.
    module("ShopApp").controller("contactController",contactController);

}());


function contactController($scope,$http,$location){
	
    $scope.sendEmail=function(){
    		var newMessage ={
    				name:$scope.name,
    				emailAddress:$scope.emailAddress,
    				emailContent:$scope.emailContent
    		}

    		$http.post("/contact", newMessage)
            	.then(
            			function(res){
            				window.alert("Message sent! Thanks for contacting us!");
            				$location.path("/");
            			});
    	}
    }