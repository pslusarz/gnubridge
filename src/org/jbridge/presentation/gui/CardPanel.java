package org.jbridge.presentation.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.gnubridge.core.Card;

public class CardPanel extends JPanel {
	
	public static final int IMAGE_WIDTH = 70;
	public static final int IMAGE_HEIGHT = 120;
	private Card card;
	private boolean selected;

	public CardPanel(Card card) {
		this.card = card;
		setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
	}

	@Override
    public void paint(Graphics g) {
       Image image = new ImageIcon("./data/images/cards/jfitz/"+card.toString().replaceAll(" ", "-")+".png").getImage();
       g.drawImage(image, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, null);
       if (selected) {
    	   g.setColor(Color.YELLOW);
    	   g.drawRect(0, 0, IMAGE_WIDTH-1, IMAGE_HEIGHT-1);
       }
    }

	public Card getCard() {
		return card;
	}

	public void setSelected(boolean b) {
		selected = b;
		getParent().repaint();
		
	}

}
