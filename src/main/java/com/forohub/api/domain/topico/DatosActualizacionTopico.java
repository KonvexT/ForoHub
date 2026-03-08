package com.forohub.api.domain.topico;

import jakarta.validation.constraints.NotNull;

public record DatosActualizacionTopico(
        @NotNull Long id,
        String titulo,
        String mensaje,
        Long autorId,
        Long cursoId
) {
}
