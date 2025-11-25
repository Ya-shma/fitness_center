package ru.fitness.repository.JDBC;

import ru.fitness.entities.Coach;
import ru.fitness.entities.Specialization;
import ru.fitness.repository.Coach.CoachRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoachRepositoryJdbcImpl extends JDBCRepository implements CoachRepository {

    @Override
    public Coach getCoachByName(String name) {
        String sql = """
            SELECT c.*, s.id as spec_id, s.name as spec_name 
            FROM coaches c 
            JOIN specializations s ON c.specialization_id = s.id 
            WHERE c.full_name = ?
            """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToCoach(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find coach by name", e);
        }
    }

    @Override
    public List<Coach> getCoachBySpecialization(int specializationId) {
        String sql = """
            SELECT c.*, s.id as spec_id, s.name as spec_name 
            FROM coaches c 
            JOIN specializations s ON c.specialization_id = s.id 
            WHERE c.specialization_id = ?
            """;
        List<Coach> coaches = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, specializationId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                coaches.add(mapResultSetToCoach(rs));
            }
            return coaches;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find coaches by specialization", e);
        }
    }

    @Override
    public void add(Coach object) {
        String sql = "INSERT INTO coaches (full_name, specialization_id) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, object.getFullName());
            stmt.setInt(2, object.getSpecialization().getId());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    object.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to add coach", e);
        }
    }

//    @Override
//    public void removeAll() {
//        String sql = "DELETE FROM coaches";
//
//        try (Connection conn = getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Failed to remove all coaches", e);
//        }
//    }

    @Override
    public void update(int id, Coach newObject) {
        String sql = "UPDATE coaches SET full_name = ?, specialization_id = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newObject.getFullName());
            stmt.setInt(2, newObject.getSpecialization().getId());
            stmt.setInt(3, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update coach", e);
        }
    }

    @Override
    public Coach getById(int id) {
        String sql = """
            SELECT c.*, s.id as spec_id, s.name as spec_name 
            FROM coaches c 
            JOIN specializations s ON c.specialization_id = s.id 
            WHERE c.id = ?
            """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToCoach(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find coach by id", e);
        }
    }

    @Override
    public List<Coach> getAll() {
        String sql = """
            SELECT c.*, s.id as spec_id, s.name as spec_name 
            FROM coaches c 
            JOIN specializations s ON c.specialization_id = s.id 
            ORDER BY c.id
            """;
        List<Coach> coaches = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                coaches.add(mapResultSetToCoach(rs));
            }
            return coaches;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all coaches", e);
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM coaches WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete coach", e);
        }
    }

    private Coach mapResultSetToCoach(ResultSet rs) throws SQLException {
        Specialization specialization = new Specialization(
                rs.getInt("spec_id"),
                rs.getString("spec_name")
        );

        return new Coach(
                rs.getInt("id"),
                rs.getString("full_name"),
                specialization
        );
    }
}