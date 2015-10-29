package com.wonders.xlab.uikit.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;
import com.wonders.xlab.uikit.R;

/**
 * Created by hua on 15/10/12.
 * 带有前后缀的TextView
 */
public class FixTextView extends TextView {
  private String prefix = "";//前缀
  private String suffix = "";//后缀

  public FixTextView(Context context) {
    this(context, null);
  }

  public FixTextView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public FixTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    TypedArray array =
        context.getTheme().obtainStyledAttributes(attrs, R.styleable.FixTextView, 0, 0);

    int index;
    for (int i = 0; i < array.getIndexCount(); i++) {
      index = array.getIndex(i);
      if (R.styleable.FixTextView_prefix == index) {
        prefix = array.getString(R.styleable.FixTextView_prefix);
      } else if (R.styleable.FixTextView_suffix == index) {
        suffix = array.getString(R.styleable.FixTextView_suffix);
      }
    }

    array.recycle();
  }

  public void setText(String text) {
    super.setText(prefix + text + suffix);
  }

  public void setPrefix(String mPrefix) {
    this.prefix = mPrefix;
    this.setText(super.getText());
  }

  public void setSuffix(String mSuffix) {
    this.suffix = mSuffix;
    this.setText(super.getText());
  }
}
