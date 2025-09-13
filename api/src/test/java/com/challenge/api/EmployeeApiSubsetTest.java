package com.challenge.api;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EmployeeApiSubsetTest {

    private static final String BASE = "/api/v1/employee";
    private static final String SECURITY_HEADER = "SECURITY-KEY";
    private static final String SECURITY_VALUE = "key";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    // ---------- helpers ----------
    private String createEmployee(String first, String last, String email) throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("firstName", first);
        body.put("lastName", last);
        body.put("email", email);

        String resp = mockMvc.perform(post(BASE)
                        .header(SECURITY_HEADER, SECURITY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid", not(blankOrNullString())))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return om.readTree(resp).get("uuid").asText();
    }

    @Test
    void incorrectMiddlewareKey_returns401() throws Exception {
        mockMvc.perform(get(BASE).header(SECURITY_HEADER, "WRONG")).andExpect(status().isUnauthorized());
    }

    @Test
    void getAll_returns200_andArray() throws Exception {
        createEmployee("Abhi", "Anna", "abhianna@gmail.com");
        createEmployee("Taj", "Bour", "tajBour@yahoo.com");

        mockMvc.perform(get(BASE).header(SECURITY_HEADER, SECURITY_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))));
    }

    @Test
    void createNewEmployee_returns200_withUuidAndEmail() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("firstName", "Alan");
        body.put("lastName", "Turing");
        body.put("email", "alan@example.com");
        body.put("jobTitle", "Engineer");
        body.put("salary", 150000);
        body.put("age", 36);

        mockMvc.perform(post(BASE)
                        .header(SECURITY_HEADER, SECURITY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid", not(blankOrNullString())))
                .andExpect(jsonPath("$.email", is("alan@example.com")));
    }

    @Test
    void getEmployeeById_returns200() throws Exception {
        String id = createEmployee("Nikola", "Jokic", "jokic@example.com");

        mockMvc.perform(get(BASE + "/" + id).header(SECURITY_HEADER, SECURITY_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid", is(id)))
                .andExpect(jsonPath("$.email", is("nikola@example.com")));
    }

    @Test
    void missingSecurityKey_returns401() throws Exception {
        mockMvc.perform(get(BASE)).andExpect(status().isUnauthorized());
    }

    @Test
    void duplicateEmail_returns409() throws Exception {
        createEmployee("Giannis", "Ante", "ante@example.com");

        Map<String, Object> dup = new HashMap<>();
        dup.put("firstName", "ante");
        dup.put("lastName", "L");
        dup.put("email", "ante@example.com");

        mockMvc.perform(post(BASE)
                        .header(SECURITY_HEADER, SECURITY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dup)))
                .andExpect(status().isConflict());
    }

    @Test
    void terminationBeforeHire_returns409() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("firstName", "Time");
        body.put("lastName", "Traveler");
        body.put("email", "time@example.com");
        body.put("contractHireDate", "2020-01-02T00:00:00Z");
        body.put("contractTerminationDate", "2019-12-31T00:00:00Z");

        mockMvc.perform(post(BASE)
                        .header(SECURITY_HEADER, SECURITY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(body)))
                .andExpect(status().isConflict());
    }
}
