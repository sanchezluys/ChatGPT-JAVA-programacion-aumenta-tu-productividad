package med.voll.api.domain.consulta.validaciones;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import javax.validation.ValidationException;

import med.voll.api.domain.consulta.dto.DatosAgendarConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class HorarioDeFuncionamientoClinicaTest {

    @InjectMocks
    private HorarioDeFuncionamientoClinica validador;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deberiaLanzarExcepcionSiEsDomingo() {
        // Arrange
        var datos = mock(DatosAgendarConsulta.class);
        when(datos.fecha()).thenReturn(LocalDateTime.of(2024, 8, 25, 10, 0)); // Domingo

        // Act & Assert
        assertThrows(ValidationException.class, () -> validador.validar(datos),
                "El horario de atención de la clínica es de lunes a sábado, de 07:00 a 19:00 horas");
    }

    @Test
    public void deberiaLanzarExcepcionSiEsAntesDeApertura() {
        // Arrange
        var datos = mock(DatosAgendarConsulta.class);
        when(datos.fecha()).thenReturn(LocalDateTime.of(2024, 8, 23, 6, 0)); // Viernes antes de las 07:00

        // Act & Assert
        assertThrows(ValidationException.class, () -> validador.validar(datos),
                "El horario de atención de la clínica es de lunes a sábado, de 07:00 a 19:00 horas");
    }

    @Test
    public void deberiaLanzarExcepcionSiEsDespuesDeCierre() {
        // Arrange
        var datos = mock(DatosAgendarConsulta.class);
        when(datos.fecha()).thenReturn(LocalDateTime.of(2024, 8, 23, 20, 0)); // Viernes después de las 19:00

        // Act & Assert
        assertThrows(ValidationException.class, () -> validador.validar(datos),
                "El horario de atención de la clínica es de lunes a sábado, de 07:00 a 19:00 horas");
    }

    @Test
    public void noDeberiaLanzarExcepcionSiEsHorarioValido() {
        // Arrange
        var datos = mock(DatosAgendarConsulta.class);
        when(datos.fecha()).thenReturn(LocalDateTime.of(2024, 8, 23, 10, 0)); // Viernes dentro del horario

        // Act & Assert
        validador.validar(datos);
    }
}
