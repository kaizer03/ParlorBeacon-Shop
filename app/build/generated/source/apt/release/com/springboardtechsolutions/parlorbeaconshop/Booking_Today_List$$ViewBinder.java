// Generated code from Butter Knife. Do not modify!
package com.springboardtechsolutions.parlorbeaconshop;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class Booking_Today_List$$ViewBinder<T extends com.springboardtechsolutions.parlorbeaconshop.Booking_Today_List> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493078, "field 'CustName'");
    target.CustName = finder.castView(view, 2131493078, "field 'CustName'");
    view = finder.findRequiredView(source, 2131493079, "field 'CustEmail'");
    target.CustEmail = finder.castView(view, 2131493079, "field 'CustEmail'");
    view = finder.findRequiredView(source, 2131493080, "field 'CustService'");
    target.CustService = finder.castView(view, 2131493080, "field 'CustService'");
    view = finder.findRequiredView(source, 2131493081, "field 'CustServTime'");
    target.CustServTime = finder.castView(view, 2131493081, "field 'CustServTime'");
    view = finder.findRequiredView(source, 2131493084, "field 'relativeLayout'");
    target.relativeLayout = finder.castView(view, 2131493084, "field 'relativeLayout'");
    view = finder.findRequiredView(source, 2131493082, "field 'CustServStatus'");
    target.CustServStatus = finder.castView(view, 2131493082, "field 'CustServStatus'");
  }

  @Override public void unbind(T target) {
    target.CustName = null;
    target.CustEmail = null;
    target.CustService = null;
    target.CustServTime = null;
    target.relativeLayout = null;
    target.CustServStatus = null;
  }
}
