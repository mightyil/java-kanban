import managers.Managers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ManagersTest {
    @Test
    void shouldReturnTaskManager() {
        assertNotNull(Managers.getDefault());
    }

    @Test
    void shouldReturnHistoryManager() {
        assertNotNull(Managers.getDefaultHistory());
    }
}
