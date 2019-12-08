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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

public class TextCopyActivity extends AppCompatActivity {

    ClipboardManager clipboard;
    String pasteText;

    Button pasteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_copy);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        pasteButton = findViewById(R.id.pasteTextButton);
        // If the clipboard doesn't contain data, disable the paste menu item.
// If it does contain data, decide if you can handle the data.
        changePasteButtonVisibility();

    }

    private void changePasteButtonVisibility(){
        if (!(clipboard.hasPrimaryClip())) {

            pasteButton.setEnabled(false);

        } else if (!(clipboard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN))) {

            // This disables the paste menu item, since the clipboard has data but it is not plain text
            pasteButton.setEnabled(false);
        } else {

            // This enables the paste menu item, since the clipboard contains plain text.
            pasteButton.setEnabled(true);
        }
    }

    public void copyText(View view) {

        EditText et = view.getRootView().findViewById(R.id.sourceEditText);
        String textToCopy = et.getText().toString();
        ClipData clip = ClipData.newPlainText("simple text", textToCopy);

        // Set the clipboard's primary clip.
        clipboard.setPrimaryClip(clip);

        changePasteButtonVisibility();

    }

    public void pasteText(View view) {
        ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
        if(item!= null) {
            pasteText = item.getText().toString();
            TextView tv = view.getRootView().findViewById(R.id.destinationTextview);
            tv.setText(pasteText);
        }
    }


}
