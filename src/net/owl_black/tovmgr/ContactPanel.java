package net.owl_black.tovmgr;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

/* Copyright (c) 2012-2016, Louis-Paul CORDIER
 * All rights reserved.
 * 
 * This file is part of The Owl VMG Reader (tovmgr).
 * The Owl VMG Reader (tovmgr) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * The Owl VMG Reader (tovmgr) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with The Owl VMG Reader (tovmgr).  If not, see <http://www.gnu.org/licenses/>. */

public class ContactPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/* Graphical resources. */
	JList<String> listContact;
	
	public ContactPanel() {
		super();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		listContact = new JList<String>();
		
		add(listContact);
	}
	
	public void setContactList(ArrayList<String> list) {
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		
		for(String k : list) //TODO: optimize dat shit
			listModel.addElement(k);
		
		listContact.setModel(listModel);
	}

}
