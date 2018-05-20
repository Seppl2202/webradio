package de.dhbw.webradio.settings;

public class GeneralSettings {
    private int bufferSize;
    private int initialVolume;

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public int getInitialVolume() {
        return initialVolume;
    }

    public void setInitialVolume(int initialVolume) {
        this.initialVolume = initialVolume;
    }

    @Override
    public boolean equals(Object obj) {
        GeneralSettings that = (GeneralSettings) obj;
        return this.getInitialVolume() == that.getInitialVolume() &&
                this.getBufferSize() == that.getBufferSize();
    }

    @Override
    public String toString() {
        return "{ bufferSize: " + this.bufferSize + " , initialVolume: " + this.initialVolume + " }";
    }
}
