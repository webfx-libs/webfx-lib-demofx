package com.chrisnewland.demofx.effect.addon;

/**
 * @author Bruno Salmon
 */
public interface HasVolume {

    int getBandCount();

    void setBandVolumes(double[] bandVolumes);

}
