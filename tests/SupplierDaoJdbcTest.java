import com.codecool.shop.config.ConnectionManager;
import com.codecool.shop.dao.implementation.SupplierDaoJdbc;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SupplierDaoJdbcTest {

    private static Connection connection;

    @BeforeAll
    static void loadTestDatabase() {
        try {
            connection = ConnectionManager.getInstance().getTestConnection();

            String createTablesQuery = readSqlQueryFile("src/main/sql/init_db.sql");

            PreparedStatement stmt = connection.prepareStatement(createTablesQuery);
            stmt.executeUpdate();

            String insertDataInTables = readSqlQueryFile("src/main/sql/init_test_data.sql");
            stmt = connection.prepareStatement(insertDataInTables);
            stmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetInstance() {
        assertNotNull(SupplierDaoJdbc.getInstance());
    }

    @Test
    void testAddMethod() {
        String query = "SELECT COUNT(*) as row_number FROM supplier;";

        int beforeAdding = 0;
        int afterAdding = 0;

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                beforeAdding = rs.getInt("row_number");
            }

            Supplier testSupplier = new Supplier("Monsters Co", "A lot of monsters");
            SupplierDaoJdbc.getTestInstance(connection).add(testSupplier);

            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                afterAdding = rs.getInt("row_number");
            }

            assertEquals(1, afterAdding - beforeAdding);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testFindMethodReturnedClass() {
        String query = "SELECT COUNT(*) as row_number FROM supplier;";

        int row_number = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
                    //ConnectionManager.getInstance().getConnection().prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                row_number = rs.getInt("row_number");
            }

            if (row_number > 0) {
                assertEquals(Supplier.class, SupplierDaoJdbc.getTestInstance(connection).find(1).getClass());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testRemoveMethod() {
        String query = "INSERT INTO supplier (name, description) VALUES ('Removable Co', 'Kill everything...');";
        String queryCountRows = "SELECT COUNT(*) as row_number FROM supplier;";

        int afterAdding = 0;
        int beforeAdding = 0;

        try {
            //Connection connection = ConnectionManager.getInstance().getConnection();

            PreparedStatement preparedStatementCountRow = connection.prepareStatement(queryCountRows);

            ResultSet rs = preparedStatementCountRow.executeQuery();

            while (rs.next()) {
                beforeAdding = rs.getInt("row_number");
            }

            Supplier testSupplier = new Supplier("Monsters Co", "A lot of monsters");
            SupplierDaoJdbc.getTestInstance(connection).add(testSupplier);

            Statement stmt = connection.createStatement();
            String selectQuery = "SELECT id FROM supplier WHERE id= (select max(id) from supplier);";
            rs = stmt.executeQuery(selectQuery);

            while (rs.next()) {
                int lastId = rs.getInt("id");
                SupplierDaoJdbc.getInstance().remove(lastId);
            }

            preparedStatementCountRow =
                    connection.prepareStatement(queryCountRows);
            rs = preparedStatementCountRow.executeQuery();

            while (rs.next()) {
                afterAdding = rs.getInt("row_number");
            }

            assertEquals(0, (afterAdding - beforeAdding));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetAllMethodNotNull() {
        Supplier testSupplier = new Supplier("GetAll Co", "We will get all the things what you desire.");
        SupplierDaoJdbc.getTestInstance(connection).add(testSupplier);

        assertNotNull(SupplierDaoJdbc.getTestInstance(connection).getAll());
    }

    @Test
    void testGetAllMethod() {
        Supplier testSupplier = new Supplier("GetAll Co", "We will get all the things what you desire.");
        SupplierDaoJdbc.getTestInstance(connection).add(testSupplier);

        List<Supplier> supplierList = SupplierDaoJdbc.getTestInstance(connection).getAll();
        assertEquals("GetAll Co",supplierList.get(supplierList.size()-1).getName());
    }

    private static String readSqlQueryFile(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        Stream<String> stream = null;
        try {
            stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stream.forEach(s -> contentBuilder.append(s).append(System.getProperty("line.separator")));

        return contentBuilder.toString();
    }
}