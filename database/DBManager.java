package database;

import entities.client.Client;
import entities.client.ClientType;
import entities.operation.Operation;
import entities.operation.OperationStatus;
import entities.operation.Transaction;
import entities.wallet.Wallet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBManager {
    private static DBManager instance;

    private static String URL;
    private static String USER;
    private static String PASSWORD;

    private DBManager() {
        try {
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream(".idea/db.properties");
            props.load(fis);

            URL = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASSWORD = props.getProperty("db.password");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not load DB configuration.");
        }
    }

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void runSQLScript(String filePath) throws Exception {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            StringBuilder sql = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sql.append(line).append("\n");
                if (line.trim().endsWith(";")) {
                    stmt.execute(sql.toString());
                    sql.setLength(0);
                }
            }

            System.out.println("SQL script executed successfully.");
        }
    }

    public Client getClientById(int clientId) {
        String query = "SELECT * FROM clients WHERE client_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, clientId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Client(
                        rs.getInt("client_id"),
                        rs.getString("name"),
                        rs.getInt("bank_id"),
                            ClientType.INDIVIDUAL.toString()
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving client with ID: " + clientId);
        }
        return null;
    }

    public void addClient(Client client) {
        String query = "INSERT INTO clients (client_id, name, bank_id, client_type) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, client.getClientId());
            pstmt.setString(2, client.getName());
            pstmt.setInt(3, client.getBankId());
            pstmt.setString(4, client.getClientType().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding client: " + client.getName());
        }
    }

    public void updateClient(Client client) {
        String query = "UPDATE clients SET name = ?, bank_id = ?, client_type = ? WHERE client_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, client.getName());
            pstmt.setInt(2, client.getBankId());
            pstmt.setString(3, client.getClientType().toString());
            pstmt.setInt(4, client.getClientId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating client: " + client.getName());
        }
    }

    public void deleteClient(int clientId) {
        String query = "DELETE FROM clients WHERE client_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, clientId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting client with ID: " + clientId);
        }
    }

    public Wallet getWalletById(int walletId) {
        String query = "SELECT * FROM wallets WHERE wallet_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, walletId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Wallet(
                        rs.getInt("wallet_id"),
                        rs.getInt("balance"),
                        rs.getBoolean("is_active")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving wallet with ID: " + walletId);
        }
        return null;
    }

    public void addWallet(Wallet wallet) {
        String query = "INSERT INTO wallets (wallet_id, balance, is_active) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, wallet.getWalletId());
            pstmt.setInt(2, wallet.getBalance());
            pstmt.setBoolean(3, wallet.isActive());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding wallet with ID: " + wallet.getWalletId());
        }
    }

    public void updateWallet(Wallet wallet) {
        String query = "UPDATE wallets SET balance = ?, is_active = ? WHERE wallet_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, wallet.getBalance());
            pstmt.setBoolean(2, wallet.isActive());
            pstmt.setInt(3, wallet.getWalletId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating wallet with ID: " + wallet.getWalletId());
        }
    }

    public void deleteWallet(int walletId) {
        String query = "DELETE FROM wallets WHERE wallet_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, walletId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting wallet with ID: " + walletId);
        }
    }

    public List<Operation> getAllOperations() {
        String query = "SELECT * FROM operations";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            List<Operation> operations = new ArrayList<>();
            while (rs.next()) {
                Operation operation = new Transaction(
                    rs.getInt("operation_id"),
                    rs.getString("time"),
                    rs.getInt("amount"),
                    getClientById(rs.getInt("sender_id")),
                    getClientById(rs.getInt("receiver_id")),
                    OperationStatus.valueOf(rs.getString("status"))
                );
                operations.add(operation);
            }
            return operations;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving operations.");
        }
    }

    public List<Wallet> getAllWallets() {
        String query = "SELECT * FROM wallets";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            List<Wallet> wallets = new ArrayList<>();
            while (rs.next()) {
                Wallet wallet = new Wallet(
                    rs.getInt("wallet_id"),
                    rs.getInt("balance"),
                    rs.getBoolean("is_active")
                );
                wallets.add(wallet);
            }
            return wallets;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving wallets.");
        }
    }

    public List<Client> getAllClients() {
        String query = "SELECT * FROM clients";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            List<Client> clients = new ArrayList<>();
            while (rs.next()) {
                Client client = new Client(
                    rs.getInt("client_id"),
                    rs.getString("name"),
                    rs.getInt("bank_id"),
                    ClientType.INDIVIDUAL.getType()
                );
                clients.add(client);
            }
            return clients;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving clients.");
        }
    }
}
