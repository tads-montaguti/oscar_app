json.extract! vote, :id, :user_id, :category, :identifier, :payload, :created_at, :updated_at
json.url vote_url(vote, format: :json)
