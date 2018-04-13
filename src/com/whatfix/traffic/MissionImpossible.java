package com.whatfix.traffic;

import java.util.Scanner;


public class MissionImpossible {
    public static void main(String args[]) {
        float orbitTrafficspeeds [] = new float[4];
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the weather condtion: ");
        String weatherCond = scanner.nextLine();
        String weatherName = SetupTrafficImpossible.retrunWeatherName(weatherCond);
        while (weatherName.equalsIgnoreCase("not valid")) {
            System.out.println("Not a valied weather condtion ! Please again enter the weather condtion: ");
            weatherCond = scanner.nextLine();
            weatherName = SetupTrafficImpossible.retrunWeatherName(weatherCond);
        }
        for(int noOrbts  =0;noOrbts<4;noOrbts++)
        {
            System.out.println("Enter the Orbit-"+(noOrbts+1)+" traffic condtion: ");
            String trafficCondOrbit = scanner.nextLine();
            while (!SetupTrafficImpossible.ifStringContainNumber(trafficCondOrbit)) {
                System.out.println("Not a valied traffic condtion ! Please again enter the traffic condtion: ");
                trafficCondOrbit = scanner.nextLine();
            }
            float speedOrbit = SetupTrafficImpossible.retrunSpeed(trafficCondOrbit);
            orbitTrafficspeeds[noOrbts] = speedOrbit;
        }
        scanner.close();

        String bestRoute = SetupTrafficImpossible.findBestVhcileOrbit(weatherName,orbitTrafficspeeds);
        System.out.println(bestRoute);

    }
}
