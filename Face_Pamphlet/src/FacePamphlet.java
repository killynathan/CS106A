/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	
	//instance variables 
	private JTextField name;
    private JButton addProfile;
    private JButton deleteProfile;
    private JButton lookupProfile;
    
    private JTextField status;
    private JButton changeStatus;
    private JTextField picture;
    private JButton changePicture;
    private JTextField friend;
    private JButton addFriend;
    private JButton deleteFriend;
    
    private FacePamphletDatabase database;
    
    private FacePamphletCanvas canvas;
  	
    private FacePamphletProfile currentProfile = null;
    
    
	public void init() {
		//set application size
		setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		
		//initialize database
		database = new FacePamphletDatabase();
		
		//initialize and add canvas
		canvas = new FacePamphletCanvas();
		add(canvas);
		
		//creating interactors
		name = new JTextField(TEXT_FIELD_SIZE);
		addProfile = new JButton("Add");
		deleteProfile = new JButton("Delete");
		lookupProfile = new JButton("Lookup");
		
		status = new JTextField(TEXT_FIELD_SIZE);
		changeStatus = new JButton("Change Status");
		picture =  new JTextField(TEXT_FIELD_SIZE);
		changePicture = new JButton("Change Picture");
		friend = new JTextField(TEXT_FIELD_SIZE);
		addFriend = new JButton("Add Friend");
		deleteFriend = new JButton("Delete Friend.");
		
		//adding interactors to application
		add(name, NORTH);
		add(addProfile, NORTH);
		add(deleteProfile, NORTH);
		add(lookupProfile, NORTH);
		
		add(status, WEST);
		add(changeStatus, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(picture, WEST);
		add(changePicture, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(friend, WEST);
		add(addFriend, WEST);		
		add(deleteFriend, WEST);
		
		//add action listeners
		name.addActionListener(this);
		status.addActionListener(this);
		picture.addActionListener(this);
		friend.addActionListener(this);
		addActionListeners();
    }
  
	public void run() {
	}
	
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
    	if (e.getSource() == addProfile) {
    		addProfile();
    	}
    	if (e.getSource() == deleteProfile) {
    		deleteProfile();
    	}
    	if (e.getSource() == lookupProfile) {
    		lookupProfile();
    	}
    	if (e.getSource() == changeStatus || e.getSource() == status) {
    		changeStatus();
    	}
    	if (e.getSource() == changePicture || e.getSource() == picture) {
    		changePicture();
    	}
    	if (e.getSource() == addFriend) {
    		addFriend();
    	}
    	if (e.getSource() == deleteFriend) {
    		deleteFriend();
    	}
    	
    	clearTextFields();
    	
    	/*if (currentProfile != null) {
    		println("current profile: " + currentProfile.toString());
    	}
    	else {
    		println("no current profile.");
    	}*/
	}
    
    private void addProfile() {
    	String profileName = name.getText();
    	FacePamphletProfile profile = database.getProfile(profileName);
		if (profile != null) {
			canvas.showMessage(profileName + " already exists.");
		}
		else {
			profile = new FacePamphletProfile(profileName);
			database.addProfile(profile);
			canvas.showMessage("added " + profileName + ".");
		}	
		
		//update current profile
		currentProfile = profile;
    }
    
    private void deleteProfile() {
    	String profileName = name.getText();
    	if (database.containsProfile(profileName)) {
    		database.deleteProfile(profileName);
        	canvas.showMessage("deleted " + profileName + ".");
    	}
    	else {
    		canvas.showMessage(profileName + " does not exists.");
    	}
    	
    	//update current profile
    	currentProfile = null;
    }
    
    private void lookupProfile() {
    	String profileName = name.getText();
    	FacePamphletProfile profile = database.getProfile(profileName);
    	if (profile != null) {
    		canvas.showMessage("Looked up " + profileName + ".");
    	}
    	else {
    		canvas.showMessage(profileName + " does not exists.");
    	}
    	
    	//update current profile 
    	currentProfile = profile;
    }
    
    private void changeStatus() {
    	if (currentProfile == null) {
    		canvas.showMessage("Please enter a user to change status of.");
    	}
    	else {
    		currentProfile.setStatus(status.getText());
    		canvas.showMessage("Updated status for " + currentProfile.getName() + ".");
    	}
    }
    
    private void changePicture() {
    	if (currentProfile == null) {
    		canvas.showMessage("Please enter a user to change the picture of.");
    	}
    	else {
    		try {
    			GImage image = new GImage(picture.getText());
    			currentProfile.setImage(image);
    			canvas.showMessage("Updated picture.");
    		} catch (ErrorException ex) {
    			canvas.showMessage("Not a valid picture.");
    		}
    	}
    }
    
    private void addFriend() {
    	if (currentProfile == null) {
    		canvas.showMessage("Please enter a user to add a friend to.");
    	}
    	else {
    		FacePamphletProfile profile = database.getProfile(friend.getText());
    		if (profile == null) {
    			canvas.showMessage("Could not find " + friend.getText() + ".");
    		}
    		else {
    			boolean notFriends = currentProfile.addFriend(profile.getName());
    			if (!notFriends) {
    				canvas.showMessage("Already a friend.");
    			}
    			else {
    				profile.addFriend(currentProfile.getName());
    				canvas.showMessage("Successfully added " + profile.getName() + ".");
    			}
    		}
    	}
    }
    
    private void deleteFriend() {
    	if (currentProfile == null) {
    		canvas.showMessage("Please enter a user to delete a friend from.");
    	}
    	else {
    		FacePamphletProfile profile = database.getProfile(friend.getText());
    		if (profile == null) {
    			canvas.showMessage("Could not find: " + friend.getText());
    		} 
    		else {
    			boolean hasFriend = currentProfile.removeFriend(profile.getName());
    			if (!hasFriend) {
    				canvas.showMessage(friend.getText() + "is not a friend.");
    			}
    			else {
    				profile.removeFriend(currentProfile.getName());
    				canvas.showMessage("Successfully deteleted " + friend.getText());
    			}
    		}
    	}
    }
    
    private void clearTextFields() {
    	name.setText("");
    	status.setText("");
    	picture.setText("");
    	friend.setText("");
    }

}
