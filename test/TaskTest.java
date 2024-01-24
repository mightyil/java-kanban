import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    void shouldBeEqualsWhenEqualsTaskId(){
        Task task1 = new Task("task", "description", 0, TaskStatus.NEW );
        Task task2 = new Task("task", "description", 0, TaskStatus.NEW );
        assertTrue(task1.equals(task2));
    }

    @Test
    void shouldBeEqualsWhenEqualsSubTaskId(){
        Epic epic = new Epic("task", "description", 0);
        SubTask subtask1 = new SubTask("task", "description", 0, TaskStatus.NEW, epic);
        SubTask subtask2 = new SubTask("task", "description", 0, TaskStatus.NEW, epic);
        assertTrue(subtask1.equals(subtask2));
    }

    @Test
    void shouldBeEqualsWhenEqualsEpicId(){
        Epic epic1 = new Epic("task", "description", 0);
        Epic epic2 = new Epic("task", "description", 0);
        assertTrue(epic1.equals(epic2));
    }

    /*
    * "проверьте, что объект Epic нельзя добавить в самого себя в виде подзадачи"
    * Реализация данной проверки невозможна, т.к. метод добавления подзадачи
    * принимает аргумент типа SubTask
    */

    /*
     * "проверьте, что объект Subtask нельзя сделать своим же эпиком"
     * Реализация данной проверки невозможна, т.к. метод добавления эпика
     * и конструктор принимают аргумент типа Epic
     */



}
