package com.gridnine.testing;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DepartureInPastFilterTest {

    @Test
    void testFilterExcludesFlightsWithPastDeparture() {

        Segment pastSegment = new Segment(
                LocalDateTime.now().minusDays(2),
                LocalDateTime.now().plusHours(2)
        );
        Flight flightWithPast = new Flight(Collections.singletonList(pastSegment));


        Segment futureSegment = new Segment(
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1).plusHours(2)
        );
        Flight flightWithFuture = new Flight(Collections.singletonList(futureSegment));

        List<Flight> flights = Arrays.asList(flightWithPast, flightWithFuture);

        DepartureInPastFilter filter = new DepartureInPastFilter();
        List<Flight> result = filter.filter(flights);

        assertEquals(1, result.size());
        assertTrue(result.contains(flightWithFuture));
        assertFalse(result.contains(flightWithPast));
    }
}
