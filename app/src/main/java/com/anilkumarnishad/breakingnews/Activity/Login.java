package com.anilkumarnishad.breakingnews.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anilkumarnishad.breakingnews.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.squareup.picasso.Picasso;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private String TAG = "MainActivity";
    private FirebaseAuth mfirebaseauth;
    private Button btnSignOut;
    private int RC_SIGN_IN = 1;
    Button skip;
    TextView txt_news_top, txt_news_sport, txt_news_technology;
    String topNews, sportNews, technologyNews, healthNews, intertenmentNews, scienceNews;
    LinearLayout top_linearLayout, sport_linearLayout, tech_linearLayout;

    private CallbackManager mcallbackManager;
    private FirebaseAuth.AuthStateListener authStateListener;
    private AccessTokenTracker accessTokenTracker;
    TextView textViewUser;
    ImageView imageView;
    private LoginButton loginButton;
    private static final String TAGG = "FacebaookAuthentication";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_news_top = findViewById(R.id.txt_news_top);
        txt_news_sport = findViewById(R.id.txt_news_sport);
      //  txt_news_technology = findViewById(R.id.txt_news_technology);

        topNews = txt_news_top.getText().toString();
        sportNews = txt_news_sport.getText().toString();
  //      technologyNews = txt_news_technology.getText().toString();

        top_linearLayout = findViewById(R.id.top_linearLayout);
        sport_linearLayout = findViewById(R.id.sport_linearLayout);
     //   tech_linearLayout = findViewById(R.id.tech_linearLayout);

        top_linearLayout.setOnClickListener(this);
        sport_linearLayout.setOnClickListener(this);
    //    tech_linearLayout.setOnClickListener(this);

        skip = findViewById(R.id.skip);
        signInButton = findViewById(R.id.sign_in_btn);
        btnSignOut = findViewById(R.id.sign_out_btn);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mfirebaseauth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        textViewUser = findViewById(R.id.username);
        imageView = findViewById(R.id.userImage);
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        mcallbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(mcallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess" + loginResult);

                handleFacebookToken(loginResult.getAccessToken());
            }
            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel");
            }
            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "onSuccess" + error);
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    updateUI(user);
                } else {
                    updateUI(null);
                }
            }
        };

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    mfirebaseauth.signOut();
                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleSignInClient.signOut();
                Toast.makeText(Login.this, "Your are logout", Toast.LENGTH_SHORT).show();
                btnSignOut.setVisibility(View.GONE);
            }
        });
    }

    private void  handleFacebookToken(AccessToken token) {
        Log.d(TAG, "onSuccess" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mfirebaseauth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, " sign in creadential is successfull");
                    FirebaseUser user = mfirebaseauth.getCurrentUser();
                    updateUII(user);
                } else {
                    Log.d(TAG, " sign in creadential is failur" , task.getException());
                    Toast.makeText(Login.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    updateUII(null);
                }
            }
        });
    }

    private  void  updateUII(FirebaseUser user){
        if(user != null){
            textViewUser.setText(user.getDisplayName());
            if(user.getPhotoUrl() != null){
                String photoUrl = user.getPhotoUrl().toString();
                photoUrl = photoUrl + "?typelarge";
                Picasso.get().load(photoUrl).into(imageView);
            }
        }
        else{
            textViewUser.setText("");
            imageView.setImageResource(R.drawable.google);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mfirebaseauth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener != null){
            mfirebaseauth.addAuthStateListener(authStateListener);

        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mcallbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount accoutn = completedTask.getResult(ApiException.class);
            Toast.makeText(this, "Sign in Successfully", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(accoutn);
        } catch (ApiException e) {
            Toast.makeText(this, "Sign in Failed", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
            e.printStackTrace();
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acct){
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mfirebaseauth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mfirebaseauth.getCurrentUser();
                            Toast.makeText(Login.this, "User Signed In", Toast.LENGTH_SHORT).show();
                            updateUI (user);
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI (null);
                        }
                    }
                });
       }

    private void updateUI (FirebaseUser firebaseUser){
        btnSignOut.setVisibility(View.GONE);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(account != null){
            String personeName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail= account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();

            SharedPreferences sharedPreferences = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("USERNAME", personeName );
            editor.putString("USERGIVENNAME", personGivenName );
            editor.putString("USERFAMILYNAME", personFamilyName );
            editor.putString("USEREMAIL", personEmail );
            editor.putString("USERID", personId );
            editor.putString("USERPHOTO", String.valueOf(personPhoto));

            editor.commit();
        }
    }


    @Override
    public void onClick(View view) {
        if (view==top_linearLayout){
            selectNews(top_linearLayout,sport_linearLayout,tech_linearLayout);
            SharedPreferences sharedPreferences = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("topNews", topNews );
            editor.commit();
        }
        if (view==sport_linearLayout){
            selectNews(sport_linearLayout,top_linearLayout,tech_linearLayout);
            SharedPreferences sharedPreferences = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("topNews", sportNews );
            editor.commit();
        }
        if (view==tech_linearLayout){
            selectNews(tech_linearLayout,sport_linearLayout,top_linearLayout);
            SharedPreferences sharedPreferences = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("topNews", technologyNews );
            editor.commit();
        }

    }

   public void selectNews(LinearLayout layout1, LinearLayout layout2, LinearLayout layout3 ){
       LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) layout1.getLayoutParams();
       layout1.setLayoutParams(params1);
       LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) layout2.getLayoutParams();
       layout2.setLayoutParams(params2);
       LinearLayout.LayoutParams params3 = (LinearLayout.LayoutParams) layout3.getLayoutParams();
       layout3.setLayoutParams(params3);

       layout1.setBackground(Login.this.getResources().getDrawable(R.drawable.gradienttab_yellow));
       layout2.setBackground(Login.this.getResources().getDrawable(R.drawable.gradient_tab));
       layout3.setBackground(Login.this.getResources().getDrawable(R.drawable.gradient_tab));

   }
}