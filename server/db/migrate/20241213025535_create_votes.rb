class CreateVotes < ActiveRecord::Migration[7.1]
  def change
    create_table :votes do |t|
      t.belongs_to :user, null: false, foreign_key: true
      t.string :payload
      t.string :best_movie_id
      t.string :best_director_id

      t.timestamps
    end
  end
end
