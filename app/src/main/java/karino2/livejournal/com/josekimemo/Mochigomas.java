package karino2.livejournal.com.josekimemo;

import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by _ on 2016/10/15.
 */

public class Mochigomas {
    ArrayList<MochigomaGroup> groups = new ArrayList<>();

    public Mochigomas() {
        // including GYOKU, for safe.
        for(int i = 0; i < 8; i++) {
            groups.add(new MochigomaGroup());
        }
    }

    int offX = 0;
    int offY = 0;
    int masuWidth = 0;

    public void offset(int x, int y, int masuWidth_) {
        offX = x;
        offY = y;
        masuWidth = masuWidth_;
    }

    public void draw(Canvas canvas) {
        float y = (float)offY;


        for(int i = 0; i < groups.size(); i++) {
            MochigomaGroup group = groups.get(i);
            if(!group.isEmpty()) {
                float x = (float) (offX + i * masuWidth);
                canvas.drawBitmap(group.getImg(), x, y, null);
            }
        }
    }


    public void addKoma(Koma koma) {
        int id = koma.getId();
        MochigomaGroup empty = null;
        for(MochigomaGroup group : groups) {
            if(group.getId() == id) {
                group.add(koma);
                return;
            } else if (empty == null && group.isEmpty()) {
                empty = group;
            }
        }
        if(empty == null)
            throw new RuntimeException("no empy mochigomagroup, never reached here.");
        empty.add(koma);
        sortGroups();
    }

    private void sortGroups() {
        // TODO: implement here.
    }


}
