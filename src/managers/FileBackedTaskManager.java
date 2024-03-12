package managers;

import managers.history.HistoryManager;
import tasks.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private File file;

    public FileBackedTaskManager(File file) {
        super();
        this.file = file;
    }

    private void save() throws ManagerSaveException {
        final String parameters = "id,type,name,status,description,epic";
        try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {
            writer.write(parameters + "\n");

            writeTasks(writer, getTasks());
            writeTasks(writer, getEpics());
            writeTasks(writer, getSubTasks());

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка записи файла");
        }
    }

    private <T> void writeTasks(FileWriter writer, List<T> tasks) throws IOException {
        for (T task : tasks) {
            writer.write(toString((Task) task) + "\n");
        }
    }

    public Task fromString(String str) {

        return null;
    }

    private String toString(Task task) {
        StringBuilder result = new StringBuilder(task.getId());
        result.append(",");
        if (task instanceof SubTask) {
            result.append(Tasks.SUBTASK);
        } else if (task instanceof Epic) {
            result.append(Tasks.EPIC);
        } else {
            result.append(Tasks.TASK);
        }
        result.append(",").append(task.getName());
        result.append(",").append(task.getStatus());
        result.append(",").append(task.getDescription());
        result.append(",");

        if (task instanceof SubTask) {
            SubTask sub = (SubTask) task;
            result.append(sub.getOwner().getId());
        }
        return result.toString();
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

