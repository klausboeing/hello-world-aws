package com.gmail.klausboeing.aws.model.util;

import com.gmail.klausboeing.aws.util.builder.BuilderGenerator;

import java.util.List;
import java.util.Map;

public class Page<T> {

    private List<T> content;

    private int limit;

    private boolean hasNext;

    private Map<String, Object> lastAttributeValue;

    @BuilderGenerator
    Page(List<T> content, int limit, Map<String, Object> lastAttributeValue) {
        this.content = content;
        this.limit = limit;
        this.lastAttributeValue = lastAttributeValue;
        this.hasNext = !(lastAttributeValue == null || lastAttributeValue.isEmpty());
    }

    public List<T> getContent() {
        return content;
    }

    public int getLimit() {
        return limit;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public Map<String, Object> getLastAttributeValue() {
        return lastAttributeValue;
    }
}
