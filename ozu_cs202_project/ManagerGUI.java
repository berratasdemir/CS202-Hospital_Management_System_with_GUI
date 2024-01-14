package ozu_cs202_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ManagerGUI extends JFrame {

    private Manager manager;
    private User managerUser;

    private JButton signInButton;

    public ManagerGUI() {
        super("Manager Screen");
        initializeComponents();
    }

    public ManagerGUI(Manager manager, User managerUser) {
        super("Manager Screen");
        this.manager = manager;
        this.managerUser = managerUser;
        initializeComponents();
    }

    private void initializeComponents() {
        signInButton = new JButton("Sign In");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(signInButton);

        getContentPane().add(panel);

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSignInScreen();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setVisible(true);
    }

    private void openSignInScreen() {
        JFrame signInFrame = new JFrame("Sign In");
        signInFrame.setLayout(new FlowLayout());

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(15);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(15);

        JButton signInScreenButton = new JButton("Sign In");

        signInScreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (signIn(username, password)) {
                    JOptionPane.showMessageDialog(signInFrame, "Sign In successful! Opening Manager's Screen.");
                    signInFrame.dispose();
                    showManagerScreen();
                } else {
                    JOptionPane.showMessageDialog(signInFrame, "Invalid username or password. Please try again.");
                }
            }
        });

        signInFrame.add(usernameLabel);
        signInFrame.add(usernameField);
        signInFrame.add(passwordLabel);
        signInFrame.add(passwordField);
        signInFrame.add(signInScreenButton);

        signInFrame.setSize(300, 150);
        signInFrame.setVisible(true);
    }

    private boolean signIn(String username, String password) {
        String url = "jdbc:mysql://localhost:3306/cs202project";
        String user = "root";
        String dbPassword = "B89.e637";

        try (Connection connection = DriverManager.getConnection(url, user, dbPassword)) {
            if (connection != null) {
                String sql = "SELECT * FROM User WHERE email = ? AND password = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);

                    ResultSet resultSet = preparedStatement.executeQuery();
                    return resultSet.next();
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    private void showManagerScreen() {
        if (manager != null) {
            JFrame managerScreen = new JFrame("Manager Information");
            managerScreen.setSize(300, 200);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(3, 1));

            JLabel nameLabel = new JLabel("Name: " + managerUser.getFirstName() + " " + managerUser.getLastName());
            JLabel emailLabel = new JLabel("Email: " + managerUser.getEmail());

            JButton closeButton = new JButton("Close");
            closeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    managerScreen.dispose();
                }
            });

            panel.add(nameLabel);
            panel.add(emailLabel);
            panel.add(closeButton);

            managerScreen.add(panel);
            managerScreen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            managerScreen.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Manager information not available.");
        }
    }

    public static void main(String[] args) {
        ManagerGUI managerGUI = new ManagerGUI(null, null);

        managerGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        managerGUI.setSize(400, 300);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                managerGUI.setVisible(true);
            }
        });
    }
}

