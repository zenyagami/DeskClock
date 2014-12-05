package com.android.deskclock.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.android.deskclock.R;
import com.android.deskclock.Utils;


/**
 * Created by zenkun 11/26/14.
 */
public class FabButton extends FrameLayout implements View.OnClickListener {
    private ImageButton mFab;
    private OnClickListener onClickListener;
    public interface customClick
    {
        public void onClick(View view);
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public void setImageResource(int resourceId)
    {
        if(mFab==null || resourceId==0)return;
        mFab.setImageResource(resourceId);

    }

    public FabButton(Context context) {
        super(context);
        init(context,null);
    }

    public FabButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public FabButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

   @TargetApi(Build.VERSION_CODES.LOLLIPOP)
   private void init(Context context,AttributeSet attrs)
   {
       mFab = (ImageButton)  ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.fab_button,this,false);
       mFab.setOnClickListener(this);
       if(Utils.isLP())
       {
           mFab.setOutlineProvider(new ViewOutlineProvider() {
               @Override
               public void getOutline(View view, Outline outline) {
                   outline.setOval(0, 0, view.getWidth(), view.getHeight());
               }
           });
           mFab.setTranslationZ(20f);
       }else
       {

       }
       if(attrs!=null)
       {
           TypedArray a = context.getTheme().obtainStyledAttributes(
                   attrs,
                   R.styleable.FabProperties,
                   0, 0);

           try {
               int resourceId =a.getResourceId(R.styleable.FabProperties_imageResource, 0);
               if(resourceId!=0)
               {
                   mFab.setImageResource(resourceId);
               }
               //tamaño
               int size =a.getDimensionPixelSize(R.styleable.FabProperties_size,0);
               if(size>0)
               {
                   ViewGroup.LayoutParams params =  mFab.getLayoutParams();
                   params.height=size;
                   params.width=size;
                   mFab.setLayoutParams(params);
               }
               int Background=a.getResourceId(R.styleable.FabProperties_fabBackground, 0);
               if(Background!=0)
               {
                   if(Utils.isLP())
                   {
                       //usamos ripple
                       mFab.setBackground(getResources().getDrawable(Background));

                   }else
                   {
                       // Attribute array
                       int[] attributes = new int[] { android.R.attr.selectableItemBackground };
                       TypedArray array = context.getTheme().obtainStyledAttributes(attributes);
                        // Drawable held by attribute 'selectableItemBackground' is at index '0'
                       Drawable d = array.getDrawable(0);
                       array.recycle();
                       LayerDrawable ld = new LayerDrawable(new Drawable[] {
                               // Nine Path Drawable
                               getResources().getDrawable(Background),
                               // Drawable from attribute
                               d });
                        // Set the background to 'ld'

                       Utils.setBackground(ld,mFab);
                   }

               }


           }catch (Exception ex)
           {
               ex.printStackTrace();
           }
           finally {
               a.recycle();
           }
       }
       addView(mFab);

   }

    @Override
    public void onClick(View v) {
        if(this.onClickListener!=null)
        {
            this.onClickListener.onClick(this);
        }
    }
}
