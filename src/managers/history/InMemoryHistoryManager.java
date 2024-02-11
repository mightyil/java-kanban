package managers.history;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Task> hashHistory = new HashMap<>();

    @Override
    public void add(Task task) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Task> getHistory() {
        return null;
    }

}
