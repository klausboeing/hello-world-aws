package com.gmail.klausboeing.aws.application;

import com.amazonaws.services.s3.AmazonS3;
import com.gmail.klausboeing.aws.model.Repository;
import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.Module;
import dagger.Provides;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class ApplicationModule {

    @Singleton
    @Provides
    Service repositorioService(@Named("bucketName") final String repositorioBucketName, final Repository repository, final AmazonS3 s3Client, final ObjectMapper om){
        return new Service(repositorioBucketName, repository, s3Client, om);
    }

}
