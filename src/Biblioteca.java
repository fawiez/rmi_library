import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Biblioteca extends Remote {
    List<String> listarLivros() throws RemoteException;
    boolean emprestarLivro(String titulo) throws RemoteException;
    boolean devolverLivro(String titulo) throws RemoteException;
}
