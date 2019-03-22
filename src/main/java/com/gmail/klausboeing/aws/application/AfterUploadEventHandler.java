package com.gmail.klausboeing.aws.application;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.gmail.klausboeing.aws.config.ProjectComponent;
import com.gmail.klausboeing.aws.model.Entity;
import com.gmail.klausboeing.aws.util.aws.AbstractS3EventHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Base64;

public class AfterUploadEventHandler extends AbstractS3EventHandler {

    @Inject
    Service service;

    @Inject
    ObjectMapper om;

    public void doHandleRequest(S3EventNotification.S3EventNotificationRecord record, ObjectMetadata metadata, Context context) {

        Entity entity;
        try {
            String data = new String(Base64.getDecoder().decode(metadata.getUserMetadata().get(Entity.S3_OBJECT_METADATA_PROPERTY_NAME)));

            entity = om.readValue(data, Entity.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        service.copy(entity, record.getS3().getBucket().getName(), record.getS3().getObject().getKey());
    }

    @Override
    protected void inject(ProjectComponent projectComponent) {
        projectComponent.inject(this);
    }
}
