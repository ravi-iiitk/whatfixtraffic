package com.whatfix.traffic;

import java.util.Arrays;
import java.util.regex.Pattern;

public class SetupTrafficImpossible {

    public static String findBestVhcileOrbit(String weatherName, float [] orbSpeeds)
    {
        Vehicle vls[] = returnVehcileArray();
        Weather wths[] = returnWeatherArray();
        Orbit orbs[] = returnOrbitArray();
        return SetupTrafficImpossible.findBestRouteVehcile(vls, wths, orbs, weatherName, orbSpeeds);

    }

    private static Orbit[] returnOrbitArray() {
        Orbit[] orbits = new Orbit[4];
        Orbit orbit1 = new Orbit();
        orbit1.setDistance(18);
        orbit1.setNoOfCraters(20);
        orbit1.setOrbitName("Orbit1");

        Orbit orbit2 = new Orbit();
        orbit2.setDistance(20);
        orbit2.setNoOfCraters(10);
        orbit2.setOrbitName("Orbit2");

        Orbit orbit3 = new Orbit();
        orbit3.setDistance(30);
        orbit3.setNoOfCraters(15);
        orbit3.setOrbitName("Orbit3");

        Orbit orbit4 = new Orbit();
        orbit4.setDistance(15);
        orbit4.setNoOfCraters(18);
        orbit4.setOrbitName("Orbit4");

        orbits[0] = orbit1;
        orbits[1] = orbit2;
        orbits[2] = orbit3;
        orbits[3] = orbit4;
        return orbits;

    }

    public static int retrunSpeed(String fullText) {
        String numbers = fullText.replaceAll("[^0-9]", "");
        return Integer.parseInt(numbers);
    }

    private static String findBestRouteVehcile(Vehicle[] vhcls, Weather[] wethers, Orbit[] orbs, String weatherName,float [] orbSpeeds) {
        Weather actualWeather = SetupTrafficImpossible.returnWeatherObject(weatherName, wethers);
        for (int i = 0; i < 3; i++) {
            if (SetupTrafficImpossible.canThisVhcileRuninWeather(vhcls[i], weatherName)) {
                float timeOption1 = SetupTrafficImpossible.getTotalTimeForOptionOne(orbs,vhcls[i], actualWeather, orbSpeeds);
                float timeOption2 = SetupTrafficImpossible.getTotalTimeForOptionTwo(orbs,vhcls[i], actualWeather, orbSpeeds);
                float timeOption3 = SetupTrafficImpossible.getTotalTimeForOptionThree(orbs,vhcls[i], actualWeather, orbSpeeds);

                String BestOption = SetupTrafficImpossible.returnBestOption(timeOption1,timeOption2,timeOption3);
                vhcls[i].setBestOption(BestOption);

                float temp = timeOption1 < timeOption2 ? timeOption1:timeOption2;
                float minTime =  timeOption3 < temp ? timeOption3:temp;
                vhcls[i].setMinimumTime(minTime);

            } else {
                vhcls[i].setBestOption("None - Vehcile Not Eligible");
                vhcls[i].setMinimumTime(99999);
            }
        }
        Arrays.sort(vhcls);
        Vehicle prioVhce = vhcls[0];
        String vechcileName =prioVhce.getVehicleName();
        String bestOption = prioVhce.getBestOption();
        return "Vehcile " + vechcileName + bestOption;
    }

    private static String returnBestOption(float a,float b,float c)
    {
        String Option1 = " to Hallitharam via Orbit1 and RK Puram via Orbit4";
        String Option2 = " to Hallitharam via Orbit2 and RK Puram via Orbit4";
        String Option3 = " to RK Puram via Orbit3 and Hallitharam via Orbit4";
        if (a<b){if (a<c){return Option1;}
        else{ return Option3;}
        }else{ if (b<c){return Option2;}
        else{return Option3;}}
    }

    private static float getTotalTimeForOptionOne(Orbit  orbs[],Vehicle vhcls,Weather actualWeather,float [] speedOrbit)
    {
        // Orbit 1 and then Orbit 4
        Orbit orb1 = orbs[0];
        Orbit orb4 = orbs[3];
        float speedOrbitOne = speedOrbit[0];
        float speedOrbitFour = speedOrbit[3];
        float timeOrb1 = SetupTrafficImpossible.totalTimeToCrossOrbitForVehcile(orb1, vhcls, actualWeather, speedOrbitOne);
        float timeOrb4 = SetupTrafficImpossible.totalTimeToCrossOrbitForVehcile(orb4, vhcls, actualWeather, speedOrbitFour);
        return (timeOrb1+timeOrb4);
    }

    private static float getTotalTimeForOptionTwo(Orbit  orbs[],Vehicle vhcls,Weather actualWeather,float [] speedOrbit)
    {
        //Orbit 2 and then Orbit 4
        Orbit orb2 = orbs[1];
        Orbit orb4 = orbs[3];
        float speedOrbitTwo = speedOrbit[1];
        float speedOrbitFour = speedOrbit[3];
        float timeOrb2 = SetupTrafficImpossible.totalTimeToCrossOrbitForVehcile(orb2, vhcls, actualWeather, speedOrbitTwo);
        float timeOrb4 = SetupTrafficImpossible.totalTimeToCrossOrbitForVehcile(orb4, vhcls, actualWeather, speedOrbitFour);
        return (timeOrb2+timeOrb4);
    }

    private static float getTotalTimeForOptionThree(Orbit  orbs[],Vehicle vhcls,Weather actualWeather,float [] speedOrbit)
    {
        // Orbit 3 and then Orbit 4
        Orbit orb3 = orbs[2];
        Orbit orb4 = orbs[3];
        float speedOrbitThree = speedOrbit[2];
        float speedOrbitFour = speedOrbit[3];
        float timeOrb3 = SetupTrafficImpossible.totalTimeToCrossOrbitForVehcile(orb3, vhcls, actualWeather, speedOrbitThree);
        float timeOrb4 = SetupTrafficImpossible.totalTimeToCrossOrbitForVehcile(orb4, vhcls, actualWeather, speedOrbitFour);
        return (timeOrb3+timeOrb4);
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
        float timeCrossCrater = SetupTrafficImpossible.findTotalTimeToCrossCrater(obt, vhce, actualWeather);
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
            changePercent = Math.abs(changePercent);
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

    public static int increase(int x,float y, boolean z)
    {
        float percentage;
        int noCrater;
        if (z)
        {
            percentage = y /100;
            noCrater = (int)(x * (1 + percentage));
            return  noCrater;
        }
        else
        {

            percentage = y /100;
            noCrater =(int) (x * (1 - percentage));
            return noCrater;
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

