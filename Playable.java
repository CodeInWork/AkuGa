package org.AkuGanew;



public interface Playable {
	String getForeGround();
	String getBackGround();
	void setForeGround(String audioFile);
	void setBackGround(String audioFile);
	boolean getPlayForeGround();
	boolean getPlayBackGround();
	void setPlayForeGround(boolean playAudio);
	void setPlayBackGround(boolean playAudio);
	
}
