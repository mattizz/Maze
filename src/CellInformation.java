/**
 * Created by Mateusz on 01.05.2017.
 */
public class CellInformation {
    private boolean visit;
    private int leftWall, rightWall, bottomWall, upWall;
    private int position_X;
    private int position_Y;


    CellInformation(int i, int j){
        visit = false;
        position_X = i;
        position_Y = j;
    }

    public void setVisit(boolean _visit)
    {
        visit = _visit;
    }

    public boolean isVisit()
    {
        return visit;
    }
}
