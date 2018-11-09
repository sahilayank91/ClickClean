package sahil.clickclean;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;

import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifDrawable;
import sahil.clickclean.model.RateCard;

public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3500;
    ImageView logoView;
    private static ArrayList<RateCard> listRateCard = new ArrayList<>();

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logoView = findViewById(R.id.imgLogo);
        GifDrawable gifDrawable = null;
        try {
            gifDrawable = new GifDrawable(getResources(), R.drawable.splash_gif);

//            gifDrawable = new GifDrawable( getAssets(), "splash_gif.gif" );
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (gifDrawable != null) {


            gifDrawable.addAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationCompleted(int loopNumber) {
                    Log.d("splashscreen", "Gif animation completed");
                    Intent i = new Intent(SplashScreen.this, WelcomeActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }
        logoView.setImageDrawable(gifDrawable);

//        getRateDetails();
//        new Handler().postDelayed(new Runnable() {
//
//            /*
//             * Showing splash screen with a timer. This will be useful when you
//             * want to show case your app logo / company
//             */
//
//            @Override
//            public void run() {
//                // This method will be executed once the timer is over
//                // Start your app main activity
//                Intent i = new Intent(SplashScreen.this, WelcomeActivity.class);
//                startActivity(i);
//
//                // close this activity
//                finish();
//            }
//        }, SPLASH_TIME_OUT);
    }



//    public void getRateDetails(){
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("clothes");
//        ref.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String prevChildKey) {
////                Post newPost = dataSnapshot.getValue(Post.class);
//                Log.e("fasfdsfsfsa",dataSnapshot.toString());
//                Cloth cloth = new Cloth();
//                cloth.setCloth(dataSnapshot.getKey());
//                if(dataSnapshot.hasChild("Wash and Iron")){
//                    cloth.setWashandiron(Long.parseLong(dataSnapshot.child("Wash and Iron").getValue().toString()));
//
//                }
//                if(dataSnapshot.hasChild("Wash")){
//                    cloth.setWash(Long.parseLong(dataSnapshot.child("Wash").getValue().toString()));
//                }
////
////
//                if(dataSnapshot.hasChild("Iron")){
//                    cloth.setIron(Long.parseLong(dataSnapshot.child("Iron").getValue().toString()));
//                }
//                listRateCard.add(cloth);
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {}
//        });
//
//    }


}