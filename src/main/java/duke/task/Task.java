package duke.task;
public class Task {
    protected String task;
    protected boolean isDone;
    protected String type;

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String checkDone() {
        if (isDone) {
            return "[X]";
        }
        return "[ ]";
    }

    public String checkType() {
        if (type != null) {
            return "[" + type + "]";
        }
        return "[ ]";
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Task(String task, boolean isDone) {
        setTask(task);
        setDone(isDone);
    }

    @Override
    public String toString() {
        return "  " + this.checkType() + this.checkDone() + " " + this.getTask();
    }

    public boolean getIsDone() {
        return this.isDone;
    }
}