/**
 * Created by Mateusz on 01.05.2017.
 */
public class CellInformations {
    private boolean visited;
    private int leftWall, rightWall, bottomWall, upWall;
    private int position_X;
    private int position_Y;


    CellInformations(int i, int j){
        visited = false;
        position_X = i;
        position_Y = j;
    }

    public void setVisited(boolean _visited)
    {
        visited = _visited;
    }

    public boolean isVisited()
    {
        return visited;
    }
}
