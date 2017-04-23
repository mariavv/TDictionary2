package com.example.maria.tdictionary;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.codehaus.jackson.JsonParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static android.R.id.input;



public class ActivityTextLang extends AppCompatActivity {

    private String text = "";
    JSONObject jsonObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_lang);

        new AsyncRequest().execute();
    }

    class AsyncRequest extends AsyncTask<Void, Void, Void> {

        String result = "";
        String send = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                send = "https://translate.yandex.net/api/v1.5/tr.json/getLangs?key=trnsl.1.1.20170407T152949Z.0894cd90fd0e6185.48c3729b8cbab51fbb1001084f42cb8ae4d9a9b8&ui=ru";
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

                     //while ((ch = in.read()) != -1)
                     //{ text_ += (char) ch; }

                    /*int i = 0;
                    String langs = "*****";
                    while ((ch = in.read()) != -1) {
                        String c = "";
                        c += (char) ch;
                        langs += c;
                        langs = langs.substring(1);
                        i++;
                        if (i == 1290) {
                            i++;
                        }
                        if (langs.equals("langs")) {
                            System.out.println("\n343334343434343434:\n"  + "\n");
                            while ((ch = in.read()) != -1) {
                                if ( !c.equals("\"") ) {
                                    result += (char) ch;
                                }
                            }
                        }
                    }*/

                    //BufferedReader streamReader = new BufferedReader(new InputStreamReader(in/*, "UTF-8"*/));
                    StringBuilder responseStrBuilder = new StringBuilder();

                    /*String inputStr;
                    while ((inputStr = streamReader.readLine()) != null) {
                        responseStrBuilder.append(inputStr);
                    }*/

                    responseStrBuilder.append(result);
                } catch (Exception e) {
                    System.out.println("\nОШИБКА:\n" + e.toString() + "\n");
                } finally {
                    return null;
                }
        }

        //@Override
        protected void onPostExecute(Void res) {
            try {
                //editText.setText(result);
                //resultText.setText(result);
            } catch (Exception e) {
                System.out.println("\nОШИБКА:\n"+e.toString()+"\n");
            }
        }
    }


}
