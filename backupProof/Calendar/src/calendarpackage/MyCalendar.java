package calendarpackage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

enum MONTHS
{
	January, Feburary, March, April, May, June, July, August, September, October, November, December;
}

enum DAYS
{
	Su, Mo, Tu, We, Th, Fr, Sa;
}

enum DAYSFULL
{
	Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday;
}
/**
 * MyCalendar creates a calendar and is able to display the calendar in month view and in day view. It also is able to create events on the calendar.
 * @author Zack
 * @version 1.0
 */
public class MyCalendar {
	GregorianCalendar c = new GregorianCalendar();
	int currentMonth= c.get(Calendar.MONTH);
	ArrayList<Event> listOfEvents = new ArrayList<Event>();
	MONTHS[] months = MONTHS.values();
	DAYS[] days = DAYS.values();
	DAYSFULL[] daysFull = DAYSFULL.values();
	/**
	 * Moves the calendars month forward by 1 month.
	 */
	public void nextMonth()
	{
		c.add(Calendar.MONTH,1);
		currentMonth= c.get(Calendar.MONTH);

	}
	
	/**
	 * Moves the calendar back by 1 month.
	 */
	public void previousMonth()
	{
		c.add(Calendar.MONTH,-1);
		currentMonth= c.get(Calendar.MONTH);

	}

	/**
	 * Moves the calendar forward by 1 day.
	 */
	public void nextDay()
	{
		c.add(Calendar.DAY_OF_MONTH,1);
		currentMonth= c.get(Calendar.MONTH);

	}

	/**
	 * Moves the calendar back by 1 day.
	 */
	public void previousDay()
	{
		c.add(Calendar.DAY_OF_MONTH,-1);
		currentMonth= c.get(Calendar.MONTH);

	}
	
	/**
	 * Creates the dayview in the console which lists all the events that occur on a single day in chronological order.
	 */
	public void dayView()
	{
		System.out.print(daysFull[c.get(Calendar.DAY_OF_WEEK)-1] + ", ");
		System.out.println(months[c.get(Calendar.MONTH)] + " " + c.get(Calendar.DAY_OF_MONTH) + ", " + c.get(Calendar.YEAR));
		for (Event eventToday: listOfEvents)
		{
			if (eventToday.sameDay(c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.YEAR)))
			{
				eventToday.printEvent();
			}
		}


	}
	/**
	 * Creates a month view calendar in the console that either highlights the current day, or highlights the days that events are on.
	 * @param type is 0 if it is set to show the current day, or 1 if it is supposed to show days where events take place
	 */
	public void printCalendar(int type)
	{
		int monthNameCenterer = ((14 - months[currentMonth].toString().length())/2);
		String centerTheMonth = "";
		for (int i = 0; i < monthNameCenterer; i++)
		{
			centerTheMonth = centerTheMonth + " ";
		}
		System.out.println( centerTheMonth + months[currentMonth] + " " + c.get(Calendar.YEAR));
		for (DAYS d: days)
		{
			System.out.print(d + " ");
		}
		System.out.println();
		GregorianCalendar tempFirstWeek = new GregorianCalendar(c.get(Calendar.YEAR), currentMonth, 1);

		int currentDayOfWeekPrinter = tempFirstWeek.get(Calendar.DAY_OF_WEEK)-1;
		int daysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		int daySpacer = 0;
		while (daySpacer < currentDayOfWeekPrinter)
		{
			System.out.print("   ");
			daySpacer++;
		}
		for (int dayNum = 1; dayNum <= daysInMonth; dayNum++)
		{
			if (currentDayOfWeekPrinter < 7)
			{
				if (dayNum < 10)
				{
					if (c.get(Calendar.DAY_OF_MONTH) == dayNum && type==0)
					{
						System.out.print("[" + dayNum + "]" + " ");
						currentDayOfWeekPrinter++;
					}
					else
					{
						boolean isEventToday = false;
						for (Event e: listOfEvents)
						{
							isEventToday = e.sameDay(currentMonth, dayNum, c.get(Calendar.YEAR));
						}
						if (isEventToday == false)
						{
							System.out.print(dayNum + "  ");
						}
						else
						{
							System.out.print("{" + dayNum + "}" + "  ");
						}
						currentDayOfWeekPrinter++;

					}
				}
				else
				{
					if (c.get(Calendar.DAY_OF_MONTH) == dayNum && type==0)
					{
						System.out.print("[" + dayNum + "] ");
						currentDayOfWeekPrinter++;

					}
					else
					{
						boolean isEventToday = false;
						for (Event e: listOfEvents)
						{
							if (e.sameDay(currentMonth, dayNum, c.get(Calendar.YEAR)))
							{
								isEventToday = e.sameDay(currentMonth, dayNum, c.get(Calendar.YEAR));
							}
						}
						if (isEventToday == false)
						{
							System.out.print(dayNum + " ");
						}
						else
						{
							System.out.print("{" + dayNum + "} ");
						}
						currentDayOfWeekPrinter++;

					}
				}
			}
			else
			{
				System.out.println("");
				currentDayOfWeekPrinter = 0;
				dayNum--;
			}
		}
		System.out.println();

	}

	/**
	 * Adds an event to the listOfEvents arrayList which stores all the events that appear on the calendar.
	 * @param newEvent the new event being added to the Calendar.
	 */
	public void addEvent(Event newEvent)
	{
		boolean conflict = false;
		if (newEvent.getEnd() != null)
		{
			for (Event conflictEvent : listOfEvents)
			{
				if (conflictEvent.getEnd() != null)
				{
					if (newEvent.getStart().compareTo(conflictEvent.getStart()) >= 0 && newEvent.getStart().compareTo(conflictEvent.getEnd()) <= 0)
					{
						conflict = true;
					}
					else if (newEvent.getEnd().compareTo(conflictEvent.getStart()) >= 0 && newEvent.getEnd().compareTo(conflictEvent.getEnd()) <= 0)
					{
						conflict = true;
					}
				}
				else
				{
					if (newEvent.getStart().compareTo(conflictEvent.getStart()) == 0 || newEvent.getEnd().compareTo(conflictEvent.getStart()) == 0)
					{
						conflict = true;
					}
					else if (conflictEvent.getStart().compareTo(newEvent.getStart()) >= 0 && conflictEvent.getStart().compareTo(newEvent.getEnd()) <= 0)
					{
						conflict = true;
					}
				}
			}
		}
		else
		{
			for (Event conflictEvent : listOfEvents)
			{
				if (conflictEvent.getEnd() != null)
				{
					if (newEvent.getStart().compareTo(conflictEvent.getStart()) >= 0 && newEvent.getStart().compareTo(conflictEvent.getEnd()) <= 0)
					{
						conflict = true;
					}
				}
				else
				{
					if (newEvent.getStart().compareTo(conflictEvent.getStart()) == 0)
					{
						conflict = true;
					}
				}
			}
		}
		if (conflict == false)
		{
			listOfEvents.add(newEvent);
			this.sortEvents();
			System.out.println("Event Created");
		}
		else
		{
			System.out.println("Event has a time conflict");
		}
	}

	/**
	 * Removes an event from the calendar from the specified date. 
	 * @param date the date on which the event being removed takes place.
	 * @param removedEvent name of the event that is being removed.
	 * @throws IOException
	 */
	public void removeEvent(String date, String removedEvent) throws IOException
	{
		Event removeThisEvent = null;
		boolean eventToBeRemoved = false;
		for (Event thisEvent : listOfEvents)
		{
			if (thisEvent.getName().equals(removedEvent))
			{
				removeThisEvent = thisEvent;
				eventToBeRemoved = true;
			}
		}
		if (eventToBeRemoved == true)
		{
			listOfEvents.remove(removeThisEvent);
		}
		this.reWrite();
	}
	
	/**
	 * Goes to the dayview of the inputted date.
	 * @param date the date that the dayview will jump to.
	 */
	public void getSelectedDate(String date)
	{
		String[] splitDate = date.split("/");
		int year = Integer.parseInt(splitDate[2]);
		int day = Integer.parseInt(splitDate[1]);
		int month = Integer.parseInt(splitDate[0])-1;
		c.set(year, month, day);
		this.dayView();
	}

	/**
	 * Removes all of the events in the listOfEvents from the inputted date
	 * @param date that all events will be removed from
	 * @throws IOException
	 */
	public void removeAllEvents(String date) throws IOException
	{
		String[] splitDate = date.split("/");
		int year = Integer.parseInt(splitDate[2]);
		int day = Integer.parseInt(splitDate[1]);
		int month = Integer.parseInt(splitDate[0])-1;
		for (int i = 0; i < listOfEvents.size(); i++)
		{
			if (listOfEvents.get(i).sameDay(month, day, year))
			{
				listOfEvents.remove(i);
				i--;
			}
		}
		this.reWrite();
	}

	/**
	 * Sorts all of the events in the event list in chronological order
	 */
	public void sortEvents()
	{
		Collections.sort(listOfEvents);
	}

	/**
	 * Loads events from "listOfEvents.txt" and inputs them into the calendar. If the file does not exist it creates one.
	 * @throws IOException
	 */
	public void load() throws IOException
	{
		File events = new File("listOfEvents.txt");
		if (events.exists())
		{
			FileInputStream fis = new FileInputStream("listOfEvents.txt");

			//Construct BufferedReader from InputStreamReader
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			String line = null;
			while ((line = br.readLine()) != null) {
				String[] eventTextLine = line.split("-");
				int eventYear = Integer.parseInt(eventTextLine[1]);
				int eventMonth = Integer.parseInt(eventTextLine[2]);
				int eventDay = Integer.parseInt(eventTextLine[3]);
				int eventStartHour = Integer.parseInt(eventTextLine[4]);
				int eventStartMin = Integer.parseInt(eventTextLine[5]);
				GregorianCalendar startTime = new GregorianCalendar(eventYear, eventMonth, eventDay, eventStartHour, eventStartMin);
				if (eventTextLine.length == 8)
				{
					int eventEndHour = Integer.parseInt(eventTextLine[6]);
					int eventEndMin = Integer.parseInt(eventTextLine[7]);
					GregorianCalendar endTime = new GregorianCalendar(eventYear, eventMonth, eventDay, eventEndHour, eventEndMin);
					this.addEvent(new Event(eventTextLine[0], startTime, endTime));
				}
				else
				{
					this.addEvent(new Event(eventTextLine[0], startTime));
				}
			}

			br.close();

		}
		else
		{
			PrintWriter eventsList = new PrintWriter("listOfEvents.txt");
			eventsList.close();
			System.out.println("This is the first run. File created.");
		}
	}

	/**
	 * Takes all of the events in the listOfEvents and uses them to update the "listOfEvents.txt".
	 * @throws IOException
	 */
	public void reWrite() throws IOException
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter("listOfEvents.txt"));
		writer.write("");
		for (Event e: listOfEvents)
		{
			String eventName = e.getName();
			int eventYear = e.getYear();
			int eventMonth = e.getMonth();
			int eventDay = e.getDay();
			int startHour = e.getStart().get(Calendar.HOUR_OF_DAY);
			int startMin = e.getStart().get(Calendar.MINUTE);
			if (e.getEnd() != null)
			{
				int endHour = e.getEnd().get(Calendar.HOUR_OF_DAY);
				int endMin = e.getEnd().get(Calendar.MINUTE);

				String formattedEvent = (eventName + "-" + eventYear + "-" + eventMonth + "-" +eventDay + "-" + 
						startHour + "-" +startMin + "-" +endHour + "-" +endMin + "\n");
				writer.append(formattedEvent);
			}
			else
			{
				String formattedEvent = (eventName + "-" + eventYear + "-" + eventMonth + "-" +eventDay + "-" + 
						startHour + "-" +startMin + "\n");
				writer.append(formattedEvent);
			}
		}
		writer.close();
	}

	/**
	 * A view that displays all events in a list in chronological order in the console.
	 */
	public void listEventsView()
	{
		this.sortEvents();
		System.out.println("Events:");
		for (Event e: listOfEvents)
		{
			System.out.print(daysFull[e.getStart().get(Calendar.DAY_OF_WEEK)] + " " + months[e.getStart().get(Calendar.MONTH)] + " " + e.getStart().get(Calendar.DAY_OF_MONTH) + ", " + e.getStart().get(Calendar.YEAR) + " ");
			e.printEvent();
		}
	}

}
