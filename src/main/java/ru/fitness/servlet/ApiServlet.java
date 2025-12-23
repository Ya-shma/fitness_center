package ru.fitness.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.fitness.entities.*;
import ru.fitness.repository.*;
import ru.fitness.repository.Booking.BookingRepository;
import ru.fitness.repository.Client.ClientRepository;
import ru.fitness.repository.Coach.CoachRepository;
import ru.fitness.repository.Specialization.SpecializationRepository;
import ru.fitness.repository.Workout.WorkoutRepository;
import ru.fitness.repository.JDBC.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@WebServlet("/api/*")
public class ApiServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private final BookingRepository bookingRepo = new BookingRepositoryJdbcImpl();
    private final ClientRepository clientRepo = new ClientRepositoryJdbcImpl();
    private final CoachRepository coachRepo = new CoachRepositoryJdbcImpl();
    private final SpecializationRepository specRepo = new SpecializationRepositoryJdbcImpl();
    private final WorkoutRepository workoutRepo = new WorkoutRepositoryJdbcImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();
        resp.setContentType("application/json");

        try {
            if (path == null || path.equals("/")) {
                Map<String, String> info = new HashMap<>();
                info.put("message", "Fitness Center Admin API");
                info.put("version", "1.0");
                writeJson(resp, info);
                return;
            }

            String[] parts = path.substring(1).split("/");
            String entity = parts[0];

            switch (entity) {
                case "clients":
                    if (parts.length == 1) {
                        // GET /api/clients
                        writeJson(resp, clientRepo.getAll());
                    } else {
                        // GET /api/clients/{id}
                        int id = Integer.parseInt(parts[1]);
                        writeJson(resp, clientRepo.getById(id));
                    }
                    break;

                case "coaches":
                    if (parts.length == 1) {
                        // GET /api/coaches
                        writeJson(resp, coachRepo.getAll());
                    } else {
                        // GET /api/coaches/{id}
                        int id = Integer.parseInt(parts[1]);
                        writeJson(resp, coachRepo.getById(id));
                    }
                    break;

                case "workouts":
                    if (parts.length == 1) {
                        // GET /api/workouts
                        writeJson(resp, workoutRepo.getAll());
                    } else {
                        // GET /api/workouts/{id}
                        int id = Integer.parseInt(parts[1]);
                        writeJson(resp, workoutRepo.getById(id));
                    }
                    break;

                case "specializations":
                    if (parts.length == 1) {
                        // GET /api/specializations
                        writeJson(resp, specRepo.getAll());
                    } else {
                        // GET /api/specializations/{id}
                        int id = Integer.parseInt(parts[1]);
                        writeJson(resp, specRepo.getById(id));
                    }
                    break;

                case "bookings":
                    if (parts.length == 1) {
                        // GET /api/bookings
                        writeJson(resp, bookingRepo.getAll());
                    } else {
                        // GET /api/bookings/{id}
                        int id = Integer.parseInt(parts[1]);
                        writeJson(resp, bookingRepo.getById(id));
                    }
                    break;

                default:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();
        resp.setContentType("application/json");

        try {
            if (path == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String entity = path.substring(1);

            switch (entity) {
                case "clients":
                    Client client = objectMapper.readValue(req.getReader(), Client.class);
                    clientRepo.add(client);
                    writeJson(resp, Map.of("success", true, "id", client.getId()));
                    break;

                case "coaches":
                    Coach coach = objectMapper.readValue(req.getReader(), Coach.class);
                    coachRepo.add(coach);
                    writeJson(resp, Map.of("success", true, "id", coach.getId()));
                    break;

                case "workouts":
                    Workout workout = objectMapper.readValue(req.getReader(), Workout.class);
                    workoutRepo.add(workout);
                    writeJson(resp, Map.of("success", true, "id", workout.getId()));
                    break;

                case "specializations":
                    Specialization spec = objectMapper.readValue(req.getReader(), Specialization.class);
                    specRepo.add(spec);
                    writeJson(resp, Map.of("success", true, "id", spec.getId()));
                    break;

                case "bookings":
                    Booking booking = objectMapper.readValue(req.getReader(), Booking.class);
                    bookingRepo.add(booking);
                    writeJson(resp, Map.of("success", true, "id", booking.getId()));
                    break;

                default:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();

        try {
            if (path == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String[] parts = path.substring(1).split("/");
            if (parts.length != 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String entity = parts[0];
            int id = Integer.parseInt(parts[1]);

            switch (entity) {
                case "clients":
                    Client client = objectMapper.readValue(req.getReader(), Client.class);
                    clientRepo.update(id, client);
                    writeJson(resp, Map.of("success", true));
                    break;

                case "coaches":
                    Coach coach = objectMapper.readValue(req.getReader(), Coach.class);
                    coachRepo.update(id, coach);
                    writeJson(resp, Map.of("success", true));
                    break;

                case "workouts":
                    Workout workout = objectMapper.readValue(req.getReader(), Workout.class);
                    workoutRepo.update(id, workout);
                    writeJson(resp, Map.of("success", true));
                    break;

                case "specializations":
                    Specialization spec = objectMapper.readValue(req.getReader(), Specialization.class);
                    specRepo.update(id, spec);
                    writeJson(resp, Map.of("success", true));
                    break;

                case "bookings":
                    Booking booking = objectMapper.readValue(req.getReader(), Booking.class);
                    bookingRepo.update(id, booking);
                    writeJson(resp, Map.of("success", true));
                    break;

                default:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();

        try {
            if (path == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String[] parts = path.substring(1).split("/");
            if (parts.length != 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String entity = parts[0];
            int id = Integer.parseInt(parts[1]);

            boolean success = false;

            switch (entity) {
                case "clients":
                    success = clientRepo.delete(id);
                    break;
                case "coaches":
                    success = coachRepo.delete(id);
                    break;
                case "workouts":
                    success = workoutRepo.delete(id);
                    break;
                case "specializations":
                    success = specRepo.delete(id);
                    break;
                case "bookings":
                    success = bookingRepo.delete(id);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
            }

            writeJson(resp, Map.of("success", success));

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void writeJson(HttpServletResponse resp, Object data) throws IOException {
        objectMapper.writeValue(resp.getWriter(), data);
    }
}