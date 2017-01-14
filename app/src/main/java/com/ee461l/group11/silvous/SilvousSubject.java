package com.ee461l.group11.silvous;

/**
 * Interface for Silvous Events
 * Used in Observer Design Pattern style
 */
public interface SilvousSubject
{
	/**
	 * Adds a SilvousObserver object to this SilvousSubject's list of observers
	 * @param sObs
	 * 	SilvousObserver object to observe this SilvousSubject
	 * @return void
	 */ 
	public void registerObserver(SilvousObserver sObs);

	/**
	 * Updates all SilvousObservers attached to this SilvousSubject based on its state
	 * @param void
	 * @return void
	 */
	public void notifyObservers();

}
