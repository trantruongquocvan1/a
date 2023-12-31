package othello;




public interface SpotListener {

	void spotClicked(Spot spot);
	void spotEntered(Spot spot);
	void spotExited(Spot spot);
}
