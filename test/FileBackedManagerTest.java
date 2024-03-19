import managers.FileBackedTaskManager;
import managers.Managers;
import managers.TaskManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.TaskStatus;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileBackedManagerTest {

    TaskManager taskManager;
    File file = null;

    @BeforeEach
    void beforeEach() {
        try {
            file = File.createTempFile("fileBacked", "Test");
            taskManager = new FileBackedTaskManager(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldBeEqualsBeforeAndAfterSave() {
        Epic epic = new Epic("Test epic", "Test epic description");
        taskManager.createEpic(epic);

        SubTask subTask = new SubTask(
                "Test subTask",
                "Test subTask description",
                TaskStatus.NEW,
                epic
        );
        taskManager.createSubTask(subTask);

        Task task = new Task("Test task", "Test task description", TaskStatus.NEW);
        taskManager.createTask(task);

        taskManager.getTaskById(3);

        final List<Epic> epics = taskManager.getEpics();
        final List<SubTask> subTasks = taskManager.getSubTasks();
        final List<Task> tasks = taskManager.getTasks();
        final List<Task> history = taskManager.getHistory();

        TaskManager loadedTaskManager = FileBackedTaskManager.loadFromFile(file);

        final List<Epic> loadedEpics = loadedTaskManager.getEpics();
        final List<SubTask> loadedSubTasks = loadedTaskManager.getSubTasks();
        final List<Task> loadedTasks = loadedTaskManager.getTasks();
        final List<Task> loadedHistory = loadedTaskManager.getHistory();

        assertEquals(loadedEpics.get(0), epics.get(0), "Эпик не сохранён");
        assertEquals(loadedSubTasks.get(0), subTasks.get(0), "Подзадача не сохранена");
        assertEquals(loadedTasks.get(0), tasks.get(0), "Задача не сохранена");
        assertEquals(loadedHistory.get(0), history.get(0), "История не сохранена");
    }

    @Test
    void shouldBeEmptyWhenFileEmpty() {
        TaskManager loadedTaskManager = FileBackedTaskManager.loadFromFile(file);

        assertEquals(0, loadedTaskManager.getEpics().size(), "Список эпиков не пуст");
        assertEquals(0, loadedTaskManager.getSubTasks().size(), "Список подзадач не пуст");
        assertEquals(0, loadedTaskManager.getTasks().size(), "Список задач не пуст");
        assertEquals(0, loadedTaskManager.getHistory().size(), "История не пуста");

    }

    @Test
    void deleteAll() {
        Epic epic = new Epic("Test epic", "Test epic description");
        taskManager.createEpic(epic);

        SubTask subTask = new SubTask(
                "Test subTask",
                "Test subTask description",
                TaskStatus.NEW,
                epic
        );
        taskManager.createSubTask(subTask);

        Task task = new Task("Test task", "Test task description", TaskStatus.NEW);
        taskManager.createTask(task);

        final List<Epic> epics = taskManager.getEpics();
        final List<SubTask> subTasks = taskManager.getSubTasks();
        final List<Task> tasks = taskManager.getTasks();

        assertEquals(epic, epics.get(0), "Эпик не сохранён");
        assertEquals(subTask, subTasks.get(0), "Подзадача не сохранена");
        assertEquals(task, tasks.get(0), "Задача не сохранена");

        taskManager.deleteAll();

        assertEquals(0, taskManager.getEpics().size(), "Список эпиков не пуст");
        assertEquals(0, taskManager.getSubTasks().size(), "Список подзадач не пуст");
        assertEquals(0, taskManager.getTasks().size(), "Список задач не пуст");
    }

    @Test
    void deleteTaskById() {
        Task task = new Task("Test task", "Test task description", TaskStatus.NEW);
        int taskId = taskManager.createTask(task);

        final List<Task> tasks = taskManager.getTasks();
        assertEquals(task, tasks.get(0), "Задача не сохранена");

        taskManager.deleteTaskById(taskId);

        assertEquals(0, taskManager.getTasks().size(), "Список задач не пуст");
    }

    @Test
    void deleteSubTaskById() {
        Epic epic = new Epic("Test epic", "Test epic description");
        taskManager.createEpic(epic);

        SubTask subTask = new SubTask(
                "Test subTask",
                "Test subTask description",
                TaskStatus.NEW,
                epic
        );
        int subTaskId = taskManager.createSubTask(subTask);

        final List<SubTask> subTasks = taskManager.getSubTasks();
        assertEquals(subTask, subTasks.get(0), "Подзадача не сохранена");

        taskManager.deleteSubTaskById(subTaskId);

        assertEquals(0, taskManager.getSubTasks().size(), "Список подзадач не пуст");
    }

    @Test
    void deleteEpicById() {
        Epic epic = new Epic("Test epic", "Test epic description");
        int epicId = taskManager.createEpic(epic);

        SubTask subTask = new SubTask(
                "Test subTask",
                "Test subTask description",
                TaskStatus.NEW,
                epic
        );
        taskManager.createSubTask(subTask);

        final List<Epic> epics = taskManager.getEpics();
        final List<SubTask> subTasks = taskManager.getSubTasks();

        assertEquals(epic, epics.get(0), "Эпик не сохранён");
        assertEquals(subTask, subTasks.get(0), "Подзадача не сохранена");


        taskManager.deleteEpicById(epicId);

        assertEquals(0, taskManager.getEpics().size(), "Список эпиков не пуст");
        assertEquals(0, taskManager.getSubTasks().size(), "Список подзадач не пуст");
    }

    @Test
    void updateTask() {
        Task task = new Task("Test task", "Test task description", TaskStatus.NEW);
        int taskId = taskManager.createTask(task);

        final List<Task> tasks = taskManager.getTasks();
        Task savedTask = tasks.get(0);

        assertEquals(task, savedTask, "Задача не сохранена");

        Task newTask = new Task("Test new task", "Test new task description", taskId, TaskStatus.NEW);
        taskManager.updateTask(newTask);

        final List<Task> newTasks = taskManager.getTasks();
        Task newSavedTask = newTasks.get(0);

        assertNotEquals(savedTask.getName(), newSavedTask.getName(), "Задача не обновлена");
        assertEquals(newTask.getName(), newSavedTask.getName(), "Задача не обновлена корректно");
    }

    @Test
    void updateSubTask() {
        Epic epic = new Epic("Test epic", "Test epic description");
        taskManager.createEpic(epic);

        SubTask subTask = new SubTask(
                "Test subTask",
                "Test subTask description",
                TaskStatus.NEW,
                epic
        );
        int subTaskId = taskManager.createSubTask(subTask);

        final List<SubTask> subTasks = taskManager.getSubTasks();
        Task savedSubTask = subTasks.get(0);

        assertEquals(subTask, savedSubTask, "Задача не сохранена");

        SubTask newSubTask = new SubTask(
                "Test new subTask",
                "Test new subTask description",
                subTaskId,
                TaskStatus.NEW,
                epic
        );
        taskManager.updateSubTask(newSubTask);

        final List<SubTask> newSubTasks = taskManager.getSubTasks();
        Task newSavedSubTask = newSubTasks.get(0);

        assertNotEquals(savedSubTask.getName(), newSavedSubTask.getName(), "Подзадача не обновлена");
        assertEquals(newSubTask.getName(), newSavedSubTask.getName(), "Подзадача не обновлена корректно");
    }

    @Test
    void updateEpic() {
        Epic epic = new Epic("Test epic", "Test epic description");
        int epicId = taskManager.createEpic(epic);

        SubTask subTask = new SubTask(
                "Test subTask",
                "Test subTask description",
                TaskStatus.NEW,
                epic
        );
        taskManager.createSubTask(subTask);

        final List<Epic> epics = taskManager.getEpics();
        final List<SubTask> subTasks = taskManager.getSubTasks();

        assertEquals(epic, epics.get(0), "Эпик не сохранён");
        assertEquals(subTask, subTasks.get(0), "Подзадача не сохранена");

        Epic newEpic = new Epic("Test new epic", "Test new epic description", epicId);

        taskManager.updateEpic(newEpic);

        Epic newSavedEpic = taskManager.getEpics().get(0);
        final List<SubTask> newSavedEpicsSubTasks = taskManager.getEpicsSubTasks(newSavedEpic);

        assertNotEquals(epic.getName(), newSavedEpic.getName(), "Эпик не обновлён");
        assertEquals(newEpic.getName(), newSavedEpic.getName(), "Эпик не обновлён");
        assertEquals(
                epic.getSubTasks(),
                newSavedEpicsSubTasks,
                "Подзадачи эпика не обновлены"
        );

    }
}
