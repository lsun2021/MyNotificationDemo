package com.demo.mynotificationdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button  btn_send_not= (Button) findViewById(R.id.btn_send_not);
        btn_send_not.setOnClickListener(this);
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
        }

    }
}
