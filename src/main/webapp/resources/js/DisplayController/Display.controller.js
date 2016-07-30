(function(){
    angular.module("ShopApp").controller("displayController",displayController);
}());

function displayController($scope,$http,$q){


    //This function describes what happens when the page is first loaded
    $scope.init=function(num,totalUrl,itemUrl){
        $scope.numOfItemsOnEachPage=num;
        $scope.totalUrl=totalUrl;
        $scope.itemUrl=itemUrl;
        $scope.numberForPagination=null;
        $scope.currentPage=1;
        $scope.total=0;


        //For the thumbnail stuff
        (function(){
            $http.get($scope.itemUrl+1+"/size/"+$scope.numOfItemsOnEachPage)
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

        //For the pagination number
        (function initialization(){

            totalNumber().then(function(){initialNumsToShow()});
        })();

    };




    //Decide what numbers to show when you click on <<
    $scope.numbersToShowLeft=function() {
            //Click <<, each number minus 5, but not smaller than 1
            if ($scope.numberForPagination[0] > 5) {
                for (var i = 0; i < $scope.numberForPagination.length; i++) {
                    $scope.numberForPagination[i] = $scope.numberForPagination[i] - 5;
                }
            }
            else {
                totalNumber().then(function () {
                    initialNumsToShow()
                });

            }
    };


    //Decide what numbers to show when you click on >>
    $scope.numbersToShowRight=function(){
        //Click >>, each number adds 5, but not larger than the total number of elements
        totalNumber().then(function() {
            //number of pages
            var numPages = Math.floor($scope.total / $scope.numOfItemsOnEachPage);
            if ($scope.total % $scope.numOfItemsOnEachPage != 0) {
                numPages++;
            }
            var diff = numPages - $scope.numberForPagination[4];
            if (diff > 5) {
                for (var i = 0; i < $scope.numberForPagination.length; i++) {
                    $scope.numberForPagination[i] = $scope.numberForPagination[i] + 5;
                }
            }
            else {
                lastNumsToShow();
            }
        })

    };


    //This function is for ng-click, when you click on number 3, this function makes the http call
    $scope.goToPage=function(numOfPage){

            //Make a http request to get all the items
            $http.get($scope.itemUrl + numOfPage + "/size/" + $scope.numOfItemsOnEachPage)
                .then(function (res) {
                    $scope.goods = res.data;
                    for (var i = 0; i < $scope.goods.length; i++) {
                        if ($scope.goods[i].itemPictures.length == 0) {
                            $scope.goods[i].itemPictures.push({"url": "http://placehold.it/800x500"});
                        }
                    }
                });

            //Update the $scope.currentPage, so the number can be highlighted
            $scope.currentPage = numOfPage;

    };





    /////////////////////////////helper functions///////////////////



    //Get the total number of Items
    function totalNumber(){
        var deferred= $q.defer();
        return $http.get($scope.totalUrl).then(
            function(res){
                $scope.total= res.data;
                deferred.resolve();
                return deferred.promise;
            },function(rej){
                $scope.total = 0;
                deferred.reject();
                return deferred.promise;
            }
        )
    }



    //Decide what numbers to show in the pagination div initially
    //The first number is always 1
    function initialNumsToShow(){



        var numberForPagination=[];



        //number of pages
        var numPages=Math.floor($scope.total/$scope.numOfItemsOnEachPage);
        if( $scope.total%$scope.numOfItemsOnEachPage!=0){
            numPages++;
        }

        if (numPages<=5){
            $scope.hide=true;//hide >> and <<
        }
        else{
            $scope.hide=false;
        }


        if ($scope.total<=$scope.numOfItemsOnEachPage){
            numberForPagination=[1];
        }
        else if ($scope.total<=$scope.numOfItemsOnEachPage*5){
            for (var i=1;i<=numPages;i++){
                numberForPagination.push(i);
            }
        }
        else{
            var numberForPagination=[1,2,3,4,5];
        }
        $scope.numberForPagination= numberForPagination;
    }


    //Decide what numbers to show in the pagination, the last number is the last page number
    function lastNumsToShow(){



        var numberForPagination=[];

        //number of pages
        var numPages=Math.floor($scope.total/$scope.numOfItemsOnEachPage);
        if( $scope.total%$scope.numOfItemsOnEachPage!=0){
            numPages++;
        }


        if ($scope.total<=$scope.numOfItemsOnEachPage){
            numberForPagination=[1];
        }
        else if ($scope.total<=$scope.numOfItemsOnEachPage*5){
            for (var i=1;i<=numPages;i++){
                numberForPagination.push(i);
            }
        }
        else{
            for (var i=4;i>=0;i--){
                numberForPagination.push(numPages-i);
            }
        }
        $scope.numberForPagination=numberForPagination;
    }


}