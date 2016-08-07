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

        $scope.newbidprice = 0;
        $scope.validBid = false;
        $scope.invalidBid = false;
        $scope.bidMessage = "";
        $scope.currentUser = UserService.returnUser();
        $scope.loggedInFlag = $scope.currentUser != null;

        // $scope.loggedInFlag = true;

        // Enable disqus comments
        $scope.disqusConfig = {
            disqus_shortname: 'guarded-journey-11719-herokuapp-com',
            disqus_identifier: 'auction-item' + itemId,
            disqus_url: 'localhost:8080/app/item/' + itemId
        };

        // Configure slideshow for item's pictures
        $scope.noWrapSlides = false;
        $scope.active = 0;

        // Get Item data for itemId
        $http.get(itemCollectionUrl + itemId).success(function(data){
            $scope.item = data;
            $scope.slides = data.itemPictures;
            // Used to determine color for auction countdown. 
            // If less than a day, display in red
            console.log(data.endtime - (Math.floor(Date.now())));
            $scope.auctionEndSoon = (data.endtime - (Math.floor(Date.now()))) <= 86400000;
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
                            bidResult('Bid successful!', data);
                        })
                        .error(function(data, status){
                            bidResult('Error reloading item.', data, status);
                        });
                })
                .error(function(data, status) {
                    bidResult('Invalid bid, please try again.', data, status);
                })
        };

        // Helper function for dealing with bid alerts
        var bidResult = function(message, data, status){
            if (status){
                console.error(message, status, data);
                $scope.invalidBid = true;
                $scope.validBid = false;
            } else {
                $scope.invalidBid = false;
                $scope.validBid = true;
            }
            $scope.bidMessage = message;
        };
    };

    init();
}

