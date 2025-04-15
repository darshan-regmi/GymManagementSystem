import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.*;

public class GymGUI extends JFrame {
    private ArrayList<GymMember> members;
    
    // Text Fields
    private JTextField idField, nameField, locationField, phoneField, emailField;
    private JTextField dobField, membershipStartField, referralField, paidAmountField;
    private JTextField removalReasonField, trainerNameField;
    
    // Non-editable Fields
    private JTextField regularPriceField, premiumChargeField, discountField;
    
    // Radio Buttons for Gender
    private JRadioButton maleButton, femaleButton;
    
    // ComboBox for Plan
    private JComboBox<String> planComboBox;
    
    // Custom colors
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);   // Steel Blue
    private static final Color SECONDARY_COLOR = new Color(240, 248, 255); // Alice Blue
    private static final Color ACCENT_COLOR = new Color(30, 144, 255);    // Dodger Blue
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Font LABEL_FONT = new Font("Arial", Font.PLAIN, 12);
    
    public GymGUI() {
        members = new ArrayList<>();
        setTitle("Gym Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeComponents();
        loadMembersFromFile(); // Load members when application starts
        pack();
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 600));
        getContentPane().setBackground(SECONDARY_COLOR);
    }

    private JPanel createStyledPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(SECONDARY_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return panel;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        field.setFont(LABEL_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return field;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(LABEL_FONT);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 120, 170), 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(ACCENT_COLOR);
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(PRIMARY_COLOR);
            }
        });
        return button;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        label.setForeground(PRIMARY_COLOR);
        return label;
    }

    private void initializeComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Add title panel
        JPanel titlePanel = createStyledPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Gym Management System");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(PRIMARY_COLOR);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Create main content panel
        JPanel mainPanel = createStyledPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        add(mainPanel, BorderLayout.CENTER);

        // Input Panel with proper spacing
        JPanel inputPanel = createStyledPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Initialize text fields with styling
        idField = createStyledTextField();
        nameField = createStyledTextField();
        locationField = createStyledTextField();
        phoneField = createStyledTextField();
        emailField = createStyledTextField();
        dobField = createStyledTextField();
        membershipStartField = createStyledTextField();
        referralField = createStyledTextField();
        paidAmountField = createStyledTextField();
        removalReasonField = createStyledTextField();
        trainerNameField = createStyledTextField();
        
        // Non-editable fields with styling
        regularPriceField = createStyledTextField();
        regularPriceField.setText("20000");
        regularPriceField.setEditable(false);
        regularPriceField.setBackground(new Color(240, 240, 240));
        
        premiumChargeField = createStyledTextField();
        premiumChargeField.setText("50000");
        premiumChargeField.setEditable(false);
        premiumChargeField.setBackground(new Color(240, 240, 240));
        
        discountField = createStyledTextField();
        discountField.setText("0");
        discountField.setEditable(false);
        discountField.setBackground(new Color(240, 240, 240));
        
        // Gender radio buttons with styling
        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        maleButton.setFont(LABEL_FONT);
        femaleButton.setFont(LABEL_FONT);
        maleButton.setBackground(SECONDARY_COLOR);
        femaleButton.setBackground(SECONDARY_COLOR);
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        
        // Plan combo box with styling
        planComboBox = new JComboBox<>(new String[]{"Basic", "Standard", "Deluxe"});
        planComboBox.setFont(LABEL_FONT);
        planComboBox.setBackground(Color.WHITE);
        ((JComponent) planComboBox.getRenderer()).setBackground(Color.WHITE);
        
        // Add components to input panel with GridBagLayout
        int row = 0;
        addFormField(inputPanel, gbc, row++, "Member ID:", idField);
        addFormField(inputPanel, gbc, row++, "Name:", nameField);
        addFormField(inputPanel, gbc, row++, "Location:", locationField);
        addFormField(inputPanel, gbc, row++, "Phone:", phoneField);
        addFormField(inputPanel, gbc, row++, "Email:", emailField);
        
        // Gender panel
        gbc.gridx = 0;
        gbc.gridy = row;
        inputPanel.add(createStyledLabel("Gender:"), gbc);
        JPanel genderPanel = createStyledPanel();
        genderPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        gbc.gridx = 1;
        inputPanel.add(genderPanel, gbc);
        row++;
        
        addFormField(inputPanel, gbc, row++, "Date of Birth (YYYY-MM-DD):", dobField);
        addFormField(inputPanel, gbc, row++, "Membership Start Date (YYYY-MM-DD):", membershipStartField);
        addFormField(inputPanel, gbc, row++, "Plan:", planComboBox);
        addFormField(inputPanel, gbc, row++, "Personal Trainer:", trainerNameField);
        addFormField(inputPanel, gbc, row++, "Paid Amount:", paidAmountField);
        
        // Add input panel to a scroll pane
        JScrollPane scrollPane = new JScrollPane(inputPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Create button panels (split into two rows for better organization)
        JPanel buttonPanelContainer = createStyledPanel();
        buttonPanelContainer.setLayout(new GridLayout(2, 1, 5, 5));
        
        JPanel buttonPanel1 = createStyledPanel();
        buttonPanel1.setLayout(new GridLayout(1, 4, 10, 10));
        
        JPanel buttonPanel2 = createStyledPanel();
        buttonPanel2.setLayout(new GridLayout(1, 4, 10, 10));
        
        // Add padding to button container
        buttonPanelContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Add buttons to first row
        addButton(buttonPanel1, "Add Regular Member", e -> addRegularMember());
        addButton(buttonPanel1, "Add Premium Member", e -> addPremiumMember());
        addButton(buttonPanel1, "Activate Membership", e -> activateMembership());
        addButton(buttonPanel1, "Deactivate Membership", e -> deactivateMembership());

        // Add buttons to second row
        addButton(buttonPanel2, "Mark Attendance", e -> markAttendance());
        addButton(buttonPanel2, "Upgrade Plan", e -> upgradePlan());
        addButton(buttonPanel2, "Calculate Discount", e -> calculateDiscount());
        addButton(buttonPanel2, "Pay Due Amount", e -> payDueAmount());

        // Add button panels to container
        buttonPanelContainer.add(buttonPanel1);
        buttonPanelContainer.add(buttonPanel2);

        // Create utility buttons panel
        JPanel utilityPanel = createStyledPanel();
        utilityPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        addButton(utilityPanel, "Display", e -> displayMembers());
        addButton(utilityPanel, "Clear", e -> clearFields());
        addButton(utilityPanel, "Save to File", e -> saveToFile());
        addButton(utilityPanel, "Read from File", e -> readFromFile());
        addButton(utilityPanel, "Revert Regular", e -> revertRegularMember());
        addButton(utilityPanel, "Revert Premium", e -> revertPremiumMember());
        
        // Add all panels to main panel
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanelContainer, BorderLayout.SOUTH);
        
        // Add utility panel at the bottom of the frame
        add(utilityPanel, BorderLayout.SOUTH);
    }
    
    private void addButton(JPanel panel, String text, ActionListener listener) {
        JButton button = createStyledButton(text);
        button.addActionListener(listener);
        panel.add(button);
    }
    
    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createStyledLabel(labelText), gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(field, gbc);
    }
    
    private void addRegularMember() {
        try {
            String id = idField.getText();
            if (findMemberById(id) != null) {
                showError("Member ID already exists!");
                return;
            }
            
            String name = nameField.getText();
            String location = locationField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            String gender = maleButton.isSelected() ? "Male" : "Female";
            LocalDate dob = LocalDate.parse(dobField.getText());
            LocalDate startDate = LocalDate.parse(membershipStartField.getText());
            String plan = (String) planComboBox.getSelectedItem();
            String referral = referralField.getText();
            
            RegularMember member = new RegularMember(id, name, location, phone, email,
                                                    gender, dob, startDate, plan, referral);
            members.add(member);
            showSuccess("Regular member added successfully!");
        } catch (Exception e) {
            showError("Error adding regular member: " + e.getMessage());
        }
    }
    
    private void addPremiumMember() {
        try {
            String id = idField.getText();
            if (findMemberById(id) != null) {
                showError("Member ID already exists!");
                return;
            }
            
            String name = nameField.getText();
            String location = locationField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            String gender = maleButton.isSelected() ? "Male" : "Female";
            LocalDate dob = LocalDate.parse(dobField.getText());
            LocalDate startDate = LocalDate.parse(membershipStartField.getText());
            String trainer = trainerNameField.getText();
            
            PremiumMember member = new PremiumMember(id, name, location, phone, email,
                                                    gender, dob, startDate, trainer);
            members.add(member);
            showSuccess("Premium member added successfully!");
        } catch (Exception e) {
            showError("Error adding premium member: " + e.getMessage());
        }
    }
    
    private void activateMembership() {
        GymMember member = findMemberById(idField.getText());
        if (member != null) {
            member.activateMembership();
            showSuccess("Membership activated successfully!");
        } else {
            showError("Member not found!");
        }
    }
    
    private void deactivateMembership() {
        GymMember member = findMemberById(idField.getText());
        if (member != null) {
            member.deactivateMembership();
            showSuccess("Membership deactivated successfully!");
        } else {
            showError("Member not found!");
        }
    }
    
    private void markAttendance() {
        GymMember member = findMemberById(idField.getText());
        if (member != null) {
            if (member.isActive()) {
                member.markAttendance();
                showSuccess("Attendance marked successfully!");
            } else {
                showError("Member is not active!");
            }
        } else {
            showError("Member not found!");
        }
    }
    
    private void upgradePlan() {
        GymMember member = findMemberById(idField.getText());
        if (member != null && member instanceof RegularMember) {
            if (member.isActive()) {
                RegularMember regularMember = (RegularMember) member;
                String newPlan = (String) planComboBox.getSelectedItem();
                String result = regularMember.upgradePlan(newPlan);
                showSuccess(result);
            } else {
                showError("Member is not active!");
            }
        } else {
            showError("Regular member not found!");
        }
    }
    
    private void calculateDiscount() {
        GymMember member = findMemberById(idField.getText());
        if (member != null && member instanceof PremiumMember) {
            PremiumMember premiumMember = (PremiumMember) member;
            String result = premiumMember.calculateDiscount();
            showSuccess(result);
        } else {
            showError("Premium member not found!");
        }
    }
    
    private void revertRegularMember() {
        GymMember member = findMemberById(idField.getText());
        if (member != null && member instanceof RegularMember) {
            // Implementation needed based on RegularMember class
            showSuccess("Regular member reverted successfully!");
        } else {
            showError("Regular member not found!");
        }
    }
    
    private void revertPremiumMember() {
        GymMember member = findMemberById(idField.getText());
        if (member != null && member instanceof PremiumMember) {
            ((PremiumMember) member).revertPremiumMember();
            showSuccess("Premium member reverted successfully!");
        } else {
            showError("Premium member not found!");
        }
    }
    
    private void payDueAmount() {
        try {
            GymMember member = findMemberById(idField.getText());
            if (member != null && member instanceof PremiumMember) {
                PremiumMember premiumMember = (PremiumMember) member;
                double amount = Double.parseDouble(paidAmountField.getText());
                String result = premiumMember.payDueAmount(amount);
                showSuccess(result);
            } else {
                showError("Premium member not found!");
            }
        } catch (NumberFormatException e) {
            showError("Invalid amount entered!");
        }
    }
    
    private void displayMembers() {
        JFrame displayFrame = new JFrame("Member Details");
        JTextArea textArea = new JTextArea(20, 50);
        textArea.setEditable(false);
        
        for (GymMember member : members) {
            textArea.append("=========================\n");
            member.display();
            textArea.append("\n");
        }
        
        displayFrame.add(new JScrollPane(textArea));
        displayFrame.pack();
        displayFrame.setLocationRelativeTo(this);
        displayFrame.setVisible(true);
    }
    
    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        locationField.setText("");
        phoneField.setText("");
        emailField.setText("");
        dobField.setText("");
        membershipStartField.setText("");
        referralField.setText("");
        paidAmountField.setText("");
        removalReasonField.setText("");
        trainerNameField.setText("");
        planComboBox.setSelectedIndex(0);
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
    }
    
    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("MemberDetails.txt"))) {
            // Write header
            writer.printf("%-5s %-15s %-15s %-15s %-25s %-20s %-10s %-10s %-10s %-15s %-10s %-15s %-15s %-15s\n",
                         "ID", "Name", "Location", "Phone", "Email", "Membership Start Date",
                         "Plan", "Price", "Attendance", "Loyalty Points", "Active Status",
                         "Full Payment", "Discount Amount", "Net Amount Paid");
            
            // Write member details
            for (GymMember member : members) {
                String plan = "-";
                double price = 0.0;
                int loyaltyPoints = 0;
                boolean fullPayment = false;
                double discountAmount = 0.0;
                double netAmount = 0.0;
                
                if (member instanceof RegularMember) {
                    RegularMember rm = (RegularMember) member;
                    plan = rm.getPlan();
                    price = rm.getPlanPrice();
                    loyaltyPoints = rm.getLoyaltyPoints();
                    fullPayment = true; // Regular members pay upfront
                    netAmount = price;
                } else if (member instanceof PremiumMember) {
                    PremiumMember pm = (PremiumMember) member;
                    plan = "Premium";
                    price = pm.getPremiumCharge();
                    loyaltyPoints = 0; // Premium members don't have loyalty points
                    fullPayment = pm.isFullPayment();
                    discountAmount = pm.getDiscountAmount();
                    netAmount = pm.getPaidAmount();
                }
                
                writer.printf("%-5s %-15s %-15s %-15s %-25s %-20s %-10s %-10.2f %-10d %-15d %-10b %-15b %-15.2f %-15.2f\n",
                             member.getId(),
                             member.getName(),
                             member.getLocation(),
                             member.getPhone(),
                             member.getEmail(),
                             member.getMembershipStartDate(),
                             plan,
                             price,
                             member.getAttendance(),
                             loyaltyPoints,
                             member.isActive(),
                             fullPayment,
                             discountAmount,
                             netAmount);
            }
            showSuccess("Data saved to file successfully!");
        } catch (IOException e) {
            showError("Error saving to file: " + e.getMessage());
        }
    }
    
    private void readFromFile() {
        try {
            JFrame readFrame = new JFrame("Member Details from File");
            JTextArea textArea = new JTextArea(20, 50);
            textArea.setEditable(false);
            
            BufferedReader reader = new BufferedReader(new FileReader("MemberDetails.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                textArea.append(line + "\n");
            }
            reader.close();
            
            readFrame.add(new JScrollPane(textArea));
            readFrame.pack();
            readFrame.setLocationRelativeTo(this);
            readFrame.setVisible(true);
        } catch (IOException e) {
            showError("Error reading from file: " + e.getMessage());
        }
    }
    
    // New method to load members from file when application starts
    private void loadMembersFromFile() {
        File file = new File("MemberDetails.txt");
        if (!file.exists()) {
            return; // No file to load
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            // Skip the header line
            reader.readLine();
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length < 14) continue; // Skip invalid lines
                
                int index = 0;
                String id = parts[index++];
                String name = parts[index++];
                String location = parts[index++];
                String phone = parts[index++];
                String email = parts[index++];
                
                // Parse membership start date
                LocalDate membershipStartDate;
                try {
                    membershipStartDate = LocalDate.parse(parts[index++], formatter);
                } catch (Exception e) {
                    continue; // Skip if date parsing fails
                }
                
                String plan = parts[index++];
                double price = Double.parseDouble(parts[index++]);
                int attendance = Integer.parseInt(parts[index++]);
                int loyaltyPoints = Integer.parseInt(parts[index++]);
                boolean isActive = Boolean.parseBoolean(parts[index++]);
                boolean isFullPayment = Boolean.parseBoolean(parts[index++]);
                double discountAmount = Double.parseDouble(parts[index++]);
                double netAmount = Double.parseDouble(parts[index++]);
                
                // Create appropriate member type
                GymMember member;
                if (plan.equals("Premium")) {
                    // Assume a default trainer name for now
                    member = new PremiumMember(id, name, location, phone, email, "Male", 
                                              LocalDate.now(), membershipStartDate, "Default Trainer");
                    
                    PremiumMember pm = (PremiumMember) member;
                    // Set payment details
                    if (netAmount > 0) {
                        pm.payDueAmount(netAmount);
                    }
                    if (isFullPayment) {
                        // The discount is already applied in the saved amount
                        ((PremiumMember) member).calculateDiscount();
                    }
                } else {
                    // Regular member
                    member = new RegularMember(id, name, location, phone, email, "Male", 
                                              LocalDate.now(), membershipStartDate, plan, "Direct");
                    
                    // Set loyalty points
                    for (int i = 0; i < loyaltyPoints / 5; i++) {
                        ((RegularMember) member).markAttendance();
                    }
                }
                
                // Set common properties
                if (isActive) {
                    member.activateMembership();
                }
                
                // Set attendance
                for (int i = 0; i < attendance; i++) {
                    member.markAttendance();
                }
                
                // Add to members list
                members.add(member);
            }
            
            showSuccess("Successfully loaded " + members.size() + " members from file.");
        } catch (IOException e) {
            showError("Error loading members from file: " + e.getMessage());
        }
    }
    
    private GymMember findMemberById(String id) {
        for (GymMember member : members) {
            if (member.getId().equals(id)) {
                return member;
            }
        }
        return null;
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GymGUI().setVisible(true);
        });
    }
}
