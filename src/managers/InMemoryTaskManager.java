package managers;

import managers.history.HistoryManager;
import tasks.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();

    HistoryManager history = Managers.getDefaultHistory();

    private int lastId = 0;

    @Override
    public void deleteAll() {
        clearTasks();
        clearEpics();
        clearSubTasks();
    }

    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
        history.remove(id);
    }

    @Override
    public void deleteSubTaskById(int id) {
        SubTask deletedSubTask = subTasks.get(id);
        subTasks.remove(id);
        history.remove(id);
        deletedSubTask.getOwner().deleteSubTask(deletedSubTask);
    }

    @Override
    public void deleteEpicById(int id) {
        Epic deletedEpic = epics.get(id);
        for (SubTask subTask : deletedEpic.getSubTasks()) {
            int subId = subTask.getId();
            subTasks.remove(subId);
            history.remove(subId);
        }
        epics.remove(id);
        history.remove(id);
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
        subTask.getOwner().updateSubTask(subTask);
    }

    @Override
    public void updateEpic(Epic epic) throws IllegalArgumentException{
        for (SubTask subTask : epics.get(epic.getId()).getSubTasks()) {
            epic.updateSubTask(subTask);
        }
        for (SubTask subTask : epic.getSubTasks()) {
            if (!subTasks.containsKey(subTask.getId())) {
                throw new IllegalArgumentException("Epic that you are trying to add is missing from the manager");
            }
        }
        epics.put(epic.getId(), epic);
    }

    @Override
    public int createTask(Task task) {
        task.setId(++lastId);
        tasks.put(lastId, task);
        return lastId;
    }

    @Override
    public int createEpic(Epic epic) {
        epic.setId(++lastId);
        epics.put(lastId, epic);
        return lastId;
    }

    @Override
    public int createSubTask(SubTask subTask) {
        subTask.setId(++lastId);
        subTasks.put(lastId, subTask);
        subTask.getOwner().addSubTask(subTask);
        return lastId;
    }

    @Override
    public Task getTaskById(int id) throws IllegalArgumentException {
        Task task = tasks.get(id);
        if (task != null) {
            history.add(task);
        } else {
            throw new IllegalArgumentException("Task with id " + id + " does not exist");
        }

        return task;
    }

    @Override
    public SubTask getSubTaskById(int id) throws IllegalArgumentException {
        SubTask subTask = subTasks.get(id);
        if(subTask != null) {
            history.add(subTask);
        } else {
            throw new IllegalArgumentException("Subtask with id " + id + " does not exist");
        }

        return subTask;
    }

    @Override
    public Epic getEpicById(int id) throws IllegalArgumentException {
        Epic epic = epics.get(id);
        if(epic != null) {
            history.add(epic);
        } else {
            throw new IllegalArgumentException("Epic with id " + id + " does not exist");
        }
        return epic;
    }

    @Override
    public void clearTasks() {
            tasks.clear();
    }

    @Override
    public void clearSubTasks() {
            subTasks.clear();
    }

    @Override
    public void clearEpics() {
            clearSubTasks();
            epics.clear();
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<SubTask> getSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<SubTask> getEpicsSubTasks(Epic epic) {
        return epic.getSubTasks();
    }

    @Override
    public ArrayList<SubTask> getEpicsSubTasks(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            return epics.get(id).getSubTasks();
        } else {
            System.out.println("tasks.Epic with id " + id + " does not exist");
            return null;
        }
    }

    public List<Task> getHistory() {
        return history.getHistory();
    }

}
