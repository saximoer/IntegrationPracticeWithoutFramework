package org.example;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class PetStoreModule extends AbstractModule {

    @Qualifier
    @Retention(RUNTIME)
    @interface PetStoreBaseUrl {}

    @Provides
    @PetStoreBaseUrl
    static String provideBaseUrl() {
        return "https://petstore.swagger.io/v2/pet";
    }
}