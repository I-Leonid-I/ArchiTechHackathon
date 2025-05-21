import database.DBManager;

public class Main {
    public static void main(String[] args) {
        try {
            // Creating the tables. Types should be created manually while running the first time
            DBManager.getInstance().runSQLScript("database/tables_creation.sql");
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
