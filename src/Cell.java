import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

/**
 * Created by Mateusz on 28.04.2017.
 */
public class Cell extends JPanel {
    private int lengthWall;
    private int a, b, k, l;                 //Kazda komorka na wygenerowanej siatce ma swoja pozycje w tablicy. Tworzona jest tablica o wymiarach [k][l]
    private CellInformations[][] table;
    private List<Integer> road;


    public Cell(int _lengthWall)
    {
        lengthWall = _lengthWall;
        a=0;
        b=0;
        table = new CellInformations[500/(_lengthWall)][500/(_lengthWall)];
        road =  new ArrayList<Integer>();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        try {                                                   //Try/catch block is paining net on whole surface
            k=0;
            for (int j = 0; j < 500; j += lengthWall) {
                l = 0;
                for (int i = 0; i < 500; i += lengthWall) {
                    g.setColor(Color.BLUE);
                    g.drawRect(i, j, lengthWall, lengthWall);
                    table[k][l] = new CellInformations(i, j);
                    l++;
                }
                k++;
            }
            delateWall(0,0,table, g);                    //Inside delateWall is algorithm which create maze
        }catch(Exception e){
           System.out.println("Error in painting cells");
           e.printStackTrace();
        }

    }


    public void delateWall(int k, int l, CellInformations[][] table, Graphics g)
    {
        int lastCell = 0;
        int direction = 4;
        boolean[] temp = {true, true, true, true};
        boolean isDone = true;                  //Variable
        CellInformations topElement = null, rightElement = null, bottomElement = null, leftElement = null;

        CellInformations currentElement = table[k][l];
        currentElement.setVisited(true);

        if((l-1>=0) && (l-1<(500/lengthWall)) && (k>=0) && (k<(500/lengthWall))){
            topElement = table[l-1][k]; //Gorna komorka
            if(topElement.isVisited() == true)
            {
                temp[0]=false;
            }
            else temp[0]=true;
        }else  temp[0]=false;
        if((l>=0) && l<(500/lengthWall) && k+1>=0 && k+1<500/lengthWall){
            rightElement = table[l][k+1]; //Prawa komorka
            if(rightElement.isVisited() == true)
            {
                temp[1]=false;
            }
            else temp[1]=true;
        }else temp[1]=false;
        if(l+1>=0 && l+1<(500/lengthWall) && k>=0 && k<(500/lengthWall)){
            bottomElement = table[l+1][k]; //Dolna komorka
            if(bottomElement.isVisited() == true)
            {
                temp[2]=false;
            }
            else temp[2]=true;
        }else temp[2]=false;
        if(k-1>=0 && k-1<(500/lengthWall) && l>=0 && l<(500/lengthWall)){
            leftElement = table[l][k-1]; //Lewa komorka
            if(leftElement.isVisited() == true)
            {
                temp[3]=false;
            }
            else temp[3]=true;
        }else temp[3]=false;

        Random rand = new Random();

        while(temp[0]!=false || temp[1]!=false || temp[2]!=false || temp[3]!=false)
        {
            direction = rand.nextInt(4); // 0 - pojdz w gore, 1-pojdz w prawo, 2-pojdz w dol, 3 pojdz w lewo
            if(temp[direction]==true)
            {
                road.add(direction);
                break;
            }
        }

        try {
            if (temp[0] == false && temp[1] == false && temp[2] == false && temp[3] == false)
            {
                isDone = isDone(table);
                if (isDone == true) {
                    direction = 5;
                } else {
                    lastCell = road.get(road.size()-1);
                    if (lastCell == 0) {
                        l++;
                        road.remove(road.size()-1);
                        delateWall(k, l, table, g);
                    } else if (lastCell == 1) {
                        k--;
                        road.remove(road.size()-1);
                        delateWall(k, l, table, g);
                    } else if (lastCell == 2) {
                        l--;
                        road.remove(road.size()-1);
                        delateWall(k, l, table, g);
                    } else if (lastCell == 3) {
                        k++;
                        road.remove(road.size()-1);
                        delateWall(k, l, table, g);
                    }
                    else
                    {
                        direction=4;
                    }
                }
            }
        }catch (Exception e)
        {
            direction=4;
        }


        g.setColor(Color.BLACK);
        switch(direction)   {
            case 0: g.drawLine(k*lengthWall, l*lengthWall, (k+1)*lengthWall, l*lengthWall); //TOP
                    l--;
                    delateWall(k, l, table, g);
                    break;
            case 1: g.drawLine((k+1)*lengthWall, l*lengthWall, (k+1)*lengthWall, (l+1)*lengthWall); //RIGHT
                    k++;
                    delateWall(k, l, table, g);
                    break;
            case 2: g.drawLine(k*lengthWall, (l+1)*lengthWall, (k+1)*lengthWall, (l+1)*lengthWall); //BOTTOM
                    l++;
                    delateWall(k, l, table, g);
                    break;
            case 3: g.drawLine(k*lengthWall, l*lengthWall, k*lengthWall, (l+1)*lengthWall); //LEFT
                    k--;
                    delateWall(k, l, table, g);
                    break;
            default: break;
        }
    }

    public boolean isDone(CellInformations[][] table){
        int a=0, b=0, c=0;
        CellInformations temp = null;
        for (int j = 0; j < 500; j += lengthWall) {
            b = 0;
            for (int i = 0; i < 500; i += lengthWall) {
                temp = table[a][b];
                if (temp.isVisited()==true)
                {
                    c++;
                }
                    b++;
                }
                a++;
            }
        if(c == ((500/lengthWall)*(500/lengthWall)))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void saveImage(String name,String type) {
        BufferedImage image = new BufferedImage(502,502, BufferedImage.TYPE_INT_RGB);
        Graphics g2 = image.createGraphics();
        this.paint(g2);
        g2.dispose();
        try{
            ImageIO.write(image, type, new File(name+"."+type));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}