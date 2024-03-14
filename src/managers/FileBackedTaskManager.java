package managers;

import managers.history.HistoryManager;
import tasks.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private final File file;

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

            writer.write("\n");
            writer.write(historyToString(history));
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка записи файла");
        }
    }

    private <T extends Task> void writeTasks(FileWriter writer, List<T> tasks) throws IOException {
        for (T task : tasks) {
            writer.write(toString((Task) task) + "\n");
        }
    }

    public Task fromString(String str) {

        return null;
    }

    private String toString(Task task) {
        StringBuilder result = new StringBuilder();
        result.append(task.getId()).append(",");
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
        StringBuilder result = new StringBuilder();
        List<Task> historyList = history.getHistory();
        int i = 0;
        int historySize = historyList.size();

        for (Task task : historyList) {
            result.append(task.getId());
            if(++i != historySize) {
                result.append(",");
            }
        }

        return result.toString();
    }

    static List<Integer> historyFromString(String value) {

        return null;
    }

    static FileBackedTaskManager loadFromFile(File file) {

        return null;
    }

    @Override
    public void deleteAll() {
        super.deleteAll();
        save();
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteSubTaskById(int id) {
        super.deleteSubTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public int createTask(Task task) {
        int result = super.createTask(task);
        save();
        return result;
    }

    @Override
    public int createEpic(Epic epic) {
        int result = super.createEpic(epic);
        save();
        return result;
    }

    @Override
    public int createSubTask(SubTask subTask) {
        int result = super.createSubTask(subTask);
        save();
        return result;
    }
}

