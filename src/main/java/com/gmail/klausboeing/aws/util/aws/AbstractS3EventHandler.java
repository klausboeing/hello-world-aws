package com.gmail.klausboeing.aws.util.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.model.GetObjectMetadataRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.gmail.klausboeing.aws.config.DaggerProjectComponent;
import com.gmail.klausboeing.aws.config.ProjectComponent;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;

public abstract class AbstractS3EventHandler implements RequestHandler<S3Event, Void> {

    private ProjectComponent projectComponent;

    @Inject
    ObjectMapper om;

    public AbstractS3EventHandler() {
        projectComponent = DaggerProjectComponent.builder().build();
        inject(projectComponent);
    }

    @Override
    public final Void handleRequest(S3Event event, Context context) {
        event.getRecords().stream().forEach(r -> {

            AmazonS3 s3CLient = projectComponent.s3CLient();

            ObjectMetadata metadata = s3CLient.getObjectMetadata(new GetObjectMetadataRequest(r.getS3().getBucket().getName(), r.getS3().getObject().getKey()));

            doHandleRequest(r, metadata, context);
        });

        return null;
    }

    protected abstract void doHandleRequest(S3EventNotification.S3EventNotificationRecord record, ObjectMetadata objectMetadata, Context context);

    protected void inject(ProjectComponent projectComponent){

    }
}
