package olya.app.remindme.utils;

import java.time.LocalDate;
import olya.app.remindme.model.Action;
import olya.app.remindme.model.TimeInterval;

public class DateCalculations {
    public static LocalDate calculateNextExecutionDate(Action action){
        if (action.getLastExecutionDate() == null) {
            return action.getStartDate();
        } else {
            LocalDate lastExecutionDate = action.getLastExecutionDate();
            TimeInterval interval = action.getIntervalBetweenEvents();
            switch (interval.getTimeUnit()) {
                case DAYS:
                    return lastExecutionDate.plusDays(interval.getQuantity());
                case WEEKS:
                    return lastExecutionDate.plusWeeks(interval.getQuantity());
                case MONTHS:
                    return lastExecutionDate.plusMonths(interval.getQuantity());
                default:
                    return lastExecutionDate.plusYears(interval.getQuantity());
            }
        }

    }
}
