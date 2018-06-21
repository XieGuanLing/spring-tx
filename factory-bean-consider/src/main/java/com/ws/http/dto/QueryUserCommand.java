package com.ws.http.dto;

/**
 * create by gl
 * on 2018/5/2
 */
public class QueryUserCommand {

    private int start;

    private int max;

    public QueryUserCommand(int start, int max) {
        this.start = start;
        this.max = max;
    }

    public QueryUserCommand() {

    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }


    @Override
    public String toString() {
        return "QueryUserCommand{" +
                "start=" + start +
                ", max=" + max +
                '}';
    }
}
