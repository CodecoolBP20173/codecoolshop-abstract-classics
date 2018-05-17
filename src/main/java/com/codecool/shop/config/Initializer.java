package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        int numberOfProducts = ProductDaoJdbc.getInstance().getNumberOfProducts();
        if (numberOfProducts == 0) {

            ProductDaoJdbc productDataStore = ProductDaoJdbc.getInstance();
            ProductCategoryDaoJdbc productCategoryDataStore = ProductCategoryDaoJdbc.getInstance();
            SupplierDaoJdbc supplierDataStore = SupplierDaoJdbc.getInstance();

            //setting up a new supplier
            Supplier amazon = new Supplier("Amazon", "Digital content and services");
            supplierDataStore.add(amazon);
            Supplier womb = new Supplier("Mother Womb", "családtag, igaza van");
            supplierDataStore.add(womb);
            Supplier catholicChurch = new Supplier("The Catholic Church", "Praise the Lord");
            supplierDataStore.add(catholicChurch);
            Supplier plasticSurgeon = new Supplier("Plastic Surgeon", "He/She will pimp you up, for the right money.");
            supplierDataStore.add(plasticSurgeon);
            Supplier umbrellaFactory = new Supplier("Umbrella Factory", "If you need the coolest umbrellas, you need the Umbrella Factory");
            supplierDataStore.add(umbrellaFactory);
            Supplier alexandra = new Supplier("Alexandra", "Books and paper stuff.");
            supplierDataStore.add(alexandra);
            Supplier toothpickSupplier = new Supplier("Daneson", "Thoothpicks for every occasion.");
            supplierDataStore.add(toothpickSupplier);
            Supplier joanStratakosPottery = new Supplier("Joan Stratakos Pottery", "Pottery and Art");
            supplierDataStore.add(joanStratakosPottery);

            //setting up a new product category
            ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
            ProductCategory people = new ProductCategory("People", "szülő", "mindig tudja mi a jó nekem és másnak is");
            ProductCategory booze = new ProductCategory("Stealthy Flask", "Container of liquid", "A flask for storing alcoholic beverages unnoticed.");
            ProductCategory forWomen = new ProductCategory("For Women", "girl stuff", "Products desined specially for women.");
            ProductCategory forMan = new ProductCategory("For Men", "man stuff", "Products desined specially for men.");
            ProductCategory book = new ProductCategory("Book", "for our educated readers", "Education and pleasure.");
            ProductCategory pottery = new ProductCategory("Pottery", "handmade art", "Pottery for storing stuff or just displaing it somewhere.");
            ProductCategory armor = new ProductCategory("Armor", "keeping you safe", "Armor for the battles to come.");

            productCategoryDataStore.add(tablet);
            productCategoryDataStore.add(people);
            productCategoryDataStore.add(booze);
            productCategoryDataStore.add(forWomen);
            productCategoryDataStore.add(forMan);
            productCategoryDataStore.add(book);
            productCategoryDataStore.add(pottery);
            productCategoryDataStore.add(armor);

            productDataStore.add(new Product("Mother Magdalena", 51, "USD", "She is our best selling Granny! Straight from the tevevision series \" Among Friends\". Charming personality, replaces your mother when needed. ", people, womb, "/static/img/product_0.jpg"));
            productDataStore.add(new Product("The Bible Flask", 25.0f, "USD", "The good book with a flask. Yes, this bible cleverly sneaks in a 4 oz stainless steel flask inside of the hollowed out pages.", booze, catholicChurch, "/static/img/product_1.jpg"));
            productDataStore.add(new Product("Boobie Bag Soft Flask", 12, "USD", "INSTANT BOOB JOB!" +
                    "  Our Boobie Bags will give you a 4 oz “lift” each side while comfortably  hidden in your bra. ", booze, plasticSurgeon, "/static/img/product_2.jpg"));
            productDataStore.add(new Product("Umbrella Flask", 14.00f, "USD", "Fake Umbrella Flask holds 9 oz. of your favorite party beverage!", booze, umbrellaFactory, "/static/img/product_3.jpg"));
            productDataStore.add(new Product("Porn For Women", 25, "USD", "Laugh out loud while relating to this sexy, smart book featuring strapping men doing housework.", forWomen, alexandra, "/static/img/product_4.jpg"));
            productDataStore.add(new Product("Scotch-Infused Toothpicks Gift Set", 30.00f, "USD", "Savor the subtle satisfaction of fine single malt scotch with these infused toothpicks.", forMan, toothpickSupplier, "/static/img/product_5.jpg"));
            productDataStore.add(new Product("Elwood the Unicorn Cereal Bowl", 42.00f, "USD", "Elwood the unicorn turns a simple bowl of cereal, soup, or ice cream into a walk on the wondrous side.", forWomen, joanStratakosPottery, "/static/img/product_6.jpg"));
            productDataStore.add(new Product("Deer Rear with Bottle Opener", 114f, "USD", "Synthetic Deer Butt Opener\n" +
                    "When a sportsman sees this Deer Butt Bottle Opener – they have to have one!", forMan, amazon, "/static/img/product_7.jpg"));
            productDataStore.add(new Product("Cat Battle Armor", 500, "USD", "If your cat spends any time out in the wild it’s time to level them up with this cat battle armor. Your cat will…", armor, amazon, "/static/img/product_8.jpg"));
        }
    }
}
