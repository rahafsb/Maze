package algorithms.search;

public abstract class AState {
    private String astate = null;
    private double cost = 0.0;
    private AState cameFrom = null;
    private boolean visit = false;

    public AState() {
    }

    public String getAstate() {
        return this.astate;
    }

    public void setAstate(String astate) {
        this.astate = astate;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setCameFrom(AState cameFrom) {
        this.cameFrom = cameFrom;
    }

    public void setVisit(boolean visit) {
        this.visit = visit;
    }

    public double getCost() {
        return this.cost;
    }

    public AState getCameFrom() {
        return this.cameFrom;
    }

    public boolean isVisit() {
        return this.visit;
    }

    public String toString() {
        return this.astate;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            AState st1 = (AState)o;
            return this.astate != null ? this.astate.equals(st1.astate) : st1.astate == null;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.astate != null ? this.astate.hashCode() : 0;
    }
}