package sahil.clickclean.Views.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import sahil.clickclean.R;
import sahil.clickclean.Views.MainActivity;

public class HomeFragment extends Fragment {

    private float startX;
    private ViewFlipper vf;

    View view;

    public HomeFragment(){

    }
    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) view = inflater.inflate(R.layout.fragment_home, container, false);
        else return view;

        vf = view.findViewById(R.id.viewFlipper);

        Animation imgAnimationIn = AnimationUtils.
                loadAnimation(getContext(), android.R.anim.slide_in_left);
        imgAnimationIn.setDuration(700);
        vf.setInAnimation(imgAnimationIn);

        Animation imgAnimationOut = AnimationUtils.
                loadAnimation(getContext(), android.R.anim.slide_out_right);
        imgAnimationOut.setDuration(700);
        vf.setOutAnimation(imgAnimationOut);

        vf.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getActionMasked();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        float endX = event.getX();
                        float endY = event.getY();

                        //swipe right
                        if (startX < endX) {
                            vf.showNext();
                        }

                        //swipe left
                        if (startX > endX) {
                            vf.showPrevious();
                        }

                        break;
                }
                return true;
            }
        });

        vf.startFlipping();

        return view;
    }
}
