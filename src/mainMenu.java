import model.Reservation;
import model.iRoom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

/**
 * Class that acts as a "driver" for the Main Menu and handles all 5 possible requests detailed below
 * Scanner's nextLine() method is leveraged throughout to allow System input to drive menu and option navigation
 * The methods created in the Resource layer are leveraged throughout
 */
public class mainMenu {

    //create singleton object based on the class' methods specified in the resource layer
    private static final api.hotelResource hotelResource = api.hotelResource.getInstance();
    private static final String expectedDateFormat = "MM/dd/yyyy";

    public static void mainMenuOptions() {
        System.out.print("Welcome to the Bakopolus Hotel Reservation Portal!\n" +
                "--------------------------------------------\n" +
                "1. Find and reserve a room\n" +
                "2. See my reservations\n" +
                "3. Create an account\n" +
                "4. Admin\n" +
                "5. Exit\n" +
                "--------------------------------------------\n" +
                "Please select a number (1-5) for the appropriate menu option:\n");
    }
    
    public static void mainMenuNavigation() {

        boolean appRunning = true;

        while(appRunning) {

            try {
                mainMenuOptions();
                Scanner scanner = new Scanner(System.in);

                int selection = Integer.parseInt(scanner.nextLine());

                switch (selection) {
                    case 1:
                        findAndReserveRoom();
                        break;
                    case 2:
                        seeMyReservation();
                        break;
                    case 3:
                        createAccount();
                        break;
                    case 4:
                        appRunning = false;
                        adminMenu.adminMenuNavigation();
                        break;
                    case 5:
                        System.out.println("Thank you for using the portal - shutting down");
                        appRunning = false;
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

    private static void seeMyReservation() {
        final Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a valid email address: name@domain.com");
        final String customerEmail = scanner.nextLine();

        hotelResource.getCustomersReservations(customerEmail);
    }

    private static void findAndReserveRoom() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter desired Check-In date mm/dd/yyyy. Ex. 06/01/2024");
        Date checkIn;
        try {
            checkIn = new SimpleDateFormat(expectedDateFormat).parse(scanner.nextLine());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Enter desired Check-Out date mm/dd/yyyy. Ex. 06/05/2024");
        Date checkOut;
        try {
            checkOut = new SimpleDateFormat(expectedDateFormat).parse(scanner.nextLine());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Collection<iRoom> availableRooms = hotelResource.findARoom(checkIn, checkOut);
        System.out.println(availableRooms);

        System.out.println("\nWould you like to book a room? y/n");
        final String bookRoom = scanner.nextLine();

        if ("y".equals(bookRoom)) {
            System.out.println("Do you have an account with us? y/n");
            final String haveAccount = scanner.nextLine();

            if ("y".equals(haveAccount)) {
                System.out.println("Enter email address. Ex. name@domain.com");
                final String customerEmail = scanner.nextLine();

                System.out.println("What room number would you like to reserve?");
                final String roomNumber = scanner.nextLine();

                final Reservation reservation = hotelResource.bookARoom(customerEmail, hotelResource.getRoom(roomNumber), checkIn, checkOut);
                System.out.println(reservation);
            } else {
                System.out.println("Please, create an account.");
                mainMenuNavigation();
            }
        } else if ("n".equals(bookRoom)) {
            mainMenuNavigation();
        }
    }

    private static void createAccount() {
        final Scanner account = new Scanner(System.in);

        System.out.println("Enter a valid email address: name@domain.com");
        final String email = account.nextLine();

        System.out.println("Enter your first name:");
        final String firstName = account.nextLine();

        System.out.println("Enter your last name:");
        final String lastName = account.nextLine();

        try {
            hotelResource.createACustomer(email, firstName, lastName);
            System.out.println("Account created successfully!\n");
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
            createAccount();
        }
    }
}
