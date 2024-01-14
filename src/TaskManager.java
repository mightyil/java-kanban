import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();

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

    public void updateEpic(Epic epic) throws IllegalArgumentException{
        for (SubTask subTask : epic.getSubTasks()) {
            if (!subTasks.containsKey(subTask.getId())) {
                throw new IllegalArgumentException("task placed in Epic that you are trying to add is missing from the manager");
            }
        }
        epics.put(epic.getId(), epic);
    }

    public int createTask(Task task) {
        task.setId(++lastId);
        tasks.put(lastId, task);
        return lastId;
    }

    public int createEpic(Epic epic) {
        epic.setId(++lastId);
        epics.put(lastId, epic);
        return lastId;
    }

    public int createSubTask(SubTask subTask) {
        subTask.setId(++lastId);
        subTasks.put(lastId, subTask);
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

    public void clearTasks() {
            tasks.clear();
    }

    public void clearSubTasks() {
            subTasks.clear();
    }

    public void clearEpics() {
            clearSubTasks();
            epics.clear();
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<SubTask> getSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public void oldPrintBoard() {
        if (!tasks.isEmpty()) {
            for (Task task : tasks.values()) {
                System.out.println(task);
            }
        } else {
            System.out.println("tasks is empty");
        }
        if(!epics.isEmpty()) {
            for (Epic epic : epics.values()) {
                System.out.println(epic);
            }
        } else {
            System.out.println("epics is empty");
        }
        if (subTasks.isEmpty()) {
            System.out.println("subTasks is empty");
        }
    }

    public void printBoard() {
        printList(getTasks());
        printList(getEpics());
        printList(getSubTasks());
    }

    public <T> void printList(ArrayList<T> taskList) {
        if (!taskList.isEmpty()) {
            for (T task : taskList) {
                System.out.println(task);
            }
        }
    }
    //как получить имя класса для сообщения о том что список пуст не придумал :(

    public ArrayList<SubTask> getEpicsSubTasks(Epic epic) {
        return epic.getSubTasks();
    }

    public ArrayList<SubTask> getEpicsSubTasks(int id) {
        return epics.get(id).getSubTasks();
    }
}
