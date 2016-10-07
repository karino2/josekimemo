package karino2.livejournal.com.josekimemo;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;


/**
 * Created by _ on 2016/10/06.
 */
public class Board {
    int boardSize = 1;
    Paint backgroundPaint = new Paint();
    Paint linePaint = new Paint();
    Rect region = new Rect();

    int offY = 80;
    int offX = 0;
    int lineWidth = 2;
    int komaSize;


    public Board() {
        backgroundPaint.setColor(0xFFDEB037);
        linePaint.setColor(0xFF000000);
    }

    void setSize(int w, int h)
    {
        int minSide = Math.min(w, h);
        boardSize = (minSide*9)/10;
        offX = (minSide - boardSize)/2;

        komaSize = (boardSize - 10*lineWidth)/9;

        offY = komaSize + 40;
        region.set(offX, offX, boardSize, boardSize);
    }

    public int getKomaSize() {
        return komaSize;
    }

    public int getOffY() {
        return offY;
    }

    public int getOffX() {
        return offX;
    }

    public int xToSuji(float x) {
        float relative = x - offX;
        if(relative < 0) {
            return -1;
        }
        int col = (int)(relative / ((float)(komaSize+lineWidth)));
        if(col > 8)
            return -1;
        return 9-col;
    }

    public int yToDan(float y) {
        float relative = y - offY;
        if(relative < 0)
            return -1;
        int row = (int)(relative / ((float)(komaSize+lineWidth)));
        if (row > 8)
            return -1;
        return row+1;
    }



    void draw(Canvas canvas)
    {
        canvas.drawRect(region, backgroundPaint);

        for(int i = 0; i < 10; i++) {
            int x = (komaSize+lineWidth)*i;
            canvas.drawLine(offX+x, offY, offX+x, offY+boardSize, linePaint);
        }

        for(int j = 0; j < 10; j++){
            int y = offY + (komaSize+lineWidth)*j;
            canvas.drawLine(offX, y, offX+boardSize, y, linePaint);
        }

    }

}
