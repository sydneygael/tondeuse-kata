package org.domain.command.impl;

import org.domain.enums.OrientationEnum;
import org.domain.models.entities.Surface;
import org.domain.models.entities.Tondeuse;
import org.domain.models.valueobjects.Position;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AvancerCommandeTest {

    @Test
    void execute_WithInsideSurface_ShouldMoveTondeuseForward() {
        // Given
        var surface = Mockito.mock(Surface.class);
        when(surface.contientPosition(any())).thenReturn(true);

        var tondeuse = new Tondeuse(1, new Position(2, 1, OrientationEnum.NORTH));
        var avancerCommande = new AvancerCommande(tondeuse, surface);

        // When
        String result = avancerCommande.execute();

        // Then
        assertEquals("2 2 N", result);
        verify(surface, times(1)).contientPosition(any());
    }

    @Test
    void execute_WithInsideSurface_ShouldMoveTondeuseToSouth() {
        // Given
        var surface = Mockito.mock(Surface.class);
        when(surface.contientPosition(any())).thenReturn(true);

        var tondeuse = new Tondeuse(1, new Position(2, 2, OrientationEnum.SOUTH));
        var avancerCommande = new AvancerCommande(tondeuse, surface);

        // When
        var result = avancerCommande.execute();

        // Then
        assertEquals("2 1 S", result);
        verify(surface, times(1)).contientPosition(any());
    }

    @Test
    void execute_WithOutsideSurface_ShouldNotMoveTondeuse() {
        // Given
        var surface = Mockito.mock(Surface.class);
        when(surface.contientPosition(any())).thenReturn(false);

        var tondeuse = new Tondeuse(1, new Position(2, 3, OrientationEnum.SOUTH));
        var avancerCommande = new AvancerCommande(tondeuse, surface);

        // When
        var result = avancerCommande.execute();

        // Then
        assertEquals("2 3 S", result);
        verify(surface, times(1)).contientPosition(any());
    }
}
