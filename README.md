# SubscriptionServiceREST
REST API Newsletter Subscription Service

Basic authentication:

URL: /api/*
user: user
password: user123

JSON:

1. Get all subscriptions:

curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/subscriptions

2. Get subscription by id = 1:

curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/subscription/1

3. Get subscriptions by user id = 1:

curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/user/1/subscription

4. Create subscription:

curl -d '{"name":"subscription11", "expireAt": "2018-11-11", "type": "PREMIUM"}' -H "Accept: Application/json" -H "Content-Type: application/json" -X POST http://localhost:8080/newsletterSub/api/subscription

XML:

1. Get all subscriptions:

curl -H "Accept: application/xml" -H "Content-Type: application/xml" -X GET http://localhost:8080/newsletterSub/api/subscriptions

2. Get subscription by id = 1:

curl -H "Accept: application/xml" -H "Content-Type: application/xml" -X GET http://localhost:8080/newsletterSub/api/subscription/1

3. Get subscriptions by user id = 1:

curl -H "Accept: application/xml" -H "Content-Type: application/xml" -X GET http://localhost:8080/newsletterSub/api/user/1/subscription

4. Create subscription:

curl -d '<Subscription><name>subscription43</name><expireAt>2018-11-11</expireAt><type>PREMIUM</type></Subscription>' -H "Accept: application/xml" -H "Content-Type: application/xml" -X POST http://localhost:8080/newsletterSub/api/subscription




