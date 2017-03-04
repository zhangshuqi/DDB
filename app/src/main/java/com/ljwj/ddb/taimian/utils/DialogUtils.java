package com.ljwj.ddb.taimian.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.ljwj.ddb.taimian.R;
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
    public static Dialog createDeleteDialog(final int state, final Activity activity, final String userId,final DeleteDialogInterface deleteDialogInterface) {
        QuitDialog.Builder builder = new QuitDialog.Builder(activity);
        builder.setMessage("确定要删除当前客户吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                LatelyClientDataDao ld =new LatelyClientDataDao(activity);
                ld.delete(userId);
                NewRoomClientDataDao ncd =new NewRoomClientDataDao(activity);
                ncd.delete(userId);
                //发送消息
                EventBus.getDefault().post(new EventBusUtilsUpdate(state+""));
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
    public static TimePickerDialog createTimeDialog(OnDateSetListener listener,Activity activity){
        long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;
        TimePickerDialog    mDialogAll = new TimePickerDialog.Builder()
                .setCallBack(listener)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("测量事件")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setHourText("时")
                .setMinuteText("分")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis())
                .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(activity.getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.ALL)
                .setWheelItemTextNormalColor(activity.getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(activity.getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(12)
                .build();
    return  mDialogAll;
    }
}
