package com.doris.password.widget.library;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Doris
 * @date 2018/11/3
 */
public class PasswordBlendView extends FrameLayout implements View.OnClickListener {

    private static final String FIRST_INPUT_TIP = "请输入%s-%s位密码",
            SECOND_INPUT_TIP = "确认密码",
            WRONG_INPUT_TIP = "密码错误！",
            CORRECT_INPUT_TIP = "密码正确！";

    private boolean mSecondInput, mUppercase;
    private PasswordViewListener mListener;
    private ViewGroup mPasswordLayout;
    private TextView mInputTip;
    private View mCursor;
    private ImageView mLock, mOk;
    private TextView mBlendChange, letterA, letterB, letterC, letterD, letterE, letterF, letterG,
            letterH, letterI, letterJ, letterK, letterL, letterM, letterN, letterO,
            letterP, letterQ, letterR, letterS, letterT, letterU, letterV, letterW,
            letterX, letterY, letterZ;

    private String mLocalPassword = "", mFirstInputTip, mSecondInputTip, mWrongLengthTip,
            mWrongInputTip, mCorrectInputTip;

    private int mMinLength = 4, mMaxLength = 8,
            mCorrectStatusColor = 0xFF61C560, mWrongStatusColor = 0xFFF24055,
            mNormalStatusColor = 0xFFFFFFFF, mNumberTextColor = 0xFF747474,
            mPasswordType = PasswordViewType.typeSet;

    public PasswordBlendView(@NonNull Context context) {
        this(context, null);
    }

    public PasswordBlendView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.view_password_blend, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PasswordView);
        try {
            mPasswordType = typedArray.getInt(R.styleable.PasswordView_type, mPasswordType);
            mMinLength = typedArray.getInt(R.styleable.PasswordView_minLength, mMinLength);
            mMaxLength = typedArray.getInt(R.styleable.PasswordView_maxLength, mMaxLength);
            mNormalStatusColor = typedArray.getColor(R.styleable.PasswordView_normalStateColor, mNormalStatusColor);
            mWrongStatusColor = typedArray.getColor(R.styleable.PasswordView_wrongStateColor, mWrongStatusColor);
            mCorrectStatusColor = typedArray.getColor(R.styleable.PasswordView_correctStateColor, mCorrectStatusColor);
            mNumberTextColor = typedArray.getColor(R.styleable.PasswordView_numberTextColor, mNumberTextColor);
            mFirstInputTip = typedArray.getString(R.styleable.PasswordView_firstInputTip);
            mSecondInputTip = typedArray.getString(R.styleable.PasswordView_secondInputTip);
            mWrongLengthTip = typedArray.getString(R.styleable.PasswordView_wrongLengthTip);
            mWrongInputTip = typedArray.getString(R.styleable.PasswordView_wrongInputTip);
            mCorrectInputTip = typedArray.getString(R.styleable.PasswordView_correctInputTip);
        } finally {
            typedArray.recycle();
        }

        mFirstInputTip = mFirstInputTip == null ? String.format(FIRST_INPUT_TIP, mMinLength, mMaxLength) : mFirstInputTip;
        mSecondInputTip = mSecondInputTip == null ? SECOND_INPUT_TIP : mSecondInputTip;
        mWrongLengthTip = mWrongLengthTip == null ? mFirstInputTip : mWrongLengthTip;
        mWrongInputTip = mWrongInputTip == null ? WRONG_INPUT_TIP : mWrongInputTip;
        mCorrectInputTip = mCorrectInputTip == null ? CORRECT_INPUT_TIP : mCorrectInputTip;

        initLetter();
        init();
    }

    private void init() {
        mPasswordLayout = findViewById(R.id.layout_password);
        mInputTip = findViewById(R.id.tv_input_tip);
        mCursor = findViewById(R.id.cursor);
        mLock = findViewById(R.id.iv_lock);
        mOk = findViewById(R.id.iv_ok);
        TextView number0 = findViewById(R.id.number0);
        TextView number1 = findViewById(R.id.number1);
        TextView number2 = findViewById(R.id.number2);
        TextView number3 = findViewById(R.id.number3);
        TextView number4 = findViewById(R.id.number4);
        TextView number5 = findViewById(R.id.number5);
        TextView number6 = findViewById(R.id.number6);
        TextView number7 = findViewById(R.id.number7);
        TextView number8 = findViewById(R.id.number8);
        TextView number9 = findViewById(R.id.number9);
        mBlendChange = findViewById(R.id.blendChange);
        ImageView blendOK = findViewById(R.id.blendOK);
        ImageView blendB = findViewById(R.id.blendB);
        mInputTip.setText(mFirstInputTip);
        number0.setOnClickListener(this);
        number1.setOnClickListener(this);
        number2.setOnClickListener(this);
        number3.setOnClickListener(this);
        number4.setOnClickListener(this);
        number5.setOnClickListener(this);
        number6.setOnClickListener(this);
        number7.setOnClickListener(this);
        number8.setOnClickListener(this);
        number9.setOnClickListener(this);
        mBlendChange.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mUppercase = !mUppercase;
                letterChange();
            }
        });
        blendOK.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
        blendB.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteChar();
            }
        });
        blendB.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                deleteAllChars();
                return true;
            }
        });
        tintImageView(mLock, mNumberTextColor);
        tintImageView(blendB, mNumberTextColor);
        tintImageView(blendOK, mNumberTextColor);
        tintImageView(mOk, mCorrectStatusColor);
        mBlendChange.setTextColor(mNumberTextColor);
        number0.setTextColor(mNumberTextColor);
        number1.setTextColor(mNumberTextColor);
        number2.setTextColor(mNumberTextColor);
        number3.setTextColor(mNumberTextColor);
        number4.setTextColor(mNumberTextColor);
        number5.setTextColor(mNumberTextColor);
        number6.setTextColor(mNumberTextColor);
        number7.setTextColor(mNumberTextColor);
        number8.setTextColor(mNumberTextColor);
        number9.setTextColor(mNumberTextColor);
    }

    private void letterChange(){
        if (mUppercase){
            mBlendChange.setText(R.string.uppercase);
            letterA.setText(letterA.getText().toString().toUpperCase());
            letterB.setText(letterB.getText().toString().toUpperCase());
            letterC.setText(letterC.getText().toString().toUpperCase());
            letterD.setText(letterD.getText().toString().toUpperCase());
            letterE.setText(letterE.getText().toString().toUpperCase());
            letterF.setText(letterF.getText().toString().toUpperCase());
            letterG.setText(letterG.getText().toString().toUpperCase());
            letterH.setText(letterH.getText().toString().toUpperCase());
            letterI.setText(letterI.getText().toString().toUpperCase());
            letterJ.setText(letterJ.getText().toString().toUpperCase());
            letterK.setText(letterK.getText().toString().toUpperCase());
            letterL.setText(letterL.getText().toString().toUpperCase());
            letterM.setText(letterM.getText().toString().toUpperCase());
            letterN.setText(letterN.getText().toString().toUpperCase());
            letterO.setText(letterO.getText().toString().toUpperCase());
            letterP.setText(letterP.getText().toString().toUpperCase());
            letterQ.setText(letterQ.getText().toString().toUpperCase());
            letterR.setText(letterR.getText().toString().toUpperCase());
            letterS.setText(letterS.getText().toString().toUpperCase());
            letterT.setText(letterT.getText().toString().toUpperCase());
            letterU.setText(letterU.getText().toString().toUpperCase());
            letterV.setText(letterV.getText().toString().toUpperCase());
            letterW.setText(letterW.getText().toString().toUpperCase());
            letterX.setText(letterX.getText().toString().toUpperCase());
            letterY.setText(letterY.getText().toString().toUpperCase());
            letterZ.setText(letterZ.getText().toString().toUpperCase());
        } else {
            mBlendChange.setText(R.string.lowercase);
            letterA.setText(letterA.getText().toString().toLowerCase());
            letterB.setText(letterB.getText().toString().toLowerCase());
            letterC.setText(letterC.getText().toString().toLowerCase());
            letterD.setText(letterD.getText().toString().toLowerCase());
            letterE.setText(letterE.getText().toString().toLowerCase());
            letterF.setText(letterF.getText().toString().toLowerCase());
            letterG.setText(letterG.getText().toString().toLowerCase());
            letterH.setText(letterH.getText().toString().toLowerCase());
            letterI.setText(letterI.getText().toString().toLowerCase());
            letterJ.setText(letterJ.getText().toString().toLowerCase());
            letterK.setText(letterK.getText().toString().toLowerCase());
            letterL.setText(letterL.getText().toString().toLowerCase());
            letterM.setText(letterM.getText().toString().toLowerCase());
            letterN.setText(letterN.getText().toString().toLowerCase());
            letterO.setText(letterO.getText().toString().toLowerCase());
            letterP.setText(letterP.getText().toString().toLowerCase());
            letterQ.setText(letterQ.getText().toString().toLowerCase());
            letterR.setText(letterR.getText().toString().toLowerCase());
            letterS.setText(letterS.getText().toString().toLowerCase());
            letterT.setText(letterT.getText().toString().toLowerCase());
            letterU.setText(letterU.getText().toString().toLowerCase());
            letterV.setText(letterV.getText().toString().toLowerCase());
            letterW.setText(letterW.getText().toString().toLowerCase());
            letterX.setText(letterX.getText().toString().toLowerCase());
            letterY.setText(letterY.getText().toString().toLowerCase());
            letterZ.setText(letterZ.getText().toString().toLowerCase());
        }
    }

    private void initLetter() {
        letterA = findViewById(R.id.letterA);
        letterB = findViewById(R.id.letterB);
        letterC = findViewById(R.id.letterC);
        letterD = findViewById(R.id.letterD);
        letterE = findViewById(R.id.letterE);
        letterF = findViewById(R.id.letterF);
        letterG = findViewById(R.id.letterG);
        letterH = findViewById(R.id.letterH);
        letterI = findViewById(R.id.letterI);
        letterJ = findViewById(R.id.letterJ);
        letterK = findViewById(R.id.letterK);
        letterL = findViewById(R.id.letterL);
        letterM = findViewById(R.id.letterM);
        letterN = findViewById(R.id.letterN);
        letterO = findViewById(R.id.letterO);
        letterP = findViewById(R.id.letterP);
        letterQ = findViewById(R.id.letterQ);
        letterR = findViewById(R.id.letterR);
        letterS = findViewById(R.id.letterS);
        letterT = findViewById(R.id.letterT);
        letterU = findViewById(R.id.letterU);
        letterV = findViewById(R.id.letterV);
        letterW = findViewById(R.id.letterW);
        letterX = findViewById(R.id.letterX);
        letterY = findViewById(R.id.letterY);
        letterZ = findViewById(R.id.letterZ);
        letterA.setOnClickListener(this);
        letterB.setOnClickListener(this);
        letterC.setOnClickListener(this);
        letterD.setOnClickListener(this);
        letterE.setOnClickListener(this);
        letterF.setOnClickListener(this);
        letterG.setOnClickListener(this);
        letterH.setOnClickListener(this);
        letterI.setOnClickListener(this);
        letterJ.setOnClickListener(this);
        letterK.setOnClickListener(this);
        letterL.setOnClickListener(this);
        letterM.setOnClickListener(this);
        letterN.setOnClickListener(this);
        letterO.setOnClickListener(this);
        letterP.setOnClickListener(this);
        letterQ.setOnClickListener(this);
        letterR.setOnClickListener(this);
        letterS.setOnClickListener(this);
        letterT.setOnClickListener(this);
        letterU.setOnClickListener(this);
        letterV.setOnClickListener(this);
        letterW.setOnClickListener(this);
        letterX.setOnClickListener(this);
        letterY.setOnClickListener(this);
        letterZ.setOnClickListener(this);
        letterA.setTextColor(mNumberTextColor);
        letterB.setTextColor(mNumberTextColor);
        letterC.setTextColor(mNumberTextColor);
        letterD.setTextColor(mNumberTextColor);
        letterE.setTextColor(mNumberTextColor);
        letterF.setTextColor(mNumberTextColor);
        letterG.setTextColor(mNumberTextColor);
        letterH.setTextColor(mNumberTextColor);
        letterI.setTextColor(mNumberTextColor);
        letterJ.setTextColor(mNumberTextColor);
        letterK.setTextColor(mNumberTextColor);
        letterL.setTextColor(mNumberTextColor);
        letterM.setTextColor(mNumberTextColor);
        letterN.setTextColor(mNumberTextColor);
        letterO.setTextColor(mNumberTextColor);
        letterP.setTextColor(mNumberTextColor);
        letterQ.setTextColor(mNumberTextColor);
        letterR.setTextColor(mNumberTextColor);
        letterS.setTextColor(mNumberTextColor);
        letterT.setTextColor(mNumberTextColor);
        letterU.setTextColor(mNumberTextColor);
        letterV.setTextColor(mNumberTextColor);
        letterW.setTextColor(mNumberTextColor);
        letterX.setTextColor(mNumberTextColor);
        letterY.setTextColor(mNumberTextColor);
        letterZ.setTextColor(mNumberTextColor);
    }

    @Override
    public void onClick(View view) {
        String tag = ((TextView) view).getText().toString();
        if (mPasswordLayout.getChildCount() >= mMaxLength) {
            return;
        }
        PasswordCircleView psdView = new PasswordCircleView(getContext());
        int size = dpToPx();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        params.setMargins(size, 0, size, 0);
        psdView.setLayoutParams(params);
        psdView.setColor(mNormalStatusColor);
        psdView.setTag(tag);
        mPasswordLayout.addView(psdView);
    }

    private int dpToPx() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, metrics);
    }

    public String getLocalPassword() {
        return mLocalPassword;
    }

    /**
     * set  localPassword
     *
     * @param localPassword the code will to check
     */
    public PasswordBlendView setLocalPassword(String localPassword) {
        this.mLocalPassword = localPassword;
        this.mPasswordType = PasswordViewType.typeCheck;
        return this;
    }

    public PasswordViewListener getListener() {
        return mListener;
    }

    public PasswordBlendView setListener(PasswordViewListener listener) {
        this.mListener = listener;
        return this;
    }

    public String getFirstInputTip() {
        return mFirstInputTip;
    }

    public PasswordBlendView setFirstInputTip(String firstInputTip) {
        this.mFirstInputTip = firstInputTip;
        return this;
    }

    public String getSecondInputTip() {
        return mSecondInputTip;
    }

    public PasswordBlendView setSecondInputTip(String secondInputTip) {
        this.mSecondInputTip = secondInputTip;
        return this;
    }

    public String getWrongLengthTip() {
        return mWrongLengthTip;
    }

    public PasswordBlendView setWrongLengthTip(String wrongLengthTip) {
        this.mWrongLengthTip = wrongLengthTip;
        return this;
    }

    public String getWrongInputTip() {
        return mWrongInputTip;
    }

    public PasswordBlendView setWrongInputTip(String wrongInputTip) {
        this.mWrongInputTip = wrongInputTip;
        return this;
    }

    public String getCorrectInputTip() {
        return mCorrectInputTip;
    }

    public PasswordBlendView setCorrectInputTip(String correctInputTip) {
        this.mCorrectInputTip = correctInputTip;
        return this;
    }

    public int getPasswordMinLength() {
        return mMinLength;
    }

    public PasswordBlendView setPasswordMinLength(int passwordMinLength) {
        this.mMinLength = passwordMinLength;
        return this;
    }

    public int getPasswordMaxLength() {
        return mMaxLength;
    }

    public PasswordBlendView setPasswordMaxLength(int passwordMaxLength) {
        this.mMaxLength = passwordMaxLength;
        return this;
    }

    public int getCorrectStatusColor() {
        return mCorrectStatusColor;
    }

    public PasswordBlendView setCorrectStatusColor(int correctStatusColor) {
        this.mCorrectStatusColor = correctStatusColor;
        return this;
    }

    public int getWrongStatusColor() {
        return mWrongStatusColor;
    }

    public PasswordBlendView setWrongStatusColor(int wrongStatusColor) {
        this.mWrongStatusColor = wrongStatusColor;
        return this;
    }

    public int getNormalStatusColor() {
        return mNormalStatusColor;
    }

    public PasswordBlendView setNormalStatusColor(int normalStatusColor) {
        this.mNormalStatusColor = normalStatusColor;
        return this;
    }

    public int getNumberTextColor() {
        return mNumberTextColor;
    }

    public PasswordBlendView setNumberTextColor(int numberTextColor) {
        this.mNumberTextColor = numberTextColor;
        return this;
    }

    public @PasswordViewType
    int getPasswordType() {
        return mPasswordType;
    }

    public PasswordBlendView setPasswordType(@PasswordViewType int passwordType) {
        this.mPasswordType = passwordType;
        return this;
    }

    protected boolean equals(String val) {
        return mLocalPassword.equals(val);
    }

    private void next() {
        if (mPasswordType == PasswordViewType.typeCheck && TextUtils.isEmpty(mLocalPassword)) {
            throw new RuntimeException("must set localPassword when type is TYPE_CHECK");
        }

        String psd = getPasswordFromView();
        if (psd.length() < mMinLength || psd.length() > mMaxLength) {
            mInputTip.setText(mWrongLengthTip);
            runTipTextAnimation();
            return;
        }

        if (mPasswordType == PasswordViewType.typeSet && !mSecondInput) {
            // second input
            mInputTip.setText(mSecondInputTip);
            mLocalPassword = psd;
            clearChar();
            mSecondInput = true;
            return;
        }

        if (equals(psd)) {
            // match
            runOkAnimation();
        } else {
            runWrongAnimation();
        }
    }

    private void tintImageView(ImageView imageView, int color) {
        imageView.getDrawable().mutate().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    private void clearChar() {
        mPasswordLayout.removeAllViews();
    }

    private void deleteChar() {
        int childCount = mPasswordLayout.getChildCount();
        if (childCount <= 0) {
            return;
        }
        mPasswordLayout.removeViewAt(childCount - 1);
    }

    private void deleteAllChars() {
        int childCount = mPasswordLayout.getChildCount();
        if (childCount <= 0) {
            return;
        }
        mPasswordLayout.removeAllViews();
    }

    public void runTipTextAnimation() {
        shakeAnimator(mInputTip).start();
    }

    public void runWrongAnimation() {
        mCursor.setTranslationX(0);
        mCursor.setVisibility(VISIBLE);
        mCursor.animate()
                .translationX(mPasswordLayout.getWidth())
                .setDuration(600)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mCursor.setVisibility(INVISIBLE);
                        mInputTip.setText(mWrongInputTip);
                        setPSDViewBackgroundResource(mWrongStatusColor);
                        Animator animator = shakeAnimator(mPasswordLayout);
                        animator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                setPSDViewBackgroundResource(mNormalStatusColor);
                                if (mSecondInput && mListener != null) {
                                    mListener.onFail(getPasswordFromView());
                                }
                            }
                        });
                        animator.start();
                    }
                })
                .start();
    }

    private Animator shakeAnimator(View view) {
        return ObjectAnimator
                .ofFloat(view, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                .setDuration(500);
    }

    private void setPSDViewBackgroundResource(int color) {
        int childCount = mPasswordLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ((PasswordCircleView) mPasswordLayout.getChildAt(i)).setColor(color);
        }
    }

    public void runOkAnimation() {
        mCursor.setTranslationX(0);
        mCursor.setVisibility(VISIBLE);
        mCursor.animate()
                .setDuration(600)
                .translationX(mPasswordLayout.getWidth())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mCursor.setVisibility(INVISIBLE);
                        setPSDViewBackgroundResource(mCorrectStatusColor);
                        mInputTip.setText(mCorrectInputTip);
                        mLock.animate().alpha(0).scaleX(0).scaleY(0).setDuration(500).start();
                        mOk.animate().alpha(1).scaleX(1).scaleY(1).setDuration(500)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        if (mListener != null) {
                                            mListener.onSuccess(getPasswordFromView());
                                        }
                                    }
                                }).start();
                    }
                })
                .start();

    }

    private String getPasswordFromView() {
        StringBuilder sb = new StringBuilder();
        int childCount = mPasswordLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = mPasswordLayout.getChildAt(i);
            String num = (String) child.getTag();
            sb.append(num);
        }
        return sb.toString();
    }

}
