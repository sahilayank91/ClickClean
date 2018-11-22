package sahil.clickclean.Views.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import sahil.clickclean.PrefManager;
import sahil.clickclean.R;
import sahil.clickclean.SharedPreferenceSingleton;
import sahil.clickclean.Views.DonateActivity;
import sahil.clickclean.Views.DonateClothes;
import sahil.clickclean.Views.LoginActivity;
import sahil.clickclean.Views.MainActivity;
import sahil.clickclean.Views.PickupActivity;
import sahil.clickclean.Views.ProfileActivity;
import sahil.clickclean.Views.RateCardActivity;
import sahil.clickclean.Views.RegisterWasherMan;
import sahil.clickclean.Views.SchedulePickup;
import sahil.clickclean.Views.TermsAndCondition;
import sahil.clickclean.Views.UploadImage;
import sahil.clickclean.Views.YourOrders;
import sahil.clickclean.WelcomeActivity;
import sahil.clickclean.model.Image;
import sahil.clickclean.model.Offer;
import sahil.clickclean.utilities.Server;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    Timer timer = new Timer();
    View view;
    private ViewPager viewPager;
    private ViewPager donationViewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout,donationDotsLayout;
    private TextView[] dots,donation_dots;
    private int[] layouts;
    SliderTimer sliderTimer =  new SliderTimer();
    private ArrayList<Image> images = new ArrayList<>();
    NestedScrollView nestedScrollView;
    CardView normal_steam,normal_wash_and_fold,normal_wash_and_iron;
    CardView express_steam,express_wash_and_fold,express_wash_and_iron,dryclean;
    public HomeFragment(){

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

//        timer.scheduleAtFixedRate(sliderTimer, 2000, 3000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.purge();
//        sliderTimer.cancel();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        timer.purge();
//        sliderTimer.cancel();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) view = inflater.inflate(R.layout.fragment_home, container, false);
        else return view;

        nestedScrollView = view.findViewById(R.id.scrollView);
        nestedScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                nestedScrollView.scrollTo(0,(int)view.findViewById(R.id.normal_wash_and_iron).getY());
            }
        },100);
        viewPager = view.findViewById(R.id.view_pager);
//        donationViewPager = view.findViewById(R.id.donation_view_pager);
        dotsLayout = view.findViewById(R.id.layoutDots);
//        donationDotsLayout = view.findViewById(R.id.donationlayoutDots);


        new getImages().execute();

        normal_steam = view.findViewById(R.id.normal_steam);
        normal_wash_and_fold = view.findViewById(R.id.normal_wash_and_fold);
        normal_wash_and_iron = view.findViewById(R.id.normal_wash_and_iron);
        express_steam = view.findViewById(R.id.express_steam);
        express_wash_and_fold = view.findViewById(R.id.express_wash_and_fold);
        express_wash_and_iron = view.findViewById(R.id.express_wash_and_iron);
        dryclean = view.findViewById(R.id.dryclean);

        final Intent intent = new Intent(getActivity(), SchedulePickup.class);

        normal_steam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("service","Ironing");
                intent.putExtra("type","Normal");
                intent.putExtra("percentage","0");

                startActivity(intent);
            }
        });

        normal_wash_and_fold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("service","Wash and Fold");
                intent.putExtra("type","Normal");
                intent.putExtra("percentage","0");

                startActivity(intent);
            }
        });

        normal_wash_and_iron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("service","Wash and Iron");
                intent.putExtra("type","Normal");
                intent.putExtra("percentage","0");
                startActivity(intent);

            }
        });


        express_steam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("service","Ironing");
                intent.putExtra("type","Express");
                intent.putExtra("percentage","0");

                startActivity(intent);

            }
        });

        express_wash_and_iron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("service","Wash and Iron");
                intent.putExtra("type","Express");
                intent.putExtra("percentage","0");

                startActivity(intent);

            }
        });

        express_wash_and_fold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("service","Wash and Fold");
                intent.putExtra("type","Express");
                intent.putExtra("percentage","0");

                startActivity(intent);

            }
        });

        dryclean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("service","Dryclean");
                intent.putExtra("type","Normal");
                intent.putExtra("percentage","0");

                startActivity(intent);
            }

        });


        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcome_slide4,
                R.layout.welcome_slide5,
                R.layout.welcome_slide6};


        // making notification bar transparent
        changeStatusBarColor();
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);


        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }


    private void addBottomDots(int currentPage) {
        if(!images.isEmpty()) {
            dots = new TextView[images.size()];
            dotsLayout.removeAllViews();
            for (int i = 0; i < images.size(); i++) {
                dots[i] = new TextView(getContext());
                dots[i].setText(Html.fromHtml("&#8226;"));
                dots[i].setTextSize(35);
                dots[i].setTextColor(getResources().getColor(R.color.white));
                dotsLayout.addView(dots[i]);
            }

            if (dots.length > 0)
                dots[currentPage].setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }



    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };


    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }



    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        MyViewPagerAdapter() {
        }

        @NonNull
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.viewpager_image, container, false);
            ImageView imageView= view.findViewById(R.id.imageOffer);
//            Glide.with(getContext()).load(images.get(position)).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
            Glide.with(getContext()).load(images.get(position).getUrl()).apply(new RequestOptions().placeholder(R.drawable.placeholder_image).fitCenter()).into(imageView);

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);


            container.addView(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(images.get(position).getType().equals("Offer")){
                        OfferFragment offerFragment = new OfferFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right).replace(R.id.main_container,offerFragment).commit();

                    }else{
                        Intent intent = new Intent(getContext(),DonateActivity.class);
                        startActivity(intent);

                    }

                }
            });


            return view;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }






    private class SliderTimer extends TimerTask {

        @Override
        public boolean cancel() {
            return super.cancel();
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(images.size()>0){
                            if (viewPager.getCurrentItem() < dots.length - 1) {
                                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                            } else {
                                viewPager.setCurrentItem(0);
                            }
                        }

                    }
                });
            }


    }



    @SuppressLint("StaticFieldLeak")
    class getImages extends AsyncTask<String, String, String> {
        boolean success = false;
        HashMap<String, String> params = new HashMap<>();



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (success) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONArray jsonArray = null;
                try {
                    jsonArray = jsonObject.getJSONArray("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                images.clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject post = null;
                    try {
                        post = jsonArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Image current = null;
                    try {
                        current = new Image(post);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    images.add(current);
                }
                myViewPagerAdapter.notifyDataSetChanged();


            } else {
                Toast.makeText(getContext(), R.string.error, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            try {
                Gson gson = new Gson();
                String json = gson.toJson(params);
                result = Server.post(getResources().getString(R.string.getImages),json);
                success = true;
            } catch (Exception e){
                e.printStackTrace();
            }

            System.out.println("Result:" + result);
            return result;
        }
    }

}
