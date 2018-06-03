package com.technorabit.agritech.fragment.dailog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.technorabit.agritech.R;


/**
 * Created by Raja on 30-05-2016.
 */
public class BaseLoadingFragment extends BaseDialogFragment {
    private static final String TITLE_TAG = "title";
    private static final String BODY_TAG = "body";
    private static final String CLOSE_BTN_TAG = "closeBtn";
    private static final String LOADING_TAG = "loading";
    private static final String ICON_TAG = "icon";
    private static final String OK_TAG = "OK_text";
    private static final String CANCEL_TAG = "CANCEL_TEXT";
    private static final String NEED_TWO_BTNS = "TWO_BTNS";

    private String title;
    private String body;
    private boolean needTwoBtns = false;
    private TwoBtnClickListener twoBtnClickListener;


    public static BaseLoadingFragment newInstance(String title, String body, boolean isLoadingReq, boolean isReqCloseBtn) {
        return newInstance(title, body, isLoadingReq, isReqCloseBtn, 0);
    }

    public static BaseLoadingFragment newInstance(String title, String body, boolean isLoadingReq) {
        BaseLoadingFragment fragment = newInstance(title, body);
        Bundle args = fragment.getArguments();
        args.putBoolean(LOADING_TAG, isLoadingReq);
        return fragment;
    }

    public static BaseLoadingFragment newInstance(String title, String body) {
        Bundle args = new Bundle();
        args.putString(TITLE_TAG, title);
        args.putString(BODY_TAG, body);
        BaseLoadingFragment fragment = new BaseLoadingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static BaseLoadingFragment newInstance(String title, String body, boolean isLoadingReq, boolean isReqCloseBtn, int icon) {
        BaseLoadingFragment fragment = newInstance(title, body);
        Bundle args = fragment.getArguments();
        args.putBoolean(LOADING_TAG, isLoadingReq);
        args.putBoolean(CLOSE_BTN_TAG, isReqCloseBtn);
        if (icon != 0)
            args.putInt(ICON_TAG, icon);
        fragment.setArguments(args);
        return fragment;
    }

    public static BaseLoadingFragment newInstance(String title, String body, String positiveBtnText, String negativeBtnText) {
        BaseLoadingFragment fragment = newInstance(title, body);
        Bundle args = fragment.getArguments();
        args.putString(OK_TAG, positiveBtnText);
        args.putString(CANCEL_TAG, negativeBtnText);
        args.putBoolean(NEED_TWO_BTNS, true);
        fragment.setArguments(args);
        return fragment;
    }

    public static BaseLoadingFragment newInstance(String title, String body, String positiveBtnText) {
        BaseLoadingFragment fragment = newInstance(title, body);
        Bundle args = fragment.getArguments();
        args.putString(OK_TAG, positiveBtnText);
        args.putBoolean(NEED_TWO_BTNS, false);
        fragment.setArguments(args);
        return fragment;
    }

    private void parseArguments() {
        try {
            Bundle args = getArguments();
            title = args.getString(TITLE_TAG);
            body = args.getString(BODY_TAG);
            needTwoBtns = args.getBoolean(NEED_TWO_BTNS);
        } catch (Exception e) {
//            CustomExceptionHandler.logDialogFragmentException(BaseLoadingFragment.this, e);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        try {
            View view = super.onCreateView(inflater, container, savedInstanceState);

            parseArguments();

            setTitleText(title);

            View layout = inflater.inflate(R.layout.dialog_fragment_base_body, null);

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);

            viewContainer.addView(layout, params);

            if (getArguments().containsKey(ICON_TAG)) {
                ((ImageView) layout.findViewById(R.id.dialog_loading_image)).setImageResource(getArguments().getInt(ICON_TAG));
            } else {
                layout.findViewById(R.id.dialog_loading_image).setVisibility(View.GONE);
            }

            if (getArguments().containsKey(CLOSE_BTN_TAG) && getArguments().getBoolean(CLOSE_BTN_TAG)) {
                //  showCloseButton(getArguments().getBoolean(CLOSE_BTN_TAG));
                layout.findViewById(R.id.okBtn).setVisibility(View.VISIBLE);
                if (getArguments().containsKey(OK_TAG))
                    ((Button) layout.findViewById(R.id.okBtn)).setText(getArguments().getString(OK_TAG));

                // layout.findViewById(R.id.cancelBtn).setVisibility(View.GONE);
                layout.findViewById(R.id.okBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            dismiss();
                        } catch (Exception e) {
//                            CustomExceptionHandler.logDialogFragmentException(BaseLoadingFragment.this, e);
                        }
                    }
                });
            } else {
                if (getArguments().containsKey(OK_TAG)) {
                    ((Button) layout.findViewById(R.id.okBtn)).setText(getArguments().getString(OK_TAG));
                    ((Button) layout.findViewById(R.id.okBtn)).setVisibility(View.VISIBLE);
                }
                layout.findViewById(R.id.okBtn).setVisibility(View.GONE);
            }

            if (getArguments().containsKey(OK_TAG)) {
                ((Button) layout.findViewById(R.id.okBtn)).setText(getArguments().getString(OK_TAG));
                ((Button) layout.findViewById(R.id.okBtn)).setVisibility(View.VISIBLE);
                ((Button) layout.findViewById(R.id.okBtn)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });
            }

            if (getArguments().containsKey(CANCEL_TAG) && getArguments().containsKey(OK_TAG) && getArguments().getBoolean(NEED_TWO_BTNS)) {
                layout.findViewById(R.id.okBtn).setVisibility(View.VISIBLE);
                ((Button) layout.findViewById(R.id.okBtn)).setText(getArguments().getString(OK_TAG));
                layout.findViewById(R.id.okBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (twoBtnClickListener != null)
                            try {
                                twoBtnClickListener.onPositiveClick();
                            } catch (Exception e) {
//                                CustomExceptionHandler.logDialogFragmentException(BaseLoadingFragment.this, e);
                            }

                        dismiss();
                    }
                });
                layout.findViewById(R.id.cancelBtn).setVisibility(View.VISIBLE);
                ((Button) layout.findViewById(R.id.cancelBtn)).setText(getArguments().getString(CANCEL_TAG));
                layout.findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (twoBtnClickListener != null)
                                twoBtnClickListener.onNegativeClick();
                            dismiss();
                        } catch (Exception e) {
//                            CustomExceptionHandler.logDialogFragmentException(BaseLoadingFragment.this, e);
                        }
                    }
                });
            }

            if (getArguments().containsKey(LOADING_TAG) && getArguments().getBoolean(LOADING_TAG, false)) {
                layout.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
            } else {
                layout.findViewById(R.id.progressBar).setVisibility(View.GONE);
            }


            TextView bodyTextView = (TextView) layout.findViewById(R.id.body);
            bodyTextView.setText(body);


            return view;

        } catch (Exception e) {
//            CustomExceptionHandler.logDialogFragmentException(BaseLoadingFragment.this, e);
        }
        return null;
    }

    public void setTwoBtnClickListener(TwoBtnClickListener twoBtnClickListener) {
        try {
            this.twoBtnClickListener = twoBtnClickListener;
        } catch (Exception e) {
//            CustomExceptionHandler.logDialogFragmentException(BaseLoadingFragment.this, e);
        }
    }

    public interface TwoBtnClickListener {
        void onPositiveClick();

        void onNegativeClick();
    }


}
