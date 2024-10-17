import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private static final String DEVICE_PATH = "devices.dat";
    private static final String BORROWING_PATH = "borrowings.dat";

    public static void main(String[] args) throws Exception {
        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        System.setOut(out);
        DeviceService deviceService = new DeviceService();
        BorrowingService borrowingService = new BorrowingService();
        deviceService.addListDevice(FileUtils.getInstance().loadFromFile(DEVICE_PATH));
        borrowingService.addListBorrowing(FileUtils.getInstance().loadFromFile(BORROWING_PATH));

        // ADD AND VIEW DEVICE
        deviceService.createDevice(
                11, Type.MOUSE, 2123.2141, RateType.SECONDHAND, new DateAudit(), "Branch 3", "Logitech G",
                "11", 300.50);

        FileUtils.getInstance().saveToFile(deviceService.getAllDevices(), DEVICE_PATH);
        deviceService.replaceListDevice(FileUtils.getInstance().loadFromFile(DEVICE_PATH));
        System.out.println("ADD AN ITEM TO DEVICE FILE:\n");
        deviceService.printDevices();

        // REMOVE AND VIEW DEVICE
        deviceService.removeDevice(2);
        FileUtils.getInstance().saveToFile(deviceService.getAllDevices(), DEVICE_PATH);
        deviceService.replaceListDevice(FileUtils.getInstance().loadFromFile(DEVICE_PATH));
        System.out.println("REMOVE AN ITEM TO DEVICE FILE:\n");
        deviceService.printDevices();

        // EDIT AND VIEW DEVICE
        Device previouseDevice = deviceService.findDeviceById(6).orElse(null);
        previouseDevice.getDateAudit().setUpdatedAt(LocalDate.now());
        previouseDevice.setBranchName("Branch66");
        previouseDevice.setItemName("Lawdwadwa");

        deviceService.updateDevice(6, previouseDevice);
        FileUtils.getInstance().saveToFile(deviceService.getAllDevices(), DEVICE_PATH);
        deviceService.replaceListDevice(FileUtils.getInstance().loadFromFile(DEVICE_PATH));
        System.out.println("UPDATE AN ITEM TO DEVICE FILE:\n");
        deviceService.printDevices();

        // VIEW SORT BY PRICE
        System.out.println("SORT BY PRICE DEVICE:\n");
        deviceService.replaceListDevice(FileUtils.getInstance().loadFromFile(DEVICE_PATH));
        deviceService.sortDevicesByPrice(SortDirection.DESC).forEach(System.out::println);

        // VIEW SORT BY CREATE DATE
        System.out.println("SORT BY CREATED DATE:\n");
        deviceService.replaceListDevice(FileUtils.getInstance().loadFromFile(DEVICE_PATH));
        deviceService.sortDevicesByCreatedDate(SortDirection.DESC).forEach(System.out::println);

        // SEARCH DATE
        System.out.println("SEARCH BY CREATED DATE:\n");
        deviceService.replaceListDevice(FileUtils.getInstance().loadFromFile(DEVICE_PATH));
        deviceService.searchDevicesByDate(LocalDate.of(2024, 10, 17)).forEach(System.out::println);

        // SEARCH NAME
        System.out.println("SEARCH BY NAME:\n");
        deviceService.replaceListDevice(FileUtils.getInstance().loadFromFile(DEVICE_PATH));
        deviceService.searchDevicesByName("DeviceName 4").forEach(System.out::println);

        // SEARCH RATE TYPE
        System.out.println("SEARCH BY RATE TYPE:\n");
        deviceService.replaceListDevice(FileUtils.getInstance().loadFromFile(DEVICE_PATH));
        deviceService.searchDevicesByRateType(RateType.NEW).forEach(System.out::println);

        // SEARCH TYPE
        System.out.println("SEARCH BY TYPE:\n");
        deviceService.replaceListDevice(FileUtils.getInstance().loadFromFile(DEVICE_PATH));
        deviceService.searchDevicesByType(Type.DISPLAY).forEach(System.out::println);

        // ADD AND VIEW BORROWING
        Employee employee = new Employee(11, "Employee 11", "Address 11", "012321241", 2000);
        DateAudit newDateAudit = new DateAudit();
        newDateAudit.setHandOverDate(LocalDate.now());
        List<Device> newDevices = deviceService.getAllDevices().stream()
                .filter(device -> device.getId() > 1 && device.getId() <= 4).collect(Collectors.toList());
        borrowingService.createBorrowing(11, newDateAudit, employee, newDevices);

        FileUtils.getInstance().saveToFile(borrowingService.getAllBorrowings(), BORROWING_PATH);
        borrowingService.replaceListBorrowing(FileUtils.getInstance().loadFromFile(BORROWING_PATH));
        System.out.println("ADD AN ITEM TO BORROWING FILE:\n");
        borrowingService.printBorrowing();

        // REMOVE AND VIEW BORROWING
        borrowingService.removeBorrowing(2);
        FileUtils.getInstance().saveToFile(borrowingService.getAllBorrowings(), BORROWING_PATH);
        borrowingService.replaceListBorrowing(FileUtils.getInstance().loadFromFile(BORROWING_PATH));
        System.out.println("REMOVE AN ITEM TO BORROWING FILE:\n");
        borrowingService.printBorrowing();

        // EDIT AND VIEW BORROWING
        Borrowing previouseBorrowing = borrowingService.findBorrowingById(6).orElse(null);
        previouseBorrowing.getDateAudit().setUpdatedAt(LocalDate.now());
        previouseBorrowing.setDeviceInfo(newDevices);
        previouseBorrowing.setTotalPrice(previouseBorrowing.calculateTotalPrice());

        borrowingService.updateBorrowing(6, previouseBorrowing);
        FileUtils.getInstance().saveToFile(borrowingService.getAllBorrowings(), BORROWING_PATH);
        borrowingService.replaceListBorrowing(FileUtils.getInstance().loadFromFile(BORROWING_PATH));
        System.out.println("UPDATE AN ITEM TO BORROWING FILE:\n");
        borrowingService.printBorrowing();

        // VIEW SORT BY PRICE
        System.out.println("SORT BY PRICE BORROWING:\n");
        borrowingService.replaceListBorrowing(FileUtils.getInstance().loadFromFile(BORROWING_PATH));
        borrowingService.sortBorrowingsByTotalPrice(SortDirection.DESC).forEach(System.out::println);

        // VIEW SORT BY HANDOVER DATE
        System.out.println("SORT BY HANDOVER DATE:\n");
        borrowingService.replaceListBorrowing(FileUtils.getInstance().loadFromFile(BORROWING_PATH));
        borrowingService.sortBorrowingsByHandoverDate(SortDirection.DESC).forEach(System.out::println);

        // SEARCH DATE
        System.out.println("SEARCH BY HANDOVER DATE:\n");
        borrowingService.replaceListBorrowing(FileUtils.getInstance().loadFromFile(BORROWING_PATH));
        borrowingService.searchBorrowingByHandoverDate(LocalDate.of(2024, 10,
                6)).forEach(System.out::println);

        // SEARCH NAME
        System.out.println("SEARCH BY NAME:\n");
        borrowingService.replaceListBorrowing(FileUtils.getInstance().loadFromFile(BORROWING_PATH));
        borrowingService.searchBorrowingByDeviceName("DeviceName 5").forEach(System.out::println);

        // SEARCH TYPE
        System.out.println("SEARCH BY TYPE:\n");
        borrowingService.replaceListBorrowing(FileUtils.getInstance().loadFromFile(BORROWING_PATH));
        borrowingService.searchBorrowingByDeviceType(Type.DISPLAY).forEach(System.out::println);

        // SEARCH PRICE
        System.out.println("SEARCH BY PRICE:\n");
        borrowingService.replaceListBorrowing(FileUtils.getInstance().loadFromFile(BORROWING_PATH));
        borrowingService.searchBorrowingByTotalPrice(88.0).forEach(System.out::println);

        // COUNT DEVICE AND BORROWING
        System.out.println("NUMBER OF DEVICE AND BORROWING:\n");
        System.out.println("NUMBER OF DEVICE: " + deviceService.getAllDevices().size());
        System.out.println("NUMBER OF BORROWING: " + borrowingService.getAllBorrowings().size());

        // TOTAL PRICE OF DEVICE AND BORROWING
        System.out.println("TOTAL PRICE OF DEVICE AND BORROWING:\n");
        for (Device device : deviceService.getAllDevices()) {
            System.out.println("DEVICE: " + device.getId() + " HAVE TOTAL PRICE: " + device.calculateTotalPrice());
        }

        for (Borrowing borrowing : borrowingService.getAllBorrowings()) {
            System.out.println("BORROWING: " + borrowing.getId() + " HAVE TOTAL PRICE: " + borrowing.getTotalPrice());
        }

        // BORROWING FROM 15 DAYS AGO
        System.out.println("BORROWING FROM 15 DAYS AGO:\n");
        borrowingService.searchBorrowingBeforeNow(15, 5).forEach(System.out::println);

        // EVICT DEVICE FROM ONE BORROW TO ANOTHER BORROW
        System.out.println(" EVICT DEVICE FROM ONE BORROW TO ANOTHER BORROW:\n");
        if (borrowingService.transferDevice(1, 4, 2)) {
            System.out.println("Transfer Device Success");
        } else {
            System.out.println("Transfer Device Failure");
        }

    }
}
