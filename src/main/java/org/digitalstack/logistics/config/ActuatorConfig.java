package org.digitalstack.logistics.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ActuatorConfig implements InfoContributor {

    private final ApplicationData applicationData;

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, String> userDetails = new HashMap<>();
        userDetails.put("Current Date", applicationData.getCurrentDate().toString());
        userDetails.put("Company Profit", applicationData.getCompanyProfit().toString());

        builder.withDetail("Company Info", userDetails);
    }
}
