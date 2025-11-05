package org.digitalstack.logistics.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Getter
public class ApplicationData {

    private LocalDate currentDate = LocalDate.of(2021, 12, 14);
    private Long companyProfit = 0L;

    public LocalDate incrementAndGetDate() {
        currentDate = currentDate.plusDays(1);
        return currentDate;
    }

    public synchronized void incrementProfit(Long amount) {
        companyProfit += amount;
    }

}
