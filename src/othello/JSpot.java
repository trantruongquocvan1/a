	package othello;
	
	
	import java.awt.BasicStroke;
	import java.awt.Canvas;
	import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.Graphics;
	import java.awt.Graphics2D;
	import java.awt.event.MouseEvent;
	import java.awt.event.MouseListener;
	import java.util.ArrayList;
	import java.util.List;
	
	import javax.swing.JPanel;
	import javax.swing.border.Border;
	import javax.swing.BorderFactory;
	import java.awt.Color;
	
	
	public  class JSpot extends JPanel implements MouseListener, Spot {
	
	    private static final int[] DX = { -1, 0, 1, -1, 1, -1, 0, 1 };
	    private static final int[] DY = { -1, -1, -1, 0, 0, 1, 1, 1 };
		private Color _spot_color;
		private Color _highlight_color;
	
		private boolean _is_empty;
		private boolean _is_highlighted;
		private SpotBoard _board;
		private int _x;
		private int _y;
	
		private List<SpotListener> _spot_listeners;
	
		public JSpot(Color background, Color spot_color, Color highlight, 
				SpotBoard board, int x, int y) {
	
	
			setBackground(background);
	
			_spot_color = spot_color;
			_highlight_color = highlight;
			_is_empty = true;
			_is_highlighted = false;
			_board = board;
			_x = x;
			_y = y;
	
			_spot_listeners = new ArrayList<SpotListener>();
	
			addMouseListener(this);
		}
	
	
	
		@Override
		public int getSpotX() {
			return _x;
		}
	
		@Override
		public int getSpotY() {
			return _y;
		}
	
		@Override
		public SpotBoard getBoard() {
			return _board;
		}
	
	
	
		@Override
		public boolean isEmpty() {
			return _is_empty;
		}
	
		@Override
		public void setSpot() {
			_is_empty = false;
			trigger_update();
		}
	
		@Override
		public void clearSpot() {
			_is_empty = true;
			trigger_update();
		}
	
	
	
		@Override
		public boolean isHighlighted() {
			return _is_highlighted;
		}
	
		@Override
		public void highlightSpot() {
			_is_highlighted = true;
			trigger_update();
		}
	
		@Override
		public void unhighlightSpot() {
			_is_highlighted = false;
			trigger_update();
		}
	
	
	
		@Override
		public void setSpotColor(Color c) {		
			if (c == null) throw new IllegalArgumentException("null color");
	
			_spot_color = c;
			trigger_update();
		}
	
		@Override
		public Color getSpotColor() {
			return _spot_color;
		}
	
		@Override
		public void setHighlight(Color c) {
			if (c == null) throw new IllegalArgumentException("null color");
	
			_highlight_color = c;
			trigger_update();
		}
	
		@Override
		public Color getHighlight() {
			return _highlight_color;
		}
	
	
	
		@Override
		public void addSpotListener(SpotListener l) {
			_spot_listeners.add(l);
		}
	
		@Override
		public void removeSpotListener(SpotListener l) {
			_spot_listeners.remove(l);
		}
	
	
		@Override
		public void paintComponent(Graphics g) {
	
			super.paintComponent(g);
	
			Graphics2D g2d = (Graphics2D) g.create();
			if (isHighlighted()) {
				g2d.setColor(getHighlight());
				g2d.setStroke(new BasicStroke(4));
				g2d.drawRect(0, 0, getWidth(), getHeight());
			}
			if (!isEmpty()) {
				g2d.setColor(getSpotColor());
				g2d.fillOval(0, 0, this.getWidth()-1, this.getHeight()-1);
			}
		}
	
		@Override
		public void mouseClicked(MouseEvent e) {
			for (SpotListener s : _spot_listeners) {
				s.spotClicked(this);
			}
		}
	
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
	
		}
	
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
	
		}
	
		@Override
		public void mouseEntered(MouseEvent e) {
			for (SpotListener s : _spot_listeners) {
				s.spotEntered(this);
			}
		}
	
		@Override
		public void mouseExited(MouseEvent e) {
			for (SpotListener s : _spot_listeners) {
				s.spotExited(this);
			}
		}	
		 public enum Player {
		        BLACK, WHITE
		    }
	    @Override
	    public boolean isLegalMove(Player currentPlayer) {
	        // Kiểm tra xem ô hiện tại có trống không
	        if (!isEmpty()) {
	            return false;
	        }
	     // Kiểm tra xem có quân cờ nào cần được lật hay không
	        boolean hasFlippablePiece = false;
	     // Duyệt qua các hướng có thể lật quân cờ
	        for (int i = 0; i < 8; i++) {
	            int x = getSpotX() + DX[i];
	            int y = getSpotY() + DY[i];
	            // Kiểm tra xem có quân cờ cùng màu nằm ở hướng đó không
	            if (getBoard().getSpotAt(x, y).getSpotColor() == getOpponentColor(currentPlayer)) {
	                // Kiểm tra xem có quân cờ của người chơi hiện tại nằm sau quân cờ cần lật không
	                while (getBoard().getSpotAt(x, y).getSpotColor() == getOpponentColor(currentPlayer)) {
	                    x += DX[i];
	                    y += DY[i];
	                    if (getBoard().getSpotAt(x, y).getSpotColor() == getCurrentPlayerColor(currentPlayer)) {
	                        hasFlippablePiece = true;
	                        break;
	                    }
	                }
	            }
	        }
	        return hasFlippablePiece;
	    }
		private Color getCurrentPlayerColor(Player currentPlayer) {
			// TODO Auto-generated method stub
		return (currentPlayer == Player.BLACK) ? Color.BLACK : Color.WHITE;
		}



		private Color getOpponentColor(Player currentPlayer) {
			// TODO Auto-generated method stub
			  return (currentPlayer == Player.BLACK) ? Color.WHITE : Color.BLACK;
		}



		private void trigger_update() {		
			repaint();
	
	
			
			new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
					}
					repaint();
				}
			}).start();
	
		}


	}
