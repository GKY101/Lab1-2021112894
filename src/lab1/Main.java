package lab1;
import javax.swing.*;
import java.awt.*;
/*
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
*/


public class Main {


    public static void main(String[] args) {
        //1.创建Frame对象
        JFrame frame = new JFrame();
        frame.setTitle("11");
        frame.setSize(1000,700);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());//设置边框布局

        //Date date=new Date();
        Lab1Solution Solusion=new Lab1Solution("input.txt");

        JPanel panel0 = new JPanel();

        //JButton button00=new JButton("点击此处");
        JLabel label00=new JLabel("未构建图");
        JButton button01=new JButton("清空文本");
        //panel0.add(button00);
        panel0.add(label00);
        panel0.add(button01);
        frame.getContentPane().add(panel0,BorderLayout.NORTH);

        JPanel panel1 = new JPanel(new GridLayout(5, 2,2,1));
        JPanel panel10=new JPanel();
        //panel1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        //panel1.setSize(100,100);
        //panel1.setBounds(100,100,100,200);
        JButton button10=new JButton("选择文件");
        panel10.add(button10);
        JLabel label10=new JLabel(Solusion.filename);
        panel10.add(label10);
        JButton button11=new JButton("构建图");
        panel10.add(button11);
        JButton button12=new JButton("打印图");
        panel10.add(button12);
        JPanel panel11=new JPanel();
        //panel11.setSize(200,100);
        JTextArea textArea10 = new JTextArea("word1");
        JTextArea textArea11 = new JTextArea("word2");
        JTextArea textArea12 = new JTextArea("inputText");

        textArea10.setPreferredSize(new Dimension(100,50));
        textArea11.setPreferredSize(new Dimension(100,50));
        textArea12.setPreferredSize(new Dimension(200,50));

        panel11.add(textArea10);
        panel11.add(textArea11);
        //panel11.add(textArea12);
        //panel1.setLayout(new FlowLayout(FlowLayout.LEADING,20,20));

        JPanel panel12=new JPanel();
        JButton button13=new JButton("查询桥接词");
        JButton button14=new JButton("生成新文本");
        JButton button15=new JButton("计算最短路径");
        JButton button16=new JButton("随机游走");
        JButton button17=new JButton("计算单源最短路径");
        panel12.add(button13);
        //panel12.add(button14);
        panel12.add(button15);
        panel12.add(button17);

        JPanel panel13=new JPanel();
        panel13.add(textArea12);

        JPanel panel14=new JPanel();
        panel14.add(button14);
        panel14.add(button16);
        panel1.add(panel10);
        panel1.add(panel11);
        panel1.add(panel12);
        panel1.add(panel13);
        panel1.add(panel14);
        frame.getContentPane().add(panel1,BorderLayout.WEST);

        JPanel panel2 = new JPanel();
        //panel1.add(new JButton("2"));
        //JLabel label20=new JLabel("空");
        //panel1.add(label20);
        JTextArea textArea20 = new JTextArea();
        JScrollPane scrollPane20 = new JScrollPane(textArea20);
        //panel1.add(scrollPane20);
        //panel1.getContentPane().add(scrollPane20);
        frame.getContentPane().add(scrollPane20,BorderLayout.CENTER);


        button01.addActionListener(e->{
            //date.label00=0;
            //label00.setText("未点击");
            textArea20.setText("");

        });

        button10.addActionListener(e->{
            JFileChooser filechooser=new JFileChooser();
            int result=filechooser.showOpenDialog(null);
            if(result==JFileChooser.APPROVE_OPTION) {
                Solusion.filename=filechooser.getSelectedFile().getAbsolutePath();
                label10.setText(Solusion.filename);
            }

        });
        button11.addActionListener(e->{
            int flag=Solusion.buildGraph();
            if(flag==1) {
                label00.setText("构建图成功");}
            else {
                label00.setText("读取文件失败，请重新选择文件");
            }
        });
        button12.addActionListener(e->{
            if(Solusion.have_build_map==1) {
                String result= Solusion.showDirectedGraph();
                label00.setText("打印图成功");
                int rs=Solusion.draw_graph();
                System.out.println(rs);
                textArea20.setText(result);}
            else {
                label00.setText("未构建图，请先构建！");
            }
        });
        button13.addActionListener(e->{
            if(Solusion.have_build_map==1) {
                String word1,word2;
                word1=textArea10.getText();
                word2=textArea11.getText();
                String result= Solusion.queryBridgeWords(word1,word2);
                label00.setText("查询桥接词成功");
                System.out.println(result);
                textArea20.setText(result);}
            else {
                label00.setText("未构建图，请先构建！");
            }
        });
        button14.addActionListener(e->{
            if(Solusion.have_build_map==1) {
                String inputText;
                inputText=textArea12.getText();
                String result= Solusion.generateNewText(inputText);
                label00.setText("生成新文本成功");
                System.out.println(result);
                textArea20.setText(result);}
            else {
                label00.setText("未构建图，请先构建！");
            }
        });
        button15.addActionListener(e->{
            if(Solusion.have_build_map==1) {
                String word1,word2;
                word1=textArea10.getText();
                word2=textArea11.getText();
                String result= Solusion.calcShortestPath(word1,word2);
                label00.setText("计算最短路径成功");
                System.out.println(result);
                textArea20.setText(result);}
            else {
                label00.setText("未构建图，请先构建！");
            }
        });
        button17.addActionListener(e->{
            if(Solusion.have_build_map==1) {
                String word1;
                word1=textArea10.getText();
                String result= Solusion.calcShortestPath(word1);
                label00.setText("计算最短路径成功");
                System.out.println(result);
                textArea20.setText(result);}
            else {
                label00.setText("未构建图，请先构建！");
            }
        });
        button16.addActionListener(e->{
            if(Solusion.have_build_map==1) {
                String result= Solusion.randomWalk();
                label00.setText("随机游走成功");
                System.out.println(result);
                textArea20.setText(result);}
            else {
                label00.setText("未构建图，请先构建！");
            }
        });
        frame.setVisible(true);
    }

}
