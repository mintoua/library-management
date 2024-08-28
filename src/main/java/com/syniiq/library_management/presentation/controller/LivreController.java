package com.syniiq.library_management.presentation.controller;

import com.syniiq.library_management.application.dto.Livre.LivreDTO;
import com.syniiq.library_management.application.dto.Livre.command.save.SaveLivreRequest;
import com.syniiq.library_management.application.dto.Livre.command.save.SaveLivreResponse;
import com.syniiq.library_management.domain.service.LivreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/livres")
@Tag(name = "Livre APIs")
public class LivreController {
    private final LivreService livreService;
    public LivreController(final LivreService livreService) {
        this.livreService = livreService;
    }


    @GetMapping("")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve!")
    })
    @Operation(summary = "Get Books", description = "return books ")
    public List<LivreDTO> getAll() {
        return this.livreService.getLivres();
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve!")
    })
    @Operation(summary = "Get Books", description = "return a book ")
    public LivreDTO getById(@PathVariable Long id) {
        return this.livreService.getLivreById(id);
    }

    @PostMapping("")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "This already exist!"),
            @ApiResponse(responseCode = "201", description = "Successfully saved!"),
            @ApiResponse(responseCode = "400", description = "Fields validation failed!")
    })
    public SaveLivreResponse add(@Valid @RequestBody SaveLivreRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return createErrorResponse(result);
        }
        return this.livreService.saveLivre(request);
    }

    @DeleteMapping("{id}")
    @ResponseBody
    @Operation(summary = "Delete an Book by id", description = "return livre deleted successfully")

    public ResponseEntity<String> delete(@PathVariable Long id) {
        this.livreService.deleteLivre(id);
        return ResponseEntity.ok("Livre with id " + id + " deleted.");
    }


    private SaveLivreResponse createErrorResponse(BindingResult result) {
        SaveLivreResponse response = new SaveLivreResponse();
        response.setMessage("Validation errors: " + result.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                .reduce((msg1, msg2) -> msg1 + ", " + msg2).orElse(""));
        return response;
    }
}
