package com.example.dy.mytime.CompletenessPackage;

public interface ICompleteness {
    //更新本周完成度
    public void updateCompleteness();
    //获取本周完成度
    public int getWeekCompleteness();
    //获取历史完成度
    public int[] getHistoryCompleteness();
    //每周更新历史完成度
    public void updateHistory();

}
