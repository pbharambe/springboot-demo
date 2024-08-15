package com.techlearning.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Welcome", description = "Welcome APIs")
@RestController
public class WebApplicationController {

    @Operation(summary = "Welcome index Page.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return Welcome message with name",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) })
    })
    @GetMapping(value = "/index/{name}")
    public ResponseEntity<String> welcome(Model model, @PathVariable("name") String name) {
        return new ResponseEntity<>("Welcome " + name + " !!", HttpStatus.OK);
    }

    @Operation(summary = "Index Page.")
    @GetMapping("/")
    public String index() {
        return "Welcome";
    }
}
