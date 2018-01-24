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
    private int size;

    public Window() {
        //Konfiguracja okna
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Generator labiryntów");
        setBounds(0, 0, 720, 540);
        setLayout(null);
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
        if (zrodlo == btnSavePNG) {
            try {
                cellue.saveImage("maze","png");
            }catch (Exception e2)
            {
                System.out.println("Błąd");
            }
        } else if (zrodlo == btnGenerate) {
            try {
                setMazeSize(size);
            }catch(Exception e1) {
                size=50;
                setMazeSize(size);
            }
        } else if (zrodlo == cbLevel) {
            String poziom = cbLevel.getSelectedItem().toString();
            if (poziom.equals("Poziom 1")) {
                size = 20;
            } else if (poziom.equals("Poziom 2")) {
                size = 25;
            } else if (poziom.equals("Poziom 3")) {
                size = 50;
            } else if (poziom.equals("Poziom 4")) {
                size = 100;
            } else if (poziom.equals("Poziom 5")) {
                size = 125;
            }
        }
    }

    public void setMazeSize(int n)
    {
        cellue = new Cell(n);
        cellue.setBounds(200,0,502,502);
        cellue.setBackground(Color.BLACK);
        this.add(cellue);
        cellue.repaint();
    }
}