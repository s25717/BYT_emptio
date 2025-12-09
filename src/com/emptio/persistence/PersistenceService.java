package com.emptio.persistence;

import java.io.IOException;

public class PersistenceService {

    private PersistenceService() {}

    // Сохраняем все зарегистрированные extents
    public static void saveAllExtents() throws IOException {
        ExtentManager.saveAll();
    }

    // Загружаем все extents
    public static void loadAllExtents() throws IOException, ClassNotFoundException {
        ExtentManager.loadAll();
    }
}
