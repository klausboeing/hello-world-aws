package com.gmail.klausboeing.aws.api;


import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Module
public class APIModule {

    @Singleton
    @Provides
    ObjectMapper objectMapper() {
        ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule());

        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return om;
    }

    @Provides
    APIGatewayProxyResponseEvent responseEvent() {
        return new APIGatewayProxyResponseEvent()
                .withHeaders(defaultHeaders())
                .withStatusCode(200);
    }

    private Map defaultHeaders() {
        Map result = new HashMap();
        result.put("Content-Type", "application/json");
        result.put("Access-Control-Allow-Origin", "*");
        return result;
    }

}
