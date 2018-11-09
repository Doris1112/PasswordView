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
public class PasswordNumberView extends FrameLayout implements View.OnClickListener {

    private static final String FIRST_INPUT_TIP = "请输入%s-%s位数字密码",
            SECOND_INPUT_TIP = "确认数字密码",
            WRONG_INPUT_TIP = "密码错误！",
            CORRECT_INPUT_TIP = "密码正确！";

    private boolean mSecondInput;
    private PasswordViewListener mListener;
    private ViewGroup mPasswordLayout;
    private TextView mInputTip;
    private View mCursor;
    private ImageView mLock, mOk;

    private String mLocalPassword = "", mFirstInputTip, mSecondInputTip, mWrongLengthTip,
            mWrongInputTip, mCorrectInputTip;

    private int mMinLength = 4, mMaxLength = 8,
            mCorrectStatusColor = 0xFF61C560, mWrongStatusColor = 0xFFF24055,
            mNormalStatusColor = 0xFFFFFFFF, mNumberTextColor = 0xFF747474,
            mPasswordType = PasswordViewType.typeSet;

    public PasswordNumberView(@NonNull Context context) {
        this(context, null);
    }

    public PasswordNumberView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.view_password_number, this);
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
        ImageView numberOK = findViewById(R.id.numberOK);
        ImageView numberB = findViewById(R.id.numberB);

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
        numberB.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteChar();
            }
        });
        numberB.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                deleteAllChars();
                return true;
            }
        });
        numberOK.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });

        tintImageView(mLock, mNumberTextColor);
        tintImageView(numberB, mNumberTextColor);
        tintImageView(numberOK, mNumberTextColor);
        tintImageView(mOk, mCorrectStatusColor);

        number0.setTag(0);
        number1.setTag(1);
        number2.setTag(2);
        number3.setTag(3);
        number4.setTag(4);
        number5.setTag(5);
        number6.setTag(6);
        number7.setTag(7);
        number8.setTag(8);
        number9.setTag(9);
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

    @Override
    public void onClick(View view) {
        int number = (int) view.getTag();
        addChar(number);
    }

    public String getLocalPassword() {
        return mLocalPassword;
    }

    /**
     * set  localPassword
     *
     * @param localPassword the code will to check
     */
    public PasswordNumberView setLocalPassword(String localPassword) {
        for (int i = 0; i < localPassword.length(); i++) {
            char c = localPassword.charAt(i);
            if (c < '0' || c > '9') {
                throw new RuntimeException("must be number digit");
            }
        }
        this.mLocalPassword = localPassword;
        this.mPasswordType = PasswordViewType.typeCheck;
        return this;
    }

    public PasswordViewListener getListener() {
        return mListener;
    }

    public PasswordNumberView setListener(PasswordViewListener listener) {
        this.mListener = listener;
        return this;
    }

    public String getFirstInputTip() {
        return mFirstInputTip;
    }

    public PasswordNumberView setFirstInputTip(String firstInputTip) {
        this.mFirstInputTip = firstInputTip;
        return this;
    }

    public String getSecondInputTip() {
        return mSecondInputTip;
    }

    public PasswordNumberView setSecondInputTip(String secondInputTip) {
        this.mSecondInputTip = secondInputTip;
        return this;
    }

    public String getWrongLengthTip() {
        return mWrongLengthTip;
    }

    public PasswordNumberView setWrongLengthTip(String wrongLengthTip) {
        this.mWrongLengthTip = wrongLengthTip;
        return this;
    }

    public String getWrongInputTip() {
        return mWrongInputTip;
    }

    public PasswordNumberView setWrongInputTip(String wrongInputTip) {
        this.mWrongInputTip = wrongInputTip;
        return this;
    }

    public String getCorrectInputTip() {
        return mCorrectInputTip;
    }

    public PasswordNumberView setCorrectInputTip(String correctInputTip) {
        this.mCorrectInputTip = correctInputTip;
        return this;
    }

    public int getPasswordMinLength() {
        return mMinLength;
    }

    public PasswordNumberView setPasswordMinLength(int passwordMinLength) {
        this.mMinLength = passwordMinLength;
        return this;
    }

    public int getPasswordMaxLength() {
        return mMaxLength;
    }

    public PasswordNumberView setPasswordMaxLength(int passwordMaxLength) {
        this.mMaxLength = passwordMaxLength;
        return this;
    }

    public int getCorrectStatusColor() {
        return mCorrectStatusColor;
    }

    public PasswordNumberView setCorrectStatusColor(int correctStatusColor) {
        this.mCorrectStatusColor = correctStatusColor;
        return this;
    }

    public int getWrongStatusColor() {
        return mWrongStatusColor;
    }

    public PasswordNumberView setWrongStatusColor(int wrongStatusColor) {
        this.mWrongStatusColor = wrongStatusColor;
        return this;
    }

    public int getNormalStatusColor() {
        return mNormalStatusColor;
    }

    public PasswordNumberView setNormalStatusColor(int normalStatusColor) {
        this.mNormalStatusColor = normalStatusColor;
        return this;
    }

    public int getNumberTextColor() {
        return mNumberTextColor;
    }

    public PasswordNumberView setNumberTextColor(int numberTextColor) {
        this.mNumberTextColor = numberTextColor;
        return this;
    }

    public @PasswordViewType
    int getPasswordType() {
        return mPasswordType;
    }

    public PasswordNumberView setPasswordType(@PasswordViewType int passwordType) {
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

    private void addChar(int number) {
        if (mPasswordLayout.getChildCount() >= mMaxLength) {
            return;
        }
        PasswordCircleView psdView = new PasswordCircleView(getContext());
        int size = dpToPx();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        params.setMargins(size, 0, size, 0);
        psdView.setLayoutParams(params);
        psdView.setColor(mNormalStatusColor);
        psdView.setTag(number);
        mPasswordLayout.addView(psdView);
    }

    private int dpToPx() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, metrics);
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
            int num = (int) child.getTag();
            sb.append(num);
        }
        return sb.toString();
    }

}
