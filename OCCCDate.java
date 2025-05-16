import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

// OCCCDate class
public class OCCCDate implements Serializable {
    // Private variables
    private int dayOfMonth, monthOfYear, year;
    private GregorianCalendar gc;
    // private flags for the format
    private boolean dateFormat, dateStyle, dateDayName;

    // Constants for date format options
    public static final boolean FORMAT_US = true;
    public static final boolean FORMAT_EURO = false;
    // Constants for the date style options
    public static final boolean STYLE_NUMBERS = true;
    public static final boolean STYLE_NAMES = false;
    // Constants for either showing the day name or hiding it
    public static final boolean SHOW_DAY_NAME = false;
    public static final boolean HIDE_DAY_NAME = true;

    // Default constructor that uses the current date
    public OCCCDate() {
        gc = new GregorianCalendar();
        // Get current date
        dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
        monthOfYear = gc.get(Calendar.MONTH) + 1; // Adding 1 cause it's from 0-11
        year = gc.get(Calendar.YEAR);
        // Set default values for format
        dateFormat = FORMAT_US;
        dateStyle = STYLE_NUMBERS;
        dateDayName = HIDE_DAY_NAME;
    }

    // Constructor to initialize with a specific date
    public OCCCDate(int day, int month, int year) throws InvalidOCCCDateException {
        gc = new GregorianCalendar();
        gc.setLenient(false);
        try {

            gc.set(year, month - 1, day); // Subtracting 1 from the month cause GregorianCalendar goes from 0-11
            // Set date based on the provided date
            int gDay = gc.get(Calendar.DAY_OF_MONTH);
            int gMonth = gc.get(Calendar.MONTH) + 1; // Add 1 since 0-11
            int gYear = gc.get(Calendar.YEAR);
            if (day != gDay || month != gMonth || year != gYear) {
                throw new InvalidOCCCDateException();
            }
            this.dayOfMonth = day;
            this.monthOfYear = month;
            this.year = year;
        } catch (InvalidOCCCDateException e) {
            System.out.println("Invalid Date: " + day + "/" + month + "/" + year);

            this.dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
            this.monthOfYear = gc.get(Calendar.MONTH) + 1;
            this.year = gc.get(Calendar.YEAR);

            // This was used for testing.
            /*
             * System.out.println("Changed to: " + this.dayOfMonth + "/" + this.monthOfYear
             * + "/" + this.year);
             */
        }

    }

    // Constructor that uses a GregorianCalendar object
    public OCCCDate(GregorianCalendar gc) {
        this(); // Call the default constructor
        this.gc = gc;
        // Set the date based on the Gregoriancalendar object
        this.dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
        this.monthOfYear = gc.get(Calendar.MONTH) + 1; // Add 1, same reason as before.
        this.year = gc.get(Calendar.YEAR);
    }

    // Copy constructor
    public OCCCDate(OCCCDate d) throws InvalidOCCCDateException {
        this(d.dayOfMonth, d.monthOfYear, d.year);
    }

    // Getter for the day of the month
    public int getDayOfMonth() {
        return dayOfMonth;
    }

    // Method to get the name of the day
    public String getDayName() {
        // Array containing the names
        String[] dayNames = { "", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
        // Get the names of the day from the array
        return dayNames[gc.get(Calendar.DAY_OF_WEEK)];
    }

    // Getter
    public int getMonthNumber() {
        return monthOfYear;
    }

    // Method for monthName. Works the same as the one above.
    public String getMonthName() {
        String[] monthNames = { "", "January", "February", "March", "April", "May", "June", "July", "August",
                "September", "October", "November", "December" };
        return monthNames[monthOfYear];
    }

    // Getter for the year
    public int getYear() {
        return year;
    }

    // Setters for the format
    public void setDateFormat(boolean df) {
        dateFormat = df;
    }

    public void setStyleFormat(boolean sf) {
        dateStyle = sf;
    }

    public void setDayName(boolean nf) {
        dateDayName = nf;
    }

    // Finds the difference between years. Uses absolute so it doesn't give a
    // negative ever.
    public int getDifferenceInYears() {
        return Math.abs(year - (Calendar.getInstance().get(Calendar.YEAR)));
    }

    // Same as above but with a provided date
    public int getDifferenceInYears(OCCCDate d) {
        return Math.abs(year - d.year);
    }

    // Check if two dobs are equal.
    public boolean equals(OCCCDate dob) {
        return (dayOfMonth == dob.dayOfMonth && monthOfYear == dob.monthOfYear && year == dob.year);
    }

    // toString method.
    public String toString() {
        String formatDate = "";
        // Check the date format
        if (dateFormat == FORMAT_US) {
            if (dateStyle == STYLE_NUMBERS) {
                // US numeric format
                formatDate = monthOfYear + "/" + dayOfMonth + "/" + year;
            } else { // US name format
                formatDate = getMonthName() + " " + dayOfMonth + ", " + year;
            }
        } else {
            if (dateStyle == STYLE_NUMBERS) {
                // EU numeric format
                formatDate = dayOfMonth + "/" + monthOfYear + "/" + year;
            } else {
                // EU name format
                formatDate = monthOfYear + "/" + dayOfMonth + "/" + year;
                /* formatDate = dayOfMonth + "," + getMonthName() + ", " + year; */
            }
        }
        if (dateDayName) { // Check if to include the day name.
            formatDate = getDayName() + ", " + formatDate;

        }
        return formatDate; // Return the formatted date string.
    }
}
