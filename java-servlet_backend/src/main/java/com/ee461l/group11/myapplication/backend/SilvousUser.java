package com.ee461l.group11.myapplication.backend;

import java.util.*;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class SilvousUser implements SilvousSubject
{
	static { ObjectifyService.register(SilvousUser.class); }
	@Id
	Long userID;
	/* Fields for the User class */
	private ArrayList<SilvousObserver> allEvents = new ArrayList<SilvousObserver>();
	private ArrayList<SilvousEvent> userCreatedEvents = new ArrayList<SilvousEvent>();
	private int userPrefDistance;

	/**
	 * Default SilvousUser constructor 
	 */
	public SilvousUser()
	{
		this.userPrefDistance = 25;
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

	/**
	 * When a new user is created, register to datastore
	 * @param void
	 * @return void
	 */
	private void registerUser()
	{
		ObjectifyService.ofy().save().entity(this).now();
	}

	/**
	 * When user has modified data, update in datastore as well
	 * @param void
	 * @return void
	 */
	private void updateUser()
	{
		ObjectifyService.ofy().save().entity(this).now();
	}
	
	
}
