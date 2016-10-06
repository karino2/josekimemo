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

    int row;
    int col;


    public void draw(Canvas canvas) {
        int y = offY+row*(komaSize+lineWidth);
        int x = offX+col*(komaSize+lineWidth);
        canvas.drawBitmap(traits.getImage(), (float)x, (float)y, null);
    }

    public Koma sente() {
        isSente = true;
        return this;
    }

    public Koma gote() {
        isSente = false;
        return this;
    }

    int offX;
    int offY;

    public Koma offset(int offX, int offY) {
        this.offX = offX;
        this.offY = offY;

        return this;
    }

    // 1 origin.
    public Koma pos(int suji, int kurai) {

        this.row = kurai-1;
        this.col = 9-suji;

        return this;
    }

    int lineWidth = 2;

    public void setKomaSize(int size) {
        komaSize = size;
    }
}
