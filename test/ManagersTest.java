import managers.InMemoryHistoryManager;
import managers.InMemoryTaskManager;
import managers.Managers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ManagersTest {
    @Test
    void shouldReturnInMemoryTaskManager() {
        assertEquals(Managers.getDefault().getClass(), InMemoryTaskManager.class);
    }

    @Test
    void shouldReturnInMemoryHistoryManager() {
        assertEquals(Managers.getDefaultHistory().getClass(), InMemoryHistoryManager.class);
    }
}
