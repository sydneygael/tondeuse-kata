package org.application.adapters.rest;

import org.application.adapters.AppTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringJUnitConfig(classes = {TondeuseController.class, AppTestConfig.class})
@WebMvcTest(TondeuseController.class)
public class TondeuseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testMoveTondeuseWithOkResponse() throws Exception {
        // Données de la requête
        var requestBody = """
                {
                  "commands": ["LEFT", "ADVANCE", "RIGHT"],
                  "tondeuse": {
                    "position": {"positionX": 3, "positionY": 3},
                    "orientation": "NORTH"
                  },
                  "surface": {
                    "positionInitial": {"positionX": 0, "positionY": 0},
                    "hauteur": 10,
                    "largeur": 10
                  }
                }
                """;

        // Mock du résultat de MoveTondeusePort.handle()
        var mockResponse = "2 3 N";

        // Effectuer la requête POST et vérifier le résultat
        mockMvc.perform(MockMvcRequestBuilders.post("/api/tondeuse/command")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(mockResponse));
    }

    @Test
    public void testShouldThrowUnknownCommandException() throws Exception {
        // Given
        var requestBody = """
                {
                  "commands": ["L", "A", "R"],
                  "tondeuse": {
                    "position": {"positionX": 3, "positionY": 3},
                    "orientation": "NORTH"
                  },
                  "surface": {
                    "positionInitial": {"x": 0, "y": 0},
                    "hauteur": 10,
                    "largeur": 10
                  }
                }
                """;

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/tondeuse/command")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
