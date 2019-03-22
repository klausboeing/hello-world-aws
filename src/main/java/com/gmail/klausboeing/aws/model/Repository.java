package com.gmail.klausboeing.aws.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.gmail.klausboeing.aws.model.util.Page;
import com.gmail.klausboeing.aws.model.util.PageBuilder;

import java.util.Map;
import java.util.stream.Collectors;

public class Repository {

    private final DynamoDBMapper dbMapper;

    Repository(DynamoDBMapper dbMapper) {
        this.dbMapper = dbMapper;
    }

    public void persist(final Entity entity) {

        this.dbMapper.save(entity);
    }


    public Page<Entity> findAll(final Integer limit, final Map<String, Object> lastAttributeValue) {

        Entity entityKeyHash = EntityBuilder
                .create()
                .build();

        Condition rangeCondiotion = new Condition()
                .withComparisonOperator(ComparisonOperator.EQ)
                .withAttributeValueList(new AttributeValue().withN("1"));

        DynamoDBQueryExpression<Entity> queryExpression = new DynamoDBQueryExpression<Entity>()
                .withHashKeyValues(entityKeyHash)
                .withRangeKeyCondition("latest", rangeCondiotion);

        QueryResultPage<Entity> resultPage = this.dbMapper.queryPage(Entity.class, queryExpression);

        PageBuilder<Entity> pageBuilder = PageBuilder.create();

        return pageBuilder.withContent(resultPage.getResults())
                .withLimit(limit)
                .withLastAttributeValue(transformLastEvaluateKey(resultPage))
                .build();
    }

    private Map<String, Object> transformLastEvaluateKey(QueryResultPage<Entity> resultPage) {
        if (resultPage.getLastEvaluatedKey() == null) {
            return null;
        }
        return resultPage.getLastEvaluatedKey().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue().getS()));
    }

}
