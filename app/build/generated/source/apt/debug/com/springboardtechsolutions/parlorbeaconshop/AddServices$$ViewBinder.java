// Generated code from Butter Knife. Do not modify!
package com.springboardtechsolutions.parlorbeaconshop;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AddServices$$ViewBinder<T extends com.springboardtechsolutions.parlorbeaconshop.AddServices> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492982, "field 'ServiceNameText'");
    target.ServiceNameText = finder.castView(view, 2131492982, "field 'ServiceNameText'");
    view = finder.findRequiredView(source, 2131492985, "field 'ServiceDurText'");
    target.ServiceDurText = finder.castView(view, 2131492985, "field 'ServiceDurText'");
  }

  @Override public void unbind(T target) {
    target.ServiceNameText = null;
    target.ServiceDurText = null;
  }
}
