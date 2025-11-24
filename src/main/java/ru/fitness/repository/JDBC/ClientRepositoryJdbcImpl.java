package ru.fitness.repository.JDBC;

import ru.fitness.entities.Client;
import ru.fitness.repository.Client.ClientRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryJdbcImpl extends JDBCBaseRepository implements ClientRepository {

    @Override
    public Client getClientByPhoneNumber(String phoneNumber) {
        System.out.println("DEBUG: ClientRepositoryJdbcImpl.getClientByPhoneNumber: " + phoneNumber);

        String sql = "SELECT * FROM clients WHERE phone = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            System.out.println("DEBUG: Connection established, preparing statement");
            stmt.setString(1, phoneNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Client client = mapResultSetToClient(rs);
                System.out.println("DEBUG: Client found: " + client.getFullName());
                return client;
            }
            System.out.println("DEBUG: Client not found");
            return null;

        } catch (SQLException e) {
            System.out.println("DEBUG: SQL Error in getClientByPhoneNumber: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to find client by phone number: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Client> getClientsByName(String name) {
        String sql = "SELECT * FROM clients WHERE full_name ILIKE ? ORDER BY full_name";
        List<Client> clients = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                clients.add(mapResultSetToClient(rs));
            }
            return clients;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find clients by name", e);
        }
    }

    @Override
    public void add(Client object) {
        System.out.println("DEBUG: ClientRepositoryJdbcImpl.add started");

        String sql = "INSERT INTO clients (full_name, phone) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            System.out.println("DEBUG: Before setting parameters");
            stmt.setString(1, object.getFullName());
            stmt.setString(2, object.getPhoneNumber());
            System.out.println("DEBUG: Before executeUpdate");
            stmt.executeUpdate();

            System.out.println("DEBUG: Before getGeneratedKeys");
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    object.setId(generatedId);
                    System.out.println("DEBUG: Generated ID: " + generatedId);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to add client: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(int id, Client newObject) {
        String sql = "UPDATE clients SET full_name = ?, phone = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newObject.getFullName());
            stmt.setString(2, newObject.getPhoneNumber());
            stmt.setInt(3, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update client", e);
        }
    }

    @Override
    public Client getById(int id) {
        String sql = "SELECT * FROM clients WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToClient(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find client by id", e);
        }
    }

    @Override
    public List<Client> getAll() {
        String sql = "SELECT * FROM clients ORDER BY id";
        List<Client> clients = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                clients.add(mapResultSetToClient(rs));
            }
            return clients;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all clients", e);
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM clients WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete client", e);
        }
    }

    private Client mapResultSetToClient(ResultSet rs) throws SQLException {
        return new Client(
                rs.getInt("id"),
                rs.getString("full_name"),
                rs.getString("phone")
        );
    }
}