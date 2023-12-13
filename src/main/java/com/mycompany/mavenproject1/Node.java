/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

/**
 *
 * @author dtien
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Node {

    private List<List<Integer>> board = new ArrayList<>();
    private int g = 0;
    private int f;
    private Node parents;
    private int x;
    private int y;
    private boolean check = false;
    private int count;
    private boolean isTarget=false;
    public Node() {

    }

    public List<List<Integer>> getBoard() {
        return board;
    }

    public void setBoard(List<List<Integer>> board) {
        this.board = board;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public Node getParents() {
        return parents;
    }

    public void setParents(Node parents) {
        this.parents = parents;
    }

    //ham nhap state
    public void input() {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 3; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                row.add(sc.nextInt());
            }
            board.add(row);
        }
    }

    //ham xuat state
    public void output() {
        for (List<Integer> x : board) {
            for (int item : x) {
                System.out.print(item + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    //ham kiem tra xem da den trang thai dich hay chua
    public boolean isGoalState(Node target) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.get(i).get(j) != target.board.get(i).get(j)) {
                    return false;
                }
            }
        }
        return true;
    }

    //ham sinh ra cac trang thai moi
    public List<Node> generateState() {
        List<Node> states = new ArrayList<>();
        int[] dx = {1, 0, -1, 0};
        int[] dy = {0, 1, 0, -1};
        int x = 0, y = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.get(i).get(j) == 0) {
                    x = i;
                    y = j;
                    break;
                }
            }
        }
        for (int d = 0; d < 4; ++d) {
            int nx = x + dx[d];
            int ny = y + dy[d];

            if (nx >= 0 && nx < 3 && ny >= 0 && ny < 3) {
                Node newState = new Node();
                newState.setParents(this);
                newState.setG(this.g + 1);
                List<List<Integer>> t = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    List<Integer> row = new ArrayList<>();
                    for (int j = 0; j < 3; j++) {
                        row.add(this.board.get(i).get(j));
                    }
                    t.add(row);
                }
                //swap
                int e1 = t.get(x).get(y);
                int e2 = t.get(nx).get(ny);
                t.get(x).set(y, e2);
                t.get(nx).set(ny, e1);
                newState.setBoard(t);
                if (parents == null || !newState.isGoalState(parents)) {
                    states.add(newState);
                }

            }
        }
        return states;
    }

    // Tính toán hàm f(n)
    public void calculateF(Node target) {
        int h = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.get(i).get(j) != target.getBoard().get(i).get(j)) {
                    h++;
                }
            }
        }
        this.f = h + g;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Node node = (Node) o;
        return g == node.g && Objects.equals(board, node.board) && Objects.equals(parents, node.parents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, g, parents);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isIsTarget() {
        return isTarget;
    }

    public void setIsTarget(boolean isTarget) {
        this.isTarget = isTarget;
    }
    
}
