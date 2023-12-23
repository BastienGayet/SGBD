package GUI;

public class reveler {

    private double accx;
    private double accy;
    private double accz;
    private String typeconduite;
    private int timestampd;
    private double gyrox;
    private double gyroy;
    private double gyroz;


    public reveler(double accx, double accy, double accz, String typeconduite, int timestampd, double gyrox, double gyroy, double gyroz) {
        this.accx = accx;
        this.accy = accy;
        this.accz = accz;
        this.typeconduite = typeconduite;
        this.timestampd = timestampd;
        this.gyrox = gyrox;
        this.gyroy = gyroy;
        this.gyroz = gyroz;
    }

    @Override
    public String toString() {
        return "reveler{" +
                "accx=" + accx +
                ", accy=" + accy +
                ", accz=" + accz +
                ", typeconduite='" + typeconduite + '\'' +
                ", timestampd=" + timestampd +
                ", gyrox=" + gyrox +
                ", gyroy=" + gyroy +
                ", gyroz=" + gyroz +
                '}';
    }

    public double getAccx() {
        return accx;
    }

    public void setAccx(double accx) {
        this.accx = accx;
    }

    public double getAccy() {
        return accy;
    }

    public void setAccy(double accy) {
        this.accy = accy;
    }

    public double getAccz() {
        return accz;
    }

    public void setAccz(double accz) {
        this.accz = accz;
    }

    public String getTypeconduite() {
        return typeconduite;
    }

    public void setTypeconduite(String typeconduite) {
        this.typeconduite = typeconduite;
    }

    public int getTimestampd() {
        return timestampd;
    }

    public void setTimestampd(int timestampd) {
        this.timestampd = timestampd;
    }

    public double getGyrox() {
        return gyrox;
    }

    public void setGyrox(double gyrox) {
        this.gyrox = gyrox;
    }

    public double getGyroy() {
        return gyroy;
    }

    public void setGyroy(double gyroy) {
        this.gyroy = gyroy;
    }

    public double getGyroz() {
        return gyroz;
    }

    public void setGyroz(double gyroz) {
        this.gyroz = gyroz;
    }
}
