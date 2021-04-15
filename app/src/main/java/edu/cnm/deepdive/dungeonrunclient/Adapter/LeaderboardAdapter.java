package edu.cnm.deepdive.dungeonrunclient.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.dungeonrunclient.databinding.ItemValueCountBinding;
import edu.cnm.deepdive.dungeonrunclient.model.Level;
import edu.cnm.deepdive.dungeonrunclient.model.User;
import java.util.Date;
import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<Holder> {

  private final Context context;
  private final List<Level> levels;
  private final List<User> users;
  private final LayoutInflater inflater;

  public LeaderboardAdapter(Context context, List<Level> levels, List<User> users) {
    this.context = context;
    this.levels = levels;
    this.users = users;
    inflater = LayoutInflater.from(context);
  }

  public List<Level> getLevels() {
    return levels;
  }

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemValueCountBinding binding = ItemValueCountBinding.inflate(inflater, parent, false);
    return new Holder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    holder.Bind(levels.get(position), users.get(position));
  }

  @Override
  public int getItemCount() {
    return levels.size();
  }
}

 class Holder extends RecyclerView.ViewHolder {

  private final ItemValueCountBinding valueBinding;
  private Level level;
  private User user;

   public Holder(@NonNull ItemValueCountBinding binding) {
     super(binding.getRoot());
     this.valueBinding = binding;
     binding.getRoot();
   }

   void Bind(Level level, User user) {
     String userLeaderboard = user.getDisplayName();
     int difficulty = level.getDifficulty();
     Date endTime = level.getEndTime();
     valueBinding.userName.setText(userLeaderboard.toString());
     valueBinding.difficulty.setText(difficulty);
     valueBinding.timeTaken.setText((CharSequence) endTime);
     valueBinding.getRoot();
   }
 }