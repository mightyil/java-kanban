package managers.history;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node> hashHistory = new HashMap<>();
    private Node head = null;
    private Node tail = null;

    @Override
    public void add(Task task) {
        int id = task.getId();
        if (hashHistory.containsKey(id)) {
            removeNode(hashHistory.get(id));
        }
        linkLast(task);
    }

    @Override
    public void remove(int id) {
        removeNode(hashHistory.get(id));
        hashHistory.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    private void linkLast(Task task) {
        Node newNode = new Node(task);
        if (tail != null) {
            newNode.prev = tail;
            tail.next = newNode;
        } else {
            head = newNode;
        }
        tail = newNode;
        hashHistory.put(task.getId(), newNode);
    }

    private List<Task> getTasks() {
        List<Task> result = new ArrayList<>();
        Node current = head;
        while (current != null) {
            result.add(current.data);
            current = current.next;
        }
        return result;
    }

    private void removeNode(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
    }

}
