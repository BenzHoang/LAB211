package view;

import controller.Validation;

public class Menu {

    public static final String[] STORE_MENU = {
        "|1.Add new vehicle and brands                                      |",
        "|2.Check to exist vehicle                                          |",
        "|3.Update vehicle and brands                                       |",
        "|4.Delete vehicle and brands                                       |",
        "|5.Search vehicle                                                  |",
        "|6.Display vehicle list                                            |",
        "|7.Save data to file                                               |",
        "|8.Print vehicle list                                              |",
        "|9.Quit:                                                           |"

    };
    public static final String[] MENU_1 = {
        "|1.Add vehicles                                                    |",
        "|2.Add brands                                                      |",
        "|3.Back to main menu                                               |",};

    public static final String[] MENU_2 = {
        "|1.Update vehicles                                                 |",
        "|2.Update brands                                                   |",
        "|3.Back to main menu                                               |",};
    
    public static final String[] MENU_3 = {
        "|1.Delete vehicles                                                 |",
        "|2.Delete brands                                                   |",
        "|3.Back to main menu                                               |",};

    public static final String[] MENU_4 = {
        "|1.Search by name vehicle and sorted by name descending            |",
        "|2.Search by id vehicle                                            |",
        "|3.Back to main menu                                               |",};

    public static final String[] MENU_5 = {
        "|1.Show all                                                        |",
        "|2.Show by price                                                   |",
        "|3.Back to main menu                                               |",};

    public static final String[] MENU_6 = {
        "|1.Show all                                                        |",
        "|2.Print by year                                                   |",
        "|3.Back to main menu                                               |",};

    public static int getChoice(String[] menu) {
        Validation valid = new Validation();
        for (int i = 0; i < menu.length; i++) {
            System.out.println(menu[i]);
        }
        System.out.println("<==================================================================>");
        return valid.checkInt("Choose: ", 1, menu.length);
    }

}
