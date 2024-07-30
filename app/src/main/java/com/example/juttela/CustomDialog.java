package com.example.juttela;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

public class CustomDialog {

    public void ShowCustomDialog(FragmentActivity context){

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.friend_dialog_box);

        Button textButton = dialog.findViewById(R.id.accept_button);
        ImageView reject = dialog.findViewById(R.id.reject_button);

        textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Chat_Activity.class);
                   context.startActivity(intent);
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
