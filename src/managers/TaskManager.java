package managers;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    void deleteAll();

    void deleteTaskById(int id);

    void deleteSubTaskById(int id);

    void deleteEpicById(int id);

    void updateTask(Task task);

    void updateSubTask(SubTask subTask);

    void updateEpic(Epic epic) throws IllegalArgumentException;

    int createTask(Task task);

    int createEpic(Epic epic);

    int createSubTask(SubTask subTask);

    Task getTaskById(int id);

    SubTask getSubTaskById(int id);

    Epic getEpicById(int id);

    void clearTasks();

    void clearSubTasks();

    void clearEpics();

    ArrayList<Task> getTasks();

    ArrayList<SubTask> getSubTasks();

    ArrayList<Epic> getEpics();

    ArrayList<SubTask> getEpicsSubTasks(Epic epic);

    ArrayList<SubTask> getEpicsSubTasks(int id);

    List<Task> getHistory();

}
