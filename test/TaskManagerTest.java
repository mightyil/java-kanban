import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {

    TaskManager taskManager;

    @BeforeEach
    void beforeEach() {
        taskManager = Managers.getDefault();
    }

    @Test
    void createTaskAndGetTasks() {
        Task task = new Task("Test createTask", "Test createTask description", TaskStatus.NEW);
        final int taskId = taskManager.createTask(task);

        final Task savedTask = taskManager.getTaskById(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void createSubTaskAndGetSubTasks() {
        Epic epic = new Epic("task", "description");
        taskManager.createEpic(epic);
        SubTask subTask = new SubTask(
                "Test createSubTask",
                "Test createSubTask description",
                TaskStatus.NEW,
                epic
        );
        final int subTaskId = taskManager.createSubTask(subTask);

        final SubTask savedSubTask = taskManager.getSubTaskById(subTaskId);

        assertNotNull(savedSubTask, "Задача не найдена.");
        assertEquals(subTask, savedSubTask, "Задачи не совпадают.");

        final List<SubTask> subTasks = taskManager.getSubTasks();

        assertNotNull(subTasks, "Задачи не возвращаются.");
        assertEquals(1, subTasks.size(), "Неверное количество задач.");
        assertEquals(subTask, subTasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void createEpicAndGetEpics() {
        Epic epic = new Epic("Test createEpic", "Test createEpic description");
        final int epicId = taskManager.createEpic(epic);

        final Epic savedEpic = taskManager.getEpicById(epicId);

        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(epic, savedEpic, "Задачи не совпадают.");

        final List<Epic> epics = taskManager.getEpics();

        assertNotNull(epics, "Задачи не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.get(0), "Задачи не совпадают.");
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
        int subTaskId = taskManager.createSubTask(subTask);

        final List<Epic> epics = taskManager.getEpics();
        final List<SubTask> subTasks = taskManager.getSubTasks();

        assertEquals(epic, epics.get(0), "Эипк не сохранён");
        assertEquals(subTask, subTasks.get(0), "Подзадача не сохранена");


        taskManager.deleteEpicById(epicId);

        assertEquals(0, taskManager.getEpics().size(), "Список подзадач не пуст");
        assertEquals(0, taskManager.getSubTasks().size(), "Список подзадач не пуст");
    }
}
