CREATE TABLE `tab_storage` (
                               `id` bigint NOT NULL,
                               `total` bigint DEFAULT NULL,
                               `product_id` bigint DEFAULT NULL,
                               `used` int DEFAULT NULL,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `storage`.`tab_storage`(`id`, `total`, `productId`, `used`) VALUES (1, 10000, 1, 0);
INSERT INTO `storage`.`tab_storage`(`id`, `total`, `productId`, `used`) VALUES (2, 10000, 2, 0);
