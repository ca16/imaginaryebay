'use strict';
(function(){
    angular.module("ShopApp").controller("homeController",homeController);
}());
function homeController($scope,$http,UserService){

    var numOfItemsOnEachPage=8;

    (function(){
        $http.get("/item/page/1/size/"+numOfItemsOnEachPage)
            .then(function(res){
                $scope.goods=res.data;
                //$scope.goods[1].itemPictures=[{"url": "http://placehold.it/800x500"}]
                for (var i=0; i< $scope.goods.length; i++){
                    if ($scope.goods[i].itemPictures.length==0){
                        $scope.goods[i].itemPictures.push({"url": "http://placehold.it/800x500"});
                    }
                }

            })
    })();





    ///////////////////////////////////////////
    //pagination stuff


    //number of elements to show on each page,
    //ToDo: this should come as a parameter
    var numOfItemsOnEachPage=8;

    $scope.numberForPagination=null;
    $scope.currentPage=1;


    (function initialization(){

       $scope.numberForPagination=initialNumsToShow();
    })();



    //Get the total number of Items
    function totalNumber(){

        return 231;
    }



    //Decide what numbers to show in the pagination div initially
    //The first number is always 0
    function initialNumsToShow(){

        //calls totalNumber() to get the total number of Items
        var total=totalNumber();

        var numberForPagination=[];


        //number of pages
        var numPages=Math.floor(total/numOfItemsOnEachPage);
        if( total%numOfItemsOnEachPage!=0){
            numPages++;
        }

        if (numPages<=5){
            $scope.hide=true;//hide >> and <<
        }
        else{
            $scope.hide=false;
        }



        if (total<=numOfItemsOnEachPage){
            numberForPagination=[1];
        }
        else if (total<=numOfItemsOnEachPage*5){
            for (var i=1;i<=numPages;i++){
                numberForPagination.push(i);
            }
        }
        else{
            var numberForPagination=[1,2,3,4,5];
        }
        return numberForPagination;
    }


    //Decide what numbers to show in the pagination, the last number is the last page number
    function lastNumsToShow(){

        //calls totalNumber() to get the total number of Items
        var total=totalNumber();


        var numberForPagination=[];

        //number of pages
        var numPages=Math.floor(total/numOfItemsOnEachPage);
        if( total%numOfItemsOnEachPage!=0){
            numPages++;
        }


        if (total<=numOfItemsOnEachPage){
            numberForPagination=[1];
        }
        else if (total<=numOfItemsOnEachPage*5){
            for (var i=1;i<=numPages;i++){
                numberForPagination.push(i);
            }
        }
        else{
            for (var i=4;i>=0;i--){
                numberForPagination.push(numPages-i);
            }
        }
        return numberForPagination;
    }



    //Decide what numbers to show when you click on <<
    $scope.numbersToShowLeft=function() {
        //Click <<, each number minus 5, but not smaller than 1
        if ($scope.numberForPagination[0] > 5) {
            for(var i=0;i<$scope.numberForPagination.length;i++){
                $scope.numberForPagination[i]= $scope.numberForPagination[i]-5;
            }
        }
        else {
            $scope.numberForPagination = initialNumsToShow();
        }
    };


    //Decide what numbers to show when you click on >>
    $scope.numbersToShowRight=function(){
            //Click >>, each number adds 5, but not larger than the total number of elements
            var total=totalNumber();
            //number of pages
            var numPages=Math.floor(total/numOfItemsOnEachPage);
            if( total%numOfItemsOnEachPage!=0){
                numPages++;
            }
            var diff=numPages-$scope.numberForPagination[4];
            if (diff>5){
                for(var i=0;i<$scope.numberForPagination.length;i++){
                    $scope.numberForPagination[i]= $scope.numberForPagination[i]+5;
                }
            }
            else{
                $scope.numberForPagination=lastNumsToShow();
            }

    };

    //This function is for ng-click, when you click on number 3, this function makes the http call
    $scope.goToPage=function(numOfPage){
        //Make a http request to get all the items
        $http.get("/item/page/"+numOfPage+"/size/"+numOfItemsOnEachPage)
            .then(function(res) {
                $scope.goods = res.data;
                //$scope.goods[1].itemPictures=[{"url": "http://placehold.it/800x500"}]
                for (var i = 0; i < $scope.goods.length; i++) {
                    if ($scope.goods[i].itemPictures.length==0) {
                        $scope.goods[i].itemPictures.push({"url": "http://placehold.it/800x500"});
                    }
                }
            });

        //Update the $scope.currentPage, so the number can be highlighted
        $scope.currentPage=numOfPage;
    }
}