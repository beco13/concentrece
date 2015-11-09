package universidad.trabajo.concentrese.core;

import android.support.annotation.DrawableRes;

import universidad.trabajo.concentrese.R;

/**
 * Created by friendly on 03/11/2015.
 */
public class Elemento {

    Boolean visible = false;

    boolean uncovered = false;

    @DrawableRes
    int imgDefault = R.mipmap.ic_question_circle_white;

    @DrawableRes
    int imgToDiscover;

    public Elemento(int imgToDiscover) {
        this.imgToDiscover = imgToDiscover;
    }

    public int getImage(){
        if(uncovered || visible){
            return imgToDiscover;
        }else{
            return imgDefault;
        }
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public int getImgToDiscover() {
        return imgToDiscover;
    }

    public boolean getUncovered(){
        return this.uncovered;
    }

    public void setUncovered(boolean uncovered){
        this.uncovered = uncovered;
    }
}
