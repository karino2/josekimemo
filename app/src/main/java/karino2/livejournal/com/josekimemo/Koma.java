package karino2.livejournal.com.josekimemo;

import android.graphics.Bitmap;
import android.graphics.Canvas;


/**
 * Created by _ on 2016/10/06.
 */
public class Koma {

    int komaSize;
    boolean sente = true;
    boolean nari = false;

    IKomaTraits traits;
    public void setKomaTraits(IKomaTraits tr) {
        traits = tr;
    }

    Bitmap senteImg;
    Bitmap senteNariImg;
    Bitmap goteImg;
    Bitmap goteNariImg;

    int id;

    public Koma(int id_, Bitmap sente, Bitmap nari, Bitmap gote, Bitmap goteNari) {
        id = id_;
        senteImg = sente;
        senteNariImg = nari;
        goteImg = gote;
        goteNariImg = goteNari;
    }

    public int getId() { return id; }

    public boolean isSente() { return sente; }

    int row;
    int col;


    public void draw(Canvas canvas) {
        int y = offY+row*(komaSize+lineWidth);
        int x = offX+col*(komaSize+lineWidth);
        canvas.drawBitmap(currentImage(), (float)x, (float)y, null);
    }

    boolean isNari() { return nari; }

    Bitmap currentImage() {
        if(isSente()) {
            if (isNari())
                return senteNariImg;
            return senteImg;
        }
        if(isNari())
            return goteNariImg;
        return goteImg;
    }

    public Koma sente() {
        sente = true;
        return this;
    }

    public Koma gote() {
        sente = false;
        return this;
    }

    int offX;
    int offY;

    public Koma offset(int offX, int offY) {
        this.offX = offX;
        this.offY = offY;

        return this;
    }
    public boolean isMochigoma() {
        if(getDan() == 0 || getDan() == 10)
            return true;
        return false;
    }


    public int getSuji() {
        return 9-col;
    }
    public int getDan() {
        return row+1;
    }

    public boolean canMove(int suji, int dan) {
        return true;
    }

    // 1 origin.
    public Koma pos(int suji, int dan) {

        this.row = dan-1;
        this.col = 9-suji;

        return this;
    }

    int lineWidth = 2;

    public void setKomaSize(int size) {
        komaSize = size;
    }
}
