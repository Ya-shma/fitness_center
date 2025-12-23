package ru.fitness.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.fitness.entities.Coach;
import ru.fitness.repository.Coach.CoachRepository;
import ru.fitness.repository.JDBC.CoachRepositoryJdbcImpl;

import java.io.IOException;

@WebServlet("/api/coaches/*")
public class CoachServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private final CoachRepository coachRepo = new CoachRepositoryJdbcImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // GET /api/coaches
                objectMapper.writeValue(resp.getWriter(), coachRepo.getAll());
            } else {
                // GET /api/coaches/{id}
                String idStr = pathInfo.substring(1);
                int id = Integer.parseInt(idStr);
                Coach coach = coachRepo.getById(id);

                if (coach != null) {
                    objectMapper.writeValue(resp.getWriter(), coach);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Coach not found");
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
            Coach coach = objectMapper.readValue(req.getReader(), Coach.class);
            coachRepo.add(coach);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            objectMapper.writeValue(resp.getWriter(),
                    java.util.Map.of("success", true, "id", coach.getId()));
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
            Coach coach = objectMapper.readValue(req.getReader(), Coach.class);

            coachRepo.update(id, coach);

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

            boolean success = coachRepo.delete(id);

            if (success) {
                objectMapper.writeValue(resp.getWriter(),
                        java.util.Map.of("success", true));
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Coach not found");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}