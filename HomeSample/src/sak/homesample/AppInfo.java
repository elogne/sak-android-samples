package sak.homesample;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;

public class AppInfo {
    public CharSequence title;   //タイトル
    public Drawable     icon;    //アイコン
    public Intent       intent;  //インテント

    //アクティビティの指定
    public void setActivity(String packageName,String className) {
        intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(new ComponentName(packageName,className));
        intent.setFlags(
        		Intent.FLAG_ACTIVITY_NEW_TASK |
        		Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
    }
}
