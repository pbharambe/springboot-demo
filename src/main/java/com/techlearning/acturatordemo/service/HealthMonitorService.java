package com.techlearning.acturatordemo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class HealthMonitorService {

    public String getHttpStatusCode(String url) {
        return RestClient.create().get().uri(url).retrieve().toEntity(String.class).getStatusCode().toString();

        /*try {
            int statusCode = HttpClientBuilder.create().build().execute(new HttpGet(url)).getStatusLine().getStatusCode();
            if (statusCode == 200) {
                return HttpStatus.OK.toString();
            }
        } catch (Exception e) {
        }
        return HttpStatus.NOT_FOUND.toString();*/
    }
}
