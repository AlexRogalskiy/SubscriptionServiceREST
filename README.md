# SubscriptionServiceREST
REST API Newsletter Subscription Service

----------------------------GENERAL-----------------------------

URL: http://host:8080/newsletterSub/api/
METHODS: GET / POST / PUT / DELETE
Content-Type: application/json, application/xml

---------------------------BASIC AUTH---------------------------

Basic authentication (ROLES: USER, ADMIN, DBA):

URL: /api/*
ROLE: USER
username: user
password: user123

curl --user user:user123
 
------------------------------JSON------------------------------

1. Get all subscriptions:

curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/subscriptions

2. Get subscription by id = 1:

curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/subscription/1

3. Get subscriptions by user id = 1:

curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/user/1/subscription

4. Create subscription:

curl -d '{"name":"subscription11", "expireAt": "2018-11-11", "type": "PREMIUM"}' -H "Accept: Application/json" -H "Content-Type: application/json" -X POST http://localhost:8080/newsletterSub/api/subscription

5. Update subscription width id = 4:

curl -d '{"name":"subscription4", "expireAt": "2018-11-11", "type": "PREMIUM"}' -H "Accept: application/json" -H "Content-Type: application/json" -X PUT http://localhost:8080/newsletterSub/api/subscription/4

6. Delete subscription with id = 4:

ROLE: ADMIN and DBA
curl -H "Accept: application/json" -H "Content-Type: application/json" -X DELETE http://localhost:8080/newsletterSub/api/subscription/4

7. Delete all subscriptions:

ROLE: ADMIN and DBA
curl -H "Accept: application/json" -H "Content-Type: application/json" -X DELETE http://localhost:8080/newsletterSub/api/subscriptions

8. Get user by id = 1:

curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/user/1

9. Create user:

curl -d '{"login":"user4@gmail.com", "age": "25", "rating": "1.00", "status": "UNVERIFIED"}' -H "Accept: Application/json" -H "Content-Type: application/json" -X POST http://localhost:8080/newsletterSub/api/user

10. Update user with id = 4:

curl -d '{"login":"user4@gmail.com", "age": "25", "rating": "10.00", "status": "UNVERIFIED"}' -H "Accept: application/json" -H "Content-Type: application/json" -X PUT http://localhost:8080/newsletterSub/api/user/4

11. Delete user with id = 4:

ROLE: ADMIN and DBA
curl -H "Accept: application/json" -H "Content-Type: application/json" -X DELETE http://localhost:8080/newsletterSub/api/user/4

12. Delete all users:

ROLE: ADMIN and DBA
curl -H "Accept: application/json" -H "Content-Type: application/json" -X DELETE http://localhost:8080/newsletterSub/api/users

13. Get all users:

curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/users

14. Get all users subscribed before date:

curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/users?date=2017-08-08&order=1

15. Get all users subscribed after date:

curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/users?date=2017-08-08{&order=0}

16. Get all users by subscription type:

curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/users?type=ADVANCED

17. Get all users by subscription type and date (before):

curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/users?type=STANDARD&date=2017-08-08&order=1

18. Get list of subscriptions assigned to user (id = 2):

curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/user/2/subscriptions

19. Get subscription order with subscription (id = 1) and to user (id = 2):

curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/user/2/subscription/1

20. Create subscription order with subscription (id = 1) and user (id = 2):

curl -d '{"subscribedAt": "2017-08-05","expiredAt": "2018-05-05","subscription":{"id": 1,"name": "subscription1","expireAt": 1544562000000,"type": "PREMIUM"}}' -H "Accept: Application/json" -H "Content-Type: application/json" -X POST http://localhost:8080/newsletterSub/api/user/2/subscription

21. Update subscription order with subscription (id = 1) and user (id = 2):

curl -d '{"subscribedAt": "2017-08-05","expiredAt": "2018-06-06","subscription":{"id": 1,"name": "subscription1","expireAt": 1544562000000,"type": "PREMIUM"}}' -H "Accept: Application/json" -H "Content-Type: application/json" -X PUT http://localhost:8080/newsletterSub/api/user/2/subscription/1

22. Delete subscription order with subscription (id = 1) and user (id = 2):

curl -H "Accept: application/json" -H "Content-Type: application/json" -X DELETE http://localhost:8080/newsletterSub/api/user/2/subscription/1

23. Delete all subscription orders with user (id = 2):

curl -H "Accept: application/json" -H "Content-Type: application/json" -X DELETE http://localhost:8080/newsletterSub/api/user/2/subscriptions

24. Get list of users assigned to subscription (id = 2):

curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/subscription/2/users

-------------------------------XML------------------------------

1. Get all subscriptions:

curl -H "Accept: application/xml" -H "Content-Type: application/xml" -X GET http://localhost:8080/newsletterSub/api/subscriptions

2. Get subscription by id = 1:

curl -H "Accept: application/xml" -H "Content-Type: application/xml" -X GET http://localhost:8080/newsletterSub/api/subscription/1

3. Get subscriptions by user id = 1:

curl -H "Accept: application/xml" -H "Content-Type: application/xml" -X GET http://localhost:8080/newsletterSub/api/user/1/subscription

4. Create subscription:

curl -d '<Subscription><name>subscription11</name><expireAt>2018-11-11</expireAt><type>PREMIUM</type></Subscription>' -H "Accept: application/xml" -H "Content-Type: application/xml" -X POST http://localhost:8080/newsletterSub/api/subscription

5. Update subscription width id = 4:

curl -d '<Subscription><name>subscription4</name><expireAt>2018-11-11</expireAt><type>PREMIUM</type></Subscription>' -H "Accept: application/xml" -H "Content-Type: application/xml" -X PUT http://localhost:8080/newsletterSub/api/subscription/4

6. Delete subscription with id = 4:

ROLE: ADMIN and DBA
curl -H "Accept: application/xml" -H "Content-Type: application/xml" -X DELETE http://localhost:8080/newsletterSub/api/subscription/4

7. Delete all subscriptions:

ROLE: ADMIN and DBA
curl -H "Accept: application/xml" -H "Content-Type: application/xml" -X DELETE http://localhost:8080/newsletterSub/api/subscriptions

8. Get user by id = 1:

curl -i -H "Accept: application/xml" -H "Content-Type: application/xml" -X GET http://localhost:8080/newsletterSub/api/user/1

9. Create user:

curl -d '<User><login>user5@gmail.com</login><age>25</age><rating>1.00</rating><status>UNVERIFIED</status></User>' -H "Accept: application/xml" -H "Content-Type: application/xml" -X POST http://localhost:8080/newsletterSub/api/user

10. Update user with id = 4:

curl -d '<User><login>user5@gmail.com</login><age>25</age><rating>10.00</rating><status>UNVERIFIED</status></User>' -H "Accept: application/xml" -H "Content-Type: application/xml" -X PUT http://localhost:8080/newsletterSub/api/user/4

11. Delete user with id = 4:

ROLE: ADMIN and DBA
curl -H "Accept: application/xml" -H "Content-Type: application/xml" -X DELETE http://localhost:8080/newsletterSub/api/user/4

12. Delete all users:

ROLE: ADMIN and DBA
curl -H "Accept: application/xml" -H "Content-Type: application/xml" -X DELETE http://localhost:8080/newsletterSub/api/users

13. Get all users:

curl -i -H "Accept: application/xml" -H "Content-Type: application/xml" -X GET http://localhost:8080/newsletterSub/api/users

14. Get all users subscribed before date:

curl -i -H "Accept: application/xml" -H "Content-Type: application/xml" -X GET http://localhost:8080/newsletterSub/api/users?date=2017-08-08&order=1

15. Get all users subscribed after date:

curl -i -H "Accept: application/xml" -H "Content-Type: application/xml" -X GET http://localhost:8080/newsletterSub/api/users?date=2017-08-08{&order=0}

16. Get all users by subscription type:

curl -i -H "Accept: application/xml" -H "Content-Type: application/xml" -X GET http://localhost:8080/newsletterSub/api/users?type=ADVANCED

17. Get all users by subscription type and date (before):

curl -i -H "Accept: application/xml" -H "Content-Type: application/xml" -X GET http://localhost:8080/newsletterSub/api/users?type=STANDARD&date=2017-08-08&order=1

18. Get list of subscriptions assigned to user (id = 2):

curl -i -H "Accept: application/xml" -H "Content-Type: application/xml" -X GET http://localhost:8080/newsletterSub/api/user/2/subscriptions

19. Get subscription order with subscription (id = 1) and to user (id = 2):

curl -i -H "Accept: application/xml" -H "Content-Type: application/xml" -X GET http://localhost:8080/newsletterSub/api/user/2/subscription/1

20. Create subscription order with subscription (id = 1) and user (id = 2):

curl -d '<UserSubOrder><subscribedAt>2017-08-05</subscribedAt><expiredAt>2018-05-05</expiredAt><subscription><id>1</id><name>subscription1</name><expireAt>1544562000000</expireAt><type>PREMIUM</type></subscription></UserSubOrder>' -H "Accept: application/xml" -H "Content-Type: application/xml" -X POST http://localhost:8080/newsletterSub/api/user/2/subscription

21. Update subscription order with subscription (id = 1) and user (id = 2):

curl -d '<UserSubOrder><subscribedAt>2017-08-05</subscribedAt><expiredAt>2018-06-06</expiredAt><subscription><id>1</id><name>subscription1</name><expireAt>1544562000000</expireAt><type>PREMIUM</type></subscription></UserSubOrder>' -H "Accept: application/xml" -H "Content-Type: application/xml" -X PUT http://localhost:8080/newsletterSub/api/user/2/subscription/1

22. Delete subscription order with subscription (id = 1) and user (id = 2):

curl -H "Accept: application/xml" -H "Content-Type: application/xml" -X DELETE http://localhost:8080/newsletterSub/api/user/2/subscription/1

23. Delete all subscription orders with user (id = 2):

curl -H "Accept: application/xml" -H "Content-Type: application/xml" -X DELETE http://localhost:8080/newsletterSub/api/user/2/subscriptions

24. Get list of users assigned to subscription (id = 2):

curl -i -H "Accept: application/xml" -H "Content-Type: application/xml" -X GET http://localhost:8080/newsletterSub/api/subscription/2/users
