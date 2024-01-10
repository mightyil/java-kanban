import java.util.ArrayList;

public class Epic extends Task{
    private ArrayList<SubTask> tasks;

    public Epic(String name, String description, int id, TaskStatus status, ArrayList<Task> tasks) {
        super(name, description, id, status);

        for (Task subTask : tasks) {
            addSubTask(subTask);
        }
    }

    public Epic(Task task, ArrayList<Task> tasks) {
        super(task.getName(), task.getDescription(), task.getId(), task.getStatus());

        for (Task subTask : tasks) {
            addSubTask(subTask);
        }
    }

    public void addSubTask(Task task) {
        if (tasks == null) {
            tasks = new ArrayList<SubTask>();
        }
        tasks.add(new SubTask(task, this));
    }

    public ArrayList<SubTask> getTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "tasks=" + tasks +
                '}';
    }
}
