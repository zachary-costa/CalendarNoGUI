package calendarpackage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.GregorianCalendar;
import java.util.Scanner;
/**
 * MyCalendarTester creates a MyCalendar and interacts with it using a scanner to traverse all of the methods inside of MyCalendar with a menu system.
 * @author Zack Costa
 * @version 1.0
 */
public class MyCalendarTester {
	public static void main(String [] args) throws ParseException, IOException
	{

		MyCalendar theCalendar = new MyCalendar();
		Scanner sc = new Scanner(System.in);
		theCalendar.printCalendar(0);
		System.out.println("Select one of the following options: [L]oad   [V]iew by  [C]reate, [G]o to [E]vent list [D]elete  [Q]uit");
		int currentMenu = 0; 
		while (sc.hasNextLine())
		{
			String input = sc.nextLine();

			if (input.equals("l") && currentMenu == 0)
			{
				theCalendar.load();
			}
			if (input.equals("v") && currentMenu == 0)
			{
				currentMenu = 1;
				System.out.println("[D]ay view or [M]onth view ? ");
			}
			else if (input.equals("c") && currentMenu == 0)
			{
				System.out.println("Create an Event:\nPlease enter a name for the Event.\n ");
				String eventName = sc.nextLine();
				System.out.println("Please enter a date for the Event. (MM/DD/YYYY)\n" );
				String date = sc.nextLine();
				String[] splitDate = date.split("/");
				int eventYear = Integer.parseInt(splitDate[2]);
				int eventDayOfMonth = Integer.parseInt(splitDate[1]);
				int eventMonth = Integer.parseInt(splitDate[0])-1;
				System.out.println("Enter a start time.(HH:MM 24-hour clock)\n");
				String startTime = sc.nextLine();
				String[] splitTime = startTime.split(":");
				int eventStartHour = Integer.parseInt(splitTime[0]);
				int eventStartMinute = Integer.parseInt(splitTime[1]);
				GregorianCalendar startTimeCalendar = new GregorianCalendar(eventYear, eventMonth, eventDayOfMonth, eventStartHour, eventStartMinute);
				System.out.println("Enter an end time. If there is no end time type 'n'. (HH:MM 24-hour clock)\n");
				String endTime = sc.nextLine();
				BufferedWriter writer = new BufferedWriter(new FileWriter("listOfEvents.txt", true));
				if (endTime.equals("n"))
				{
					endTime = null;
					theCalendar.addEvent(new Event(eventName,startTimeCalendar));
					writer.append(eventName + "-" + eventYear + "-" + eventMonth + "-" + eventDayOfMonth + "-" + eventStartHour + "-" + eventStartMinute + "\n");

				}
				else
				{
					String[] splitTimeEnd = endTime.split(":");
					int eventEndHour = Integer.parseInt(splitTimeEnd[0]);
					int eventEndMinute = Integer.parseInt(splitTimeEnd[1]);
					GregorianCalendar endTimeCalendar = new GregorianCalendar(eventYear, eventMonth, eventDayOfMonth, eventEndHour, eventEndMinute);
					theCalendar.addEvent(new Event(eventName,startTimeCalendar,endTimeCalendar));
					writer.append(eventName + "-" + eventYear + "-" + eventMonth + "-" + eventDayOfMonth + "-" + eventStartHour + "-" + eventStartMinute + "-" + eventEndHour + "-" + eventEndMinute + "\n");

				}
				writer.close();
				currentMenu = 0;
				System.out.println("Select one of the following options: [L]oad   [V]iew by  [C]reate, [G]o to [E]vent list [D]elete  [Q]uit");

			}
			
			else if (input.equals("d") && currentMenu == 0)
			{
				currentMenu = 4;
				System.out.println("Enter a date (MM/DD/YYYY) ");
				String deleteDate = sc.nextLine();
				System.out.println("Delete [S]elected or [A]ll from current Date: " + deleteDate);
				String nextInput = sc.nextLine();
				if (nextInput.equals("s"))
				{
					System.out.println("Enter event name: ");
					String deleteEventName = sc.nextLine();
					theCalendar.removeEvent(deleteDate, deleteEventName);
					System.out.println("Event Deleted");
				}
				else if (nextInput.equals("a"))
				{
					theCalendar.removeAllEvents(deleteDate);
					System.out.println("Events Deleted");
				}
				currentMenu = 0;
				System.out.println("Select one of the following options: [L]oad   [V]iew by  [C]reate, [G]o to [E]vent list [D]elete  [Q]uit");
			}
			
			else if (input.equals("g") && currentMenu == 0)
			{
				System.out.println("Enter a date to jump to(MM/DD/YYYY) ");
				String jumpToDate = sc.nextLine();
				theCalendar.getSelectedDate(jumpToDate);
				currentMenu = 2;
				System.out.println("[P]revious or [N]ext or [M]ain Menu ?"); 
			}
			
			else if (input.equals("e") && currentMenu == 0)
			{
				theCalendar.listEventsView();
				System.out.println();
				System.out.println("Select one of the following options: [L]oad   [V]iew by  [C]reate, [G]o to [E]vent list [D]elete  [Q]uit");

			}
			else if (input.equals("q") && currentMenu == 0)
			{
				theCalendar.reWrite();
				System.out.println("Saving and Exiting Calendar");
				sc.close();
				break;
			}
			
			else if (input.equals("d") && currentMenu == 1)
			{
				currentMenu = 2;
				theCalendar.dayView();
				System.out.println("[P]revious or [N]ext or [M]ain Menu ?"); 
			}
			else if (input.equals("m") && currentMenu == 1)
			{
				currentMenu = 3;
				theCalendar.printCalendar(1);
				System.out.println("[P]revious or [N]ext or [M]ain Menu ?"); 
			}

			else if (input.equals("n"))
			{
				if (currentMenu == 2)
				{
					theCalendar.nextDay();
					theCalendar.dayView();
				}
				else if (currentMenu == 3)
				{
					theCalendar.nextMonth();
					theCalendar.printCalendar(1);

				}
				System.out.println("[P]revious or [N]ext or [M]ain Menu ?"); 
			}
			else if (input.equals("p"))
			{
				if (currentMenu == 2)
				{
					theCalendar.previousDay();
					theCalendar.dayView();
				}
				else if (currentMenu == 3)
				{
					theCalendar.previousMonth();
					theCalendar.printCalendar(1);

				}
				System.out.println("[P]revious or [N]ext or [M]ain Menu ?"); 

			}
			else if (input.equals("m") && currentMenu != 1)
			{
				currentMenu = 0;
				System.out.println();
				System.out.println("Select one of the following options: [L]oad   [V]iew by  [C]reate, [G]o to [E]vent list [D]elete  [Q]uit");

			}
		}
	}
}

