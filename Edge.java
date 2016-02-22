package org.AkuGanew;

import java.util.Observable;

/*
 * represents the possible choices you have at each knot. It is carrying the Destination you reach 
 * by the choice (Place destination) and a boolean which indicates weather or not the destination is updated.
 * The new state is represented by the integer state.
 */

public class Edge extends Observable implements Playable {
	private Place destination;
	private int state;
	private String name;
	private String audioForeGround;
	private String audioBackGround;
	private boolean placeUpdate;
	private boolean end;
	private boolean playForeGround;     
	private boolean playBackGround;
	
	public Edge(String name) {
		super();
		this.name = name;
		this.state = 0;
		this. placeUpdate = false;
		audioForeGround = "ungesetztes Audo file";
		audioBackGround = "ungesetztes Audo file";
		destination = new Place("ungesetzter Name");
		end = false;
		playForeGround = false;
		playBackGround = false;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public boolean isPlaceUpdate() {
		return placeUpdate;
	}

	public void setPlaceUpdate(boolean placeUpdate) {
		this.placeUpdate = placeUpdate;
	}
	
	@Override
	public boolean getPlayForeGround() {
		return playForeGround;
	}
	
	@Override
	public boolean getPlayBackGround() {
		return playBackGround;
	}

	@Override
	public void setPlayForeGround(boolean shouldPlay) {
		this.playForeGround = shouldPlay;
		setChanged();            
		notifyObservers();    
		
	}
	
	@Override
	public void setPlayBackGround(boolean shouldPlay) {
		this.playBackGround = shouldPlay;
		setChanged();
		notifyObservers();
	}
	
	//Getters and Setters
	@Override
	public String getForeGround() {
		return audioForeGround;
	}
	
	@Override
	public String getBackGround() {
		return audioBackGround;
	}
	
	@Override
	public void setForeGround(String audio) {
		this.audioForeGround = audio;
	}
	
	@Override
	public void setBackGround(String audio) {
		this.audioBackGround = audio;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	
	public Place getDestination() {
		return destination;
	}


	public void setDestination(Place destination) {
		this.destination = destination;
	}

	public Boolean getEnd() {
		return end;
	}

	public void setEnd(Boolean end) {
		this.end = end;
	}
	
	
	
}
