CREATE TABLE `tab_order` (
                             `id` bigint NOT NULL,
                             `user_id` bigint DEFAULT NULL,
                             `product_id` bigint DEFAULT NULL,
                             `count` int DEFAULT NULL,
                             `money` decimal(10,0) DEFAULT NULL,
                             `status` int DEFAULT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;