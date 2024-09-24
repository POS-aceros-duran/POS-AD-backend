package com.aceros_duran.pos.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenderRequest {
    @Schema(description = "Id opcional (sólo si se está creando.")
    private Long idGender;
    @Schema(description = "Genero a insertar")
    @NotNull(message = "The field gender can't be null")
    @NotBlank(message = "The field gender can't be blank")
    private String gender;
}
