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
    int komaSize;

    void initKomas()
    {
        Resources res = getContext().getResources();
        komaSize = board.getKomaSize();
        offY = board.getOffY();

        komaImages.loadKomas(komaSize, res);

        setupSenteFu(komaSize, offY);
        setupGoteFu(komaSize, offY);

        komas.add(
                makeSenteKoma(new HiTraits(komaImages.getSenteImage(KomaImages.IDX_HI)))
                .pos(2, 8));

        komas.add(
                makeGoteKoma(new HiTraits(komaImages.getGoteImage(KomaImages.IDX_HI)))
                        .pos(8, 2));

    }

    Koma makeKoma(IKomaTraits traits) {
        Koma koma = new Koma();
        koma.setKomaSize(komaSize);
        koma.setKomaTraits(traits);
        koma.offset(0, offY);
        return koma;
    }

    Koma makeSenteKoma(IKomaTraits traits) {
        return makeKoma(traits).sente();
    }

    Koma makeGoteKoma(IKomaTraits traits) {
        return makeKoma(traits).gote();
    }

    private void setupSenteFu(int komaSize, int offY) {
        FuTraits fuTraits = new FuTraits(komaImages.getSenteImage(KomaImages.IDX_FU));

        for(int i = 0; i < 9; i++) {
            // 1 origin.
            komas.add(makeSenteKoma(fuTraits).pos(i+1, 7));
        }
    }

    private void setupGoteFu(int komaSize, int offY) {
        FuTraits fuTraits = new FuTraits(komaImages.getGoteImage(KomaImages.IDX_FU));

        for(int i = 0; i < 9; i++) {
            komas.add(makeGoteKoma(fuTraits).pos(i+1, 3));
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
