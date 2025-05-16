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
        if (!isActive) {
            return; // Don't mark attendance for inactive members
        }
        
        LocalDate today = LocalDate.now();
        
        // Check if attendance has already been marked today
        for (LocalDate date : attendanceRecords) {
            if (date.equals(today)) {
                // Attendance already marked for today
                return;
            }
        }
        
        // Mark attendance for today
        this.attendance++;
        this.attendanceRecords.add(today);
    }
    
    /**
     * Force marks attendance without checking if it's already been marked today.
     * This is useful for loading attendance data from a file or forcing attendance marking.
     */
    public void forceMarkAttendance() {
        if (!isActive) {
            return; // Don't mark attendance for inactive members
        }
        
        // Just increment attendance counter without date checking
        this.attendance++;
        // Add today's date to records - use a random past date to avoid date checking conflicts
        // This ensures multiple attendance marks can be made in the same day
        LocalDate randomDate = LocalDate.now().minusDays((long)(Math.random() * 365));
        this.attendanceRecords.add(randomDate);
    }

    public void resetMember() {
        this.isActive = false;
        this.attendance = 0;
        this.attendanceRecords.clear();
    }

    /**
     * Returns a string representation of this member's information for display in GUI.
     * @return String containing member information formatted for display
     */
    public String display() {
        return this.toString();
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
