import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StorageNodeInterface extends Remote {
    String writeChunk(String chunkName, String data) throws RemoteException;
    String readChunk(String chunkName) throws RemoteException;
}