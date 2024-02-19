package EmployeeModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

class LegalAgeChecker {

    static Date checkAge(String dateOfBirth) throws ParseException, IllegalArgumentException {
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat adoptedDateFormat = new SimpleDateFormat(pattern);
        adoptedDateFormat.setLenient(false);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        Date birthDate = adoptedDateFormat.parse(dateOfBirth);
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(formatter);
        Date today = adoptedDateFormat.parse(formattedDate);

        long differenceInMilliSeconds = Math.abs(today.getTime() - birthDate.getTime());
        long differenceInDays = TimeUnit.DAYS.convert(differenceInMilliSeconds, TimeUnit.MILLISECONDS);
        int daysIn18Years = 6574;
        if (differenceInDays < daysIn18Years) {
            throw new IllegalArgumentException("A Person must be an adult(>= 18 y.o.) to be employed");
        }

        return birthDate;
    }
}
