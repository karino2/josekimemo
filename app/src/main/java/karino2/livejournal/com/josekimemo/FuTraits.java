package karino2.livejournal.com.josekimemo;

import android.graphics.Bitmap;

/**
 * Created by _ on 2016/10/06.
 */
public class FuTraits implements IKomaTraits {
    Bitmap koma;
    public FuTraits(Bitmap img) {
        koma = img;
    }

    @Override
    public Bitmap getImage() {
        return koma;
    }
}
