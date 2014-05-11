package brickBreaker;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

//@XmlType(name = "highscoreType")
public class Score implements Serializable {
	
	//@XmlElement(required = true)
	public String player;
	
	//@XmlElement(required = true)
	public int bricksRemaining;
	
	//@XmlElement(required = true)
	public long timeMillis;
	
	public Score(String player, int bricksRemaining, long timeMillis) {
		this.player = player;
		this.bricksRemaining = bricksRemaining;
		this.timeMillis = timeMillis;
	}
}
