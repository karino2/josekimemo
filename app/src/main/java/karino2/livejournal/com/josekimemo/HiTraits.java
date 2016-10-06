package karino2.livejournal.com.josekimemo;

import android.graphics.Bitmap;

/**
 * Created by _ on 2016/10/06.
 */
public class HiTraits implements IKomaTraits {
    Bitmap koma;
    public HiTraits(Bitmap img) { koma = img; }

    @Override
    public Bitmap getImage() {
        return koma;
    }
}
