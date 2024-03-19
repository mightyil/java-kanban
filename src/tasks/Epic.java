package tasks;

import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {
    private final HashMap<Integer, SubTask> tasks;

    public Epic(String name, String description, int id) {
        super(name, description, id, TaskStatus.NEW);

        tasks = new HashMap<>();
    }

    public Epic(String name, String description) {
        super(name, description, TaskStatus.NEW);

        tasks = new HashMap<>();
    }

    public void addSubTask(SubTask subTask) {
        tasks.put(subTask.getId(), subTask);
        setStatus(checkStatus());
    }

    public void updateSubTask(SubTask subTask) {
        tasks.put(subTask.getId(), subTask);
        setStatus(checkStatus());
    }

    public void deleteSubTask(SubTask subTask) {
        tasks.remove(subTask.getId());
        setStatus(checkStatus());
    }

    public TaskStatus checkStatus() {
        TaskStatus status = null;
        for (SubTask task : tasks.values()) {
            if (status == null) {
                status = task.getStatus();
            }
            if (task.getStatus() != status) {
                return TaskStatus.IN_PROGRESS;
            }
        }
        return status == null ? TaskStatus.NEW : status;
    }

    public ArrayList<SubTask> getSubTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public String toString() {
        String epic = "tasks.Epic{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                ", subTasks: \n";
        for (SubTask subTask : tasks.values()) {
            epic += "\t" + subTask + "\n";
        }
        return epic + '}';
    }

    public Epic copy() {
        Epic copyEpic = new Epic(getName(), getDescription(), getId());
        for (SubTask subTask : tasks.values()) {
            copyEpic.addSubTask(subTask);
        }
        return copyEpic;
    }
}
