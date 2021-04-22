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

public class LeaderboardAdapter extends RecyclerView.Adapter<Holder> {

  private final Context context;
  private final List<Attempt> attempts;
  private final LayoutInflater inflater;

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


  public class Holder extends RecyclerView.ViewHolder {

    private final ItemValueCountBinding binding;
    private Attempt attempt;

    public Holder(ItemValueCountBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
      binding.getRoot();
    }

    public void bind(int position) {
      attempt = attempts.get(position);
      binding.userName.setText(attempt.getUser().getDisplayName());
      binding.difficulty.setText(String.valueOf(attempt.getDifficulty()));
      binding.timeElapsed.setText(String.valueOf(attempt.getTimeElapsed()));
    }
  }
}