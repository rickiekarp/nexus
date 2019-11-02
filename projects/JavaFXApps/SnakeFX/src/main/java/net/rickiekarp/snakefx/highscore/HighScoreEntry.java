package net.rickiekarp.snakefx.highscore;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Date;

public class HighScoreEntry implements Comparable<HighScoreEntry> {

	@JsonIgnore
	private int ranking;

	private int id;
	private String name;
	private int points;
	private Date dateAdded;

	public HighScoreEntry(){
	}

	public HighScoreEntry(String playername, int points){
		this.name = playername;
		this.points = points;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	@Override
	public int compareTo(HighScoreEntry o) {
		return Integer.compare(o.points, this.points);
	}

	@Override
	public String toString(){
		return ":" + name + "->" + points + " points";
	}

}
