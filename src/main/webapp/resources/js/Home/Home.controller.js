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

    })()





    ///////////////////////////////////////////
    //pagination stuff


    //number of elements to show on each page,
    //ToDo: this should come as a parameter
    var numOfItemsOnEachPage=8;

    $scope.numberForPagination=null;
    $scope.currentPage=1;


    (function initialization(){

        //calls totalNumber() to get the total number of Items
        var total=totalNumber();

        //Initialize $scope.numberForPagination
        if (total<=numOfItemsOnEachPage){
            $scope.numberForPagination=[1];
        }
        else if (total<=numOfItemsOnEachPage*5){
            var numPages=total/numOfItemsOnEachPage;
            if( total%numOfItemsOnEachPage!=0){
                numPages++;
            }
            $scope.numberForPagination=[];
            for (var i=1;i<=numPages;i++){
                $scope.numberForPagination.push(i);
            }
        }
        else{
            $scope.numberForPagination=[1,2,3,4,5];
        }
    })();



    function totalNumber(){
        //Get the total number of Items
        return 11;
    }

    $scope.numbersToShow=function(){
        //Click <<, each number minus 5, but not smaller than 1

        //Click >>, each number adds 5, but not larger than the total number of elements

    }

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
            })

        //Update the currentPage
        $scope.currentPage=numOfPage;
    }
}