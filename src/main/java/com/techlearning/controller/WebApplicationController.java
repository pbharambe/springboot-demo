package com.techlearning.controller;

import com.techlearning.response.GenericResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Welcome", description = "Welcome APIs")
@RestController
@Validated
public class WebApplicationController {

    @Operation(summary = "Welcome index Page.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return Welcome message with name", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))})
    })
    @GetMapping(value = "/index/{name}")
    public ResponseEntity<String> welcome(Model model,
                                          @NotBlank(message = "Name  must not be blank")
                                          @PathVariable("name") String name) {
        return new ResponseEntity<>("Welcome " + name + " !!", HttpStatus.OK);
    }

    @Operation(summary = "Index Page.")
    @GetMapping("/")
    public String index() {
        return "Welcome";
    }

    @GetMapping(value = "/greet/{name}")
    public ResponseEntity<GenericResponse<String>> genericWelcome(@PathVariable("name") String name) {
        if (name.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .header("custome-header", "value")
                    .body(GenericResponse.empty());
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .header("custome-header", "value")
                    .body(GenericResponse.success("Welcome " + name + " !!"));
        }
    }

}
