package net.owl_black.tovmgr;

import java.awt.Color;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.owl_black.tovmgr.SmsBubble.BubbleDirection;

public class MessagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	JPanel subMessagePan;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame frame = new JFrame("MessagePanel test");
		
		frame.setSize(400, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(new Color(238, 238, 238));
		
		JPanel pan = new MessagePanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		frame.add(pan);
		
		frame.setVisible(true);

	}
	
	private void insertBubble(SmsBubble bub) {
		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.X_AXIS));
		
		
		if(bub.getOrientation() == BubbleDirection.BBL_RECEIVED__RIGHT) {
			pan.add(Box.createHorizontalGlue());
			pan.add(bub);
		} else {
			pan.add(bub);
			pan.add(Box.createHorizontalGlue());
		}
		
		
		//For DEBUG
		//pan.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		//this.add(pan);
		this.add(bub);
	}
	SmsBubble bub;
	public MessagePanel() {
		
		
		//this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		 bub = new SmsBubble(BubbleDirection.BBL_SENT__LEFT, "Lorem ipsum dolor sit amet, test test", "Dec 10 10:52");
		//bub.setAlignmentX(LEFT_ALIGNMENT);
		//insertBubble(bub);
		//lab = new JLabel("Test1 my super label");
		//lab.set
		insertBubble(bub);
		
		bub = new SmsBubble(BubbleDirection.BBL_RECEIVED__RIGHT, "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore", "Dec 10 10:52");
		//bub.setAlignmentX(RIGHT_ALIGNMENT);
		insertBubble(bub);
		//insertBubble(new JLabel("Test2 eazfjezjfiopezjf zejfpzeofjkez fez pf"));
		
		
		//this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		// Adding border with a title
		/*
		TitledBorder title = BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED) , "Conversation");
		title.setTitleJustification(TitledBorder.CENTER);
		this.setBorder(title);*/
		
		/*
		
		
		
		
		bub = new SmsBubble(BubbleDirection.BBL_RECEIVED__RIGHT, "et dolore magna aliqua. Ut enim", "Dec 10 10:52");
		bub.setAlignmentX(LEFT_ALIGNMENT);
		subMessagePan.add(bub);
		
		bub = new SmsBubble(BubbleDirection.BBL_RECEIVED__RIGHT, "occaecat", "Dec 10 10:52");
		bub.setAlignmentX(LEFT_ALIGNMENT);
		subMessagePan.add(bub);
		
		bub = new SmsBubble(BubbleDirection.BBL_SENT__LEFT, "non proident, sunt in culpa qui officia deserunt", "Dec 10 10:52");
		bub.setAlignmentX(RIGHT_ALIGNMENT);
		subMessagePan.add(bub);
		
		bub = new SmsBubble(BubbleDirection.BBL_SENT__LEFT,"rud exercitation ullamco laboris nisi ut aliquip ex ea commodo c", "Dec 10 10:52");
		bub.setAlignmentX(RIGHT_ALIGNMENT);
		subMessagePan.add(bub);
		*/

        
		
		//Add a scroll bar to the pan
		/*
		JScrollPane scrollBar = new JScrollPane(subMessagePan);
		scrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollBar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//scrollBar.setBounds(50, 30, 300, 50);
		//this.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
		//subMessagePan.setPreferredSize(new Dimension(600, 40));
		

		// Add the scrollable conversation to the panel (parameter)
		 * */
		 
		
		//this.setMaximumSize(new Dimension(20, 20));
	}
	
	public void addMessage(String date, String message) {
		
	}


}
