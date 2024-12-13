json.extract! user, :id, :name, :voting_token, :created_at, :updated_at
json.url user_url(user, format: :json)
