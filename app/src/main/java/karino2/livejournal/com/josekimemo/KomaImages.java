package karino2.livejournal.com.josekimemo;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;


/**
 * Created by _ on 2016/10/06.
 */
public class KomaImages {
    ArrayList<Bitmap> senteKomas = new ArrayList<>();
    ArrayList<Bitmap> goteKomas =  new ArrayList<>();

    public static final int IDX_FU = 0;
    public static final int IDX_HI = 1;
    public static final int IDX_KAKU = 2;
    public static final int IDX_KYO = 3;
    public static final int IDX_KEI = 4;
    public static final int IDX_GIN = 5;
    public static final int IDX_KIN = 6;
    public static final int IDX_GYOKU = 7;
    public static final int IDX_TO = 8;
    public static final int IDX_RYU = 9;
    public static final int IDX_UMA = 10;
    public static final int IDX_NARIKYO = 11;
    public static final int IDX_NARIKEI = 12;
    public static final int IDX_NARIGIN = 13;


    public Bitmap getSenteImage(int komaIdx) {
        return senteKomas.get(komaIdx);
    }

    public void loadKomas(int komaSize, Resources resources) {

        loadSenteKoma(komaSize, resources, R.drawable.fu);
        loadSenteKoma(komaSize, resources, R.drawable.hi);
        loadSenteKoma(komaSize, resources, R.drawable.kaku);
        loadSenteKoma(komaSize, resources, R.drawable.kyo);
        loadSenteKoma(komaSize, resources, R.drawable.kei);
        loadSenteKoma(komaSize, resources, R.drawable.gin);
        loadSenteKoma(komaSize, resources, R.drawable.kin);
        loadSenteKoma(komaSize, resources, R.drawable.gyoku);
        loadSenteKoma(komaSize, resources, R.drawable.to);
        loadSenteKoma(komaSize, resources, R.drawable.ryu);
        loadSenteKoma(komaSize, resources, R.drawable.uma);
        loadSenteKoma(komaSize, resources, R.drawable.narikyo);
        loadSenteKoma(komaSize, resources, R.drawable.narikei);
        loadSenteKoma(komaSize, resources, R.drawable.narigin);

    }

    private void loadSenteKoma(int komaSize, Resources resources, int resid) {
        Bitmap koma = Bitmap.createBitmap(komaSize, komaSize, Bitmap.Config.ARGB_8888);
        Bitmap tmp = BitmapFactory.decodeResource(resources, resid);
        GameView.stretch(tmp, koma);

        senteKomas.add(koma);
    }

}
