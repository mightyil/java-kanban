import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    void shouldBeEqualsWhenEqualsTaskId(){
        Task task1 = new Task("task", "description",0, TaskStatus.NEW );
        Task task2 = new Task("another task", "description",0, TaskStatus.NEW );
        assertTrue(task1.equals(task2));
    }

    @Test
    void shouldNotBeEqualsWhenDifferentTaskId(){
        Task task1 = new Task("task", "description",0, TaskStatus.NEW );
        Task task2 = new Task("task", "description",1, TaskStatus.NEW );
        assertFalse(task1.equals(task2));
    }

    @Test
    void shouldBeEqualsWhenEqualsSubTaskId(){
        Epic epic = new Epic("task", "description", 0);
        SubTask subtask1 = new SubTask("task", "description", 1, TaskStatus.NEW, epic);
        SubTask subtask2 = new SubTask("another task", "description", 1, TaskStatus.NEW, epic);
        assertTrue(subtask1.equals(subtask2));
    }

    @Test
    void shouldBeEqualsWhenEqualsEpicId(){
        Epic epic1 = new Epic("task", "description", 2);
        Epic epic2 = new Epic("another task", "description", 2);
        assertTrue(epic1.equals(epic2));
    }

}
