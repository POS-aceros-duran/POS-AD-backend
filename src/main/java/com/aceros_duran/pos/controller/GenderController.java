package com.aceros_duran.pos.controller;

import com.aceros_duran.pos.dto.request.GenderRequest;
import com.aceros_duran.pos.dto.response.GenderResponse;
import com.aceros_duran.pos.service.GenderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Genders")
@RestController
@RequestMapping("/pos-duran/api/v1/genders")
public class GenderController {

        private final GenderService genderService;

        public GenderController(GenderService genderService) {
                this.genderService = genderService;
        }

        @PostMapping
        @Operation(summary = "Crea un género")
        @ApiResponse(responseCode = "200", description = "Se ha creado el genero")
        public ResponseEntity<GenderResponse> createGender(@Valid @RequestBody GenderRequest genderRequest) {

                GenderResponse createdGender = genderService.createGender(genderRequest);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdGender);
        }

        @Operation(summary = "Se obtiene un género dado un Id")
        @ApiResponse(responseCode = "200", description = "Devuelve el género en caso de que exista.")
        @GetMapping("/{id}")
        public ResponseEntity<GenderResponse> getGenderById(@Valid @PathVariable Long id) {
                GenderResponse genderResponse = genderService.getGenderById(id);
                return ResponseEntity.ok(genderResponse);
        }

        @GetMapping
        @Operation(summary = "Obtiene todo el listado de géneros")
        @ApiResponse(responseCode = "200")
        public ResponseEntity<List<GenderResponse>> getAllGenders() {
                List<GenderResponse> allGenders = genderService.getAllGenders();
                return ResponseEntity.ok(allGenders);
        }

        @PutMapping("/{id}")
        @Operation(summary = "Actualiza un género")
        @ApiResponse(responseCode = "200")
        public ResponseEntity<GenderResponse> updateGender(@Valid @PathVariable Long id,
                        @Valid @RequestBody GenderRequest updatedGenderRequest) {
                GenderResponse updatedGender = genderService.updateGender(id, updatedGenderRequest);
                return ResponseEntity.ok(updatedGender);
        }
}
