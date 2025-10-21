package org.digitalstack.logistics.controller;

import lombok.RequiredArgsConstructor;
import org.digitalstack.logistics.dto.DestinationDto;
import org.digitalstack.logistics.service.DestinationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/destinations")
@RequiredArgsConstructor
public class DestinationController {

    private final DestinationService destinationService;

    @GetMapping
    public ResponseEntity<List<DestinationDto>> getAllDestinations() {
        return new ResponseEntity<>(destinationService.getAllDestinations(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DestinationDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(destinationService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        destinationService.deleteById(id);
    }

}
