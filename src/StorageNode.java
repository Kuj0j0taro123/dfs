import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.*;
import java.io.*;
import java.util.*;



public class StorageNode extends UnicastRemoteObject implements StorageNodeInterface {
    private String storagePath;

    public StorageNode(String storagePath) throws RemoteException {
        this.storagePath = storagePath;
        new File(storagePath).mkdirs(); // Create storage directory
    }

    @Override
    public String writeChunk(String chunkName, String data) throws RemoteException {
        try (FileWriter writer = new FileWriter(storagePath + "/" + chunkName)) {
            writer.write(data);
            return "Chunk " + chunkName + " written successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to write chunk.";
        }
    }

    @Override
    public String readChunk(String chunkName) throws RemoteException {
        try (BufferedReader reader = new BufferedReader(new FileReader(storagePath + "/" + chunkName))) {
            StringBuilder data = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line).append("\n");
            }
            return data.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to read chunk.";
        }
    }

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1098); // Start RMI registry
            StorageNode storageNode = new StorageNode("storage");
            Naming.rebind("StorageNode", storageNode);
            System.out.println("Storage Node is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
