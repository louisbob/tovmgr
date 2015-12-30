package net.owl_black.tovmgr;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class SmsBubble extends JPanel {

	private static final long serialVersionUID = 1L;
	
	enum BubbleDirection {
		BBL_RECEIVED__RIGHT,
		BBL_SENT__LEFT
	}
	
	/* Modifiable properties. */
	private String messageText;
	private String txtDate;
	private BubbleDirection orientation;

	/* Theming. */
	private Color clConversationBg 	= new Color(238, 238, 238); //Grey light
	
	private Color clTime;
	private Color clBbl;
	private Color clTxtBbl;
	private Font  fontTxt;
	private Font  fontDate;
	
	/* Graphical resources. */
	private JLabel lblDate;
	private Insets insetTxt;
	private JTextArea lblMessage;
	
	private Polygon triangle;
	private int triangle_dim = 21;
	private int[] triangle_y_points;
	
	
	/* Test function. */
	public static void main(String[] args) {
		JFrame frame = new JFrame("SmsBubble test");
		
		frame.setSize(400, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(new Color(238, 238, 238));
		
		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		
		SmsBubble bbl1 = new SmsBubble(BubbleDirection.BBL_RECEIVED__RIGHT, "This is my text message. it is super cool to have this kind of super styled tuf"
				+ "with another style of things lorem ipsum", "Dec. 12/2015, 12:41pm");
		
		SmsBubble bbl2 = new SmsBubble(BubbleDirection.BBL_SENT__LEFT, "This is my text message.", "Dec. 12/2015, 12:41pm");
		
		SmsBubble bbl3 = new SmsBubble(BubbleDirection.BBL_SENT__LEFT, "This is my text message. it is super cool to have this kind of super styled tuf"
				+ "with another style of things lorem ipsum", "Dec. 12/2015, 12:41pm");
		
		//FOR DEBUG
		//bbl.setBorder(BorderFactory.createLineBorder(Color.black));
		//bbl.setMaximumSize(new Dimension(300, 150));
		
		pan.add(bbl1);
		pan.add(bbl2);
		pan.add(bbl3);
		frame.add(pan);
		
		frame.setVisible(true);
	}
	
	public SmsBubble(BubbleDirection direction, String messageText, String dateText) {
		
		//Default behavior
		this.orientation 	= direction;
		this.messageText 	= messageText; //Useful for line wrapping.
		this.txtDate 		= "<html>" + dateText + "</html>";
		
	    this.setLayout(new GridBagLayout());
	    this.setDoubleBuffered(true);
	    
	    //Set style & theme
	    if(this.orientation == BubbleDirection.BBL_RECEIVED__RIGHT) {
	    	clTime 		= new Color(132, 132, 132); //Grey
	    	clBbl 		= new Color(255, 255, 255); //White
		    clTxtBbl 	= Color.BLACK;
		    insetTxt	= new Insets(0, 6, 0, triangle_dim+4);
	    } else {
	    	clTime 		= new Color(159, 216, 188);
	    	clBbl 		= new Color(15, 157, 88);   //Green
		    clTxtBbl 	= Color.WHITE;
		    insetTxt	= new Insets(0, triangle_dim+4, 0, 5);
	    }
	    this.setBackground(clConversationBg);
	    
	    fontDate = new Font("Calibri",1,10);
	    fontTxt = new Font("Calibri",1,12);
  		
  		GridBagConstraints c = new GridBagConstraints();
  		
  		//Create the date
  		lblDate = new JLabel(this.txtDate, SwingConstants.CENTER);
  	    lblDate.setFont(fontDate);
  	    lblDate.setForeground(clTime);
		
		//Date label constraints
		c.anchor = GridBagConstraints.PAGE_START;
  	    c.fill = GridBagConstraints.HORIZONTAL;
  	    c.gridwidth = GridBagConstraints.RELATIVE;
  	    c.gridx = 0;
		c.gridy = 0;
		c.insets = insetTxt;
		c.ipady = 5;
		c.weightx = 1.0;
		c.weighty = 0.0;
  		
		this.add(lblDate, c);
  		
  		//Create the text label
  	    lblMessage = new JTextArea(this.messageText);
  	    lblMessage.setEditable(false);
  	    lblMessage.setCursor(null);  
  	    lblMessage.setOpaque(false);  
  	    lblMessage.setFocusable(false);
  	    lblMessage.setLineWrap(true);
  	    lblMessage.setWrapStyleWord(true);
  	    lblMessage.setFont(fontTxt);
  	    lblMessage.setForeground(clTxtBbl);
  	    
  	    //Date label constraints
  	    c.anchor = GridBagConstraints.NORTHWEST;
  	    c.fill = GridBagConstraints.HORIZONTAL;
  	    c.gridwidth = GridBagConstraints.RELATIVE;
  	    c.gridx = 0;
		c.gridy = 1;
		c.insets = insetTxt;
		c.ipady = 0;
		c.weightx = 1.0;
		c.weighty = 1.0;

		this.add(lblMessage, c);
		
  	    //Generate points for the triangle.
  	    triangle_y_points = new int[] {1, 1, triangle_dim+1};
  	    
  	    //In case of left bubble, we don't need to recompute the triangle each time.
  	    if(this.orientation == BubbleDirection.BBL_SENT__LEFT) {
	    	int[] xPoints = {1, triangle_dim+1, triangle_dim+1};
		    triangle = new Polygon(xPoints, triangle_y_points, xPoints.length);
	    }
  	    
  	    //FOR DEBUG
  	    //lblDate.setBorder(BorderFactory.createLineBorder(Color.black, 1));
  	    //lblMessage.setBorder(BorderFactory.createLineBorder(Color.black, 1));
  	    //this.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
	}
	
	public SmsBubble() {
		this(BubbleDirection.BBL_RECEIVED__RIGHT, "", "");
	}
	
	public SmsBubble(BubbleDirection direction) {
		this(direction, "", "");
	}
	
	public SmsBubble(BubbleDirection direction, String messageText) {
		this(direction, messageText, "");
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);

		//Get dimensions to create a re-dimension relativeness bubble
		Dimension dimPanel = this.getSize();
		
		//Create the rectangle bubble
		RoundRectangle2D roundedRectangle;
		
		if(this.orientation == BubbleDirection.BBL_RECEIVED__RIGHT) {
			roundedRectangle = new RoundRectangle2D.Float(
		    		1, 1, dimPanel.width-triangle_dim, dimPanel.height-2, 8, 8); // x pos, y pos, rect width, rect height, corner round
		} else {
			roundedRectangle = new RoundRectangle2D.Float(
					triangle_dim-1, 1, dimPanel.width-triangle_dim-1, dimPanel.height-2, 8, 8); // x pos, y pos, rect width, rect height, corner round
		}
	    
		Graphics2D graphBubble = (Graphics2D) g;
	    graphBubble.setColor(clBbl);
	    graphBubble.draw(roundedRectangle);
	    graphBubble.fill(roundedRectangle);
	    
	    //Create the triangle shape. We only need to re-create it if the orientation is BBL_RECEIVED__RIGHT
	    if(this.orientation == BubbleDirection.BBL_RECEIVED__RIGHT) {
	    	int[] xPoints = {dimPanel.width-triangle_dim-1, dimPanel.width-1, dimPanel.width-triangle_dim-1};
		    triangle = new Polygon(xPoints, triangle_y_points, xPoints.length);
	    } 
	    
	    graphBubble.draw(triangle);
	    graphBubble.fill(triangle);
	    
	    //FOR DEBUG
	    //graphics2.setColor(Color.BLUE);
	};
	
	
	/*
	 * Setters and getters
	 */
	
	public BubbleDirection getOrientation() {
		return orientation;
	}
	
	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public String getStrDate() {
		return txtDate;
	}

	public void setStrDate(String txtDate) {
		this.txtDate = txtDate;
	}
}
