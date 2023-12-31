package othello;


import java.util.Iterator;




public interface SpotBoard extends Iterable<Spot> {

	int getSpotWidth();
 
	int getSpotHeight();

	Spot getSpotAt(int x, int y);

	void addSpotListener(SpotListener spot_listener);

	void removeSpotListener(SpotListener spot_listener);

	Iterator<Spot> iterator();

	Spot getSpotAt1(int x, int y);

	SpotBoard copyBoard();

	void placePiece(Spot move, OthelloAI othelloAI);

}