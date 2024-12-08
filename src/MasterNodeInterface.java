import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

// Interface for Master Node
public interface MasterNodeInterface extends Remote {
    String listFiles() throws RemoteException;
    String getFileInfo(String filename) throws RemoteException;
    String registerFile(String filename, List<String> chunks) throws RemoteException;
    String deleteFile(String filename) throws RemoteException;
}