package com.emptio.persistence;

import java.io.*;
import java.util.*;

public class ExtentManager {
    private static final String FILE_PATH = "extents.dat";

    // Зарегистрированные extents
    private static final Map<String, List<? extends Serializable>> extentsMap = new HashMap<>();

    private ExtentManager() {}

    // Регистрация extents
    public static <T extends Serializable> void registerExtent(String className, List<T> extent) {
        if (className != null && extent != null) extentsMap.put(className, extent);
    }

    // Сохранение всех extents в файл
    public static void saveAll() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(extentsMap);
        }
    }

    // Загрузка всех extents из файла
    @SuppressWarnings("unchecked")
    public static void loadAll() throws IOException, ClassNotFoundException {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Map<String, List<? extends Serializable>> loaded =
                    (Map<String, List<? extends Serializable>>) ois.readObject();

            for (Map.Entry<String, List<? extends Serializable>> entry : loaded.entrySet()) {
                String className = entry.getKey();
                List<? extends Serializable> loadedExtent = entry.getValue();

                // Найти extents по имени класса и обновить содержимое
                if (extentsMap.containsKey(className)) {
                    List<Serializable> currentExtent = (List<Serializable>) extentsMap.get(className);
                    currentExtent.clear();
                    currentExtent.addAll(loadedExtent);
                }
            }
        }
    }
}
