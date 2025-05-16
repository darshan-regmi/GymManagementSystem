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
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180); // Steel Blue
    private static final Color SECONDARY_COLOR = new Color(240, 248, 255); // Alice Blue
    private static final Color ACCENT_COLOR = new Color(30, 144, 255); // Dodger Blue
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Font LABEL_FONT = new Font("Arial", Font.PLAIN, 12);

    public GymGUI() {
        members = new ArrayList<>();
        setTitle("Gym Management System");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // Add window listener to save data when closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveMembersToFile();
                System.exit(0);
            }
        });
        initializeComponents();
        loadMembersFromFile(); // Load members when application starts
        pack();
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 600));
        getContentPane().setBackground(SECONDARY_COLOR);
        setVisible(true);
    }
    
    // Method to save members to file when the program closes
    private void saveMembersToFile() {
        saveToFile(); // Call the existing saveToFile method
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
                BorderFactory.createEmptyBorder(8, 15, 8, 15)));
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
        planComboBox = new JComboBox<>(new String[] { "Basic", "Standard", "Deluxe" });
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
        addButton(buttonPanel1, "Add Regular Member", _ -> addRegularMember());
        addButton(buttonPanel1, "Add Premium Member", _ -> addPremiumMember());
        addButton(buttonPanel1, "Activate Membership", _ -> activateMembership());
        addButton(buttonPanel1, "Deactivate Membership", _ -> deactivateMembership());

        // Add buttons to second row
        addButton(buttonPanel2, "Mark Attendance", _ -> markAttendance());
        addButton(buttonPanel2, "Upgrade Plan", _ -> upgradePlan());
        addButton(buttonPanel2, "Calculate Discount", _ -> calculateDiscount());
        addButton(buttonPanel2, "Pay Due Amount", _ -> payDueAmount());

        // Add button panels to container
        buttonPanelContainer.add(buttonPanel1);
        buttonPanelContainer.add(buttonPanel2);

        // Create utility buttons panel
        JPanel utilityPanel = createStyledPanel();
        utilityPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

        addButton(utilityPanel, "Display", _ -> displayMembers());
        addButton(utilityPanel, "Clear", _ -> clearFields());
        addButton(utilityPanel, "Save to File", _ -> saveToFile());
        addButton(utilityPanel, "Read from File", _ -> readFromFile());
        addButton(utilityPanel, "Revert Regular", _ -> revertRegularMember());
        addButton(utilityPanel, "Revert Premium", _ -> revertPremiumMember());

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
            // Validate required fields
            if (validateRequiredFields() == false) {
                return;
            }
            
            String id = idField.getText().trim();
            if (findMemberById(id) != null) {
                showError("Member ID already exists!");
                return;
            }

            String name = nameField.getText().trim();
            String location = locationField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            
            // Validate gender selection
            if (!maleButton.isSelected() && !femaleButton.isSelected()) {
                showError("Please select a gender!");
                return;
            }
            String gender = maleButton.isSelected() ? "Male" : "Female";
            
            // Parse dates with proper error handling
            LocalDate dob;
            LocalDate startDate;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                dob = LocalDate.parse(dobField.getText().trim(), formatter);
                startDate = LocalDate.parse(membershipStartField.getText().trim(), formatter);
                
                // Validate date logic
                if (dob.isAfter(LocalDate.now())) {
                    showError("Date of birth cannot be in the future!");
                    return;
                }
                if (dob.isAfter(startDate)) {
                    showError("Date of birth cannot be after membership start date!");
                    return;
                }
            } catch (Exception e) {
                showError("Invalid date format! Please use YYYY-MM-DD format.");
                return;
            }
            
            String plan = (String) planComboBox.getSelectedItem();
            // Ensure plan is exactly one of the valid options
            if (!plan.equals("Basic") && !plan.equals("Standard") && !plan.equals("Deluxe")) {
                plan = "Basic"; // Default to Basic if somehow an invalid option was selected
            }
            String referral = referralField.getText().trim();

            RegularMember member = new RegularMember(id, name, location, phone, email,
                    gender, dob, startDate, plan, referral);
            members.add(member);
            showSuccess("Regular member added successfully!");
            clearFields(); // Clear fields after successful addition
        } catch (Exception e) {
            showError("Error adding regular member: " + e.getMessage());
        }
    }

    private void addPremiumMember() {
        try {
            // Validate required fields
            if (validateRequiredFields() == false) {
                return;
            }
            
            String id = idField.getText().trim();
            if (findMemberById(id) != null) {
                showError("Member ID already exists!");
                return;
            }

            String name = nameField.getText().trim();
            String location = locationField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            
            // Validate gender selection
            if (!maleButton.isSelected() && !femaleButton.isSelected()) {
                showError("Please select a gender!");
                return;
            }
            String gender = maleButton.isSelected() ? "Male" : "Female";
            
            // Parse dates with proper error handling
            LocalDate dob;
            LocalDate startDate;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                dob = LocalDate.parse(dobField.getText().trim(), formatter);
                startDate = LocalDate.parse(membershipStartField.getText().trim(), formatter);
                
                // Validate date logic
                if (dob.isAfter(LocalDate.now())) {
                    showError("Date of birth cannot be in the future!");
                    return;
                }
                if (dob.isAfter(startDate)) {
                    showError("Date of birth cannot be after membership start date!");
                    return;
                }
            } catch (Exception e) {
                showError("Invalid date format! Please use YYYY-MM-DD format.");
                return;
            }
            
            String trainer = trainerNameField.getText().trim();
            if (trainer.isEmpty()) {
                showError("Personal trainer name cannot be empty for Premium members!");
                return;
            }

            PremiumMember member = new PremiumMember(id, name, location, phone, email,
                    gender, dob, startDate, trainer);
            members.add(member);
            showSuccess("Premium member added successfully!");
            clearFields(); // Clear fields after successful addition
        } catch (Exception e) {
            showError("Error adding premium member: " + e.getMessage());
        }
    }
    
    // Validate that all required fields are filled
    private boolean validateRequiredFields() {
        if (idField.getText().trim().isEmpty()) {
            showError("Member ID cannot be empty!");
            return false;
        }
        if (nameField.getText().trim().isEmpty()) {
            showError("Name cannot be empty!");
            return false;
        }
        if (locationField.getText().trim().isEmpty()) {
            showError("Location cannot be empty!");
            return false;
        }
        if (phoneField.getText().trim().isEmpty()) {
            showError("Phone cannot be empty!");
            return false;
        }
        if (emailField.getText().trim().isEmpty()) {
            showError("Email cannot be empty!");
            return false;
        }
        if (dobField.getText().trim().isEmpty()) {
            showError("Date of birth cannot be empty!");
            return false;
        }
        if (membershipStartField.getText().trim().isEmpty()) {
            showError("Membership start date cannot be empty!");
            return false;
        }
        return true;
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
        String id = idField.getText().trim();
        if (id.isEmpty()) {
            showError("Please enter a member ID");
            return;
        }
        
        GymMember member = findMemberById(id);
        if (member == null) {
            showError("Member not found with ID: " + id);
            return;
        }
        
        if (!member.isActive()) {
            showError("Cannot mark attendance for inactive member. Please activate first.");
            return;
        }
        
        // Force attendance marking by using a special method
        forceMarkAttendance(member);
        
        // Save to file immediately to persist changes
        saveToFile();
        
        showSuccess("Attendance marked successfully!");
    }
    
    // Helper method to force attendance marking regardless of date
    private void forceMarkAttendance(GymMember member) {
        if (member == null) return;
        
        // Always use forceMarkAttendance to ensure attendance is incremented
        member.forceMarkAttendance();
        
        // For RegularMember, also add loyalty points
        if (member instanceof RegularMember regularMember) {
            // Add loyalty points (5 points per attendance)
            int currentPoints = regularMember.getLoyaltyPoints();
            regularMember.setLoyaltyPoints(currentPoints + 5);
        }
    }

    private void upgradePlan() {
        String memberId = idField.getText().trim();
        if (memberId.isEmpty()) {
            showError("Please enter a member ID");
            return;
        }
        
        GymMember member = findMemberById(memberId);
        if (member == null) {
            showError("Member not found!");
            return;
        }
        
        // Type safety check
        if (!(member instanceof RegularMember)) {
            showError("This operation is only available for Regular members!");
            return;
        }
        
        RegularMember regularMember = (RegularMember) member;
        if (!regularMember.isActive()) {
            showError("Member is not active!");
            return;
        }
        
        String newPlan = (String) planComboBox.getSelectedItem();
        if (newPlan == null || newPlan.isEmpty()) {
            showError("Please select a plan");
            return;
        }
        
        // Ensure the plan is exactly one of the valid options
        if (!newPlan.equals("Basic") && !newPlan.equals("Standard") && !newPlan.equals("Deluxe")) {
            showError("Invalid plan selected. Please choose Basic, Standard, or Deluxe.");
            return;
        }
        
        String result = regularMember.upgradePlan(newPlan);
        showSuccess(result);
    }

    private void calculateDiscount() {
        String memberId = idField.getText().trim();
        if (memberId.isEmpty()) {
            showError("Please enter a member ID");
            return;
        }
        
        GymMember member = findMemberById(memberId);
        if (member == null) {
            showError("Member not found!");
            return;
        }
        
        // Type safety check
        if (!(member instanceof PremiumMember)) {
            showError("This operation is only available for Premium members!");
            return;
        }
        
        PremiumMember premiumMember = (PremiumMember) member;
        String result = premiumMember.calculateDiscount();
        showSuccess(result);
    }

    private void revertRegularMember() {
        String memberId = idField.getText().trim();
        if (memberId.isEmpty()) {
            showError("Please enter a member ID");
            return;
        }
        
        GymMember member = findMemberById(memberId);
        if (member == null) {
            showError("Member not found!");
            return;
        }
        
        // Type safety check
        if (!(member instanceof RegularMember)) {
            showError("This operation is only available for Regular members!");
            return;
        }
        
        // Safe casting after type check
        RegularMember regularMember = (RegularMember) member;
        regularMember.resetMember();
        showSuccess("Regular member reverted successfully!");
    }

    private void revertPremiumMember() {
        String memberId = idField.getText().trim();
        if (memberId.isEmpty()) {
            showError("Please enter a member ID");
            return;
        }
        
        GymMember member = findMemberById(memberId);
        if (member == null) {
            showError("Member not found!");
            return;
        }
        
        // Type safety check
        if (!(member instanceof PremiumMember)) {
            showError("This operation is only available for Premium members!");
            return;
        }
        
        // Safe casting after type check
        PremiumMember premiumMember = (PremiumMember) member;
        premiumMember.revertPremiumMember();
        showSuccess("Premium member reverted successfully!");
    }

    private void payDueAmount() {
        String memberId = idField.getText().trim();
        if (memberId.isEmpty()) {
            showError("Please enter a member ID");
            return;
        }
        
        GymMember member = findMemberById(memberId);
        if (member == null) {
            showError("Member not found!");
            return;
        }
        
        // Type safety check
        if (!(member instanceof PremiumMember)) {
            showError("This operation is only available for Premium members!");
            return;
        }
        
        String amountText = paidAmountField.getText().trim();
        if (amountText.isEmpty()) {
            showError("Please enter a payment amount");
            return;
        }
        
        try {
            double amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                showError("Payment amount must be greater than zero");
                return;
            }
            
            // Safe casting after type check
            PremiumMember premiumMember = (PremiumMember) member;
            String result = premiumMember.payDueAmount(amount);
            showSuccess(result);
        } catch (NumberFormatException e) {
            showError("Please enter a valid numeric amount!");
        }
    }

    private void displayMembers() {
        if (members.isEmpty()) {
            showError("No members to display!");
            return;
        }
        
        JFrame displayFrame = new JFrame("Member Details");
        JTextArea textArea = new JTextArea(20, 50);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Use monospaced font for better alignment

        for (GymMember member : members) {
            textArea.append("=========================\n");
            textArea.append(member.display()); // Use the display method instead of toString
            textArea.append("\n=========================\n\n");
        }

        JScrollPane scrollPane = new JScrollPane(textArea);
        displayFrame.add(scrollPane);
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
        // Define the file path
        String filePath = "MemberDetails.txt";
        File file = new File(filePath);
        
        try {
            // Check if the directory exists, if not create it
            File parentDir = file.getParentFile();
            // Safely handle potential null parent directory
            if (parentDir != null && !parentDir.exists()) {
                if (!parentDir.mkdirs()) {
                    showError("Failed to create directory for saving file.");
                    return;
                }
            }
            
            // Try to create a writer with proper error handling
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                // Write header
                writer.printf("%-5s %-15s %-15s %-15s %-25s %-20s %-10s %-10s %-10s %-15s %-10s %-15s %-15s %-15s\n", 
                             "ID", "Name", "Location", "Phone", "Email", "Membership Start Date", 
                             "Plan", "Price", "Attendance", "Loyalty Points", "Active Status", 
                             "Full Payment", "Discount Amount", "Net Amount Paid");
                
                // Write member details
                for (GymMember member : members) {
                    String plan = "-";
                    double planPrice = 0.0;
                    int loyaltyPoints = 0;
                    boolean fullPayment = false;
                    double discount = 0.0;
                    double netAmount = 0.0;
                    
                    // Using switch pattern matching for cleaner code
                    switch (member) {
                        case RegularMember regularMember -> {
                            plan = regularMember.getPlan();
                            planPrice = regularMember.getPlanPrice();
                            loyaltyPoints = regularMember.getLoyaltyPoints();
                            fullPayment = true; 
                            netAmount = planPrice;
                        }
                        case PremiumMember premiumMember -> {
                            plan = "Premium";
                            planPrice = premiumMember.getPremiumCharge();
                            loyaltyPoints = 0; // Premium members don't have loyalty points
                            fullPayment = premiumMember.isFullPayment();
                            discount = premiumMember.getDiscountAmount();
                            netAmount = premiumMember.getPaidAmount();
                        }
                        default -> {
                            // This should not happen as we only have these two member types
                            System.err.println("Warning: Unknown member type encountered: " + member.getClass().getName());
                        }
                    }
                    
                    writer.printf("%-5s %-15s %-15s %-15s %-25s %-20s %-10s %-10.2f %-10d %-15d %-10b %-15b %-15.2f %-15.2f\n",
                                 member.getId(),
                                 member.getName(),
                                 member.getLocation(),
                                 member.getPhone(),
                                 member.getEmail(),
                                 member.getMembershipStartDate(),
                                 plan,
                                 planPrice,
                                 member.getAttendance(),
                                 loyaltyPoints,
                                 member.isActive(),
                                 fullPayment,
                                 discount,
                                 netAmount);
                }
                // Safely show success message
                if (writer != null) {
                }
            }
        } catch (SecurityException e) {
            showError("Security error: No permission to write to file: " + e.getMessage());
        } catch (IOException e) {
            showError("Error saving to file: " + e.getMessage());
        } catch (Exception e) {
            showError("Unexpected error while saving: " + e.getMessage());
        }
    }

    private void readFromFile() {
        File file = new File("MemberDetails.txt");
        
        if (!file.exists()) {
            showError("File does not exist: MemberDetails.txt");
            return;
        }
        
        if (!file.canRead()) {
            showError("Cannot read file: MemberDetails.txt (Permission denied)");
            return;
        }
        
        JFrame readFrame = new JFrame("Member Details from File");
        JTextArea textArea = new JTextArea(20, 5010);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Use monospaced font for better alignment

        // Use try-with-resources to ensure the reader is closed properly
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                textArea.append(line + "\n");
            }
            
            JScrollPane scrollPane = new JScrollPane(textArea);
            readFrame.add(scrollPane);
            readFrame.pack();
            readFrame.setLocationRelativeTo(this);
            readFrame.setVisible(true);
        } catch (FileNotFoundException e) {
            showError("File not found: " + e.getMessage());
        } catch (IOException e) {
            showError("Error reading from file: " + e.getMessage());
        } catch (Exception e) {
            showError("Unexpected error while reading file: " + e.getMessage());
        }
    }

    // New method to load members from file when application starts
    private void loadMembersFromFile() {
        File file = new File("MemberDetails.txt");
        if (!file.exists() || !file.canRead()) {
            return; // No file to load or can't read
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            // Skip the header line
            reader.readLine();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            
            // Use a CSV parser-like approach for better handling of spaces in fields
            while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                try {
                    // Split the line into columns based on fixed width format
                    // This is more robust than splitting by whitespace
                    if (line.length() < 50) continue; // Skip too short lines
                    
                    String id = line.substring(0, 5).trim();
                    String name = line.substring(5, 20).trim();
                    String location = line.substring(20, 35).trim();
                    String phone = line.substring(35, 50).trim();
                    String email = line.substring(50, 75).trim();
                    
                    // Parse dates with proper error handling
                    String startDateStr = line.substring(75, 95).trim();
                    LocalDate membershipStartDate;
                    LocalDate dob = LocalDate.now().minusYears(20); // Default age if not available
                    
                    try {
                        membershipStartDate = LocalDate.parse(startDateStr, formatter);
                    } catch (Exception e) {
                        // If date parsing fails, use current date
                        membershipStartDate = LocalDate.now();
                    }
                    
                    // Extract remaining fields with proper error handling
                    String plan = "";
                    // Using primitive types with sensible defaults
                    int attendance = 0;
                    int loyaltyPoints = 0;
                    boolean isActive = false;
                    boolean isFullPayment = false;
                    double netAmount = 0.0;
                    
                    try {
                        int currentPos = 95;
                        plan = line.substring(currentPos, currentPos + 10).trim();
                        // Ensure plan is one of the valid options
                        if (!plan.equals("Basic") && !plan.equals("Standard") && !plan.equals("Deluxe") && !plan.equals("Premium")) {
                            // Try to match to the closest valid plan
                            if (plan.startsWith("B") || plan.startsWith("b")) {
                                plan = "Basic";
                            } else if (plan.startsWith("S") || plan.startsWith("s")) {
                                plan = "Standard";
                            } else if (plan.startsWith("D") || plan.startsWith("d")) {
                                plan = "Deluxe";
                            } else {
                                plan = "Basic"; // Default to Basic if no match
                            }
                        }
                        currentPos += 10;
                        
                        // Skip plan price as it's not needed
                        currentPos += 10;
                        
                        attendance = Integer.parseInt(line.substring(currentPos, currentPos + 10).trim());
                        currentPos += 10;
                        
                        loyaltyPoints = Integer.parseInt(line.substring(currentPos, currentPos + 15).trim());
                        currentPos += 15;
                        
                        isActive = Boolean.parseBoolean(line.substring(currentPos, currentPos + 10).trim());
                        currentPos += 10;
                        
                        isFullPayment = Boolean.parseBoolean(line.substring(currentPos, currentPos + 15).trim());
                        currentPos += 15;
                        
                        // Skip discount amount as it's not needed
                        currentPos += 15;
                        
                        if (currentPos < line.length()) {
                            netAmount = Double.parseDouble(line.substring(currentPos).trim());
                        }
                    } catch (NumberFormatException | IndexOutOfBoundsException e) {
                        // Continue with default values if parsing fails
                    }
                    
                    // Randomly assign gender for demonstration (in a real app, you'd store this)
                    String gender = Math.random() > 0.5 ? "Male" : "Female";
                    
                    // Create appropriate member type
                    GymMember member;
                    if (plan.equalsIgnoreCase("Premium")) {
                        // Use a meaningful trainer name
                        String trainerName = "Default Trainer";
                        if (line.length() > 200) {
                            trainerName = line.substring(200, 220).trim();
                            if (trainerName.isEmpty()) trainerName = "Default Trainer";
                        }
                        
                        member = new PremiumMember(id, name, location, phone, email, gender,
                                dob, membershipStartDate, trainerName);

                        PremiumMember pm = (PremiumMember) member;
                        // Set payment details
                        if (netAmount > 0) {
                            pm.payDueAmount(netAmount);
                        }
                        if (isFullPayment) {
                            pm.calculateDiscount();
                        }
                    } else {
                        // Regular member
                        // Use a meaningful referral source
                        String referralSource = "Direct";
                        if (line.length() > 220) {
                            referralSource = line.substring(220, 240).trim();
                            if (referralSource.isEmpty()) referralSource = "Direct";
                        }
                        
                        member = new RegularMember(id, name, location, phone, email, gender,
                                dob, membershipStartDate, plan, referralSource);

                        // Set loyalty points directly using the setter method
                        RegularMember rm = (RegularMember) member;
                        rm.setLoyaltyPoints(loyaltyPoints);
                    }

                    // Set common properties
                    if (isActive) {
                        member.activateMembership();
                    }

                    // Set attendance using forceMarkAttendance to ensure it's properly tracked
                    for (int i = 0; i < attendance; i++) {
                        member.forceMarkAttendance();
                    }

                    // Add to members list
                    members.add(member);
                } catch (Exception e) {
                    // Skip this line if any error occurs during parsing
                    System.err.println("Error parsing line: " + line + ", Error: " + e.getMessage());
                }
            }
            
            if (!members.isEmpty()) {
            }
        } catch (FileNotFoundException e) {
            // File not found, but we already checked existence, so this is unlikely
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            showError("Error loading members from file: " + e.getMessage());
        } catch (Exception e) {
            showError("Unexpected error while loading members: " + e.getMessage());
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
        new GymGUI();
    }
}
