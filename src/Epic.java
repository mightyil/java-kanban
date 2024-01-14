import java.util.HashMap;

public class Epic extends Task{
    private final HashMap<Integer, SubTask> tasks;

    public Epic(String name, String description, int id) {
        super(name, description, id, TaskStatus.NEW);

        tasks = new HashMap<>();
    }

    public void addSubTask(SubTask subTask) {
        tasks.put(subTask.getId(), subTask);
        setStatus(checkStatus());
    }

    public void updateSubTask(SubTask subTask) {
        for (SubTask task : tasks.values()) {
            int taskId = task.getId();
            if (taskId == subTask.getId()) {
                tasks.replace(taskId, subTask);
                break;
            }
        }
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

    public HashMap<Integer, SubTask> getSubTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        String epic = "Epic{" +
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
}
