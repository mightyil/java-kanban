package tasks;

public class SubTask extends Task {
    private Epic owner;

    public SubTask(String name, String description, int id, TaskStatus status, Epic owner) {
        super(name, description, id, status);
        this.owner = owner;
    }

    public SubTask(String name, String description, TaskStatus status, Epic owner) {
        super(name, description, status);
        this.owner = owner;
    }

    public Epic getOwner() {
        return owner;
    }

    public void setOwner(Epic owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "tasks.SubTask{" +
                "owner=" + owner.getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status='" + getStatus() + '\'' +
                '}';
    }


    public SubTask copy() {
        return new SubTask(getName(), getDescription(), getId(), getStatus(), getOwner());
    }
}
