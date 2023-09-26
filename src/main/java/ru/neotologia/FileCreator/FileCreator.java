package ru.neotologia.FileCreator;

import ru.neotologia.loger.Loger;

import java.io.File;
import java.util.Date;

public class FileCreator {
    static String fileLog = "file.log";

    public static void create() {
        try {
            File file = new File(fileLog);
            if (file.createNewFile()) {
                Loger.write("INFO", "Создан фаил " + fileLog + " " + new Date());
            }
        } catch (Exception e) {
            Loger.write("ERROR", " " + e.getMessage());
        }
    }
}

