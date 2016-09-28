// Generated code from Butter Knife. Do not modify!
package com.springboardtechsolutions.parlorbeaconshop;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class Reset_Password$$ViewBinder<T extends com.springboardtechsolutions.parlorbeaconshop.Reset_Password> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493001, "field 'pass1'");
    target.pass1 = finder.castView(view, 2131493001, "field 'pass1'");
    view = finder.findRequiredView(source, 2131493002, "field 'pass2'");
    target.pass2 = finder.castView(view, 2131493002, "field 'pass2'");
  }

  @Override public void unbind(T target) {
    target.pass1 = null;
    target.pass2 = null;
  }
}
