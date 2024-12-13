class CreateVotes < ActiveRecord::Migration[7.1]
  def change
    create_table :votes do |t|
      t.belongs_to :user, null: false, foreign_key: true
      t.string :category
      t.string :identifier
      t.string :payload

      t.timestamps
    end
  end
end
