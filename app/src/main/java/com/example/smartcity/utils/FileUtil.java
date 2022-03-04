package com.example.smartcity.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.smartcity.R;
import com.example.smartcity.bean.UserBean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileUtil {

    private static Bitmap avatarBitmap;

    public static String bitmapToString(Bitmap bitmap) {
        //将Bitmap转换成字符串
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 1, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static Bitmap stringToBitmap(String string) {
        // 将字符串转换成Bitmap类型
        byte[] bytes = Base64.decode(string, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static void saveImage(String path, Bitmap bitmap) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getAvatar(Context context) {
//        String path = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/avatar.jpeg";
//        File file = new File(path);
        if (SPUtil.getString(context, "avatar", "noAvatar").equals("noAvatar")) {
            new GetRetrofit(context).getWithToken().getUserBean().enqueue(new Callback<UserBean>() {
                @Override
                public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                    final UserBean body = response.body();
                    final UserBean.UserDTO user = body.getUser();
                    String avatarString = user.getAvatar();
                    if (TextUtils.isEmpty(avatarString)) {
                        avatarBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.a);
                        SPUtil.putString(context, "avatar", bitmapToString(avatarBitmap));
                    } else {
                        Bitmap bitmap = stringToBitmap(avatarString);
                        SPUtil.putString(context, "avatar",avatarString);
                        avatarBitmap = bitmap;
                    }
                }

                @Override
                public void onFailure(Call<UserBean> call, Throwable throwable) {

                }
            });
        } else {
            Log.i("ssss", "getAvatar: ");
            return stringToBitmap(SPUtil.getString(context, "avatar", "noAvatar"));
        }
        return avatarBitmap;
    }
}
