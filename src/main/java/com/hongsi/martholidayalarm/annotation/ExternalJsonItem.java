package com.hongsi.martholidayalarm.annotation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@JsonIgnoreProperties(ignoreUnknown = true)
public @interface ExternalJsonItem {

}
