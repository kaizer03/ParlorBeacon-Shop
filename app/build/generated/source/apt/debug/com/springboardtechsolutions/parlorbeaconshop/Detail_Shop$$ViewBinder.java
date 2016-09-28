// Generated code from Butter Knife. Do not modify!
package com.springboardtechsolutions.parlorbeaconshop;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class Detail_Shop$$ViewBinder<T extends com.springboardtechsolutions.parlorbeaconshop.Detail_Shop> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493047, "field 'profilepic'");
    target.profilepic = finder.castView(view, 2131493047, "field 'profilepic'");
    view = finder.findRequiredView(source, 2131493048, "field 'frontpic'");
    target.frontpic = finder.castView(view, 2131493048, "field 'frontpic'");
    view = finder.findRequiredView(source, 2131493050, "field 'shopkeepernametext'");
    target.shopkeepernametext = finder.castView(view, 2131493050, "field 'shopkeepernametext'");
    view = finder.findRequiredView(source, 2131493051, "field 'shopnametext'");
    target.shopnametext = finder.castView(view, 2131493051, "field 'shopnametext'");
    view = finder.findRequiredView(source, 2131493052, "field 'emailtext'");
    target.emailtext = finder.castView(view, 2131493052, "field 'emailtext'");
    view = finder.findRequiredView(source, 2131493053, "field 'shopphonetext'");
    target.shopphonetext = finder.castView(view, 2131493053, "field 'shopphonetext'");
    view = finder.findRequiredView(source, 2131493054, "field 'shopaddrtext'");
    target.shopaddrtext = finder.castView(view, 2131493054, "field 'shopaddrtext'");
    view = finder.findRequiredView(source, 2131493055, "field 'shopcitytext'");
    target.shopcitytext = finder.castView(view, 2131493055, "field 'shopcitytext'");
    view = finder.findRequiredView(source, 2131493056, "field 'shopziptext'");
    target.shopziptext = finder.castView(view, 2131493056, "field 'shopziptext'");
    view = finder.findRequiredView(source, 2131493057, "field 'shopopentext'");
    target.shopopentext = finder.castView(view, 2131493057, "field 'shopopentext'");
    view = finder.findRequiredView(source, 2131493058, "field 'shopclosetext'");
    target.shopclosetext = finder.castView(view, 2131493058, "field 'shopclosetext'");
    view = finder.findRequiredView(source, 2131493049, "method 'onClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.profilepic = null;
    target.frontpic = null;
    target.shopkeepernametext = null;
    target.shopnametext = null;
    target.emailtext = null;
    target.shopphonetext = null;
    target.shopaddrtext = null;
    target.shopcitytext = null;
    target.shopziptext = null;
    target.shopopentext = null;
    target.shopclosetext = null;
  }
}
