class User < ApplicationRecord
  has_one :vote, dependent: :destroy
  acts_as_token_authenticatable

  devise :database_authenticatable, :registerable,
         :recoverable, :rememberable, :validatable

  before_create :set_voting_token

  private

  def set_voting_token
    self.voting_token = rand(0..100)
  end
end
