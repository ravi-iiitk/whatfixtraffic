package com.whatfix.traffic;

public class Vehicle implements Comparable<Vehicle> {

    private float speed;
    private float timeToCrossCrate;
    private boolean runInRainyWeather;
    private boolean runInWindyWeather;
    private boolean runInSunnyWeather;
    private String vehicleName;
    private String bestOption;
    private float minimumTime;
    private int priority;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public float getSpeed() {
        return speed;
    }

    public String getBestOption() {
        return bestOption;
    }

    public void setBestOption(String bestOrbit) {
        this.bestOption = bestOrbit;
    }

    public float getMinimumTime() {
        return minimumTime;
    }

    public void setMinimumTime(float minimumTime) {
        this.minimumTime = minimumTime;
    }

    public float getMaximumSpeed(float maxSpeedTraffic) {
        if(this.speed>maxSpeedTraffic)
            return maxSpeedTraffic;
        else if(this.speed<maxSpeedTraffic)
            return this.speed;
        else
           return this.speed;
    }


    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public float getTimeToCrossCrate() {
        return timeToCrossCrate;
    }

    public void setTimeToCrossCrate(int timeToCrossCrate) {
        this.timeToCrossCrate = timeToCrossCrate;
    }

    public boolean isRunInRainyWeather() {
        return runInRainyWeather;
    }

    public void setRunInRainyWeather(boolean runInRainyWeather) {
        this.runInRainyWeather = runInRainyWeather;
    }

    public boolean isRunInWindyWeather() {
        return runInWindyWeather;
    }

    public void setRunInWindyWeather(boolean runInWindyWeather) {
        this.runInWindyWeather = runInWindyWeather;
    }

    public boolean isRunInSunnyWeather() {
        return runInSunnyWeather;
    }

    public void setRunInSunnyWeather(boolean runInSunnyWeather) {
        this.runInSunnyWeather = runInSunnyWeather;
    }

    public int compareTo(Vehicle V) {
        return Float.compare(this.getMinimumTime(), V.getMinimumTime());
    }

}
