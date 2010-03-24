package com.grub.svg4mobile;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Camera;
import android.os.Bundle;
import android.view.View;

public class Svg4mobile extends Activity {
	Svg4mobileView view;
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = new Svg4mobileView(this);
		setContentView(view);
	}

}
