import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class OCCCDate implements Serializable {
    private int dayOfMonth, monthOfYear, year;
    private GregorianCalendar gc;
    private boolean dateFormat, dateStyle, dateDayName;

    public static final boolean FORMAT_US = true;
    public static final boolean FORMAT_EURO = false;
    public static final boolean STYLE_NUMBERS = true;
    public static final boolean STYLE_NAMES = false;
    public static final boolean SHOW_DAY_NAME = false;
    public static final boolean HIDE_DAY_NAME = true;

    public OCCCDate() {
        gc = new GregorianCalendar();
        dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
        year = gc.get(Calendar.YEAR);
        dateFormat = FORMAT_US;
        dateStyle = STYLE_NUMBERS;
        dateDayName = HIDE_DAY_NAME;
    }

    public OCCCDate(int day, int month, int year) throws InvalidOCCCDateException {
        gc = new GregorianCalendar();
        gc.setLenient(false);
        try {

            gc.set(year, month - 1, day);
            int gDay = gc.get(Calendar.DAY_OF_MONTH);
            int gMonth = gc.get(Calendar.MONTH) + 1;
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
        }

    }

    public OCCCDate(GregorianCalendar gc) {
        this();
        this.gc = gc;
        this.dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
        this.monthOfYear = gc.get(Calendar.MONTH) + 1;
        this.year = gc.get(Calendar.YEAR);
    }

    public OCCCDate(OCCCDate d) throws InvalidOCCCDateException {
        this(d.dayOfMonth, d.monthOfYear, d.year);
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public String getDayName() {
        String[] dayNames = { "", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
        return dayNames[gc.get(Calendar.DAY_OF_WEEK)];
    }

    public int getMonthNumber() {
        return monthOfYear;
    }

    public String getMonthName() {
        String[] monthNames = { "", "January", "February", "March", "April", "May", "June", "July", "August",
                "September", "October", "November", "December" };
        return monthNames[monthOfYear];
    }

    public int getYear() {
        return year;
    }

    public void setDateFormat(boolean df) {
        dateFormat = df;
    }

    public void setStyleFormat(boolean sf) {
        dateStyle = sf;
    }

    public void setDayName(boolean nf) {
        dateDayName = nf;
    }

    public int getDifferenceInYears() {
        return Math.abs(year - (Calendar.getInstance().get(Calendar.YEAR)));
    }

    public int getDifferenceInYears(OCCCDate d) {
        return Math.abs(year - d.year);
    }

    public boolean equals(OCCCDate dob) {
        return (dayOfMonth == dob.dayOfMonth && monthOfYear == dob.monthOfYear && year == dob.year);
    }

    public String toString() {
        String formatDate = "";
        if (dateFormat == FORMAT_US) {
            if (dateStyle == STYLE_NUMBERS) {
                formatDate = monthOfYear + "/" + dayOfMonth + "/" + year;
            } else {
                formatDate = getMonthName() + " " + dayOfMonth + ", " + year;
            }
        } else {
            if (dateStyle == STYLE_NUMBERS) {
                formatDate = dayOfMonth + "/" + monthOfYear + "/" + year;
            } else {
                formatDate = monthOfYear + "/" + dayOfMonth + "/" + year;
            }
        }
        if (dateDayName) {
            formatDate = getDayName() + ", " + formatDate;

        }
        return formatDate;
    }
}
