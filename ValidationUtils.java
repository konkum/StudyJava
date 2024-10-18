import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * ValidationUtils
 */
public class ValidationUtils {
    public static int validateInteger(String input, String fieldName) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " must be a valid integer.");
        }
    }

    public static double validateDouble(String input, String fieldName) {
        try {
            double value = Double.parseDouble(input);
            if (value < 0) {
                throw new IllegalArgumentException(fieldName + " must be a positive number.");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " must be a valid number.");
        }
    }

    public static String validateNonEmpty(String input, String fieldName, int maxLength) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
        if (input.length() > maxLength) {
            throw new IllegalArgumentException(fieldName + " cannot exceed " + maxLength + " characters.");
        }
        return input;
    }

    public static LocalDate validateDate(String input, String fieldName) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(input, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(fieldName + " must be in the format yyyy-MM-dd.");
        }
    }

    public static Type validateType(String input) {
        try {
            return Type.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid RateType. Must be one of: MOUSE, KEYBOARD, DISPLAY, CASE, CABLE.");
        }
    }

    public static RateType validateRateType(String input) {
        try {
            return RateType.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid RateType. Must be one of: New, LikeNew, Secondhand, Error.");
        }
    }

    public static List<Device> validateDeviceSelection(String[] deviceIds, List<Device> availableDevices) {
        List<Device> selectedDevices = new ArrayList<>();

        for (String idStr : deviceIds) {
            int id = validateInteger(idStr.trim(), "Device ID");
            Device device = availableDevices.stream()
                    .filter(dev -> dev.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Device ID: " + id));
            selectedDevices.add(device);
        }

        return selectedDevices;
    }

    public static String validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
            throw new IllegalArgumentException("PhoneNumber must be a valid 10-digit number.");
        }
        return phoneNumber;
    }
}