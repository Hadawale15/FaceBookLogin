package com.example.demofacebooklogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class SecondPage extends AppCompatActivity {

    ImageView imageView;
    TextView name;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);
       imageView=findViewById(R.id.i11);
       name=findViewById(R.id.t2);
       button=findViewById(R.id.logout_id);

       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               LoginManager.getInstance().logOut();
               Intent intent=new Intent(SecondPage.this,MainActivity.class);
               startActivity(intent);
               finish();
           }
       });

        AccessToken accessToken=AccessToken.getCurrentAccessToken();

        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                        try {
                            String FullName=object.getString("name");
                            String Url=object.getJSONObject("picture").getJSONObject("data").getString("url");
                            name.setText(FullName);
                            Picasso.get().load(Url).into(imageView);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();


    }
}