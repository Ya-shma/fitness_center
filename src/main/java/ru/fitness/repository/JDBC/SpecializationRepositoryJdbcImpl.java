package ru.fitness.repository.JDBC;

import ru.fitness.entities.Specialization;
import ru.fitness.repository.Specialization.SpecializationRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpecializationRepositoryJdbcImpl extends JDBCRepository implements SpecializationRepository {

    @Override
    public Specialization getSpecializationByName(String name) {
        String sql = "SELECT * FROM specializations WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToSpecialization(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find specialization by name", e);
        }
    }

    @Override
    public void add(Specialization object) {
        String sql = "INSERT INTO specializations (name) VALUES (?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, object.getName());
            stmt.executeUpdate();

            // Получаем сгенерированный ID
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    object.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to add specialization", e);
        }
    }

//    @Override
//    public void removeAll() {
//        String sql = "DELETE FROM specializations";
//
//        try (Connection conn = getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Failed to remove all specializations", e);
//        }
//    }

    @Override
    public void update(int id, Specialization newObject) {
        String sql = "UPDATE specializations SET name = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newObject.getName());
            stmt.setInt(2, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update specialization", e);
        }
    }

    @Override
    public Specialization getById(int id) {
        String sql = "SELECT * FROM specializations WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToSpecialization(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find specialization by id", e);
        }
    }

    @Override
    public List<Specialization> getAll() {
        String sql = "SELECT * FROM specializations ORDER BY id";
        List<Specialization> specializations = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                specializations.add(mapResultSetToSpecialization(rs));
            }
            return specializations;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all specializations", e);
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM specializations WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete specialization", e);
        }
    }

    private Specialization mapResultSetToSpecialization(ResultSet rs) throws SQLException {
        return new Specialization(
                rs.getInt("id"),
                rs.getString("name")
        );
    }
}