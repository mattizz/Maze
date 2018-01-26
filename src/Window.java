/**
 * Created by Mateusz on 28.03.2017.
 */
import javafx.scene.control.ComboBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.SocketPermission;

public class Window extends JFrame implements ActionListener {
    private JButton btnSavePNG;
    private JButton btnGenerate;
    private JComboBox cbLevel;
    private Cell cellue;
    private int size=50;

    public Window() {
        //Konfiguracja okna
        JFrame window = new JFrame();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Generator labiryntów");
        setBounds(0, 0, 720, 540);
        setLayout(null);
        setResizable(false);
        setVisible(true);

        //Tworzenie przycisku Zapisz jako PNG
        btnSavePNG = new JButton("Zapisz jako PNG");
        btnSavePNG.setBounds(0, 0, 200, 40);
        this.add(btnSavePNG);
        btnSavePNG.addActionListener(this);

        //Tworzenie przycisku generowania labiryntu
        btnGenerate = new JButton("Generuj labirynt");
        btnGenerate.setBounds(0, 40, 200, 40);
        this.add(btnGenerate);
        btnGenerate.addActionListener(this);

        //Tworzenie etykiety z nazwą poziomu
        JLabel tekst = new JLabel("Wybierz poziom", SwingConstants.CENTER);
        tekst.setForeground(Color.BLACK);
        tekst.setBounds(0, 80, 200, 40);
        this.add(tekst);

        //Tworzenie ComboBox'a do wyboru poziomu
        cbLevel = new JComboBox();
        cbLevel.setBounds(0, 120, 200, 40);
        cbLevel.addItem("Poziom 1");
        cbLevel.addItem("Poziom 2");
        cbLevel.addItem("Poziom 3");
        cbLevel.addItem("Poziom 4");
        cbLevel.addItem("Poziom 5");
        cbLevel.addItem("Poziom 6");
        this.add(cbLevel);
        cbLevel.addActionListener(this);

        //Tworzenie nowego panelu na którym będzie rysowany labirynt
        cellue = new Cell(50);
        cellue.setBounds(200,0,502,502);
        cellue.setBackground(Color.BLACK);
        this.add(cellue);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object zrodlo = e.getSource();
        try{
            if (zrodlo == btnSavePNG) {
                cellue.saveImage("maze","png");
            } else if (zrodlo == btnGenerate){
                generateCellsMaze();
            } else if (zrodlo == cbLevel) {
                String poziom = cbLevel.getSelectedItem().toString();
                if (poziom.equals("Poziom 1")) {
                    size = 5;
                } else if (poziom.equals("Poziom 2")) {
                    size = 20;
                } else if (poziom.equals("Poziom 3")) {
                    size = 25;
                } else if (poziom.equals("Poziom 4")) {
                    size = 50;
                } else if (poziom.equals("Poziom 5")) {
                    size = 100;
                } else if (poziom.equals("Poziom 6")) {
                    size = 125;
                }
            }
        }catch(Exception ex){
           System.out.print("Agh! Error. Sorry but I have some problem with maze :(");
           ex.printStackTrace();
        }
    }

    public void generateCellsMaze() {
        try {
            cellue = new Cell(size);
            cellue.setBounds(200, 0, 502, 502);
            cellue.setBackground(Color.BLACK);
            this.add(cellue);
            cellue.repaint();
        } catch (Exception ex) {
            System.out.println("Agh! Error in creating cells!");
            ex.printStackTrace();
        }
    }
}