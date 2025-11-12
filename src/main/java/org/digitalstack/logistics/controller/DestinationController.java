package org.digitalstack.logistics.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.digitalstack.logistics.dto.AddDestinationDto;
import org.digitalstack.logistics.dto.DestinationDto;
import org.digitalstack.logistics.service.DestinationService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/destinations")
@RequiredArgsConstructor
public class DestinationController {

    private final DestinationService destinationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<DestinationDto> getAllDestinations() {
        return destinationService.getAllDestinations();
    }

    @Operation(summary = "Get a destination by id", description = "Returns a destination as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved",
                        content = {@Content(mediaType = "application/json",
                                            schema = @Schema(implementation = DestinationDto.class)
                                            )}),
            @ApiResponse(responseCode = "404", description = "Not found - The destination was not found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public DestinationDto getById(@PathVariable Long id) {
        return destinationService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        destinationService.deleteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public DestinationDto addDestination(@RequestBody @Validated AddDestinationDto request) {
        return destinationService.addDestination(request);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public DestinationDto editDestination(@RequestBody @Validated DestinationDto request) {
        return destinationService.editDestination(request);
    }

}
