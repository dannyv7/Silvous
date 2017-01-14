package com.ee461l.group11.silvous;

/**
 * Interface to be implemented by Silvous objects to act as observers
 * Utilized in a Observer Design Pattern style
 */
public interface SilvousObserver
{
	/**
	 * Called by the SilvousSubject this SilvousObserver is attached to
	 * Updates its functionality based on new state
	 * @param void
	 * @return void
	 */
	public void update();
}
