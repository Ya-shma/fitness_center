package ru.fitness.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.fitness.entities.Client;
import ru.fitness.repository.Client.ClientRepository;
import ru.fitness.repository.JDBC.ClientRepositoryJdbcImpl;

import java.io.IOException;

@WebServlet("/api/clients/*")
public class ClientServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private final ClientRepository clientRepo = new ClientRepositoryJdbcImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // GET /api/clients
                objectMapper.writeValue(resp.getWriter(), clientRepo.getAll());
            } else {
                // GET /api/clients/{id}
                String idStr = pathInfo.substring(1);
                int id = Integer.parseInt(idStr);
                Client client = clientRepo.getById(id);

                if (client != null) {
                    objectMapper.writeValue(resp.getWriter(), client);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Client not found");
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
            Client client = objectMapper.readValue(req.getReader(), Client.class);
            clientRepo.add(client);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            objectMapper.writeValue(resp.getWriter(),
                    java.util.Map.of("success", true, "id", client.getId()));
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
            Client client = objectMapper.readValue(req.getReader(), Client.class);

            clientRepo.update(id, client);

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

            boolean success = clientRepo.delete(id);

            if (success) {
                objectMapper.writeValue(resp.getWriter(),
                        java.util.Map.of("success", true));
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Client not found");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}