package universidad.trabajo.concentrese.core;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import universidad.trabajo.concentrese.R;

import static universidad.trabajo.concentrese.R.drawable.btn_black_radius;

/**
 * Created by friendly on 02/11/2015.
 */
public class ImageButtonAdapter extends BaseAdapter {

    private Context tmpContext;
    private Elemento elementsPictures[];


    public ImageButtonAdapter(Context tmpContext, Elemento[] tmpListPictures) {
        this.tmpContext = tmpContext;
        this.elementsPictures = tmpListPictures;
    }

    public int getCount() {
        return 16;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView tmpImageButton;

        if (convertView == null) {
            tmpImageButton = new ImageView(tmpContext);
            tmpImageButton.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT,130));
            tmpImageButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
            tmpImageButton.setBackgroundResource(btn_black_radius);
        } else {
            tmpImageButton = (ImageView) convertView;
        }

        tmpImageButton.setImageResource(elementsPictures[position].getImage());

        return tmpImageButton;
    }

}
