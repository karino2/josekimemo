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
    int lineWidth = 2;
    int komaSize;


    public Board() {
        backgroundPaint.setColor(0xFFDEB037);
        linePaint.setColor(0xFF000000);
    }

    void setSize(int w, int h)
    {
        boardSize = Math.min(w, h);
        region.set(0, 0, boardSize, boardSize);
        komaSize = (boardSize - 10*lineWidth)/9;

        offY = komaSize + 40;
    }

    public int getKomaSize() {
        return komaSize;
    }

    public int getOffY() {
        return offY;
    }

    void draw(Canvas canvas)
    {
        canvas.drawRect(region, backgroundPaint);

        for(int i = 0; i < 10; i++) {
            int x = (komaSize+lineWidth)*i;
            canvas.drawLine(x, offY, x, offY+boardSize, linePaint);
        }

        for(int j = 0; j < 10; j++){
            int y = offY + (komaSize+lineWidth)*j;
            canvas.drawLine(0, y, boardSize, y, linePaint);
        }

    }

}
