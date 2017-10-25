

package com.example.saaibi.parcial.Model;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.saaibi.parcial.Domain.Event;

public class EventDetailViewModel {

  private Event event;

  public EventDetailViewModel(Event event) {
    this.event = event;
  }

  public String getNameEvent() {
    return event.getNameEvent();
  }

  public String getAttenEvent() {return event.getAttenEvent();}

  public String getTipeEvent() {return event.getTipeEvent();}

  public String getCityEvent() {return event.getCityEvent();}

  public String getDateEvent() {
    return event.getDateEvent();
  }

  public String getHourEvent() {
    return event.getHourEvent();
  }

  public String getRequirementEvent() {
    return event.getRequirementEvent();
  }

  public String getDescriptionEvent() {
    return event.getDescriptionEvent();
  }

  public String getPictureProfile() {
    return event.getHourEvent();
  }

  @BindingAdapter({"imageUrl"})
  public static void loadImage(ImageView view, String imageUrl) {
    Glide.with(view.getContext()).load(imageUrl).into(view);
  }
}
