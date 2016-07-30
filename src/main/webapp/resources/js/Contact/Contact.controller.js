(function(){
    angular.
    module("ShopApp").controller("contactController",contactController);

}());


function contactController($scope,$http){
	
    $scope.sendEmail=function(){
    	if ($scope.name=="Tina"){
            window.alert("You are our favorite user!");
        }
    	else{
    		var newMessage ={
    				name:$scope.name,
    				emailAddress:$scope.emailAddress,
    				emailContent:$scope.emailContent
    		}

    		$http.post("/ImaginaryEbay/contact", newMessage)
            	.then(
            			function(res){
            				window.alert("Message sent! Thanks for contacting us!");
            			});
    	}
    }
}