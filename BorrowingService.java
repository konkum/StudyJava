import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BorrowingService {
    private List<Borrowing> borrowings = new ArrayList<>();

    public void createBorrowing(int id, DateAudit dateAudit, Employee employee, List<Device> devices) {
        borrowings.add(new Borrowing(0, dateAudit, employee, devices));
    }

    public void addBorrowing(Borrowing borrowing) {
        borrowings.add(borrowing);
    }

    public void addListBorrowing(List<Borrowing> newBorrowings) {
        this.borrowings.addAll(newBorrowings);
    }

    public void printBorrowing() {
        borrowings.forEach(System.out::println);
    }

    public List<Borrowing> getAllBorrowings() {
        return borrowings;
    }

    public void replaceListBorrowing(List<Borrowing> newBorrowings) {
        borrowings = newBorrowings;
    }

    public void removeBorrowing(int id) {
        borrowings.removeIf(borrowing -> borrowing.getId() == id);
    }

    public boolean updateBorrowing(int id, Borrowing newBorrowing) {
        Optional<Borrowing> optionalBorrowing = findBorrowingById(id);

        if (optionalBorrowing.isPresent()) {
            Borrowing existingBorrowing = optionalBorrowing.get();

            // Update the fields of the existing device
            existingBorrowing.setId(newBorrowing.getId());
            existingBorrowing.setDateAudit(newBorrowing.getDateAudit());
            existingBorrowing.setEmployeeInfo(newBorrowing.getEmployeeInfo());
            existingBorrowing.setDeviceInfo(newBorrowing.getDeviceInfo());

            System.out.println("Borrowing with ID " + id + " has been updated.");
            return true;
        } else {
            System.out.println("Borrowing with ID " + id + " not found.");
            return false;
        }
    }

    public List<Borrowing> sortBorrowingsByTotalPrice(SortDirection sortDirection) {
        return borrowings.stream()
                .sorted((b1, b2) -> {
                    int result = Double.compare(b1.getTotalPrice(), b2.getTotalPrice());
                    return sortDirection == SortDirection.ASC ? result : -result;
                })
                .toList();
    }

    public List<Borrowing> sortBorrowingsByHandoverDate(SortDirection sortDirection) {
        return borrowings.stream()
                .sorted((b1, b2) -> {
                    int result = b1.getDateAudit().getHandOverDate().compareTo(b2.getDateAudit().getHandOverDate());
                    return sortDirection == SortDirection.ASC ? result : -result;
                })
                .toList();
    }

    public List<Borrowing> searchBorrowingByTotalPrice(double totalPrice) {
        return borrowings.stream()
                .filter(borrowing -> borrowing.getTotalPrice() == totalPrice)
                .collect(Collectors.toList());
    }

    public List<Borrowing> searchBorrowingByHandoverDate(LocalDate handoverDate) {
        return borrowings.stream()
                .filter(borrowing -> borrowing.getDateAudit().getHandOverDate().equals(handoverDate))
                .collect(Collectors.toList());
    }

    public List<Borrowing> searchBorrowingBeforeNow(int days, long ammount) {
        LocalDate date = LocalDate.now().minusDays(days);

        return borrowings.stream()
                .filter(borrowing -> borrowing.getDateAudit().getHandOverDate().isBefore(date))
                .limit(ammount)
                .collect(Collectors.toList());
    }

    public List<Borrowing> searchBorrowingByDeviceName(String name) {
        return borrowings.stream()
                .filter((borrowing) -> {
                    for (Device element : borrowing.getDeviceInfo()) {
                        return element.getItemName().equals(name);
                    }

                    return false;
                })
                .collect(Collectors.toList());
    }

    public List<Borrowing> searchBorrowingByDeviceType(Type type) {
        return borrowings.stream()
                .filter((borrowing) -> {
                    for (Device element : borrowing.getDeviceInfo()) {
                        return element.getType().equals(type);
                    }

                    return false;
                })
                .collect(Collectors.toList());
    }

    public Optional<Borrowing> findBorrowingById(int id) {
        return borrowings.stream().filter(borrowing -> borrowing.getId() == id).findFirst();
    }

    public boolean transferDevice(int borrowingIdFrom, int borrowingIdTo, int deviceId) {
        Borrowing existingBorrowing = findBorrowingById(borrowingIdFrom).orElse(null);
        Device existingDevice = existingBorrowing.getDeviceInfo().stream().filter(device -> device.getId() == deviceId)
                .findFirst().orElse(null);
        if (existingBorrowing == null || existingDevice == null) {
            return false;
        }

        // Remove device from the existing borrowing
        existingBorrowing.removeDevice(existingDevice);

        Borrowing newBorrowing = findBorrowingById(borrowingIdTo).orElse(null);
        if (newBorrowing == null) {
            System.out.println("TEST");
            return false;
        }

        newBorrowing.addDevice(existingDevice);
        return true;
    }

}