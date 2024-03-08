package managers;

import managers.history.HistoryManager;
import tasks.Task;

import java.io.File;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private File saveFile;

    public FileBackedTaskManager(File file) {
        super();
        saveFile = file;
    }

    public void save() {

    }

    public Task fromString(String str) {

        return null;
    }

    public String toString(Task task) {

        return null;
    }

    static String historyToString(HistoryManager history) {

        return null;
    }

    static List<Integer> historyFromString(String value) {

        return null;
    }

    static FileBackedTaskManager loadFromFile(File file) {

        return null;
    }
}
