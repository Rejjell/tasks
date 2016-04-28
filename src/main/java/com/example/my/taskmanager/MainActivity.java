package com.example.my.taskmanager;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    TextView out;
    ArrayList<String> taskList;
    ActivityManager activityManager;
    static int taskMax = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        out = (TextView) findViewById(R.id.Out);
        taskList = new ArrayList<String>();
        activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        //checkReflection();
        String tmp =  "";

        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(taskMax);

        ActivityManager.RunningTaskInfo task = tasks.get(0);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (ActivityManager.RunningTaskInfo t : activityManager.getRunningTasks(taskMax)) {
            tmp += t.topActivity.getPackageName() + "\n" + t.topActivity.getClassName() + "\n";
            if (t.topActivity.getClassName().contains("CoolReader")) {
                task = t;
                break;
            }
        }
        startActivity(new Intent()
                .setClassName(task.topActivity.getPackageName(), task.topActivity.getClassName())
                /*.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)*/);

        /*
        for (ActivityManager.RunningTaskInfo task : activityManager.getRunningTasks(taskMax)){
            tmp += task.topActivity.getPackageName() + "\n" + task.topActivity.getClassName() + "\n";
            //out.append(tmp);
        }
        */


    }

    private void checkReflection() {
        try {
            Class c;
            c = Class.forName("android.app.ActivityManagerNative");

            Method m = c.getDeclaredMethod("getTasks", new Class[]{int.class, int.class});
            out.append(m.toString());
            Object o = m.invoke(null, new Object[]{10, 0});
            out.append("" + (o==null) + "\n" + o.toString());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
