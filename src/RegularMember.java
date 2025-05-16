import java.time.LocalDate;

public class RegularMember extends GymMember {
    private String plan;  // Basic, Standard, or Deluxe
    private double planPrice;
    private String referralSource;
    private int loyaltyPoints;
    public int attendance;

    public RegularMember(String id, String name, String location, String phone,
                        String email, String gender, LocalDate dob, 
                        LocalDate membershipStartDate, String plan, String referralSource) {
        super(id, name, location, phone, email, gender, dob, membershipStartDate);
        this.plan = plan;
        this.setPlanPrice();
        this.referralSource = referralSource;
        this.loyaltyPoints = 0;
    }

    // Getters
    public String getPlan() { return plan; }
    public double getPlanPrice() { return planPrice; }
    public String getReferralSource() { return referralSource; }
    public int getLoyaltyPoints() { return loyaltyPoints; }
    
    // Setter for loyalty points
    public void setLoyaltyPoints(int points) {
        this.loyaltyPoints = points;
    }
    
    // Setters
    public void setPlan(String plan) {
        // Validate plan name
        if (!isValidPlan(plan)) {
            throw new IllegalArgumentException("Invalid plan name. Must be 'Basic', 'Standard', or 'Deluxe'.");
        }
        this.plan = plan;
        setPlanPrice();
    }
    
    // Helper method to validate plan names
    private boolean isValidPlan(String plan) {
        if (plan == null) return false;
        String planLower = plan.toLowerCase();
        return planLower.equals("basic") || planLower.equals("standard") || planLower.equals("deluxe");
    }

    private void setPlanPrice() {
        // Using modern switch expression for cleaner code
        planPrice = switch(plan.toLowerCase()) {
            case "basic" -> 6500;
            case "standard" -> 12500;
            case "deluxe" -> 18500;
            default -> {
                // This should not happen due to validation in setPlan, but as a fallback:
                System.err.println("Warning: Invalid plan '" + plan + "', defaulting to Basic price.");
                plan = "Basic"; // Correct the plan name
                yield 6500; // Default to basic price
            }
        };
    }

    public String upgradePlan(String newPlan) {
        if (!isActive()) {
            return "Member must be active to change plan!";
        }
        
        // Validate the new plan
        if (!isValidPlan(newPlan)) {
            return "Invalid plan name. Must be 'Basic', 'Standard', or 'Deluxe'.";
        }
        
        if (newPlan.equalsIgnoreCase(this.plan)) {
            return "Already on this plan!";
        }
        
        // Get plan levels for comparison
        int currentLevel = getPlanLevel(this.plan);
        int newLevel = getPlanLevel(newPlan);
        
        // Store old plan for message
        String oldPlan = this.plan;
        
        // Update plan
        this.plan = newPlan;
        setPlanPrice();
        
        // Handle upgrade vs downgrade differently
        if (newLevel > currentLevel) {
            // It's an upgrade
            loyaltyPoints += 10; // Reward for upgrading
            return "Plan upgraded successfully from " + oldPlan + " to " + newPlan;
        } else {
            // It's a downgrade
            return "Plan downgraded from " + oldPlan + " to " + newPlan;
        }
    }
    
    // Helper method to get plan level for comparison
    private int getPlanLevel(String planName) {
        // Using modern switch expression for cleaner code
        return switch(planName.toLowerCase()) {
            case "basic" -> 1;
            case "standard" -> 2;
            case "deluxe" -> 3;
            default -> 0; // Should not happen due to validation
        };
    }

    @Override
    public void markAttendance() {
        if (isActive()) {
            super.markAttendance();
            loyaltyPoints += 5;// Reward for attendance
            attendance +=1;
        }
    }

    @Override
    public void resetMember() {
        super.resetMember();
        this.loyaltyPoints = 0;
        this.plan = "basic";
        this.setPlanPrice();
    }

    @Override
    public String display() {
        return this.toString();
    }
    
    @Override
    public String toString() {
        return super.toString() +
               "\nPlan: " + plan +
               "\nPlan Price: " + planPrice +
               "\nReferral Source: " + referralSource +
               "\nLoyalty Points: " + loyaltyPoints;
    }
}
