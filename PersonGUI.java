
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

public class PersonGUI extends JFrame {

    private ArrayList<Person> personList;
    private JComboBox<Person> personComboBox;
    private JMenuItem saveMenuItem;
    private JMenuItem saveAsMenuItem;

    private String currentFilePath;

    private boolean isModified = false;

    public PersonGUI() {
        super("Person Hierarchy Application");
        UIManager.put("Label.foreground", Color.BLACK);
        UIManager.put("TextField.foreground", Color.BLACK);
        UIManager.put("TextArea.foreground", Color.BLACK);
        UIManager.put("ComboBox.foreground", Color.BLACK);
        UIManager.put("ComboBox.background", Color.WHITE);
        UIManager.put("OptionPane.messageForeground", Color.BLACK);
        UIManager.put("Button.foreground", Color.BLACK);
        UIManager.put("Menu.foreground", Color.BLACK);
        UIManager.put("MenuItem.foreground", Color.BLACK);
        UIManager.put("MenuBar.foreground", Color.BLACK);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.decode("#f5f5f5"));

        personList = new ArrayList<>();

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open...");
        saveMenuItem = new JMenuItem("Save");
        saveAsMenuItem = new JMenuItem("Save As...");
        saveAsMenuItem.setEnabled(false);
        JMenuItem deleteMenuItem = new JMenuItem("Delete Person");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.add(deleteMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem helpMenuItem = new JMenuItem("Help");
        helpMenu.add(helpMenuItem);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.decode("#f5f5f5"));

        personComboBox = new JComboBox<>();
        personComboBox.setFont(new Font("SansSerif", Font.PLAIN, 16));
        personComboBox.setPreferredSize(new Dimension(300, 30));
        mainPanel.add(personComboBox, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.decode("#f5f5f5"));

        JButton addButton = new JButton("Add Person");
        addButton.setBackground(Color.BLACK);
        JButton editButton = new JButton("Edit Person");
        editButton.setBackground(Color.BLACK);

        styleButton(addButton);
        styleButton(editButton);

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        newMenuItem.addActionListener(e -> newFile());
        openMenuItem.addActionListener(e -> openFile());
        saveMenuItem.addActionListener(e -> saveFile());
        saveAsMenuItem.addActionListener(e -> saveAsFile());
        deleteMenuItem.addActionListener(e -> deletePerson());
        exitMenuItem.addActionListener(e -> exitApplication());
        helpMenuItem.addActionListener(e -> showHelpDialog());
        addButton.addActionListener(e -> addPerson());
        editButton.addActionListener(e -> editPerson());

        saveMenuItem.setEnabled(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitApplication();
            }
        });

        setVisible(true);
    }

    private void newFile() {
        if (isModified) {
            int option = JOptionPane.showConfirmDialog(this,
                    "Unsaved changes detected. Do you want to save before creating a new file?", "New File",
                    JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                saveFile();
            } else if (option == JOptionPane.NO_OPTION) {
                personList.clear();
                refreshComboBox();
                currentFilePath = null;
                saveMenuItem.setEnabled(false);
                isModified = false;
            } else {
                return;
            }
        }
        personList.clear();
        refreshComboBox();
        currentFilePath = null;
        saveMenuItem.setEnabled(false);
        isModified = false;
    }

    @SuppressWarnings("unchecked")
    private void openFile() {
        if (isModified) {
            int option = JOptionPane.showConfirmDialog(this,
                    "Unsaved changes detected. Do you want to save before opening a new file?", "Open File",
                    JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                saveFile();
            } else if (option == JOptionPane.NO_OPTION) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try (ObjectInputStream ois = new ObjectInputStream(
                            new FileInputStream(fileChooser.getSelectedFile()))) {
                        personList = (ArrayList<Person>) ois.readObject();
                        currentFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                        refreshComboBox();
                        saveMenuItem.setEnabled(true);
                        isModified = false;
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                return;
            } else {
                return;
            }
        }
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileChooser.getSelectedFile()))) {
                personList = (ArrayList<Person>) ois.readObject();
                currentFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                refreshComboBox();
                saveMenuItem.setEnabled(true);
                isModified = false;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile() {
        if (currentFilePath != null) {
            saveDataToFile(currentFilePath);
        } else {
            saveAsFile();
        }
    }

    private void saveAsFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            saveDataToFile(fileChooser.getSelectedFile().getAbsolutePath());
            saveMenuItem.setEnabled(true);
            isModified = false;
        }
    }

    private void showHelpDialog() {
        String helpMessage = "Welcome to the Person Hierarchy Application!\n\n"
                + "This application allows you to manage a list of individuals, including different types such as Regular, Registered, and OCCC Persons. Below are instructions for using the program:\n\n"
                + "1. Creating a New File:\n"
                + "   - Navigate to File -> New to create a new person list.\n"
                + "   - If there are unsaved changes, you will be prompted to save them before proceeding.\n\n"
                + "2. Opening an Existing File:\n"
                + "   - Navigate to File -> Open... to load a previously saved person list.\n"
                + "   - If there are unsaved changes, you will be prompted to save them first.\n\n"
                + "3. Saving Your Work:\n"
                + "   - Use File -> Save to save the current list.\n"
                + "   - On first save, you will be prompted to choose a file name and location.\n"
                + "   - Use File -> Save As... to save the list with a new name or location.\n\n"
                + "4. Adding a Person:\n"
                + "   - Click the Add Person button.\n"
                + "   - Select the type of person to add and enter the required details.\n"
                + "   - For OCCC Persons, a valid student ID is required.\n\n"
                + "5. Editing a Person:\n"
                + "   - Select a person from the drop-down list.\n"
                + "   - Click the Edit Person button and update the details as needed.\n\n"
                + "6. Deleting a Person:\n"
                + "   - Select a person from the drop-down list.\n"
                + "   - Navigate to File -> Delete Person to remove the selected individual.\n\n"
                + "7. Exiting the Application:\n"
                + "   - Use File -> Exit to close the application.\n"
                + "   - You will be prompted to save any unsaved changes before exiting.\n\n";

        JOptionPane.showMessageDialog(this, helpMessage, "Help", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editPerson() {
        if (personComboBox.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "No persons available for editing.");
            return;
        }

        Person selectedPerson = (Person) personComboBox.getSelectedItem();

        String newFirstName = JOptionPane.showInputDialog(this, "Enter new first name:", selectedPerson.getFirstName());
        if (newFirstName == null)
            return;
        while (newFirstName == null || newFirstName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "First name cannot be empty.");
            newFirstName = JOptionPane.showInputDialog(this, "Enter first name:");
            if (newFirstName == null)
                return;
        }
        String newLastName = JOptionPane.showInputDialog(this, "Enter new last name:", selectedPerson.getLastName());
        if (newLastName == null)
            return;
        while (newLastName == null || newLastName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Last name cannot be empty.");
            newLastName = JOptionPane.showInputDialog(this, "Enter last name:");
            if (newLastName == null)
                return;
        }
        selectedPerson.setFirstName(newFirstName);
        selectedPerson.setLastName(newLastName);
        refreshComboBox();
        isModified = true;
    }

    private void addPerson() {
        String firstName = null;
        String lastName = null;
        String[] options = { "Regular Person", "Registered Person", "OCCC Person" };
        int choice = JOptionPane.showOptionDialog(this, "Select person type:", "Person Type",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice == JOptionPane.CLOSED_OPTION || choice == -1) {
            return;
        }

        if (choice != 2) {
            firstName = JOptionPane.showInputDialog(this, "Enter first name:");
            if (firstName == null)
                return;
            while (firstName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "First name cannot be empty.");
                firstName = JOptionPane.showInputDialog(this, "Enter first name:");

                if (firstName == null)
                    return;
            }

            lastName = JOptionPane.showInputDialog(this, "Enter last name:");
            if (lastName == null)
                return;
            while (lastName == null || lastName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Last name cannot be empty.");
                lastName = JOptionPane.showInputDialog(this, "Enter last name:");
                if (lastName == null)
                    return;
            }
        }

        OCCCDate birthDate = null;
        if (choice != 2) {
            while (birthDate == null) {
                try {
                    String dateInput = JOptionPane.showInputDialog(this, "Enter birth date (dd/mm/yyyy):");
                    if (dateInput == null)
                        return;

                    String[] parts = dateInput.split("/");
                    int day = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    int year = Integer.parseInt(parts[2]);

                    birthDate = new OCCCDate(day, month, year);
                } catch (InvalidOCCCDateException e) {
                    JOptionPane.showMessageDialog(this, "Invalid Date!", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Invalid Date!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        Person person = null;

        switch (choice) {
            case 0:
                person = new Person(firstName, lastName, birthDate);
                break;
            case 1:
                String govID = null;

                govID = JOptionPane.showInputDialog(this, "Enter government ID:");
                if (govID == null)
                    return;
                while (govID.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Government ID cannot be empty");
                    govID = JOptionPane.showInputDialog(this, "Enter government ID:");
                    if (govID == null) {
                        return;
                    }
                }
                person = new RegisteredPerson(firstName, lastName, birthDate, govID);
                break;

            case 2:
                firstName = JOptionPane.showInputDialog(this, "Enter first name:");
                if (firstName == null)
                    return;
                while (firstName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "First name cannot be empty.");
                    firstName = JOptionPane.showInputDialog(this, "Enter first name:");
                    if (firstName == null)
                        return;
                }

                lastName = JOptionPane.showInputDialog(this, "Enter last name:");
                if (lastName == null)
                    return;
                while (lastName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Last name cannot be empty.");
                    lastName = JOptionPane.showInputDialog(this, "Enter last name:");
                    if (lastName == null)
                        return;
                }

                OCCCDate occcBirthDate = null;
                while (occcBirthDate == null) {
                    try {
                        String dateInput = JOptionPane.showInputDialog(this, "Enter birth date (dd/mm/yyyy):");
                        if (dateInput == null)
                            return;

                        String[] parts = dateInput.split("/");
                        if (parts.length != 3)
                            throw new IllegalArgumentException();
                        int day = Integer.parseInt(parts[0]);
                        int month = Integer.parseInt(parts[1]);
                        int year = Integer.parseInt(parts[2]);

                        occcBirthDate = new OCCCDate(day, month, year);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Invalid date format! Please use dd/mm/yyyy.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

                govID = JOptionPane.showInputDialog(this, "Enter government ID:");
                if (govID == null)
                    return;
                while (govID.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Government ID cannot be empty.");
                    govID = JOptionPane.showInputDialog(this, "Enter government ID:");
                    if (govID == null)
                        return;
                }

                String studentID = JOptionPane.showInputDialog(this, "Enter student ID:");
                if (studentID == null)
                    return;
                while (studentID.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Student ID cannot be empty.");
                    studentID = JOptionPane.showInputDialog(this, "Enter student ID:");
                    if (studentID == null)
                        return;
                }

                RegisteredPerson registeredBase = new RegisteredPerson(firstName, lastName, occcBirthDate, govID);
                person = new OCCCPerson(registeredBase, studentID);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Invalid selection.");
                return;
        }

        personList.add(person);
        refreshComboBox();
        isModified = true;

        if (personList.size() == 1) {
            saveAsMenuItem.setEnabled(true);
        }
    }

    private void deletePerson() {
        if (personComboBox.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "No persons available for deletion.");
            return;
        }

        int selectedIndex = personComboBox.getSelectedIndex();
        if (selectedIndex != -1) {
            personList.remove(selectedIndex);
            refreshComboBox();
            isModified = true;
        } else {
            JOptionPane.showMessageDialog(this, "Please select a person to delete.");
        }
    }

    private void saveDataToFile(String filename) {
        if (personList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No data to save.");
            return;
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(personList);
            JOptionPane.showMessageDialog(this, "Data saved successfully.");
            currentFilePath = filename;
            isModified = false;
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error: File not found. Please check the file path.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while saving data: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshComboBox() {
        personComboBox.removeAllItems();
        for (Person person : personList) {
            if (person != null) {
                try {
                    String firstName = person.getFirstName();
                    String lastName = person.getLastName();
                    if (firstName != null && !firstName.isEmpty() && lastName != null && !lastName.isEmpty()) {
                        personComboBox.addItem(person);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(new Color(60, 120, 200));
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void exitApplication() {
        if (!personList.isEmpty() && isModified == true) {
            int option = JOptionPane.showConfirmDialog(this, "Do you want to save before exiting?", "Confirm Exit",
                    JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.NO_OPTION) {
                dispose();
            } else if (option == JOptionPane.YES_OPTION) {
                if (currentFilePath == null) {
                    saveAsFile();
                    dispose();
                } else {
                    saveFile();
                    dispose();
                }
            }
        } else if (personList.isEmpty() || isModified == false) {
            dispose();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        SwingUtilities.invokeLater(PersonGUI::new);
    }
}
