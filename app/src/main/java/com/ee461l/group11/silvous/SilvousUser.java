package com.ee461l.group11.silvous;

import android.location.Location;

import java.util.*;

public class SilvousUser implements SilvousSubject
{
	/* Fields for the User class */
	private ArrayList<SilvousObserver> allEvents = new ArrayList<SilvousObserver>();
	private ArrayList<SilvousEvent> userCreatedEvents = new ArrayList<SilvousEvent>();
	private int userPrefDistance;
	private SilvousLocation userLocation;
	private String userEmail;
	private String userID;


	/**
	 * Default SilvousUser constructor 
	 */
	public SilvousUser()
	{
		userLocation=new SilvousLocation();
		this.userPrefDistance = 25;
		userEmail = "INVALID";
	}

	public SilvousUser(String email)
	{
		this.userEmail = email;
		this.userPrefDistance = 25;
	}

	public SilvousUser(String ID, String email)
	{
		this.userID = ID;
		this.userEmail = email;
	}

	@Override	
	public void registerObserver(SilvousObserver sObs)
	{
		allEvents.add(sObs);
	}

	@Override
	public void notifyObservers()
	{
		for(SilvousObserver sObs : allEvents)
		{
			sObs.update();
		}
	
	}

	public String getUserID()
	{
		return this.userID;
	}


	
	/**
	 * Method to add an event this user created to their list
	 * @param sEvent
	 * 	SilvousEvent objected created by this user
	 * @return void
	 */
	public void registerOwnership(SilvousEvent sEvent)
	{
		userCreatedEvents.add(sEvent);
		refresh();
	}

	/**
	 * Sets the distance at which the user wants the app to display events
	 * @param distance
	 * 	int value of the desired distance in miles
	 * @return void
	 */
	public void setPrefDistance(int distance)
	{
		this.userPrefDistance = distance;
	}

	/**
	 * Refreshes the ArrayLists containing SilvousEvents to load recently created events
	 * @param void
	 * @return void
	 */
	public void refresh()
	{
		
	}

	public String getUserEmail()
	{
		return this.userEmail;
	}

	/**
	 * Retrieves the current User's location
	 * @return
	 * 	SilvousLocation object
     */
	public SilvousLocation getUserLocation()
	{
		return this.userLocation;
	}

	/**
	 * Gets the distance at which the CurrentUser wants to display events
	 * @return
     */
	public int getUserPrefLocation()
	{
		return this.userPrefDistance;
	}

	/**
	 * Determines if an event is within the user's range to display
	 * @param event
	 * 	SilvousEvent object
	 * @return
	 * 	boolean true/false
     */
	boolean inRange(SilvousEvent event)
	{
		Location userLoc = new Location("dummy");
		userLoc.setLatitude(this.userLocation.getX_coord());
		userLoc.setLongitude(this.userLocation.getY_coord());
		Location eventLoc = new Location("dummy");
		eventLoc.setLatitude(event.getEventLocation().getX_coord());
		eventLoc.setLongitude(event.getEventLocation().getY_coord());
		return (userLoc.distanceTo(eventLoc)*0.000621371) <=this.userPrefDistance;
	}

	
	
}
