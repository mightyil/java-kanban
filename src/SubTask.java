public class SubTask extends Task{
    private Epic owner;

    public SubTask(String name, String description, int id, TaskStatus status, Epic owner) {
        super(name, description, id, status);
        this.owner = owner;
        this.owner.addSubTask(this);
    }

    public SubTask(Task task, Epic owner) {
        super(task.getName(), task.getDescription(), task.getId(), task.getStatus());
        this.owner = owner;
//        this.owner.addSubTask(this);
    }

    public Epic getOwner() {
        return owner;
    }

    public void setOwner(Epic owner) {
        this.owner = owner;
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
