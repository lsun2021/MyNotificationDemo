package com.demo.mynotificationdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.icu.text.BreakIterator;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    private static final String TAG = "MainActivity";
    private ButtonBroadCaseReceiver broadCaseReceiver;
    public boolean isPlay = false;  //是否正在播放
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button  btn_send_not= (Button) findViewById(R.id.btn_send_not);
        Button  btn_send_mystyle_not= (Button) findViewById(R.id.btn_send_mystyle_not);
        btn_send_not.setOnClickListener(this);
        btn_send_mystyle_not.setOnClickListener(this);
        initReceiver();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_send_not:
                Intent  intent = new Intent(this,NotificationActivity.class);
                PendingIntent pendingIntent  =PendingIntent.getActivity(this,0,intent,0);
                NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
                mBuilder.setContentTitle("This is  a  Title");
//                mBuilder.setContentText("服务在后台。没错，Android 中的服务是存在后台中的，它适合去执行哪些不需要与用户交互的但是还被要求长期运行的任务。比如，你在聊QQ的时候，喜欢听音乐，这时候你的音乐就是在后台挂着，即使你开了另外一个应用程序，当前的应用程序还是不会被关闭。\n" +
//                        "  不过需要说明的是，Service服务不是运行在一个独立的程序中的，而是依赖于创建服务的这个应用程序中的。如果这个程序被杀死，服务也会停止。\n" +
//                        "  前面说服务是在后台的。");
                mBuilder.setWhen(System.currentTimeMillis());
                mBuilder.setSmallIcon(R.mipmap.ic_launcher);
                mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
                mBuilder.setContentIntent(pendingIntent);
                //设置点击消失
//                mBuilder.setAutoCancel(true);
                //设置长文字
//                mBuilder.setStyle(new android.support.v4.app.NotificationCompat.BigTextStyle().bigText("服务在后台。没错，Android 中的服务是存在后台中的，它适合去执行哪些不需要与用户交互的但是还被要求长期运行的任务。比如，你在聊QQ的时候，喜欢听音乐，这时候你的音乐就是在后台挂着，即使你开了另外一个应用程序，当前的应用程序还是不会被关闭。\\n\" +\n" +
//                                          "  不过需要说明的是，Service服务不是运行在一个独立的程序中的，而是依赖于创建服务的这个应用程序中的。如果这个程序被杀死，服务也会停止。\\n\" +\n" +
//                        "  前面说服务是在后台的"));
                //设置大图
                mBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.a2  )));
               //设置优先级
                 mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
                manager.notify(1,mBuilder.build());
                break;

            case R.id.btn_send_mystyle_not:
                showButtonNotify();
                break;
        }

    }


    private void showButtonNotify() {
        NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        RemoteViews mRemoteViews= new RemoteViews(getPackageName(),R.layout.notification_my_style);
        mRemoteViews.setImageViewResource(R.id.iv_not_head_image,R.mipmap.youname);

        mRemoteViews.setTextViewText(R.id.tv_not_head_title,"应思量");
        mRemoteViews.setTextViewText(R.id.tv_not_head_content,"五色石");
        if( Build.VERSION.SDK_INT <= 9){
            mRemoteViews.setViewVisibility(R.id.ll_custom_button, View.GONE);
        }else{
            mRemoteViews.setViewVisibility(R.id.ll_custom_button, View.VISIBLE);
            if(isPlay){
                mRemoteViews.setImageViewResource(R.id.iv_not_play,R.drawable.btn_pause);
            }else{
                mRemoteViews.setImageViewResource(R.id.iv_not_play,R.drawable.btn_play);
            }
        }
        //点击事件处理
        Intent buttonIntent=new Intent(ACTION_BUTTON);
         //头像点击
        buttonIntent.putExtra(INTENT_BUTTONID_TAG,BUTTON_PREV_ID);
        //这里加了广播，所及INTENT的必须用getBroadcast方法
        PendingIntent intent_head_image = PendingIntent.getBroadcast(this, 1, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.iv_not_head_image, intent_head_image);
        //歌名
        buttonIntent.putExtra(INTENT_BUTTONID_TAG,TEXTVIEW_TITLE_ID);
        PendingIntent intent_title = PendingIntent.getBroadcast(this,2,buttonIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.tv_not_head_title,intent_title);
        //歌手
        buttonIntent.putExtra(INTENT_BUTTONID_TAG,TEXTVIEW_CONTENT_ID);
        PendingIntent intent_content = PendingIntent.getBroadcast(this,3,buttonIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.tv_not_head_content,intent_content);

        //上一曲
        buttonIntent.putExtra(INTENT_BUTTONID_TAG,IMAGEVIEW_LEFT_ID);
        PendingIntent intent_left = PendingIntent.getBroadcast(this,4,buttonIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.iv_not_up,intent_left);
        //播放、暂停按钮
        buttonIntent.putExtra(INTENT_BUTTONID_TAG,IMAGEVIEW_CONTENT_ID);
        PendingIntent intent_play= PendingIntent.getBroadcast(this,5,buttonIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.iv_not_play, intent_play);
        //下一曲
        buttonIntent.putExtra(INTENT_BUTTONID_TAG,IMAGEVIEW_RIGHT_ID);
        PendingIntent intent_right= PendingIntent.getBroadcast(this,6,buttonIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.iv_not_next, intent_right);

        NotificationCompat.Builder  builder= new NotificationCompat.Builder(this);
        builder.setContent(mRemoteViews);
        builder.setWhen(System.currentTimeMillis());
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setOngoing(true);
        builder.setSmallIcon(R.drawable.a2);

        manager.notify(1, builder.build());
    }

    /** 通知栏按钮点击事件对应的ACTION */
    public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
    public final static String INTENT_BUTTONID_TAG = "ButtonId";
    public final static int BUTTON_PREV_ID = 1;  //点击头像
    public final static  int  TEXTVIEW_TITLE_ID=2; //点击标题
    public final static  int  TEXTVIEW_CONTENT_ID=3; //点击内容
    public final static  int  IMAGEVIEW_LEFT_ID=4; //点击播放
    public final static  int  IMAGEVIEW_CONTENT_ID=5; //点击播放
    public final static  int  IMAGEVIEW_RIGHT_ID=6; //点击往下


    public  void  initReceiver(){
        broadCaseReceiver = new ButtonBroadCaseReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_BUTTON);
        registerReceiver(broadCaseReceiver, intentFilter);
    }

    private  class ButtonBroadCaseReceiver  extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(ACTION_BUTTON)){
                //通过传递过来的ID判断按钮点击属性或者通过getResultCode()获得相应点击事件
                int  buttonId =  intent.getIntExtra(INTENT_BUTTONID_TAG,0);
                switch (buttonId){
                    case BUTTON_PREV_ID:
                        Log.e(TAG, "onReceive:点击头像了 " );
                        Toast.makeText(context, "点击头像了", Toast.LENGTH_SHORT).show();
                        Intent  imageBtn=new Intent(getApplicationContext(),HeadImageActivity.class);
                        startActivity(imageBtn);
                        break;
                    case TEXTVIEW_TITLE_ID:
                        Log.e(TAG, "onReceive:点击标题了 " );
                        Toast.makeText(context, "点击标题了", Toast.LENGTH_SHORT).show();
                        break;
                    case TEXTVIEW_CONTENT_ID:
                        Log.e(TAG, "onReceive:点击了内容 " );
                        Toast.makeText(context, "点击了内容", Toast.LENGTH_SHORT).show();
                        break;
                    case IMAGEVIEW_LEFT_ID:
                        Log.e(TAG, "onReceive: 点击了上一曲 " );
                        Toast.makeText(context, "点击了上一曲 ", Toast.LENGTH_SHORT).show();
                        break;
                    case IMAGEVIEW_CONTENT_ID:
                        String play_status = "";
                        isPlay = !isPlay;
                        if(isPlay){
                            play_status = "开始播放";
                        }else{
                            play_status = "已暂停";
                        }
                        showButtonNotify();
                        Log.d(TAG , play_status);
                        Toast.makeText(getApplicationContext(), play_status, Toast.LENGTH_SHORT).show();
                        break;
                    case IMAGEVIEW_RIGHT_ID:
                        Log.e(TAG, "onReceive: 点击了下一曲 " );
                        Toast.makeText(context, "点击了下一曲", Toast.LENGTH_SHORT).show();
                        break;

                }
            }

        }
    }


    @Override
    protected void onDestroy() {
        if(broadCaseReceiver != null){
            unregisterReceiver(broadCaseReceiver);
        }
        super.onDestroy();
    }
}
