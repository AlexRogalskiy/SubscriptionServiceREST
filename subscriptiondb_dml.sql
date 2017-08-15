INSERT INTO users (login, age, registered_at, status) VALUES ('user1@gmail.com', 25, '2017-04-18', 'UNVERIFIED'), ('user2@gmail.com', 26, '2017-04-30', 'ACTIVE'), ('user3@gmail.com', 27, '2017-01-30', 'BLOCKED');

INSERT INTO subscriptions (name, expired_at, status) VALUES ('subscription1', '2018-12-12', 'PREMIUM'), ('subscription2', '2018-11-12', 'ADVANCED'), ('subscription3', '2018-10-12', 'STANDARD');

INSERT INTO user_sub_orders (user_id, subscription_id, subscribed_at, expired_at) SELECT u.user_id, s.subscription_id, '2017-12-12' AS subscribedAt, '2018-05-12' AS createdAt FROM users u, subscriptions s WHERE u.login = 'user1@gmail.com' AND s.name = 'subscription1';
INSERT INTO user_sub_orders (user_id, subscription_id, subscribed_at, expired_at) SELECT u.user_id, s.subscription_id, '2017-09-12' AS subscribedAt, '2018-09-12' AS createdAt FROM users u, subscriptions s WHERE u.login = 'user1@gmail.com' AND s.name = 'subscription2';
INSERT INTO user_sub_orders (user_id, subscription_id, subscribed_at, expired_at) SELECT u.user_id, s.subscription_id, '2017-08-30' AS subscribedAt, '2017-08-30' AS createdAt FROM users u, subscriptions s WHERE u.login = 'user2@gmail.com' AND s.name = 'subscription3';
INSERT INTO user_sub_orders (user_id, subscription_id, subscribed_at, expired_at) SELECT u.user_id, s.subscription_id, '2017-10-25' AS subscribedAt, '2018-04-25' AS createdAt FROM users u, subscriptions s WHERE u.login = 'user3@gmail.com' AND s.name = 'subscription2';
