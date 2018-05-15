package com.codecool.shop.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import org.mindrot.jbcrypt.BCrypt;


/*String passwordToHash = "password";
        byte[] salt = getSalt();

        String securePassword = getSecurePassword(passwordToHash, salt);
        System.out.println(securePassword); //Prints 83ee5baeea20b6c21635e4ea67847f66

        String regeneratedPassowrdToVerify = getSecurePassword(passwordToHash, salt);
        System.out.println(regeneratedPassowrdToVerify); //Prints 83ee5baeea20b6c21635e4ea67847f66*/


public class Utils {
    public String hashPassword(String plainText) {
        return BCrypt.hashpw(plainText, BCrypt.gensalt(12));
    }

    public Boolean checkPasswords(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }

    /*public static String getSecurePassword(String passwordToHash, byte[] salt) {
        String generatedPassword = null;
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt);

            byte[] bytes = md.digest(passwordToHash.getBytes());

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public Boolean verify_password(String plainTextPassword, String hashedPassword) {
        plainTextPassword
        hashed_bytes_password = hashed_password.encode('utf-8')
        return bcrypt.checkpw(plain_text_password.encode('utf-8'), hashed_bytes_password)
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }*/
}

