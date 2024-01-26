import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HistoryManagersTest {

    @Test
    void getHistory() {

        TaskManager manager = Managers.getDefault();

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

        Task newTask = new Task("task one", "updated description of task one", task.getId(),
                TaskStatus.IN_PROGRESS);
        manager.updateTask(newTask);

        SubTask newSubTask = new SubTask("updated subtask 1","updated description 1",subTask.getId(),
                TaskStatus.DONE, epic);
        manager.updateSubTask(newSubTask);
        SubTask newSubTask3 = new SubTask("updated subtask 3","updated description 3",subTask3.getId(),
                TaskStatus.DONE, epic);
        manager.updateSubTask(newSubTask3);

        manager.getTaskById(1);
        manager.getSubTaskById(3);
        manager.getSubTaskById(5);

        final List<Task> history = manager.getHistory();

        assertNotEquals(history.get(0), history.get(3), "Старая версия задачи не сохранена");
        assertNotEquals(history.get(1), history.get(4), "Старая версия задачи не сохранена");
        assertNotEquals(history.get(2), history.get(5), "Старая версия задачи не сохранена");
    }
}
