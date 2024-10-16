import java.io.Serializable;

public class Borrowing implements Serializable {
    private int id;
    private DateAudit dateAudit;
    private String totalPrice;
    private Employee employeeInfo;
    private Device deviceInfo;

    public Borrowing(int id, DateAudit dateAudit, String totalPrice, Employee employeeInfo, Device deviceInfo) {
        this.id = id;
        this.dateAudit = dateAudit;
        this.totalPrice = totalPrice;
        this.employeeInfo = employeeInfo;
        this.deviceInfo = deviceInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DateAudit getDateAudit() {
        return dateAudit;
    }

    public void setDateAudit(DateAudit dateAudit) {
        this.dateAudit = dateAudit;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Employee getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(Employee employeeInfo) {
        this.employeeInfo = employeeInfo;
    }

    public Device getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(Device deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
