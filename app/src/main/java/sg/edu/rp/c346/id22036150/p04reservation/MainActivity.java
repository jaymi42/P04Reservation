package sg.edu.rp.c346.id22036150.p04reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;
import java.time.LocalDate;
import java.time.ZoneId;

public class MainActivity extends AppCompatActivity {

    EditText nameInput;
    EditText numInput;
    EditText grpInput;
    DatePicker dp;
    TimePicker tp;
    RadioGroup smokingRG;
    Button btnConfirm;
    Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.nameInput);
        numInput = findViewById(R.id.numInput);
        grpInput = findViewById(R.id.grpInput);
        dp = findViewById(R.id.datePicker);
        tp = findViewById(R.id.timePicker);
        smokingRG = findViewById(R.id.smokingRG);
        btnConfirm = findViewById(R.id.buttonConfirm);
        btnReset = findViewById(R.id.buttonReset);

        
        dp.updateDate(2023, 7, 1);
        tp.setCurrentHour(19);
        tp.setCurrentMinute(30);




        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add your code for the action
                int dayCheck = 12;
                String period = "";
                int hour = 0;
                if (dayCheck > tp.getCurrentHour()) {
                    period = "am";
                    hour = tp.getCurrentHour();
                } else if (dayCheck <= tp.getCurrentHour()) {
                    period = "pm";
                    hour = tp.getCurrentHour() - 12;
                }

                if (nameInput.getText().toString() != null) {
                    if (numInput.length() == 8) {
                        if ((grpInput.getText().toString() != null) && (Integer.parseInt(grpInput.getText().toString()) >= 1)) {

                            String reservation;
                            reservation = String.format("Name: %s | Contact: %d | Pax: %d | ", nameInput.getText().toString(), Integer.parseInt(numInput.getText().toString()), Integer.parseInt(grpInput.getText().toString()));

                            if (smokingRG.getCheckedRadioButtonId() == R.id.radioBtnNoSmoke) {
                                reservation += "Non-Smoking Area | ";

                            } else {
                                reservation += "Smoking Area | ";
                            }

                            reservation += String.format("Date: %d/%d/%d | Time: %d:%d%s", dp.getDayOfMonth(), (dp.getMonth() + 1), dp.getYear(), hour, tp.getCurrentMinute(), period);


                            Toast.makeText(getApplicationContext(), reservation, Toast.LENGTH_LONG).show();


                        } else {
                            Toast.makeText(getApplicationContext(), "Field Incomplete", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Field Incomplete", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Field Incomplete", Toast.LENGTH_LONG).show();
                }
            }
        });

        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener(){
            @Override
            public void onTimeChanged(TimePicker v, int hourOfDay, int minute){
                if ((tp.getCurrentHour() < 8) || (tp.getCurrentHour() > 20)){
                    tp.setCurrentHour(20);
                    tp.setCurrentMinute(00);
                }

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dp.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    LocalDate currDate = LocalDate.now(ZoneId.systemDefault());

                    LocalDate selectedDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth);

                    if(selectedDate.isBefore(currDate)){
                        dp.updateDate(currDate.getYear(), currDate.getMonthValue(), currDate.getDayOfMonth() + 1);
                    }
                }
            });
        }
        
         btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameInput.setText("");
                numInput.setText("");
                grpInput.setText("");
                smokingRG.clearCheck();
                dp.updateDate(2023, 5,1);
                tp.setIs24HourView(true);
                tp.setCurrentHour(19);
                tp.setCurrentMinute(30);

            }
        });


    }
}
