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

    int offY = 20;
    int offX;
    int lineWidth = 2;
    int komaSize;
    int goteMochigomaTop;
    int boardTop;
    int senteMochigomaTop;


    // mochigoma board sep width
    int mbSepWidth = 5;


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

        goteMochigomaTop = offY;
        boardTop = goteMochigomaTop+komaSize + mbSepWidth;
        senteMochigomaTop = boardTop+boardSize+mbSepWidth;

        region.set(offX, offX, boardSize+offX, senteMochigomaTop+komaSize+offY);
    }

    public int getKomaSize() {
        return komaSize;
    }

    public int getBoardTop() {
        return boardTop;
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

    public static final int DAN_GOTE_MOCHIGOMA = 0;
    public static final int DAN_SENTE_MOCHIGOMA = 10;

    public int yToDan(float y) {
        if(y < goteMochigomaTop)
            return -1;

        float relative = y - goteMochigomaTop;
        if(relative < komaSize)
            return DAN_GOTE_MOCHIGOMA;

        if(y < boardTop)
            return -1; // inside mbSep

        if( y > senteMochigomaTop) {
            if(y < senteMochigomaTop+komaSize)
                return DAN_SENTE_MOCHIGOMA;
            else
                return -1;
        }

        relative = y - boardTop;
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
            canvas.drawLine(offX+x, boardTop, offX+x, boardTop+boardSize, linePaint);
        }

        for(int j = 0; j < 10; j++){
            int y = boardTop + (komaSize+lineWidth)*j;
            canvas.drawLine(offX, y, offX+boardSize, y, linePaint);
        }

    }

}
