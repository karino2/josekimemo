package karino2.livejournal.com.josekimemo;

import java.util.HashMap;

/**
 * Created by _ on 2016/10/07.
 */
public class Game {
    public static final int STATE_NORMAL = 1;
    public static final int STATE_SELECT = 2;

    // public static final int STATE_QUERY_NARU = 3;

    int state = STATE_NORMAL;
    boolean issente = true;
    Mochigomas senteMochigomas;
    Mochigomas goteMochigomas;

    public void setMochigomas(Mochigomas sente, Mochigomas gote) {
        senteMochigomas = sente;
        goteMochigomas = gote;
    }

    public boolean isSente() {
        return issente;
    }

    public int getState() {
        return state;
    }

    HashMap<Integer, Koma> komas = new HashMap<>();


    int skToKey(int suji, int dan) {
        return suji*100+dan;
    }

    int keyToSuji(int mix) {
        return mix/100;
    }

    int keyToDan(int mix) {
        return mix%100;
    }

    public void setKoma(int suji, int dan, Koma koma) {
        komas.put(skToKey(suji, dan), koma);
    }

    public Koma getKoma(int suji, int dan) {
        int key = skToKey(suji, dan);
        if(komas.containsKey(key))
            return komas.get(key);
        return null;
    }


    // return need redraw.
    public boolean onTouch(int suji, int dan) {
        if(state == STATE_NORMAL) {
            return onTouchNormal(suji, dan);
        } else if(state == STATE_SELECT) {
            return onTouchSelected(suji, dan);
        }
        return false;
    }

    Koma selected;
    public Koma getSelected() { return selected; }

    void gotoSelectState(int suji, int dan, Koma selCand) {
        state = STATE_SELECT;
        selected = selCand;
    }

    boolean onTouchNormal(int suji, int dan) {
        Koma selCand = getKoma(suji, dan);
        if(selCand == null)
            return false;
        if(isSente() && selCand.isSente()) {
            gotoSelectState(suji, dan, selCand);
            return true;
        } else if(!isSente() && !selCand.isSente()) {
            gotoSelectState(suji, dan, selCand);
            return true;
        }
        return false;
    }

    void moveKoma(int suji, int dan, Koma target) {
        int orgSuji = target.getSuji();
        int orgDan = target.getDan();

        int key = skToKey(orgSuji, orgDan);
        komas.remove(key);
        komas.put(skToKey(suji, dan), target);
        target.pos(suji, dan);

    }

    boolean onTouchSelected(int suji, int dan) {
        Koma selCand = getKoma(suji, dan);

        // can't move on to our koma.
        if(selCand != null && selCand.isSente() == selected.isSente())
            return false;

        if(selected.canMove(suji, dan)) {
            if(selCand == null) {
                moveKoma(suji, dan, selected);
                gotoNextTurn();
                return true;
            } else {
                if(selCand.isSente()) {
                    gotoGoteMochigoma(selCand);
                } else {
                    gotoSenteMochigoma(selCand);
                }
                moveKoma(suji, dan, selected);
                gotoNextTurn();
                return true;
            }
            // need to support take here.

        }

        return false;
    }

    private void gotoSenteMochigoma(Koma selCand) {
        selCand.sente();
        selCand.pos(0, Board.DAN_SENTE_MOCHIGOMA);
        senteMochigomas.addKoma(selCand);
    }

    private void gotoGoteMochigoma(Koma selCand) {
        selCand.gote();
        selCand.pos(0, Board.DAN_GOTE_MOCHIGOMA);
        goteMochigomas.addKoma(selCand);
    }

    private void gotoNextTurn() {
        issente = !isSente();
        selected = null;
        state = STATE_NORMAL;
    }

}
