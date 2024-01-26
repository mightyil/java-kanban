import java.util.List;

public interface HistoryManager {
    public <T extends Task> void add(T task);
    List<Task> getHistory();
}
