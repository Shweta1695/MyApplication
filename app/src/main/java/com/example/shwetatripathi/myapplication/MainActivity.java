package com.example.shwetatripathi.myapplication;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shwetatripathi.myapplication.model.Blog;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();

    // Movies json url
    //private static final String url = "https://54.242.83.222:8080/MakeMyTrip/mmt/blog/seeAllBlogs";
    private ProgressDialog pDialog;
    private List<Blog> movieList = new ArrayList<Blog>();
    String baseUrl = "http://54.242.83.222:8080/MakeMyTrip/mmt/blog/seeAllBlogs";  // This is the API base URL (GitHub API)
    String url;  // This will hold the full URL which will include the username entered in the etGitHubUser.
   private ListView listView;
    EditText etGitHubUser; // This will be a reference to our GitHub username input.
    Button btnGetRepos;  // This is a reference to the "Get Repos" button.
    TextView tvRepoList;  // This will reference our repo list text box.
    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


            this.etGitHubUser = (EditText) findViewById(R.id.et_github_user);  // Link our github user text box.
            this.btnGetRepos = (Button) findViewById(R.id.btn_get_repos);  // Link our clicky button.
            this.tvRepoList = (TextView) findViewById(R.id.tv_repo_list);  // Link our repository list text output box.
            this.tvRepoList.setMovementMethod(new ScrollingMovementMethod());  // This makes our text box scrollable, for those big GitHub contributors with lots of repos :)

            requestQueue = Volley.newRequestQueue(this);  // This setups up a new request queue which we will need to make HTTP requests.
        }

        private void clearRepoList() {
            // This will clear the repo list (set it as a blank string).
            this.tvRepoList.setText("");
        }

        private void addToRepoList(String repoName, String lastUpdated) {
            // This will add a new repo to our list.
            // It combines the repoName and lastUpdated strings together.
            // And then adds them followed by a new line (\n\n make two new lines).
            String strRow = repoName + " / " + lastUpdated;
            String currentText = tvRepoList.getText().toString();
            this.tvRepoList.setText(currentText + "\n\n" + strRow);
        }

        private void setRepoListText(String str) {
            // This is used for setting the text of our repo list box to a specific string.
            // We will use this to write a "No repos found" message if the user doens't have any.
            this.tvRepoList.setText(str);
        }

        private void getRepoList(String username) {
            // First, we insert the username into the repo url.
            // The repo url is defined in GitHubs API docs (https://developer.github.com/v3/repos/).


            // Next, we create a new JsonArrayRequest. This will use Volley to make a HTTP request
            // that expects a JSON Array Response.
            // To fully understand this, I'd recommend readng the office docs: https://developer.android.com/training/volley/index.html

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, baseUrl, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            //mTextView.setText("Response: " + response.toString());
                            Log.d("JSON","RESULT IS  "+ response.toString());
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error

                        }
                    });


            // Add the request we just defined to our request queue.
            // The request queue will automatically handle the request as soon as it can.
            requestQueue.add(jsonObjectRequest);
        }
        public void getReposClicked(View v) {
            // Clear the repo list (so we have a fresh screen to add to)
            clearRepoList();
            // Call our getRepoList() function that is defined above and pass in the
            // text which has been entered into the etGitHubUser text input field.
            getRepoList(etGitHubUser.getText().toString());
        }

    }

