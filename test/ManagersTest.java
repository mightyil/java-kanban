import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

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
