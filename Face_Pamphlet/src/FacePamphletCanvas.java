/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	
	//instance variables
	private GLabel message;
	private GLabel name;
	private GImage image;
	private GLabel status;
	private GLabel friends;
	private GCompound friendsList;
	
	private double width = 621;
	private double height = 464;
	private double base = 18;
	
	public FacePamphletCanvas() {
		message = new GLabel("");
		message.setFont(MESSAGE_FONT);
		add(message, (width - message.getWidth()) / 2, height - BOTTOM_MESSAGE_MARGIN);
		
		name = new GLabel("No profile selected");
		name.setFont(PROFILE_NAME_FONT);
		add(name, LEFT_MARGIN, base + TOP_MARGIN);
		
		image = new GImage("StanfordTree.JPG");
		image.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
		add(image, LEFT_MARGIN, base + TOP_MARGIN + IMAGE_MARGIN);
		
		status = new GLabel("");
		status.setFont(PROFILE_STATUS_FONT);
		add(status, LEFT_MARGIN, base + TOP_MARGIN + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN);
		
		friends = new GLabel("Friends:");
		friends.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friends, (width - friends.getWidth()) / 2, base + TOP_MARGIN + IMAGE_MARGIN);
		
		friendsList = new GCompound();
		add(friendsList, (width - friends.getWidth()) / 2, base + TOP_MARGIN + IMAGE_MARGIN + friends.getHeight());
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		remove(message);
		message.setLabel(msg);
		add(message, (width - message.getWidth()) / 2, height - BOTTOM_MESSAGE_MARGIN);
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		//check for if no current profile
		if (profile == null) {
			//set defaults
			name.setLabel("No Profile Selected");	
		}
		
		//name
		name.setLabel(profile.getName());
		
		//profile pic
		GImage img = profile.getImage();
		if (img != null) {
			image.setImage(img.getImage());
			image.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
		}
		
		//status
		status.setLabel(profile.getStatus());
		
		//friends
		friendsList.removeAll();
		double currentHeight = 0;
		Iterator<String> it = profile.getFriends();
		while (it.hasNext()) {
			GLabel friend = new GLabel(it.next());
			friend.setFont(PROFILE_FRIEND_FONT);
			friendsList.add(friend, 0, currentHeight);
			currentHeight += friend.getHeight();
		} 	
	}

	
}
