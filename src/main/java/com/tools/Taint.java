package com.tools;

import java.util.ArrayList;

/*
    污点类：
    --tainLevel：污点等级；
    --varName：原变量名称；
    --propagateList：传播列表
 */

public class Taint {

    private TaintLevel taintLevel;
    private String varName;
    private ArrayList<Taint> propagateList;

    public Taint(){
        this.propagateList = new ArrayList<>();
    }

    public Taint(TaintLevel taintLevel, String varName) {
        this.propagateList = new ArrayList<>();
        this.taintLevel = taintLevel;
        this.varName = varName;
    }

    public Taint(TaintLevel taintLevel, String varName, Taint taint) {
        this.taintLevel = taintLevel;
        this.varName = varName;
        this.propagateList = new ArrayList<>();
        this.propagateList.add(taint);
    }

    public TaintLevel getTaintLevel() {
        return taintLevel;
    }

    public void setTaintLevel(TaintLevel taintLevel) {
        this.taintLevel = taintLevel;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public void addTaint(Taint t){
        this.propagateList.add(t);
    }
}
