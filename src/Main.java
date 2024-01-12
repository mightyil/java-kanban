public class Main {

    public static void main(String[] args) {

        TaskManager manager = new TaskManager();

        System.out.println("Cоздадим таск");
        Task task = new Task("task one", "description of task one", 0,TaskStatus.NEW);
        manager.createTask(task);
        manager.printBoard();

        System.out.println("\nОбновим таск");
        Task newTask = new Task("task one", "updated description of task one", task.getId(),
                TaskStatus.IN_PROGRESS);
        manager.updateTask(newTask);
        manager.printBoard();

        System.out.println("\nСоздадим эпик");
        Epic epic = new Epic("epic one", "epics description", 0);
        manager.createEpic(epic);
        manager.printBoard();

        System.out.println("\nОбновим эпик");
        Epic newEpic = new Epic("epic 1", "updated epics description", epic.getId());
        manager.updateEpic(newEpic);
        manager.printBoard();

        System.out.println("\nДобавим сабтасков");
        SubTask subTask = new SubTask("subtask 1","description 1",0, TaskStatus.NEW, newEpic);
        manager.createSubTask(subTask);
        SubTask subTask2 = new SubTask("subtask 2","description 2",0,
                TaskStatus.IN_PROGRESS, newEpic);
        manager.createSubTask(subTask2);
        SubTask subTask3 = new SubTask("subtask 3","description 3",0, TaskStatus.DONE, newEpic);
        manager.createSubTask(subTask3);
        manager.printBoard();

        System.out.println("\nОбновим сабтаски");
        SubTask newSubTask = new SubTask("subtask 1","updated description 1",subTask.getId(),
                TaskStatus.DONE, newEpic);
        manager.updateSubTask(newSubTask);
        SubTask newSubTask2 = new SubTask("subtask 2","updated description 2",subTask2.getId(),
                TaskStatus.DONE, newEpic);
        manager.updateSubTask(newSubTask2);
        manager.printBoard();

        System.out.println("\nСверим сабтаски в менеджере и эпике");
        System.out.println(manager.getSubTasks().values());



        System.out.println("\nСоздадим ещё один эпик с сабтасками");
        epic = new Epic("epic two", "epics two description", 0);
        manager.createEpic(epic);
        subTask = new SubTask("subtask 2_1","description 1",0, TaskStatus.NEW, epic);
        manager.createSubTask(subTask);
        subTask2 = new SubTask("subtask 2_2","description 2",0, TaskStatus.IN_PROGRESS, epic);
        manager.createSubTask(subTask2);
        subTask3 = new SubTask("subtask 2_3","description 3",0, TaskStatus.DONE, epic);
        manager.createSubTask(subTask3);
        subTask3 = new SubTask("subtask 2_4","description 4",0, TaskStatus.DONE, epic);
        manager.createSubTask(subTask3);
        manager.printBoard();

        System.out.println("\nУдалим по-одному таску, сабтаску и эпику");
        manager.deleteTaskById(1);
        manager.deleteSubTaskById(8);
        manager.deleteEpic(2);
        manager.printBoard();

        System.out.println("\nСверим сабтаски в менеджере и эпике");
        System.out.println(manager.getSubTasks().values());

        System.out.println("\nУдалим остатки");
        manager.deleteAll();
        manager.printBoard();
    }
}
