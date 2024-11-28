CREATE TABLE `tab_business` (
                                `id` bigint NOT NULL,
                                `message` varchar(64) DEFAULT NULL,
                                `version` int NOT NULL DEFAULT '0',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;