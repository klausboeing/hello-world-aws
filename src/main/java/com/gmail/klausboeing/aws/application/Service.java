package com.gmail.klausboeing.aws.application;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.gmail.klausboeing.aws.model.Entity;
import com.gmail.klausboeing.aws.model.Repository;
import com.gmail.klausboeing.aws.model.util.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class Service {

    private static final String SOURCE_STORAGE = "source/";

    private static final String COPY_STORAGE = "copy/";

    private final String bucketName;

    private final AmazonS3 s3Client;

    private final Repository repository;

    private final ObjectMapper om;

    Service(final String bucketName, final Repository repository, final AmazonS3 s3Client, final ObjectMapper om){
        this.bucketName = bucketName;
        this.s3Client  = s3Client;
        this.repository = repository;
        this.om = om;
    }

    public URL generateUploadURL(final Entity entity){

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, SOURCE_STORAGE.concat(UUID.randomUUID().toString()))
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(Date.from(LocalDateTime.now().plusHours(1).atZone(ZoneId.systemDefault()).toInstant()));

        try {
            String data = Base64.getEncoder().encodeToString(om.writeValueAsString(entity).getBytes());

            generatePresignedUrlRequest.addRequestParameter(Headers.S3_USER_METADATA_PREFIX.concat(Entity.S3_OBJECT_METADATA_PROPERTY_NAME), data);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return  s3Client.generatePresignedUrl(generatePresignedUrlRequest);
    }

    public URL generateDownloadURL(final String partitionKey, final String sortKey){
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, COPY_STORAGE.concat(String.format("%s/%s", partitionKey, sortKey)))
                        .withMethod(HttpMethod.GET)
                        .withExpiration(Date.from(LocalDateTime.now().plusHours(1).atZone(ZoneId.systemDefault()).toInstant()));

        return  s3Client.generatePresignedUrl(generatePresignedUrlRequest);
    }

    public void copy(final Entity entity, final String bucketName, final String recepcaoObjectKey){

        CopyObjectRequest copyObjRequest = new CopyObjectRequest(bucketName, recepcaoObjectKey, bucketName, entity.getS3ObjectKey());

        s3Client.copyObject(copyObjRequest);

        save(entity);
    }

    public void save(final Entity entity){
        this.repository.persist(entity);
    }

    public Page<Entity> findAll(){
        return repository.findAll( 0, null);
    }

}
