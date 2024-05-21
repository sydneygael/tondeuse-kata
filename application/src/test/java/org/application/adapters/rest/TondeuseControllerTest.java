package org.application.adapters.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.application.usescases.DeplacerTondeuse;
import org.domain.models.entities.SurfaceRectangle;
import org.domain.models.entities.Tondeuse;
import org.domain.models.valueobjects.Position;
import org.application.ports.input.MoveTondeusePort;
import org.application.ports.input.MoveTondeusePort.DeplacerTondeuseRequete;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.domain.enums.CommandEnum.*;
import static org.domain.enums.OrientationEnum.NORTH;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TondeuseController.class)
class TondeuseControllerTest {
  
  @Autowired
  private MockMvc mockMvc;
  
  @Autowired
  private ObjectMapper objectMapper;
  
  @MockBean
  private DeplacerTondeuse deplacerTondeuse;
  
  @Test
  void testMoveTondeuseWithOkResponse() throws Exception {
    var position = Position.of(3, 3, NORTH);
    var deplacerTondeuseRequete = new DeplacerTondeuseRequete(
        List.of(LEFT, ADVANCE, RIGHT),
        new Tondeuse(1, position),
        new SurfaceRectangle(position, 10, 10));
    when(deplacerTondeuse.traiter(deplacerTondeuseRequete))
        .thenReturn("2 3 N");
    
    mockMvc.perform(post("/api/tondeuse/command")
               .contentType(APPLICATION_JSON)
               .content(objectMapper.writeValueAsBytes(deplacerTondeuseRequete)))
           .andExpect(status().isOk())
           .andExpect(content().string("2 3 N"));
  }
  
  @Test
  void testShouldThrowUnknownCommandException() throws Exception {
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
    
    mockMvc.perform(post("/api/tondeuse/command")
               .contentType(APPLICATION_JSON)
               .content(requestBody))
           .andExpect(status().isBadRequest());
  }
}
