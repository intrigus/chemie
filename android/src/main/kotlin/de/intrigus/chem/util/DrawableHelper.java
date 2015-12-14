package de.intrigus.chem.util;

import android.content.Context;

import de.intrigus.chem.R;

public class DrawableHelper {
    public static int getElementDrawable(int elementNumber, Context context) {
        int id = context.getResources().getIdentifier("element_" + elementNumber, "drawable", context.getPackageName());
        if (id == 0) {
            return R.drawable.element_not_found;
        }
        return id;
    }
}
