package app.editor.stas.editor;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.annotation.Size;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.text.style.AlignmentSpan;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SeekBar.OnSeekBarChangeListener, View.OnClickListener, View.OnTouchListener {

    AlertDialog.Builder builder;

    SharedPreferences sPref;

    ImageView colorPreview, updateImage;
    TextView textColor;

    private ImageView mBlock;
    private ViewGroup mMoveBlock;

    Context context;

    static EditText MAIN_TEXT;

    static String main_txt = "ENTER TEXT";

    static public Typeface face;

    static int red, green, blue;

    final int DIALOG_SIZE = 1;
    final int DIALOG_COLOR = 2;
    final int DIALOG_STYLE = 3;
    final int DIALOG_SCALE = 6;
    final int DIALOG_SCALE_X = 7;
    final int DIALOG_SCALE_Y = 8;
    final int DIALOG_SAVE = 4;
    final int DIALOG_LOAD = 5;

    private int X = 0;
    private int Y = 0;
    private int mX;
    private int mY;
    static int SIZE = 30, SIZE_BLOCK = 30;

    int CHECK_SIZE = 0, CHECK_COLOR = 0, CHECK_STYLE = 35, CHECK_SAVE = 0, CHECK_LOAD = 0, CHECK_SCALE = 0, CHECK_SCALE_X = 1,
            CHECK_SCALE_Y = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMoveBlock = (ViewGroup) findViewById(R.id.move);
        mBlock = (ImageView) mMoveBlock.findViewById(R.id.imageView);

        MAIN_TEXT = (EditText) findViewById(R.id.main_text);

        updateImage = (ImageView) findViewById(R.id.updateImage);

        context = MainActivity.this;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // мой код

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100, 100);

        mBlock.setLayoutParams(layoutParams);

        mBlock.setOnTouchListener(this);

        MAIN_TEXT.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        main_txt = MAIN_TEXT.getText().toString();
        if (id == R.id.nav_style) {
            showDialog(DIALOG_STYLE);
        } else if (id == R.id.nav_color) {
            showDialog(MainActivity.this);
        } else if (id == R.id.nav_size) {
            MAIN_TEXT.setText(main_txt);
            showDialog3(MainActivity.this);
        } else if (id == R.id.nav_scale) {
            showDialog(DIALOG_SCALE);
        } else if (id == R.id.nav_block) {
            mBlock.setVisibility(View.GONE);
            showDialog2(MainActivity.this);
        } else if (id == R.id.nav_save) {
            main_txt = MAIN_TEXT.getText().toString();
            showDialog(DIALOG_SAVE);
        } else if (id == R.id.nav_load) {
            loadText();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);  //  с помошью этого переходит на дргуой активити
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_SIZE:

                final String[] arrSize = new String[]{"SIZE=1", "SIZE=2", "SIZE=3", "SIZE=4", "SIZE=5", "SIZE=6", "SIZE=7", "SIZE=8"
                        , "SIZE=9", "SIZE=10", "SIZE=11", "SIZE=12", "SIZE=13", "SIZE=14"};

                builder = new AlertDialog.Builder(this);
                builder.setTitle("CHOOSE SIZE"); // заголовок для диалога

                builder.setItems(arrSize, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        CHECK_SIZE = item;
                    }
                });
                builder.setCancelable(false);
                return builder.create();

            case DIALOG_STYLE:
                final String[] arrStyle = new String[]{"Buccaneer", "Chocolate", "AA BadaBoom BB", "AA Clobberin Time Smooth", "MavickFont", "Amphi CYR",
                                                       "Astoria Deco", "Chuck", "Coca-Cola", "E4 Digital Cyrillic", "Pepsi", "Pixel", "Pravda Newspaper",
                                                       "Realize My Passion", "Snowstorm Inline", "Soviet", "SW Foxes", "CCZoinks", "GrotoGr", "JasperCapsSh",
                                                       "Bauhaus-Heavy", "Bemount Line", "Bemount", "Current", "DS Podd Cyr", "DS Quadro", "DS Reckoning Cyr",
                                                       "Five", "Ginebra", "Highliner Thin", "Highliner", "KB Zero", "Ogilvie Cyr", "Shihan", "Treffi", "Standard"};

                builder = new AlertDialog.Builder(this);
                builder.setTitle("CHOOSE STYLE"); // заголовок для диалога

                builder.setItems(arrStyle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        CHECK_STYLE = item;
                        check_style();
                    }
                });
                builder.setCancelable(false);
                return builder.create();

            case DIALOG_SCALE:

                final String[] arrScale = new String[]{"SCALE_X", "SCALE_Y"};

                builder = new AlertDialog.Builder(this);
                builder.setTitle("CHOOSE SCALE"); // заголовок для диалога

                builder.setItems(arrScale, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        CHECK_SCALE = item;
                        check_scale();
                    }
                });
                builder.setCancelable(false);
                return builder.create();

            case DIALOG_SCALE_X:

                final String[] arrScale_x = new String[]{"SCALE=1", "SCALE=2", "SCALE=3", "SCALE=4", "SCALE=5", "SCALE=6", "SCALE=7", "SCALE=8"
                                                         , "SCALE=9", "SCALE=10", "SCALE=11", "SCALE=12"};

                builder = new AlertDialog.Builder(this);
                builder.setTitle("CHOOSE SCALE X"); // заголовок для диалога

                builder.setItems(arrScale_x, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        CHECK_SCALE_X = item;
                        check_scale_x();
                    }
                });
                builder.setCancelable(false);
                return builder.create();

            case DIALOG_SCALE_Y:

                final String[] arrScale_y = new String[]{"SCALE=1", "SCALE=2", "SCALE=3", "SCALE=4", "SCALE=5", "SCALE=6", "SCALE=7", "SCALE=8"
                        , "SCALE=9", "SCALE=10", "SCALE=11", "SCALE=12"};

                builder = new AlertDialog.Builder(this);
                builder.setTitle("CHOOSE SCALE Y"); // заголовок для диалога

                builder.setItems(arrScale_y, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        CHECK_SCALE_Y = item;
                        check_scale_y();
                    }
                });
                builder.setCancelable(false);
                return builder.create();

            case DIALOG_SAVE:

                builder = new AlertDialog.Builder(context);
                builder.setTitle("You want to save the text?");  // заголовок
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        saveText();
                        Toast.makeText(context, "SAVE",
                                Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        Toast.makeText(context, "NOT SAVE", Toast.LENGTH_LONG)
                                .show();
                    }
                });
                builder.setCancelable(true);
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(context, "NOT SAVE",
                                Toast.LENGTH_LONG).show();
                    }
                });
                builder.setCancelable(false);
                return builder.create();

            default:
                break;

        }
        return super.onCreateDialog(id);
    }

    public static void showDialog(Activity activity) {

        //dialob builder
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.colorselectordialog);

        final TextView textColor = (TextView) dialog.findViewById(R.id.textColor);

        textColor.setText(main_txt);

        //dialog  init
        // that thing init objects inside dialog
        final SeekBar seekBarRed = (SeekBar) dialog.findViewById(R.id.seekBarRed);
        final SeekBar seekBarGreen = (SeekBar) dialog.findViewById(R.id.seekBarGreen);
        final SeekBar seekBarBlue = (SeekBar) dialog.findViewById(R.id.seekBarBlue);

        final ImageView colorPreview = (ImageView) dialog.findViewById(R.id.colorPreview);

        Button buttonDialog = (Button) dialog.findViewById(R.id.buttonDialog);
//        close dialog button

//        listener for all seekbars
        SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//               switch for get active object i mean object seekbar
                switch (seekBar.getId()) {
                    case R.id.seekBarRed:
                        red = i;
                        break;
                    case R.id.seekBarGreen:
                        green = i;
                        break;
                    case R.id.seekBarBlue:
                        blue = i;
                        break;
                }

                Button buttonDialog = (Button) dialog.findViewById(R.id.buttonDialog);

                buttonDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        MAIN_TEXT.setTextColor(Color.rgb(red, green, blue));
                    }
                });
                //set background color and text

                // that thing set negative color for better visiblity
                textColor.setTextColor(Color.rgb(red, green, blue));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        //set listener
        seekBarRed.setOnSeekBarChangeListener(seekBarListener);
        seekBarGreen.setOnSeekBarChangeListener(seekBarListener);
        seekBarBlue.setOnSeekBarChangeListener(seekBarListener);

        seekBarRed.setProgress(red);
        seekBarGreen.setProgress(green);
        seekBarBlue.setProgress(blue);

        dialog.show();
    }

    public static void showDialog3(Activity activity) {


        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_size);

        SeekBar seekBar_size = (SeekBar) dialog.findViewById(R.id.seekBar_size);
        final TextView textView = (TextView) dialog.findViewById(R.id.textView4);
        textView.setText(main_txt);

        SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//               switch for get active object i mean object seekbar
                switch (seekBar.getId()) {
                    case R.id.seekBar_size:
                        float a = Float.parseFloat(String.valueOf(seekBar.getProgress()));
                        if (a <= 5 && a >= 0) {
                            textView.setTextSize(5);
                        }
                        if (a <= 10 && a > 5) {
                            textView.setTextSize(10);
                        }
                        if (a <= 15 && a > 10) {
                            textView.setTextSize(15);
                        }
                        if (a <= 20 && a > 15) {
                            textView.setTextSize(20);
                        }
                        if (a <= 25 && a > 20) {
                            textView.setTextSize(25);
                        }
                        if (a <= 30 && a > 25) {
                            textView.setTextSize(30);
                        }
                        if (a <= 35 && a > 30) {
                            textView.setTextSize(35);
                        }
                        if (a <= 40 && a > 35) {
                            textView.setTextSize(40);
                        }
                        if (a <= 45 && a > 40) {
                            textView.setTextSize(45);
                        }
                        if (a <= 50 && a > 45) {
                            textView.setTextSize(50);
                        }
                        if (a <= 55 && a > 50) {
                            textView.setTextSize(55);
                        }
                        if (a <= 60 && a > 55) {
                            textView.setTextSize(60);
                        }
                        if (a <= 65 && a > 60) {
                            textView.setTextSize(65);
                        }
                        if (a <= 70 && a > 65) {
                            textView.setTextSize(70);
                        }
                        if (a <= 75 && a > 70) {
                            textView.setTextSize(75);
                        }
                        if (a <= 80 && a > 75) {
                            textView.setTextSize(80);
                        }
                        if (a <= 85 && a > 80) {
                            textView.setTextSize(85);
                        }
                        if (a <= 90 && a > 85) {
                            textView.setTextSize(90);
                        }
                        if (a <= 95 && a > 90) {
                            textView.setTextSize(95);
                        }
                        if (a <= 100 && a > 95) {
                            textView.setTextSize(100);
                        }
                        SIZE = (int) a;
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };

        Button buttonDialog = (Button) dialog.findViewById(R.id.finish_size);

        buttonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                MAIN_TEXT.setTextSize(SIZE);
            }
        });
        seekBar_size.setOnSeekBarChangeListener(seekBarListener);
        seekBar_size.setProgress(SIZE);
        dialog.show();
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()) {

            case R.id.seekBarRed:
                red = i;
                break;
            case R.id.seekBarGreen:
                green = i;
                break;
            case R.id.seekBarBlue:
                blue = i;
                break;
        }
        colorPreview.setBackgroundColor(Color.rgb(red, green, blue));
        textColor.setText(String.format("#%02x%02x%02x", red, green, blue));
        textColor.setTextColor(Color.rgb(Math.abs(red - 255), Math.abs(green - 255), Math.abs(blue - 255)));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_text:

                break;
        }
    }



    void check_style() {
        if (CHECK_STYLE == 0) {
            face = Typeface.createFromAsset(getAssets(), "fonts/Buccaneer.ttf");
            MAIN_TEXT.setTypeface(face);
        } else if (CHECK_STYLE == 1) {
            face = Typeface.createFromAsset(getAssets(), "fonts/Chocolate.otf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 2) {
            face = Typeface.createFromAsset(getAssets(), "fonts/AA BadaBoom BB.ttf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 3) {
            face = Typeface.createFromAsset(getAssets(), "fonts/AA Clobberin Time Smooth.ttf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 4) {
            face = Typeface.createFromAsset(getAssets(), "fonts/aMavickFont.ttf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 5) {
            face = Typeface.createFromAsset(getAssets(), "fonts/Amphi CYR.otf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 6) {
            face = Typeface.createFromAsset(getAssets(), "fonts/Astoria Deco.ttf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 7) {
            face = Typeface.createFromAsset(getAssets(), "fonts/Chuck.ttf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 8) {
            face = Typeface.createFromAsset(getAssets(), "fonts/Coca Cola.otf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 9) {
            face = Typeface.createFromAsset(getAssets(), "fonts/E4 Digital Cyrillic.otf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 10) {
            face = Typeface.createFromAsset(getAssets(), "fonts/Pepsi.otf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 11) {
            face = Typeface.createFromAsset(getAssets(), "fonts/Pixel.otf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 12) {
            face = Typeface.createFromAsset(getAssets(), "fonts/Pravda Newspaper.otf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 13) {
            face = Typeface.createFromAsset(getAssets(), "fonts/Realize My Passion.otf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 14) {
            face = Typeface.createFromAsset(getAssets(), "fonts/Snowstorm Inline.otf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 15) {
            face = Typeface.createFromAsset(getAssets(), "fonts/soviet.ttf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 16) {
            face = Typeface.createFromAsset(getAssets(), "fonts/SW Foxes.ttf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 17) {
            face = Typeface.createFromAsset(getAssets(), "fonts/v_CCZoinks.ttf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 18) {
            face = Typeface.createFromAsset(getAssets(), "fonts/fonts2/a_GrotoGr.ttf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 19) {
            face = Typeface.createFromAsset(getAssets(), "fonts/fonts2/a_JasperCapsSh.ttf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 20) {
            face = Typeface.createFromAsset(getAssets(), "fonts/fonts2/Bauhaus-Heavy.ttf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 21) {
            face = Typeface.createFromAsset(getAssets(), "fonts/fonts2/Bemount Line.ttf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 22) {
            face = Typeface.createFromAsset(getAssets(), "fonts/fonts2/Bemount.otf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 23) {
            face = Typeface.createFromAsset(getAssets(), "fonts/fonts2/Current.ttf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 24) {
            face = Typeface.createFromAsset(getAssets(), "fonts/fonts2/DS Podd Cyr.ttf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 25) {
            face = Typeface.createFromAsset(getAssets(), "fonts/fonts2/DS Quadro.ttf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 26) {
            face = Typeface.createFromAsset(getAssets(), "fonts/fonts2/DS Reckoning Cyr.ttf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 27) {
            face = Typeface.createFromAsset(getAssets(), "fonts/fonts2/Five.ttf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 28) {
            face = Typeface.createFromAsset(getAssets(), "fonts/fonts2/Ginebra.ttf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 29) {
            face = Typeface.createFromAsset(getAssets(), "fonts/fonts2/Highliner Thin.otf");
            MAIN_TEXT.setTypeface(face);
        }else if (CHECK_STYLE == 30) {
            face = Typeface.createFromAsset(getAssets(), "fonts/fonts2/Highliner.otf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 31) {
            face = Typeface.createFromAsset(getAssets(), "fonts/fonts2/KB Zero.ttf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 32) {
            face = Typeface.createFromAsset(getAssets(), "fonts/fonts2/Ogilvie Cyr.ttf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 33) {
            face = Typeface.createFromAsset(getAssets(), "fonts/fonts2/Shihan.otf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 34) {
            face = Typeface.createFromAsset(getAssets(), "fonts/fonts2/Treffi.ttf");
            MAIN_TEXT.setTypeface(face);
        }
        else if (CHECK_STYLE == 35) {
            MAIN_TEXT.setTextAppearance(this, android.R.style.TextAppearance);
            MAIN_TEXT.setTextSize(SIZE);
            MAIN_TEXT.setTextColor(Color.rgb(red,green,blue));
        }
    }

    void check_scale() {
        if (CHECK_SCALE == 0) {
            showDialog(DIALOG_SCALE_X);
        }
        if (CHECK_SCALE == 1) {
            showDialog(DIALOG_SCALE_Y);
        }
    }

    void check_scale_x() {
        if (CHECK_SCALE_X == 0) {
            MAIN_TEXT.setScaleX((float) 0.5); //работает
        }
        if (CHECK_SCALE_X == 1) {
            MAIN_TEXT.setScaleX(1);
        }
        if (CHECK_SCALE_X == 2) {
            MAIN_TEXT.setScaleX((float) 1.5);
        }
        if (CHECK_SCALE_X == 3) {
            MAIN_TEXT.setScaleX(2);
        }
        if (CHECK_SCALE_X == 4) {
            MAIN_TEXT.setScaleX((float) 2.5);
        }
        if (CHECK_SCALE_X == 5) {
            MAIN_TEXT.setScaleX(3);
        }
        if (CHECK_SCALE_X == 6) {
            MAIN_TEXT.setScaleX((float) 3.5);
        }
        if (CHECK_SCALE_X == 7) {
            MAIN_TEXT.setScaleX(4);
        }
        if (CHECK_SCALE_X == 8) {
            MAIN_TEXT.setScaleX((float) 4.5);
        }
        if (CHECK_SCALE_X == 9) {
            MAIN_TEXT.setScaleX(5);
        }
        if (CHECK_SCALE_X == 10) {
            MAIN_TEXT.setScaleX((float) 5.5);
        }
        if (CHECK_SCALE_X == 11) {
            MAIN_TEXT.setScaleX(6);
        }
        if(CHECK_SCALE_X == 12) {
            MAIN_TEXT.setTextSize(40);
        }
    }

    void check_scale_y() {
        if (CHECK_SCALE_Y == 0) {
            MAIN_TEXT.setScaleY((float) 0.5); //работает
        }
        if (CHECK_SCALE_Y == 1) {
            MAIN_TEXT.setScaleY(1);
        }
        if (CHECK_SCALE_Y == 2) {
            MAIN_TEXT.setScaleY((float) 1.5);
        }
        if (CHECK_SCALE_Y == 3) {
            MAIN_TEXT.setScaleY(2);
        }
        if (CHECK_SCALE_Y == 4) {
            MAIN_TEXT.setScaleY((float) 2.5);
        }
        if (CHECK_SCALE_Y == 5) {
            MAIN_TEXT.setScaleY(3);
        }
        if (CHECK_SCALE_Y == 6) {
            MAIN_TEXT.setScaleY((float) 3.5);
        }
        if (CHECK_SCALE_Y == 7) {
            MAIN_TEXT.setScaleY(4);
        }
        if (CHECK_SCALE_Y == 8) {
            MAIN_TEXT.setScaleY((float) 4.5);
        }
        if (CHECK_SCALE_Y == 9) {
            MAIN_TEXT.setScaleY(5);
        }
        if (CHECK_SCALE_Y == 10) {
            MAIN_TEXT.setScaleY((float) 5.5);
        }
        if (CHECK_SCALE_Y == 11) {
            MAIN_TEXT.setScaleY(6);
        }
        if(CHECK_SCALE_X == 12) {
            MAIN_TEXT.setTextSize(40);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        mBlock.setImageResource(R.drawable.squarewhite);

        //Определение координат через getRawX() и getRawY() дает
        //координаты по отношению к размерам экрана устройства:
        X = (int) event.getRawX();
        Y = (int) event.getRawY();

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            //ACTION_DOWN срабатывает при прикосновении к экрану,
            //здесь определяется начальное стартовое положение объекта:
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                mX = X - lParams.leftMargin;
                mY = Y - lParams.topMargin;
                break;

            //ACTION_MOVE обрабатывает случившиеся в процессе прикосновения изменения, здесь
            //содержится информация о последней точке, где находится объект после окончания действия прикосновения ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                        .getLayoutParams();
                layoutParams.leftMargin = X - mX;
                layoutParams.topMargin = Y - mY;
                view.setLayoutParams(layoutParams);
                mBlock.setImageResource(R.drawable.square);
                break;
        }
        return true;
    }

    public void showDialog2(Activity activity) {
        //dialob builder
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialogxml);

        mBlock.setVisibility(View.GONE);

        Button button = (Button) dialog.findViewById(R.id.button);
        SeekBar seekBar = (SeekBar) dialog.findViewById(R.id.seekBar);
        final ImageView imageView = (ImageView) dialog.findViewById(R.id.imageView);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(300, 300);
        imageView.setLayoutParams(layoutParams);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);

        SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//               switch for get active object i mean object seekbar
                switch (seekBar.getId()) {
                    case R.id.seekBar:
                         float a = Float.parseFloat(String.valueOf(seekBar.getProgress()));
                        if (a <= 5 && a >= 0) {
                            mBlock.setScaleY((float) 0.3);
                            mBlock.setScaleX((float) 0.3);

                            imageView.setScaleY((float) 0.3);
                            imageView.setScaleX((float) 0.3);
                        }
                        if (a <= 10 && a > 5) {
                            mBlock.setScaleY((float) 0.4);
                            mBlock.setScaleX((float) 0.4);

                            imageView.setScaleY((float) 0.4);
                            imageView.setScaleX((float) 0.4);
                        }
                        if (a <= 15 && a > 10) {
                            mBlock.setScaleY((float) 0.6);
                            mBlock.setScaleX((float) 0.6);

                            imageView.setScaleY((float) 0.6);
                            imageView.setScaleX((float) 0.6);
                        }
                        if (a <= 20 && a > 15) {
                            mBlock.setScaleY((float) 0.8);
                            mBlock.setScaleX((float) 0.8);

                            imageView.setScaleY((float) 0.8);
                            imageView.setScaleX((float) 0.8);
                        }
                        if (a <= 25 && a > 20) {
                            mBlock.setScaleY((float) 1);
                            mBlock.setScaleX((float) 1);

                            imageView.setScaleY((float) 1);
                            imageView.setScaleX((float) 1);
                        }
                        if (a <= 30 && a > 25) {
                            mBlock.setScaleY((float) 1.2);
                            mBlock.setScaleX((float) 1.2);

                            imageView.setScaleY((float) 1.2);
                            imageView.setScaleX((float) 1.2);
                        }
                        if (a <= 35 && a > 30) {
                            mBlock.setScaleY((float) 1.4);
                            mBlock.setScaleX((float) 1.4);

                            imageView.setScaleY((float) 1.4);
                            imageView.setScaleX((float) 1.4);
                        }
                        if (a <= 40 && a > 35) {
                            mBlock.setScaleY((float) 1.6);
                            mBlock.setScaleX((float) 1.6);

                            imageView.setScaleY((float) 1.6);
                            imageView.setScaleX((float) 1.6);
                        }
                        if (a <= 45 && a > 40) {
                            mBlock.setScaleY((float) 1.8);
                            mBlock.setScaleX((float) 1.8);

                            imageView.setScaleY((float) 1.8);
                            imageView.setScaleX((float) 1.8);
                        }
                        if (a <= 50 && a > 45) {
                            mBlock.setScaleY((float) 2);
                            mBlock.setScaleX((float) 2);

                            imageView.setScaleY((float) 2);
                            imageView.setScaleX((float) 2);
                        }
                        if (a <= 55 && a > 50) {
                            mBlock.setScaleY((float) 2.2);
                            mBlock.setScaleX((float) 2.2);

                            imageView.setScaleY((float) 2.2);
                            imageView.setScaleX((float) 2.2);
                        }
                        if (a <= 60 && a > 55) {
                            mBlock.setScaleY((float) 2.4);
                            mBlock.setScaleX((float) 2.4);

                            imageView.setScaleY((float) 2.4);
                            imageView.setScaleX((float) 2.4);
                        }
                        if (a <= 65 && a > 60) {
                            mBlock.setScaleY((float) 2.6);
                            mBlock.setScaleX((float) 2.6);

                            imageView.setScaleY((float) 2.6);
                            imageView.setScaleX((float) 2.6);
                        }
                        if (a <= 70 && a > 65) {
                            mBlock.setScaleY((float) 2.8);
                            mBlock.setScaleX((float) 2.8);

                            imageView.setScaleY((float) 2.8);
                            imageView.setScaleX((float) 2.8);
                        }
                        if (a <= 75 && a > 70) {
                            mBlock.setScaleY((float) 3);
                            mBlock.setScaleX((float) 3);

                            imageView.setScaleY((float) 3);
                            imageView.setScaleX((float) 3);
                        }
                        if (a <= 80 && a > 75) {
                            mBlock.setScaleY((float) 3.2);
                            mBlock.setScaleX((float) 3.2);

                            imageView.setScaleY((float) 3.2);
                            imageView.setScaleX((float) 3.2);
                        }
                        if (a <= 85 && a > 80) {
                            mBlock.setScaleY((float) 3.4);
                            mBlock.setScaleX((float) 3.4);

                            imageView.setScaleY((float) 3.4);
                            imageView.setScaleX((float) 3.4);
                        }
                        if (a <= 90 && a > 85) {
                            mBlock.setScaleY((float) 3.6);
                            mBlock.setScaleX((float) 3.6);

                            imageView.setScaleY((float) 3.6);
                            imageView.setScaleX((float) 3.6);
                        }
                        if (a <= 95 && a > 90) {
                            mBlock.setScaleY((float) 3.8);
                            mBlock.setScaleX((float) 3.8);

                            imageView.setScaleY((float) 3.8);
                            imageView.setScaleX((float) 3.8);
                        }
                        if (a <= 100 && a > 95) {
                            mBlock.setScaleY((float) 4);
                            mBlock.setScaleX((float) 4);

                            imageView.setScaleY((float) 4);
                            imageView.setScaleX((float) 4);
                        }
                        SIZE_BLOCK = (int) a;
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBlock.setVisibility(View.VISIBLE);
                mBlock.setImageResource(R.drawable.square);
                dialog.dismiss();
            }
        });

        seekBar.setOnSeekBarChangeListener(seekBarListener);
        seekBar.setProgress(SIZE_BLOCK);
        dialog.show();
    }

    void saveText() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt("style", CHECK_STYLE);
        ed.putInt("scale", CHECK_SCALE);
        ed.putInt("scale_x", CHECK_SCALE_X);
        ed.putInt("scale_y", CHECK_SCALE_Y);
        ed.putInt("size_block", SIZE_BLOCK);
        ed.putInt("size", SIZE);
        ed.putInt("red", red);
        ed.putInt("green",green);
        ed.putInt("blue", blue);
        ed.putString("maintxt", main_txt);
        ed.putInt("X", X);
        ed.putInt("Y", Y);
        ed.putInt("mX", mX);
        ed.putInt("mY", mY);
        ed.commit();
    }

    void loadText() {
        sPref = getPreferences(MODE_PRIVATE);
        CHECK_STYLE = sPref.getInt("style", CHECK_STYLE);
        SIZE = sPref.getInt("size", SIZE);
        CHECK_SCALE = sPref.getInt("scale", CHECK_SCALE);
        CHECK_SCALE_X = sPref.getInt("scale_x", CHECK_SCALE_X);
        CHECK_SCALE_Y = sPref.getInt("scale_y", CHECK_SCALE_Y);
        SIZE_BLOCK = sPref.getInt("size_block", SIZE_BLOCK);
        red = sPref.getInt("red", red);
        green = sPref.getInt("green", green);
        blue = sPref.getInt("blue", blue);
        main_txt = sPref.getString("maintxt", main_txt);
        X = sPref.getInt("X", X);
        Y = sPref.getInt("Y", Y);
        mX = sPref.getInt("mX", mX);
        mY = sPref.getInt("mY", mY);
        check_scale_x();
        check_scale_y();
        check_style();
        MAIN_TEXT.setText(main_txt);
        MAIN_TEXT.setTextColor(Color.rgb(red,green,blue));
        MAIN_TEXT.setTextSize(SIZE);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("color2", CHECK_COLOR);
        outState.putInt("size2", CHECK_SIZE);
        outState.putInt("style2", CHECK_STYLE);
        outState.putString("main_txt", main_txt);
        outState.putInt("scale_x", CHECK_SCALE_X);
        outState.putInt("scale_y", CHECK_SCALE_Y);
        outState.putInt("size", SIZE);
        outState.putInt("red", red);
        outState.putInt("green", green);
        outState.putInt("blue", blue);
        outState.putInt("x", X);
        outState.putInt("y", Y);
        check_scale_x();
        check_scale_y();
        check_style();
        MAIN_TEXT.setTextColor(Color.rgb(red,green,blue));
        MAIN_TEXT.setTextSize(SIZE);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        CHECK_SIZE = savedInstanceState.getInt("size2");
        CHECK_STYLE = savedInstanceState.getInt("style2");
        main_txt = savedInstanceState.getString("main_txt");
        CHECK_SCALE_X = savedInstanceState.getInt("scale_x");
        CHECK_SCALE_Y = savedInstanceState.getInt("scale_y");
        red = savedInstanceState.getInt("red");
        green = savedInstanceState.getInt("green");
        blue = savedInstanceState.getInt("blue");
        SIZE = savedInstanceState.getInt("size");
        X = savedInstanceState.getInt("x");
        Y = savedInstanceState.getInt("y");
        check_scale_x();
        check_scale_y();
        check_style();
        MAIN_TEXT.setTextColor(Color.rgb(red,green,blue));
        MAIN_TEXT.setTextSize(SIZE);
    }

    public void update(View view) {
        MAIN_TEXT.setTextAppearance(this, android.R.style.TextAppearance_Large);
        MAIN_TEXT.setText("ENTER TEXT");
        MAIN_TEXT.setTextSize(30);
        CHECK_SCALE_X = 1;
        CHECK_SCALE_Y = 1;
        check_scale_x();
        check_scale_y();
        updateImage.setImageResource(R.drawable.effect);
    }
}
