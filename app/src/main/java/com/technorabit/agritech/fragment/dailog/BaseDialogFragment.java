package com.technorabit.agritech.fragment.dailog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.technorabit.agritech.R;


/**
 * Created by Raja.P on 30-05-2016.
 */
public class BaseDialogFragment extends DialogFragment {

    public static final String FRAGMENT_TAG = "DialogFragment";
    private static int commitNumber = 0;
    protected FrameLayout viewContainer;
    private TextView titleTextView;
    private View closeButton;
    private DialogInterface.OnDismissListener dismissListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.BaseDialogTheme);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        getDialog().setCanceledOnTouchOutside(getCancelble());
        getDialog().requestWindowFeature(STYLE_NO_TITLE);
        getDialog().setCancelable(getCancelble());
        setCancelable(getCancelble());
        View view = inflater.inflate(R.layout.dialog_fragment_simple_text, container);
        titleTextView = (TextView) view.findViewById(R.id.iht_dialog_fragment_title);
        viewContainer = (FrameLayout) view.findViewById(R.id.iht_dialog_fragment_container);
        closeButton = view.findViewById(R.id.x_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }


    public void setCancelableDialog(boolean cancelable) {
        getDialog().setCancelable(cancelable);
    }

    /**
     * Set the title of the dialog fragment
     *
     * @param text title
     */
    protected final void setTitleText(final String text) {
        titleTextView.setText(text);
    }


    /**
     * Sets title bar height
     *
     * @param height height in dp
     */
    protected final void setTitleHeight(final int height) {
        titleTextView.setHeight((int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, height, getResources().getDisplayMetrics())));
    }

    /**
     * Set the visibility of the close button. By default, close button is GONE.
     *
     * @param show show
     */
    public final void showCloseButton(final boolean show) {
        closeButton.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    protected final void hideTitle() {
        titleTextView.setVisibility(View.GONE);
    }


    /**
     * Callback for dismiss of this dialog fragment.
     *
     * @param dismissListener Listener for dismiss
     */
    public final BaseDialogFragment setOnDismissListener(final DialogInterface.OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
        return this;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (dismissListener != null) {
            dismissListener.onDismiss(dialog);
        }
    }

    @Override
    public void show(FragmentManager manager, String tag) {
//        if (commitNumber == 0) {
        if (!manager.isDestroyed())
            try {
                commitNumber = super.show(manager.beginTransaction(), tag);
            } catch (IllegalStateException e) {

            }
//        }
    }

    @Override
    public void dismiss() {
        if (getFragmentManager() != null && getFragmentManager().beginTransaction() != null)
            try {
                super.dismiss();
            } catch (IllegalStateException e) {

            }
        commitNumber = 0;
    }

    public boolean getCancelble() {
        return false;
    }
}
