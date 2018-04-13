package com.whatfix.traffic;

import java.util.Scanner;


public class lengaburuTraffic {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the weather condtion: ");
        String weatherCond = scanner.nextLine();
        String weatherName = SetupTraffic.retrunWeatherName(weatherCond);
        while (weatherName.equalsIgnoreCase("not valid")) {
            System.out.println("Not a valied weather condtion ! Please again enter the weather condtion: ");
            weatherCond = scanner.nextLine();
            weatherName = SetupTraffic.retrunWeatherName(weatherCond);
        }

        System.out.println("Enter the Orbit-1 traffic condtion: ");
        String trafficCondOrbit1 = scanner.nextLine();
        while (!SetupTraffic.ifStringContainNumber(trafficCondOrbit1)) {
            System.out.println("Not a valied traffic condtion ! Please again enter the traffic condtion: ");
            trafficCondOrbit1 = scanner.nextLine();
        }
        float speedOrbitOne = SetupTraffic.retrunSpeed(trafficCondOrbit1);
        System.out.println("Enter the Orbit-2 traffic condtion: ");
        String trafficCondOrbit2 = scanner.nextLine();
        while (!SetupTraffic.ifStringContainNumber(trafficCondOrbit2)) {
            System.out.println("Not a valied traffic condtion ! Please again enter the traffic condtion: ");
            trafficCondOrbit2 = scanner.nextLine();
        }
        scanner.close();
        float speedOrbitTwo = SetupTraffic.retrunSpeed(trafficCondOrbit2);
        String bestRoute = SetupTraffic.findBestVhcileOrbit(weatherName,speedOrbitOne,speedOrbitTwo);
        System.out.println(bestRoute);

    }
}
