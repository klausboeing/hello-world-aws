package com.gmail.klausboeing.aws.util.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.gmail.klausboeing.aws.config.DaggerProjectComponent;
import com.gmail.klausboeing.aws.config.ProjectComponent;

public abstract class AbstractDynamoEventHandler implements RequestHandler<DynamodbEvent, Void> {

    private ProjectComponent projectComponent;

    public AbstractDynamoEventHandler() {
        projectComponent = DaggerProjectComponent.builder().build();
        inject(projectComponent);
    }

    @Override
    public final Void handleRequest(DynamodbEvent event, Context context) {
        for (DynamodbEvent.DynamodbStreamRecord record : event.getRecords()) {
            if (record == null) {
                continue;
            }

            doHandleRequest(record, context);
        };

        return null;
    }

    protected abstract void doHandleRequest(DynamodbEvent.DynamodbStreamRecord record, Context context);

    protected void inject(ProjectComponent projectComponent){

    }
}
