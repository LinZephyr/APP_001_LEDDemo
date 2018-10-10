package com.example.cfinvo.app_001_leddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.cfinvo.hardlibrary.HardControl;

public class MainActivity extends AppCompatActivity {

    private boolean ledon = false;
    private Button button = null;
    private CheckBox checkBoxled1 = null;
    private CheckBox checkBoxled2 = null;
    private CheckBox checkBoxled3 = null;
    private CheckBox checkBoxled4 = null;


    class MyButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            HardControl hardControl = new HardControl();

            ledon = !ledon;
            if(ledon){
                button.setText("ALL OFF");
                checkBoxled1.setChecked(true);
                checkBoxled2.setChecked(true);
                checkBoxled3.setChecked(true);
                checkBoxled4.setChecked(true);

                /* 调用JNI实现的HardControl类来点亮LED */
                HardControl.ledCtrl(0, 1);
                HardControl.ledCtrl(1, 1);
                HardControl.ledCtrl(2, 1);
                HardControl.ledCtrl(3, 1);
                //HardControl.ledCtrl(1, 1);
            }
            else{
                button.setText("ALL ON");
                checkBoxled1.setChecked(false);
                checkBoxled2.setChecked(false);
                checkBoxled3.setChecked(false);
                checkBoxled4.setChecked(false);

                /* 熄灭 */
                HardControl.ledCtrl(0, 0);
                HardControl.ledCtrl(1, 0);
                HardControl.ledCtrl(2, 0);
                HardControl.ledCtrl(3, 0);
                //HardControl.ledCtrl(1, 0);
            }
        }
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.LED1:
                if (checked){
                    HardControl.ledCtrl(0, 1);
                    // Put some meat on the sandwich
                    Toast.makeText(getApplicationContext(), "LED1 on", Toast.LENGTH_SHORT).show();
                }
                else{
                    HardControl.ledCtrl(0, 0);
                    Toast.makeText(getApplicationContext(), "LED1 off", Toast.LENGTH_SHORT).show();
                    // Remove the meat
                }
                break;
            case R.id.LED2:
                if (checked){
                    HardControl.ledCtrl(1, 1);
                    // Put some meat on the sandwich
                    Toast.makeText(getApplicationContext(), "LED2 on", Toast.LENGTH_SHORT).show();
                }
                else{
                    HardControl.ledCtrl(1, 0);
                    Toast.makeText(getApplicationContext(), "LED2 off", Toast.LENGTH_SHORT).show();
                    // Remove the meat
                }
                break;
            case R.id.LED3:
                if (checked){
                    HardControl.ledCtrl(2, 1);
                    // Put some meat on the sandwich
                    Toast.makeText(getApplicationContext(), "LED3 on", Toast.LENGTH_SHORT).show();
                }
                else{
                    HardControl.ledCtrl(2, 0);
                    Toast.makeText(getApplicationContext(), "LED3 off", Toast.LENGTH_SHORT).show();
                    // Remove the meat
                }
                break;
            case R.id.LED4:
                if (checked){
                    HardControl.ledCtrl(3, 1);
                    // Put some meat on the sandwich
                    Toast.makeText(getApplicationContext(), "LED4 on", Toast.LENGTH_SHORT).show();
                }
                else{
                    HardControl.ledCtrl(3, 0);
                    Toast.makeText(getApplicationContext(), "LED4 off", Toast.LENGTH_SHORT).show();
                    // Remove the meat
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HardControl.ledOpen();

        button = findViewById(R.id.button);
        button.setOnClickListener(new MyButtonListener());

        checkBoxled1 = findViewById(R.id.LED1);
        checkBoxled2 = findViewById(R.id.LED2);
        checkBoxled3 = findViewById(R.id.LED3);
        checkBoxled4 = findViewById(R.id.LED4);

    }
}
