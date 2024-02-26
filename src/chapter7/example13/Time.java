package chapter7.example13;

public class Time {

    private int hour, minute, second;

    public Time(int hour, int minute, int second) {
        setHour(hour);
        setMinute(minute);
        setSecond(second);
    }

    public int getHour() { return this.hour; }
    public void setHour(int hour) { this.hour = hour; }

    public int getMinute() { return this.minute; }
    public void setMinute(int minute) { this.minute = minute; }

    public int getSecond() { return this.second; }
    public void setSecond(int second) { this.second = second; }

    @Override
    public String toString() {
        return "Time{" +
                "hour=" + hour +
                ", minute=" + minute +
                ", second=" + second +
                '}';
    }
}
