package com.tools;

/*
    污点等级：
    --高危:source及直接数据流传播的变量；
    --危险：高危数据以参数传入非sink方法的返回值；
    --临界：以高危数据为控制条件的内部参数；
    --可忽略：与污点传播无关的变量；
 */
public enum TaintLevel {
    HIGHDANGER,
    DANGER,
    CRITICALITY,
    NEGLIGIBLE
}
