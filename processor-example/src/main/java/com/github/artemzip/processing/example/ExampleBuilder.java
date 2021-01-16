package com.github.artemzip.processing.example;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

@Target({ TYPE, METHOD, CONSTRUCTOR})
@Retention(SOURCE)
public @interface ExampleBuilder {}
