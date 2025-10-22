package org.digitalstack.logistics.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Getter
public class ApplicationData {

    private final LocalDate currentDate = LocalDate.of(2021, 12, 15);

}
