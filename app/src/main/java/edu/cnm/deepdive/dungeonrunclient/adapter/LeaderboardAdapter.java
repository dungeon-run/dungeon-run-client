package edu.cnm.deepdive.dungeonrunclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.dungeonrunclient.databinding.ItemValueCountBinding;
import edu.cnm.deepdive.dungeonrunclient.model.Attempt;
import edu.cnm.deepdive.dungeonrunclient.model.User;
import java.util.List;

/**
 *
 */
public class LeaderboardAdapter extends RecyclerView.Adapter<Holder> {

  private final Context context;
  private final List<Attempt> attempts;
  private final List<User> users;
  private final LayoutInflater inflater;

  public LeaderboardAdapter(Context context, List<Attempt> attempts, List<User> users) {
    this.context = context;
    this.attempts = attempts;
    this.users = users;
    inflater = LayoutInflater.from(context);
  }

  public List<Attempt> getAttempts() {
    return attempts;
  }

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemValueCountBinding binding = ItemValueCountBinding.inflate(inflater, parent, false);
    return new Holder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    holder.Bind(attempts.get(position), users.get(position));
  }

  @Override
  public int getItemCount() {
    return attempts.size();
  }
}

 class Holder extends RecyclerView.ViewHolder {

  private final ItemValueCountBinding valueBinding;
  private Attempt attempt;
  private User user;

   public Holder(@NonNull ItemValueCountBinding binding) {
     super(binding.getRoot());
     this.valueBinding = binding;
     binding.getRoot();
   }

   void Bind(Attempt attempt, User user) {
     String userLeaderboard = user.getDisplayName();
     int difficulty = attempt.getDifficulty();
     long timeElapsed = attempt.getTimeElapsed();
     valueBinding.userName.setText(userLeaderboard);
     valueBinding.difficulty.setText(difficulty);
     valueBinding.timeElapsed.setText((int) timeElapsed);
     valueBinding.getRoot();
   }
 }