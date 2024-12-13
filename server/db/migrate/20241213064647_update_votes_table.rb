class UpdateVotesTable < ActiveRecord::Migration[7.1]
  def change
    remove_index :votes, :user_id
    add_index :votes, :user_id, unique: true
  end
end
