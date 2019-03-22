package com.gmail.klausboeing.aws.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import dagger.Module;
import dagger.Provides;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Objects;

@Module
public class ProjectModule {

    @Singleton
    @Provides
    @Named("bucketName")
    String bucketName() {
        return Objects.requireNonNull(System.getenv("BUCKET_NAME"));
    }


    @Singleton
    @Provides
    @Named("tableNamePrefix")
    String tableNamePrefix() {
        return Objects.requireNonNull(System.getenv("TABLE_NAME_DYNAMO_DB_PREFIX"));
    }

    @Singleton
    @Provides
    AmazonS3 s3CLient() {
        return AmazonS3ClientBuilder.standard().build();
    }

    @Singleton
    @Provides
    AmazonDynamoDB dynamoDb() {
        AmazonDynamoDB dynamoDB = AmazonDynamoDBClientBuilder
                .standard()
                .build();

        return dynamoDB;
    }

    @Singleton
    @Provides
    DynamoDBMapper dynamoDBMapper(final AmazonDynamoDB dynamoDB, final @Named("tableNamePrefix") String tableNamePrefix) {
        DynamoDBMapperConfig config = DynamoDBMapperConfig
                .builder()
                .withTableNameOverride(DynamoDBMapperConfig.TableNameOverride.withTableNamePrefix(tableNamePrefix))
                .build();

        DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB, config);

        return mapper;
    }

}
