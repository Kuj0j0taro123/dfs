import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.*;
import java.util.*;



// Implementation of Master Node
public class MasterNode extends UnicastRemoteObject implements MasterNodeInterface {
    private Map<String, List<String>> metadata; // Filename -> List of chunk locations

    public MasterNode() throws RemoteException {
        metadata = new HashMap<>();
    }

    @Override
    public synchronized String listFiles() throws RemoteException {
        return String.join(", ", metadata.keySet());
    }

    @Override
    public synchronized String getFileInfo(String filename) throws RemoteException {
        if (metadata.containsKey(filename)) {
            return "Chunks for " + filename + ": " + metadata.get(filename).toString();
        } else {
            return "File not found.";
        }
    }

    @Override
    public synchronized String registerFile(String filename, List<String> chunks) throws RemoteException {
        metadata.put(filename, chunks);
        return "File " + filename + " registered successfully.";
    }

    @Override
    public synchronized String deleteFile(String filename) throws RemoteException {
        if (metadata.containsKey(filename)) {
            metadata.remove(filename);
            return "File " + filename + " deleted successfully.";
        } else {
            return "File not found.";
        }
    }

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099); // Start RMI registry
            MasterNode masterNode = new MasterNode();
            Naming.rebind("MasterNode", masterNode);
            System.out.println("Master Node is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
