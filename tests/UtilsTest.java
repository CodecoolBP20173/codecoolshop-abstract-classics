import com.codecool.shop.utils.Utils;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UtilsTest.class);

    @Test
    public void testCheckValidPasswords() {
        String password = "helloWorld";
        String hashedPassword = Utils.hashPassword(password);
        assertTrue(Utils.checkPasswords(password, hashedPassword));
    }

    @Test
    public void testCheckInvalidPasswords() {
        String password = "helloWorld";
        String password2 = "asd123";
        String hashedPassword = Utils.hashPassword(password);
        assertFalse(Utils.checkPasswords(password2, hashedPassword));
    }

}