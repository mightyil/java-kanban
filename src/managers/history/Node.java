package managers.history;

import tasks.Task;

public class Node {
    public Task data;
    public int prev;
    public int next;

    public Node(Task data) {
        this.data = data;
        this.prev = -1;
        this.next = -1;
    }
}
