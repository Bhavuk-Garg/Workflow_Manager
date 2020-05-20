package com.xor.taskExecutor.config.beanConfig;

public class Edge {
    int to;
    String output;

    public Edge(int to, String output) {
        this.to = to;
        this.output = output;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
