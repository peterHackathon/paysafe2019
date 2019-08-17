CREATE TABLE `categories` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(512),
  `image` varchar(1024),
  `type` varchar(512),
  `color` varchar(64),
  `user_id` int,
  PRIMARY KEY (`id`)
) 

CREATE TABLE `users` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(64),
  `password` varchar(512),
  `first_name` varchar(64),
  `last_name` varchar(64),
  `photo` varchar(512),
  `wallet` float,
  `role` varchar(64),
  PRIMARY KEY (`id`)
)

CREATE TABLE `users_clustering_data` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int,
  `age` int,
  `monthly_income` int,
  `spending_score` int,
  PRIMARY KEY (`id`)
)

CREATE TABLE `transactions` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `category_id` int,
  `date` Date,
  `note` varchar(1024),
  `color` varchar(64),
  `price` float,
  `real_price` float,
  PRIMARY KEY (`id`)
)

CREATE TABLE `cat_image` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT, 
  `name` varchar(1024),
  PRIMARY KEY (`id`)
)