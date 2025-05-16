import java.time.LocalDate;

public class PremiumMember extends GymMember {
    private final double premiumCharge;
    private String personalTrainer;
    private boolean isFullPayment;
    private double paidAmount;
    private double discountAmount;

    public PremiumMember(String id, String name, String location, String phone,
                        String email, String gender, LocalDate dob, 
                        LocalDate membershipStartDate, String personalTrainer) {
        super(id, name, location, phone, email, gender, dob, membershipStartDate);
        this.premiumCharge = 50000;
        
        // Validate trainer name
        if (personalTrainer == null || personalTrainer.trim().isEmpty()) {
            throw new IllegalArgumentException("Personal trainer name cannot be empty for Premium members");
        }
        this.personalTrainer = personalTrainer;
        
        this.isFullPayment = false;
        this.paidAmount = 0;
        this.discountAmount = 0;
    }

    // Getters
    public double getPremiumCharge() { return premiumCharge; }
    public String getPersonalTrainer() { return personalTrainer; }
    public boolean isFullPayment() { return isFullPayment; }
    public double getPaidAmount() { return paidAmount; }
    public double getDiscountAmount() { return discountAmount; }

    public String payDueAmount(double payment) {
        // Check if payment is already complete
        if (this.isFullPayment) {
            return "Payment is already complete!";
        }
        
        // Validate payment amount
        if (payment <= 0) {
            return "Payment amount must be positive!";
        }

        // Check if payment would exceed the total charge
        double totalAfterPayment = this.paidAmount + payment;
        if (totalAfterPayment > premiumCharge) {
            return String.format("Payment amount exceeds the premium charge! Maximum payment allowed: %.2f", 
                              (premiumCharge - this.paidAmount));
        }

        // Process the payment
        this.paidAmount += payment;
        
        // Check if full payment is made
        this.isFullPayment = (this.paidAmount >= premiumCharge - 0.01); // Allow for small floating-point errors
        
        // If full payment is made, ask if discount should be applied instead of doing it automatically
        if (this.isFullPayment) {
            return String.format("Full payment of %.2f completed! You are eligible for a discount. Use 'Calculate Discount' to apply it.", 
                              this.paidAmount);
        } else {
            double remainingAmount = premiumCharge - this.paidAmount;
            return String.format("Payment of %.2f successful! Remaining amount to be paid: %.2f", 
                              payment, remainingAmount);
        }
    }

    public String calculateDiscount() {
        if (isFullPayment) {
            // Calculate 10% discount
            this.discountAmount = premiumCharge * 0.10;
            
            // Apply discount to paid amount
            double originalAmount = this.paidAmount;
            this.paidAmount -= discountAmount;
            
            return String.format("Discount of %.2f applied! Original amount: %.2f, Final amount after discount: %.2f", 
                              discountAmount, originalAmount, paidAmount);
        }
        return "No discount available. Complete the full payment first!";
    }

    public void revertPremiumMember() {
        super.resetMember();
        this.personalTrainer = "";
        this.isFullPayment = false;
        this.paidAmount = 0;
        this.discountAmount = 0;
    }

    @Override
    public String display() {
        return this.toString();
    }
    
    @Override
    public String toString() {
        return super.toString() +
               "\nPersonal Trainer: " + personalTrainer +
               "\nPaid Amount: " + paidAmount +
               "\nPayment Status: " + (isFullPayment ? "Complete" : "Incomplete") +
               "\nRemaining Amount: " + (premiumCharge - paidAmount) +
               (isFullPayment ? "\nDiscount Applied: " + discountAmount : "");
    }
}
