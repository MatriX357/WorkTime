package pl.maciejnalewajka.worktime;

public class Elementy {
    private int progres;
    private String name;
    private String active;
    private String procent;

    public Elementy(int progres, String name, String procent, String active) {
        this.progres = progres;
        this.name = name;
        this.procent = procent;
        this.active = active;
    }

    public String getProcent() {
        return procent;
    }

    public void setProcent(String procent) {
        this.procent = procent;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public int getProgres() {
        return progres;
    }

    public void setProgres(int progres) {
        this.progres = progres;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}