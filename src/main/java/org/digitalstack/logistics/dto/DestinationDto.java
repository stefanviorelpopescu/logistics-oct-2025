package org.digitalstack.logistics.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record DestinationDto(
        @Schema(example = "1")
        @NotNull
        Long id,
        @Schema(example = "Ploiesti")
        @NotEmpty (message = "Destination name should not be empty!")
        String name,
        @Schema(example = "5")
        @Min (value = 1, message = "Distance should be > 0!")
        Integer distance) {

        @JsonIgnore
        @AssertTrue (message = "Name should be longer than distance")
        public boolean isNameValid() {
                return name.length() > distance;
        }

}
