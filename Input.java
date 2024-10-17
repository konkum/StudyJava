import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Input {
    public static void main(String[] args) {
        DeviceService deviceService = new DeviceService();
        BorrowingService borrowingService = new BorrowingService();
        EmployeeService employeeService = new EmployeeService();
        for (int i = 1; i <= 5; i++) {
            Employee employee = new Employee(i, "Employee " + i, "Address " + i, "Phone" + i, 1000 + i * 100);
            employeeService.addEmployee(employee);
        }

        // Generate 10 devices
        for (int i = 1; i <= 10; i++) {
            DateAudit dateAudit = new DateAudit();
            dateAudit.setCreateAt(LocalDate.now().minusDays(i + 10));
            Device device = new Device(
                    i,
                    Type.values()[i % Type.values().length],
                    100 + i * 10,
                    RateType.values()[i % RateType.values().length],
                    dateAudit,
                    "Branch " + (i % 3 + 1),
                    "DeviceName " + i,
                    "Version " + (1 + i % 3),
                    100 + i * 10);
            deviceService.addDevice(device);
        }

        for (int i = 1; i <= 10; i++) {
            List<Device> borrowedDevices = new ArrayList<>();
            borrowedDevices.add(deviceService.getAllDevices().get(i % 10)); // Borrow one device

            Borrowing borrowing = new Borrowing(
                    i,
                    new DateAudit(),
                    employeeService.getEmployee(i % 5),
                    borrowedDevices);
            borrowing.getDateAudit().setHandOverDate(LocalDate.now().minusDays(10 + i));
            borrowingService.addBorrowing(borrowing);
        }

        FileUtils.getInstance().saveToFile(deviceService.getAllDevices(), "devices.dat");
        FileUtils.getInstance().saveToFile(borrowingService.getAllBorrowings(), "borrowings.dat");
    }
}
