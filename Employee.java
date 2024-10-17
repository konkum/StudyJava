import java.io.Serializable;

/**
 * Employee
 */
public class Employee implements Serializable {
    private int id;
    private String fullName;
    private String address;
    private String phoneNumber;
    private int accountNumber;

    public Employee(int id, String fullName, String address, String phoneNumber, int accountNumber) {
        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.accountNumber = accountNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", fullName=" + fullName + ", address=" + address + ", phoneNumber=" + phoneNumber
                + ", accountNumber=" + accountNumber + "]";
    }

}