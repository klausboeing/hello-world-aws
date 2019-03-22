package com.gmail.klausboeing.aws.api;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.gmail.klausboeing.aws.application.Service;
import com.gmail.klausboeing.aws.config.ProjectComponent;
import com.gmail.klausboeing.aws.model.Entity;
import com.gmail.klausboeing.aws.util.aws.APIGatewayProxyRequestEvent;
import com.gmail.klausboeing.aws.util.aws.AbstractAPIGatewayEventHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.inject.Provider;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UploadURLRequestEventHandler extends AbstractAPIGatewayEventHandler {

    @Inject
    ObjectMapper om;

    @Inject
    Service service;

    @Inject
    Provider<APIGatewayProxyResponseEvent> responseEventProvider;

    @Override
    protected APIGatewayProxyResponseEvent doHandleRequest(APIGatewayProxyRequestEvent event, Context context) {
        try {

            Entity entity = om.readValue(event.getBody(), Entity.class);

            Map<String, Object> data = new HashMap<>();
            data.put("url", service.generateUploadURL(entity));

            return responseEventProvider.get().withBody(om.writeValueAsString(data));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void inject(ProjectComponent projectComponent) {
        projectComponent.inject(this);
    }

}
