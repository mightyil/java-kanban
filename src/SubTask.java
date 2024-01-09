public class SubTask extends Task{
    private Epic owner;

    public SubTask(String name, String description, int id, TaskStatus status, Epic owner) {
        super(name, description, id, status);
        this.owner = owner;
    }

    public SubTask(Task task, Epic owner) {
        super(task.getName(), task.getDescription(), task.getId(), task.getStatus());
        this.owner = owner;
    }

    public Task getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "owner=" + owner +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                '}';
    }
}
