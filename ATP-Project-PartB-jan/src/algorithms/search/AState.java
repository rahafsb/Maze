package algorithms.search;

import java.io.Serializable;

public abstract class AState implements Serializable {
    private String astate;
    private double cost;
    private AState cameFrom;
    private boolean visit;

    public AState() {
        this.astate=null;
        this.cost =0;
        this.cameFrom =null;
        this.visit =false;
    }

    public String getAstate() {
        return astate;
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
        return cost;
    }

    public AState getCameFrom() {
        return cameFrom;
    }

    public boolean isVisit() {
        return visit;
    }
    public String toString(){
        return  this.astate;

    }

    @Override
    public boolean equals(Object o ){
        if(this == o){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }
       AState st1 = (AState) o;
        return  astate != null? astate.equals(st1.astate) : st1.astate ==null;
    }
    // from the lecture power point
    @Override
    public int hashCode(){
        return astate != null ? astate.hashCode() : 0;
    }

}
