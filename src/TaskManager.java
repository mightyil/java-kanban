import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, SubTask> subTasks;
    private HashMap<Integer, Epic> epics;

    private int lastId = 0;

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public SubTask getSubTaskById(int id) {
        return subTasks.get(id);
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public void addTask(Task task) {
        if (tasks == null) {
            tasks = new HashMap<>();
        }
        tasks.put(task.getId(), task);
    }

    public void addSubTask(SubTask subTask) {
        if (subTasks == null) {
            subTasks = new HashMap<>();
        }
        subTasks.put(subTask.getId(), subTask);
    }

    public void addEpic(Epic epic) {
        if (epics == null) {
            epics = new HashMap<>();
        }
        epics.put(epic.getId(), epic);
    }

    public void clearTasks() {
        if(tasks != null) {
            tasks.clear();
        }
    }

    public void clearSubTasks() {
        if(subTasks != null) {
            subTasks.clear();
        }
    }

    public void clearEpics() {
        if(epics != null) {
            epics.clear();
        }
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public HashMap<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }
}
