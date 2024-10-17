import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class DeviceService {
    private List<Device> devices = new ArrayList<>();

    public void printDevices() {
        devices.forEach(System.out::println);
    }

    public void createDevice(int id, Type type, double unitPrice, RateType rateType, DateAudit dateAudit,
            String branchName,
            String itemName, String version, double originalPrice) {
        devices.add(new Device(id, type, unitPrice, rateType, dateAudit, branchName, itemName, version, originalPrice));
    }

    public void addDevice(Device device) {
        devices.add(device);
    }

    public void addListDevice(List<Device> newDevices) {
        this.devices.addAll(newDevices);
    }

    public void replaceListDevice(List<Device> newDevices) {
        this.devices = newDevices;
    }

    public List<Device> getAllDevices() {
        return devices;
    }

    public Optional<Device> findDeviceById(int id) {
        return devices.stream().filter(device -> device.getId() == id).findFirst();
    }

    public boolean updateDevice(int id, Device newDevice) {
        Optional<Device> optionalDevice = findDeviceById(id);

        if (optionalDevice.isPresent()) {
            Device existingDevice = optionalDevice.get();

            // Update the fields of the existing device
            existingDevice.setType(newDevice.getType());
            existingDevice.setUnitPrice(newDevice.getUnitPrice());
            existingDevice.setRateType(newDevice.getRateType());
            existingDevice.setBranchName(newDevice.getBranchName());
            existingDevice.setItemName(newDevice.getItemName());
            existingDevice.setVersion(newDevice.getVersion());
            existingDevice.setOriginalPrice(newDevice.getOriginalPrice());
            existingDevice.setDateAudit(newDevice.getDateAudit());

            System.out.println("Device with ID " + id + " has been updated.");
            return true;
        } else {
            System.out.println("Device with ID " + id + " not found.");
            return false;
        }
    }

    public void removeDevice(int id) {
        devices.removeIf(device -> device.getId() == id);
    }

    public List<Device> sortDevicesByPrice(SortDirection sortDirection) {
        return devices.stream()
                .sorted((device1, device2) -> {
                    int result = Double.compare(device1.getUnitPrice(), device2.getUnitPrice());
                    return sortDirection == SortDirection.ASC ? result : -result;
                })
                .collect(Collectors.toList());
    }

    public List<Device> sortDevicesByCreatedDate(SortDirection sortDirection) {
        return devices.stream()
                .sorted((device1, device2) -> {
                    int result = device1.getDateAudit().getCreateAt()
                            .compareTo(device2.getDateAudit().getCreateAt());
                    return sortDirection == SortDirection.ASC ? result : -result;
                })
                .collect(Collectors.toList());
    }

    public List<Device> searchDevicesByName(String name) {
        return devices.stream()
                .filter(device -> device.getItemName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Device> searchDevicesByType(Type type) {
        return devices.stream()
                .filter(device -> device.getType().equals(type))
                .collect(Collectors.toList());
    }

    public List<Device> searchDevicesByDate(LocalDate date) {
        return devices.stream()
                .filter(device -> device.getDateAudit().getCreateAt().equals(date))
                .collect(Collectors.toList());
    }

    public List<Device> searchDevicesByRateType(RateType rateType) {
        return devices.stream()
                .filter(device -> device.getRateType().equals(rateType))
                .collect(Collectors.toList());
    }
}
