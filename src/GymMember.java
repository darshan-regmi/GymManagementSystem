import java.time.LocalDate;

public class GymMember {
    private String id;
    private String name;
    private String location;
    private String phone;
    private String email;
    private String gender;
    private LocalDate dob;
    private LocalDate membershipStartDate;
    private boolean isActive;
    private int attendance;

    public GymMember(String id, String name, String location, String phone, 
                     String email, String gender, LocalDate dob, LocalDate membershipStartDate) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.dob = dob;
        this.membershipStartDate = membershipStartDate;
        this.isActive = false;
        this.attendance = 0;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getGender() { return gender; }
    public LocalDate getDob() { return dob; }
    public LocalDate getMembershipStartDate() { return membershipStartDate; }
    public boolean isActive() { return isActive; }
    public int getAttendance() { return attendance; }

    // Methods
    public void activateMembership() {
        this.isActive = true;
    }

    public void deactivateMembership() {
        this.isActive = false;
    }

    public void markAttendance() {
        if (isActive) {
            this.attendance++;
        }
    }

    public void resetMember() {
        this.isActive = false;
        this.attendance = 0;
    }

    public void display() {
        System.out.println("Member ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Location: " + location);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println("Gender: " + gender);
        System.out.println("Date of Birth: " + dob);
        System.out.println("Membership Start Date: " + membershipStartDate);
        System.out.println("Active Status: " + isActive);
        System.out.println("Attendance: " + attendance);
    }
}
