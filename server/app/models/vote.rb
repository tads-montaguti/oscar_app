class Vote < ApplicationRecord
  belongs_to :user

  attr_accessor :voting_token

  validates :user, presence: true, uniqueness: true
  validate :validate_voting_token, on: :create

  private

  def validate_voting_token
    errors.add(:voting_token, "is missing") if voting_token.nil? || voting_token.to_s.empty?
    errors.add(:voting_token, "is invalid") if self.user.voting_token.to_s != voting_token.to_s
  end
end
