package com.ceng319.externalstorage;
// The following code is referred from the website tutorial:
// https://www.journaldev.com/9400/android-external-storage-read-write-save-file
// Save and load the files to/from  the external drive.
import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {
    EditText inputText;
    TextView response;
    Button saveButton,readButton;

    // TODO 1: Declare the name and path of the files you want to use. finished.
    private String filename = "MapleLeaf.txt";
    private String filepath = "MapleLeaf";
    File myExternalFile;
    String myData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TODO 2: Get the inputText and response views. finished.
        findAllViews();

        // TODO 3: Make sure the file is available. Finished.
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            saveButton.setEnabled(false);
        } else {
            // TODO 4: Save this file onto the external storage directory. Check Gist finished
            // Create a directory under external storage directory.
            // Use getExternalFilesDir(filepath) to save it to APP private directory.
            File f = new File(String.valueOf(this.getExternalFilesDir(filepath)));
            if (!f.exists()) {
                f.mkdirs();
            }
            myExternalFile = new File(f.getPath(), filename);  // now myExternalFile will handle the file with filename under directory f.
        }

        // TODO 5: Program the Button to save the editText into a text file
        programSaveButton();

        // TODO 6: Program the Button to load the text file from storage
        programReadButton();

    }

    private void programReadButton() {
        readButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    myData = "";
                    FileInputStream fis = new FileInputStream(myExternalFile);
                    DataInputStream in = new DataInputStream(fis);
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String strLine;
                    while ((strLine = br.readLine()) != null) {
                        myData = myData + strLine;
                    }
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                inputText.setText(myData);
                response.setText("MapleLeaf.txt data retrieved from External Storage...");
            }
        });
    }

    private void programSaveButton() {
        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    FileOutputStream fos = new FileOutputStream(myExternalFile);
                    fos.write(inputText.getText().toString().getBytes());
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                inputText.setText("");
                response.setText("File MapleLeaf.txt saved!");
            }
        });
    }

    private void findAllViews(){
        inputText = findViewById(R.id.myInputText);
        response = findViewById(R.id.response);
        saveButton = findViewById(R.id.saveExternalStorage);
        readButton = findViewById(R.id.getExternalStorage);
    }

    // Check if the external storage is available.
    // This is a static method you can use for other APPs.
    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    // Check if the external storage is read only or can be written.
    // This is a static method you can use for other APPs.
    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }
}
