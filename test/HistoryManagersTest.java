import managers.Managers;
import managers.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HistoryManagersTest {
    TaskManager manager;

    @BeforeEach
    void beforeEach() {
        manager = Managers.getDefault();
    }

    @Test
    void getHistory() {
        Task task = new Task("task one", "description of task one", TaskStatus.NEW);
        manager.createTask(task);

        Epic epic = new Epic("epic one", "epics description");
        manager.createEpic(epic);

        SubTask subTask = new SubTask("subtask 1","description 1", TaskStatus.NEW, epic);
        manager.createSubTask(subTask);
        SubTask subTask2 = new SubTask("subtask 2","description 2",
                TaskStatus.IN_PROGRESS, epic);
        manager.createSubTask(subTask2);
        SubTask subTask3 = new SubTask("subtask 3","description 3", TaskStatus.DONE, epic);
        manager.createSubTask(subTask3);

        manager.getTaskById(1);
        manager.getSubTaskById(3);
        manager.getSubTaskById(5);

        final List<Task> history = manager.getHistory();

        assertEquals(task, history.get(0), "Задача не сохранена");
        assertEquals(subTask, history.get(1), "Задача не сохранена");
        assertEquals(subTask3, history.get(2), "Задача не сохранена");
    }

    @Test
    void shouldBeCorrectSize() {
        Task task = new Task("task one", "description of task one", TaskStatus.NEW);
        manager.createTask(task);

        for (int i = 0; i < 11; i++) {
            manager.getTaskById(1);
        }

        assertEquals(1, manager.getHistory().size(), "Размер больше 10");
    }
}
