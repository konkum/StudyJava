import java.io.Serializable;

public class Device implements Serializable {
    private int id;
    private Type type;
    private double unitPrice;
    private RateType rateType;
    private DateAudit dateAudit;
    private String branchName;
    private String itemName;
    private String version;
    private double originalPrice;

    public Device(int id, Type type, double unitPrice, RateType rateType, DateAudit dateAudit, String branchName,
            String itemName, String version, double originalPrice) {
        this.id = id;
        this.type = type;
        this.unitPrice = unitPrice;
        this.rateType = rateType;
        this.dateAudit = dateAudit;
        this.branchName = branchName;
        this.itemName = itemName;
        this.version = version;
        this.originalPrice = originalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public RateType getRateType() {
        return rateType;
    }

    public void setRateType(RateType rateType) {
        this.rateType = rateType;
    }

    public DateAudit getDateAudit() {
        return dateAudit;
    }

    public void setDateAudit(DateAudit dateAudit) {
        this.dateAudit = dateAudit;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double calculateTotalPrice() {
        return this.originalPrice * this.rateType.getValue();
    }

    @Override
    public String toString() {
        return "Device [id=" + id + ", type=" + type + ", unitPrice=" + unitPrice + ", rateType=" + rateType
                + ", dateAudit=" + dateAudit + ", branchName=" + branchName + ", itemName=" + itemName + ", version="
                + version + ", originalPrice=" + originalPrice + "]";
    }
}
