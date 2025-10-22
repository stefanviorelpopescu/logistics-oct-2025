package org.digitalstack.logistics.controller;

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
