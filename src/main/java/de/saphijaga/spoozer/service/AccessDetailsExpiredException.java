package de.saphijaga.spoozer.service;

/**
 * Created by samuel on 10.05.16.
 */
public class AccessDetailsExpiredException extends Exception {
    public AccessDetailsExpiredException(Throwable throwable) {
        super(throwable);
    }
}