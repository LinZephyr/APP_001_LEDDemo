package com.example.cfinvo.app_001_leddemo;

import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//import android.os.ILedService;
//import android.os.ServiceManager;

public class MainActivity extends AppCompatActivity {

    private boolean ledon = false;
    private Button button = null;
    private CheckBox checkBoxled1 = null;
    private CheckBox checkBoxled2 = null;
    private CheckBox checkBoxled3 = null;
    private CheckBox checkBoxled4 = null;


    /* 定义一个Service成员 */
    //private ILedService iLedService = null;
    Object proxy = null;
    Method ledCtrl = null;


    class MyButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            ledon = !ledon;
            if(ledon){
                button.setText("ALL OFF");
                checkBoxled1.setChecked(true);
                checkBoxled2.setChecked(true);
                checkBoxled3.setChecked(true);
                checkBoxled4.setChecked(true);

                /* 调用JNI实现的iLedService类来点亮LED */
//                try {
//                    for (int i = 0; i < 4; i++)
//                        iLedService.ledCtrl(i, 1);
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }

                //使用反射
                try {
                    for (int i = 0; i < 4; i++)
                        ledCtrl.invoke(proxy, i, 1);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
            else{
                button.setText("ALL ON");
                checkBoxled1.setChecked(false);
                checkBoxled2.setChecked(false);
                checkBoxled3.setChecked(false);
                checkBoxled4.setChecked(false);

                /* 熄灭 */
//                try {
//                    for (int i = 0; i < 4; i++)
//                        ledCtrl.invoke(proxy, i, 1);
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }

                try {
                    for (int i = 0; i < 4; i++)
                        ledCtrl.invoke(proxy, i, 0);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        try {
            switch(view.getId()) {
                case R.id.LED1:
                    if (checked){
                        //iLedService.ledCtrl(0, 1);
                        ledCtrl.invoke(proxy, 0, 1);
                        // Put some meat on the sandwich
                        Toast.makeText(getApplicationContext(), "LED1 on", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //iLedService.ledCtrl(0, 0);
                        ledCtrl.invoke(proxy, 0, 0);
                        Toast.makeText(getApplicationContext(), "LED1 off", Toast.LENGTH_SHORT).show();
                        // Remove the meat
                    }
                    break;
                case R.id.LED2:
                    if (checked){
                        //iLedService.ledCtrl(1, 1);
                        ledCtrl.invoke(proxy, 1, 1);
                        // Put some meat on the sandwich
                        Toast.makeText(getApplicationContext(), "LED2 on", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //iLedService.ledCtrl(1, 0);
                        ledCtrl.invoke(proxy, 1, 0);
                        Toast.makeText(getApplicationContext(), "LED2 off", Toast.LENGTH_SHORT).show();
                        // Remove the meat
                    }
                    break;
                case R.id.LED3:
                    if (checked){
                        //iLedService.ledCtrl(2, 1);
                        ledCtrl.invoke(proxy, 2, 1);
                        // Put some meat on the sandwich
                        Toast.makeText(getApplicationContext(), "LED3 on", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //iLedService.ledCtrl(2, 0);
                        ledCtrl.invoke(proxy, 2, 0);
                        Toast.makeText(getApplicationContext(), "LED3 off", Toast.LENGTH_SHORT).show();
                        // Remove the meat
                    }
                    break;
                case R.id.LED4:
                    if (checked){
                        //iLedService.ledCtrl(3, 1);
                        ledCtrl.invoke(proxy, 3, 1);
                        // Put some meat on the sandwich
                        Toast.makeText(getApplicationContext(), "LED4 on", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //iLedService.ledCtrl(3, 0);
                        ledCtrl.invoke(proxy, 3, 0);
                        Toast.makeText(getApplicationContext(), "LED4 off", Toast.LENGTH_SHORT).show();
                        // Remove the meat
                    }
                    break;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //iLedService.ledOpen();
        /* 给service赋值*/
        //iLedService = ILedService.Stub.asInterface(ServiceManager.getService("led"));

        /********************* 通过反射的方式来获得服务 ***************************/
        try {
            Method getService = Class.forName("android.os.ServiceManager").getMethod("getService", String.class);
            //第一个参数应该是实例化对象，但是因为getService是个static方法，所以可以写成null
            Object ledService = getService.invoke(null, "led");

            //$Stub表示获得这个对象的内部类Stub
            Method asInterface = Class.forName("android.os.ILedService$Stub").getMethod("asInterface", IBinder.class);
            proxy = asInterface.invoke(null, ledService);
            ledCtrl = Class.forName("android.os.ILedService$Stub$Proxy").getMethod("ledCtrl", int.class, int.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        button = findViewById(R.id.button);
        button.setOnClickListener(new MyButtonListener());

        checkBoxled1 = findViewById(R.id.LED1);
        checkBoxled2 = findViewById(R.id.LED2);
        checkBoxled3 = findViewById(R.id.LED3);
        checkBoxled4 = findViewById(R.id.LED4);

    }
}
