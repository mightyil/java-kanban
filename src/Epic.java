import java.util.ArrayList;

public class Epic extends Task{
    private ArrayList<SubTask> tasks;

    public Epic(String name, String description, int id) {
        super(name, description, id, TaskStatus.NEW);

        tasks = new ArrayList<SubTask>();
    }

    public Epic(String name, String description, int id, ArrayList<SubTask> subTasks) {
        super(name, description, id, TaskStatus.NEW);

        tasks = subTasks;
        for (SubTask subTask : subTasks) {
            subTask.setOwner(this);
        }
        setStatus(checkStatus());
    }

    public Epic(Task task) {
        super(task.getName(), task.getDescription(), task.getId(), TaskStatus.NEW);

        tasks = new ArrayList<SubTask>();
    }

    public Epic (Epic epic, ArrayList<SubTask> subTasks) {
        super(epic.getName(), epic.getDescription(), epic.getId(), TaskStatus.NEW);

        tasks = subTasks;
        for (SubTask subTask : subTasks) {
            subTask.setOwner(this);
        }
        setStatus(checkStatus());
    }

    public void addSubTask(SubTask subTask) {
        tasks.add(subTask);
        setStatus(checkStatus());
    }

    public void updateSubTask(SubTask subTask) {
        for(SubTask task : tasks) {
            if(task.getId() == subTask.getId()) {
                tasks.set(tasks.indexOf(task), subTask);
                break;
            }
        }
        setStatus(checkStatus());
    }

    public void deleteSubTask(SubTask subTask) {
        tasks.remove(subTask);
        setStatus(checkStatus());
    }

//    public TaskStatus checkStatus() {
//        boolean isNew = true;
//        boolean isDone = true;
//        for (SubTask task : tasks) {
//            TaskStatus status = task.getStatus();
//            if (status != TaskStatus.NEW) {
//                isNew = false;
//            }
//            if (status != TaskStatus.DONE) {
//                isDone = false;
//            }
//            if (!isDone && !isNew) {
//                return TaskStatus.IN_PROGRESS;
//            }
//        }
//        if(isNew) {
//            return TaskStatus.NEW;
//        } else {
//            return TaskStatus.DONE;
//        }
//    }

//    second version
    public TaskStatus checkStatus() {
        TaskStatus status = null;
        for (SubTask task : tasks) {
            if(status == null) {
                status = task.getStatus();
            }
            if (task.getStatus() != status) {
                return TaskStatus.IN_PROGRESS;
            }
        }
        return status == null ? TaskStatus.NEW : status;
    }

    public ArrayList<SubTask> getSubTasks() {
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
        for (SubTask subTask : tasks) {
            epic += "\t" + subTask + "\n";
        }
        return epic + '}';
    }
}
