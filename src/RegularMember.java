import java.time.LocalDate;

public class RegularMember extends GymMember {
    private String plan;  // Basic, Standard, or Deluxe
    private double planPrice;
    private String referralSource;
    private int loyaltyPoints;

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
    
    // Setters
    public void setPlan(String plan) {
        this.plan = plan;
        setPlanPrice();
    }

    private void setPlanPrice() {
        switch(plan.toLowerCase()) {
            case "basic":
                planPrice = 1000;
                break;
            case "standard":
                planPrice = 2000;
                break;
            case "deluxe":
                planPrice = 3000;
                break;
            default:
                planPrice = 1000; // Default to basic price
        }
    }

    public String upgradePlan(String newPlan) {
        if (!isActive()) {
            return "Member must be active to upgrade plan!";
        }
        
        if (newPlan.equals(this.plan)) {
            return "Already on this plan!";
        }
        
        this.plan = newPlan;
        setPlanPrice();
        loyaltyPoints += 10; // Reward for upgrading
        return "Plan upgraded successfully to " + newPlan;
    }

    @Override
    public void markAttendance() {
        if (isActive()) {
            super.markAttendance();
            loyaltyPoints += 5; // Reward for attendance
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
    public void display() {
        System.out.println(this.toString());
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
