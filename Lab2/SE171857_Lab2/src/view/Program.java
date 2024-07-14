package view;

import controller.FlightManagementSystem;
import java.text.ParseException;

public class Program {

    public void execute() throws ParseException {
        FlightManagementSystem FMS = new FlightManagementSystem();
        FMS.loadData();
        int choice;

        do {
            System.out.println("<<<======================== FLIGH MANAGE ========================>>>");
            choice = Menu.getChoice(Menu.STORE_MENU);

            switch (choice) {
                case 1:
                    int c2;
                    do {
                        c2 = Menu.getChoice(Menu.MENU_1);

                        switch (c2) {
                            case 1:
                                FMS.addNewFlight();
                                break;
                            case 2:
                                FMS.displayFlightList();
                                break;
                            case 3:
                                break;
                            default:
                                System.out.println("Invalid selection");
                        }
                    } while (c2 != 3);
                    break;
                case 2:
                    int c3;
                    do {
                        c3 = Menu.getChoice(Menu.MENU_2);

                        switch (c3) {
                            case 1:
                                FMS.searchFlightBaseOnDepatureArrived();
                                break;
                            case 2:
                                FMS.bookPassengerReservation();
                                break;
                            case 3:
                                break;
                            default:
                                System.out.println("Invalid selection");
                        }
                    } while (c3 != 3);
                    break;

                case 3:
                    int c4;
                    do {
                        c4 = Menu.getChoice(Menu.MENU_3);
                        switch (c4) {
                            case 1:
                                FMS.performCheckIn();
                                break;
                            case 2:
                                FMS.printAvailableSeats();
                                break;
                            case 3:
                                FMS.changeSeat();
                                break;
                            case 4:
                                break;
                            default:
                                System.out.println("Invalid selection");
                        }
                    } while (c4 != 4);
                    break;

                case 4:
                    int c5;
                    do {

                        c5 = Menu.getChoice(Menu.MENU_4);

                        switch (c5) {
                            case 1:
                                FMS.addCrew();
                                break;
                            case 2:
                                FMS.manageCrewAssignments();
                                break;
                            case 3:
                                FMS.displayCrewMembers();
                                break;
                            case 4:
                                FMS.CrewsInFlight();
                                break;
                            case 5:
                                break;
                            default:
                                System.out.println("Invalid selection");
                        }
                    } while (c5 != 5);
                    break;

                case 5:
                    FMS.saveData();
                    break;
                case 6:
                    FMS.displayFlightListByDesending();
                    break;
                case 7:
                    if (!FMS.checkAdmin()) {
                        break;
                    }
                    int c7;
                    boolean exitSubMenu = false;
                    do {
                        c7 = Menu.getChoice(Menu.MENU_5);
                        switch (c7) {
                            case 1:
                                FMS.addCrewAdmin();
                                break;
                            case 2:
                                FMS.updateCrew();
                                break;
                            case 3:
                                FMS.displayFlightList();
                                break;
                            case 4:
                                FMS.updateFlight();
                                break;
                            case 5:
                                FMS.deleteFlightByNumber();
                                break;
                            case 6:
                                exitSubMenu = true;
                                break;
                            default:
                                System.out.println("Invalid selection");
                        }
                    } while (!exitSubMenu);
                    break;

                case 8:
                    System.out.println("Good Bye");
                    break;

            }

            System.out.println();
        } while (choice != Menu.STORE_MENU.length);
    }
}
