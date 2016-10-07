package karino2.livejournal.com.josekimemo;

import android.graphics.Bitmap;
import android.graphics.Canvas;


/**
 * Created by _ on 2016/10/06.
 */
public class Koma {

    int komaSize;
    boolean sente = true;

    IKomaTraits traits;
    public void setKomaTraits(IKomaTraits tr) {
        traits = tr;
    }

    Bitmap komaImg;
    Bitmap nariKomaImg;

    public boolean isSente() { return sente; }

    public void setKomaImg(Bitmap koma, Bitmap nariKoma) {
        komaImg = koma;
        nariKomaImg = nariKoma;
    }

    int row;
    int col;


    public void draw(Canvas canvas) {
        int y = offY+row*(komaSize+lineWidth);
        int x = offX+col*(komaSize+lineWidth);
        canvas.drawBitmap(komaImg, (float)x, (float)y, null);
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
