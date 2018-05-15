package com.codecool.shop.utils;
import org.mindrot.jbcrypt.BCrypt;


/**
 * This class is for methods which can be used but they don't belong to any of the software logics.
 * For working put the following dependency into the pom:
 *         <dependency>
 *             <groupId>org.mindrot</groupId>
 *             <artifactId>jbcrypt</artifactId>
 *             <version>0.4</version>
 *         </dependency>
 */
public class Utils {

    /**
     * It makes text to hashed text with salt.
     * @param plainText (String): text for hashing with salt
     * @return hashed text (String)
     */
    public static String hashPassword(String plainText) {
        return BCrypt.hashpw(plainText, BCrypt.gensalt(12));
    }

    /**
     * It checks whether two text are the same. One of them is a hashed text, the other is a plain text.
     * @param plainTextPassword (String): text for hashing with salt
     * @param hashedPassword (String): hashed text
     * @return true or false
     */
    public static Boolean checkPasswords(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}

