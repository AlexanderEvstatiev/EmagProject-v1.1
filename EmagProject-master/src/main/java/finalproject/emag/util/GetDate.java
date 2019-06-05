package finalproject.emag.util;

import finalproject.emag.util.exception.BaseException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

public class GetDate {

    public static LocalDate getDate(String inputDate) {
        LocalDate date = null;
        if(inputDate!=null) {
            try {
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                format.setLenient(false);
                date = format.parse(inputDate).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }
            catch (ParseException e) {
                throw new BaseException("Wrong date input.");
            }
        }
        return date;
    }
}