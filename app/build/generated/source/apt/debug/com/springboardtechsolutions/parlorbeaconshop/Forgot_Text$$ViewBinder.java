// Generated code from Butter Knife. Do not modify!
package com.springboardtechsolutions.parlorbeaconshop;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class Forgot_Text$$ViewBinder<T extends com.springboardtechsolutions.parlorbeaconshop.Forgot_Text> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492992, "field 'forgot_pin_text'");
    target.forgot_pin_text = finder.castView(view, 2131492992, "field 'forgot_pin_text'");
  }

  @Override public void unbind(T target) {
    target.forgot_pin_text = null;
  }
}
