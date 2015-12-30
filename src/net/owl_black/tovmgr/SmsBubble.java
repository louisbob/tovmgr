package net.owl_black.tovmgr;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.text.View;

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
	private JTextArea lblMessage;
	private JPanel panText;
	private Border bordersPan;
	private int		pxTxtLength; 	
	
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
		
	    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	    this.setDoubleBuffered(true);
	    
	    //Set style:
	    if(this.orientation == BubbleDirection.BBL_RECEIVED__RIGHT) {
	    	clTime 		= new Color(132, 132, 132); //Grey
	    	clBbl 		= new Color(255, 255, 255); //White
		    clTxtBbl 	= Color.BLACK;
		    bordersPan = BorderFactory.createEmptyBorder(1, 8, 10, 20); //top, left, bottom, right
		    //this.add(Box.createHorizontalGlue());
	    } else {
	    	clTime 		= new Color(159, 216, 188);
	    	clBbl 		= new Color(15, 157, 88);   //Green
		    clTxtBbl 	= Color.WHITE;
		    bordersPan  = BorderFactory.createEmptyBorder(1, 28, 10, 8);
	    }
	    
	    fontDate = new Font("Calibri",1,10);
	    fontTxt = new Font("Calibri",1,12);
	    
	    //Layout that holds the text and the date.
  		panText = new JPanel(true); //Double buffered
  		panText.setLayout(new BoxLayout(panText, BoxLayout.Y_AXIS));
  		panText.setBackground(new Color(0,0,0,0)); //Set maximum transparency
  		
  		//Create margin/padding around the text:
  		panText.setBorder(bordersPan); 
  		
  		//Create the date
  		lblDate = new JLabel(this.txtDate);
  		lblDate.setAlignmentX(CENTER_ALIGNMENT);
  	    lblDate.setFont(fontDate);
  	    lblDate.setForeground(clTime);
		lblDate.setDoubleBuffered(true); //TODO: check performances
		System.out.println(lblDate.getPreferredSize().toString());
		lblDate.setMaximumSize(lblDate.getPreferredSize());
		lblDate.setMinimumSize(lblDate.getPreferredSize());
		panText.add(lblDate);
  		
  		//Create the text label
  	    lblMessage = new JTextArea(this.messageText);
  	    //lblMessage.setAlignmentX(CENTER_ALIGNMENT);
  	    lblMessage.setEditable(false);
  	    lblMessage.setCursor(null);  
  	    lblMessage.setOpaque(false);  
  	    lblMessage.setFocusable(false);
  	    lblMessage.setLineWrap(true);
  	    lblMessage.setWrapStyleWord(true);
  	    lblMessage.setFont(fontTxt);
  	    lblMessage.setForeground(clTxtBbl);
  	    lblMessage.setDoubleBuffered(true);
  	    //Set the maximum dimension of the bubble regarding the length of the text to display.
  	    //This allows to get a better style for the bubble, avoiding having big bubble with small text inside.
  	    pxTxtLength = lblMessage.getFontMetrics(fontTxt).stringWidth(messageText);
  	    
  	   // if(pxTxtLength < lblMessage)
  	    //	pxTxtLength = lblMessage.getFontMetrics(fontDate).stringWidth(dateText) + 15;
  	    lblMessage.setMinimumSize(new Dimension(lblDate.getFontMetrics(fontDate).stringWidth(dateText)+4,0));
  	    lblMessage.setMaximumSize(new Dimension(pxTxtLength+2, Integer.MAX_VALUE));
  	    
  	    System.out.println(pxTxtLength);

  	    panText.add(lblMessage);
  	    
  	    //this.add(comp)q
  	    this.add(panText);
  	    
  	  
  	    
  	    //if(this.orientation == BubbleDirection.BBL_SENT__LEFT)
  		//  this.add(Box.createHorizontalGlue());
  	    
  	    //FOR DEBUG
  	    //lblDate.setBorder(BorderFactory.createLineBorder(Color.black, 1));
  	    //lblMessage.setBorder(BorderFactory.createLineBorder(Color.black, 1));
  	    //panText.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
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
	public void paint(Graphics g) {
		
		lblDate.setMaximumSize(lblDate.getPreferredSize());
		panText.setMaximumSize(new Dimension(
  	    		pxTxtLength + bordersPan.getBorderInsets(panText).left + bordersPan.getBorderInsets(panText).right+4,
  	    		lblMessage.getPreferredSize().height+25));
		
		//Set constraints from the bubble
		
		Graphics2D graphBubble = (Graphics2D) g;
		
		//Get dimensions to create a re dimension relativeness bubble
		Dimension dimPanel 		= this.getSize();
		Dimension dimPanelMax 	= this.getMaximumSize();
		
		if(dimPanel.height > dimPanelMax.height)
			dimPanel.height = dimPanelMax.height;
		
		if(dimPanel.width > dimPanelMax.width)
			dimPanel.width = dimPanelMax.width;
		
		RoundRectangle2D roundedRectangle;
		
		//Create the rectangle bubble
		if(this.orientation == BubbleDirection.BBL_RECEIVED__RIGHT) {
			roundedRectangle = new RoundRectangle2D.Float(
		    		1, 1, dimPanel.width-18, dimPanel.height, 8, 8); // x pos, y pos, rect width, rect height, corner round
		} else {
			roundedRectangle = new RoundRectangle2D.Float(
		    		18, 1, dimPanel.width-19, dimPanel.height, 8, 8); // x pos, y pos, rect width, rect height, corner round
		}
	    
	    
	    graphBubble.setColor(clBbl);
	    graphBubble.draw(roundedRectangle);
	    graphBubble.fill(roundedRectangle);
	    
	    //Create the triangle TODO: make it parameterizable
	    Polygon triangle;
	    
	    if(this.orientation == BubbleDirection.BBL_RECEIVED__RIGHT) {
	    	int[] xPoints = {dimPanel.width-20, dimPanel.width, dimPanel.width-20};
		    int[] yPoints = {1, 1, 21};
		    triangle = new Polygon(xPoints, yPoints, xPoints.length);
	    } else {
	    	int[] xPoints = {1, 21, 21};
		    int[] yPoints = {1, 1, 21};
		    triangle = new Polygon(xPoints, yPoints, xPoints.length);
	    }
	    
	    graphBubble.draw(triangle);
	    graphBubble.fill(triangle);
	    
	    
	    
	    super.paint(g);
	    //FOR DEBUG
	    //graphics2.setColor(Color.BLUE);
	};
	
	
	//from https://tips4java.wordpress.com/2008/10/26/text-utilities/
	public static int getWrappedLines(JTextArea component)
	{
		View view = component.getUI().getRootView(component).getView(0);
		int preferredHeight = (int)view.getPreferredSpan(View.Y_AXIS);
		int lineHeight = component.getFontMetrics( component.getFont() ).getHeight();
		return preferredHeight / lineHeight;
	}
	
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
