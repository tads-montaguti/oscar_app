class User < ApplicationRecord
  has_many :votes
  acts_as_token_authenticatable

  devise :database_authenticatable, :registerable,
         :recoverable, :rememberable, :validatable

  before_update :set_voting_token, if: :user_attributes_changed?

  private

  def set_voting_token
    self.voting_token = rand(0..100)
  end

  def user_attributes_changed?
    (changed - ['updated_at']).any?
  end
end
