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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class GameView extends View {

    private Bitmap offScr;
    Canvas canvas = new Canvas();
    Rect region = new Rect();

    Board board = new Board();
    Game game = new Game();

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    Paint backgroundPaint = new Paint();

    ArrayList<Koma> komas = new ArrayList<Koma>();
    Mochigomas senteMochigoma = new Mochigomas();
    Mochigomas goteMochigoma = new Mochigomas();

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

    int boardTop;
    int offX;
    int komaSize;

    void setKoma(int suji, int dan, Koma koma) {
        koma.pos(suji, dan);
        komas.add(koma);
        game.setKoma(suji, dan, koma);
    }

    void initKomas()
    {
        Resources res = getContext().getResources();
        komaSize = board.getKomaSize();
        boardTop = board.getBoardTop();
        offX = board.getOffX();

        game.setMochigomas(senteMochigoma, goteMochigoma);

        int lineWidth = 2;
        senteMochigoma.offset(offX, board.getSenteMochigomaTop(), komaSize+lineWidth);
        goteMochigoma.offset(offX, board.getGoteMochigomaTop(), komaSize+lineWidth);

        komaImages.loadKomas(komaSize, res);

        setupSenteFu(komaSize);
        setupGoteFu(komaSize);

        setKoma(2, 8, makeSenteKoma(new HiTraits(), KomaImages.IDX_HI));
        setKoma(8, 2, makeGoteKoma(new HiTraits(), KomaImages.IDX_HI));

        // kaku
        setKoma(8, 8, makeSenteKoma(null, KomaImages.IDX_KAKU));
        setKoma(2, 2, makeGoteKoma(null, KomaImages.IDX_KAKU));

        // kyo
        setKoma(1, 9, makeSenteKoma(null, KomaImages.IDX_KYO));
        setKoma(9, 9, makeSenteKoma(null, KomaImages.IDX_KYO));
        setKoma(1, 1, makeGoteKoma(null, KomaImages.IDX_KYO));
        setKoma(9, 1, makeGoteKoma(null, KomaImages.IDX_KYO));

        // kei
        setKoma(2, 9, makeSenteKoma(null, KomaImages.IDX_KEI));
        setKoma(8, 9, makeSenteKoma(null, KomaImages.IDX_KEI));
        setKoma(2, 1, makeGoteKoma(null, KomaImages.IDX_KEI));
        setKoma(8, 1, makeGoteKoma(null, KomaImages.IDX_KEI));

        // gin
        setKoma(3, 9, makeSenteKoma(null, KomaImages.IDX_GIN));
        setKoma(7, 9, makeSenteKoma(null, KomaImages.IDX_GIN));
        setKoma(3, 1, makeGoteKoma(null, KomaImages.IDX_GIN));
        setKoma(7, 1, makeGoteKoma(null, KomaImages.IDX_GIN));

        // kin
        setKoma(4, 9, makeSenteKoma(null, KomaImages.IDX_KIN));
        setKoma(6, 9, makeSenteKoma(null, KomaImages.IDX_KIN));
        setKoma(4, 1, makeGoteKoma(null, KomaImages.IDX_KIN));
        setKoma(6, 1, makeGoteKoma(null, KomaImages.IDX_KIN));

        // gyoku
        setKoma(5, 9, makeSenteKoma(null, KomaImages.IDX_GYOKU));
        setKoma(5, 1, makeGoteKoma(null, KomaImages.IDX_GYOKU));

    }

    Koma makeKoma(IKomaTraits traits, int komaIdx)
    {
        Koma koma = new Koma(komaIdx, komaImages.getSenteImage(komaIdx), komaImages.getSenteNariImage(komaIdx), komaImages.getGoteImage(komaIdx), komaImages.getGoteNariImage(komaIdx));
        koma.setKomaSize(komaSize);
        koma.setKomaTraits(traits);
        koma.offset(offX, boardTop);
        return koma;
    }


    Koma makeSenteKoma(IKomaTraits traits, int komaIdx) {
        return makeKoma(traits, komaIdx).sente();
    }

    Koma makeGoteKoma(IKomaTraits traits, int komaIdx) {
        return makeKoma(traits, komaIdx).gote();
    }

    private void setupSenteFu(int komaSize) {
        FuTraits fuTraits = new FuTraits();

        for(int i = 0; i < 9; i++) {
            // 1 origin.
            setKoma(i+1, 7, makeSenteKoma(fuTraits, KomaImages.IDX_FU));
        }
    }

    private void setupGoteFu(int komaSize) {
        FuTraits fuTraits = new FuTraits();

        for(int i = 0; i < 9; i++) {
            setKoma(i+1, 3, makeGoteKoma(fuTraits, KomaImages.IDX_FU));
        }

    }

    void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int suji = board.xToSuji(x);
                int dan = board.yToDan(y);
                if(suji < 0 || dan < 0) {
                    break; // need to support mochigoma.
                }else {
                    if ( dan == Board.DAN_GOTE_MOCHIGOMA || dan == Board.DAN_SENTE_MOCHIGOMA) {
                        if(dan == Board.DAN_GOTE_MOCHIGOMA)
                            showMessage("gote mochigoma dan");
                        else
                            showMessage("sente mochigoma dan");
                        break;
                    }
                    if(game.onTouch(suji, dan)) {
                        invalidate();
                    }
                    return true;
                }
                /*
                if(game.getState() == Game.STATE_SELECT) {
                    Koma koma = game.getSelected();
                    Log.d("JosekiMemo", "selected: suji,dan = " + koma.getSuji() + ", " + koma.getDan());
                }
                */
        }
        return super.onTouchEvent(event);
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
            if(!koma.isMochigoma())
                koma.draw(this.canvas);
        }

        senteMochigoma.draw(this.canvas);
        goteMochigoma.draw(this.canvas);

        canvas.drawBitmap(offScr, region, region, null);
    }
}
