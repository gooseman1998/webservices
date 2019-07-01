package nl.hu.v1wac.firstapp.application;

import nl.hu.v1wac.firstapp.domain.WorldService;

public class ServiceProvider {
    private static WorldService worldService = new WorldService();

    public static WorldService getWorldService() {
        return worldService;
    }
}