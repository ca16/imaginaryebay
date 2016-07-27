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

	curl -X POST -H "Content-Type: application/json" -d "{\"password\":\"Testaburger\", \"name\":\"Wendy\", \"email\":\"wendy@gmail.com\",\"address\":\"Boston\"}" -i  http://localhost:8080/ImaginaryEbay/user/new


2. update user with ID: (need to login first, and must be the user himself or an admin)

	curl -X PUT -H "Content-Type: application/json" -d "{\"password\":\"Tucker\", \"name\":\"Craig\", \"email\":\"craig@gmail.com\",\"address\":\"Bush\"}" -i -b cookies.txt http://localhost:8080/ImaginaryEbay/user/2
	
	
3. return all user: (login is needed, and must be admin)

	curl -X GET -b cookies.txt http://localhost:8080/user
	
4. return user info based on name (login is needed, must be the user himself, or admin)

	curl -X GET -b cookies.txt http://localhost:8080/user/name/Wendy	


5. return user info based on email address (login is needed, must be the user himself, or admin)
	
	curl -X GET -b cookies.txt http://localhost:8080/user/email/eric@gmail.com/
	
	**The last / cannot be omitted

6. return all the items by a user with a specified ID

	curl -X GET http://localhost:8080/user/item/3
	

	

	

#####Controller:
Controller checks if the id in url is the same as the id got from cookies
	
#####Repoistory:

	
	

