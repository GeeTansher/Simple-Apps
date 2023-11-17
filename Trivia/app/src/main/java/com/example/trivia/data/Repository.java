package com.example.trivia.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.trivia.controller.AppController;
import com.example.trivia.model.Question;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    public final ArrayList<Question> questionArrayList = new ArrayList<>();
    String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    public List<Question> getQuestions(final AnsListAsyncResponse callBack) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    Question question = new Question(response.getJSONArray(i).getString(0),
                            response.getJSONArray(i).getBoolean(1));
                    questionArrayList.add(question);
//                    Log.d("Hi", questionArrayList.get(i).toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
//            Log.d("Hello", questionArrayList.get(3).getStatement() + " ");
            if (callBack != null) {
                callBack.processFinished(questionArrayList);
            }
//            Log.d("Hello", questionArrayList.get(3).getStatement() + " ");

        }, error -> {

        });
//        Log.d("Hello", questionArrayList.get(3).getStatement() + " ");


        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        return questionArrayList;
    }
}
