package managers;

import managers.customExceptions.ManagerLoadException;
import managers.customExceptions.ManagerSaveException;
import managers.history.HistoryManager;
import tasks.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private final File file;

    public FileBackedTaskManager(File file) {
        super();
        this.file = file;
    }

    private void save() throws ManagerSaveException {
        final String parameters = "id,type,name,status,description,epic";
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(file.toURI()), StandardCharsets.UTF_8)) {
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

    private <T extends Task> void writeTasks(BufferedWriter writer, List<T> tasks) throws IOException {
        for (T task : tasks) {
            writer.write(toString((Task) task) + "\n");
        }
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

        if (task instanceof SubTask sub) {
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
            if (++i != historySize) {
                result.append(",");
            }
        }

        return result.toString();
    }

    private static List<Integer> historyFromString(String value) {
        List<Integer> result = new ArrayList<>();
        for (String id : value.split(",")) {
            result.add(Integer.parseInt(id));
        }
        return result;
    }


    public static FileBackedTaskManager loadFromFile(File file) throws IllegalStateException {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(file.toURI()), StandardCharsets.UTF_8)) {
            String line = "";
            reader.readLine();
            while (reader.ready()) {
                line = reader.readLine();
                if (!line.isEmpty()) {
                    String[] params = line.split(",");
                    int id = Integer.parseInt(params[0]);
                    String type = params[1];
                    String name = params[2];
                    TaskStatus status = switch (params[3]) {
                        case "NEW" -> TaskStatus.NEW;
                        case "IN_PROGRESS" -> TaskStatus.IN_PROGRESS;
                        case "DONE" -> TaskStatus.DONE;
                        default ->
                            throw new IllegalStateException("Unexpected value: " + params[3]);
                    };
                    String descr = params[4];

                    switch (type) {
                        case "TASK" ->
                            manager.updateTaskWithoutSave(new Task(name, descr, id, status));
                        case "EPIC" ->
                            manager.updateEpicWithoutSave(new Epic(name, descr, id));
                        case "SUBTASK" ->
                            manager.updateSubTaskWithoutSave(new SubTask(name, descr, id, status,
                                    (Epic) manager.getTask(Integer.parseInt(params[5]))));
                        default ->
                            throw new IllegalStateException("Unexpected value: " + type);
                    }
                    if (id > manager.lastId) {
                        manager.lastId = id;
                    }
                } else {
                    String history = reader.readLine();
                    if (history != null) {
                        for (int id : historyFromString(history)) {
                            manager.history.add(manager.getTask(id));
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerLoadException("Ошибка чтения файла");
        }
        return manager;
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

    @Override
    public Task getTaskById(int id) throws IllegalArgumentException {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public SubTask getSubTaskById(int id) throws IllegalArgumentException {
        SubTask subTask = super.getSubTaskById(id);
        save();
        return subTask;
    }

    @Override
    public Epic getEpicById(int id) throws IllegalArgumentException {
        Epic epic = super.getEpicById(id);
        save();
        return epic;
    }

    public void updateTaskWithoutSave(Task task) {
        super.updateTask(task);
    }

    public void updateSubTaskWithoutSave(SubTask subTask) {
        super.updateSubTask(subTask);
    }

    public void updateEpicWithoutSave(Epic epic) {
        super.updateEpic(epic);
    }
}

