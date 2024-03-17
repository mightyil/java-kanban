import managers.*;
import tasks.*;

import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception{

        TaskManager manager = Managers.getDefault();

        System.out.println("Cоздадим два таска, эпик и эпик с тремя подзадачами");

        Task task = new Task("task one", "description of task one", TaskStatus.NEW);
        manager.createTask(task);
        Task task2 = new Task("task two", "description of task two", TaskStatus.IN_PROGRESS);
        manager.createTask(task2);

        Epic epic = new Epic("epic one", "epics description");
        manager.createEpic(epic);

        epic = new Epic("epic two", "epics two description");
        manager.createEpic(epic);
        SubTask subTask = new SubTask("subtask 2_1","description 1", TaskStatus.NEW, epic);
        manager.createSubTask(subTask);
        SubTask subTask2 = new SubTask("subtask 2_2","description 2", TaskStatus.IN_PROGRESS, epic);
        manager.createSubTask(subTask2);
        SubTask subTask3 = new SubTask("subtask 2_3","description 3", TaskStatus.DONE, epic);
        manager.createSubTask(subTask3);
        printAllTasks(manager);

        System.out.println("Обратимся к ним");
        manager.getTaskById(1);
        manager.getTaskById(2);
        manager.getEpicById(3);
        manager.getEpicById(4);
        manager.getSubTaskById(6);
        manager.getSubTaskById(7);

        System.out.println("История:");
        printList(manager.getHistory());

        System.out.println("Обратимся к ним ещё раз");
        manager.getTaskById(1);
        manager.getTaskById(2);
        manager.getEpicById(3);
        manager.getEpicById(4);

        System.out.println("История:");
        printList(manager.getHistory());

        System.out.println("И ещё");
        manager.getSubTaskById(7);
        manager.getSubTaskById(6);
        manager.getEpicById(4);
        manager.getEpicById(3);
        manager.getTaskById(2);
        manager.getTaskById(1);

        System.out.println("История:");
        printList(manager.getHistory());

        System.out.println("Удалим задачу с id 2");
        manager.deleteTaskById(2);

        System.out.println("История:");
        printList(manager.getHistory());
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        printList(manager.getTasks());

        System.out.println("Эпики:");
        printList(manager.getEpics());

        System.out.println("Подзадачи:");
        printList(manager.getSubTasks());

        System.out.println("История:");
        printList(manager.getHistory());
    }

    private static <T extends Task> void printList(List<T> taskList) {
        if (!taskList.isEmpty()) {
            for (T task : taskList) {
                System.out.println(task);
            }
        } else {
            System.out.println("empty");
        }
    }
}