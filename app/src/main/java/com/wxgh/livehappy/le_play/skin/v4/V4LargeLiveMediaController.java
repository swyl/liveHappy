package com.wxgh.livehappy.le_play.skin.v4;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.wxgh.livehappy.le_play.skin.base.BaseChangeModeBtn;
import com.wxgh.livehappy.le_play.skin.base.BaseLiveSeekBar;
import com.wxgh.livehappy.le_play.skin.base.BasePlayBtn;
import com.wxgh.livehappy.le_play.skin.controller.BaseMediaController;
import com.lecloud.leutils.ReUtils;
import com.letv.controller.interfacev1.IPanoVideoChangeMode;

public class V4LargeLiveMediaController extends BaseMediaController {
    public static final String PANO_CHANGE_MODE_NAME = "vnew_change_mode";
    private BaseLiveSeekBar seekbar;
    private TextView seekTime;

    public V4LargeLiveMediaController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public V4LargeLiveMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public V4LargeLiveMediaController(Context context) {
        super(context);
    }

    public void registerPanoVideoChange(IPanoVideoChangeMode changeModeListener) {
        View view = findViewByResName(PANO_CHANGE_MODE_NAME);
        if (view != null && view instanceof BaseChangeModeBtn) {
            ((BaseChangeModeBtn) view).registerPanoVideoChange(changeModeListener);
        }
    }

    private View findViewByResName(String resName) {
        int index = childId.indexOf(resName);
        if (index != -1) {
            return childViews.get(index);
        }
        return null;
    }

    @Override
    protected void onSetLayoutId() {
        layoutId = "letv_skin_v4_controller_large_live_layout";
        childId.add("vnew_play_btn");
        childId.add("vnew_chg_btn");
        childId.add("vnew_rate_btn");
        childId.add(PANO_CHANGE_MODE_NAME);
        childId.add("vnew_seekbar");
    }

    @Override
    protected void onInitView() {
        BasePlayBtn playBtn = (BasePlayBtn) childViews.get(0);
        playBtn.setPlayBtnType(BasePlayBtn.play_btn_type_vod);// 设置按钮模式
        seekbar = (BaseLiveSeekBar) childViews.get(4);
        seekTime = (TextView) findViewById(ReUtils.getId(context, "vnew_time_text"));
        seekbar.setOnSeekChangeListener(new BaseLiveSeekBar.OnSeekChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!seekbar.isShown()) {
                    return;
                }
                if (seekTime != null) {
                    if (seekTime.getVisibility() != VISIBLE) {
                        seekTime.setVisibility(VISIBLE);
                    }
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) seekTime.getLayoutParams();
                    int right = seekBar.getRight() - seekBar.getWidth() * progress / seekBar.getMax();
                    if (fromUser) {
                        seekTime.setText("正在播放：" + seekbar.getSeekToTime());
                    } else {
                        seekTime.setText("正在播放：" + seekbar.getCurrentTime());
                    }
                    int leftMargin = seekBar.getRight() - right - seekTime.getMeasuredWidth();
                    if (leftMargin > 0) {
                        params.rightMargin = right;
                        seekTime.setLayoutParams(params);
                    }
                }
            }
        });
    }

    @Override
    protected void initPlayer() {

    }

    public BaseLiveSeekBar getSeekbar() {
        return seekbar;
    }

}
