package sahil.clickclean.Views.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
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
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import sahil.clickclean.PrefManager;
import sahil.clickclean.R;
import sahil.clickclean.Views.DonateClothes;
import sahil.clickclean.Views.LoginActivity;
import sahil.clickclean.Views.MainActivity;
import sahil.clickclean.WelcomeActivity;

public class HomeFragment extends Fragment {

    Timer timer = new Timer();
    View view;
    private ViewPager viewPager;
    private ViewPager donationViewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private MyDonationViewPagerAdapter donationOfferViewPagerAdapter;
    private LinearLayout dotsLayout,donationDotsLayout;
    private TextView[] dots,donation_dots;
    private int[] layouts;
    SliderTimer sliderTimer =  new SliderTimer();
    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<String> donation_images = new ArrayList<>();

    public HomeFragment(){

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getAllImages();
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

        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        donationViewPager = view.findViewById(R.id.donation_view_pager);
        dotsLayout = (LinearLayout) view.findViewById(R.id.layoutDots);
        donationDotsLayout = view.findViewById(R.id.donationlayoutDots);


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

        donationOfferViewPagerAdapter = new MyDonationViewPagerAdapter();
        donationViewPager.setAdapter(donationOfferViewPagerAdapter);
        donationViewPager.addOnPageChangeListener(donationViewPagerChangeListener);
        donationViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }


    private void addBottomDots(int currentPage) {
        dots = new TextView[images.size()];

        int[] colorsActive = getContext().getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getContext().getResources().getIntArray(R.array.array_dot_inactive);

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


    private void addBottomDotstoOfferPager(int currentPage) {
        donation_dots = new TextView[donation_images.size()];

        int[] colorsActive = getContext().getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getContext().getResources().getIntArray(R.array.array_dot_inactive);

        donationDotsLayout.removeAllViews();
        for (int i = 0; i < donation_images.size(); i++) {
            donation_dots[i] = new TextView(getContext());
            donation_dots[i].setText(Html.fromHtml("&#8226;"));
            donation_dots[i].setTextSize(35);
            donation_dots[i].setTextColor(getResources().getColor(R.color.white));
            donationDotsLayout.addView(donation_dots[i]);
        }

        if (donation_dots.length > 0)
            donation_dots[currentPage].setTextColor(getResources().getColor(R.color.colorPrimary));
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

    //  viewpager change listener
    ViewPager.OnPageChangeListener donationViewPagerChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDotstoOfferPager(position);
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
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.viewpager_image, container, false);
            ImageView imageView= view.findViewById(R.id.imageOffer);
            Glide.with(getContext()).load(images.get(position)).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(view);

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





    /**
     * View pager adapter
     */
    public class MyDonationViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        MyDonationViewPagerAdapter() {
        }

        @NonNull
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.viewpager_image, container, false);
            ImageView imageView= view.findViewById(R.id.imageOffer);
            Glide.with(getContext()).load(donation_images.get(position)).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return donation_images.size();
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

                        if(donation_images.size()>0){
                            if (donationViewPager.getCurrentItem() < donation_dots.length - 1) {
                                donationViewPager.setCurrentItem(donationViewPager.getCurrentItem() + 1);
                            } else {
                                donationViewPager.setCurrentItem(0);
                            }
                        }






                    }
                });
            }


    }

    public void getAllImages(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("donation_image");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String prevChildKey) {
//                Post newPost = dataSnapshot.getValue(Post.class);
                Log.e("fasfdsfsfsa",dataSnapshot.toString());
                donation_images.add(dataSnapshot.getValue().toString());
                donationOfferViewPagerAdapter.notifyDataSetChanged();
                addBottomDotstoOfferPager(0);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
               addBottomDotstoOfferPager(0);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
              donation_images.remove(dataSnapshot.getValue().toString());
                donationOfferViewPagerAdapter.notifyDataSetChanged();

                addBottomDotstoOfferPager(0);

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
                addBottomDotstoOfferPager(0);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });




        DatabaseReference refs = FirebaseDatabase.getInstance().getReference("image");
        refs.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String prevChildKey) {
//                Post newPost = dataSnapshot.getValue(Post.class);
                Log.e("fasfdsfsfsa",dataSnapshot.toString());
                images.add(dataSnapshot.getValue().toString());
                myViewPagerAdapter.notifyDataSetChanged();
                addBottomDots(0);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                addBottomDots(0);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                images.remove(dataSnapshot.getValue().toString());
                myViewPagerAdapter.notifyDataSetChanged();

                addBottomDots(0);

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
                addBottomDots(0);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

}
