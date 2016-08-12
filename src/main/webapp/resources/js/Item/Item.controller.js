'use strict';
(function(){
        angular.module("ShopApp").controller("itemController", itemController);
}());

function itemController($scope, $routeParams, $http, UserService){

    var init = function(){
        // Grab Item ID from URL
        var itemId = $routeParams.itemId;
        var itemCollectionUrl = "/item/";
        var biddingCollectionUrl = "/bidding";
        var oneDayInMilliseconds = 86400000;

        $scope.newbidprice = 0;
        $scope.currentUser = UserService.returnUser();
        $scope.loggedInFlag = $scope.currentUser != null;


        // Enable disqus comments
        $scope.disqusConfig = {
            disqus_shortname: 'guarded-journey-11719-herokuapp-com',
            disqus_identifier: 'auction-item' + itemId,
            disqus_url: 'http://localhost:8081/app/item/' + itemId
        };

        // Configure slideshow for item's pictures
        $scope.noWrapSlides = false;
        $scope.active = 0;

        // Get Item data for itemId
        $http.get(itemCollectionUrl + itemId).success(function(data){
            $scope.item = data;
            $scope.slides = data.itemPictures;
            // Determines if user can edit this item
            $scope.userCanEdit = $scope.loggedInFlag && $scope.currentUser.id === data.userr.id;
            // Used to determine color for auction countdown. 
            // If less than a day, display in red
            var remainingTime = (data.endtime - (Math.floor(Date.now())));
            $scope.auctionEndSoon = remainingTime <= oneDayInMilliseconds;
            $scope.auctionOver = remainingTime <= 0;
        });

        // POSTs a Bidding with the specified price
        $scope.makeBid = function(){
            var bidPrice = $scope.newbidprice;
            $http.post(biddingCollectionUrl + '/itemID/' + itemId + '/price/' + bidPrice)
                .success(function(){
                    // Reload the item to display new bid
                    $http.get(itemCollectionUrl + itemId)
                        .success(function(data){
                            $scope.item = data;
                            bidResult(data)
                        })
                        .error(function(data, status){
                            bidResult(data, status)
                        });
                })
                .error(function(data, status) {
                    bidResult(data, status)
                })
        };
        
        // Handle the response from a posting a Bid
        // If a status code is found, show invalid bid warning
        // Else, show bid success message
        var bidResult = function(data, status){
            if (status){
                console.error(data, status);
                $scope.validBid = false;
                $scope.invalidBid = true;                
            }else {
                $scope.validBid = true;
                $scope.invalidBid = false;                
            }
        };
    };

    init();
}

