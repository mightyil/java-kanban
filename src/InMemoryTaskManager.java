import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();

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
    }

    @Override
    public void deleteSubTaskById(int id) {
        SubTask deletedSubTask = subTasks.get(id);
        subTasks.remove(id);
        deletedSubTask.getOwner().deleteSubTask(deletedSubTask);
    }

    @Override
    public void deleteEpic(int id) {
        Epic deletedEpic = epics.get(id);
        for (SubTask subTask : deletedEpic.getSubTasks()) {
            subTasks.remove(subTask.getId());
        }
        epics.remove(id);
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
        for (SubTask subTask : epic.getSubTasks()) {
            if (!subTasks.containsKey(subTask.getId())) {
                throw new IllegalArgumentException("task placed in Epic that you are trying to add is missing from the manager");
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
    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    @Override
    public SubTask getSubTaskById(int id) {
        return subTasks.get(id);
    }

    @Override
    public Epic getEpicById(int id) {
        return epics.get(id);
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

    public void printBoard() {
        printList(getTasks(), "tasks");
        printList(getEpics(), "epics");
        printList(getSubTasks(), "subTasks");
    }

    public <T> void printList(ArrayList<T> taskList, String listName) {
        if (!taskList.isEmpty()) {
            for (T task : taskList) {
                System.out.println(task);
            }
        } else {
            System.out.println(listName + " is empty");
        }
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
            System.out.println("Epic with id " + id + " does not exist");
            return null;
        }
    }
}
