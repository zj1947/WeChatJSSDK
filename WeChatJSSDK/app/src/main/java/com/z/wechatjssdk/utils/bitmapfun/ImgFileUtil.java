package com.z.wechatjssdk.utils.bitmapfun;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.IOException;

/**
 * Created by Administrator on 15-1-13.
 */
public class ImgFileUtil {

    public static Bitmap rotate(Bitmap b,String imageUri){
        ExifInterface exifInterface = null;
        int iDegree=0;
        try {
            exifInterface = new ExifInterface(imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (exifInterface != null) {
            int ori = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            switch (ori) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    iDegree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    iDegree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    iDegree = 270;
                    break;
                default:
                    iDegree = 0;
                    break;
            }
        }
        return rotate(b,iDegree);
    }

    public static Bitmap rotate(Bitmap b, int degrees) {
        if (degrees != 0 && b != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float)b.getWidth() / 2, (float) b.getHeight() / 2);
            try {
                Bitmap b2 = Bitmap.createBitmap(
                        b, 0, 0, b.getWidth(), b.getHeight(), m, true);
                if (b != b2) {
                    if (b != null && !b.isRecycled()) {
                        b.recycle();
                        b = null;
                    }

                    b = b2;
                }
            } catch (OutOfMemoryError ex) {
                // 建议大家如何出现了内存不足异常，最好return 原始的bitmap对象。.
                ex.printStackTrace();
            }
        }
        return b;
    }

}
