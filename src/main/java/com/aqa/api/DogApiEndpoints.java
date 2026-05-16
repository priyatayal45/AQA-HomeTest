package com.aqa.api;

public class DogApiEndpoints {

    private DogApiEndpoints() {}

    public static final String RANDOM_IMAGE   = "/breeds/image/random";
    public static final String ALL_BREEDS     = "/breeds/list/all";
    public static final String IMAGE_BY_BREED = "/breed/{breed}/images/random";
    public static final String INVALID_BREED  = "/breed/invalidbreedxyz/images/random";
}
