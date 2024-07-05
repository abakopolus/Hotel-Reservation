import api.adminResource;
import model.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Class that acts as a "driver" for the Admin Menu and handles all 5 possible requests detailed below
 * Scanner's nextLine() method is leveraged throughout to allow System input to drive menu and option navigation
 * The methods created in the Resource layer are leveraged throughout
 */
public class adminMenu {

    //create singleton object based on the class' methods specified in the resource layer
    private static final api.adminResource adminResource = api.adminResource.getInstance();

    public static void adminMenuOptions() {
        System.out.print("\nBakopolus Hotel Admin Menu\n" +
                "--------------------------------------------\n" +
                "1. See all Customers\n" +
                "2. See all Rooms\n" +
                "3. See all Reservations\n" +
                "4. Add a Room\n" +
                "5. Back to Main Menu\n" +
                "--------------------------------------------\n" +
                "Please select a number (1-5) for the menu option:\n");
    }

    public static void adminMenuNavigation() {

        boolean appRunning = true;

        while(appRunning) {

            try {
                adminMenuOptions();
                Scanner scanner = new Scanner(System.in);
                int selection = Integer.parseInt(scanner.nextLine());

                switch (selection) {
                    case 1:
                        seeAllCustomers();
                        break;
                    case 2:
                        seeAllRooms();
                        break;
                    case 3:
                        seeAllReservations();
                        break;
                    case 4:
                        addARoom();
                        break;
                    case 5:
                        appRunning = false;
                        mainMenu.mainMenuNavigation();
                        break;
                    default:
                        System.out.println("Please enter a number between 1 and 5 to proceed");
                }
            }
            catch (Exception ex) {
                ex.getLocalizedMessage();
            }
        }
    }

    private static void addARoom() {
        Scanner roomDetails = new Scanner(System.in);
        roomType typeOfRoom;

        System.out.println("Enter new room number:");
        final String roomNumber = roomDetails.nextLine();
        int isInt = Integer.parseInt(roomNumber);

        System.out.println("Enter new room price:");
        double price = Double.parseDouble(roomDetails.nextLine());

        System.out.println("Enter a single (1) or double (2) room type:");
        int roomSize = Integer.parseInt(roomDetails.nextLine());

        if (roomSize == 1) {
            typeOfRoom = roomType.SINGLE;
        }
        else {
            typeOfRoom = roomType.DOUBLE;}

        List<iRoom> listRoom = new LinkedList<iRoom>();
        iRoom newRoom = new Room(roomNumber, price, typeOfRoom);
        listRoom.add(newRoom);
        adminResource.addRoom(listRoom);
    }

    private static void seeAllReservations() {
        adminResource.displayAllReservations();
    }

    private static void seeAllRooms() {
        Collection<iRoom> rooms = adminResource.getAllRooms();

        if (rooms.isEmpty()) {
            System.out.println("No rooms have been added to the hotel yet!");
        }
        //The double colon in the forEach statement (Class::Method) is leveraged for looping
        else {rooms.forEach(System.out::println);}
    }

    private static void seeAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers have been added to the hotel yet!");
        }
        //The double colon in the forEach statement (Class::Method) is leveraged for looping
        else {
            customers.forEach(System.out::println);
        }
    }
    }