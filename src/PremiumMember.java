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
        if (this.isFullPayment) {
            return "Payment is already complete!";
        }

        double totalAfterPayment = this.paidAmount + payment;
        if (totalAfterPayment > premiumCharge) {
            return "Payment amount exceeds the premium charge!";
        }

        this.paidAmount += payment;
        this.isFullPayment = (this.paidAmount == premiumCharge);
        
        double remainingAmount = premiumCharge - this.paidAmount;
        return String.format("Payment successful! Remaining amount to be paid: %.2f", remainingAmount);
    }

    public String calculateDiscount() {
        if (isFullPayment) {
            this.discountAmount = premiumCharge * 0.10; // 10% discount
            return String.format("Discount calculated successfully! Discount amount: %.2f", discountAmount);
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
    public void display() {
        super.display();
        System.out.println("Personal Trainer: " + personalTrainer);
        System.out.println("Paid Amount: " + paidAmount);
        System.out.println("Payment Status: " + (isFullPayment ? "Complete" : "Incomplete"));
        
        double remainingAmount = premiumCharge - paidAmount;
        System.out.println("Remaining Amount to be Paid: " + remainingAmount);
        
        if (isFullPayment) {
            System.out.println("Discount Amount: " + discountAmount);
        }
    }
}
