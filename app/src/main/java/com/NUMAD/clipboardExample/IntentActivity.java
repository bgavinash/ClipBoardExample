package com.NUMAD.clipboardExample;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static android.content.ClipDescription.MIMETYPE_TEXT_INTENT;
import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

public class IntentActivity extends AppCompatActivity {

    ClipboardManager clipboard;
    Intent pasteItem;

    Button pasteButton,launchButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        pasteButton = findViewById(R.id.pasteTextButton);
        launchButton = findViewById(R.id.launchIntentButton);
        // If the clipboard doesn't contain data, disable the paste menu item.
        // If it does contain data, decide if you can handle the data.
        changePasteButtonVisibility();
        changeLlaunchButtonVisiblility();
    }

    private void changePasteButtonVisibility(){
        if (!(clipboard.hasPrimaryClip())) {

            pasteButton.setEnabled(false);

        } else if (!(clipboard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_INTENT))) {

            // This disables the paste menu item, since the clipboard has data but it is not plain text
            pasteButton.setEnabled(false);
        } else {
            // Checks to see if the clip item contains an Intent, by testing to see if getIntent() returns null
            Intent pasteIntent = clipboard.getPrimaryClip().getItemAt(0).getIntent();
            // This enables the paste menu item, since the clipboard contains plain text.
            if(pasteIntent != null) {
                pasteButton.setEnabled(true);
            }
        }
    }

    private void changeLlaunchButtonVisiblility(){
        launchButton.setEnabled(pasteItem!=null);
    }
    public void copyText(View view) {

        Intent appIntent = new Intent(this, com.NUMAD.clipboardExample.MainActivity.class);
        ClipData clip = ClipData.newIntent("Intent", appIntent);
        clipboard.setPrimaryClip(clip);
        changePasteButtonVisibility();

    }

    public void pasteText(View view) {
        //Since the button is enabled only when intent is present on the clipboard,we can directly
        // paste the items into paste Item
        pasteItem = clipboard.getPrimaryClip().getItemAt(0).getIntent();
        changeLlaunchButtonVisiblility();
    }


    public void launchIntent(View view) {
        //Clear the back stack when launching, otherwise back button behaviour will be not as expected.
        //i.e. Since we are launching the main activity here, this activity if in back stack, back button on main screen will
        // lead back to this screen, which is un intended
        pasteItem.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(pasteItem);
    }
}
