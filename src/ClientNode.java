import java.rmi.*;
import java.util.*;

public class ClientNode {
    public static void main(String[] args) {
        try {
            MasterNodeInterface master = (MasterNodeInterface) Naming.lookup("//localhost/MasterNode");
            Scanner scanner = new Scanner(System.in);

            System.out.println("Distributed File System CLI");
            while (true) {
                System.out.print("Enter command (ls, register, info, delete, exit): ");
                String command = scanner.nextLine();

                if (command.equals("ls")) {
                    System.out.println("Files: " + master.listFiles());

                } else if (command.startsWith("register")) {
                    System.out.print("Enter filename: ");
                    String filename = scanner.nextLine();
                    System.out.print("Enter chunk locations (comma-separated): ");
                    List<String> chunks = Arrays.asList(scanner.nextLine().split(","));
                    System.out.println(master.registerFile(filename, chunks));

                } else if (command.startsWith("info")) {
                    System.out.print("Enter filename: ");
                    String filename = scanner.nextLine();
                    System.out.println(master.getFileInfo(filename));

                } else if (command.startsWith("delete")) {
                    System.out.print("Enter filename: ");
                    String filename = scanner.nextLine();
                    System.out.println(master.deleteFile(filename));

                } else if (command.equals("exit")) {
                    System.out.println("Exiting...");
                    break;

                } else {
                    System.out.println("Unknown command.");
                }
            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
