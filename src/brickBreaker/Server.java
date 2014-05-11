package brickBreaker;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server extends UnicastRemoteObject implements HighscoreManager {

	protected Server() throws RemoteException {
		super();
	}

	@Override
	public ArrayList<Score> getScores() throws RemoteException {
		
		ArrayList<Score> scores = new ArrayList<Score>();
		
		for (int i = 0; i < 10; i++) {
			scores.add(new Score("Matyas", i, i*1000L));
		}
		
		return scores;
	}

	@Override
	public void uploadScore(Score score) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public static void main(String args[]) {

		try {
			Server server = new Server();

			Registry registry = LocateRegistry.createRegistry(1099);
			registry.rebind("regEntry_HighscoreManager", server);

			System.out.println("Server ready");
		} catch (Exception e) {
			System.out.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
}
