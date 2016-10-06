package karino2.livejournal.com.josekimemo;

/**
 * Created by _ on 2016/10/06.
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class GameView extends View {

    private Bitmap offScr;
    Canvas canvas = new Canvas();
    Rect region = new Rect();

    Board board = new Board();

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    Paint backgroundPaint = new Paint();

    ArrayList<Koma> komas = new ArrayList<Koma>();

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        board.setSize(w, h);

        if(offScr != null) offScr.recycle();

        offScr = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(offScr);
    }


    public static void stretchUpsideDown(Bitmap src, Bitmap dest)
    {
        dest.eraseColor( 0 );

        Canvas canvas = new Canvas( dest );
        Paint paint = new Paint();
        paint.setFilterBitmap( true );

        Matrix m = new Matrix();
        m.postScale( (float)dest.getWidth()/src.getWidth(), (float)dest.getHeight()/src.getHeight() );
        m.postRotate(180, dest.getWidth()/2, dest.getWidth()/2);

        canvas.drawBitmap(src, m, paint);
    }

    public static void stretch(Bitmap src, Bitmap dest)
    {
        dest.eraseColor( 0 );

        Canvas canvas = new Canvas( dest );
        Paint paint = new Paint();
        paint.setFilterBitmap( true );

        Matrix m = new Matrix();
        m.postScale( (float)dest.getWidth()/src.getWidth(), (float)dest.getHeight()/src.getHeight() );
        canvas.drawBitmap(src, m, paint);
    }

    boolean initialized = false;
    KomaImages komaImages = new KomaImages();

    int offY;
    int offX;
    int komaSize;

    void initKomas()
    {
        Resources res = getContext().getResources();
        komaSize = board.getKomaSize();
        offY = board.getOffY();
        offX = board.getOffX();

        komaImages.loadKomas(komaSize, res);

        setupSenteFu(komaSize);
        setupGoteFu(komaSize);

        komas.add(
                makeSenteKoma(new HiTraits(), KomaImages.IDX_HI)
                .pos(2, 8));

        komas.add(
                makeGoteKoma(new HiTraits(), KomaImages.IDX_HI)
                        .pos(8, 2));

        // kaku
        komas.add(
                makeSenteKoma(null, KomaImages.IDX_KAKU)
                        .pos(8, 8));
        komas.add(
                makeGoteKoma(null, KomaImages.IDX_KAKU)
                        .pos(2, 2));

        // kyo
        komas.add(
                makeSenteKoma(null, KomaImages.IDX_KYO)
                        .pos(1, 9));
        komas.add(
                makeSenteKoma(null, KomaImages.IDX_KYO)
                        .pos(9, 9));
        komas.add(
                makeGoteKoma(null, KomaImages.IDX_KYO)
                        .pos(1, 1));
        komas.add(
                makeGoteKoma(null, KomaImages.IDX_KYO)
                        .pos(9, 1));

        // kei
        komas.add(
                makeSenteKoma(null, KomaImages.IDX_KEI)
                        .pos(2, 9));
        komas.add(
                makeSenteKoma(null, KomaImages.IDX_KEI)
                        .pos(8, 9));
        komas.add(
                makeGoteKoma(null, KomaImages.IDX_KEI)
                        .pos(2, 1));
        komas.add(
                makeGoteKoma(null, KomaImages.IDX_KEI)
                        .pos(8, 1));

        // gin
        komas.add(
                makeSenteKoma(null, KomaImages.IDX_GIN)
                        .pos(3, 9));
        komas.add(
                makeSenteKoma(null, KomaImages.IDX_GIN)
                        .pos(7, 9));
        komas.add(
                makeGoteKoma(null, KomaImages.IDX_GIN)
                        .pos(3, 1));
        komas.add(
                makeGoteKoma(null, KomaImages.IDX_GIN)
                        .pos(7, 1));

        // kin
        komas.add(
                makeSenteKoma(null, KomaImages.IDX_KIN)
                        .pos(4, 9));
        komas.add(
                makeSenteKoma(null, KomaImages.IDX_KIN)
                        .pos(6, 9));
        komas.add(
                makeGoteKoma(null, KomaImages.IDX_KIN)
                        .pos(4, 1));
        komas.add(
                makeGoteKoma(null, KomaImages.IDX_KIN)
                        .pos(6, 1));

        // gyoku
        komas.add(
                makeSenteKoma(null, KomaImages.IDX_GYOKU)
                        .pos(5, 9));
        komas.add(
                makeGoteKoma(null, KomaImages.IDX_GYOKU)
                        .pos(5, 1));
    }

    Koma makeKoma(IKomaTraits traits, Bitmap komaImg) {
        Koma koma = new Koma();
        koma.setKomaSize(komaSize);
        koma.setKomaTraits(traits);
        koma.setKomaImg(komaImg, null);
        koma.offset(offX, offY);
        return koma;
    }

    Koma makeSenteKoma(IKomaTraits traits, int komaIdx) {
        return makeKoma(traits, komaImages.getSenteImage(komaIdx)).sente();
    }

    Koma makeGoteKoma(IKomaTraits traits, int komaIdx) {
        return makeKoma(traits, komaImages.getGoteImage(komaIdx)).gote();
    }

    private void setupSenteFu(int komaSize) {
        FuTraits fuTraits = new FuTraits();

        for(int i = 0; i < 9; i++) {
            // 1 origin.
            komas.add(makeSenteKoma(fuTraits, KomaImages.IDX_FU).pos(i+1, 7));
        }
    }

    private void setupGoteFu(int komaSize) {
        FuTraits fuTraits = new FuTraits();

        for(int i = 0; i < 9; i++) {
            komas.add(makeGoteKoma(fuTraits, KomaImages.IDX_FU).pos(i+1, 3));
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // canvas
        if(offScr == null)
            return;

        if(!initialized) {
            initialized = true;
            initKomas();
        }

        backgroundPaint.setColor(0xFFDEB037);
        region.set(0, 0, offScr.getWidth(), offScr.getHeight());

        this.canvas.drawRect(region, backgroundPaint);

        board.draw(this.canvas);
        for(Koma koma : komas) {
            koma.draw(this.canvas);
        }


        canvas.drawBitmap(offScr, region, region, null);
    }
}
