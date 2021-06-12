package com.test;

import com.tools.Taint;
import com.tools.TaintLevel;

public class Base {
    int i;
    String s;

    public int methodTest(int i, int j) {
        j += 2;
        i += 1;
        return i + j;
    }
}
