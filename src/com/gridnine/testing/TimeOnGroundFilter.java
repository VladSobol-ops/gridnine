package com.gridnine.testing;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class TimeOnGroundFilter implements FlightFilter {
    @Override
    public List<Flight> filter(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> {
                    List<Segment> segments = flight.getSegments();
                    if (segments.size() <= 1) return true;
                    long totalGroundMinutes = 0;
                    for (int i = 0; i < segments.size() - 1; i++) {
                        totalGroundMinutes += Duration.between(
                                segments.get(i).getArrivalDate(),
                                segments.get(i + 1).getDepartureDate()
                        ).toMinutes();
                    }
                    return totalGroundMinutes <= 120;
                })
                .collect(Collectors.toList());
    }
}
