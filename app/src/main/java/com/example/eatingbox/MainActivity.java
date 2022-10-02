package com.example.eatingbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    Button login, logon;
    EditText account;
    EditText passwd;
    String acc, pwd, check;
    Boolean isLoginPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.login);
        logon = findViewById(R.id.logon);
        account = findViewById(R.id.account);
        passwd = findViewById(R.id.passwd);
        login.setOnClickListener(new loginonClick());
        logon.setOnClickListener(new logononClick());
        Util.testlog("onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    class logononClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            //TODO

        }
    }

    class loginonClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            acc = account.getText().toString();
            pwd = passwd.getText().toString();
            login.setEnabled(false);
            account.setEnabled(false);
            passwd.setEnabled(false);
            Util.testlog("account: " + acc + " ,password: " + pwd);
            Intent intent = new Intent();
            while (pwd != null && acc != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Socket socket = new Socket("123.56.231.80", 6172);

                            OutputStream sendData = socket.getOutputStream();
                            sendData.write((acc + "\r\n" + pwd + "\r\n").getBytes(StandardCharsets.UTF_8));
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                            check = bufferedReader.readLine();

                            sendData.close();
                            bufferedReader.close();
                            socket.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            if (check == "1") {
                Util.testlog("验证成功正在跳转");
                Toast toast = Toast.makeText(getApplicationContext(), "欢迎，" + acc, Toast.LENGTH_SHORT);
                toast.show();
//                intent.putExtra("acc",acc);
//                intent.setClass(MainActivity.this,Select.class);
//                MainActivity.this.startActivity(intent);
            } else if (check == "0") {
                Util.testlog("密码验证失败");
                login.setEnabled(true);
                passwd.setText("");
                passwd.setEnabled(true);
                account.setEnabled(true);
                Toast toast = Toast.makeText(getApplicationContext(), "用户名或密码错误，请重新输入", Toast.LENGTH_SHORT);
                toast.show();

            } else {
                Util.testlog("服务器返回有问题");
                login.setEnabled(true);
                passwd.setEnabled(true);
                account.setEnabled(true);
                Toast toast = Toast.makeText(getApplicationContext(), "服务器错误，请联系客服或管理员", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }


}
