package com.gmail.klausboeing.aws.api;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.gmail.klausboeing.aws.application.Service;
import com.gmail.klausboeing.aws.config.ProjectComponent;
import com.gmail.klausboeing.aws.util.aws.APIGatewayProxyRequestEvent;
import com.gmail.klausboeing.aws.util.aws.AbstractAPIGatewayEventHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.inject.Provider;

public class GetEntityEventHandler extends AbstractAPIGatewayEventHandler {

    @Inject
    ObjectMapper om;

    @Inject
    Service service;

    @Inject
    Provider<APIGatewayProxyResponseEvent> responseEventProvider;

    @Override
    protected APIGatewayProxyResponseEvent doHandleRequest(APIGatewayProxyRequestEvent event, Context context) {
        try {
            return responseEventProvider.get().withBody(om.writeValueAsString(service.findAll()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void inject(ProjectComponent projectComponent) {
        projectComponent.inject(this);
    }


}