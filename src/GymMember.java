import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private List<LocalDate> attendanceRecords;

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
        this.attendanceRecords = new ArrayList<>();
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
    public List<LocalDate> getAttendanceRecords() { return attendanceRecords; }
    
    // Setters
    public void setName(String name) { this.name = name; }
    public void setLocation(String location) { this.location = location; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }

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
            this.attendanceRecords.add(LocalDate.now());
        }
    }

    public void resetMember() {
        this.isActive = false;
        this.attendance = 0;
        this.attendanceRecords.clear();
    }

    public void display() {
        System.out.println(this.toString());
    }
    
    @Override
    public String toString() {
        return "Member ID: " + id +
               "\nName: " + name +
               "\nLocation: " + location +
               "\nPhone: " + phone +
               "\nEmail: " + email +
               "\nGender: " + gender +
               "\nDate of Birth: " + dob +
               "\nMembership Start Date: " + membershipStartDate +
               "\nActive Status: " + isActive +
               "\nAttendance: " + attendance;
    }
}
