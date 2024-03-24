-- revaluation.`user` definition
CREATE DATABASE revaluation;
USE revaluation;

CREATE TABLE `user`
(
    `id`          bigint                                                       NOT NULL AUTO_INCREMENT,
    `user_name`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `password`    varchar(256)                                                 NOT NULL,
    `create_time` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_users_name` (`user_name`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 20
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;