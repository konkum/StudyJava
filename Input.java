import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Input {
    public static void main(String[] args) {
        inputValidation();
    }

    public static void inputValidation() {
        Scanner scanner = new Scanner(System.in);
        DeviceService deviceService = new DeviceService();
        BorrowingService borrowingService = new BorrowingService();
        EmployeeService employeeService = new EmployeeService();

        String mode = "device";
        System.out.println("Start entering devices. Type 'next' to switch, or 'end' to finish.");

        while (true) {
            if (mode.equals("device")) {
                System.out.println("Enter device details (or type 'next' at the end to switch to employee input):");
                Device device = createDeviceFromInput(scanner);
                if (device != null) {
                    deviceService.addDevice(device);
                }
                if (shouldSwitchMode(scanner)) {
                    mode = "employee";
                    System.out.println("Switched to employee input.");
                }
            }

            if (mode.equals("employee")) {
                System.out.println("Enter employee details (or type 'next' at the end to switch to borrowing input):");
                Employee employee = createEmployeeFromInput(scanner);
                if (employee != null) {
                    employeeService.addEmployee(employee);
                }
                if (shouldSwitchMode(scanner)) {
                    mode = "borrowing";
                    System.out.println("Switched to borrowing input.");
                }
            }

            if (mode.equals("borrowing")) {
                System.out.println("Enter borrowing details (or type 'next' or 'end' at the end to finish):");
                Borrowing borrowing = createBorrowingFromInput(scanner, deviceService.getAllDevices(),
                        employeeService.getAllEmployees());
                if (borrowing != null) {
                    borrowingService.addBorrowing(borrowing);
                    ;
                }
                if (shouldSwitchMode(scanner)) {
                    System.out.println("Finished input process.");
                    break;
                }
            }
        }

        FileUtils.getInstance().saveToFile(deviceService.getAllDevices(), "devices.dat");
        FileUtils.getInstance().saveToFile(borrowingService.getAllBorrowings(), "borrowings.dat");

        deviceService.getAllDevices().forEach(System.out::println);
        borrowingService.getAllBorrowings().forEach(System.out::println);
    }

    // Function to check if the user wants to switch modes or finish
    private static boolean shouldSwitchMode(Scanner scanner) {
        System.out.print("Type 'next' to switch to next input ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("next") || input.equals("end");
    }

    public static Device createDeviceFromInput(Scanner scanner) {

        try {
            System.out.print("Enter Device ID: ");
            int id = ValidationUtils.validateInteger(scanner.nextLine(), "ID");

            System.out.print("Enter Device Type (e.g., Mouse, Keyboard, etc.): ");
            Type type = ValidationUtils.validateType(scanner.nextLine());

            System.out.print("Enter Unit Price: ");
            double unitPrice = ValidationUtils.validateDouble(scanner.nextLine(), "UnitPrice");

            System.out.print("Enter RateType (New, LikeNew, Secondhand, Error): ");
            RateType rateType = ValidationUtils.validateRateType(scanner.nextLine());

            System.out.print("Enter Branch Name: ");
            String branchName = ValidationUtils.validateNonEmpty(scanner.nextLine(), "BranchName", 50);

            System.out.print("Enter Item Name: ");
            String itemName = ValidationUtils.validateNonEmpty(scanner.nextLine(), "ItemName", 50);

            System.out.print("Enter Version: ");
            String version = ValidationUtils.validateNonEmpty(scanner.nextLine(), "Version", 50);

            System.out.print("Enter Origin Price: ");
            double originPrice = ValidationUtils.validateDouble(scanner.nextLine(), "OriginPrice");

            return new Device(id, type, unitPrice, rateType, new DateAudit(), branchName, itemName, version,
                    originPrice);
        } catch (IllegalArgumentException e) {
            System.out.println("Input error: " + e.getMessage());
            return null;
        }
    }

    public static Employee createEmployeeFromInput(Scanner scanner) {

        try {
            System.out.print("Enter Employee ID: ");
            int id = ValidationUtils.validateInteger(scanner.nextLine(), "ID");

            System.out.print("Enter Full Name: ");
            String fullName = ValidationUtils.validateNonEmpty(scanner.nextLine(), "FullName", 50);

            System.out.print("Enter Address: ");
            String address = ValidationUtils.validateNonEmpty(scanner.nextLine(), "Address", 50);

            System.out.print("Enter Phone Number: ");
            String phoneNumber = ValidationUtils.validatePhoneNumber(scanner.nextLine());

            System.out.print("Enter Account Balance: ");
            int accountBalance = ValidationUtils.validateInteger(scanner.nextLine(), "AccountBalance");

            return new Employee(id, fullName, address, phoneNumber, accountBalance);
        } catch (IllegalArgumentException e) {
            System.out.println("Input error: " + e.getMessage());
            return null;
        }
    }

    public static Borrowing createBorrowingFromInput(Scanner scanner, List<Device> availableDevices,
            List<Employee> availableEmployees) {

        try {
            System.out.print("Enter Borrowing ID: ");
            int id = ValidationUtils.validateInteger(scanner.nextLine(), "ID");

            DateAudit dateAudit = new DateAudit();
            dateAudit.setHandOverDate(LocalDate.now());

            System.out.println("Select an Employee by ID: ");
            availableEmployees.forEach(System.out::println);
            int employeeId = ValidationUtils.validateInteger(scanner.nextLine(), "Employee ID");
            Employee employee = availableEmployees.stream()
                    .filter(emp -> emp.getId() == employeeId)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Employee ID"));

            System.out.println("Select Devices to Borrow (comma separated IDs): ");
            availableDevices.forEach(System.out::println);
            String[] deviceIds = scanner.nextLine().split(",");
            List<Device> selectedDevices = ValidationUtils.validateDeviceSelection(deviceIds, availableDevices);

            return new Borrowing(id, dateAudit, employee, selectedDevices);
        } catch (IllegalArgumentException e) {
            System.out.println("Input error: " + e.getMessage());
            return null; // Handle as per your need
        }
    }

    static void sampleData() {
        DeviceService deviceService = new DeviceService();
        BorrowingService borrowingService = new BorrowingService();
        EmployeeService employeeService = new EmployeeService();
        for (int i = 1; i <= 5; i++) {
            Employee employee = new Employee(i, "Employee " + i, "Address " + i, "01234567" + i, 1000 + i * 100);
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
