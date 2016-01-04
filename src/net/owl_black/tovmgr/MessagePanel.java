package net.owl_black.tovmgr;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.colorchooser.ColorSelectionModel;

import net.owl_black.tovmgr.SmsBubble.BubbleDirection;

public class MessagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	//from http://stackoverflow.com/questions/15783014/jtextarea-on-jpanel-inside-jscrollpane-does-not-resize-properly
	private static class ScrollablePanel extends JPanel implements Scrollable{
		private static final long serialVersionUID = 1L;

		public Dimension getPreferredScrollableViewportSize() {
	        return super.getPreferredSize(); //tell the JScrollPane that we want to be our 'preferredSize' - but later, we'll say that vertically, it should scroll.
	    }

	    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
	        return 30;
	    }

	    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
	        return 30;
	    }

	    public boolean getScrollableTracksViewportWidth() {
	        return true;//track the width, and re-size as needed.
	    }

	    public boolean getScrollableTracksViewportHeight() {
	        return false; //we don't want to track the height, because we want to scroll vertically.
	    }
	}
	
	ScrollablePanel subMessagePan;
	
	private int row_number;

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("MessagePanel test");
		
		frame.setSize(400, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(new Color(238, 238, 238));
		
		JPanel pan = new MessagePanel();
		frame.add(pan);
		frame.setVisible(true);
		frame.revalidate();
	}
	
	private void insertBubble(SmsBubble bub, boolean lastOne) {
		GridBagConstraints c = new GridBagConstraints();
		
  	    c.gridx = 0;
		c.gridy = row_number++;
		c.ipady = 5;
		c.weightx = 1.0;
		c.weighty = 0.0;
		if(lastOne)
			c.gridheight = GridBagConstraints.RELATIVE;
		
		if(bub.getOrientation() == BubbleDirection.BBL_RECEIVED__RIGHT) {
			c.anchor = GridBagConstraints.EAST;
		} else {
			c.anchor = GridBagConstraints.WEST;
		}
		
		//For DEBUG
		//bub.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		subMessagePan.add(bub, c);
	}
	
	public MessagePanel() {
		
		SmsBubble bub;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		subMessagePan = new ScrollablePanel();
		subMessagePan.setLayout(new GridBagLayout());
		row_number = 0;
		
		bub = new SmsBubble(BubbleDirection.BBL_SENT__LEFT, "Lorem ipsum dolor sit amet, test test", "Dec 10 10:52");
		insertBubble(bub, false);
		bub = new SmsBubble(BubbleDirection.BBL_RECEIVED__RIGHT, "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore", "Dec 10 10:52");
		insertBubble(bub, false);
		bub = new SmsBubble(BubbleDirection.BBL_RECEIVED__RIGHT, "et dolore magna aliqua. Ut enim", "Dec 10 10:52");
		insertBubble(bub, false);
		bub = new SmsBubble(BubbleDirection.BBL_RECEIVED__RIGHT, "occaecat", "Dec 10 10:52");
		insertBubble(bub, false);
		bub = new SmsBubble(BubbleDirection.BBL_SENT__LEFT, "non proident, sunt in culpa qui officia deserunt", "Dec 10 10:52");
		insertBubble(bub, false);
		bub = new SmsBubble(BubbleDirection.BBL_SENT__LEFT,"rud exercitation ullamco laboris nisi ut aliquip ex ea commodo c", "Dec 10 10:52");
		insertBubble(bub, false);
		bub = new SmsBubble(BubbleDirection.BBL_SENT__LEFT,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor "
				+ "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut "
				+ "aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat "
				, "Dec 10 10:52");
		insertBubble(bub, true);
		
		// Adding border with a title
		TitledBorder title = new TitledBorder("Conversation");
		title.setBorder(BorderFactory.createEmptyBorder());
		title.setTitleJustification(TitledBorder.CENTER);
		this.setBorder(title);
		
		//Add a scroll bar to the pan
		JScrollPane scrollBar = new JScrollPane(subMessagePan);
		scrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollBar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		//For DEBUG
		//subMessagePan.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		this.add(scrollBar);
	}
	
	public void addMessage(String date, String message) {
		
	}


}
