package com.ljwj.ddb.taimian.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.ljwj.ddb.taimian.bean.LatelyClientDataDao;
import com.ljwj.ddb.taimian.bean.NewRoomClientDataDao;
import com.ljwj.ddb.taimian.bean.OfficialClientDataDao;
import com.ljwj.ddb.taimian.bean.SoonClientDataDao;
import com.ljwj.ddb.taimian.dialog.QuitDialog;

import org.greenrobot.eventbus.EventBus;

/*
 *  @项目名：  DDB 
 *  @包名：    com.ljwj.ddb.taimian.utils
 *  @文件名:   DialogUtils
 *  @创建者:   Administrator
 *  @创建时间:  2017/3/4 0004 04:54
 *  @描述：    TODO
 */
public class DialogUtils {
    private static final String TAG = "DialogUtils";
    public static Dialog createDeleteDialog(final int position, final Activity activity, final String userId,final DeleteDialogInterface deleteDialogInterface) {
        QuitDialog.Builder builder = new QuitDialog.Builder(activity);
        builder.setMessage("确定要删除当前客户吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                //临时客户数据库删除
                SoonClientDataDao cd         =new SoonClientDataDao(activity);
                cd.delete(userId);
                //最近客户数据库删除
                LatelyClientDataDao ld =new LatelyClientDataDao(activity);
                ld.delete(userId);
                //删除测量客户数据库
                OfficialClientDataDao officialD = new OfficialClientDataDao(activity);
                officialD.delete(userId);
                //房间类型数据库
                NewRoomClientDataDao ncd =new NewRoomClientDataDao(activity);
                ncd.delete(userId);
                //发送消息
                EventBus.getDefault().post(new EventBusUtilsUpdate("1"));

                Toast.makeText(activity, "删除成功", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                deleteDialogInterface.positive();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                deleteDialogInterface.negative();
            }
        });
        return builder.create();
    }

    public interface DeleteDialogInterface{
        void positive();
        void negative();
    }
}
