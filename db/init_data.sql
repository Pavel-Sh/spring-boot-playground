INSERT INTO `microservice` (`id`, `name`) VALUES
(1, 'users'),
(2, 'orders'),
(3, 'reports'),
(4, 'search');

ALTER TABLE `microservice`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;