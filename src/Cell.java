import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.util.*;
import java.util.List;

/**
 * Created by Mateusz on 28.04.2017.
 */
public class Cell extends JPanel {

    private int lengthWall;
    private int row, column;
    private CellInformation[][] oneCell;
    private List<Integer> road;

    boolean done = false;

    public Cell(int _lengthWall)
    {
        lengthWall = _lengthWall;
        oneCell = new CellInformation[500/(_lengthWall)][500/(_lengthWall)];
        road =  new ArrayList<Integer>();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        try {                                                   //Try/catch block is paining net on whole surface
            row=0;
            for (int j = 0; j < 500; j += lengthWall) {
                column = 0;
                for (int i = 0; i < 500; i += lengthWall) {
                    g.setColor(Color.BLUE);
                    g.drawRect(i, j, lengthWall, lengthWall);
                    oneCell[row][column] = new CellInformation(i, j);
                    column++;
                }
                row++;
            }

            g.setColor(Color.yellow);
            g.fillRect(lengthWall/4,lengthWall/4,lengthWall/2,lengthWall/2);
            g.fillRect(((row-1)*lengthWall)+(lengthWall/4), (column-1)*lengthWall+(lengthWall/4), lengthWall/2, lengthWall/2);

            row = 0;
            column = 0;

            while(done!=true){
                delateWall(oneCell, g);
            }

        }catch(Exception e){
           System.out.println("Error in painting cells");
           e.printStackTrace();
        }

    }


    public void delateWall(CellInformation[][] table, Graphics g)
    {
        int lastCell;
        int direction = 4;
        CellInformation currentElement = table[row][column];
        currentElement.setVisit(true);
        boolean[] neighbourCells = {true, true, true, true};     //zmienna pomocnicza okreslajaca czy mozna sie przemiescic do komorki gornej, dolnej, lewej, prawej
        CellInformation topElement = null,
                        rightElement = null,
                        bottomElement = null,
                        leftElement = null;


        if(row-1>=0 && row-1<500/lengthWall){
                topElement = table[row-1][column]; //Gorna komorka
                if(topElement.isVisit() == true)
                {
                    neighbourCells[0]=false;
                }
                else neighbourCells[0]=true;
            }else  neighbourCells[0]=false;

            if(column+1>=0 && column+1<500/lengthWall){
                rightElement = table[row][column+1]; //Prawa komorka
                if(rightElement.isVisit() == true)
                {
                    neighbourCells[1]=false;
                }
                else neighbourCells[1]=true;
            }else neighbourCells[1]=false;

            if(row+1>=0 && row+1<500/lengthWall){
                bottomElement = table[row+1][column]; //Dolna komorka
                if(bottomElement.isVisit() == true)
                {
                    neighbourCells[2]=false;
                }
                else neighbourCells[2]=true;
            }else neighbourCells[2]=false;

            if(column-1>=0 && column-1<500/lengthWall){
                leftElement = table[row][column-1]; //Lewa komorka
                if(leftElement.isVisit() == true)
                {
                    neighbourCells[3]=false;
                }
                else neighbourCells[3]=true;
            }else neighbourCells[3]=false;

            Random rand = new Random();

            while(neighbourCells[0]!=false || neighbourCells[1]!=false || neighbourCells[2]!=false || neighbourCells[3]!=false)     //Losuj w pętli kierunek
            {                                                                               //0 - pojdz w gore, 1-pojdz w prawo, 2-pojdz w dol, 3 pojdz w lewo
                direction = rand.nextInt(4);                                        //Losuj liczby od 0 do 3
                if(neighbourCells[direction]==true)
                {
                    road.add(direction);                                                   //Zapisz kierunek drogi
                    break;
                }
            }

            try {
                if (neighbourCells[0]==false && neighbourCells[1]==false && neighbourCells[2]==false && neighbourCells[3]==false) //Sytuacja w ktorej trzeba sie cofnac i szukac nowej drogi
                {
                    done = isDone(table);         //Sprwadz czy wszystkie komorki zostaly odwiedzone
                    if (done == true) {
                        //direction = 5;              //Jesli tak ustaw zmienna direction na 5
                    }else{
                        lastCell = road.get(road.size()-1);     //Dostan wczesniejsza odwiedzona komorke
                        if (lastCell == 0) {                    //Jezeli jest to komorka gorna to
                            row++;
                            road.remove(road.size()-1);
                            return;
                        } else if (lastCell == 1) {             //Jezeli jest to komorka z prawej
                            column--;
                            road.remove(road.size()-1);
                            return;
                        } else if (lastCell == 2) {             //Jezeli jest to komorka z dolu
                            row--;
                            road.remove(road.size()-1);
                            return;
                        } else if (lastCell == 3) {             //Jezeli jest to komorka z lewej
                            column++;
                            road.remove(road.size()-1);
                            return;
                        }
                        else
                        {
                            direction=4;
                        }
                    }
                }
            }catch (Exception e)
            {
                System.out.println("Agh! Exception");
                e.printStackTrace();
                direction=4;
            }


            g.setColor(Color.BLACK);
            try{
                switch(direction)   {
                    case 0: g.drawLine(column*lengthWall+1, row*lengthWall, (column+1)*lengthWall-1, row*lengthWall); //TOP
                        row--;
                        break;
                    case 1: g.drawLine((column+1)*lengthWall, row*lengthWall+1, (column+1)*lengthWall, (row+1)*lengthWall-1); //RIGHT
                        column++;
                        break;
                    case 2: g.drawLine(column*lengthWall+1, (row+1)*lengthWall, (column+1)*lengthWall-1, (row+1)*lengthWall); //BOTTOM
                        row++;
                        break;
                    case 3: g.drawLine(column*lengthWall, row*lengthWall+1, column*lengthWall, (row+1)*lengthWall-1); //LEFT
                        column--;
                        break;
                    default: break;
                }
            }catch(Exception exc){
                System.out.println("Problems with delating wall!");
                exc.printStackTrace();
            }
    }



    public boolean isDone(CellInformation[][] table){

        int a=0, b=0, c=0;
        CellInformation temp = null;

        for (int j = 0; j < 500; j += lengthWall) {
            b = 0;
            for (int i = 0; i < 500; i += lengthWall){
                temp = table[a][b];
                if (temp.isVisit()==true){
                    c++;
                }
                b++;
            }
            a++;
        }

        if(c == ((500/lengthWall)*(500/lengthWall))) {
            return true;
        }else{
            return false;
        }
    }
}