package ru.fitness.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.fitness.entities.Workout;
import ru.fitness.repository.Workout.WorkoutRepository;
import ru.fitness.repository.JDBC.WorkoutRepositoryJdbcImpl;

import java.io.IOException;

@WebServlet("/api/workouts/*")
public class WorkoutServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private final WorkoutRepository workoutRepo = new WorkoutRepositoryJdbcImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                objectMapper.writeValue(resp.getWriter(), workoutRepo.getAll());
            } else {
                String idStr = pathInfo.substring(1);
                int id = Integer.parseInt(idStr);
                Workout workout = workoutRepo.getById(id);

                if (workout != null) {
                    objectMapper.writeValue(resp.getWriter(), workout);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Workout not found");
                }
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        try {
            Workout workout = objectMapper.readValue(req.getReader(), Workout.class);
            workoutRepo.add(workout);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            objectMapper.writeValue(resp.getWriter(),
                    java.util.Map.of("success", true, "id", workout.getId()));
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is required");
            return;
        }

        try {
            String idStr = pathInfo.substring(1);
            int id = Integer.parseInt(idStr);
            Workout workout = objectMapper.readValue(req.getReader(), Workout.class);

            workoutRepo.update(id, workout);

            objectMapper.writeValue(resp.getWriter(),
                    java.util.Map.of("success", true));
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is required");
            return;
        }

        try {
            String idStr = pathInfo.substring(1);
            int id = Integer.parseInt(idStr);

            boolean success = workoutRepo.delete(id);

            if (success) {
                objectMapper.writeValue(resp.getWriter(),
                        java.util.Map.of("success", true));
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Workout not found");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}