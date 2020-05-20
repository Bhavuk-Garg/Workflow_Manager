package com.xor.taskExecutor.config.beanConfig;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    Map<Integer, List<Pair<Integer,String >>> adjList;


    public Graph(){
        adjList=new HashMap<>();
    }

    void addEdge(int from,int to,String output)
    {
        adjList.putIfAbsent(from,new ArrayList<>());
        adjList.get(from).add(new MutablePair<>(to,output));
    }
    public List<Pair<Integer,String>> getEdges(int id) {
        return adjList.get(id);
    }
}

