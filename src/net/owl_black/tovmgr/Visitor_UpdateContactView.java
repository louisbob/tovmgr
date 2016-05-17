package net.owl_black.tovmgr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ezvcard.VCard;
import ezvcard.property.FormattedName;
import ezvcard.property.Telephone;
import net.owl_black.vmgparser.IVisitable;
import net.owl_black.vmgparser.IVisitor;
import net.owl_black.vmgparser.VmgBody;
import net.owl_black.vmgparser.VmgBodyExtended;
import net.owl_black.vmgparser.VmgEnvelope;
import net.owl_black.vmgparser.VmgObj;
import net.owl_black.vmgparser.VmgProperty;

public class Visitor_UpdateContactView implements IVisitor{
	ArrayList<Conversation> conversations;
	ArrayList<String> contactNames;
	
	public Visitor_UpdateContactView() {
		conversations = new ArrayList<Conversation>();
		contactNames = new ArrayList<String>();
	}
	
	public void processVmgDatabase(ArrayList<VmgObj> vmgDatabase) {
		for (VmgObj vObj : vmgDatabase) {
			vObj.accept(this);
		}
	}
	
	@Override
	public void visit(IVisitable e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(VmgObj e) {

		//if the (list of) contacts doesn't exists in any conversation, create a new conversation
		Conversation foundConversation = null;
		for (Conversation conv : conversations) {
			if(conv.isContainingContactCollection(e.getvOriginator())) {
				foundConversation = conv;
				break;
			}
		}
		
		if(foundConversation == null) {
			//We didn't find any conversation containing contacts from the vcard, so create a new one.
			Conversation newConv = new Conversation();
			newConv.contacts.addAll(e.getvOriginator());
			
			for(VCard c : newConv.contacts)
			{
				FormattedName vEZName = c.getFormattedName();
				
				//Check if we have a name. If not, use the phone number as contact name.
				if(vEZName == null)
				{
					List<Telephone> vTel =  c.getTelephoneNumbers();
					
					if(vTel == null)
					{
						//We have no phone number either.
						contactNames.add("<<UNKNOWN>>");
						continue;
					}
					
					//Take the first number as the contact name.
					for(Telephone t : vTel)
					{
						String vName = t.getText();
						if(vName != null)
						{
							System.out.println("New conversation with: " + vName);
							contactNames.add(vName);
						}
					}
				}
				else
				{
					//We have a name: add it directly to the conversation.
					contactNames.add(vEZName.getValue());
				}
			}
				
			//Add the message to the conversation
			newConv.messages.add(e);
			
			//Add the conversation to the list of all conversation
			conversations.add(newConv);
			
			System.out.println("New conversation added");
		} else {
			foundConversation.messages.add(e);
			System.out.println("Added to current conversation");
		}
	}

	@Override
	public void visit(VmgProperty e) {
		
	}

	@Override
	public void visit(VmgEnvelope e) {
	}

	@Override
	public void visit(VmgBody e) {
	}
	
	@Override
	public void visit(VmgBodyExtended e) {
	}
	
	/*Represent a conversation in the GUI. A conversation is a group of message
	received and sent by the same contact(s).
	*/
	class Conversation {
		ArrayList<VCard> contacts;
		ArrayList<VmgObj> messages;
		
		public Conversation() {
			contacts = new ArrayList<VCard>();
			messages = new ArrayList<VmgObj>();
		}
		
		public boolean isContainingContactCollection(Collection<VCard> inputVcard) {
			
			for (VCard vc : inputVcard) {
				boolean found = false;
				
				for (VCard contact : contacts) {
					if(vc.equals(contact)) {
						found = true;
						break;
					}
				}
				if(!found)
					return false;
			}
			
			return true;
		}
	}

}
