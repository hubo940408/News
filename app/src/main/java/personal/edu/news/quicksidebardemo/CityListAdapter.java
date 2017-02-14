package personal.edu.news.quicksidebardemo;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import personal.edu.news.quicksidebardemo.constants.ExpressCom;
import personal.edu.news.quicksidebardemo.model.City;


/**
 * Adapter holding a list of animal names of type String. Note that each item must be unique.
 */
public abstract class CityListAdapter <VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
  private ArrayList<ExpressCom> items = new ArrayList<>();

  public CityListAdapter() {
    setHasStableIds(true);
  }

  public void add(ExpressCom object) {
    items.add(object);
    notifyDataSetChanged();
  }

  public void add(int index, ExpressCom object) {
    items.add(index, object);
    notifyDataSetChanged();
  }

  public void addAll(Collection<? extends ExpressCom> collection) {
    if (collection != null) {
      items.addAll(collection);
      notifyDataSetChanged();
    }
  }

  public void addAll(ExpressCom... items) {
    addAll(Arrays.asList(items));
  }

  public void clear() {
    items.clear();
    notifyDataSetChanged();
  }

  public void remove(String object) {
    items.remove(object);
    notifyDataSetChanged();
  }

  public ExpressCom getItem(int position) {
    return items.get(position);
  }

  @Override
  public long getItemId(int position) {
    return getItem(position).hashCode();
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

    @Override
    public void onBindViewHolder(VH holder, final int position) {

    }



}
