package com.whatfix.traffic;

import java.util.Arrays;
import java.util.regex.Pattern;

public class SetupTraffic {

    public static String findBestVhcileOrbit(String weatherName, float speedOrbitOne, float speedOrbitTwo)
    {
        Vehicle vls[] = returnVehcileArray();
        Weather wths[] = returnWeatherArray();
        Orbit orbs[] = returnOrbitArray();
        return SetupTraffic.findBestRouteVehcile(vls, wths, orbs, weatherName, speedOrbitOne, speedOrbitTwo);
    }

    private static Orbit[] returnOrbitArray() {
        Orbit[] orbits = new Orbit[2];
        Orbit orbit1 = new Orbit();
        orbit1.setDistance(18);
        orbit1.setNoOfCraters(20);
        orbit1.setOrbitName("Orbit1");

        Orbit orbit2 = new Orbit();
        orbit2.setDistance(20);
        orbit2.setNoOfCraters(10);
        orbit2.setOrbitName("Orbit2");
        orbits[0] = orbit1;
        orbits[1] = orbit2;
        return orbits;

    }

    public static int retrunSpeed(String fullText) {
        String numbers = fullText.replaceAll("[^0-9]", "");
        return Integer.parseInt(numbers);
    }

    private static String findBestRouteVehcile(Vehicle[] vhcls, Weather[] wethers, Orbit[] orbs, String weatherName, float speedOrbitOne, float speedOrbitTwo) {

        Orbit orb1 = orbs[0];
        Orbit orb2 = orbs[1];
        Weather actualWeather = SetupTraffic.returnWeatherObject(weatherName, wethers);
        for (int i = 0; i < 3; i++) {

            if (SetupTraffic.canThisVhcileRuninWeather(vhcls[i], weatherName)) {
                float timeOrb1 = SetupTraffic.totalTimeToCrossOrbitForVehcile(orb1, vhcls[i], actualWeather, speedOrbitOne);
                float timeOrb2 = SetupTraffic.totalTimeToCrossOrbitForVehcile(orb2, vhcls[i], actualWeather, speedOrbitTwo);
                if (timeOrb1 < timeOrb2) {
                    vhcls[i].setBestOption("Orbit1");
                    vhcls[i].setMinimumTime(timeOrb1);
                } else if (timeOrb2 < timeOrb1) {
                    vhcls[i].setBestOption("Orbit2");
                    vhcls[i].setMinimumTime(timeOrb2);
                } else {
                    vhcls[i].setBestOption("Orbit1");
                    vhcls[i].setMinimumTime(timeOrb1);
                }
            } else {
                vhcls[i].setBestOption("Orbit1");
                vhcls[i].setMinimumTime(99999);
            }
        }
        Arrays.sort(vhcls);
        String vechcileName = "";
        Vehicle prioVhce = vhcls[0];
        if (vhcls[0].getMinimumTime() == vhcls[1].getMinimumTime() && vhcls[1].getMinimumTime() == vhcls[2].getMinimumTime()) {
            if (vhcls[0].getBestOption().equalsIgnoreCase(vhcls[1].getBestOption()) && vhcls[1].getBestOption().equalsIgnoreCase(vhcls[2].getBestOption())) {
                if (vhcls[0].getPriority() > vhcls[1].getPriority()) {
                    prioVhce = vhcls[0];
                } else {
                    prioVhce = vhcls[1];
                }
                if (prioVhce.getPriority() > vhcls[2].getPriority()) {
                    prioVhce = prioVhce;
                } else {
                    prioVhce = vhcls[2];
                }

            }
        }

        if (vhcls[0].getMinimumTime() == vhcls[1].getMinimumTime()) {
            if (vhcls[0].getBestOption().equalsIgnoreCase(vhcls[1].getBestOption())) {
                if (vhcls[0].getPriority() > vhcls[1].getPriority()) {
                    prioVhce = vhcls[0];
                } else {
                    prioVhce = vhcls[1];
                }
            }
        }


        vechcileName = prioVhce.getVehicleName();
        String orbitName = prioVhce.getBestOption();
        return "Vehcile " + vechcileName + " on " + orbitName;
    }

    private static boolean canThisVhcileRuninWeather(Vehicle v, String weatherName) {
        if (weatherName.toLowerCase().contains("sunny")) {
            return v.isRunInSunnyWeather();
        } else if (weatherName.toLowerCase().contains("rainy")) {
            return v.isRunInRainyWeather();
        } else if (weatherName.toLowerCase().contains("windy")) {
            return v.isRunInWindyWeather();
        } else {
            return false;
        }

    }

    public static Weather returnWeatherObject(String weatherName, Weather wther[]) {
        for (int j = 0; j < 3; j++) {
            if (weatherName.toLowerCase().equalsIgnoreCase(wther[j].getName())) {
                return wther[j];
            }
        }
        return null;
    }

    private static float totalTimeToCrossOrbitForVehcile(Orbit obt, Vehicle vhce, Weather actualWeather, float speed) {
        float maxSpeedVchile = vhce.getMaximumSpeed(speed);
        float timeCrossCrater = SetupTraffic.findTotalTimeToCrossCrater(obt, vhce, actualWeather);
        float timeToCrossOrbit = obt.getDistance()/maxSpeedVchile;
        return timeCrossCrater + timeToCrossOrbit;
    }

    private static float findTotalTimeToCrossCrater(Orbit obt, Vehicle vch, Weather actualWeather) {
        float changePercent = actualWeather.getChangeInCrate();
        float timeMin ;
        float timeHour;
        int noOrgCharter = obt.getNoOfCraters();
        if (changePercent > 0) {
            int totalCrater = SetupTrafficImpossible.increase(noOrgCharter,changePercent,true);
            timeMin=totalCrater  * vch.getTimeToCrossCrate();
            timeHour = timeMin * Float.parseFloat("0.016666");
            return timeHour;
        } else if (changePercent < 0) {
            int totalCrater = SetupTrafficImpossible.increase(noOrgCharter,changePercent,false);
            timeMin= totalCrater * vch.getTimeToCrossCrate();
            timeHour = timeMin * Float.parseFloat("0.016666");
            return timeHour;
        } else {

            timeMin = obt.getNoOfCraters() * vch.getTimeToCrossCrate();
            timeHour = timeMin * Float.parseFloat("0.016666");
            return timeHour;

        }


    }

    public static String retrunWeatherName(String weatherCond) {
        if (weatherCond.toLowerCase().contains("sunny")) {
            return "Sunny";
        } else if (weatherCond.toLowerCase().contains("rainy")) {
            return "Rainy";
        } else if (weatherCond.toLowerCase().contains("windy")) {
            return "Windy";
        } else {
            return "not valid";
        }

    }

    private static Vehicle[] returnVehcileArray() {
        Vehicle[] vehciles = new Vehicle[3];
        Vehicle bike = new Vehicle();
        bike.setVehicleName("Bike");
        bike.setSpeed(10);
        bike.setTimeToCrossCrate(2);
        bike.setRunInRainyWeather(false);
        bike.setRunInSunnyWeather(true);
        bike.setRunInWindyWeather(true);
        bike.setPriority(3);
        vehciles[0] = bike;

        Vehicle car = new Vehicle();
        car.setVehicleName("Super Car");
        car.setSpeed(20);
        car.setTimeToCrossCrate(3);
        car.setRunInRainyWeather(true);
        car.setRunInSunnyWeather(true);
        car.setRunInWindyWeather(true);
        car.setPriority(1);
        vehciles[1] = car;

        Vehicle tuktuk = new Vehicle();
        tuktuk.setVehicleName("Tuk Tuk");
        tuktuk.setSpeed(12);
        tuktuk.setTimeToCrossCrate(1);
        tuktuk.setRunInRainyWeather(true);
        tuktuk.setRunInSunnyWeather(true);
        tuktuk.setRunInWindyWeather(false);
        tuktuk.setPriority(2);
        vehciles[2] = tuktuk;

        return vehciles;
    }

    private static Weather[] returnWeatherArray() {
        Weather[] weathers = new Weather[3];
        Weather sunny = new Weather();
        sunny.setName("Sunny");
        sunny.setChangeInCrate(-10);
        Weather rainy = new Weather();
        rainy.setName("Rainy");
        rainy.setChangeInCrate(20);
        Weather windy = new Weather();
        windy.setName("Windy");
        windy.setChangeInCrate(0);
        weathers[0] = sunny;
        weathers[1] = rainy;
        weathers[2] = windy;
        return weathers;

    }

    public static boolean ifStringContainNumber(String inpStr) {
        String regex = "(.)*(\\d)(.)*";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(inpStr).matches();
    }
}
