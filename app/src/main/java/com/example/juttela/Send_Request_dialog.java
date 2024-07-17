package com.example.juttela;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.juttela.Fragment.Chat.Chat_Activity;

public class Send_Request_dialog {
    public void Request_dialog(Context context){

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.send_request);

        Button textButton = dialog.findViewById(R.id.request_button);
        ImageView reject = dialog.findViewById(R.id.cancel);

        textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Request Sended", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Not Accepted", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });


        dialog.show();
    }
}
