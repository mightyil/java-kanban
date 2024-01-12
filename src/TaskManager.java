import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, SubTask> subTasks;
    private HashMap<Integer, Epic> epics;

    private int lastId = 0;

    public void deleteAll() {
        clearTasks();
        clearEpics();
        clearSubTasks();
    }

    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    public void deleteSubTaskById(int id) {
        SubTask deletedSubTask = subTasks.get(id);
        subTasks.remove(id);
        deletedSubTask.getOwner().deleteSubTask(deletedSubTask);
    }

    public void deleteEpic(int id) {
        Epic deletedEpic = epics.get(id);
        for (SubTask subTask : deletedEpic.getSubTasks()) {
            subTasks.remove(subTask.getId());
        }
        epics.remove(id);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
        subTask.getOwner().updateSubTask(subTask);
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public int createTask(Task task) {
        task.setId(++lastId);
        putTask(task);
        return lastId;
    }

    public int createEpic(Epic epic) {
        epic.setId(++lastId);
        putEpic(epic);
        return lastId;
    }

    public int createSubTask(SubTask subTask) {
        subTask.setId(++lastId);
        putSubTask(subTask);
        subTask.getOwner().addSubTask(subTask);
        return lastId;
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public SubTask getSubTaskById(int id) {
        return subTasks.get(id);
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public void putTask(Task task) {
        if (tasks == null) {
            tasks = new HashMap<>();
        }
        tasks.put(task.getId(), task);
    }

    public void putSubTask(SubTask subTask) {
        if (subTasks == null) {
            subTasks = new HashMap<>();
        }
        subTasks.put(subTask.getId(), subTask);
    }

    public void putEpic(Epic epic) {
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

    public void printBoard() {
        if (tasks != null && !tasks.isEmpty()) {
            for (Task task : tasks.values()) {
                System.out.println(task);
            }
        } else {
            System.out.println("tasks is empty");
        }
        if(epics != null && !epics.isEmpty()) {
            for (Epic epic : epics.values()) {
                System.out.println(epic);
            }
        } else {
            System.out.println("epics is empty");
        }
        if (subTasks != null && subTasks.isEmpty()) {
            System.out.println("subTasks is empty");
        }
    }
}
