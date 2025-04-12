import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class BibliotecaServer extends UnicastRemoteObject implements Biblioteca {

    private Map<String, Boolean> livros;

    protected BibliotecaServer() throws RemoteException {
        super();
        livros = new HashMap<>();
        livros.put("O Senhor dos Anéis", true);
        livros.put("Jogos Vorazes", true);
        livros.put("Harry Potter", true);
    }

    @Override
    public List<String> listarLivros() throws RemoteException {
        List<String> disponiveis = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : livros.entrySet()) {
            if (entry.getValue()) {
                disponiveis.add(entry.getKey());
            }
        }
        return disponiveis;
    }

    @Override
    public boolean emprestarLivro(String titulo) throws RemoteException {
        if (livros.containsKey(titulo) && livros.get(titulo)) {
            livros.put(titulo, false);
            return true;
        }
        return false;
    }

    @Override
    public boolean devolverLivro(String titulo) throws RemoteException {
        if (livros.containsKey(titulo) && !livros.get(titulo)) {
            livros.put(titulo, true);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        try {
            System.out.println("Iniciando servidor RMI...");
            BibliotecaServer server = new BibliotecaServer();
            LocateRegistry.createRegistry(1100);
            Naming.rebind("//localhost:1100/MeuServico", server);
            System.out.println("Servidor Biblioteca pronto...");
        } catch (Exception e) {
            System.err.println("Erro no servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}