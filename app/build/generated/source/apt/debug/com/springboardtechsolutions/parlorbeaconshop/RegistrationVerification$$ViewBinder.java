// Generated code from Butter Knife. Do not modify!
package com.springboardtechsolutions.parlorbeaconshop;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RegistrationVerification$$ViewBinder<T extends com.springboardtechsolutions.parlorbeaconshop.RegistrationVerification> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492997, "field 'Register_Pin'");
    target.Register_Pin = finder.castView(view, 2131492997, "field 'Register_Pin'");
  }

  @Override public void unbind(T target) {
    target.Register_Pin = null;
  }
}
