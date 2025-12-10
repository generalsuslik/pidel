INSERT INTO pizzas (description, fat, kcal, name, price, protein, pizza_image_id)
VALUES
('mmm, love it', 14, 250, 'Fucking pepperoni', 700, 20, (SELECT id FROM images WHERE pizza_name = 'pepperoni')),
('ham, yammy mozzarella, mashrooms', 15.06, 240, 'Ham & Mushrooms', 650, 24, (SELECT id FROM images WHERE pizza_name = 'ham_cheese'));

INSERT INTO pizza_pizza_size (pizza_id, pizza_size_id)
VALUES
(1, (SELECT id FROM pizza_size WHERE size = 20)),
(1, (SELECT id FROM pizza_size WHERE size = 25)),
(1, (SELECT id FROM pizza_size WHERE size = 30)),
(1, (SELECT id FROM pizza_size WHERE size = 35)),
(2, (SELECT id FROM pizza_size WHERE size = 20)),
(2, (SELECT id FROM pizza_size WHERE size = 25)),
(2, (SELECT id FROM pizza_size WHERE size = 30)),
(2, (SELECT id FROM pizza_size WHERE size = 35));