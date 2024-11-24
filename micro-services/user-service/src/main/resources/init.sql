CREATE TABLE `user` (
                        `id` int NOT NULL,
                        `user_name` varchar(32) DEFAULT NULL,
                        `password` varchar(32) DEFAULT NULL,
                        `email` varchar(32) DEFAULT NULL,
                        `phone` varchar(32) DEFAULT NULL,
                        `create_time` datetime DEFAULT NULL,
                        `update_time` datetime DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;