package com.mastercard.citynode.service;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
 
public class Path_Between_Nodes
{
    private Map<String, LinkedHashSet<String>> map = new HashMap();
    private static String  START;
    private static String  END;
    private static boolean flag;
    
    public void initIndexes(String node1, String node2) {
    	START = node1;
    	END = node2	;
    }
    
    public void addEdge(String node1, String node2)
    {
        LinkedHashSet<String> adjacent = map.get(node1);
        if (adjacent == null)
        {
            adjacent = new LinkedHashSet();
            map.put(node1, adjacent);
        }
        adjacent.add(node2);
    }
 
    public void addTwoWayVertex(String node1, String node2)
    {
        addEdge(node1, node2);
        addEdge(node2, node1);
    }
 
    public boolean isConnected(String node1, String node2)
    {
        Set adjacent = map.get(node1);
        if (adjacent == null)
        {
            return false;
        }
        return adjacent.contains(node2);
    }
 
    public LinkedList<String> adjacentNodes(String last)
    {
        LinkedHashSet<String> adjacent = map.get(last);
        if (adjacent == null)
        {
            return new LinkedList();
        }
        return new LinkedList<String>(adjacent);
    }
 
    
    public void breadthFirst(Path_Between_Nodes graph,LinkedList<String> visited)
    {
        LinkedList<String> nodes = graph.adjacentNodes(visited.getLast());
 
        for (String node : nodes)
        {
            if (visited.contains(node))
            {
                continue;
            }
            if (node.equals(END))
            {
                visited.add(node);
                flag = true;
                visited.removeLast();
                break;
            }
        }
 
        for (String node : nodes)
        {
            if (visited.contains(node) || node.equals(END))
            {
                continue;
            }
            visited.addLast(node);
            breadthFirst(graph, visited);
            visited.removeLast();
        }
        if (flag == false)
        {
            System.out.println("No path Exists between " + START + " and "
                    + END);
            flag = true;
        }
 
    }
 
}
