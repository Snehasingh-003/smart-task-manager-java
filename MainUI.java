import javax.swing.*;
import java.awt.*;

public class MainUI {

    private TaskManager manager = new TaskManager();
    private DefaultListModel<Task> listModel = new DefaultListModel<>();

    public MainUI() {
        try {
           manager.loadTasks();
           for (Task t : manager.getTasks()) {
               listModel.addElement(t);
            }
      } catch (Exception e) {}
        System.out.println("App started...");
        JFrame frame = new JFrame("Smart Task Manager");
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
        public void windowClosing(java.awt.event.WindowEvent e) {
        try {
            manager.saveTasks();
        } catch (Exception ex) {}
    }
});
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Task list
        JList<Task> taskList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(taskList);

        // Input field
        JTextField taskField = new JTextField();

        // Priority dropdown
        String[] priorities = {"High", "Medium", "Low"};
        JComboBox<String> priorityBox = new JComboBox<>(priorities);

        // Buttons
        JButton addBtn = new JButton("Add Task");
        JButton deleteBtn = new JButton("Delete Task");
        JButton completeBtn = new JButton("Mark Complete");

        // Add button action
        addBtn.addActionListener(e -> {
            String text = taskField.getText();
            String priority = (String) priorityBox.getSelectedItem();

            if (!text.isEmpty()) {
                manager.addTask(text, priority);
                listModel.addElement(new Task(text, priority));
                taskField.setText("");
            }
        });

        // Delete button action
        deleteBtn.addActionListener(e -> {
            int index = taskList.getSelectedIndex();
            if (index != -1) {
                manager.deleteTask(index);
                listModel.remove(index);
            }
        });

        //complete button action
        completeBtn.addActionListener(e -> {
            int index = taskList.getSelectedIndex();
            if (index != -1) {
               Task t = listModel.get(index);
               t.setCompleted(true);
               taskList.repaint();
            }
        });

        // Panel
        JPanel panel = new JPanel(new GridLayout(5, 1));
        panel.add(taskField);
        panel.add(priorityBox);
        panel.add(addBtn);
        panel.add(deleteBtn);
        panel.add(completeBtn);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // MAIN METHOD (IMPORTANT)
    public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(() -> {
        new MainUI();
    });
}
}