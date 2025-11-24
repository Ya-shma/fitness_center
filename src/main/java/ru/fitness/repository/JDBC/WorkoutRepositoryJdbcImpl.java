package ru.fitness.repository.JDBC;

import ru.fitness.entities.Workout;
import ru.fitness.repository.Workout.WorkoutRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WorkoutRepositoryJdbcImpl extends JDBCBaseRepository implements WorkoutRepository {

    @Override
    public List<Workout> getWorkoutsByCoach(int coachId) {
        String sql = "SELECT * FROM workouts WHERE coach_id = ? ORDER BY date_time";
        List<Workout> workouts = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, coachId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                workouts.add(mapResultSetToWorkout(rs));
            }
            return workouts;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find workouts by coach", e);
        }
    }

    @Override
    public List<Workout> getWorkoutsByName(String name) {
        String sql = "SELECT * FROM workouts WHERE name ILIKE ? ORDER BY date_time";
        List<Workout> workouts = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                workouts.add(mapResultSetToWorkout(rs));
            }
            return workouts;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find workouts by name", e);
        }
    }

    @Override
    public void add(Workout object) {
        String sql = "INSERT INTO workouts (name, date_time, duration_minutes, max_capacity, coach_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, object.getName());
            stmt.setTimestamp(2, Timestamp.valueOf(object.getDateTime()));
            stmt.setInt(3, object.getDurationMinutes());
            stmt.setInt(4, object.getMaxCapacity());
            stmt.setInt(5, object.getCoachId());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    object.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to add workout", e);
        }
    }

    @Override
    public void update(int id, Workout newObject) {
        String sql = "UPDATE workouts SET name = ?, date_time = ?, duration_minutes = ?, max_capacity = ?, coach_id = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newObject.getName());
            stmt.setTimestamp(2, Timestamp.valueOf(newObject.getDateTime()));
            stmt.setInt(3, newObject.getDurationMinutes());
            stmt.setInt(4, newObject.getMaxCapacity());
            stmt.setInt(5, newObject.getCoachId());
            stmt.setInt(6, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update workout", e);
        }
    }

    @Override
    public Workout getById(int id) {
        String sql = "SELECT * FROM workouts WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToWorkout(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find workout by id", e);
        }
    }

    @Override
    public List<Workout> getAll() {
        String sql = "SELECT * FROM workouts ORDER BY date_time";
        List<Workout> workouts = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                workouts.add(mapResultSetToWorkout(rs));
            }
            return workouts;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all workouts", e);
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM workouts WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete workout", e);
        }
    }

    private Workout mapResultSetToWorkout(ResultSet rs) throws SQLException {
        return new Workout(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getTimestamp("date_time").toLocalDateTime(),
                rs.getInt("duration_minutes"),
                rs.getInt("max_capacity"),
                rs.getInt("coach_id")
        );
    }

//    @Override
//    public void removeAll() {
//        String sql = "DELETE FROM workouts";
//
//        try (Connection conn = getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Failed to remove all workouts", e);
//        }
//    }
}