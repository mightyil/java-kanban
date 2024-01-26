import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private final ArrayList<Task> history = new ArrayList<>();

    @Override
    public <T extends Task> void add(T task) {
        if (history.size() == 10) {
            history.remove(0);
        }
        history.add(task.copy());
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }

}
