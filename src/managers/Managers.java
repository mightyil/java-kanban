package managers;

import managers.history.HistoryManager;
import managers.history.InMemoryHistoryManager;

public class Managers {

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

}
