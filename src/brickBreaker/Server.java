package brickBreaker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

public class Server extends UnicastRemoteObject implements HighscoreManager {

	protected Server() throws RemoteException {
		super();
	}

	@Override
	public ArrayList<Score> getScores() throws RemoteException {
		
		JAXBElement<HighscoreListType> scoreList = null;
		
		try {
			JAXBContext ctx = JAXBContext.newInstance("brickBreaker");
			
			Unmarshaller umars = ctx.createUnmarshaller();
			
			Source source = new StreamSource(new FileInputStream(".\\scoreData\\highscores.xml"));
			scoreList = umars.unmarshal(source, HighscoreListType.class);
			
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if (scoreList == null) return null;
		
		ArrayList<Score> scores = new ArrayList<Score>();
		
		for (HighscoreType ht : scoreList.getValue().getHighscore()) {
			scores.add(new Score(ht.getPlayer(), ht.getBricksRemaining(), ht.getTimeMillis()));
		}
		
		return scores;
	}

	@Override
	public void uploadScore(Score score) throws RemoteException {
		
		JAXBElement<HighscoreListType> scoreList = null;
		
		try {
			JAXBContext ctx = JAXBContext.newInstance("brickBreaker");
			
			Unmarshaller umars = ctx.createUnmarshaller();
			
			Source source = new StreamSource(new FileInputStream(".\\scoreData\\highscores.xml"));
			scoreList = umars.unmarshal(source, HighscoreListType.class);
			
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if (scoreList == null) return;
		
		HighscoreType ht = new HighscoreType();
		ht.setPlayer(score.player);
		ht.setBricksRemaining(score.bricksRemaining);
		ht.setTimeMillis((int) score.timeMillis);
		
		HighscoreListType highscorelist = scoreList.getValue();
		highscorelist.getHighscore().add(ht);
		
		try {
			JAXBContext ctx = JAXBContext.newInstance("brickBreaker");
			
			Marshaller mars = ctx.createMarshaller();
			
			mars.marshal(highscorelist, new FileOutputStream(".\\scoreData\\highscores.xml"));
			
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
