package karino2.livejournal.com.josekimemo;

import android.graphics.Canvas;


/**
 * Created by _ on 2016/10/06.
 */
public class Koma {

    int komaSize;
    boolean isSente = true;

    IKomaTraits traits;
    public void setKomaTraits(IKomaTraits tr) {
        traits = tr;
    }

    // row, col is 1 origin!
    int row;
    int col;


    public void draw(Canvas canvas) {
        int y = offY+(row-1)*(komaSize+lineWidth);
        int x = offX+(col-1)*(komaSize+lineWidth);
        canvas.drawBitmap(traits.getImage(), (float)x, (float)y, null);
    }

    public void sente() {
        isSente = true;
    }

    public void gote() {
        isSente = false;
    }

    int offX;
    int offY;

    // 1 origin.
    public void pos(int offX, int offY, int row, int col) {
        this.offX = offX;
        this.offY = offY;

        this.row = row;
        this.col = col;
    }

    int lineWidth = 2;

    public void setKomaSize(int size) {
        komaSize = size;
    }
}
