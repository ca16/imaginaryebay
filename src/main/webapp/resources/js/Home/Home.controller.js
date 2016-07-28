(function(){
    angular.module("ShopApp").controller("homeController",homeController)
        .controller("paginationController",paginationController);
}());


<<<<<<< HEAD
function homeController($scope,$http,UserService, ItemService){

    var itemCollectionUrl = "/item/";
    
    $http.get(itemCollectionUrl).success(function(data){
        $scope.goods = data
    });
    
    // $scope.goods=[{name:"Goodie 0",shortDescription:"Description 1"},
    //                 {name:"Goodie 2",shortDescription:"Description 2"},
    //                 {name:"Goodie 3",shortDescription:"Description 3"},
    //                 {name:"Goodie 4",shortDescription:"Description 4"},
    //                 {name:"Goodie 5",shortDescription:"Description 5"}];
=======
function homeController($scope,$http,UserService){

>>>>>>> origin/master


    (function(){
        $http.get("/item")
            .then(function(res){
                $scope.goods=res.data;
                //$scope.goods[1].itemPictures=[{"url": "http://placehold.it/800x500"}]
            })

    })()




}


function paginationController($scope){

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
        return 30;
    }

    $scope.numbersToShow=function(){
        //Click <<, each number minus 5, but not smaller than 1

        //Click >>, each number adds 5, but not larger than the total number of elements

    }

    $scope.goToPage=function(numOfPage){
        //Make a http request to get all the items

        //Update the currentPage
        $scope.currentPage=numOfPage;
    }
}