package ru.fns.suppliers.minio;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE, METHOD, PARAMETER, FIELD })
@Retention(RUNTIME)
@Qualifier
public @interface Minio {

}
