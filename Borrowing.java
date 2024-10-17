import java.io.Serializable;
import java.util.List;

public class Borrowing implements Serializable {
    private int id;
    private DateAudit dateAudit;
    private double totalPrice;
    private Employee employeeInfo;
    private List<Device> deviceInfo;

    public Borrowing(int id, DateAudit dateAudit, Employee employeeInfo, List<Device> deviceInfo) {
        this.id = id;
        this.dateAudit = dateAudit;
        this.employeeInfo = employeeInfo;
        this.deviceInfo = deviceInfo;
        this.totalPrice = calculateTotalPrice();
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Employee getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(Employee employeeInfo) {
        this.employeeInfo = employeeInfo;
    }

    public List<Device> getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(List<Device> deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public double calculateTotalPrice() {
        return deviceInfo.stream().mapToDouble(Device::calculateTotalPrice).sum();
    }

    public void addDevice(Device device) {
        deviceInfo.add(device);
    }

    public void removeDevice(Device device) {
        deviceInfo.remove(device);
    }

    @Override
    public String toString() {
        return "Borrowing [id=" + id + ", dateAudit=" + dateAudit + ", totalPrice=" + totalPrice + ", employeeInfo="
                + employeeInfo + ", deviceInfo=" + deviceInfo + "]";
    }
}
