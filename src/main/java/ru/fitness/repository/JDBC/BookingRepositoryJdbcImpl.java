package ru.fitness.repository.JDBC;

import ru.fitness.entities.Booking;
import ru.fitness.repository.Booking.BookingRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingRepositoryJdbcImpl extends JDBCBaseRepository implements BookingRepository {

    @Override
    public List<Booking> getBookingsByClient(int clientId) {
        String sql = "SELECT * FROM bookings WHERE client_id = ? ORDER BY booking_date";
        List<Booking> bookings = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
            return bookings;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find bookings by client", e);
        }
    }

    @Override
    public List<Booking> getBookingsByWorkout(int workoutId) {
        String sql = "SELECT * FROM bookings WHERE workout_id = ? ORDER BY booking_date";
        List<Booking> bookings = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, workoutId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
            return bookings;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find bookings by workout", e);
        }
    }

    @Override
    public List<Booking> getBookingsByStatus(String status) {
        String sql = "SELECT * FROM bookings WHERE status = ? ORDER BY booking_date";
        List<Booking> bookings = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
            return bookings;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find bookings by status", e);
        }
    }

    @Override
    public List<Booking> getBookingsByDateRange(LocalDateTime start, LocalDateTime end) {
        String sql = "SELECT * FROM bookings WHERE booking_date BETWEEN ? AND ? ORDER BY booking_date";
        List<Booking> bookings = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(start));
            stmt.setTimestamp(2, Timestamp.valueOf(end));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
            return bookings;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find bookings by date range", e);
        }
    }

    @Override
    public List<Booking> getActiveBookings() {
        return getBookingsByStatus("active");
    }

    @Override
    public boolean cancelBooking(int bookingId) {
        String sql = "UPDATE bookings SET status = 'cancelled' WHERE id = ? AND status = 'active'";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookingId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to cancel booking", e);
        }
    }

    @Override
    public boolean completeBooking(int bookingId) {
        String sql = "UPDATE bookings SET status = 'completed' WHERE id = ? AND status = 'active'";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookingId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to complete booking", e);
        }
    }

    @Override
    public int getBookingsCountByWorkout(int workoutId) {
        String sql = "SELECT COUNT(*) FROM bookings WHERE workout_id = ? AND status = 'active'";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, workoutId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to get bookings count by workout", e);
        }
    }

    @Override
    public boolean hasActiveBooking(int clientId, int workoutId) {
        String sql = "SELECT COUNT(*) FROM bookings WHERE client_id = ? AND workout_id = ? AND status = 'active'";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clientId);
            stmt.setInt(2, workoutId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to check active booking", e);
        }
    }

    @Override
    public void add(Booking object) {
        String sql = "INSERT INTO bookings (client_id, workout_id, booking_date, status, created_at) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, object.getClientId());
            stmt.setInt(2, object.getWorkoutId());
            stmt.setTimestamp(3, Timestamp.valueOf(object.getBookingDate()));
            stmt.setString(4, object.getStatus());
            stmt.setTimestamp(5, Timestamp.valueOf(object.getCreatedAt() != null ? object.getCreatedAt() : LocalDateTime.now()));
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    object.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to add booking", e);
        }
    }

    @Override
    public void update(int id, Booking newObject) {
        String sql = "UPDATE bookings SET client_id = ?, workout_id = ?, booking_date = ?, status = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newObject.getClientId());
            stmt.setInt(2, newObject.getWorkoutId());
            stmt.setTimestamp(3, Timestamp.valueOf(newObject.getBookingDate()));
            stmt.setString(4, newObject.getStatus());
            stmt.setInt(5, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update booking", e);
        }
    }

    @Override
    public Booking getById(int id) {
        String sql = "SELECT * FROM bookings WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToBooking(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find booking by id", e);
        }
    }

    @Override
    public List<Booking> getAll() {
        String sql = "SELECT * FROM bookings ORDER BY booking_date";
        List<Booking> bookings = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
            return bookings;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all bookings", e);
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM bookings WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete booking", e);
        }
    }

    private Booking mapResultSetToBooking(ResultSet rs) throws SQLException {
        Booking booking = new Booking(
                rs.getInt("id"),
                rs.getInt("client_id"),
                rs.getInt("workout_id"),
                rs.getTimestamp("booking_date").toLocalDateTime(),
                rs.getString("status")
        );

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            booking.setCreatedAt(createdAt.toLocalDateTime());
        }

        return booking;
    }

    @Override
    public void removeAll() {
        String sql = "DELETE FROM bookings";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove all bookings", e);
        }
    }
}