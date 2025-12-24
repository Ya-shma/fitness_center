package ru.fitness.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.fitness.entities.Specialization;
import ru.fitness.repository.Specialization.SpecializationRepository;
import ru.fitness.repository.JDBC.SpecializationRepositoryJdbcImpl;

import java.io.IOException;

@WebServlet("/api/specializations/*")
public class SpecializationServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private final SpecializationRepository specRepo = new SpecializationRepositoryJdbcImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                objectMapper.writeValue(resp.getWriter(), specRepo.getAll());
            } else {
                String idStr = pathInfo.substring(1);
                int id = Integer.parseInt(idStr);
                Specialization spec = specRepo.getById(id);

                if (spec != null) {
                    objectMapper.writeValue(resp.getWriter(), spec);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Specialization not found");
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
            Specialization spec = objectMapper.readValue(req.getReader(), Specialization.class);
            specRepo.add(spec);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            objectMapper.writeValue(resp.getWriter(),
                    java.util.Map.of("success", true, "id", spec.getId()));
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
            Specialization spec = objectMapper.readValue(req.getReader(), Specialization.class);

            specRepo.update(id, spec);

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

            boolean success = specRepo.delete(id);

            if (success) {
                objectMapper.writeValue(resp.getWriter(),
                        java.util.Map.of("success", true));
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Specialization not found");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}