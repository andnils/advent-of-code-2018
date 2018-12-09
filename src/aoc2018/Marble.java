package aoc2018.day09;

public class Marble {

    private final long points;
    private Marble prev;
    private Marble next;
    
    public Marble(long points) {
        this.points = points;
    }

    public long getPoints() {
        return points;
    }
    
    public Marble getNext() {
        return next;
    }

    public Marble setNext(Marble next) {
        this.next = next;
        return this;
    }

    public Marble getPrev() {
        return prev;
    }

    public Marble setPrev(Marble prev) {
        this.prev = prev;
        return this;
    }

    public Marble remove() {
        Marble oldPrev = this.prev;
        Marble oldNext = this.next;
        oldPrev.setNext(oldNext);
        oldNext.setPrev(oldPrev);
        return next;
    }

    public Marble insertAfter(Marble other) {
        Marble oldNext = this.next;
        this.setNext(other);
        oldNext.setPrev(other);
        other.setNext(oldNext);
        other.setPrev(this);
        return other;
    }

    public Marble insertBefore(Marble other) {
        Marble oldPrev = this.prev;
        this.setPrev(other);
        oldPrev.setNext(other);
        other.setNext(this);
        other.setPrev(oldPrev);
        return other;
    }

    @Override
    public String toString() {
        long prevPoints = prev == null ? -1 : prev.getPoints();
        long nextPoints = next == null ? -1 : next.getPoints();
        return String.format("...%d-> %d <-%d...", prevPoints, this.getPoints(), nextPoints);
    }

}
