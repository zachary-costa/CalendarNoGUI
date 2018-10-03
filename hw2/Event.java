import java.util.Calendar;
import java.util.GregorianCalendar;
/**
 * Event stores a name/description and a start date and potentially an end date to the event in GregorianCalendar format if applicable
 * @author Zack Costa
 * @version 1.0
 */
public class Event implements Comparable<Object>{

	private String name;
	private GregorianCalendar start;
	private GregorianCalendar end;

	/**
	 * Constructor for Event class
	 * @param name is the name/description of the event
	 * @param start the date/time the event starts
	 * @param end the date/time the event ends if applicable
	 */
	public Event(String name,GregorianCalendar start, GregorianCalendar end)
	{
		this.name = name;
		this.start = start;
		this.end = end;

	}

	/**
	 * Constructor for the Event class if there is no end date/time
	 * @param name is the name/description of the event
	 * @param start the date/time the event starts
	 */
	public Event(String name, GregorianCalendar start)
	{
		this.name = name;
		this.start = start;
		this.end = null;
	}
	
	/**
	 * Returns the name of the Event
	 * @return name is the name/description of the event
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Returns a GregorianCalendar that is at the start date/time
	 * @return start the date/time the event starts
	 */
	public GregorianCalendar getStart()
	{
		return start;
	}

	/**
	 * Returns a GregorianCalendar that is at the end date/time
	 * @return end the date/time the event end
	 */
	public GregorianCalendar getEnd()
	{
		return end;
	}
	
	/**
	 * Returns the month the event takes place on
	 * @return month the month the event takes place on
	 */
	public int getMonth()
	{
		return start.get(Calendar.MONTH);
	}

	/**
	 * Returns the day of the month the event takes place on
	 * @return day of the month the event takes place on
	 */
	public int getDay()
	{
		return start.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Returns the year the event takes place on
	 * @return year the event takes place on
	 */
	public int getYear()
	{
		return start.get(Calendar.YEAR);
	}

	/**
	 * Input a month, day, and year and checks if that day matches the day of this event.
	 * @param month the month being compared to this event
	 * @param day the day of the month being compared to this event
	 * @param year the year being compared to this event
	 * @return true if the inputted information matches the date this event takes place on, or false if it is a different date
	 */
	public boolean sameDay(int month, int day, int year)
	{
		int listEventMonth = this.getMonth();
		int listEventDay = this.getDay();
		int listEventYear = this.getYear();
		if (month == listEventMonth && day == listEventDay && year == listEventYear)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Prints out the date and time information of this event in the console.
	 * Formats it with the Name of the event first, then it states the range of times the event takes place in.
	 */
	public void printEvent()
	{
		if (end != null)
		{
			if (start.get(Calendar.MINUTE) != 0 && end.get(Calendar.MINUTE) != 0)
			{
				System.out.println(name + " " + 
						start.get(Calendar.HOUR_OF_DAY) + ":" + start.get(Calendar.MINUTE) + "-" 
						+ end.get(Calendar.HOUR_OF_DAY) + ":" + end.get(Calendar.MINUTE));
			}
			else if (start.get(Calendar.MINUTE) + end.get(Calendar.MINUTE) == 0)
			{
				System.out.println(name + " " + 
						start.get(Calendar.HOUR_OF_DAY) + ":" + "00" + "-" 
						+ end.get(Calendar.HOUR_OF_DAY) + ":" + "00" );

			}
			else if (end.get(Calendar.MINUTE) == 0)
			{
				System.out.println(name + " " + 
						start.get(Calendar.HOUR_OF_DAY) + ":" + start.get(Calendar.MINUTE) + "-" 
						+ end.get(Calendar.HOUR_OF_DAY) + ":" + "00");

			}
			else
			{
				System.out.println(name + " " + 
						start.get(Calendar.HOUR_OF_DAY) + ":" + "00" + "-" 
						+ end.get(Calendar.HOUR_OF_DAY) + ":" + end.get(Calendar.MINUTE));

			}
		}
		else
		{
			if (start.get(Calendar.MINUTE) != 0)
			{
				System.out.println(name + " " + 
						start.get(Calendar.HOUR_OF_DAY) + ":" + start.get(Calendar.MINUTE));
				}
			else if (start.get(Calendar.MINUTE) == 0)
			{
				System.out.println(name + " " + 
						start.get(Calendar.HOUR_OF_DAY) + ":" + "00"); 

			}
			else
			{
				System.out.println(name + " " + 
						start.get(Calendar.HOUR_OF_DAY) + ":" + "00");
			}
		}
	}

	/**
	 * Compares the date/time of this event and another event.
	 * @return positive if the date is after, 0 if the dates are the same, negative if the date is before
	 */
	@Override
	public int compareTo(Object otherEvent) {
		return this.getStart().compareTo(((Event) otherEvent).getStart());

	}
}
