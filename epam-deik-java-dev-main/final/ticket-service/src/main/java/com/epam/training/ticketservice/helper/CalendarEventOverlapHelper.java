package com.epam.training.ticketservice.helper;

import com.epam.training.ticketservice.model.DateInterval;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CalendarEventOverlapHelper {

    private List<DateInterval> distinctIntervals;

    public CalendarEventOverlapHelper() {
        distinctIntervals = new ArrayList<DateInterval>();
    }

    public boolean isDistinct(DateInterval interval) {
        boolean result = false;
        if(distinctIntervals != null && distinctIntervals.size() > 0) {
            for(int i = 0; i < distinctIntervals.size(); i++) {
                if(overlaps(interval, distinctIntervals.get(i))) {
                    result = true;
                }
            }
        }
        distinctIntervals.add(interval);
        return result;
    }

    public boolean isIntervalsClean(List<DateInterval> intervals) {
        boolean result = true;
        for(DateInterval element:intervals) {
            if(isDistinct(element)) {
                result = false;
            }
        }
        return result;
    }

    public boolean overlaps(DateInterval left, DateInterval right) {
        boolean result = true;
        if(left.getEnd().before(right.getStart())
                && left.getEnd().before(right.getEnd())){
            result = false;
        }
        if(left.getStart().after(right.getStart())
                && left.getStart().after(right.getEnd())) {
            result = false;
        }
        return result;
    }
}
