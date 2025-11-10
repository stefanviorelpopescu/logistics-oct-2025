package org.digitalstack.logistics.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record DestinationDto(
        @NotNull
        Long id,
        @NotEmpty (message = "Destination name should not be empty!")
        String name,
        @Min (value = 1, message = "Distance should be > 0!")
        Integer distance) {

        @JsonIgnore
        @AssertTrue (message = "Name should be longer than distance")
        public boolean isNameValid() {
                return name.length() > distance;
        }

}
