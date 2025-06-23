package com.gridnine.testing;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();

        System.out.println("Исходные перелёты:");
        printFlights(flights);

        System.out.println("\nИсключаем перелёты с вылетом до текущего времени:");
        List<Flight> filtered1 = new DepartureInPastFilter().filter(flights);
        printFlights(filtered1);

        System.out.println("\nИсключаем перелёты, где есть сегменты с прилётом раньше вылета:");
        List<Flight> filtered2 = new ArrivalBeforeDepartureFilter().filter(filtered1);
        printFlights(filtered2);

        System.out.println("\nИсключаем перелёты, где общее время на земле больше 2 часов:");
        List<Flight> filtered3 = new ExcessiveGroundTimeFilter().filter(filtered2);
        printFlights(filtered3);
    }

    private static void printFlights(List<Flight> flights) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        int i = 1;
        for (Flight flight : flights) {
            System.out.println("--------------------------------------------------");
            System.out.println("Перелёт " + i++ + ":");
            List<Segment> segments = flight.getSegments();

            for (int j = 0; j < segments.size(); j++) {
                Segment seg = segments.get(j);
                System.out.println("  Сегмент " + (j + 1) + ":");
                System.out.println("    Вылет : " + seg.getDepartureDate().format(fmt));
                System.out.println("    Прилёт: " + seg.getArrivalDate().format(fmt));
                if (j < segments.size() - 1) {
                    Duration groundTime = Duration.between(
                            seg.getArrivalDate(),
                            segments.get(j + 1).getDepartureDate()
                    );
                    System.out.println("    время на земле: "
                            + groundTime.toHours() + " ч "
                            + (groundTime.toMinutes() % 60) + " мин");
                    System.out.println();
                }
            }
            System.out.println();
        }
        System.out.println("Всего перелётов: " + flights.size());
        System.out.println("--------------------------------------------------\n");
    }
}
