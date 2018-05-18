INSERT INTO public.supplier (name, description) VALUES ('Amazon', 'Digital content and services');
INSERT INTO public.supplier (name, description) VALUES ('Mother Womb', 'családtag, igaza van');
INSERT INTO public.supplier (name, description) VALUES ('The Catholic Church', 'Praise the Lord');
INSERT INTO public.supplier (name, description) VALUES ('Plastic Surgeon', 'He/She will pimp you up, for the right money.');
INSERT INTO public.supplier (name, description) VALUES ('Umbrella Factory', 'If you need the coolest umbrellas, you need the Umbrella Factory');
INSERT INTO public.supplier (name, description) VALUES ('Alexandra', 'Books and paper stuff.');
INSERT INTO public.supplier (name, description) VALUES ('Daneson', 'Thoothpicks for every occasion.');
INSERT INTO public.supplier (name, description) VALUES ('Joan Stratakos Pottery', 'Pottery and Art');

INSERT INTO public.product_category (name, description, department) VALUES ('Tablet', 'Hardware', 'A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.');
INSERT INTO public.product_category (name, description, department) VALUES ('People', 'szülő', 'mindig tudja mi a jó nekem és másnak is');
INSERT INTO public.product_category (name, description, department) VALUES ('Stealthy Flask', 'Container of liquid', 'Container of liquid", "A flask for storing alcoholic beverages unnoticed.');
INSERT INTO public.product_category (name, description, department) VALUES ('For Women', 'girl stuff', 'Products desined specially for women.');
INSERT INTO public.product_category (name, description, department) VALUES ('For Men', 'man stuff', 'Products desined specially for men.');
INSERT INTO public.product_category (name, description, department) VALUES ('Book', 'for our educated readers', 'Education and pleasure.');
INSERT INTO public.product_category (name, description, department) VALUES ('Pottery', 'handmade art', 'Pottery for storing stuff or just displaing it somewhere.');
INSERT INTO public.product_category (name, description, department) VALUES ('Armor', 'keeping you safe', 'Armor for the battles to come.');

INSERT INTO public.product (name, description, default_price, default_currency, product_category_id, supplier_id,product_image) VALUES ('Mother Magdalena', 'She is our best selling Granny! Straight from the tevevision series " Among Friends". Charming personality, replaces your mother when needed.', 51, 'USD', 1, 1, '/static/img/product_1');
INSERT INTO public.product (name, description, default_price, default_currency, product_category_id, supplier_id,product_image) VALUES ('The Bible Flask', 'The good book with a flask. Yes, this bible cleverly sneaks in a 4 oz stainless steel flask inside of the hollowed out pages.', 25, 'USD', 2, 2, '/static/img/product_2');
INSERT INTO public.product (name, description, default_price, default_currency, product_category_id, supplier_id,product_image) VALUES ('Boobie Bag Soft Flask', 'INSTANT BOOB JOB!  Our Boobie Bags will give you a 4 oz “lift” each side while comfortably  hidden in your bra.', 12, 'USD', 2, 3, '/static/img/product_3');
INSERT INTO public.product (name, description, default_price, default_currency, product_category_id, supplier_id,product_image) VALUES ('Umbrella Flask', 'Fake Umbrella Flask holds 9 oz. of your favorite party beverage!', 14, 'USD', 3, 5, '/static/img/product_4');
INSERT INTO public.product (name, description, default_price, default_currency, product_category_id, supplier_id,product_image) VALUES ('Porn For Women', 'Laugh out loud while relating to this sexy, smart book featuring strapping men doing housework.', 25, 'USD', 3, 5, '/static/img/product_5');
INSERT INTO public.product (name, description, default_price, default_currency, product_category_id, supplier_id,product_image) VALUES ('Scotch-Infused Toothpicks Gift Set', 'Savor the subtle satisfaction of fine single malt scotch with these infused toothpicks.', 30, 'USD', 4, 6, '/static/img/product_6');
INSERT INTO public.product (name, description, default_price, default_currency, product_category_id, supplier_id,product_image) VALUES ('Elwood the Unicorn Cereal Bowl', 'Elwood the unicorn turns a simple bowl of cereal, soup, or ice cream into a walk on the wondrous side.', 42, 'USD', 3, 7, '/static/img/product_7');
INSERT INTO public.product (name, description, default_price, default_currency, product_category_id, supplier_id,product_image) VALUES ('Deer Rear with Bottle Opener', 'Synthetic Deer Butt Opener
 "When a sportsman sees this Deer Butt Bottle Opener – they have to have one!', 114, 'USD', 4, 0, '/static/img/product_8');
INSERT INTO public.product (name, description, default_price, default_currency, product_category_id,supplier_id,product_image) VALUES ('Cat Battle Armor', 'If your cat spends any time out in the wild it’s time to level them up with this cat battle armor. Your cat will…', 500, 'USD', 7, 0, '/static/img/product_9');
