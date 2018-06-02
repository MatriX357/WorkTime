package pl.maciejnalewajka.worktime.Elements;

public class Elements {
    private int progress;
    private String name;
    private String active;
    private String percent;

    public Elements(int progress, String name, String percent, String active) {
        this.progress = progress;
        this.name = name;
        this.percent = percent;
        this.active = active;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}