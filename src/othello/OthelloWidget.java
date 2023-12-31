package othello;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OthelloWidget extends JPanel implements ActionListener, SpotListener {



	private enum Player {
		BLACK, WHITE
	};

	private JSpotBoard _board; 
	private JLabel _message; 
	private boolean _game_won; 
	private boolean _game_draw; 
	private int _gameint;
	public int _noBlack;
	public int _noWhite;
	private String _winnerName;
	public HashSet<Point> placeablePositions;

	private Player _next_to_play; 

	public OthelloWidget() {




		_board = new JSpotBoard(8, 8, new Color(0.5f, 0.5f, 0.5f));
		_size = 8;

		Spot initSpot1 = _board.getSpotAt(4, 4);
		Spot initSpot2 = _board.getSpotAt(4, 3);
		Spot initSpot3 = _board.getSpotAt(3, 4);
		Spot initSpot4 = _board.getSpotAt(3, 3);
		
		placeablePositions = new HashSet<Point>();
		
		_message = new JLabel();

	

		setLayout(new BorderLayout());
		add(_board, BorderLayout.CENTER);

	

		JPanel reset_message_panel = new JPanel();
		reset_message_panel.setLayout(new BorderLayout());

	

		JButton reset_button = new JButton("Restart");
		reset_button.addActionListener(this);
		reset_message_panel.add(reset_button, BorderLayout.EAST);
		reset_message_panel.add(_message, BorderLayout.CENTER);

	

		add(reset_message_panel, BorderLayout.SOUTH);

	
		_board.addSpotListener(this);

	
		resetGame();
		spotClicked(initSpot3);

		spotClicked(initSpot4);
		spotClicked(initSpot2);
		spotClicked(initSpot1);

	}



	private void resetGame() {


		Spot initSpot1 = _board.getSpotAt(4, 4);
		Spot initSpot2 = _board.getSpotAt(4, 3);
		Spot initSpot3 = _board.getSpotAt(3, 4);
		Spot initSpot4 = _board.getSpotAt(3, 3);
		_gameint = 0;
		
		for (Spot s : _board) {
			s.clearSpot();
			s.setSpotColor(Color.BLACK);
		}


		_game_won = false;
		_game_draw = false;
		_next_to_play = Player.BLACK;


		spotClicked(initSpot3);

		spotClicked(initSpot4);
		spotClicked(initSpot2);
		spotClicked(initSpot1);
		
		_message.setText("Welcome to Othello. Black to play");
	}


	@Override
	public void actionPerformed(ActionEvent e) {

		resetGame();
	}

	

	@Override
	public void spotClicked(Spot s) {

		if (isLegalMove(s) == false && _gameint >= 4) {
			return;
		}
		
	
		if (_game_won) {
			return;
		}

		flipPieces(s);
		

		String player_name = null;
		String next_player_name = null;
		Color player_color = null;

		if (_next_to_play == Player.BLACK) {
			player_color = Color.BLACK;
			player_name = "Black";
			next_player_name = "White";
			_next_to_play = Player.WHITE;
		} else {
			player_color = Color.WHITE;
			player_name = "White";
			next_player_name = "Black";
			_next_to_play = Player.BLACK;
		}

	
		if (s.isEmpty()) {
			s.setSpotColor(player_color);
			s.toggleSpot();

		} else {

			if (_next_to_play == Player.BLACK) {
				player_color = Color.WHITE;
				player_name = "White";
				next_player_name = "White";
				_next_to_play = Player.WHITE;

			} else {

				player_color = Color.BLACK;
				player_name = "Black";
				next_player_name = "Black";
				_next_to_play = Player.BLACK;

			}
		}

		int numOfValids = 0;
		_gameint ++;
		for (Spot Spotter : _board) {

			Spotter.unhighlightSpot();
			if (isLegalMove(Spotter)) {
				numOfValids++;

			}
		}
		
		if (numOfValids == 0 && _gameint >= 4) {
			
			if (_next_to_play == Player.BLACK) {
				player_color = Color.WHITE;
				player_name = "White";
				next_player_name = "White";
				_next_to_play = Player.WHITE;

			} else {

				player_color = Color.BLACK;
				player_name = "Black";
				next_player_name = "Black";
				_next_to_play = Player.BLACK;
			}
		}

		Color nextCol;
		if (player_color == Color.BLACK) {
			nextCol = Color.WHITE;
		} else {
			nextCol = Color.BLACK;
		}
		
	

		if (_gameint >= 4) {
		if (s.isEmpty()) {
			_message.setText(next_player_name + " to play.");
		} else {
			if ((checkWin() == true) && (_winnerName != (null))) {

				_message.setText(_winnerName + " wins! " + " Score: " + _noBlack + " to " + _noWhite);
				
			} else {

				if ((_winnerName == null) && (_game_won == true)) {

					_message.setText(" Draw game.");

				} else {

					_message.setText(next_player_name + " to play.");
				}
			}
		}
		}
	}

	@Override
	public void spotEntered(Spot s) {


		if (_game_won) {
			return;
		}
		if (isLegalMove(s) == true) {
			
		s.highlightSpot();
		}
	}

	@Override
	public void spotExited(Spot s) {

		
			
			if (_game_won == true) {
				return;
			}

		s.unhighlightSpot();
		
	}
// Kiếm tra xem trò chơi kết thúc hay chưa 
	public boolean checkWin() {

		if (_game_won) {
			return true;
		}

		int countBlacks = 0;
		int countWhites = 0;

			
			for (Spot s : _board) {
				if (s.getSpotColor() == Color.WHITE) {
	
					countWhites++;
				}

				else if (s.getSpotColor() == Color.BLACK
						&& (s.isEmpty() == false)) {
					
					countBlacks++;
				}
			}

			System.out.println("Blacks " + countBlacks);
			System.out.println("Whites " + countWhites);
			_noWhite = countWhites;
			_noBlack = countBlacks;

			
            int playCol;
			if (_next_to_play == Player.BLACK) {
            	playCol = 0;
            } else {
            	playCol = 1;
            }
            
			System.out.println("Legal? " + hasLegalMoves());
			if ((hasLegalMoves() == false)) {
				if (countBlacks > countWhites) {
					_winnerName = "Black";
				} 
				
				if (countWhites > countBlacks) {
					_winnerName = "White";
				} 
				
				if (countWhites == countBlacks) {
					_winnerName = null;
				} 
				
				System.out.println("GAME WON");
				_game_won = true;

				return true;


			}

		


		return false;

	}
// Kiểm tra xem có hòa hay không 
	public boolean checkDraw() {
		int countBlacks = 0;
		int countWhites = 0;

		for (int x = 0; x < 8; x++) {

			for (int y = 0; y < 8; y++) {

				if (_board.getSpotAt(x, y).getSpotColor() == Color.WHITE) {
					countWhites++;
				}

				else if (_board.getSpotAt(x, y).getSpotColor() == Color.BLACK
						&& (_board.getSpotAt(x, y).isEmpty() == false)) {

					countBlacks++;
				}

			}



		}
		return false;

	}
	
	

 
	protected static final int[] DX = { -1, 0, 1, -1, 1, -1, 0, 1 };
    protected static final int[] DY = { -1, -1, -1, 0, 0, 1, 1, 1 };

 // Kiếm tra xem một nước đi có hợp lệ hay không? 
    public boolean isLegalMove (Spot piece)
    {

        if (!piece.isEmpty()) {
            return false;
        }

        for (int ii = 0; ii < DX.length; ii++) {

            boolean sawOther = false, sawSelf = false;
            int x = piece.getSpotX(), y = piece.getSpotY();
            

            for (int dd = 0; dd < _size; dd++) {
                x += DX[ii];
                y += DY[ii];



                if (!inBounds(x, y)) {
                    break;
                }

                
                int color = getColor(x, y);
                int playCol; 
                if (_next_to_play == Player.BLACK) {
                	playCol = 0;
                } else {
                	playCol = 1;
                }
                

                if (color == -1) {
                    break;
                } else if (color == 1 - playCol) {
                    sawOther = true;
                } else if (color == playCol) {
                    sawSelf = true;
                    break;
                }
            }

   
            if (sawOther && sawSelf) {
                return true;
            }
        }
        return false;
    }

 
    public boolean hasLegalMoves (int color, Spot piece)
    {


        Color owner = piece.getSpotColor();
        Point Piece = null;
        for (int yy = 0; yy < _size; yy++) {
            for (int xx = 0; xx < _size; xx++) {
                if (getColor(xx, yy) != -1) {
                    continue;
                }
                Piece.x = xx;
                Piece.y = yy;
                if (isLegalMove(_board.getSpotAt(Piece.x, Piece.y))) {
                    return true;
                }
            }
        }
        return false;
    }
    //Kiếm tra xem có bất kỳ nước đi hợp lệ nào cho người chơi hay không
    public boolean hasLegalMoves ()
    {
    	
    	for (Spot s: _board) {
   
                if (isLegalMove(s)) {
                    return true;
                }
            }
    
        
    
        return false;
    }

  //Lật quân cờ khi nước đi hợp lệ
    public void flipPieces (Spot placed)
    {
        ArrayList<Spot> toflip = new ArrayList<Spot>();

 
        for (int ii = 0; ii < DX.length; ii++) {

            int x = placed.getSpotX(), y = placed.getSpotY();
            int next;
			int playCol; 
			if (_next_to_play == Player.BLACK) {
				playCol = 0;
				next = 1;
			} else {
				playCol = 1;
				next = 0; 
			}

			
            for (int dd = 0; dd < _size; dd++) {
                x += DX[ii];
                y += DY[ii];
               

  
                if (!inBounds(x, y)) {
                    break;
                }

                int color = getColor(x, y);
                if (color == -1) {
                    break;

                } else if (color == next) {

                    for (Spot piece : _board) {
                        if (piece.getSpotX() == x && piece.getSpotY() == y) {
                            toflip.add(piece);
                            break;
                        }
                    }

                } else if (color == playCol) {

                	
                    for (Spot piece : toflip) {
                    	Color temp;
                        if (piece.getSpotColor() == Color.BLACK) {
                        	temp = Color.WHITE;
                        } else {
                        	temp = Color.BLACK;
                        }
                        
                        piece.setSpotColor(temp);
                    }
                    break;
                }
            }
            toflip.clear();
        }
    }
    
    protected final Color getColorC (int x, int y)
    {
        return _board.getSpotAt(x, y).getSpotColor();
    }
    
    protected final int getColor (int x, int y)
    {
    	if (_board.getSpotAt(x, y).isEmpty() == true) {
    		return -1;
    	} else if (_board.getSpotAt(x, y).getSpotColor() == Color.BLACK) {
    		return 0;
    	} else {
    		return 1;
    	}
    	
    }
    
    protected final int getColor (Spot spot)
    {
    	
    	int x = spot.getSpotX();
    	int y = spot.getSpotY();
    			
    	
    	if (_board.getSpotAt(x, y).getSpotColor() == Color.BLACK && _board.getSpotAt(x, y).isEmpty() == true) {
    		return -1;
    	}
    	if (_board.getSpotAt(x, y).getSpotColor() == Color.BLACK && _board.getSpotAt(x, y).isEmpty() == false) {
    		return 0;
    	}
    	if (_board.getSpotAt(x, y).getSpotColor() == Color.WHITE) {
    		return 1;
    	}
    	
    	else {
    		return -2;
    	}
    }
    
    
    protected final boolean inBounds (Spot spot)
    {
    	
    	int x = spot.getSpotX();
    	int y = spot.getSpotY();
    	
        return x >= 0 && y >= 0 && x < this._size && y < this._size;
    }
    
    
    protected final boolean inBounds (int x, int y)
    {
    	
        return x >= 0 && y >= 0 && x < this._size && y < this._size;
    }
    
    
    protected int _size;
    protected int[] _state;

}