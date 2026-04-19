import java.util.*;
import java.io.*;
public class TaskManager {

    private List<Task> tasks = new ArrayList<>();

    public void addTask(String title, String priority) {
        tasks.add(new Task(title, priority));
    }

    public void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void saveTasks() throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt"));
    for (Task t : tasks) {
        writer.write(t.getTitle() + "," + t.getPriority() + "," + t.isCompleted());
        writer.newLine();
    }
    writer.close();
}

public void loadTasks() throws IOException {
    File file = new File("tasks.txt");
    if (!file.exists()) return;

    BufferedReader reader = new BufferedReader(new FileReader(file));
    String line;

    while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        Task task = new Task(parts[0], parts[1]);
        task.setCompleted(Boolean.parseBoolean(parts[2]));
        tasks.add(task);
    }

    reader.close();
}
}
