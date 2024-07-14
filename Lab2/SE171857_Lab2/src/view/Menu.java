package view;

import controller.Validation;

public class Menu {

    public static final String[] STORE_MENU = {
        "|1.Flight schedule management                                      |",
        "|2.Passenger reservation and booking                               |",
        "|3.Passenger check-in and seat allocation                          |",
        "|4.Crew management and assignments                                 |",
        "|5.Data storage for flight details, reservations, and assignments  |",
        "|6.Display flight information by date descending                   |",
        "|7.Administrator access for system management                      |",
        "|8.Quit:                                                           |"

    };

    public static final String[] MENU_1 = {
        "|1.Add a flight                                                    |",
        "|2.Show all flights                                                |",
        "|3.Back to main menu                                               |",};

    public static final String[] MENU_2 = {
        "|1.Search avaible flights base on depature and arrived locations   |",
        "|2.Create Reservation                                              |",
        "|3.Back to main menu                                               |",};

    public static final String[] MENU_3 = {
        "|1.Check In and book seats alocations                              |",
        "|2.Status avaible seat by flight number                            |",
        "|3.Change Seat                                                     |",
        "|4.Back to main menu                                               |",};

    public static final String[] MENU_4 = {
        "|1.Add Crews                                                       |",
        "|2.Add Crews to Flights                                            |",
        "|3.Display Crews Member                                            |",
        "|4.Display Crews in Flights                                        |",
        "|5.Back to main menu                                               |",};

    public static final String[] MENU_5 = {
        "|1.Add new admin                                                   |",
        "|2.Update crew                                                     |",
        "|3.Show all flight                                                 |",
        "|4.Update flight by flight number                                  |",
        "|5.Remove flight by flight number                                  |",
        "|6.Back to main menu                                               |",};

    public static int getChoice(String[] menu) {
        Validation valid = new Validation();
        for (int i = 0; i < menu.length; i++) {
            System.out.println(menu[i]);
        }
        System.out.println("<==================================================================>");
        return valid.checkInt("Choose: ", 1, menu.length);
    }

}
