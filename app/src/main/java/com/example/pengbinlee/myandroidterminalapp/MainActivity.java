package com.example.pengbinlee.myandroidterminalapp;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView content;
    private EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init() {
        content = (TextView) findViewById(R.id.content);
        input = (EditText) findViewById(R.id.input);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        showShellResult();
        cleanInput();
    }

    public void showShellResult() {
        String result = "";
        String temp = content.getText().toString();
        String shellCommand = input.getText().toString().trim();

        if(TextUtils.isEmpty(shellCommand)) {
            Toast.makeText(this, R.string.input_is_empty, Toast.LENGTH_SHORT).show();
        } else {
            Runtime mRuntime = Runtime.getRuntime();
            try {
                Process mProcess = mRuntime.exec(shellCommand);
                InputStream is = mProcess.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader mReader = new BufferedReader(isr);
                String string;
                while ((string = mReader.readLine()) != null) {
                    result = result + string + "\n";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            content.setText(temp + "$" + shellCommand + "\n" + result);
        }
    }

    public void cleanInput() {
        input.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clean:
                content.setText("");
                break;
            case R.id.about:
                displayAboutDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void displayAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(R.string.about);
        builder.setMessage(R.string.about_content);

        builder.show();
    }
}