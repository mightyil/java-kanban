package managers.customExceptions;

public class ManagerLoadException extends RuntimeException {
    public ManagerLoadException(String msg) {
        super(msg);
    }
}
