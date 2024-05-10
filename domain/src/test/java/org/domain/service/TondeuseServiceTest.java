package org.domain.service;

import org.domain.enums.CommandEnum;
import org.domain.exceptions.UnknownCommandException;
import org.domain.models.entities.SurfaceRectangle;
import org.domain.models.entities.Tondeuse;
import org.domain.models.valueobjects.Position;
import org.domain.ports.input.MoveTondeusePort.TondeuseMoveRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static java.util.Collections.singletonList;
import static org.domain.enums.OrientationEnum.NORTH;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TondeuseServiceTest {
  
  private TondeuseService tondeuseService;
  
  @BeforeEach
  void setUp() {
    tondeuseService = new TondeuseService();
  }
  
  @ParameterizedTest
  @EnumSource(CommandEnum.class)
  void testHandle(CommandEnum command) throws UnknownCommandException {
    // Given
    var position = Position.of(0, 0, NORTH);
    var tondeuse = new Tondeuse(1, position);
    var surface = new SurfaceRectangle(position, 5, 5);
    
    // When
    var result = tondeuseService.handle(new TondeuseMoveRequest(singletonList(command), tondeuse, surface));
    
    // Then
    switch (command) {
      case ADVANCE:
        assertEquals("0 1 N", result);
        break;
      case LEFT:
        assertEquals("0 0 W", result);
        break;
      case RIGHT:
        assertEquals("0 0 E", result);
        break;
      default:
        break;
    }
  }
}
