package com.NUMAD.clipboardExample;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class TextCopyActivity extends AppCompatActivity {

    ClipboardManager clipboard;
    String pasteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_copy);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);

    }

    public void copyText(View view) {

        EditText et = view.getRootView().findViewById(R.id.sourceEditText);
        String textToCopy = et.getText().toString();
        ClipData clip = ClipData.newPlainText("simple text", textToCopy);

        // Set the clipboard's primary clip.
        clipboard.setPrimaryClip(clip);

    }

    public void pasteText(View view) {
        ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
        pasteText = item.getText().toString();
        TextView tv = view.getRootView().findViewById(R.id.destinationTextview);
        tv.setText(pasteText);
    }


}
