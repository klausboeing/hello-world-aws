package com.gmail.klausboeing.aws.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ModelModule {

    @Singleton
    @Provides
    Repository repository(final DynamoDBMapper dbMapper) {
        return new Repository(dbMapper);
    }

}
