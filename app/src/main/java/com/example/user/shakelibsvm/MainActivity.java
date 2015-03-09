package com.example.user.shakelibsvm;

import android.content.Context;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import libsvm.*;


public class MainActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView)findViewById(R.id.accuracy);

        String sd_card = Environment.getExternalStorageDirectory().toString();
        String path = sd_card + "/Log/Mei";

        String train_path = path + "/train.txt";
        String test_path = path + "/test.txt";
        String output_path = path + "/result.txt";
        String model_name = path + "/my_model.txt";

        String[] trainArgs = {train_path, model_name};
        String[] testArgs = {test_path, model_name, output_path};
        svm_train train = new svm_train();
        svm_predict predict = new svm_predict();
        try {
            long start_train_time = System.nanoTime();
            train.main(trainArgs);
            long end_train_time = System.nanoTime();
            textView.setText("LibSVM has finished Training. The Training time is: \n");
            long train_time = (end_train_time - start_train_time) / 1000000;//get milliseconds
            textView.append(String.valueOf(train_time) + "ms");
            long start_test_time = System.nanoTime();
            predict.main(testArgs);
            long end_test_time = System.nanoTime();
            textView.append("\nLibSVM has finished Testing. The Testing time is: \n");
            long test_time = (end_test_time - start_test_time) / 1000000;//get milliseconds
            textView.append(String.valueOf(test_time) + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*try {
            //InputStream in_train = getResources().openRawResource(R.raw.a1a_train);
            //if(in_train != null){
                //InputStreamReader in_train_reader = new InputStreamReader(in_train);
                File file = new File(train_path);
                BufferedReader bufferedReader_train = new BufferedReader(new FileReader(file));
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader_train.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }
                //in_train.close();
                data_train = stringBuilder.toString();
                textView.setText(data_train);

            //}
        } catch (IOException e) {
            e.printStackTrace();
        }*/
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
