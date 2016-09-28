// Generated code from Butter Knife. Do not modify!
package com.springboardtechsolutions.parlorbeaconshop;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class Booking_List$$ViewBinder<T extends com.springboardtechsolutions.parlorbeaconshop.Booking_List> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493019, "field 'CustName'");
    target.CustName = finder.castView(view, 2131493019, "field 'CustName'");
    view = finder.findRequiredView(source, 2131493020, "field 'CustEmail'");
    target.CustEmail = finder.castView(view, 2131493020, "field 'CustEmail'");
    view = finder.findRequiredView(source, 2131493021, "field 'CustService'");
    target.CustService = finder.castView(view, 2131493021, "field 'CustService'");
    view = finder.findRequiredView(source, 2131493022, "field 'CustServTime'");
    target.CustServTime = finder.castView(view, 2131493022, "field 'CustServTime'");
    view = finder.findRequiredView(source, 2131493023, "field 'CustServStatus'");
    target.CustServStatus = finder.castView(view, 2131493023, "field 'CustServStatus'");
    view = finder.findRequiredView(source, 2131493024, "field 'CustServDate'");
    target.CustServDate = finder.castView(view, 2131493024, "field 'CustServDate'");
    view = finder.findRequiredView(source, 2131493026, "field 'relativeLayout'");
    target.relativeLayout = finder.castView(view, 2131493026, "field 'relativeLayout'");
  }

  @Override public void unbind(T target) {
    target.CustName = null;
    target.CustEmail = null;
    target.CustService = null;
    target.CustServTime = null;
    target.CustServStatus = null;
    target.CustServDate = null;
    target.relativeLayout = null;
  }
}
