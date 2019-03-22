package com.gmail.klausboeing.aws.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.gmail.klausboeing.aws.model.dynamodb.LocalDateTimeConverter;
import com.gmail.klausboeing.aws.util.builder.BuilderGenerator;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@DynamoDBTable(tableName = "emtity")
public class Entity {

    public static final String S3_OBJECT_METADATA_PROPERTY_NAME = "data";

    private static final String COPY_INDEX_NAME = "CopyIndex";

    @JsonIgnore
    @DynamoDBHashKey
    private String partitionKey;

    @JsonIgnore
    @DynamoDBRangeKey
    private String sortKey;

    private String description;

    @DynamoDBTypeConvertedEnum
    private Type type;

    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    private LocalDateTime dateTime;

    @DynamoDBIndexRangeKey(localSecondaryIndexName = COPY_INDEX_NAME)
    private boolean copy;

    @JsonCreator
    @BuilderGenerator
    protected Entity(
            @JsonProperty("partitionKey") String partitionKey,
            @JsonProperty("sortKey") String sortKey,
            @JsonProperty("description") String description,
            @JsonProperty("type") Type type,
            @JsonProperty("dateTime") LocalDateTime dateTime) {
        this.partitionKey = partitionKey;
        this.sortKey = sortKey;
        this.description = description;
        this.type = type;
        this.dateTime = dateTime;
    }

    public Entity() {
    }

    public Entity toCopy() {
        Entity entity = EntityBuilder.create().copy(this).build();

        entity.copy = true;

        return entity;
    }

    public void setPartitionKey(String partitionKey) {
        this.partitionKey = partitionKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setCopy(boolean copy) {
        this.copy = copy;
    }

    public String getPartitionKey() {
        return partitionKey;
    }

    public String getSortKey() {
        return sortKey;
    }

    public String getDescription() {
        return description;
    }

    public Type getType() {
        return type;
    }

    @DynamoDBIgnore
    @JsonIgnore
    public String getS3ObjectKey() {
        return String.format("copy/%s/%s", getPartitionKey(), getSortKey());
    }

    @DynamoDBIgnore
    public String getPathRequestDownload() {
        return String.format("/copy/%s/%s/download", getPartitionKey(), getSortKey());
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public boolean isCopy() {
        return this.copy;
    }

}
