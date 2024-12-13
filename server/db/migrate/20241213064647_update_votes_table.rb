class UpdateVotesTable < ActiveRecord::Migration[7.1]
  def change
    remove_index :votes, :user_id
    add_index :votes, :user_id, unique: true

    change_table :votes do |t|
      t.string :best_movie_id
      t.string :best_director_id
    end
  end
end
