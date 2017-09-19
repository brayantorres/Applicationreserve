
package com.example.saaibi.parcial.Model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.saaibi.parcial.Domain.Event;
import com.example.saaibi.parcial.Views.EventDetailActivity;

/**
 * Created by SAAIBI on 13/9/2017.
 */
public class ItemEventViewModel extends BaseObservable {

  private Event event;
  private Context context;

  public ItemEventViewModel(Event event, Context context) {
    this.event = event;
    this.context = context;
  }

  public String getNameEvent() {
    return event.getNameEvent();
  }

  public String getTypeEvent() {
    return event.getTipeEvent();
  }

  public String getHourEvent() {
    return event.getDateEvent();
  }

  public String getPictureProfile() {
    return event.getTipeEvent();
  }

  @BindingAdapter("imageUrl") public static void setImageUrl(ImageView imageView, String url) {
    Glide.with(imageView.getContext()).load(url).into(imageView);
  }

  public void onItemClick(View view) {
    context.startActivity(EventDetailActivity.launchDetail(view.getContext(), event));
  }

  public void setEvent(Event event) {
    this.event = event;
    notifyChange();
  }
}
