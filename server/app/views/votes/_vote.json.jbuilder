json.extract! vote, :id, :user_id, :best_movie_id, :best_director_id, :payload, :created_at, :updated_at
json.url vote_url(vote, format: :json)
