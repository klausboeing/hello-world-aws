package com.gmail.klausboeing.aws.util.builder;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import net.karneim.pojobuilder.Visibility;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@GeneratePojoBuilder(withConstructor = Visibility.PRIVATE, withFactoryMethod = "create", withCopyMethod = true)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface BuilderGenerator {
}
