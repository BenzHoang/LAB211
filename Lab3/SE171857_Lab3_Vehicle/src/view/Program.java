package view;

import controller.VehicleManage;
import java.text.ParseException;

public class Program {

    public void execute() throws ParseException {
        VehicleManage vm = new VehicleManage();
        vm.loadToFile();
        int choice;

        do {
            System.out.println("<<<===================== SHOWROOM VEHICLES ======================>>>");
            choice = Menu.getChoice(Menu.STORE_MENU);

            switch (choice) {
                case 1:
                    int c2;
                    do {
                        c2 = Menu.getChoice(Menu.MENU_1);
                        switch (c2) {
                            case 1:
                                vm.addVehicle();
                                break;
                            case 2:
                                vm.addBrand();
                                break;
                            case 3:
                                break;
                            default:
                                System.out.println("Invalid selection");
                        }
                    } while (c2 != 3);
                    break;
                case 2:
                    vm.checkVehicle();
                    break;

                case 3:
                    int c4;
                    do {
                        c4 = Menu.getChoice(Menu.MENU_2);
                        switch (c4) {
                            case 1:
                                vm.updateVehicle();
                                break;
                            case 2:
                                vm.updateBrands();
                                break;
                            case 3:
                                break;
                            default:
                                System.out.println("Invalid selection");
                        }
                    } while (c4 != 3);
                    break;

                case 4:
                    int c5;
                    do {

                        c5 = Menu.getChoice(Menu.MENU_3);

                        switch (c5) {
                            case 1:
                                vm.deleteVehicle();
                                break;
                            case 2:
                                vm.deleteBrands();
                                break;
                            case 3:
                                break;
                            default:
                                System.out.println("Invalid selection");
                        }
                    } while (c5 != 3);
                    break;

                case 5:
                    int c6;
                    do {

                        c6 = Menu.getChoice(Menu.MENU_4);

                        switch (c6) {
                            case 1:
                                vm.showNameByDescending();
                                break;
                            case 2:
                                vm.searchVehicleByID();
                                break;
                            case 3:
                                break;
                            default:
                                System.out.println("Invalid selection");
                        }
                    } while (c6 != 3);
                    break;
                case 6:
                    int c7;
                    do {

                        c7 = Menu.getChoice(Menu.MENU_5);

                        switch (c7) {
                            case 1:
                                vm.showAll();
                                vm.displayBrands();
                                break;
                            case 2:
                                vm.showByPrice();
                                break;
                            case 3:
                                break;
                            default:
                                System.out.println("Invalid selection");
                        }
                    } while (c7 != 3);
                    break;
                case 7:
                    vm.saveToFile();
                    break;

                case 8:
                    int c8;
                    do {

                        c8 = Menu.getChoice(Menu.MENU_6);

                        switch (c8) {
                            case 1:
                                vm.showAll();
                                break;
                            case 2:
                                vm.showByYear();
                                break;
                            case 3:
                                break;
                            default:
                                System.out.println("Invalid selection");
                        }
                    } while (c8 != 3);
                    break;
                case 9:
                    System.out.println("Good bye");
                    break;

            }

            System.out.println();
        } while (choice != Menu.STORE_MENU.length);
    }
}
