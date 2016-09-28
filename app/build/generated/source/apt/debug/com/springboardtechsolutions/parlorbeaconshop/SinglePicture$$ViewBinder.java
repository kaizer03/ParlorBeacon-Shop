// Generated code from Butter Knife. Do not modify!
package com.springboardtechsolutions.parlorbeaconshop;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SinglePicture$$ViewBinder<T extends com.springboardtechsolutions.parlorbeaconshop.SinglePicture> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493095, "field 'singleimage'");
    target.singleimage = finder.castView(view, 2131493095, "field 'singleimage'");
  }

  @Override public void unbind(T target) {
    target.singleimage = null;
  }
}
