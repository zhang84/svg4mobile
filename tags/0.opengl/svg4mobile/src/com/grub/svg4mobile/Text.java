package com.grub.svg4mobile;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.opengl.GLUtils;

public class Text extends Figure {
  
  private String colorString;
  private Paint mLabelPaint;
  private float x,y;
  private float twidth, theight;
  private int mTextureID;
  
  
  private int mStrikeWidth = 256;
  private int mStrikeHeight = 64;
  private boolean mFullColor = true;
  private Bitmap mBitmap;
  private Canvas mCanvas;
  private Paint mClearPaint;
  private String text = "texTo de prueba"; 
  private int mU;
  private int mV;
  private int mLineHeight;
  private int[] mCrop = new int[4];
  
  public Text(float x, float y, int size, String rgb) {
    
    mClearPaint = new Paint();
    mClearPaint.setARGB(0, 0, 0, 0);
    mClearPaint.setStyle(Style.FILL);
    
    this.colorString = rgb;
    this.x = x;
    this.y = y;
    mLabelPaint = new Paint();
    mLabelPaint.setTextSize(size);
    mLabelPaint.setAntiAlias(true);
    
    //Asigna color
    int c = Color.parseColor(this.colorString);
    mLabelPaint.setARGB(255, Color.red(c), Color.green(c), Color.blue(c));
    
    
    mU = 0;
    mV = 0;
    mLineHeight = 0;
    Bitmap.Config config = mFullColor ? 
    Bitmap.Config.ARGB_4444 : Bitmap.Config.ALPHA_8;
    mBitmap = Bitmap.createBitmap(mStrikeWidth, mStrikeHeight, config);
    mCanvas = new Canvas(mBitmap);
    mBitmap.eraseColor(0);
    Rect padding = new Rect();
    
    
    int ascent = 0;
    int descent = 0;
    int measuredTextWidth = 0;
    
    
    // Paint.ascent is negative, so negate it.
    ascent = (int) Math.ceil(-mLabelPaint.ascent());
    descent = (int) Math.ceil(mLabelPaint.descent());
    measuredTextWidth = (int) Math.ceil(mLabelPaint.measureText(text));
    
    int textHeight = ascent + descent;
    int textWidth = Math.min(mStrikeWidth,measuredTextWidth);
    
    int padHeight = padding.top + padding.bottom;
    int padWidth = padding.left + padding.right;
    int height = textHeight + padHeight;
    int width = textWidth + padWidth;
    int effectiveTextHeight = height - padHeight;
    int effectiveTextWidth = width - padWidth;
    
    int centerOffsetHeight = (effectiveTextHeight - textHeight) / 2;
    int centerOffsetWidth = (effectiveTextWidth - textWidth) / 2;
    
    // Make changes to the local variables, only commit them
    // to the member variables after we've decided not to throw
    // any exceptions.
    
    int u = mU;
    int v = mV;
    int lineHeight = mLineHeight;
    
    if (width > mStrikeWidth) {
      width = mStrikeWidth;
    }
    
    // Is there room for this string on the current line?
    if (u + width > mStrikeWidth) {
      // No room, go to the next line:
      u = 0;
      v += lineHeight;
      lineHeight = 0;
    }
    lineHeight = Math.max(lineHeight, height);
    if (v + lineHeight > mStrikeHeight) {
      throw new IllegalArgumentException("Fuera del espacio de la textura.");
    }
    
    int vBase = v + ascent;
    
    mCanvas.drawText(text, u + padding.left + centerOffsetWidth, vBase + padding.top + centerOffsetHeight, mLabelPaint);
    
    // We know there's enough space, so update the member variables
    mU = u + width;
    mV = v;
    mLineHeight = lineHeight;
    mCrop[0] = u;
    mCrop[1] = v + height;
    mCrop[2] = width;
    mCrop[3] = -height;
    
    twidth = width;
    theight = height;
  }
  /**
  * Función que pinta la figura
  * @param gl
  */
  public void draw(GL10 gl) {     
    
    int[] textures = new int[1];
    gl.glGenTextures(1, textures, 0);
    mTextureID = textures[0];
    gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID);
    
    // Use Nearest for performance.
    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
    
    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
    
    gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_REPLACE);
    

    gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID);
    GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, mBitmap, 0);
    
    //gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID);
    gl.glShadeModel(GL10.GL_FLAT);
    gl.glEnable(GL10.GL_BLEND);
    gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
    gl.glColor4x(0x10000, 0x10000, 0x10000, 0x10000);
    
    gl.glMatrixMode(GL10.GL_PROJECTION);
    gl.glPushMatrix();
    gl.glLoadIdentity();
    
    gl.glMatrixMode(GL10.GL_MODELVIEW);
    gl.glPushMatrix();
    gl.glLoadIdentity();
    // Magic offsets to promote consistent rasterization.
    gl.glTranslatef(0.375f, 0.375f, 0.0f);
    
    gl.glPushMatrix();
    float snappedX = (float) Math.floor(x);
    float snappedY = (float) Math.floor(y);
    gl.glTranslatef(snappedX, snappedY, 0.0f);
    
    gl.glEnable(GL10.GL_TEXTURE_2D);
    ((GL11)gl).glTexParameteriv(GL10.GL_TEXTURE_2D, GL11Ext.GL_TEXTURE_CROP_RECT_OES, mCrop, 0);
    //gl.glRotatef(45, 0, 0, 1.0f);
    ((GL11Ext)gl).glDrawTexiOES((int) snappedX, (int) snappedY, 0, (int) twidth, (int) theight);
    gl.glPopMatrix();
    
    gl.glDisable(GL10.GL_BLEND);
    gl.glMatrixMode(GL10.GL_PROJECTION);
    gl.glPopMatrix();
    gl.glMatrixMode(GL10.GL_MODELVIEW);
    gl.glPopMatrix();
    
    //Desactiva algunos parametros de opengl para no interferir con el resto de figuras
    //gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    gl.glDisable(GL10.GL_TEXTURE_2D);
    
    
  }
  
  public void destructor() {
	// Reclaim storage used by bitmap and canvas.
	mBitmap.recycle();
	mBitmap = null;
	mCanvas = null;
  }
}
