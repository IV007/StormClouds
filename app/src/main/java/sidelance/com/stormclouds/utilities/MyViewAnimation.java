package sidelance.com.stormclouds.utilities;

import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Custom class to handle dynamic view animation
 */
public class MyViewAnimation {



    public static void shortFadeIn(View view){

        YoYo.with(Techniques.FadeIn).duration(3000).playOn(view);
    }

    public static void longFadeIn(View view){

        YoYo.with(Techniques.FadeIn).duration(7000).playOn(view);
    }

    /////////////////////////////////////////////////////////////////////////////////
    // Slide
    // SlideInLeft, SlideInRight, SlideInUp, SlideInDown,
    // SlideOutLeft, SlideOutRight, SlideOutUp, SlideOutDown
    /////////////////////////////////////////////////////////////////////////////////

    public static void slideInRight(View view) {
        YoYo.with(Techniques.SlideInRight).duration(3000).playOn(view);
    }

    public static void slideInLeft(View view) {
        YoYo.with(Techniques.SlideInLeft).duration(2000).playOn(view);
    }

    public static void slideOutRight(View view) {
        YoYo.with(Techniques.SlideOutRight).duration(3000).playOn(view);
    }

    public static void slideOutLeft(View view) {
        YoYo.with(Techniques.SlideOutLeft).duration(2000).playOn(view);
    }


    public static void slideInUp(View view) {
        YoYo.with(Techniques.SlideInUp).duration(3000).playOn(view);
    }

    public static void slideInDown(View view) {
        YoYo.with(Techniques.SlideInDown).duration(2000).playOn(view);
    }

    public static void slideOutUp(View view) {
        YoYo.with(Techniques.SlideOutUp).duration(3000).playOn(view);
    }

    public static void slideOutdown(View view) {
        YoYo.with(Techniques.SlideOutDown).duration(2000).playOn(view);
    }

    /////////////////////////////////////////////////////////////////////////////////
    // Attention  Animation                                                                 //
    // Flash, Pulse, RubberBand, Shake, Swing, Wobble, Bounce, Tada, StandUp, Wave //
    /////////////////////////////////////////////////////////////////////////////////

    public static void flash(View view) {
        YoYo.with(Techniques.Flash).duration(2000).playOn(view);
    }

    public static void pulse(View view) {
        YoYo.with(Techniques.Pulse).duration(2000).playOn(view);
    }

    public static void rubberBand(View view) {
        YoYo.with(Techniques.RubberBand).duration(2000).playOn(view);
    }

    public static void shake(View view) {
        YoYo.with(Techniques.Shake).duration(2000).playOn(view);
    }

    public static void swing(View view) {
        YoYo.with(Techniques.Swing).duration(2000).playOn(view);
    }

    public static void wobble(View view) {
        YoYo.with(Techniques.Wobble).duration(2000).playOn(view);
    }

    public static void bounce(View view) {
        YoYo.with(Techniques.Bounce).duration(2000).playOn(view);
    }

    public static void tada(View view) {
        YoYo.with(Techniques.Tada).duration(2000).playOn(view);
    }

    public static void standUp(View view) {
        YoYo.with(Techniques.StandUp).duration(2000).playOn(view);
    }

    public static void wave(View view) {
        YoYo.with(Techniques.Wave).duration(2000).playOn(view);
    }



    /////////////////////////////////////////////

    public static void sequence1(View view1, View view2, View view3, View view4, View view5, View view6, View view7){
        YoYo.with(Techniques.FlipOutX).duration(2000).playOn(view1);
        YoYo.with(Techniques.FlipOutX).duration(3000).playOn(view2);
        YoYo.with(Techniques.FlipOutX).duration(4000).playOn(view3);
        YoYo.with(Techniques.FlipOutX).duration(5000).playOn(view4);
        YoYo.with(Techniques.FlipOutY).duration(6000).playOn(view5);
        YoYo.with(Techniques.StandUp).duration(4000).playOn(view6);
        YoYo.with(Techniques.Shake).duration(8000).playOn(view7);
    }

    public static void spinButtonAnimation(View view) {

        YoYo.with(Techniques.FadeOut).duration(3000).playOn(view);
        YoYo.with(Techniques.FlipInX).duration(4000).playOn(view);
        YoYo.with(Techniques.FadeIn).duration(5000).playOn(view);
        YoYo.with(Techniques.StandUp).duration(6000).playOn(view);

    }

    public static void fadeInAndBounceAnimation(View view){
        YoYo.with(Techniques.FadeIn).duration(7000).playOn(view);
        YoYo.with(Techniques.FadeOut).duration(8000).playOn(view);
        YoYo.with(Techniques.FadeIn).duration(9000).playOn(view);
        YoYo.with(Techniques.Bounce).duration(10000).playOn(view);
        YoYo.with(Techniques.Flash).duration(11000).playOn(view);


    }

    public static void  rotateButtonAnimation(View view){

        YoYo.with(Techniques.RotateIn).duration(6000).playOn(view).isRunning();
    }

}
