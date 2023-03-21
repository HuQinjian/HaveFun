package com.example.havefun;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.havefun.databinding.ActivityMainBinding;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private ActivityMainBinding mainBinding;
    private Map<String, Object> map = new HashMap<>();
    private MyDialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        myDialog = new MyDialog(this);
        mainBinding.etPrice.setEnabled(false);
        mainBinding.etMinprice.setEnabled(false);
        mainBinding.etMaxprice.setEnabled(false);
        mainBinding.etAccessibility.setEnabled(false);
        mainBinding.etMinaccessibility.setEnabled(false);
        mainBinding.etMaxaccessibility.setEnabled(false);
        mainBinding.btnGetData.setOnClickListener(this);
        mainBinding.radiogroup01.setOnCheckedChangeListener(this);
        mainBinding.radiogroup02.setOnCheckedChangeListener(this);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onClick(View view) {

        setMapValue();

        if (!(mainBinding.etKey.getText().toString().trim().isEmpty() || mainBinding.etKey.getText().toString().trim().equals(""))) {
            map.clear();
            map.put("key", mainBinding.etKey.getText().toString().trim());
        } else {
            //将除key之外的不空的值设入map中
        }
        Toast.makeText(this, "map:" + map.toString(), Toast.LENGTH_SHORT).show();
        //请求数据并展示
        RetrofitClient_bore.getInstance().getService(Service.class).getActivity(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<com.example.havefun.boreBean>() {
                    @Override
                    public void accept(com.example.havefun.boreBean boreBean) throws Throwable {
                        if (boreBean.getActivity() == null) {
                            mainBinding.tvActivity01.setText("啊哦，没找到符合要求的活动呢~");
                            mainBinding.tvActivity02.setText("试着更改查询条件试试~");
                        } else {
                            //将返回的activity翻译成中文
                            RetrofitClient_translate.getmInstance().getService(Service.class).getTranslateData("json", "AUTO", boreBean.getActivity())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Consumer<TranslateBean>() {
                                        @Override
                                        public void accept(TranslateBean translateBean) throws Throwable {
                                            mainBinding.tvActivity01.setText("无聊的话不如" + translateBean.getTranslateResult().get(0).get(0).getTgt() + "吧？");
                                        }
                                    }, new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) throws Throwable {
                                            Toast.makeText(MainActivity.this, "翻译错误！" + throwable.toString(), Toast.LENGTH_SHORT).show();
                                            System.out.println(throwable);
                                        }
                                    });
                            mainBinding.tvActivity02.setText("How about to " + boreBean.getActivity() + "?");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Toast.makeText(MainActivity.this, "获取活动错误！" + throwable.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //设置经费，难易及范围
    private void setMapValue() {

        map.clear();

        String str_key = mainBinding.etKey.getText().toString();
        String str_participants = mainBinding.etParticipants.getText().toString();
        String str_type = mainBinding.spinnerType.getSelectedItem().toString();
        String str_price = mainBinding.etPrice.getText().toString().trim();
        String str_minprice = mainBinding.etMinprice.getText().toString().trim();
        String str_maxprice = mainBinding.etMaxprice.getText().toString().trim();
        String str_accessibility = mainBinding.etAccessibility.getText().toString().trim();
        String str_minaccessibility = mainBinding.etMinaccessibility.getText().toString().trim();
        String str_maxaccessibility = mainBinding.etMaxaccessibility.getText().toString().trim();

        //判断key是否输入
        if (str_key.length() != 0) {
            mainBinding.tvKey.setTextColor(getResources().getColor(R.color.black));
            mainBinding.spinnerType.setEnabled(false);
            mainBinding.etParticipants.setEnabled(false);
            mainBinding.radiogroup01.setEnabled(false);
            mainBinding.radiogroup02.setEnabled(false);
            map.put("key", Integer.parseInt(str_key));
            return;
        } else {
            mainBinding.tvKey.setTextColor(getResources().getColor(R.color.gray));
            mainBinding.spinnerType.setEnabled(true);
            mainBinding.etParticipants.setEnabled(true);
            mainBinding.radiogroup01.setEnabled(true);
            mainBinding.radiogroup02.setEnabled(true);
        }

        //判断人数是否输入
        if (str_participants.length() != 0) {
            mainBinding.tvParticipants.setTextColor(getResources().getColor(R.color.black));
            if (Integer.parseInt(str_participants) > 20) {
                myDialog.ShowDialog("作者有话说", "吹牛逼要有限度，你丫要有超过20个朋友还用得着打开本应用？给老子重输！", "对不起，我错了！", "扎心了，老铁，我去改改");
                mainBinding.etParticipants.setText("");
            } else if (Integer.parseInt(str_participants) < 0) {
                myDialog.ShowDialog("作者有话说", "一个人都没有，是给鬼选的活动吗？给老子重输！", "对不起，我错了！", "扎心了，老铁，我去改改");
                mainBinding.etParticipants.setText("");
            } else {
                map.put("participants", Integer.parseInt(str_participants));
            }
        } else {
            mainBinding.tvParticipants.setTextColor(getResources().getColor(R.color.gray));
        }

        //判断类型是否选择
        if (mainBinding.spinnerType.getSelectedItemId() == 0) {
            mainBinding.tvType.setTextColor(getResources().getColor(R.color.gray));
        } else {
            mainBinding.tvType.setTextColor(getResources().getColor(R.color.black));
        }
        switch (str_type) {
            case "教育":
                map.put("type", "education");
                break;
            case "娱乐":
                map.put("type", "recreational");
                break;
            case "社交":
                map.put("type", "social");
                break;
            case "DIY":
                map.put("type", "diy");
                break;
            case "慈善":
                map.put("type", "charity");
                break;
            case "烹饪":
                map.put("type", "cooking");
                break;
            case "放松":
                map.put("type", "relaxation");
                break;
            case "音乐":
                map.put("type", "music");
                break;
            case "繁忙的工作":
                map.put("type", "busywork");
                break;
            default:
                break;
        }

        //判断price输入合法性
        if (mainBinding.radiogroup01.getCheckedRadioButtonId() == R.id.radiogroup01btn01 && str_price.length() != 0) {
            float price = Float.parseFloat(str_price);
            if (price < 0.0 || price > 1.0) {
                mainBinding.etPrice.setText("");
                myDialog.ShowDialog("作者有话说", "你丫是盲人嘛！？没看到经费区间是0到1啊？真想给你一棒子！", "对不起，我错了！", "扎心了，老铁，我去改改");
            } else {
                map.put("price", price);
                if (map.containsKey("minprice")) {
                    map.remove("minprice");
                }
                if (map.containsKey("maxprice")) {
                    map.remove("maxprice");
                }
            }
        }

        //判断minprice,maxprice输入合法性
        if (mainBinding.radiogroup01.getCheckedRadioButtonId() == R.id.radiogroup01btn02) {
            if (str_minprice.length() != 0 && str_maxprice.length() != 0) {
                float minprice = Float.parseFloat(str_minprice);
                float maxprice = Float.parseFloat(str_maxprice);
                if (minprice < 0.0 || minprice > 1.0 || maxprice < 0.0 || maxprice > 1.0) {
                    mainBinding.etMinprice.setText("");
                    mainBinding.etMaxprice.setText("");
                    myDialog.ShowDialog("作者有话说", "你丫是盲人嘛！？没看到经费区间是0到1啊？真想给你一棒子！", "对不起，我错了！", "扎心了，老铁，我去改改");
                }
                if (minprice > maxprice) {
                    mainBinding.etMinprice.setText("");
                    mainBinding.etMaxprice.setText("");
                    myDialog.ShowDialog("作者有话说", "你丫数学是体育老师教的吗？下限比上限大？真想给你一棒子！", "对不起，我错了！", "扎心了，老铁，我去改改");
                } else {
                    map.put("minprice", minprice);
                    map.put("maxprice", maxprice);
                }
            } else if (str_minprice.length() != 0){
                map.put("maxprice", 1.0f);
                map.put("minprice", Float.parseFloat(str_minprice));

            } else if (str_maxprice.length() != 0) {
                map.put("maxprice", Float.parseFloat(str_maxprice));
                map.put("minprice", 0.0f);
            }else {
                map.remove("minprice");
                map.remove("maxprice");
            }
            if (map.containsKey("price")){
                map.remove("price");
            }
        }

        if (mainBinding.radiogroup02.getCheckedRadioButtonId() == R.id.radiogroup02btn01 && str_accessibility.length() != 0) {
            float accessibility = Float.parseFloat(str_accessibility);
            if (accessibility < 0.0 || accessibility > 1.0) {
                mainBinding.etAccessibility.setText("");
                myDialog.ShowDialog("作者有话说", "你丫是盲人嘛！？没看到难易区间是0到1啊？真想给你一棒子！", "对不起，我错了！", "扎心了，老铁，我去改改");
            } else {
                map.put("accessiblity", accessibility);
                if (map.containsKey("minaccessiblity")) {
                    map.remove("minaccessiblity");
                }
                if (map.containsKey("maxaccessiblity")) {
                    map.remove("maxaccessiblity");
                }
            }
        }

        //判断minaccessibility,maxaccessibility输入合法性
        if (mainBinding.radiogroup02.getCheckedRadioButtonId() == R.id.radiogroup02btn02) {
            if (str_minaccessibility.length() != 0&&str_maxaccessibility.length()!=0){
            float minaccessibility = Float.parseFloat(str_minaccessibility);
                float maxaccessibility = Float.parseFloat(str_maxaccessibility);
            if (minaccessibility < 0.0 || minaccessibility > 1.0 || maxaccessibility < 0.0 || maxaccessibility > 1.0) {
                mainBinding.etMinaccessibility.setText("");
                mainBinding.etMaxaccessibility.setText("");
                myDialog.ShowDialog("作者有话说", "你丫是盲人嘛！？没看到难易区间是0到1啊？真想给你一棒子！", "对不起，我错了！", "扎心了，老铁，我去改改");
            }
                if (minaccessibility > maxaccessibility) {
                    mainBinding.etMinaccessibility.setText("");
                    mainBinding.etMaxaccessibility.setText("");
                    myDialog.ShowDialog("作者有话说", "你丫数学是体育老师教的吗？下限比上限大？真想给你一棒子！", "对不起，我错了！", "扎心了，老铁，我去改改");
                } else {
                    map.put("minaccessiblity", minaccessibility);
                    map.put("maxaccessiblity", maxaccessibility);
                }
            } else if (str_minaccessibility.length() != 0){
                map.put("minaccessiblity", Float.parseFloat(str_minaccessibility));
                map.put("maxaccessiblity", 1.0f);
            } else if (str_maxaccessibility.length() != 0) {
                map.put("minaccessiblity", 0.0f);
                map.put("maxaccessiblity", Float.parseFloat(str_maxaccessibility));
            }else {
                map.remove("minaccessiblity");
                map.remove("maxaccessiblity");
            }
            if (map.containsKey("accessiblity")){
                map.remove("accessiblity");
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        int id = radioGroup.getCheckedRadioButtonId();
        switch (radioGroup.getId()) {
            case R.id.radiogroup01:
                if (id == R.id.radiogroup01btn01) {
                    mainBinding.tvPrice.setTextColor(getResources().getColor(R.color.black));
                    mainBinding.tvPricerange.setTextColor(getResources().getColor(R.color.gray));
                    mainBinding.etPrice.setEnabled(true);
                    mainBinding.etMinprice.setEnabled(false);
                    mainBinding.etMaxprice.setEnabled(false);

                } else {
                    mainBinding.tvPricerange.setTextColor(getResources().getColor(R.color.black));
                    mainBinding.tvPrice.setTextColor(getResources().getColor(R.color.gray));
                    mainBinding.etPrice.setEnabled(false);
                    mainBinding.etMinprice.setEnabled(true);
                    mainBinding.etMaxprice.setEnabled(true);

                }
                break;
            case R.id.radiogroup02:
                if (id == R.id.radiogroup02btn01) {
                    mainBinding.tvAccessibility.setTextColor(getResources().getColor(R.color.black));
                    mainBinding.tvAccessibilityrange.setTextColor(getResources().getColor(R.color.gray));
                    mainBinding.etAccessibility.setEnabled(true);
                    mainBinding.etMinaccessibility.setEnabled(false);
                    mainBinding.etMaxaccessibility.setEnabled(false);

                } else {
                    mainBinding.tvAccessibility.setTextColor(getResources().getColor(R.color.gray));
                    mainBinding.tvAccessibilityrange.setTextColor(getResources().getColor(R.color.black));
                    mainBinding.etAccessibility.setEnabled(false);
                    mainBinding.etMinaccessibility.setEnabled(true);
                    mainBinding.etMaxaccessibility.setEnabled(true);

                }
                break;
            default:
                break;
        }
    }


    /*
    设置点击editText以外的地方软键盘消失
    ---------start--------
     */
    @Override//屏幕触摸事件监听
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //判断是否为点击动作
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();//获取当前点击坐标
            if (isShouldHideKeyboard(v, ev)) {
                //点击EditText之外的地方
                hideKeyboard(v.getWindowToken());//收起软键盘
                v.clearFocus();//清除edittext获取的焦点
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    //点击EditText之外的部分隐藏软键盘
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，
        // 第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    //点击EditText以外的地方收起软键盘
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /*
    设置点击editText以外的地方软键盘消失
    ---------end--------
     */

}