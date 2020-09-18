package com.hfad.imageclassification;

//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void connectServer(View v){

        EditText ipv4AddressView = findViewById(R.id.IPAddress);
        String ipv4Address = ipv4AddressView.getText().toString();
        EditText portNumberView = findViewById(R.id.portNumber);
        String portNumber = portNumberView.getText().toString();

        // Create the server's URL
        String postUrl= "http://"+ipv4Address+":"+portNumber+"/";

        String postBodyText="Hello";

        // Declare the type of content
        MediaType mediaType = MediaType.parse("text/plain; charset=utf-8");

        // Represents the message to be sent. create() accepts Content type and Content
        RequestBody postBody = RequestBody.create(mediaType, postBodyText);

        // Send the message to the server
        postRequest(postUrl, postBody);
    }

    /*/
        This method sends the message to the server. It accepts destination URL and Request Body
     */
    public void postRequest(String postUrl, RequestBody postBody) {

        OkHttpClient client = new OkHttpClient();

        // Create HTTP request to send via HTTP connection. Request.Builder is reponsible for
        // mapping destination URL with request body. Request built using built() method
        Request request = new Request.Builder()
                .url(postUrl)
                .post(postBody)
                .build();

        // newCall() returns an instance of the Call interface, which represents a request waiting
        // to be sent over the channel using OkHttpClient

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Cancel the post on failure.
                call.cancel();

                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView responseText = findViewById(R.id.responseText);
                        responseText.setText("Failed to Connect to Server");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView responseText = findViewById(R.id.responseText);
                        try {
                            // response from server is returned is using body() method
                            responseText.setText(response.body().string());

                        }

                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}


/*
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

 */