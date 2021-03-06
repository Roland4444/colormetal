package ru.com.avs.model;

import java.io.Serializable;

public enum ScaleType implements Serializable {

    SCALE_3000(9600, "10"),
    SCALE_600(4800, "4A"),
    SCALE_3000_2(9600, "10"),
    SCALE_60(9600, "00"),
    SCALE_60_BACK(9600, "00");   ////<<<<<<<<<<<>>>>>>>>>>>>>>>>////

    private int speed;
    private String command;

    ScaleType(int speed, String command) {
        this.speed = speed;
        this.command = command;
    }

    public int getSpeed() {
        return speed;
    }

    public String getCommand() {
        return command;
    }
}
