INSERT INTO public.supplier (id, name, description) VALUES (1, 'Amazon', 'Digital content and services');
INSERT INTO public.supplier (id, name, description) VALUES (2, 'Mother Womb', 'családtag, igaza van');
INSERT INTO public.supplier (id, name, description) VALUES (3, 'The Catholic Church', 'Praise the Lord');
INSERT INTO public.supplier (id, name, description) VALUES (4, 'Plastic Surgeon', 'He/She will pimp you up, for the right money.');
INSERT INTO public.supplier (id, name, description) VALUES (5, 'Umbrella Factory', 'If you need the coolest umbrellas, you need the Umbrella Factory');
INSERT INTO public.supplier (id, name, description) VALUES (6, 'Alexandra', 'Books and paper stuff.');
INSERT INTO public.supplier (id, name, description) VALUES (7, 'Daneson', 'Thoothpicks for every occasion.');
INSERT INTO public.supplier (id, name, description) VALUES (8, 'Joan Stratakos Pottery', 'Pottery and Art');

INSERT INTO public.product_category (id, name, description, department) VALUES (1, 'Tablet', 'Hardware', 'A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.');
INSERT INTO public.product_category (id, name, description, department) VALUES (2, 'People', 'szülő', 'mindig tudja mi a jó nekem és másnak is');
INSERT INTO public.product_category (id, name, description, department) VALUES (3, 'Stealthy Flask', 'Container of liquid', 'Container of liquid", "A flask for storing alcoholic beverages unnoticed.');
INSERT INTO public.product_category (id, name, description, department) VALUES (4, 'For Women', 'girl stuff', 'Products desined specially for women.');
INSERT INTO public.product_category (id, name, description, department) VALUES (5, 'For Men', 'man stuff', 'Products desined specially for men.');
INSERT INTO public.product_category (id, name, description, department) VALUES (6, 'Book', 'for our educated readers', 'Education and pleasure.');
INSERT INTO public.product_category (id, name, description, department) VALUES (7, 'Pottery', 'handmade art', 'Pottery for storing stuff or just displaing it somewhere.');
INSERT INTO public.product_category (id, name, description, department) VALUES (8, 'Armor', 'keeping you safe', 'Armor for the battles to come.');

INSERT INTO public.product (id, name, description, default_price, default_currency, product_category_id, supplier_id, product_image) VALUES (1, 'Mother Magdalena', 'She is our best selling Granny! Straight from the tevevision series " Among Friends". Charming personality, replaces your mother when needed.', 51, 'USD', 2, 2, '/static/img/product_1');
INSERT INTO public.product (id, name, description, default_price, default_currency, product_category_id, supplier_id, product_image) VALUES (2, 'The Bible Flask', 'The good book with a flask. Yes, this bible cleverly sneaks in a 4 oz stainless steel flask inside of the hollowed out pages.', 25, 'USD', 3, 3, '/static/img/product_2');
INSERT INTO public.product (id, name, description, default_price, default_currency, product_category_id, supplier_id, product_image) VALUES (3, 'Boobie Bag Soft Flask', 'INSTANT BOOB JOB!  Our Boobie Bags will give you a 4 oz “lift” each side while comfortably  hidden in your bra.', 12, 'USD', 3, 4, '/static/img/product_3');
INSERT INTO public.product (id, name, description, default_price, default_currency, product_category_id, supplier_id, product_image) VALUES (4, 'Umbrella Flask', 'Fake Umbrella Flask holds 9 oz. of your favorite party beverage!', 14, 'USD', 3, 5, '/static/img/product_4');
INSERT INTO public.product (id, name, description, default_price, default_currency, product_category_id, supplier_id, product_image) VALUES (5, 'Porn For Women', 'Laugh out loud while relating to this sexy, smart book featuring strapping men doing housework.', 25, 'USD', 4, 6, '/static/img/product_5');
INSERT INTO public.product (id, name, description, default_price, default_currency, product_category_id, supplier_id, product_image) VALUES (6, 'Scotch-Infused Toothpicks Gift Set', 'Savor the subtle satisfaction of fine single malt scotch with these infused toothpicks.', 30, 'USD', 5, 7, '/static/img/product_6');
INSERT INTO public.product (id, name, description, default_price, default_currency, product_category_id, supplier_id, product_image) VALUES (7, 'Elwood the Unicorn Cereal Bowl', 'Elwood the unicorn turns a simple bowl of cereal, soup, or ice cream into a walk on the wondrous side.', 42, 'USD', 4, 8, '/static/img/product_7');
INSERT INTO public.product (id, name, description, default_price, default_currency, product_category_id, supplier_id, product_image) VALUES (8, 'Deer Rear with Bottle Opener', 'Synthetic Deer Butt Opener
 "When a sportsman sees this Deer Butt Bottle Opener – they have to have one!', 114, 'USD', 5, 1, '/static/img/product_8');
INSERT INTO public.product (id, name, description, default_price, default_currency, product_category_id, supplier_id, product_image) VALUES (9, 'Cat Battle Armor', 'If your cat spends any time out in the wild it’s time to level them up with this cat battle armor. Your cat will…', 500, 'USD', 8, 1, '/static/img/product_9');
