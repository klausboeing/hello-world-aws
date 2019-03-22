package com.gmail.klausboeing.aws.application;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.gmail.klausboeing.aws.config.ProjectComponent;
import com.gmail.klausboeing.aws.model.Entity;
import com.gmail.klausboeing.aws.model.Repository;
import com.gmail.klausboeing.aws.util.aws.AbstractDynamoEventHandler;

import javax.inject.Inject;

public class AfterCopyEventHandler extends AbstractDynamoEventHandler {

    @Inject
    DynamoDBMapper dynamoDBMapper;

    @Inject
    Repository repository;

    @Inject
    Service service;

    @Override
    protected void doHandleRequest(DynamodbEvent.DynamodbStreamRecord record, Context context) {
        if(record.getDynamodb().getNewImage() == null){
            return;
        }

        Entity entity = dynamoDBMapper.marshallIntoObject(Entity.class, record.getDynamodb().getNewImage());

        if(entity.isCopy()){
            return;
        }

        entity = entity.toCopy();

        service.save(entity);
    }

    @Override
    protected void inject(ProjectComponent projectComponent) {
        projectComponent.inject(this);
    }
}
