/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

/**
 *
 * @author dtien
 */
import com.sun.source.tree.Scope;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Arrays;

public class AlgorithmUI  {
    java.util.List<Node> listXY;
    private java.util.List<Node>[] listG;
    private int cellSize = 30;
    private PriorityQueue<Node> openList;
    private Set<Node> closedList;
    private Node target;
    private Node start;
    private Node result;
    private int width=1518;
    public AlgorithmUI() {
        System.out.println("INIT");
        listXY=new ArrayList<>();
        target = new Node();
        start = new Node();
        openList = new PriorityQueue<>((o1, o2) -> o1.getF() - o2.getF());
        closedList = new HashSet<>();
   
    }
    public AlgorithmUI(int width){
        this();
        this.width=width;
    }

    public Node solver() {
        int count=0;
        listG = new ArrayList[10000];
        int x = width / 2 - 3 * cellSize / 2;
        int y = 30;
        openList.add(start);
        start.calculateF(target);
        listG[0] = new ArrayList<>();
        listG[0].add(start);
        start.setX(x);
        start.setY(y);
        start.setCount(0);
        count++;
        while (!openList.isEmpty()) {
            Node currentState = openList.poll();
            currentState.calculateF(target);
            
            if (currentState.isGoalState(target)) {
                currentState.setIsTarget(true);
                return currentState;
            } else {
                java.util.List<Node> nextStates = currentState.generateState();

                int gIndex = currentState.getG() + 1;
                if (listG[gIndex] == null) {
                    listG[gIndex] = new ArrayList<>();
                }
                for (Node next : nextStates) {
                    next.setCount(count++);
                    next.calculateF(target);
                    next.setParents(currentState);
                    listG[gIndex].add(next);
                    openList.add(next);
                    next.setY(y + cellSize * 5 * next.getG());
                }

            }
            closedList.add(currentState);

        }

        return null;
    }

    public void process() {
        if (isSolvable(start, target)) {
            this.result = solver();
            Node result1=this.result;
            int cost = result1.getG();
            java.util.List<Node> results = new ArrayList<>();
            results.add(result1);
            while (result1.getParents() != null) {
                results.add(result1.getParents());
                result1 = result1.getParents();
            }
            Collections.reverse(results);
            for (Node x : results) {
//                x.output();
                  x.setCheck(true);
            }
            System.out.println("Số lần di chuyển: " + cost);
        } else {
            System.out.println("Không thể tới đích!");
        }

    }

    public static boolean isSolvable(Node start, Node target) {
        // Biểu diễn trạng thái ban đầu và mục tiêu dưới dạng các dãy số
        int[] flatStart = new int[9];
        int[] flatTarget = new int[9];
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                flatStart[k] = start.getBoard().get(i).get(j);
                flatTarget[k] = target.getBoard().get(i).get(j);
                k++;
            }
        }

        // Tính tích hợp của số lần di chuyển cho cả trạng thái ban đầu và mục tiêu
        int inversionsInitial = countInversions(flatStart);
        int inversionsTarget = countInversions(flatTarget);

        // Kiểm tra tính chẵn/lẻ của số lần di chuyển cho cả hai trạng thái
        return (inversionsInitial % 2 == 0 && inversionsTarget % 2 == 0)
                || (inversionsInitial % 2 != 0 && inversionsTarget % 2 != 0);
    }

    public static int countInversions(int[] array) {
        int inversions = 0;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] != 0 && array[j] != 0 && array[i] > array[j]) {
                    inversions++;
                }
            }
        }
        return inversions;
    }

    public java.util.List<Node> getListXY(){
        process();
            for (int i = 0; i <= result.getG(); i++) {
                if (listG[i] != null && i != 0) {
                    java.util.List<Node> temp = new ArrayList<>(listG[i]);
                    temp.sort((o1, o2) -> o1.getParents().getX() - o2.getParents().getX());
                    int size = listG[i].size();
                    int margin = width / size - (width / size / 2 - 3 * cellSize / 2) < 95 ? 95 : width / size - (width / size / 2 - 3 * cellSize / 2);
                    int xi = margin;
                    for (Node x : temp) {
                        x.setX(xi);
                        listXY.add(x);
                        xi += margin;
                    }
                } else if (i == 0) {
                    listXY.add(start);
                }
            }
         return listXY;
    }

    public Node getTarget() {
        return target;
    }

    public void setTarget(Node target) {
        this.target = target;
    }

    public Node getStart() {
        return start;
    }

    public void setStart(Node start) {
        this.start = start;
    }
    
}
