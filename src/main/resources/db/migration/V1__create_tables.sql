-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
                            `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
                            `active` bit NOT NULL DEFAULT 1,
                            `first_name` varchar(255) NOT NULL,
                            `last_name` varchar(255) NOT NULL,
                            `email` varchar(255) NOT NULL,
                            `phone_number` varchar(25) NOT NULL,
                            `password` varchar(255) NOT NULL,
                            PRIMARY KEY (`id`)
);


-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
                            `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
                            `role_name` varchar(255) NOT NULL,
                            PRIMARY KEY (`id`)
);


-- -------------------------------
-- Table structure for users_roles
-- -------------------------------
DROP TABLE IF EXISTS `users_roles`;
CREATE TABLE `users_roles` (
								`user_id` bigint UNSIGNED NOT NULL,
								`role_id` bigint UNSIGNED NOT NULL,
                                 PRIMARY KEY (`user_id`, `role_id`),
                                 CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                 CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);


-- ----------------------------
-- Table structure for subjects
-- ----------------------------
DROP TABLE IF EXISTS `subjects`;
CREATE TABLE `subjects` (
								`id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
								`active` bit NOT NULL DEFAULT 1,
								`name` varchar(255) NOT NULL,
								`user_id` bigint UNSIGNED NOT NULL,
								PRIMARY KEY (`id`),
                                CONSTRAINT `FK_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);


-- ----------------------------
-- Table structure for actions
-- ----------------------------
DROP TABLE IF EXISTS `actions`;
CREATE TABLE `actions` (
								`id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
								`active` bit NOT NULL DEFAULT 1,
								`subject_id` bigint UNSIGNED NOT NULL,
                                `title` varchar(255) NOT NULL,
                                `start_date` date NOT NULL,
                                `frequency` bigint UNSIGNED NOT NULL,
                                `is_recurring` bit NOT NULL DEFAULT 0,
                                `notice_period_advance` int UNSIGNED,
                                `notice_period_short` int UNSIGNED NOT NULL,
                                `requires_confirmation` bit NOT NULL DEFAULT 0,
								PRIMARY KEY (`id`),
                                CONSTRAINT `FK_subject_id` FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);



-- ----------------------------
-- Table structure for events
-- ----------------------------
DROP TABLE IF EXISTS `events`;
CREATE TABLE `events` (
								`id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
								`action_id` bigint UNSIGNED NOT NULL,
                                `date` date NOT NULL,
                                `note` varchar(255),
                                `is_done` bit,
								PRIMARY KEY (`id`),
                                CONSTRAINT `FK_action_id` FOREIGN KEY (`action_id`) REFERENCES `actions` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);
