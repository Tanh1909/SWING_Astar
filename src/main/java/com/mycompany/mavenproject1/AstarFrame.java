/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

/**
 *
 * @author dtien
 */
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AstarFrame extends JFrame {

    static int index = 0;
    private DrawPanel dp;
    private int time = 1000;
    private int width = 1518;
    private Node start;
    private Node target;

    public AstarFrame() {
        setTitle("Astar");
        setSize(400, 400);
        setPreferredSize(new Dimension(512, 512));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.start = new Node();
        this.target = new Node();
        this.dp = new DrawPanel();
        JScrollPane js = new JScrollPane(dp);
        add(js);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
 
    }

    public AstarFrame(int time) {
        this();
        this.time = time;
    }

    public AstarFrame(int time, int width) {
        this();
        this.time = time;
        this.width = width;
    }

    public AstarFrame(Node start, Node target) {
        this();
        this.start = start;
        this.target = target;
    }

    public void init() {
        // Tạo và bắt đầu một luồng để vẽ liên tục
        AlgorithmUI al = new AlgorithmUI(width);
        al.setStart(start);
        al.setTarget(target);
        List<Node> listXy = al.getListXY();
        Node[] arr = new Node[listXy.size()];
        for (Node x : listXy) {
            arr[x.getCount()] = x;
        }
        System.out.println("Chi phí đến đích: "+(listXy.size()-1));
        Thread drawingThread = new Thread(() -> {
            while (index++ < listXy.size() - 1) {
                dp.drawTree(index, arr);

                try {
                    Thread.sleep(time); // Tạm dừng 100 milliseconds trước khi vẽ lại
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        drawingThread.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AstarFrame example = new AstarFrame();
            example.setVisible(true);
        });
    }
}
