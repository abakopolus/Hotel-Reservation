package model;

import java.util.regex.Pattern;

/**
 * Define Customer class, with an expected first name, last name, and email
 * Accessor methods are provided given the private class variables
 * emailChecker function below ensures validity of an input email
 */
public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;

    private static final String email_regex = "^(.+)@(.+).(.+)$";

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;

        emailChecker(email);
        this.email = email;
    }

    public final String getFirstName() {
        return firstName;
    }

    public final String getLastName() {
        return lastName;
    }

    public final String getEmail() {
        return email;
    }

    /**
     * Evaluate customer email to ensure validity and throw an exception if
     * not in the expected format
     * @param email Customer's email
     */
    private void emailChecker(String email) {
        Pattern pattern = Pattern.compile(email_regex);
        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid Email Address - Please Enter a Valid Email");
        }
    }

    @Override
    public final String toString() {
        return "First Name: " + firstName + "\nLast Name: " + lastName + "\nEmail:  " + email + "\n";
    }

}
