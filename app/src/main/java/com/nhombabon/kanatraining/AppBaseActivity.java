package com.nhombabon.kanatraining;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.style.StyleSpan;
import android.text.style.TextAppearanceSpan;

import java.util.HashMap;

//public class AppBaseActivity extends Activity implements Animation.AnimationListener {
public class AppBaseActivity extends Activity  {
    //Sets the text color, size, style, and typeface to match a TextAppearance resource.
    protected static TextAppearanceSpan mTextColorSpan;
    //Style of words, bold, itali,underline
    protected static StyleSpan sBoldStyleSpan = new StyleSpan(1);
    //HashMap of String and font(Typeface)
    private HashMap<String, Typeface> mFaceSave = new HashMap();
    //Handler like Thread, but you can delay like async in C#
    protected Handler mHandler = new Handler();
    //DialogShow like Dialog in c#
    protected DialogInterface mProgressDialog;

/*
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(3);
        //mTextColorSpan = new TextAppearanceSpan(this,  );
    }

    class closeProgressDialog implements OnDismissListener {
        closeProgressDialog() {
        }

        public void onDismiss(DialogInterface arg0)
        {
           AppBaseActivity.this.closeProgressDialog();
        }
    }

    public void clickNavBack()
    {
        finish();
    }

    public void clickNone(View v) {
    }

    //Open new activity from url
    public void openBrawser(String url) {
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
    }

    protected Animation loadAnimation(int id) {
        Animation anim = AnimationUtils.loadAnimation(this, id);
        anim.setAnimationListener(this);
        return anim;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    protected void fontChange(String fontName, TextView v) {
        Typeface face;
        if (this.mFaceSave.get(fontName) != null) {
            face = (Typeface) this.mFaceSave.get(fontName);
        } else {
            face = Typeface.createFromAsset(getAssets(), "font/" + fontName);
            this.mFaceSave.put(fontName, face);
        }
        v.setTypeface(face);
    }

    //Convert Hex color to Color
    protected int colorWithHex(String hexColor) {
        return Color.parseColor("#" + hexColor);
    }

    //Load resource to view background
    protected void loadResBgImage(String fileName, View view) {
        try {
            int id = getResources().getIdentifier(fileName, "drawable", getPackageName());
            if (id == 0) {
                throw new FileNotFoundException("Not Found - " + fileName);
            }
            Options options = new BitmapFactory.Options();
            options.inPurgeable = true;
            options.inPreferredConfig = RGB_565;
            view.setBackground(new BitmapDrawable(getResources(), decodeResource(getResources(), id, options)));
        } catch (IOException e) {
            Log.e("loadResBgImage", "Error in loadResBgImage: " + e.getMessage());
        }
    }

    //Load resource to ImageView
    protected void loadResImage(String fileName, ImageView view) {
        try {
            int id = getResources().getIdentifier(fileName, "drawable", getPackageName());
            if (id == 0) {
                throw new FileNotFoundException("Not Found - " + fileName);
            }
            Options options = new Options();
            options.inPurgeable = true;
            options.inPreferredConfig = RGB_565;
            view.setImageBitmap(decodeResource(getResources(), id, options));
        } catch (IOException e) {
            Log.e("loadResImage", "Error in loadResImage: " + e.getMessage());
        }
    }

    //Load resource from asset to ImageView
    protected void loadAssetImage(String fileName, ImageView view) {
        try {
            view.setImageBitmap(decodeStream(getResources().getAssets().open(fileName)));
        } catch (IOException e) {
            Log.e("loadAssetImage", "Error in loadAssetImage: " + e.getMessage());
        }
    }

    //Test rieng sau
    protected SpannableStringBuilder spanString(String text, String colString) {
        int start = text.indexOf("[");
        int end = text.indexOf("]");
        SpannableStringBuilder sb = new SpannableStringBuilder();
        if (start < 0 || end < 0) {
            sb.append(text);
        } else {
            int color;
            String firstText = text.substring(0, start);
            String secondText = text.substring(start + 1, end);
            String thirdText = text.substring(end + 1, text.length());
            switch (Integer.parseInt(colString)) {
                case 1:
                    color = colorWithHex("d3e8c0");
                    break;
                case 2:
                    color = colorWithHex("cbedee");
                    break;
                case 3:
                    color = colorWithHex("ffcdcc");
                    break;
                case 4:
                    color = colorWithHex("feebb6");
                    break;
                case 5:
                    color = colorWithHex("d9d0f1");
                    break;
                case 6:
                    color = colorWithHex("e7ecee");
                    break;
                default:
                    color = colorWithHex("717171");
                    break;
            }
            ForegroundColorSpan bfFcsSpan = new ForegroundColorSpan(color);
            ForegroundColorSpan afFcsSpan = new ForegroundColorSpan(color);
            sb.append(firstText);
            sb.append(secondText);
            sb.append(thirdText);
            if (firstText.length() > 0) {
                sb.setSpan(bfFcsSpan, 0, firstText.length(), 33);
            }
            sb.setSpan(sBoldStyleSpan, firstText.length(), firstText.length() + secondText.length(), 33);
            sb.setSpan(afFcsSpan, firstText.length() + secondText.length(), text.length() - 2, 33);
        }
        return sb;
    }


    public void closeProgressDialog() {
        if (this.mProgressDialog != null) {
            this.mProgressDialog.dismiss();
            this.mProgressDialog = null;
        }
    }

    protected void showProgressDialog(final Runnable cancel, int resId) {
        closeProgressDialog();
        boolean isCancelable = cancel != null;
        AppTransparentDialog diag = new AppTransparentDialog(this, getLayoutInflater().inflate(R.layout.loading, null));
        diag.setCancelable(isCancelable);
        diag.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                if (cancel != null) {
                    cancel.run();
                }
            }
        });
        diag.setOnDismissListener(new closeProgressDialog());
        diag.show();
        this.mProgressDialog = diag;
    }

    //Test rieng sau
    protected void createBlurImage(Bitmap source, ImageView imageView) {
        GPUImageGaussianBlurFilter filter_gaussian = new GPUImageGaussianBlurFilter();
        GPUImageNormalBlendFilter filter_blend = new GPUImageNormalBlendFilter();
        Bitmap bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(bitmap);
        Paint p = new Paint();
        p.setColor(822083583);
        cv.drawRect(0.0f, 0.0f, 1.0f, 1.0f, p);
        filter_blend.setBitmap(bitmap);
        GPUImage gpuImage = new GPUImage(this);
        gpuImage.setImage(source);
        gpuImage.setFilter(filter_blend);
        gpuImage.setImage(gpuImage.getBitmapWithFilterApplied());
        gpuImage.setFilter(filter_gaussian);
        filter_gaussian.setBlurSize(10.0f);
        gpuImage.setImage(gpuImage.getBitmapWithFilterApplied());
        filter_gaussian.setBlurSize(5.0f);
        gpuImage.setImage(gpuImage.getBitmapWithFilterApplied());
        filter_gaussian.setBlurSize(1.0f);
        imageView.setImageBitmap(gpuImage.getBitmapWithFilterApplied());
        filter_blend.setBitmap(null);
        bitmap.recycle();
        source.recycle();
    }

    //Get image from cache, cache la bo nho tam
    public Bitmap getViewBitmap(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap cache = view.getDrawingCache();
        if (cache == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);
        cache = Bitmap.createBitmap(cache, 0, 0, cache.getWidth(), cache.getHeight(), matrix, true);
        view.setDrawingCacheEnabled(false);
        return cache;
    }

    //Set thread handler =null and font, colortext null
    public void finish() {
        super.finish();
        this.mHandler = null;
        sBoldStyleSpan = null;
        mTextColorSpan = null;
    }

    protected void onDestroy() {
        Log.e("onDestroy", getClass().getName());
        super.onDestroy();
    }

    //Play file sound
    public MediaPlayer getAssetsMediaFile(Resources resource, String filename) {
        MediaPlayer p = new MediaPlayer();
        try {
            AssetFileDescriptor afd = resource.getAssets().openFd(filename);
            p.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            p.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }*/
}
