// Generated code from Butter Knife. Do not modify!
package com.springboardtechsolutions.parlorbeaconshop;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class Single_Service$$ViewBinder<T extends com.springboardtechsolutions.parlorbeaconshop.Single_Service> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493107, "field 'ServiceName'");
    target.ServiceName = finder.castView(view, 2131493107, "field 'ServiceName'");
    view = finder.findRequiredView(source, 2131493108, "field 'ServiceDuration'");
    target.ServiceDuration = finder.castView(view, 2131493108, "field 'ServiceDuration'");
  }

  @Override public void unbind(T target) {
    target.ServiceName = null;
    target.ServiceDuration = null;
  }
}
