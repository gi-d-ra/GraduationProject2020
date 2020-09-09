### curl samples.
> For windows use `Git Bash`

<br/>
<br/>


##Endpoints for admin

#### get All Restaurants with dishes of today but without votes
`curl --user admin@gmail.com:admin http://localhost:8080/RestaurantVote/rest/admin/restaurants`

#### create Restaurant
`curl --user admin@gmail.com:admin -X POST http://localhost:8080/RestaurantVote/rest/admin/restaurants?restName=KFC`

#### update Restaurant 100002
`curl --user admin@gmail.com:admin -X PUT http://localhost:8080/RestaurantVote/rest/admin/restaurants/100002?restName=KFC2`

#### delete Restaurant 100002
`curl --user admin@gmail.com:admin -X DELETE http://localhost:8080/RestaurantVote/rest/admin/restaurants/100002`

#### get History(List of restaurants with count of votes in all time)
`curl --user admin@gmail.com:admin http://localhost:8080/RestaurantVote/rest/admin/restaurants/history`

#### get History in date (List of restaurants with count of votes in date)
`curl --user admin@gmail.com:admin http://localhost:8080/RestaurantVote/rest/admin/restaurants/history?date=2015-05-30`

#### get Restaurant 100005 with Dishes and Votes today
`curl --user admin@gmail.com:admin http://localhost:8080/RestaurantVote/rest/admin/restaurants/100005`

#### get Restaurant 100005 with Dishes and Votes in date
`curl --user admin@gmail.com:admin http://localhost:8080/RestaurantVote/rest/admin/restaurants/100005?date=2015-05-29`

#### create Dish for Restaurant 100002
`curl --user admin@gmail.com:admin  --data '{"id":null,"name":"Cake2","date":null,"price":200}' -H "Content-Type: application/json" -X POST http://localhost:8080/RestaurantVote/rest/admin/restaurants/100002/dishes`

#### get Dish 100007
`curl --user admin@gmail.com:admin http://localhost:8080/RestaurantVote/rest/admin/restaurants/dishes/100007`

#### update Dish 100007 for Restaurant 100005
`curl --user admin@gmail.com:admin  --data '{"id":100007,"name":"dishUPD","date":"2015-05-29","price":120,"restaurant":null}' -H "Content-Type: application/json" -X PUT http://localhost:8080/RestaurantVote/rest/admin/restaurants/100005/dishes/100007`

#### delete Dish 100007 for Restaurant 100005
`curl --user admin@gmail.com:admin -X DELETE http://localhost:8080/RestaurantVote/rest/admin/restaurants/100005/dishes/100007`


<br/>

##Endpoints for regular user

#### get All Restaurants with dishes of today but without votes
`curl --user user@yandex.ru:password  http://localhost:8080/RestaurantVote/rest/user/restaurants/`

#### get Restaurant 100005 with a menu of today
`curl --user user@yandex.ru:password  http://localhost:8080/RestaurantVote/rest/user/restaurants/100005`

#### vote for Restaurant 100005
`curl --user user@yandex.ru:password -X POST http://localhost:8080/RestaurantVote/rest/user/restaurants/voter?restId=100005`

#### get scores of restaurants of current voting
`curl --user user@yandex.ru:password  http://localhost:8080/RestaurantVote/rest/user/restaurants/score`

#### update vote (vote for Restaurant 100002)
`curl --user user@yandex.ru:password -X PUT http://localhost:8080/RestaurantVote/rest/user/restaurants/voter?"restId=100002&voteId=100009"`

#### get history of votes
`curl --user user@yandex.ru:password  http://localhost:8080/RestaurantVote/rest/user/restaurants/history`






