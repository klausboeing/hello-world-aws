package com.gmail.klausboeing.aws.util.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.gmail.klausboeing.aws.config.DaggerProjectComponent;
import com.gmail.klausboeing.aws.config.ProjectComponent;

public abstract class AbstractAPIGatewayEventHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private ProjectComponent projectComponent;

    public AbstractAPIGatewayEventHandler() {
        projectComponent = DaggerProjectComponent.builder().build();

        inject(projectComponent);
    }

    @Override
    public final APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {

        APIGatewayProxyResponseEvent response = doHandleRequest(event, context);

        return response;
    }

    protected abstract APIGatewayProxyResponseEvent doHandleRequest(APIGatewayProxyRequestEvent event, Context context);

    protected void inject(final ProjectComponent projectComponent){
    }

}
