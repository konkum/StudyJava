import java.io.Serializable;

public class Device implements Serializable {
    private int id;
    private Type type;
    private float unitPrice;
    private RateType rateType;
    private DateAudit dateAudit;
    private String branchName;
    private String itemName;
    private String version;
    private float originalPrice;

    public Device(int id, Type type, float unitPrice, RateType rateType, DateAudit dateAudit, String branchName,
            String itemName, String version, float originalPrice) {
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

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
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

    public float getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(float originalPrice) {
        this.originalPrice = originalPrice;
    }
}
