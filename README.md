Imaginary Ebay 
===


To login:
----------
1. not store the cookies:

	curl -X POST -d username=butters@gmail.com -d password=Stotch http://localhost:8080/login 
	
2. store the cookies:
	
	curl -X POST -d username=butters@gmail.com -d password=Stotch -c ./cookies.txt http://localhost:8080/login 




User:
-----
#####REST Api:
1. create a new user: (no login is needed)

	curl -X POST -H "Content-Type:application/json" -d '{"password":"Testaburger", "name":"Wendy", "email":"wendy@gmail.com"}' -i  http://localhost:8080/user/new


2. update user with ID: (need to login first, and must be the user himself or an admin)

	curl -X PUT -H "Content-Type:application/json" -d '{"password":"Tucker", "name":"Craig", "email":"craig@gmail.com","address":"Bush"}' -i -b cookies.txt http://localhost:8080/user/2
	
	
3. return all user: (login is needed, and must be admin)

	curl -X GET -b cookies.txt http://localhost:8080/user
	
4. return user info based on name (login is needed, must be the user himself, or admin)

	curl -X GET -b cookies.txt http://localhost:8080/user/name/Wendy	


5. return user info based on email address (login is needed, must be the user himself, or admin)
	
	curl -X GET -b cookies.txt http://localhost:8080/user/email/eric@gmail.com/
	
	**The last / cannot be omitted

6. return all the items by a user with a specified ID

	curl -X GET http://localhost:8080/user/item/3
	

	
Bidding:
-----
#####REST Api:
1. create a new bidding:

	 curl -X POST http://localhost:8080/bidding/itemID/1/price/900
	 
	 (logged in user bids on item with id=2 with price:900)
	 (bid must be created before item's endtime, and price must be greater than highest bid. If
	 and item does not have a highest bid, the price must be greater than the item's original price.
	 User cannot bid on their own item. requires authentication.)

2. get bidding with bidding's ID:
	
	curl -X GET http://localhost:8080/bidding/3

3. get all the biddings by one user
	
	curl -X GET http://localhost:8080/bidding/userID/2

	(needs authentication)

4. get all the biddings on one item

	curl -X GET http://localhost:8080/bidding/itemID/1

	(needs authentication)

5. get the highest bidding on one item

	curl -X GET http://localhost:8080/bidding/highest/itemID/1

6. get active auction items based on bidder

    curl -X GET http://localhost:8080/bidding/active/1


    The following require Authentication:

7. get items from successful auctions based on bidder

    curl -X GET http://localhost:8080/bidding/successful/1

8. the number of active auction items based on bidder

    curl -X GET http://localhost:8080/bidding/active/1/count

9. the number of items from successful auctions based on bidder

    curl -X GET http://localhost:8080/bidding/successful/1/count

10. get active auction items based on bidder with pagination (page number and page size)\

    curl -X GET http://localhost:8080/bidding/active/1/page/1/size/10

    (would return the first 10 such items)

11. get items from successful auction based on bidder with pagination

    curl -X GET http://localhost:8080/bidding/successful/1/page/2/size/5

    (would return items 6 to 10)