
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FileUtils {
    public static void save(String fileName, Object object) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(object);
            System.out.println("Object stored in file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
