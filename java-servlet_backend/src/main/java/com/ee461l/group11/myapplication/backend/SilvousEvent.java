package com.ee461l.group11.myapplication.backend;

import java.util.*;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class SilvousEvent implements SilvousObserver
{
	static { ObjectifyService.register(SilvousEvent.class); }
	@Id
	Long eventID;
	/* Fields for the Event class */
	private String eventName;
	private Date eventDate;
	private String eventDescription;
	private SilvousUser eventCreator;
	private int x_coord;
	private int y_coord;

	public SilvousEvent() {}


	/** 
	 * Primary constructor for events, should be called after user submits on GUI and extract data from the text boxes as arguments
	 * @param name
	 * 	The name of the SilvousUser's event
	 * @param description
	 * 	A description of the SilvousUser's event
	 * @param date
	 * 	When the event will occur
	 * @param x
	 * 	X coordinate of the event's location (use maps api)
	 * @param y
	 * 	Y coordinate of the event's location (use maps api)
	 * @return
	 * 	Initialized SilvousEvent object
	 */
	public SilvousEvent(String name, String description, Date date, int x, int y)
	{
		this.eventName = name;
		this.eventDate = date;
		this.eventDescription = description;
		this.x_coord = x;
		this.y_coord = y;
	}

	/**
	 * Allows the user to change the name of the event
	 * @param newName
	 * 	String object that is the new name of the event
	 * @return void
	 */
	public void editEventName(String newName)
	{
		this.eventName = newName;
	}

	/** 
	 * Allows the user to modify the date of the event
	 * @param newDate
	 * 	Date object that represents the updated event occurance
	 * @return void
	 */
	public void changeEventDate(Date newDate)
	{
		this.eventDate = newDate;
	}

	/**
	 * Allows the user to change the event's description
	 * @param newDescription
	 * 	String object that contains the new event description
	 * @return void
	 */
	public void editEventDescription(String newDescription)
	{
		this.eventDescription = newDescription;
	}

	/**
	 * Allows the user to change the location of the event
	 * @param newX
	 * 	New int x coordinate of event location - determine with maps api
	 * @param newY
	 * 	New int y coordinate of event location = determine with maps api
	 * @return void 
	 */
	public void editEventLocation(int newX, int newY)
	{
		this.x_coord = newX;
		this.y_coord = newY;
	}

	/**
	 * Getter method for the name of the event 
	 * @param void
	 * @return eventName
	 * 	Name of the user created event
	 */
	public String getEventName()
	{ 
		return this.eventName; 
	}

	/**
	 * Getter method for the date of the event
	 * @param void
	 * @return eventDate
	 * 	Date object that has when the event occurs
	 */
	public Date getEventDate()
	{
		return this.eventDate;
	}

	/**
	 * Getter method for the description of the event
	 * @param void
	 * @return eventDescription
	 * 	String object that contains the event's description
	 */
	public String getEventDescription()
	{
		return this.eventDescription;
	}

	/**
	 * Getter method for the creator of the event 
	 * @param void
	 * @return eventCreator
	 * 	SilvousUser object that created this event
	 */
	public SilvousUser getEventCreator()
	{
		return this.eventCreator;
	}
	

	/**
	 * When called will determine whether to show this object or not based on user's 
	 * distance from this event
	 * @param void
	 * @return void
	 */
	@Override
	public void update()
	{
		
	}



}

	
