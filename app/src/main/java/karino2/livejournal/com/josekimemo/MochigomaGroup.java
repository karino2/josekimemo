package karino2.livejournal.com.josekimemo;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by _ on 2016/10/15.
 */

public class MochigomaGroup {
    ArrayList<Koma> komas = new ArrayList<>();
    int offX = 0; int offY = 0;

    public void setOffset(int x, int y) {
        offX = x;
        offY = y;
    }

    public boolean isEmpty() {
        return komas.size() == 0;
    }

    public int getId() {
        if(isEmpty())
            return -1;
        return komas.get(0).getId();
    }

    public Bitmap getImg() {
        if(isEmpty())
            throw new RuntimeException("get image for empty mochigoma group.");
        return komas.get(0).currentImage();
    }

    public void add(Koma koma) {
        komas.add(koma);
    }

    void draw(Canvas canvas) {
        Bitmap img = komas.get(0).currentImage();
        canvas.drawBitmap(img, (float)offX, (float)offY, null);

        // should draw number here.
    }


}
