import com.codecool.shop.config.ConnectionManager;
import com.codecool.shop.dao.implementation.SupplierDaoJdbc;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class SupplierDaoJdbcTest {

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
                    ConnectionManager.getInstance().getConnection().prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                beforeAdding = rs.getInt("row_number");
            }

            Supplier testSupplier = new Supplier("Monsters Co", "A lot of monsters");
            SupplierDaoJdbc.getInstance().add(testSupplier);

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
            PreparedStatement preparedStatement =
                    ConnectionManager.getInstance().getConnection().prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                row_number = rs.getInt("row_number");
            }

            if (row_number > 0)
            {
                assertEquals(Supplier.class, SupplierDaoJdbc.getInstance().find(1).getClass());
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
            Connection connection = ConnectionManager.getInstance().getConnection();

            PreparedStatement preparedStatementCountRow =
                    connection.prepareStatement(queryCountRows);

            ResultSet rs = preparedStatementCountRow.executeQuery();

            while(rs.next()) {
                beforeAdding = rs.getInt("row_number");
            }

            Supplier testSupplier = new Supplier("Monsters Co", "A lot of monsters");
            SupplierDaoJdbc.getInstance().add(testSupplier);


      /*      PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

            String querySelectAll = "SELECT * FROM supplier;";
            preparedStatement =
                    connection.prepareStatement(querySelectAll);
            rs = preparedStatement.executeQuery();

            while(rs.next()) {
                if (rs.isLast()) SupplierDaoJdbc.getInstance().remove(rs.getInt("id"));
            }*/

            Statement stmt = connection.createStatement();
            String selectQuery = "SELECT id FROM supplier WHERE id= (select max(id) from supplier);";
            rs = stmt.executeQuery(selectQuery);

            while(rs.next()) {
                int lastId = rs.getInt("id");
                SupplierDaoJdbc.getInstance().remove(lastId);
            }






            preparedStatementCountRow =
                    connection.prepareStatement(queryCountRows);
            rs = preparedStatementCountRow.executeQuery();

            while(rs.next()) {
                afterAdding = rs.getInt("row_number");
                System.err.println(afterAdding + " " + beforeAdding);
            }

            assertEquals(0, (afterAdding-beforeAdding));

        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    @Test
    void testGetAllMethod() {
    }
}