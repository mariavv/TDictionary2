package com.example.maria.tdictionary;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Xml;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;



import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;

import static android.R.attr.key;
import static android.R.attr.textColorHighlight;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //trnsl.1.1.20170407T152949Z.0894cd90fd0e6185.48c3729b8cbab51fbb1001084f42cb8ae4d9a9b8

    //https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20170407T152949Z.0894cd90fd0e6185.48c3729b8cbab51fbb1001084f42cb8ae4d9a9b8&text="school"&lang=en-fr

    //https://translate.yandex.net/api/v1.5/tr.json/getLangs?key=trnsl.1.1.20170407T152949Z.0894cd90fd0e6185.48c3729b8cbab51fbb1001084f42cb8ae4d9a9b8&ui=ru

    Button btnSrcTextLang;
    Button btnTranslatedLang;
    Button btnTranslate;
    TextView editText;
    TextView resultText;

    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSrcTextLang = (Button) findViewById(R.id.btnSrcTextLang);
        btnTranslatedLang = (Button) findViewById(R.id.btnTranslatedLang);
        btnTranslate = (Button) findViewById(R.id.btnTranslate);
        editText = (TextView) findViewById(R.id.editText);
        resultText = (TextView) findViewById(R.id.resultText);

        TextWatcherP inputTextWatcher= new TextWatcherP(editText);
        editText.addTextChangedListener(inputTextWatcher);


        btnSrcTextLang.setOnClickListener(this);
        btnTranslatedLang.setOnClickListener(this);
        btnTranslate.setOnClickListener(this);

        resultText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                return false;
            }
        });

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return false;
            }
        });
    }

    public class TextWatcherP implements android.text.TextWatcher {

        public TextView t;

        public TextWatcherP(TextView et){
            super();
            t = et;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTranslatedLang:
                Intent intent = new Intent(this, ActivityTextLang.class);
                startActivity(intent);
                break;
            case R.id.btnTranslate:
                if (editText.getText().length() != 0) {
                    new AsyncRequest().execute();
                }
                break;
            case R.id.btnSrcTextLang:
                break;
            default:
                break;
        }
    }

    class AsyncRequest extends AsyncTask<Void, Void, Void> {

        String result = "";
        String send = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                send = "https://translate.yandex.net/api/v1.5/tr.json/translate?" +
                        "key=trnsl.1.1.20170407T152949Z.0894cd90fd0e6185.48c3729b8cbab51fbb1001084f42cb8ae4d9a9b8"
                        + "&text=" + URLEncoder.encode(editText.getText().toString(), "UTF-8") +
                        "&lang=ru-en"
                        + "&options=1";
            } catch (Exception e) {
                System.out.println("\nОШИБКА:" + e.toString() + "\n");
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL(send);
                URLConnection uc = url.openConnection();

                InputStream in = uc.getInputStream();
                int ch;
                String text_ = "";

                try {
                    while ((ch = in.read()) != -1) {
                        text_ += (char) ch;
                    }

                    BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

                    StringBuilder responseStrBuilder = new StringBuilder();

                    //String inputStr;;
                    //while ((inputStr = streamReader.readLine()) != null)
                    //    responseStrBuilder.append(inputStr);

                    text_ = URLDecoder.decode(text_, "UTF-8");
                    responseStrBuilder.append(text_);
                    JSONObject jsonObj = new JSONObject(responseStrBuilder.toString());
                    JSONArray lang =  jsonObj.getJSONArray("text");
                    result = lang.get(0).toString();
                } finally {
                    try {
                        in.close();
                    } catch (IOException ex) {
                        System.err.println(ex);
                    }
                }
            }
            catch(Exception e)
            {
                System.out.println("\nОШИБКА:\n"+e.toString()+"\n");
            }
            finally
            {
                return null;
            }
        }

        //@Override
        protected void onPostExecute(Void res) {
            //super.onPostExecute();
            try {
                resultText.setText(result);
            } catch (Exception e) {
                System.out.println("\nОШИБКА:\n"+e.toString()+"\n");
            }
        }
    }

}
