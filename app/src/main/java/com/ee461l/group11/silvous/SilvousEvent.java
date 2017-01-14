package com.ee461l.group11.silvous;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class SilvousEvent implements SilvousObserver
{
	/* Fields for the Event class */
	private SilvousLocation eventLocation;
	private int eventID;
	private String eventName;
	private Date eventDate;
	private long longDate;
	private String eventDescription;
	private String eventCreator;

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public SilvousEvent(){}

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
	public SilvousEvent(String name, String description, Date date, double x, double y)
	{
		this.eventName = name;
		this.eventDate = date;
		this.eventDescription = description;
		this.eventLocation = new SilvousLocation(x, y);
		//ask jimmy
		this.longDate = this.eventDate.getTime();
	}

	/**
	 * Sets a unique id for this event
	 * @param id
     */
	public void setEventID(int id)
	{
		this.eventID = id;
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
	public void editEventLocation(double newX, double newY)
	{
		this.eventLocation.changeLocation(newX, newY);
	}

	/**
	 * Get the location of this event
	 * @return
	 * 	SilvousLocation object
     */
	public SilvousLocation getEventLocation()
	{
		return this.eventLocation;
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

	public long getLongDate(){ return this.longDate; }

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
	 * Sets the email of the user who created this event
	 * @param eventCreator
	 * 	String containing user email
     */
	public void setEventCreator(String eventCreator) {
		this.eventCreator = eventCreator;
	}

	/**
	 * Getter method for the creator of the event 
	 * @param void
	 * @return eventCreator
	 * 	SilvousUser object that created this event
	 */
	public String getEventCreator()
	{
		return this.eventCreator;
	}

	public double getDistanceFrom(SilvousLocation location)
	{
		return this.eventLocation.calculateDistance(location);
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
		SilvousFirebaseManager.updateEvent(this);
	}

	public int getEventID()
	{
		return this.eventID;
	}

}

	
