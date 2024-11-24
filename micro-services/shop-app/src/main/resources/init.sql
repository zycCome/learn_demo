CREATE TABLE `shop` (
                        `id` int NOT NULL,
                        `shop_name` varchar(32) DEFAULT NULL,
                        `address` varchar(32) DEFAULT NULL,
                        `email` varchar(32) DEFAULT NULL,
                        `create_time` datetime DEFAULT NULL,
                        `update_time` datetime DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;