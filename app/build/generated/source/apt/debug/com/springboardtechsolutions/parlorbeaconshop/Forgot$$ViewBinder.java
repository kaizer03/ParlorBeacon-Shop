// Generated code from Butter Knife. Do not modify!
package com.springboardtechsolutions.parlorbeaconshop;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class Forgot$$ViewBinder<T extends com.springboardtechsolutions.parlorbeaconshop.Forgot> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493028, "field 'emailtext'");
    target.emailtext = finder.castView(view, 2131493028, "field 'emailtext'");
    view = finder.findRequiredView(source, 2131493029, "method 'onClick1'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick1(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.emailtext = null;
  }
}
