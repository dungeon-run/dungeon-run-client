package edu.cnm.deepdive.dungeonrunclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.dungeonrunclient.adapter.LeaderboardAdapter.Holder;
import edu.cnm.deepdive.dungeonrunclient.databinding.ItemValueCountBinding;
import edu.cnm.deepdive.dungeonrunclient.model.Attempt;
import java.util.List;

/**
 * Leaderboard adapter collects the information needed to display the database views for the
 * recycler view in the leaderboard fragment.
 */
public class LeaderboardAdapter extends RecyclerView.Adapter<Holder> {

  private final Context context;
  private final List<Attempt> attempts;
  private final LayoutInflater inflater;

  /**
   * Sets up the fields to be used in the class.
   * @param context Context passes the context of this class.
   * @param attempts Creates an instance of attempts for use.
   */
  public LeaderboardAdapter(Context context, List<Attempt> attempts) {
    this.context = context;
    this.attempts = attempts;
    inflater = LayoutInflater.from(context);
  }

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemValueCountBinding binding = ItemValueCountBinding.inflate(inflater, parent, false);
    return new Holder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    holder.bind(position);
  }

  @Override
  public int getItemCount() {
    return attempts.size();
  }

  /**
   * Sub class to hold all of the objects gained from the service side.
   */
  public class Holder extends RecyclerView.ViewHolder {

    private final ItemValueCountBinding binding;
    private Attempt attempt;

    /**
     * Holder to set up the binding for valueCount layout.
     * @param binding Creates an instance of binding to bind the valueCount to the database entries.
     */
    public Holder(ItemValueCountBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
      binding.getRoot();
    }

    /**
     * Binds the valueCount layout to the individual positions of the items from the service.
     * @param position The positions of integers for the leaderboard display.
     */
    public void bind(int position) {
      attempt = attempts.get(position);
      binding.userName.setText(attempt.getUser().getDisplayName());
      binding.difficulty.setText(String.valueOf(attempt.getDifficulty()));
      binding.timeElapsed.setText(String.valueOf(attempt.getTimeElapsed()));
    }
  }
}